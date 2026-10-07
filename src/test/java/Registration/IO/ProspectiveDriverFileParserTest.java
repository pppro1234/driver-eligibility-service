package Registration.IO;

import Registration.Entity.Application;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ProspectiveDriverFileParserTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final ProspectiveDriverFileParser parser = new ProspectiveDriverFileParser();
    @TempDir Path directory;

    private static final String VALID = """
            {
              "driver":{"firstName":"Synthetic","lastName":"Tester","birthDate":"1990-02-28"},
              "license":{"number":"SYN-LIC-1","firstName":"Synthetic","lastName":"Tester",
                "address":"1 Example St","birthDate":"1990-02-28","countryAndState":"US, MA",
                "issuanceDate":"2020-01-01","expirationDate":"2035-01-01"},
              "vehicles":[{"year":2020,"color":"Blue","make":"Honda","model":"Civic",
                "ownerName":"Tester, Synthetic","plate":"SYN-1"}],
              "insurance":{"owner":{"firstName":"Synthetic","lastName":"Tester"},
                "expirationDate":"2035-01-01","drivers":["Tester, Synthetic"]},
              "driverHistory":[],"vehicleHistory":[]
            }
            """;

    @Test
    void preservesOriginalAllCasesImportAndDetailedHistories() throws Exception {
        var applications = parser.parse(Path.of("src/main/resources/all-cases-drivers.json"));
        assertEquals(18, applications.size());
        assertEquals(2, applications.getFirst().getVehicle().size());
        assertEquals(2, applications.getFirst().getDriverHistoryDetails().size());
        assertEquals(1, applications.getFirst().getVehicleHistory().size());
        assertEquals(2, applications.getFirst().getVehicleHistoryRecords().size());
    }

    @Test
    void singleApplicationAndFileWrapperProduceSameDomainData() throws Exception {
        JsonNode node = MAPPER.readTree(VALID);
        Application direct = parser.parseApplication(node);
        Path file = directory.resolve("wrapped.json");
        Files.writeString(file, "{\"applications\":[" + node + "]}");
        Application imported = parser.parse(file).getFirst();
        assertEquals(direct.getDriver().getName(), imported.getDriver().getName());
        assertEquals(direct.getDriver().getBirthday(), imported.getDriver().getBirthday());
        assertEquals(direct.getDriverLicense().getLicenseNumber(), imported.getDriverLicense().getLicenseNumber());
        assertEquals(direct.getVehicle().getFirst().toString(), imported.getVehicle().getFirst().toString());
        assertEquals(direct.getVehicleInsurance().getNameOnPolicy(), imported.getVehicleInsurance().getNameOnPolicy());
    }

    @Test
    void absentHistoriesMeanEmptyAndOptionalVehicleTextDefaultsToEmpty() throws Exception {
        ObjectNode node = valid();
        node.remove("driverHistory");
        node.remove("vehicleHistory");
        ((ObjectNode) node.at("/vehicles/0")).remove("color");
        ((ObjectNode) node.at("/vehicles/0")).remove("ownerName");
        ((ObjectNode) node.get("insurance")).remove("drivers");
        Application result = parser.parseApplication(node);
        assertTrue(result.getDriverHistoryDetails().isEmpty());
        assertTrue(result.getVehicleHistory().isEmpty());
        assertEquals(1, result.getVehicleHistoryRecords().size());
        assertEquals("", result.getVehicle().getFirst().getOwner());
        assertEquals("", result.getVehicle().getFirst().getColor());
        assertTrue(result.getVehicleInsurance().getDrivers().isEmpty());
        assertEquals(1, parseFile(node).size());
    }

    static Stream<Arguments> invalidFields() {
        return Stream.of(
                Arguments.of("/driver", null),
                Arguments.of("/driver/firstName", null),
                Arguments.of("/driver/firstName", "null"),
                Arguments.of("/driver/firstName", "123"),
                Arguments.of("/driver/firstName", "\"Syn\\u0000thetic\""),
                Arguments.of("/vehicles/0/color", "\"Bl\\u0000ue\""),
                Arguments.of("/vehicles", "\"2020,Bl\\u0000ue,Honda,Civic,Synthetic Tester,SYN-1\""),
                Arguments.of("/driver/lastName", "true"),
                Arguments.of("/license/number", "12345"),
                Arguments.of("/license/address", "[]"),
                Arguments.of("/license/expirationDate", "\"2025-02-29\""),
                Arguments.of("/driver/birthDate", "\"2020-02-30\""),
                Arguments.of("/driver/birthDate", "\"0000-01-01\""),
                Arguments.of("/driver/birthDate", "\"1990-2-28\""),
                Arguments.of("/driver/birthDate", "\"1990-02-28T00:00:00Z\""),
                Arguments.of("/driver/firstName", "\"  \""),
                Arguments.of("/vehicles", "null"),
                Arguments.of("/vehicles", "[]"),
                Arguments.of("/vehicles", "true"),
                Arguments.of("/vehicles", "[null]"),
                Arguments.of("/vehicles/0/year", "2020.5"),
                Arguments.of("/vehicles/0/year", "2020.0"),
                Arguments.of("/vehicles/0/year", "1e3"),
                Arguments.of("/vehicles/0/year", "2147483648"),
                Arguments.of("/vehicles/0/year", "-2147483649"),
                Arguments.of("/vehicles/0/year", "\"2020\""),
                Arguments.of("/vehicles/0/color", "17"),
                Arguments.of("/vehicles/0/ownerName", "null"),
                Arguments.of("/vehicles/0/plate", "\" \""),
                Arguments.of("/insurance/owner", "\"Tester, Synthetic\""),
                Arguments.of("/insurance/drivers", "null"),
                Arguments.of("/insurance/drivers", "[false]"),
                Arguments.of("/insurance/drivers", "[{}]"),
                Arguments.of("/insurance/drivers", "[\"First Middle Last\"]"),
                Arguments.of("/insurance/drivers", "[\"Last, First, Ignored\"]"),
                Arguments.of("/driverHistory", "{}"),
                Arguments.of("/driverHistory", "null"),
                Arguments.of("/driverHistory", "\"clean\""),
                Arguments.of("/driverHistory", "[null]"),
                Arguments.of("/driverHistory", "[{\"date\":\"2025-01-01\",\"category\":\"MOVING\"}]"),
                Arguments.of("/driverHistory", "[{\"date\":\"2025-01-01\",\"category\":\"UNKNOWN\",\"type\":\"DUI\"}]"),
                Arguments.of("/driverHistory", "[{\"date\":\"2025-01-01\",\"category\":\"MOVING\",\"type\":\"UNKNOWN\"}]"),
                Arguments.of("/vehicleHistory", "{}"),
                Arguments.of("/vehicleHistory", "null"),
                Arguments.of("/vehicleHistory", "[1]"),
                Arguments.of("/vehicleHistory", "[{\"plate\":\"UNKNOWN\",\"date\":\"2025-01-01\",\"category\":\"CRASH\",\"type\":\"FENDER_BENDER\",\"offender\":\"Tester, Synthetic\"}]"),
                Arguments.of("/vehicleHistory", "[{\"plate\":\"SYN-1\",\"date\":\"2025-01-01\",\"category\":\"CRASH\",\"type\":\"FENDER_BENDER\",\"offender\":null}]"),
                Arguments.of("/unexpected", "true"),
                Arguments.of("/driver/unexpected", "true"),
                Arguments.of("/license/unexpected", "true"),
                Arguments.of("/vehicles/0/unexpected", "true"),
                Arguments.of("/insurance/unexpected", "true"),
                Arguments.of("/insurance/owner/unexpected", "true"),
                Arguments.of("/driverHistory", "[{\"date\":\"2025-01-01\",\"category\":\"MOVING\",\"type\":\"DUI\",\"ignored\":true}]")
        );
    }

    @ParameterizedTest(name = "strict at both entry points: {0} = {1}")
    @MethodSource("invalidFields")
    void rejectsMalformedFieldsInSingleApplicationAndFile(String pointer, String replacement) throws Exception {
        ObjectNode node = valid();
        int slash = pointer.lastIndexOf('/');
        ObjectNode parent = (ObjectNode) node.at(pointer.substring(0, slash));
        String field = pointer.substring(slash + 1);
        if (replacement == null) {
            parent.remove(field);
        } else {
            parent.set(field, MAPPER.readTree(replacement));
        }
        assertThrows(FileParseException.class, () -> parser.parseApplication(node));
        assertThrows(FileParseException.class, () -> parseFile(node));
    }

    @Test
    void rejectsDuplicatePlatesInsteadOfDuplicatingOrDiscardingHistory() throws Exception {
        ObjectNode node = valid();
        ArrayNode vehicles = (ArrayNode) node.get("vehicles");
        vehicles.add(vehicles.get(0).deepCopy());
        assertThrows(FileParseException.class, () -> parser.parseApplication(node));
        assertThrows(FileParseException.class, () -> parseFile(node));
    }

    @Test
    void acceptsLegacyAliasesWithoutLosingHistory() throws Exception {
        ObjectNode node = valid();
        ObjectNode vehicle = (ObjectNode) node.at("/vehicles/0");
        vehicle.set("plateNumber", vehicle.remove("plate"));
        vehicle.set("owner", vehicle.remove("ownerName"));
        node.set("vehicle", node.remove("vehicles").get(0));
        ObjectNode insurance = (ObjectNode) node.get("insurance");
        insurance.remove("owner");
        insurance.put("ownerFirst", "Synthetic");
        insurance.put("ownerLast", "Tester");
        insurance.put("drivers", "Synthetic Tester");
        node.set("vehicleHistory", MAPPER.readTree("""
                [{"plate":"SYN-1","date":"2020-01-01","category":"crash",
                  "type":"fender_bender","offenderName":{"firstName":"Synthetic","lastName":"Tester"}}]
                """));
        Application result = parser.parseApplication(node);
        assertEquals("SYN-1", result.getVehicle().getFirst().getPlateNumber());
        assertEquals(1, result.getVehicleHistory().size());
        assertEquals(1, parseFile(node).getFirst().getVehicleHistory().size());
    }

    @Test
    void acceptsExplicitLegacyCsvButRejectsBlankRequiredFields() throws Exception {
        ObjectNode node = valid();
        node.put("vehicles", "2020,Blue,Honda,Civic,Synthetic Tester,SYN-1");
        assertEquals("SYN-1", parser.parseApplication(node).getVehicle().getFirst().getPlateNumber());
        assertEquals(1, parseFile(node).size());
        node.put("vehicles", "2020,Blue,,Civic,Synthetic Tester,SYN-1");
        assertThrows(FileParseException.class, () -> parser.parseApplication(node));
        node.put("vehicles", "2020,Blue,Honda,Civic,Synthetic Tester,");
        assertThrows(FileParseException.class, () -> parser.parseApplication(node));
    }

    @Test
    void rejectsAmbiguousAliasesEvenWhenValuesAgree() throws Exception {
        ObjectNode node = valid();
        node.set("vehicle", node.get("vehicles"));
        ObjectNode ambiguousVehicles = node;
        assertThrows(FileParseException.class, () -> parser.parseApplication(ambiguousVehicles));
        node = valid();
        ObjectNode vehicle = (ObjectNode) node.at("/vehicles/0");
        vehicle.put("plateNumber", "SYN-1");
        ObjectNode ambiguousPlate = node;
        assertThrows(FileParseException.class, () -> parser.parseApplication(ambiguousPlate));
        node = valid();
        ((ObjectNode) node.get("insurance")).put("ownerFirst", "Synthetic");
        ObjectNode ambiguousOwner = node;
        assertThrows(FileParseException.class, () -> parser.parseApplication(ambiguousOwner));
    }

    @Test
    void fileSyntaxRejectsDuplicateKeysTrailingTokensAndNonstandardJson() throws Exception {
        String[] invalid = {
                "[" + VALID.replace("\"firstName\":\"Synthetic\"", "\"firstName\":\"Hidden\",\"firstName\":\"Synthetic\"") + "]",
                "[" + VALID + "] []", "[" + VALID + ",]", "/* comment */[" + VALID + "]",
                "[" + VALID.replace("\"firstName\"", "'firstName'") + "]", "{broken}", ""
        };
        for (String json : invalid) {
            Path path = directory.resolve("invalid.json");
            Files.writeString(path, json);
            assertThrows(FileParseException.class, () -> parser.parse(path), json);
        }
    }

    @Test
    void rejectsInvalidRootsAndUnknownWrapperFields() throws Exception {
        assertThrows(FileParseException.class, () -> parser.parseApplication(MAPPER.readTree("[]")));
        assertThrows(FileParseException.class, () -> parser.parseApplication(null));
        for (String json : new String[]{"null", "{}", "{\"applications\":[],\"typo\":true}"}) {
            Path path = directory.resolve("root.json");
            Files.writeString(path, json);
            assertThrows(FileParseException.class, () -> parser.parse(path));
        }
        assertThrows(FileParseException.class, () -> parser.parse((Path) null));
        assertThrows(FileParseException.class, () -> parser.parse((String) null));
        assertThrows(FileParseException.class, () -> parser.parse(directory.resolve("missing.json")));
    }

    private static ObjectNode valid() throws Exception {
        return (ObjectNode) MAPPER.readTree(VALID);
    }

    private java.util.List<Application> parseFile(JsonNode node) throws Exception {
        Path file = directory.resolve("applications.json");
        Files.writeString(file, "[" + node + "]");
        return parser.parse(file);
    }
}
