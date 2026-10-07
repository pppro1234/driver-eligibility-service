# Testing and verification

The test suite exercises domain rules, strict JSON parsing, the CLI, HTTP endpoints, JPA persistence, and transaction failures. Database integration tests use a real temporary PostgreSQL process, with no H2 replacement or mocked repository.

## Run the checks

From the repository root, with JDK 21 selected:

```sh
./gradlew clean build
./gradlew javadoc
```

The build creates the executable JAR and runs the full test suite, including PostgreSQL and separate-JVM restart checks. Javadoc generation checks the API comments separately. On macOS, select the JDK with `export JAVA_HOME=$(/usr/libexec/java_home -v 21)` if necessary.

Tests start PostgreSQL 14.22 on a temporary port and delete the test cluster afterward. They do not require a pre-existing database or Docker. PostgreSQL must run as a non-root user; a startup failure fails the tests rather than skipping them. The tests execute `SELECT version()` to confirm the database engine.

For the CLI, a fixed evaluation date makes the example outcomes reproducible:

```sh
printf 'exit\n' | ./gradlew run --args='examples/cli-demo.json --date 2026-09-22'
printf 'Smith\nexit\n' | ./gradlew run --args='src/main/resources/all-cases-drivers.json --date 2026-09-22'
```

The first command imports two applications, approves one, and rejects one. The second exercises file import and querying by last name.

## Recorded results

On **2026-10-05** (America/Los_Angeles), `./gradlew --no-daemon clean build javadoc` completed successfully from the repository root on macOS ARM64 with Oracle JDK 21.0.8 and Gradle 8.14. All **1,045 tests passed**, with **0 failures, 0 errors, and 0 skipped tests** across 26 test classes. These counts include entity and enum tests as well as integration tests. Javadoc generation succeeded with missing-comment and missing-tag warnings. The executable `driver-eligibility-service-2.0.0.jar` passed the separate-JVM restart test. Both CLI commands above completed successfully, including the last-name query.

| Test class | Recorded invocations | Scope |
|---|---:|---|
| `ApplicationPostgresIntegrationTest` | 45 | Actual HTTP requests and PostgreSQL persistence |
| `ProspectiveDriverFileParserTest` | 63 | Shared API/file input contract |
| `ValidatorTest` | 53 | Six-rule eligibility and fixed-clock boundaries |
| `RideshareDriverValidatorTest` | 5 | CLI import, validation, argument handling, and exit |

## Integration coverage

| Scenario | Assertions |
|---|---|
| Eligible application | POST returns 201/APPROVED; GET returns the persisted snapshot; expected child rows exist |
| Business rejection | POST returns 201/REJECTED with persisted reasons; GET returns the same result |
| Malformed requests | Invalid fields, dates, types, histories, JSON, or request keys return 400 without saving |
| Missing application | A valid but unknown UUID returns 404 |
| Repeated request | Reused keys return 409 for identical or different payloads; a new key permits a new application |
| Concurrent duplicate keys | Six concurrent HTTP requests produce one creation and five conflicts, with one stored aggregate |
| Pagination and filtering | Page content, counts, empty pages, status filters, and invalid pagination parameters |
| Write failure | PostgreSQL triggers fail child-table writes; the entire aggregate rolls back, and retry succeeds after the fault is removed |
| Integrity conflict | A SQLSTATE 23514 constraint failure returns 409 without exposing SQL and leaves no partial records |
| Context restart | A new Spring context reads the saved result and its stored rule version from the same database |
| Executable restart | Two separate `java -jar` processes share a database; the second reads the first process's rejected application and still rejects its request key |
| Repeat startup | Flyway recognizes applied migrations and Hibernate validates the schema without clearing data |

The executable restart test also verifies the packaged main class. `bootJar` and `bootRun` must launch `Registration.Api.RegistrationServiceApplication`; `run` launches `Registration.RideshareDriverValidator`.

## Reports and quality checks

- Test report: `build/reports/tests/test/index.html`
- JUnit XML: `build/test-results/test/`
- JaCoCo report: `build/jacocoHtml/index.html`
- PMD report: `build/reports/pmd/main.html`
- API reference: `build/docs/javadoc/index.html`

The build enforces a JaCoCo minimum coverage ratio of 0.20. This threshold is not a claim about the measured coverage. PMD is advisory (`ignoreFailures=true`); its findings must be read independently of build success. This run reported **657 PMD findings**. Gradle also reported deprecated API usage; Gradle 9 compatibility has not been verified.

The workflow at `.github/workflows/java-backend.yml` configures Java 21 builds on Linux and report uploads. Its YAML structure and root-relative build/report paths were checked locally. A workflow file alone is not proof of a remote passing run; no remote CI result is included in this verification record.

## Limits of the evidence

The tests demonstrate application-process restart persistence, not recovery from power loss, disk failure, or database-server crashes. They do not measure throughput, latency, production availability, or real-user scale. No production deployment or real identity verification is covered.

The service uses synthetic data and binds to loopback by default. Long-running local use requires a separately configured persistent PostgreSQL database. Authentication, authorization, operational backups, and privacy retention workflows are outside the implemented scope.
