package Registration.Api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import java.net.URI;
import java.net.ServerSocket;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Duration;
import java.time.Instant;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

/**
 * End-to-end HTTP tests using a disposable, real PostgreSQL server process.
 * No mocked repository, in-memory database, Docker daemon, or existing user database is used.
 */
@Tag("postgres")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ApplicationPostgresIntegrationTest {

  private final ObjectMapper json = new ObjectMapper();
  private final HttpClient http = HttpClient.newBuilder()
      .connectTimeout(Duration.ofSeconds(10)).build();
  private EmbeddedPostgres postgres;
  private ServletWebServerApplicationContext application;
  private String baseUrl;

  @BeforeAll
  void startPostgresAndApplication() throws Exception {
    postgres = EmbeddedPostgres.builder().setPort(0).start();
    startApplication();
    assertTrue(queryText("SELECT version()").startsWith("PostgreSQL"),
        "Integration tests must actually run against PostgreSQL");
  }

  private void startApplication() {
    application = (ServletWebServerApplicationContext) new SpringApplicationBuilder(
        RegistrationServiceApplication.class).run(
        "--server.port=0",
        "--spring.datasource.url=" + postgres.getJdbcUrl("postgres", "postgres"),
        "--spring.datasource.username=postgres",
        "--spring.datasource.password=",
        "--app.evaluation-date=2026-09-22",
        "--spring.main.banner-mode=off",
        "--logging.level.root=WARN");
    baseUrl = "http://127.0.0.1:" + application.getWebServer().getPort();
  }

  @BeforeEach
  void emptyDisposableDatabase() throws Exception {
    execute("TRUNCATE TABLE applications CASCADE");
  }

  @AfterAll
  void stopApplicationAndPostgres() throws Exception {
    if (application != null) {
      application.close();
    }
    if (postgres != null) {
      postgres.close();
    }
  }

  @Test
  void approvedApplicationPersistsAllBusinessDataAndCanBeRetrieved() throws Exception {
    ObjectNode request = approved();
    Response created = post("approved-001", request);

    assertEquals(201, created.status(), created.raw());
    UUID.fromString(created.body().path("id").asText());
    assertEquals("APPROVED", created.body().path("status").asText());
    assertEquals(0, created.body().path("failureReasons").size());
    assertEquals("2026-09-22", created.body().path("evaluationDate").asText());
    assertEquals("eligibility-six-rules-v1", created.body().path("ruleVersion").asText());
    Instant submitted = Instant.parse(created.body().path("submittedAt").asText());
    Instant evaluated = Instant.parse(created.body().path("evaluatedAt").asText());
    assertFalse(evaluated.isBefore(submitted));

    Response fetched = get("/api/applications/" + created.body().path("id").asText());
    assertEquals(200, fetched.status(), fetched.raw());
    assertEquals(created.body(), fetched.body());
    JsonNode saved = fetched.body().path("application");
    assertEquals("Morgan", saved.path("driver").path("firstName").asText());
    assertEquals("SYNTHETIC-MA-001", saved.path("license").path("number").asText());
    assertEquals(2, saved.path("vehicles").size());
    assertEquals(2, saved.path("insurance").path("drivers").size());
    assertEquals(1, saved.path("driverHistory").size());
    assertEquals(1, saved.path("vehicleHistory").size());
    assertEquals("FENDER_BENDER", saved.path("vehicleHistory").get(0).path("type").asText());
    assertEquals(1, count("applications"));
    assertEquals(2, count("application_vehicles"));
    assertEquals(2, count("application_insured_drivers"));
    assertEquals(2, count("application_incidents"));
    assertEquals(0, count("application_failures"));
  }

  @Test
  void businessRejectionIsCreatedAndQueryableWithPersistedReasons() throws Exception {
    Response created = post("rejected-001", rejected());
    assertEquals(201, created.status(), created.raw());
    assertEquals("REJECTED", created.body().path("status").asText());
    assertTrue(created.body().path("failureReasons").size() >= 1);
    assertTrue(created.body().path("failureReasons").toString().toLowerCase().contains("vehicle"));

    Response fetched = get("/api/applications/" + created.body().path("id").asText());
    assertEquals(200, fetched.status(), fetched.raw());
    assertEquals(created.body(), fetched.body());
    assertEquals(created.body().path("failureReasons").size(), count("application_failures"));
  }

  @Test
  void extremeIntegerVehicleYearCannotBypassTheExistingAgeRuleThroughOverflow() throws Exception {
    ObjectNode request = approved();
    vehicle(request).put("year", Integer.MIN_VALUE);
    Response created = post("extreme-vehicle-year", request);
    assertEquals(201, created.status(), created.raw());
    assertEquals("REJECTED", created.body().path("status").asText());
    assertTrue(created.body().path("failureReasons").toString().toLowerCase().contains("vehicle"));
    assertEquals(Integer.MIN_VALUE, created.body().path("application").path("vehicles")
        .get(0).path("year").asInt());
  }

  @ParameterizedTest(name = "invalid input: {0}")
  @MethodSource("invalidApplications")
  void malformedFieldsFailBeforeSaving(String description, Consumer<ObjectNode> mutation)
      throws Exception {
    ObjectNode request = approved();
    mutation.accept(request);
    Response response = post("invalid-" + UUID.randomUUID(), request);
    assertError(response, 400, "INVALID_REQUEST");
    assertEquals(0, count("applications"), description);
    assertEquals(0, count("application_vehicles"), description);
    assertEquals(0, count("application_failures"), description);
  }

  static Stream<Arguments> invalidApplications() {
    return Stream.of(
        invalid("missing driver", a -> a.remove("driver")),
        invalid("missing required name", a -> object(a, "driver").remove("firstName")),
        invalid("numeric name", a -> object(a, "driver").put("firstName", 123)),
        invalid("text contains PostgreSQL-incompatible NUL", a -> object(a, "driver")
            .put("firstName", "Mor\u0000gan")),
        invalid("null name", a -> object(a, "driver").putNull("firstName")),
        invalid("blank license number", a -> object(a, "license").put("number", "  ")),
        invalid("invalid calendar date", a -> object(a, "driver").put("birthDate", "1990-02-30")),
        invalid("invalid date format", a -> object(a, "license").put("expirationDate", "2030/01/01")),
        invalid("boolean date", a -> object(a, "insurance").put("expirationDate", true)),
        invalid("fractional vehicle year", a -> vehicle(a).put("year", 2020.5)),
        invalid("string vehicle year", a -> vehicle(a).put("year", "2020")),
        invalid("overflow vehicle year", a -> vehicle(a).put("year", 2147483648L)),
        invalid("numeric optional text", a -> vehicle(a).put("color", 99)),
        invalid("empty vehicles", a -> a.putArray("vehicles")),
        invalid("duplicate vehicle plate", a -> ((ObjectNode) a.path("vehicles").get(1))
            .put("plate", "DEMO-001")),
        invalid("ambiguous vehicle aliases", a -> a.set("vehicle", a.path("vehicles").get(0))),
        invalid("unknown top-level field", a -> a.put("driverHistroy", "misspelled")),
        invalid("unknown nested field", a -> object(a, "license").put("expiry", "2030-01-01")),
        invalid("driver history object", a -> a.putObject("driverHistory")),
        invalid("driver history string", a -> a.put("driverHistory", "none")),
        invalid("driver history null", a -> a.putNull("driverHistory")),
        invalid("vehicle history object", a -> a.putObject("vehicleHistory")),
        invalid("vehicle history number", a -> a.put("vehicleHistory", 0)),
        invalid("vehicle history null", a -> a.putNull("vehicleHistory")),
        invalid("driver history scalar entry", a -> a.putArray("driverHistory").add("DUI")),
        invalid("vehicle history null entry", a -> a.putArray("vehicleHistory").addNull()),
        invalid("unknown incident category", a -> ((ObjectNode) a.path("driverHistory").get(0))
            .put("category", "UNKNOWN")),
        invalid("unknown incident type", a -> ((ObjectNode) a.path("driverHistory").get(0))
            .put("type", "UNKNOWN")),
        invalid("missing vehicle incident offender", a -> ((ObjectNode) a.path("vehicleHistory").get(0))
            .remove("offender")),
        invalid("incident references absent vehicle", a -> ((ObjectNode) a.path("vehicleHistory").get(0))
            .put("plate", "NOT-IN-APPLICATION")),
        invalid("malformed incident date", a -> ((ObjectNode) a.path("driverHistory").get(0))
            .put("date", "2026-13-01"))
    );
  }

  private static Arguments invalid(String description, Consumer<ObjectNode> mutation) {
    return Arguments.of(description, mutation);
  }

  @Test
  void malformedJsonAndNonObjectBodiesReturn400WithoutSaving() throws Exception {
    for (String body : List.of("{", "[]", "null", "\"text\"", "",
        approved() + " {}", "{\"driver\":{},\"driver\":{}}")) {
      assertError(postRaw("bad-body", body), 400, "INVALID_REQUEST");
    }
    assertEquals(0, count("applications"));
  }

  @Test
  void requestKeyMustBePresentAndMeetItsDocumentedContract() throws Exception {
    for (String key : new String[]{null, "", "key with spaces", "a".repeat(101)}) {
      assertError(post(key, approved()), 400, "INVALID_REQUEST");
    }
    assertEquals(0, count("applications"));
  }

  @Test
  void unknownApplicationReturns404AndMalformedIdReturns400() throws Exception {
    assertError(get("/api/applications/" + UUID.randomUUID()), 404, "NOT_FOUND");
    assertError(get("/api/applications/not-a-uuid"), 400, "INVALID_REQUEST");
  }

  @Test
  void duplicateRequestKeyConflictsButSameDriverMayReapplyWithNewKey() throws Exception {
    Response first = post("stable-request-key", approved());
    assertEquals(201, first.status(), first.raw());
    assertError(post("stable-request-key", approved()), 409, "DUPLICATE_REQUEST");
    assertError(post("stable-request-key", rejected()), 409, "DUPLICATE_REQUEST");
    assertEquals(1, count("applications"));

    Response reapplication = post("new-request-key", approved());
    assertEquals(201, reapplication.status(), reapplication.raw());
    assertNotEquals(first.body().path("id"), reapplication.body().path("id"));
    assertEquals(2, count("applications"));
  }

  @Test
  void concurrentDuplicateRequestsProduceOneCompleteApplication() throws Exception {
    String body = approved().toString();
    List<CompletableFuture<HttpResponse<String>>> calls = new ArrayList<>();
    for (int i = 0; i < 6; i++) {
      calls.add(http.sendAsync(postRequest("racing-key", body), HttpResponse.BodyHandlers.ofString()));
    }
    int created = 0;
    int conflicts = 0;
    for (CompletableFuture<HttpResponse<String>> call : calls) {
      Response response = response(call.join());
      if (response.status() == 201) {
        created++;
      } else {
        assertError(response, 409, "DUPLICATE_REQUEST");
        conflicts++;
      }
    }
    assertEquals(1, created);
    assertEquals(5, conflicts);
    assertEquals(1, count("applications"));
    assertEquals(2, count("application_vehicles"));
    assertEquals(2, count("application_incidents"));
  }

  @Test
  void paginationAndStatusFiltersReturnMatchingApplicationsOnly() throws Exception {
    for (int i = 0; i < 3; i++) {
      assertEquals(201, post("approved-page-" + i, approved()).status());
    }
    for (int i = 0; i < 2; i++) {
      assertEquals(201, post("rejected-page-" + i, rejected()).status());
    }

    Response first = get("/api/applications?page=0&size=2");
    assertEquals(200, first.status(), first.raw());
    assertEquals(5, first.body().path("totalElements").asInt());
    assertEquals(3, first.body().path("totalPages").asInt());
    assertEquals(0, first.body().path("number").asInt());
    assertEquals(2, first.body().path("size").asInt());
    assertEquals(2, first.body().path("content").size());
    Response second = get("/api/applications?page=1&size=2");
    assertEquals(200, second.status(), second.raw());
    for (JsonNode row : first.body().path("content")) {
      for (JsonNode next : second.body().path("content")) {
        assertNotEquals(row.path("id"), next.path("id"));
      }
    }

    Response approved = get("/api/applications?status=APPROVED&page=1&size=2");
    assertEquals(200, approved.status(), approved.raw());
    assertEquals(3, approved.body().path("totalElements").asInt());
    assertEquals(1, approved.body().path("content").size());
    assertEquals("APPROVED", approved.body().path("content").get(0).path("status").asText());
    Response rejected = get("/api/applications?status=REJECTED&size=10");
    assertEquals(200, rejected.status(), rejected.raw());
    assertEquals(2, rejected.body().path("totalElements").asInt());
    rejected.body().path("content").forEach(row -> {
      assertEquals("REJECTED", row.path("status").asText());
      assertTrue(row.path("failureReasons").size() >= 1);
    });
    Response empty = get("/api/applications?page=99&size=10");
    assertEquals(200, empty.status(), empty.raw());
    assertEquals(0, empty.body().path("content").size());
    assertEquals(5, empty.body().path("totalElements").asInt());
  }

  @Test
  void invalidPaginationAndStatusReturn400() throws Exception {
    for (String query : List.of("page=-1", "size=0", "size=101", "page=abc", "status=PENDING",
        "page=2147483647&size=100")) {
      assertError(get("/api/applications?" + query), 400, "INVALID_REQUEST");
    }
  }

  @Test
  void failureDuringChildInsertRollsBackApplicationAndEveryChildTable() throws Exception {
    execute("""
        CREATE FUNCTION test_fail_child_insert() RETURNS trigger LANGUAGE plpgsql AS $$
        BEGIN
          RAISE EXCEPTION 'intentional integration-test child write failure' USING ERRCODE = 'XX000';
        END
        $$
        """);
    execute("""
        CREATE TRIGGER test_fail_child_insert BEFORE INSERT ON application_failures
        FOR EACH ROW EXECUTE FUNCTION test_fail_child_insert()
        """);
    try {
      Response failed = post("rollback-key", rejected());
      assertError(failed, 500, "INTERNAL_ERROR");
      assertFalse(failed.raw().contains("intentional integration-test"),
          "Internal database details must not appear in the public error response");
      for (String table : List.of("applications", "application_vehicles", "application_insured_drivers",
          "application_incidents", "application_failures")) {
        assertEquals(0, count(table), "Rollback must leave no rows in " + table);
      }
    } finally {
      execute("DROP TRIGGER test_fail_child_insert ON application_failures");
      execute("DROP FUNCTION test_fail_child_insert()");
    }
    Response retry = post("rollback-key", rejected());
    assertEquals(201, retry.status(), retry.raw());
    assertEquals(1, count("applications"), "A failed transaction must not consume the request key");
  }

  @Test
  void databaseConstraintConflictHasDistinct409ResponseAndLeavesNoPartialApplication() throws Exception {
    execute("""
        CREATE FUNCTION test_constraint_conflict() RETURNS trigger LANGUAGE plpgsql AS $$
        BEGIN
          RAISE EXCEPTION 'intentional integration-test constraint conflict' USING ERRCODE = '23514';
        END
        $$
        """);
    execute("""
        CREATE TRIGGER test_constraint_conflict BEFORE INSERT ON application_vehicles
        FOR EACH ROW EXECUTE FUNCTION test_constraint_conflict()
        """);
    try {
      Response failed = post("constraint-key", approved());
      assertError(failed, 409, "DATA_CONFLICT");
      assertFalse(failed.raw().contains("intentional integration-test"));
      for (String table : List.of("applications", "application_vehicles", "application_insured_drivers",
          "application_incidents", "application_failures")) {
        assertEquals(0, count(table), "Constraint failure must roll back " + table);
      }
    } finally {
      execute("DROP TRIGGER test_constraint_conflict ON application_vehicles");
      execute("DROP FUNCTION test_constraint_conflict()");
    }
  }

  @Test
  void completeApplicationRemainsQueryableAfterApplicationRestart() throws Exception {
    Response created = post("restart-key", rejected());
    assertEquals(201, created.status(), created.raw());
    String id = created.body().path("id").asText();
    execute("UPDATE applications SET rule_version = 'archived-policy-v0' WHERE request_key = 'restart-key'");
    ObjectNode savedSnapshot = created.body().deepCopy();
    savedSnapshot.put("ruleVersion", "archived-policy-v0");

    application.close();
    startApplication();

    Response fetched = get("/api/applications/" + id);
    assertEquals(200, fetched.status(), fetched.raw());
    assertEquals(savedSnapshot, fetched.body(), "The complete persisted snapshot must survive restart");
    assertError(post("restart-key", approved()), 409, "DUPLICATE_REQUEST");
    assertEquals(1, count("applications"));
  }

  @Test
  void executableJarRetainsRejectedApplicationAfterSeparateJvmRestart(@TempDir Path logs) throws Exception {
    String jarProperty = System.getProperty("application.jar");
    assertNotNull(jarProperty, "Gradle must provide application.jar and build bootJar before testing");
    Path jar = Path.of(jarProperty);
    assertTrue(Files.isRegularFile(jar), "Executable jar must exist: " + jar);
    JsonNode saved;
    String id;
    try (JarProcess first = startJar(jar, logs.resolve("first-start.log"))) {
      Response created = response(http.send(postRequest(first.baseUrl(), "jar-restart-key",
          rejected().toString()), HttpResponse.BodyHandlers.ofString()));
      assertEquals(201, created.status(), created.raw());
      assertEquals("REJECTED", created.body().path("status").asText());
      assertTrue(created.body().path("failureReasons").size() > 0);
      saved = created.body();
      id = saved.path("id").asText();
    }
    try (JarProcess restarted = startJar(jar, logs.resolve("second-start.log"))) {
      Response fetched = response(http.send(HttpRequest.newBuilder(URI.create(
          restarted.baseUrl() + "/api/applications/" + id)).timeout(Duration.ofSeconds(10))
          .GET().build(), HttpResponse.BodyHandlers.ofString()));
      assertEquals(200, fetched.status(), fetched.raw());
      assertEquals(saved, fetched.body(), "A new JVM must retrieve the persisted rejection and snapshot");
      Response duplicate = response(http.send(postRequest(restarted.baseUrl(), "jar-restart-key",
          approved().toString()), HttpResponse.BodyHandlers.ofString()));
      assertError(duplicate, 409, "DUPLICATE_REQUEST");
    }
    assertEquals(1, count("applications"));
  }

  private JarProcess startJar(Path jar, Path log) throws Exception {
    int port;
    try (ServerSocket reservation = new ServerSocket(0)) {
      port = reservation.getLocalPort();
    }
    Process process = new ProcessBuilder(Path.of(System.getProperty("java.home"), "bin", "java").toString(),
        "-jar", jar.toString(), "--server.address=127.0.0.1", "--server.port=" + port,
        "--spring.datasource.url=" + postgres.getJdbcUrl("postgres", "postgres"),
        "--spring.datasource.username=postgres", "--spring.datasource.password=",
        "--app.evaluation-date=2026-09-22", "--spring.main.banner-mode=off", "--logging.level.root=WARN")
        .redirectErrorStream(true).redirectOutput(log.toFile()).start();
    JarProcess running = new JarProcess(process, "http://127.0.0.1:" + port);
    try {
      long deadline = System.nanoTime() + Duration.ofSeconds(30).toNanos();
      while (System.nanoTime() < deadline) {
        if (!process.isAlive()) {
          fail("Executable jar exited during startup: " + Files.readString(log));
        }
        try {
          HttpResponse<String> probe = http.send(HttpRequest.newBuilder(
              URI.create(running.baseUrl() + "/api/applications")).timeout(Duration.ofSeconds(1))
              .GET().build(), HttpResponse.BodyHandlers.ofString());
          if (probe.statusCode() == 200) {
            return running;
          }
        } catch (java.io.IOException ignored) {
          // A refused connection is expected until the server binds its port.
        }
        Thread.sleep(100);
      }
      fail("Executable jar did not become ready within 30 seconds: " + Files.readString(log));
      throw new IllegalStateException("Unreachable");
    } catch (Exception | AssertionError failure) {
      running.close();
      throw failure;
    }
  }

  private record JarProcess(Process process, String baseUrl) implements AutoCloseable {
    @Override
    public void close() throws Exception {
      process.destroy();
      if (!process.waitFor(10, TimeUnit.SECONDS)) {
        process.destroyForcibly();
        assertTrue(process.waitFor(10, TimeUnit.SECONDS), "Executable jar process must terminate");
      }
    }
  }

  private ObjectNode approved() throws Exception {
    return (ObjectNode) json.readTree("""
        {
          "driver": {"firstName":"Morgan","lastName":"Example","birthDate":"1990-04-15"},
          "license": {
            "number":"SYNTHETIC-MA-001","firstName":"Morgan","lastName":"Example",
            "address":"1 Example Street, Boston, MA","birthDate":"1990-04-15",
            "countryAndState":"US, MA","issuanceDate":"2020-01-01","expirationDate":"2030-01-01"
          },
          "vehicles": [
            {"year":2021,"color":"Blue","make":"Toyota","model":"Camry",
             "ownerName":"Example, Morgan","plate":"DEMO-001"},
            {"year":2022,"color":"White","make":"Honda","model":"Civic",
             "ownerName":"Example, Morgan","plate":"DEMO-002"}
          ],
          "insurance": {
            "owner":{"firstName":"Morgan","lastName":"Example"},
            "expirationDate":"2030-01-01","drivers":["Example, Morgan","Example, Taylor"]
          },
          "driverHistory":[{"date":"2024-01-01","category":"MOVING","type":"DISTRACTED_DRIVING"}],
          "vehicleHistory":[{"plate":"DEMO-001","date":"2020-01-01","category":"CRASH",
            "type":"FENDER_BENDER","offender":"Example, Morgan"}]
        }
        """);
  }

  private ObjectNode rejected() throws Exception {
    ObjectNode request = approved();
    vehicle(request).put("year", 1990);
    return request;
  }

  private static ObjectNode object(ObjectNode node, String field) {
    return (ObjectNode) node.get(field);
  }

  private static ObjectNode vehicle(ObjectNode node) {
    return (ObjectNode) node.path("vehicles").get(0);
  }

  private Response post(String key, JsonNode body) throws Exception {
    return postRaw(key, body.toString());
  }

  private Response postRaw(String key, String body) throws Exception {
    return response(http.send(postRequest(key, body), HttpResponse.BodyHandlers.ofString()));
  }

  private HttpRequest postRequest(String key, String body) {
    return postRequest(baseUrl, key, body);
  }

  private HttpRequest postRequest(String targetBaseUrl, String key, String body) {
    HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create(targetBaseUrl + "/api/applications"))
        .timeout(Duration.ofSeconds(30)).header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(body));
    if (key != null) {
      builder.header("Idempotency-Key", key);
    }
    return builder.build();
  }

  private Response get(String path) throws Exception {
    return response(http.send(HttpRequest.newBuilder(URI.create(baseUrl + path))
        .timeout(Duration.ofSeconds(30)).GET().build(), HttpResponse.BodyHandlers.ofString()));
  }

  private Response response(HttpResponse<String> response) throws Exception {
    return new Response(response.statusCode(), json.readTree(response.body()), response.body());
  }

  private static void assertError(Response response, int status, String code) {
    assertEquals(status, response.status(), response.raw());
    assertEquals(code, response.body().path("code").asText(), response.raw());
    assertFalse(response.body().path("message").asText().isBlank());
    Instant.parse(response.body().path("timestamp").asText());
  }

  private long count(String table) throws Exception {
    return Long.parseLong(queryText("SELECT count(*) FROM " + table));
  }

  private String queryText(String sql) throws Exception {
    try (Connection connection = postgres.getPostgresDatabase().getConnection();
         Statement statement = connection.createStatement();
         ResultSet result = statement.executeQuery(sql)) {
      assertTrue(result.next());
      return result.getString(1);
    }
  }

  private void execute(String sql) throws Exception {
    try (Connection connection = postgres.getPostgresDatabase().getConnection();
         Statement statement = connection.createStatement()) {
      statement.execute(sql);
    }
  }

  private record Response(int status, JsonNode body, String raw) {}
}
