package Validation;

import Registration.Entity.Application;
import Registration.Entity.Date;
import Registration.Entity.Driver;
import Registration.Entity.DriverLicense;
import Registration.Entity.Name;
import Registration.Entity.Vehicle;
import Registration.Entity.VehicleHistory;
import Registration.Entity.VehicleInsurance;
import Registration.IncidentFactory.*;
import Registration.Validation.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the six eligibility strategies and their date boundaries.
 */
class ValidatorTest {
    private static final LocalDate TODAY = LocalDate.of(2026, 9, 22);
    private static final Clock CLOCK = Clock.fixed(TODAY.atStartOfDay(ZoneOffset.UTC).toInstant(), ZoneOffset.UTC);
    private final Validator validator = Validator.defaultValidator(CLOCK);

    private static Date date(LocalDate value) {
        return new Date.Builder(value.getYear(), value.getMonthValue(), value.getDayOfMonth()).build();
    }

    private Application validApplication() {
        Name name = new Name("John", "Doe");
        Date birthday = date(TODAY.minusYears(30));
        Driver driver = new Driver.Builder(name, birthday).build();
        return new Application.Builder()
                .driver(driver)
                .driverLicense(new DriverLicense.Builder().licenseNumber("SYNTHETIC-001")
                        .name(name).address("123 Example St").birthDate(birthday).countryAndState("US, MA")
                        .issuanceDate(date(TODAY.minusYears(2))).experienceDate(date(TODAY.plusYears(2))).build())
                .vehicle(new ArrayList<>(List.of(new Vehicle("Honda", "Accord", 2023, "John Doe", "DEMO-001"))))
                .vehicleInsurance(new VehicleInsurance.Builder().nameOnPolicy(name).drivers(new ArrayList<>())
                        .expirationDate(date(TODAY.plusYears(1))).build())
                .driverHistory(new ArrayList<>()).vehicleHistory(new ArrayList<>())
                .vehicleHistoryRecords(new ArrayList<>()).build();
    }

    private void incident(Application app, LocalDate when, Incident incident) {
        app.setVehicleHistoryRecords(List.of(new VehicleHistory(List.of(date(when)), List.of(incident),
                List.of(app.getDriver().getName()))));
    }

    @Test void allSixRulesPassForValidApplication() {
        ValidationReport report = validator.validate(validApplication());
        assertTrue(report.isValid());
        assertTrue(report.getReasons().isEmpty());
    }

    @Test void aggregatesFailuresFromEveryRuleWithoutShortCircuiting() {
        Application app = validApplication();
        app.getDriver().setBirthday(date(TODAY.minusYears(18)));
        app.getDriverLicense().setBirthDate(app.getDriver().getBirthday());
        app.getDriverLicense().setCountryAndState("UK");
        app.getVehicle().get(0).setYear(2000);
        app.getVehicleInsurance().setExpirationDate(date(TODAY.minusDays(1)));
        app.setDriverHistory(List.of(new MovingViolation(MovingViolationEnum.DUI)));
        incident(app, TODAY, new Crash(CrashEnum.FENDER_BENDER));
        ValidationReport report = validator.validate(app);
        assertFalse(report.isValid());
        assertEquals(6, report.getReasons().size());
        assertTrue(report.getReasons().get(0).contains("21 years"));
        assertTrue(report.getReasons().get(5).contains("recent disqualifying"));
    }

    @Test void reportReasonsAreDefensiveAndReadOnly() {
        List<String> reasons = new ArrayList<>(List.of("Rejected"));
        ValidationReport report = new ValidationReport(false, reasons);
        reasons.clear();
        assertEquals(List.of("Rejected"), report.getReasons());
        assertThrows(UnsupportedOperationException.class, () -> report.getReasons().clear());
    }

    @Test void customStrategiesStillSupported() {
        Validator custom = new Validator(List.of(app -> ValidationResult.fail("custom failure")));
        assertEquals(List.of("custom failure"), custom.validate(validApplication()).getReasons());
    }

    @Test void ageExactly21Passes() {
        Application app = validApplication();
        app.getDriver().setBirthday(date(TODAY.minusYears(21)));
        assertTrue(new AgeValidationStrategy(CLOCK).validate(app).isValid());
    }

    @Test void ageOneDayUnder21Fails() {
        Application app = validApplication();
        app.getDriver().setBirthday(date(TODAY.minusYears(21).plusDays(1)));
        assertFalse(new AgeValidationStrategy(CLOCK).validate(app).isValid());
    }

    @Test void invalidBirthdateFails() {
        Application app = validApplication();
        app.getDriver().setBirthday(new Date.Builder(1990, 2, 30).build());
        assertEquals("Invalid driver birthdate", new AgeValidationStrategy(CLOCK).validate(app).getMessage());
    }

    @Test void missingDriverFails() {
        Application app = validApplication();
        app.setDriver(null);
        assertFalse(new AgeValidationStrategy(CLOCK).validate(app).isValid());
        assertFalse(new LicenseValidationStrategy(CLOCK).validate(app).isValid());
        assertFalse(new InsuranceValidationStrategy(CLOCK).validate(app).isValid());
    }

    @ParameterizedTest
    @ValueSource(strings = {"US", "USA", "US, MA", "United States, MA", "Canada, ON"})
    void supportedLicenseJurisdictionsPass(String jurisdiction) {
        Application app = validApplication();
        app.getDriverLicense().setCountryAndState(jurisdiction);
        assertTrue(new LicenseValidationStrategy(CLOCK).validate(app).isValid());
    }

    @Test void foreignLicenseFails() {
        Application app = validApplication();
        app.getDriverLicense().setCountryAndState("UK");
        assertFalse(new LicenseValidationStrategy(CLOCK).validate(app).isValid());
    }

    @Test void licenseNameMustMatchExactly() {
        Application app = validApplication();
        app.getDriverLicense().setName(new Name("john", "Doe"));
        assertFalse(new LicenseValidationStrategy(CLOCK).validate(app).isValid());
    }

    @Test void licenseBirthdayMustMatchExactly() {
        Application app = validApplication();
        app.getDriverLicense().setBirthDate(date(TODAY.minusYears(31)));
        assertFalse(new LicenseValidationStrategy(CLOCK).validate(app).isValid());
    }

    @Test void licenseIssuedExactlySixMonthsAgoPasses() {
        Application app = validApplication();
        app.getDriverLicense().setIssuanceDate(date(TODAY.minusMonths(6)));
        assertTrue(new LicenseValidationStrategy(CLOCK).validate(app).isValid());
    }

    @Test void licenseIssuedOneDayTooRecentlyFails() {
        Application app = validApplication();
        app.getDriverLicense().setIssuanceDate(date(TODAY.minusMonths(6).plusDays(1)));
        assertFalse(new LicenseValidationStrategy(CLOCK).validate(app).isValid());
    }

    @Test void licenseExpiringTodayPasses() {
        Application app = validApplication();
        app.getDriverLicense().setExpiratonDate(date(TODAY));
        assertTrue(new LicenseValidationStrategy(CLOCK).validate(app).isValid());
    }

    @Test void expiredLicenseFails() {
        Application app = validApplication();
        app.getDriverLicense().setExpiratonDate(date(TODAY.minusDays(1)));
        assertFalse(new LicenseValidationStrategy(CLOCK).validate(app).isValid());
    }

    @Test void missingOrInvalidLicenseDatesFail() {
        Application app = validApplication();
        app.getDriverLicense().setIssuanceDate(null);
        assertFalse(new LicenseValidationStrategy(CLOCK).validate(app).isValid());
        app.getDriverLicense().setIssuanceDate(date(TODAY.minusYears(2)));
        app.getDriverLicense().setExpiratonDate(new Date.Builder(2027, 2, 30).build());
        assertFalse(new LicenseValidationStrategy(CLOCK).validate(app).isValid());
        app.setDriverLicense(null);
        assertFalse(new LicenseValidationStrategy(CLOCK).validate(app).isValid());
    }

    @Test void policyOwnerIsCovered() {
        assertTrue(new InsuranceValidationStrategy(CLOCK).validate(validApplication()).isValid());
    }

    @Test void listedDriverIsCoveredAmongOtherDrivers() {
        Application app = validApplication();
        app.getVehicleInsurance().setNameOnPolicy(new Name("Other", "Owner"));
        app.getVehicleInsurance().setDrivers(Arrays.asList(null,
                new Driver.Builder(new Name("Another", "Driver"), date(TODAY.minusYears(40))).build(),
                app.getDriver()));
        assertTrue(new InsuranceValidationStrategy(CLOCK).validate(app).isValid());
    }

    @Test void driverNotOnPolicyFails() {
        Application app = validApplication();
        app.getVehicleInsurance().setNameOnPolicy(new Name("Other", "Owner"));
        assertFalse(new InsuranceValidationStrategy(CLOCK).validate(app).isValid());
    }

    @Test void insuranceExpiringTodayPassesAndYesterdayFails() {
        Application app = validApplication();
        app.getVehicleInsurance().setExpirationDate(date(TODAY));
        assertTrue(new InsuranceValidationStrategy(CLOCK).validate(app).isValid());
        app.getVehicleInsurance().setExpirationDate(date(TODAY.minusDays(1)));
        assertFalse(new InsuranceValidationStrategy(CLOCK).validate(app).isValid());
    }

    @Test void missingInsuranceOrDateFails() {
        Application app = validApplication();
        app.getVehicleInsurance().setExpirationDate(null);
        assertFalse(new InsuranceValidationStrategy(CLOCK).validate(app).isValid());
        app.setVehicleInsurance(null);
        assertFalse(new InsuranceValidationStrategy(CLOCK).validate(app).isValid());
    }

    @Test void vehicleAt15YearBoundaryPassesAnd16Fails() {
        Application app = validApplication();
        app.getVehicle().get(0).setYear(TODAY.getYear() - 15);
        assertTrue(new VehicleValidationStrategy(CLOCK).validate(app).isValid());
        app.getVehicle().get(0).setYear(TODAY.getYear() - 16);
        assertFalse(new VehicleValidationStrategy(CLOCK).validate(app).isValid());
    }

    @Test void extremeAncientVehicleYearCannotOverflowIntoAcceptance() {
        Application app = validApplication();
        app.getVehicle().get(0).setYear(Integer.MIN_VALUE);
        assertFalse(new VehicleValidationStrategy(CLOCK).validate(app).isValid());
        assertTrue(validator.validate(app).getReasons().contains("Vehicle must not be older than 15 years"));
    }

    @Test void allVehiclesAreChecked() {
        Application app = validApplication();
        app.getVehicle().add(new Vehicle("Old", "Car", 2000, "John Doe", "OLD-001"));
        assertFalse(new VehicleValidationStrategy(CLOCK).validate(app).isValid());
    }

    @Test void missingVehiclesFail() {
        Application app = validApplication();
        app.setVehicle(List.of());
        assertFalse(new VehicleValidationStrategy(CLOCK).validate(app).isValid());
        app.setVehicle(null);
        assertFalse(new VehicleValidationStrategy(CLOCK).validate(app).isValid());
        app.setVehicle(Arrays.asList((Vehicle) null));
        assertFalse(new VehicleValidationStrategy(CLOCK).validate(app).isValid());
    }

    @ParameterizedTest
    @EnumSource(value = MovingViolationEnum.class, names = {"RECKLESS_DRIVING", "SPEEDING", "DUI", "DRIVING_WITHOUT_VALID_LICENSE_INSURANCE"})
    void originalDisqualifyingDriverViolationsFail(MovingViolationEnum type) {
        Application app = validApplication();
        app.setDriverHistory(List.of(new MovingViolation(MovingViolationEnum.DISTRACTED_DRIVING), new MovingViolation(type)));
        assertFalse(new DrivingHistoryValidationStrategy().validate(app).isValid());
        assertFalse(validator.validate(app).isValid());
    }

    @ParameterizedTest
    @EnumSource(value = MovingViolationEnum.class, names = {"DISTRACTED_DRIVING", "FAILURE_TO_OBEY_SIGNS"})
    void originalAllowedMovingDriverViolationsRemainAllowed(MovingViolationEnum type) {
        Application app = validApplication();
        app.setDriverHistory(List.of(new MovingViolation(type)));
        assertTrue(new DrivingHistoryValidationStrategy().validate(app).isValid());
    }

    @Test void driverCrashesAndNonMovingViolationsRemainAllowed() {
        Application app = validApplication();
        app.setDriverHistory(List.of(new Crash(CrashEnum.FENDER_BENDER),
                new NonMovingViolation(NonMovingViolationEnum.PARKING_VIOLATION)));
        assertTrue(new DrivingHistoryValidationStrategy().validate(app).isValid());
    }

    @Test void absentDriverHistoryMeansNoIncidents() {
        Application app = validApplication();
        app.setDriverHistory(null);
        assertTrue(new DrivingHistoryValidationStrategy().validate(app).isValid());
    }

    @ParameterizedTest
    @EnumSource(CrashEnum.class)
    void allRecentVehicleCrashesFail(CrashEnum type) {
        Application app = validApplication();
        incident(app, TODAY.minusDays(1), new Crash(type));
        assertFalse(new VehicleHistoryValidationStrategy(CLOCK).validate(app).isValid());
    }

    @ParameterizedTest
    @EnumSource(MovingViolationEnum.class)
    void allRecentVehicleMovingViolationsFail(MovingViolationEnum type) {
        Application app = validApplication();
        incident(app, TODAY.minusDays(1), new MovingViolation(type));
        assertFalse(new VehicleHistoryValidationStrategy(CLOCK).validate(app).isValid());
    }

    @Test void recentVehicleNonMovingViolationPasses() {
        Application app = validApplication();
        incident(app, TODAY, new NonMovingViolation(NonMovingViolationEnum.PARKING_VIOLATION));
        assertTrue(new VehicleHistoryValidationStrategy(CLOCK).validate(app).isValid());
    }

    @Test void vehicleIncidentAtSixMonthCutoffFailsButEarlierPasses() {
        Application app = validApplication();
        incident(app, TODAY.minusMonths(6), new Crash(CrashEnum.FENDER_BENDER));
        assertFalse(new VehicleHistoryValidationStrategy(CLOCK).validate(app).isValid());
        incident(app, TODAY.minusMonths(6).minusDays(1), new Crash(CrashEnum.FENDER_BENDER));
        assertTrue(new VehicleHistoryValidationStrategy(CLOCK).validate(app).isValid());
    }

    @Test void invalidVehicleIncidentDateFails() {
        Application app = validApplication();
        app.setVehicleHistoryRecords(List.of(new VehicleHistory(List.of(new Date.Builder(2026, 2, 30).build()),
                List.of(new Crash(CrashEnum.FENDER_BENDER)), List.of(app.getDriver().getName()))));
        assertFalse(new VehicleHistoryValidationStrategy(CLOCK).validate(app).isValid());
    }

    @Test void nullApplicationFailsSafelyForEachStrategy() {
        assertFalse(new AgeValidationStrategy(CLOCK).validate(null).isValid());
        assertFalse(new LicenseValidationStrategy(CLOCK).validate(null).isValid());
        assertFalse(new VehicleValidationStrategy(CLOCK).validate(null).isValid());
        assertFalse(new InsuranceValidationStrategy(CLOCK).validate(null).isValid());
        assertFalse(new DrivingHistoryValidationStrategy().validate(null).isValid());
        assertFalse(new VehicleHistoryValidationStrategy(CLOCK).validate(null).isValid());
        assertEquals(6, validator.validate(null).getReasons().size());
    }

    @Test void fixedClockControlsEvaluationInsteadOfMachineDate() {
        Application app = validApplication();
        assertTrue(validator.validate(app).isValid());
        Clock later = Clock.fixed(TODAY.plusYears(5).atStartOfDay(ZoneOffset.UTC).toInstant(), ZoneOffset.UTC);
        assertFalse(Validator.defaultValidator(later).validate(app).isValid());
    }

    @Test void defaultValidatorSamplesClockOncePerApplication() {
        AtomicInteger samples = new AtomicInteger();
        Clock advancing = new Clock() {
            public ZoneId getZone() { return ZoneOffset.UTC; }
            public Clock withZone(ZoneId zone) { return this; }
            public Instant instant() { return CLOCK.instant().plusSeconds(86400L * samples.getAndIncrement()); }
        };
        Application app = validApplication();
        app.getDriverLicense().setExpiratonDate(date(TODAY));
        app.getVehicleInsurance().setExpirationDate(date(TODAY));
        Validator timeAware = Validator.defaultValidator(advancing);
        assertTrue(timeAware.validate(app).isValid());
        assertEquals(1, samples.get());
        assertFalse(timeAware.validate(app).isValid());
        assertEquals(2, samples.get());
    }
}
