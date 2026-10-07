package Registration.IO;

import Registration.Entity.Application;
import Registration.Entity.Date;
import Registration.Entity.Driver;
import Registration.Entity.DriverHistory;
import Registration.Entity.DriverLicense;
import Registration.Entity.Name;
import Registration.Entity.Vehicle;
import Registration.Entity.VehicleHistory;
import Registration.Entity.VehicleInsurance;
import Registration.IncidentFactory.Crash;
import Registration.IncidentFactory.CrashEnum;
import Registration.IncidentFactory.CrashFactory;
import Registration.IncidentFactory.Incident;
import Registration.IncidentFactory.MovingViolation;
import Registration.IncidentFactory.MovingViolationEnum;
import Registration.IncidentFactory.MovingViolationFactory;
import Registration.IncidentFactory.NonMovingViolation;
import Registration.IncidentFactory.NonMovingViolationEnum;
import Registration.IncidentFactory.NonMovingViolationFactory;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Shared input contract for the JSON file importer and HTTP API.
 * Parsing verifies structure, never business eligibility. Legacy aliases are accepted only
 * when unambiguous; explicit nulls, coercions, unknown fields and discarded history are rejected.
 */
public class ProspectiveDriverFileParser {
    private final ObjectMapper mapper = new ObjectMapper(JsonFactory.builder()
            .enable(StreamReadFeature.STRICT_DUPLICATE_DETECTION).build())
            .enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);

    public List<Application> parse(String filePath) throws IOException, FileParseException {
        if (filePath == null) {
            throw invalid("file", "path is required");
        }
        return parse(Path.of(filePath));
    }

    public List<Application> parse(Path filePath) throws IOException, FileParseException {
        if (filePath == null || !Files.isRegularFile(filePath)) {
            throw invalid("file", "must refer to an existing regular file");
        }
        JsonNode root;
        try (Reader reader = Files.newBufferedReader(filePath)) {
            root = mapper.readTree(reader);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            throw new FileParseException("Invalid JSON in " + filePath, e);
        }
        if (root != null && root.isObject()) {
            fields(root, "$", "applications");
            root = root.get("applications");
        }
        if (root == null || !root.isArray()) {
            throw invalid("$", "must be an array, or an object containing an applications array");
        }
        List<Application> applications = new ArrayList<>();
        for (int i = 0; i < root.size(); i++) {
            applications.add(buildApplication(root.get(i), "applications[" + i + "]"));
        }
        return applications;
    }

    /** Parse one application with precisely the same field contract as file import. */
    public Application parseApplication(JsonNode application) throws FileParseException {
        return buildApplication(application, "application");
    }

    private Application buildApplication(JsonNode node, String path) throws FileParseException {
        fields(node, path, "driver", "license", "vehicles", "vehicle", "insurance",
                "driverHistory", "vehicleHistory");
        JsonNode driverNode = object(node, "driver", path);
        fields(driverNode, path + ".driver", "firstName", "lastName", "birthDate");
        Name driverName = nameFields(driverNode, path + ".driver");
        Date birthDate = date(driverNode, "birthDate", path + ".driver");

        JsonNode licenseNode = object(node, "license", path);
        String licensePath = path + ".license";
        fields(licenseNode, licensePath, "number", "firstName", "lastName", "address", "birthDate",
                "countryAndState", "issuanceDate", "expirationDate");
        DriverLicense license = new DriverLicense.Builder()
                .licenseNumber(text(licenseNode, "number", licensePath))
                .name(nameFields(licenseNode, licensePath))
                .address(text(licenseNode, "address", licensePath))
                .birthDate(date(licenseNode, "birthDate", licensePath))
                .countryAndState(text(licenseNode, "countryAndState", licensePath))
                .issuanceDate(date(licenseNode, "issuanceDate", licensePath))
                // The original builder calls its expiration-date setter experienceDate.
                .experienceDate(date(licenseNode, "expirationDate", licensePath))
                .build();
        Driver driver = new Driver.Builder(driverName, birthDate).driverLicense(license).build();

        JsonNode vehicleNode = alias(node, "vehicles", "vehicle", path);
        List<Vehicle> vehicles = new ArrayList<>();
        if (vehicleNode != null && vehicleNode.isArray()) {
            for (int i = 0; i < vehicleNode.size(); i++) {
                vehicles.add(vehicle(vehicleNode.get(i), path + ".vehicles[" + i + "]"));
            }
        } else {
            vehicles.add(vehicle(vehicleNode, path + ".vehicles"));
        }
        if (vehicles.isEmpty()) {
            throw invalid(path + ".vehicles", "must not be empty");
        }
        Set<String> plates = new HashSet<>();
        for (Vehicle vehicle : vehicles) {
            if (!plates.add(vehicle.getPlateNumber())) {
                throw invalid(path + ".vehicles", "plate values must be unique within one application");
            }
        }

        JsonNode insuranceNode = object(node, "insurance", path);
        String insurancePath = path + ".insurance";
        fields(insuranceNode, insurancePath, "owner", "ownerFirst", "ownerLast", "expirationDate", "drivers");
        Name owner;
        if (insuranceNode.has("owner")) {
            if (insuranceNode.has("ownerFirst") || insuranceNode.has("ownerLast")) {
                throw invalid(insurancePath, "use owner or ownerFirst/ownerLast, not both");
            }
            JsonNode ownerNode = object(insuranceNode, "owner", insurancePath);
            fields(ownerNode, insurancePath + ".owner", "firstName", "lastName");
            owner = nameFields(ownerNode, insurancePath + ".owner");
        } else {
            owner = new Name(text(insuranceNode, "ownerFirst", insurancePath),
                    text(insuranceNode, "ownerLast", insurancePath));
        }
        List<Driver> insuredDrivers = new ArrayList<>();
        JsonNode insuredNode = insuranceNode.get("drivers");
        if (insuredNode != null) {
            if (insuredNode.isArray()) {
                for (int i = 0; i < insuredNode.size(); i++) {
                    insuredDrivers.add(insuredDriver(insuredNode.get(i), insurancePath + ".drivers[" + i + "]"));
                }
            } else {
                // Preserve the original single-name shorthand alongside the canonical array.
                insuredDrivers.add(insuredDriver(insuredNode, insurancePath + ".drivers"));
            }
        }
        VehicleInsurance insurance = new VehicleInsurance.Builder().nameOnPolicy(owner)
                .drivers(insuredDrivers).expirationDate(date(insuranceNode, "expirationDate", insurancePath)).build();

        List<DriverHistory> driverDetails = new ArrayList<>();
        List<Incident> driverIncidents = new ArrayList<>();
        JsonNode driverHistory = optionalArray(node, "driverHistory", path);
        if (driverHistory != null) {
            for (int i = 0; i < driverHistory.size(); i++) {
                String recordPath = path + ".driverHistory[" + i + "]";
                JsonNode record = driverHistory.get(i);
                fields(record, recordPath, "date", "category", "type");
                Incident incident = incident(record, recordPath);
                driverDetails.add(new DriverHistory(date(record, "date", recordPath), incident));
                driverIncidents.add(incident);
            }
        }

        Map<String, List<Date>> plateDates = new HashMap<>();
        Map<String, List<Incident>> plateIncidents = new HashMap<>();
        Map<String, List<Name>> plateOffenders = new HashMap<>();
        JsonNode vehicleHistory = optionalArray(node, "vehicleHistory", path);
        if (vehicleHistory != null) {
            for (int i = 0; i < vehicleHistory.size(); i++) {
                String recordPath = path + ".vehicleHistory[" + i + "]";
                JsonNode record = vehicleHistory.get(i);
                fields(record, recordPath, "plate", "date", "category", "type", "offender", "offenderName");
                String plate = text(record, "plate", recordPath);
                if (!plates.contains(plate)) {
                    throw invalid(recordPath + ".plate", "must match a vehicle in this application");
                }
                Date incidentDate = date(record, "date", recordPath);
                Incident incident = incident(record, recordPath);
                Name offender = name(alias(record, "offender", "offenderName", recordPath), recordPath + ".offender");
                plateDates.computeIfAbsent(plate, ignored -> new ArrayList<>()).add(incidentDate);
                plateIncidents.computeIfAbsent(plate, ignored -> new ArrayList<>()).add(incident);
                plateOffenders.computeIfAbsent(plate, ignored -> new ArrayList<>()).add(offender);
            }
        }
        List<VehicleHistory> vehicleRecords = new ArrayList<>();
        List<Incident> vehicleIncidents = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            String plate = vehicle.getPlateNumber();
            List<Incident> incidents = plateIncidents.getOrDefault(plate, new ArrayList<>());
            vehicleRecords.add(new VehicleHistory(plateDates.getOrDefault(plate, new ArrayList<>()), incidents,
                    plateOffenders.getOrDefault(plate, new ArrayList<>())));
            vehicleIncidents.addAll(incidents);
        }
        return new Application.Builder().driver(driver).driverLicense(license).vehicle(vehicles)
                .vehicleInsurance(insurance).driverHistory(driverIncidents).vehicleHistory(vehicleIncidents)
                .driverHistoryDetails(driverDetails).vehicleHistoryRecords(vehicleRecords).build();
    }

    private static Vehicle vehicle(JsonNode node, String path) throws FileParseException {
        if (node != null && node.isTextual()) {
            // Deliberately retain the old YEAR,Color,Make,Model,OwnerName,Plate form.
            String[] parts = textual(node, path, false).split(",", -1);
            if (parts.length != 6) {
                throw invalid(path, "CSV vehicle must contain exactly six fields");
            }
            int year;
            try {
                year = Integer.parseInt(parts[0].trim());
            } catch (NumberFormatException e) {
                throw invalid(path + ".year", "must be a 32-bit integer");
            }
            return new Vehicle.Builder().year(year).color(parts[1].trim())
                    .make(nonblank(parts[2], path + ".make")).model(nonblank(parts[3], path + ".model"))
                    .owner(parts[4].trim()).plateNumber(nonblank(parts[5], path + ".plate")).build();
        }
        fields(node, path, "year", "color", "make", "model", "ownerName", "owner", "plate", "plateNumber");
        JsonNode year = node.get("year");
        if (year == null || !year.isIntegralNumber() || !year.canConvertToInt()) {
            throw invalid(path + ".year", "must be a 32-bit JSON integer");
        }
        JsonNode owner = alias(node, "ownerName", "owner", path);
        JsonNode plate = alias(node, "plate", "plateNumber", path);
        return new Vehicle.Builder().year(year.intValue()).color(optionalText(node, "color", path))
                .make(text(node, "make", path)).model(text(node, "model", path))
                .owner(owner == null ? "" : textual(owner, path + ".ownerName", true))
                .plateNumber(textual(plate, path + ".plate", false)).build();
    }

    private static Driver insuredDriver(JsonNode node, String path) throws FileParseException {
        // Only names are used for insurance coverage by the existing domain rule.
        return new Driver.Builder(name(node, path), new Date.Builder(1900, 1, 1).build()).build();
    }

    private static Name name(JsonNode node, String path) throws FileParseException {
        if (node != null && node.isObject()) {
            fields(node, path, "firstName", "lastName");
            return nameFields(node, path);
        }
        String raw = textual(node, path, false);
        if (raw.contains(",")) {
            String[] parts = raw.split(",", -1);
            if (parts.length == 2) {
                return new Name(nonblank(parts[1], path), nonblank(parts[0], path));
            }
        } else {
            String[] parts = raw.split("\\s+");
            if (parts.length == 2) {
                return new Name(parts[0], parts[1]);
            }
        }
        throw invalid(path, "use a firstName/lastName object or an unambiguous 'Last, First' / 'First Last' string");
    }

    private static Name nameFields(JsonNode node, String path) throws FileParseException {
        return new Name(text(node, "firstName", path), text(node, "lastName", path));
    }

    private static Incident incident(JsonNode node, String path) throws FileParseException {
        String category = text(node, "category", path).toUpperCase(Locale.ROOT);
        String type = text(node, "type", path).toUpperCase(Locale.ROOT);
        try {
            switch (category) {
                case "MOVING":
                case "MOVING_VIOLATION":
                    MovingViolation moving = (MovingViolation) new MovingViolationFactory().createViolation();
                    moving.setViolationType(MovingViolationEnum.valueOf(type));
                    return moving;
                case "NONMOVING":
                case "NONMOVING_VIOLATION":
                    NonMovingViolation nonmoving = (NonMovingViolation) new NonMovingViolationFactory().createViolation();
                    nonmoving.setViolationType(NonMovingViolationEnum.valueOf(type));
                    return nonmoving;
                case "CRASH":
                    Crash crash = (Crash) new CrashFactory().createViolation();
                    crash.setCrashType(CrashEnum.valueOf(type));
                    return crash;
                default:
                    throw invalid(path + ".category", "unknown incident category");
            }
        } catch (IllegalArgumentException e) {
            throw invalid(path + ".type", "unknown type for incident category " + category);
        }
    }

    private static Date date(JsonNode node, String field, String path) throws FileParseException {
        String value = text(node, field, path);
        if (!value.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")) {
            throw invalid(path + "." + field, "must be a date in YYYY-MM-DD format");
        }
        try {
            LocalDate date = LocalDate.parse(value);
            if (date.getYear() == 0) {
                throw invalid(path + "." + field, "year must be between 0001 and 9999");
            }
            return new Date.Builder(date.getYear(), date.getMonthValue(), date.getDayOfMonth()).build();
        } catch (DateTimeParseException e) {
            throw invalid(path + "." + field, "must be a valid calendar date");
        }
    }

    private static JsonNode alias(JsonNode node, String preferred, String legacy, String path) throws FileParseException {
        if (node.has(preferred) && node.has(legacy)) {
            throw invalid(path, "use " + preferred + " or " + legacy + ", not both");
        }
        return node.has(preferred) ? node.get(preferred) : node.get(legacy);
    }

    private static JsonNode object(JsonNode node, String field, String path) throws FileParseException {
        JsonNode value = node.get(field);
        if (value == null || !value.isObject()) {
            throw invalid(path + "." + field, "must be an object");
        }
        return value;
    }

    private static JsonNode optionalArray(JsonNode node, String field, String path) throws FileParseException {
        JsonNode value = node.get(field);
        if (value != null && !value.isArray()) {
            throw invalid(path + "." + field, "must be an array when present; omit it for no records");
        }
        return value;
    }

    private static String text(JsonNode node, String field, String path) throws FileParseException {
        return textual(node.get(field), path + "." + field, false);
    }

    private static String optionalText(JsonNode node, String field, String path) throws FileParseException {
        return node.has(field) ? textual(node.get(field), path + "." + field, true) : "";
    }

    private static String textual(JsonNode node, String path, boolean allowBlank) throws FileParseException {
        if (node == null || !node.isTextual()) {
            throw invalid(path, "must be a JSON string");
        }
        if (node.textValue().indexOf(0) >= 0) {
            throw invalid(path, "must not contain U+0000");
        }
        return allowBlank ? node.textValue().trim() : nonblank(node.textValue(), path);
    }

    private static String nonblank(String value, String path) throws FileParseException {
        if (value.trim().isEmpty()) {
            throw invalid(path, "must not be blank");
        }
        return value.trim();
    }

    private static void fields(JsonNode node, String path, String... allowed) throws FileParseException {
        if (node == null || !node.isObject()) {
            throw invalid(path, "must be an object");
        }
        Set<String> names = Set.of(allowed);
        Iterator<String> actual = node.fieldNames();
        while (actual.hasNext()) {
            String field = actual.next();
            if (!names.contains(field)) {
                throw invalid(path + "." + field, "unknown field");
            }
        }
    }

    private static FileParseException invalid(String path, String message) {
        return new FileParseException(path + ": " + message);
    }
}
