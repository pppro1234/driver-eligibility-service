package Registration.Api;

import Registration.Entity.Application;
import Registration.Entity.DriverHistory;
import Registration.Entity.Name;
import Registration.Entity.VehicleHistory;
import Registration.IncidentFactory.*;
import Registration.Validation.DateUtils;
import Registration.Validation.ValidationReport;
import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/** One immutable submission aggregate, deliberately independent of the CLI driver pool. */
@Entity
@Table(name = "applications")
public class StoredApplication {
    @Id
    private UUID id;
    @Column(name = "request_key", nullable = false, length = 100)
    private String requestKey;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private ApplicationStatus status;
    @Column(name = "submitted_at", nullable = false)
    private Instant submittedAt;
    @Column(name = "evaluated_at", nullable = false)
    private Instant evaluatedAt;
    @Column(name = "evaluation_date", nullable = false)
    private LocalDate evaluationDate;
    @Column(name = "rule_version", nullable = false, length = 40)
    private String ruleVersion;
    @Column(name = "driver_first_name", nullable = false, columnDefinition = "text")
    private String driverFirstName;
    @Column(name = "driver_last_name", nullable = false, columnDefinition = "text")
    private String driverLastName;
    @Column(name = "driver_birth_date", nullable = false)
    private LocalDate driverBirthDate;
    @Column(name = "license_number", nullable = false, columnDefinition = "text")
    private String licenseNumber;
    @Column(name = "license_first_name", nullable = false, columnDefinition = "text")
    private String licenseFirstName;
    @Column(name = "license_last_name", nullable = false, columnDefinition = "text")
    private String licenseLastName;
    @Column(name = "license_address", nullable = false, columnDefinition = "text")
    private String licenseAddress;
    @Column(name = "license_birth_date", nullable = false)
    private LocalDate licenseBirthDate;
    @Column(name = "license_jurisdiction", nullable = false, columnDefinition = "text")
    private String licenseJurisdiction;
    @Column(name = "license_issued", nullable = false)
    private LocalDate licenseIssued;
    @Column(name = "license_expires", nullable = false)
    private LocalDate licenseExpires;
    @Column(name = "policy_first_name", nullable = false, columnDefinition = "text")
    private String policyFirstName;
    @Column(name = "policy_last_name", nullable = false, columnDefinition = "text")
    private String policyLastName;
    @Column(name = "policy_expires", nullable = false)
    private LocalDate policyExpires;

    @ElementCollection
    @CollectionTable(name = "application_vehicles", joinColumns = @JoinColumn(name = "application_id"))
    @OrderColumn(name = "position")
    private List<StoredVehicle> vehicles = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "application_insured_drivers", joinColumns = @JoinColumn(name = "application_id"))
    @OrderColumn(name = "position")
    private List<StoredName> insuredDrivers = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "application_incidents", joinColumns = @JoinColumn(name = "application_id"))
    @OrderColumn(name = "position")
    private List<StoredIncident> incidents = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "application_failures", joinColumns = @JoinColumn(name = "application_id"))
    @OrderColumn(name = "position")
    @Column(name = "reason", nullable = false, columnDefinition = "text")
    private List<String> failures = new ArrayList<>();

    protected StoredApplication() { }

    static StoredApplication from(Application application, String key, ValidationReport report,
                                  Instant submitted, Instant evaluated, LocalDate date) {
        StoredApplication stored = new StoredApplication();
        stored.id = UUID.randomUUID();
        stored.requestKey = key;
        stored.status = report.isValid() ? ApplicationStatus.APPROVED : ApplicationStatus.REJECTED;
        stored.submittedAt = submitted;
        stored.evaluatedAt = evaluated;
        stored.evaluationDate = date;
        stored.ruleVersion = "eligibility-six-rules-v1";
        var driver = application.getDriver();
        var license = application.getDriverLicense();
        var insurance = application.getVehicleInsurance();
        stored.driverFirstName = driver.getName().getFirstName();
        stored.driverLastName = driver.getName().getLastName();
        stored.driverBirthDate = DateUtils.toLocalDate(driver.getBirthday());
        stored.licenseNumber = license.getLicenseNumber();
        stored.licenseFirstName = license.getName().getFirstName();
        stored.licenseLastName = license.getName().getLastName();
        stored.licenseAddress = license.getAddress();
        stored.licenseBirthDate = DateUtils.toLocalDate(license.getBirthDate());
        stored.licenseJurisdiction = license.getCountryAndState();
        stored.licenseIssued = DateUtils.toLocalDate(license.getIssuanceDate());
        stored.licenseExpires = DateUtils.toLocalDate(license.getExpiratonDate());
        stored.policyFirstName = insurance.getNameOnPolicy().getFirstName();
        stored.policyLastName = insurance.getNameOnPolicy().getLastName();
        stored.policyExpires = DateUtils.toLocalDate(insurance.getExpirationDate());
        application.getVehicle().forEach(vehicle -> stored.vehicles.add(new StoredVehicle(vehicle)));
        insurance.getDrivers().forEach(covered -> stored.insuredDrivers.add(new StoredName(covered.getName())));
        for (DriverHistory history : application.getDriverHistoryDetails()) {
            stored.incidents.add(new StoredIncident("DRIVER", null,
                    DateUtils.toLocalDate(history.getViolationDate()), history.getViolationIncident(), null));
        }
        for (int v = 0; v < application.getVehicleHistoryRecords().size(); v++) {
            VehicleHistory history = application.getVehicleHistoryRecords().get(v);
            String plate = application.getVehicle().get(v).getPlateNumber();
            for (int i = 0; i < history.getIncidents().size(); i++) {
                stored.incidents.add(new StoredIncident("VEHICLE", plate,
                        DateUtils.toLocalDate(history.getIncidentDates().get(i)), history.getIncidents().get(i),
                        history.getOffendingDrivers().get(i)));
            }
        }
        stored.failures.addAll(report.getReasons());
        return stored;
    }

    ApplicationResponse toResponse() {
        var driver = new ApplicationSnapshot.DriverData(driverFirstName, driverLastName, driverBirthDate);
        var license = new ApplicationSnapshot.LicenseData(licenseNumber, licenseFirstName, licenseLastName,
                licenseAddress, licenseBirthDate, licenseJurisdiction, licenseIssued, licenseExpires);
        var insurance = new ApplicationSnapshot.InsuranceData(
                new ApplicationSnapshot.NameData(policyFirstName, policyLastName), policyExpires,
                insuredDrivers.stream().map(StoredName::toView).toList());
        var snapshot = new ApplicationSnapshot(driver, license, vehicles.stream().map(StoredVehicle::toView).toList(),
                insurance, incidents.stream().filter(i -> i.scope.equals("DRIVER")).map(StoredIncident::toView).toList(),
                incidents.stream().filter(i -> i.scope.equals("VEHICLE")).map(StoredIncident::toView).toList());
        return new ApplicationResponse(id, status, List.copyOf(failures), submittedAt, evaluatedAt,
                evaluationDate, ruleVersion, snapshot);
    }

    @Embeddable
    public static class StoredVehicle {
        @Column(name = "model_year", nullable = false)
        private int year;
        @Column(nullable = false, columnDefinition = "text")
        private String color;
        @Column(nullable = false, columnDefinition = "text")
        private String make;
        @Column(nullable = false, columnDefinition = "text")
        private String model;
        @Column(name = "owner_name", nullable = false, columnDefinition = "text")
        private String ownerName;
        @Column(nullable = false, columnDefinition = "text")
        private String plate;
        protected StoredVehicle() { }
        StoredVehicle(Registration.Entity.Vehicle vehicle) {
            year = vehicle.getYear(); color = vehicle.getColor(); make = vehicle.getMake();
            model = vehicle.getModel(); ownerName = vehicle.getOwner(); plate = vehicle.getPlateNumber();
        }
        ApplicationSnapshot.VehicleData toView() {
            return new ApplicationSnapshot.VehicleData(year, color, make, model, ownerName, plate);
        }
    }

    @Embeddable
    public static class StoredName {
        @Column(name = "first_name", nullable = false, columnDefinition = "text")
        private String firstName;
        @Column(name = "last_name", nullable = false, columnDefinition = "text")
        private String lastName;
        protected StoredName() { }
        StoredName(Name name) { firstName = name.getFirstName(); lastName = name.getLastName(); }
        ApplicationSnapshot.NameData toView() { return new ApplicationSnapshot.NameData(firstName, lastName); }
    }

    @Embeddable
    public static class StoredIncident {
        @Column(nullable = false, length = 10)
        private String scope;
        @Column(columnDefinition = "text")
        private String plate;
        @Column(name = "incident_date", nullable = false)
        private LocalDate date;
        @Column(nullable = false, length = 30)
        private String category;
        @Column(name = "incident_type", nullable = false, length = 80)
        private String type;
        @Column(name = "offender_first_name", columnDefinition = "text")
        private String offenderFirstName;
        @Column(name = "offender_last_name", columnDefinition = "text")
        private String offenderLastName;
        protected StoredIncident() { }
        StoredIncident(String scope, String plate, LocalDate date, Incident incident, Name offender) {
            this.scope = scope; this.plate = plate; this.date = date;
            category = switch (incident.getIncidentCategory()) {
                case MOVING_VIOLATION -> "MOVING";
                case NONMOVING_VIOLATION -> "NONMOVING";
                case CRASH -> "CRASH";
            };
            type = switch (incident) {
                case MovingViolation moving -> moving.getViolationType().name();
                case NonMovingViolation nonMoving -> nonMoving.getViolationType().name();
                case Crash crash -> crash.getCrashType().name();
                default -> throw new IllegalArgumentException("Unsupported incident class");
            };
            if (offender != null) { offenderFirstName = offender.getFirstName(); offenderLastName = offender.getLastName(); }
        }
        ApplicationSnapshot.IncidentData toView() {
            return new ApplicationSnapshot.IncidentData(plate, date, category, type,
                    offenderFirstName == null ? null : new ApplicationSnapshot.NameData(offenderFirstName, offenderLastName));
        }
    }
}
