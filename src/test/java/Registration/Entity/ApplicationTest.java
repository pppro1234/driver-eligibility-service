package Registration.Entity;

import Registration.IncidentFactory.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests application construction and field access.
 */
class ApplicationTest {

    private Driver testDriver;
    private DriverLicense testLicense;
    private List<Vehicle> testVehicles;
    private VehicleInsurance testInsurance;
    private List<Incident> testDriverHistory;
    private List<Incident> testVehicleHistory;

    /**
     * Sets up test fixtures before each test method.
     */
    @BeforeEach
    void setUp() {
        // Create driver
        Name driverName = new Name("John", "Doe");
        Date birthday = new Date.Builder(1990, 5, 15).build();
        testDriver = new Driver.Builder(driverName, birthday).build();

        // Create license
        testLicense = new DriverLicense.Builder()
                .licenseNumber("DL123456")
                .name(driverName)
                .address("123 Main St")
                .birthDate(birthday)
                .countryAndState("USA-CA")
                .issuanceDate(new Date.Builder(2020, 1, 1).build())
                .experienceDate(new Date.Builder(2028, 1, 1).build())
                .build();

        // Create vehicles
        Vehicle vehicle1 = new Vehicle.Builder()
                .make("Toyota")
                .model("Camry")
                .year(2020)
                .owner("John Doe")
                .plateNumber("ABC123")
                .build();
        Vehicle vehicle2 = new Vehicle.Builder()
                .make("Honda")
                .model("Accord")
                .year(2021)
                .owner("John Doe")
                .plateNumber("XYZ789")
                .build();
        testVehicles = new ArrayList<>(Arrays.asList(vehicle1, vehicle2));

        // Create insurance
        testInsurance = new VehicleInsurance.Builder()
                .nameOnPolicy(driverName)
                .drivers(Arrays.asList(testDriver))
                .expirationDate(new Date.Builder(2025, 12, 31).build())
                .build();

        // Create driver history
        testDriverHistory = new ArrayList<>();
        testDriverHistory.add(new MovingViolation(MovingViolationEnum.SPEEDING));
        testDriverHistory.add(new NonMovingViolation(NonMovingViolationEnum.PARKING_VIOLATION));

        // Create vehicle history
        testVehicleHistory = new ArrayList<>();
        testVehicleHistory.add(new Crash(CrashEnum.FENDER_BENDER));
    }

    // ============ Builder Tests - All Fields ============

    /**
     * Tests building with all fields set.
     */
    @Test
    void testBuilderWithAllFields() {
        Application app = new Application.Builder()
                .driver(testDriver)
                .driverLicense(testLicense)
                .vehicle(testVehicles)
                .vehicleInsurance(testInsurance)
                .driverHistory(testDriverHistory)
                .vehicleHistory(testVehicleHistory)
                .build();

        assertNotNull(app);
        assertEquals(testDriver, app.getDriver());
        assertEquals(testLicense, app.getDriverLicense());
        assertEquals(testVehicles, app.getVehicle());
        assertEquals(testInsurance, app.getVehicleInsurance());
        assertEquals(testDriverHistory, app.getDriverHistory());
        assertEquals(testVehicleHistory, app.getVehicleHistory());
    }

    /**
     * Tests building with no fields set.
     */
    @Test
    void testBuilderWithNoFields() {
        Application app = new Application.Builder().build();

        assertNotNull(app);
        assertNull(app.getDriver());
        assertNull(app.getDriverLicense());
        assertNull(app.getVehicle());
        assertNull(app.getVehicleInsurance());
        assertNull(app.getDriverHistory());
        assertNull(app.getVehicleHistory());
    }

    /**
     * Tests building with only required fields (driver and license).
     */
    @Test
    void testBuilderWithOnlyRequiredFields() {
        Application app = new Application.Builder()
                .driver(testDriver)
                .driverLicense(testLicense)
                .build();

        assertNotNull(app);
        assertEquals(testDriver, app.getDriver());
        assertEquals(testLicense, app.getDriverLicense());
        assertNull(app.getVehicle());
        assertNull(app.getVehicleInsurance());
        assertNull(app.getDriverHistory());
        assertNull(app.getVehicleHistory());
    }

    /**
     * Tests building with driver only.
     */
    @Test
    void testBuilderWithDriverOnly() {
        Application app = new Application.Builder()
                .driver(testDriver)
                .build();

        assertEquals(testDriver, app.getDriver());
        assertNull(app.getDriverLicense());
    }

    /**
     * Tests building with various field combinations.
     */
    @Test
    void testBuilderWithPartialFields() {
        Application app = new Application.Builder()
                .driver(testDriver)
                .vehicle(testVehicles)
                .driverHistory(testDriverHistory)
                .build();

        assertEquals(testDriver, app.getDriver());
        assertEquals(testVehicles, app.getVehicle());
        assertEquals(testDriverHistory, app.getDriverHistory());
        assertNull(app.getDriverLicense());
        assertNull(app.getVehicleInsurance());
        assertNull(app.getVehicleHistory());
    }

    // ============ Builder Method Chaining Tests ============

    /**
     * Tests driver() method chaining.
     */
    @Test
    void testBuilderDriverMethodChaining() {
        Application.Builder builder = new Application.Builder();
        Application.Builder result = builder.driver(testDriver);

        assertSame(builder, result);
    }

    /**
     * Tests driverLicense() method chaining.
     */
    @Test
    void testBuilderDriverLicenseMethodChaining() {
        Application.Builder builder = new Application.Builder();
        Application.Builder result = builder.driverLicense(testLicense);

        assertSame(builder, result);
    }

    /**
     * Tests vehicle() method chaining.
     */
    @Test
    void testBuilderVehicleMethodChaining() {
        Application.Builder builder = new Application.Builder();
        Application.Builder result = builder.vehicle(testVehicles);

        assertSame(builder, result);
    }

    /**
     * Tests vehicleInsurance() method chaining.
     */
    @Test
    void testBuilderVehicleInsuranceMethodChaining() {
        Application.Builder builder = new Application.Builder();
        Application.Builder result = builder.vehicleInsurance(testInsurance);

        assertSame(builder, result);
    }

    /**
     * Tests driverHistory() method chaining.
     */
    @Test
    void testBuilderDriverHistoryMethodChaining() {
        Application.Builder builder = new Application.Builder();
        Application.Builder result = builder.driverHistory(testDriverHistory);

        assertSame(builder, result);
    }

    /**
     * Tests vehicleHistory() method chaining.
     */
    @Test
    void testBuilderVehicleHistoryMethodChaining() {
        Application.Builder builder = new Application.Builder();
        Application.Builder result = builder.vehicleHistory(testVehicleHistory);

        assertSame(builder, result);
    }

    /**
     * Tests complete method chaining.
     */
    @Test
    void testBuilderCompleteMethodChaining() {
        Application app = new Application.Builder()
                .driver(testDriver)
                .driverLicense(testLicense)
                .vehicle(testVehicles)
                .vehicleInsurance(testInsurance)
                .driverHistory(testDriverHistory)
                .vehicleHistory(testVehicleHistory)
                .build();

        assertNotNull(app);
        assertEquals(testDriver, app.getDriver());
        assertEquals(testVehicleHistory, app.getVehicleHistory());
    }

    /**
     * Tests builder with different method ordering.
     */
    @Test
    void testBuilderWithDifferentMethodOrder() {
        Application app = new Application.Builder()
                .vehicleHistory(testVehicleHistory)
                .driver(testDriver)
                .vehicleInsurance(testInsurance)
                .driverLicense(testLicense)
                .driverHistory(testDriverHistory)
                .vehicle(testVehicles)
                .build();

        assertEquals(testDriver, app.getDriver());
        assertEquals(testLicense, app.getDriverLicense());
    }

    /**
     * Tests builder with null values explicitly set.
     */
    @Test
    void testBuilderWithNullValues() {
        Application app = new Application.Builder()
                .driver(null)
                .driverLicense(null)
                .vehicle(null)
                .vehicleInsurance(null)
                .driverHistory(null)
                .vehicleHistory(null)
                .build();

        assertNull(app.getDriver());
        assertNull(app.getDriverLicense());
        assertNull(app.getVehicle());
        assertNull(app.getVehicleInsurance());
        assertNull(app.getDriverHistory());
        assertNull(app.getVehicleHistory());
    }

    // ============ Getter Tests ============

    /**
     * Tests getDriver() returns correct value.
     */
    @Test
    void testGetDriver() {
        Application app = new Application.Builder()
                .driver(testDriver)
                .build();

        assertEquals(testDriver, app.getDriver());
    }

    /**
     * Tests getDriverLicense() returns correct value.
     */
    @Test
    void testGetDriverLicense() {
        Application app = new Application.Builder()
                .driverLicense(testLicense)
                .build();

        assertEquals(testLicense, app.getDriverLicense());
    }

    /**
     * Tests getVehicle() returns correct value.
     */
    @Test
    void testGetVehicle() {
        Application app = new Application.Builder()
                .vehicle(testVehicles)
                .build();

        assertEquals(testVehicles, app.getVehicle());
    }

    /**
     * Tests getVehicleInsurance() returns correct value.
     */
    @Test
    void testGetVehicleInsurance() {
        Application app = new Application.Builder()
                .vehicleInsurance(testInsurance)
                .build();

        assertEquals(testInsurance, app.getVehicleInsurance());
    }

    /**
     * Tests getDriverHistory() returns correct value.
     */
    @Test
    void testGetDriverHistory() {
        Application app = new Application.Builder()
                .driverHistory(testDriverHistory)
                .build();

        assertEquals(testDriverHistory, app.getDriverHistory());
    }

    /**
     * Tests getVehicleHistory() returns correct value.
     */
    @Test
    void testGetVehicleHistory() {
        Application app = new Application.Builder()
                .vehicleHistory(testVehicleHistory)
                .build();

        assertEquals(testVehicleHistory, app.getVehicleHistory());
    }

    // ============ Setter Tests ============

    /**
     * Tests setDriver() updates the value.
     */
    @Test
    void testSetDriver() {
        Application app = new Application.Builder().build();

        app.setDriver(testDriver);

        assertEquals(testDriver, app.getDriver());
    }

    /**
     * Tests setDriverLicense() updates the value.
     */
    @Test
    void testSetDriverLicense() {
        Application app = new Application.Builder().build();

        app.setDriverLicense(testLicense);

        assertEquals(testLicense, app.getDriverLicense());
    }

    /**
     * Tests setVehicle() updates the value.
     */
    @Test
    void testSetVehicle() {
        Application app = new Application.Builder().build();

        app.setVehicle(testVehicles);

        assertEquals(testVehicles, app.getVehicle());
    }

    /**
     * Tests setVehicleInsurance() updates the value.
     */
    @Test
    void testSetVehicleInsurance() {
        Application app = new Application.Builder().build();

        app.setVehicleInsurance(testInsurance);

        assertEquals(testInsurance, app.getVehicleInsurance());
    }

    /**
     * Tests setDriverHistory() updates the value.
     */
    @Test
    void testSetDriverHistory() {
        Application app = new Application.Builder().build();

        app.setDriverHistory(testDriverHistory);

        assertEquals(testDriverHistory, app.getDriverHistory());
    }

    /**
     * Tests setVehicleHistory() updates the value.
     */
    @Test
    void testSetVehicleHistory() {
        Application app = new Application.Builder().build();

        app.setVehicleHistory(testVehicleHistory);

        assertEquals(testVehicleHistory, app.getVehicleHistory());
    }

    /**
     * Tests setters with null values.
     */
    @Test
    void testSettersWithNull() {
        Application app = new Application.Builder()
                .driver(testDriver)
                .driverLicense(testLicense)
                .vehicle(testVehicles)
                .vehicleInsurance(testInsurance)
                .driverHistory(testDriverHistory)
                .vehicleHistory(testVehicleHistory)
                .build();

        app.setDriver(null);
        app.setDriverLicense(null);
        app.setVehicle(null);
        app.setVehicleInsurance(null);
        app.setDriverHistory(null);
        app.setVehicleHistory(null);

        assertNull(app.getDriver());
        assertNull(app.getDriverLicense());
        assertNull(app.getVehicle());
        assertNull(app.getVehicleInsurance());
        assertNull(app.getDriverHistory());
        assertNull(app.getVehicleHistory());
    }

    /**
     * Tests setters overwrite existing values.
     */
    @Test
    void testSettersOverwriteExistingValues() {
        Application app = new Application.Builder()
                .driver(testDriver)
                .driverLicense(testLicense)
                .build();

        Driver newDriver = new Driver.Builder(
                new Name("Jane", "Smith"),
                new Date.Builder(1985, 6, 20).build()
        ).build();

        app.setDriver(newDriver);

        assertEquals(newDriver, app.getDriver());
    }

    // ============ Integration Tests ============

    /**
     * Tests complete lifecycle: build, access, modify.
     */
    @Test
    void testCompleteLifecycle() {
        // Build
        Application app = new Application.Builder()
                .driver(testDriver)
                .driverLicense(testLicense)
                .vehicle(testVehicles)
                .build();

        // Access
        assertEquals(testDriver, app.getDriver());
        assertEquals(2, app.getVehicle().size());

        // Modify
        app.setVehicleInsurance(testInsurance);
        app.setDriverHistory(testDriverHistory);

        // Verify
        assertEquals(testInsurance, app.getVehicleInsurance());
        assertEquals(2, app.getDriverHistory().size());
    }

    /**
     * Tests realistic application scenario.
     */
    @Test
    void testRealisticApplicationScenario() {
        // Create complete application
        Name applicantName = new Name("Alice", "Johnson");
        Date birthDate = new Date.Builder(1988, 3, 15).build();

        Driver driver = new Driver.Builder(applicantName, birthDate).build();

        DriverLicense license = new DriverLicense.Builder()
                .licenseNumber("CA98765432")
                .name(applicantName)
                .address("789 Pine St, San Francisco, CA 94102")
                .birthDate(birthDate)
                .countryAndState("USA-CA")
                .issuanceDate(new Date.Builder(2018, 3, 15).build())
                .experienceDate(new Date.Builder(2026, 3, 15).build())
                .build();

        Vehicle vehicle = new Vehicle.Builder()
                .make("Tesla")
                .model("Model 3")
                .year(2022)
                .owner("Alice Johnson")
                .plateNumber("TESLA123")
                .build();

        Application app = new Application.Builder()
                .driver(driver)
                .driverLicense(license)
                .vehicle(Collections.singletonList(vehicle))
                .build();

        // Verify
        assertEquals("Alice", app.getDriver().getName().getFirstName());
        assertEquals("CA98765432", app.getDriverLicense().getLicenseNumber());
        assertEquals(1, app.getVehicle().size());
        assertEquals("Tesla", app.getVehicle().get(0).getMake());
    }

    /**
     * Tests that builder creates independent instances.
     */
    @Test
    void testBuilderCreatesIndependentInstances() {
        Application.Builder builder = new Application.Builder()
                .driver(testDriver)
                .driverLicense(testLicense);

        Application app1 = builder.build();
        Application app2 = builder.build();

        assertNotSame(app1, app2);

        // Modify app1
        app1.setVehicle(testVehicles);

        // app2 should not have vehicles
        assertNull(app2.getVehicle());
    }

    /**
     * Tests updating all fields via setters.
     */
    @Test
    void testUpdatingAllFieldsViaSetters() {
        Application app = new Application.Builder().build();

        app.setDriver(testDriver);
        app.setDriverLicense(testLicense);
        app.setVehicle(testVehicles);
        app.setVehicleInsurance(testInsurance);
        app.setDriverHistory(testDriverHistory);
        app.setVehicleHistory(testVehicleHistory);

        assertEquals(testDriver, app.getDriver());
        assertEquals(testLicense, app.getDriverLicense());
        assertEquals(testVehicles, app.getVehicle());
        assertEquals(testInsurance, app.getVehicleInsurance());
        assertEquals(testDriverHistory, app.getDriverHistory());
        assertEquals(testVehicleHistory, app.getVehicleHistory());
    }

    /**
     * Tests getter and setter consistency for all fields.
     */
    @Test
    void testGetterSetterConsistency() {
        Application app = new Application.Builder().build();

        app.setDriver(testDriver);
        assertEquals(testDriver, app.getDriver());

        app.setDriverLicense(testLicense);
        assertEquals(testLicense, app.getDriverLicense());

        app.setVehicle(testVehicles);
        assertEquals(testVehicles, app.getVehicle());

        app.setVehicleInsurance(testInsurance);
        assertEquals(testInsurance, app.getVehicleInsurance());

        app.setDriverHistory(testDriverHistory);
        assertEquals(testDriverHistory, app.getDriverHistory());

        app.setVehicleHistory(testVehicleHistory);
        assertEquals(testVehicleHistory, app.getVehicleHistory());
    }

    /**
     * Tests that getters return same references (no defensive copying).
     */
    @Test
    void testGettersReturnSameReferences() {
        Application app = new Application.Builder()
                .driver(testDriver)
                .driverLicense(testLicense)
                .vehicle(testVehicles)
                .vehicleInsurance(testInsurance)
                .driverHistory(testDriverHistory)
                .vehicleHistory(testVehicleHistory)
                .build();

        assertSame(testDriver, app.getDriver());
        assertSame(testLicense, app.getDriverLicense());
        assertSame(testVehicles, app.getVehicle());
        assertSame(testInsurance, app.getVehicleInsurance());
        assertSame(testDriverHistory, app.getDriverHistory());
        assertSame(testVehicleHistory, app.getVehicleHistory());
    }

    // ============ List Modification Tests ============

    /**
     * Tests modifying vehicle list after creation.
     */
    @Test
    void testModifyingVehicleListAfterCreation() {
        Application app = new Application.Builder()
                .vehicle(testVehicles)
                .build();

        assertEquals(2, app.getVehicle().size());

        Vehicle newVehicle = new Vehicle.Builder()
                .make("Ford")
                .model("Focus")
                .year(2019)
                .build();
        app.getVehicle().add(newVehicle);

        assertEquals(3, app.getVehicle().size());
    }

    /**
     * Tests modifying driver history list after creation.
     */
    @Test
    void testModifyingDriverHistoryAfterCreation() {
        Application app = new Application.Builder()
                .driverHistory(testDriverHistory)
                .build();

        assertEquals(2, app.getDriverHistory().size());

        app.getDriverHistory().add(new Crash(CrashEnum.NO_INJURY_CRASH));

        assertEquals(3, app.getDriverHistory().size());
    }

    /**
     * Tests modifying vehicle history list after creation.
     */
    @Test
    void testModifyingVehicleHistoryAfterCreation() {
        Application app = new Application.Builder()
                .vehicleHistory(testVehicleHistory)
                .build();

        assertEquals(1, app.getVehicleHistory().size());

        app.getVehicleHistory().add(new MovingViolation(MovingViolationEnum.SPEEDING));

        assertEquals(2, app.getVehicleHistory().size());
    }

    // ============ Empty List Tests ============

    /**
     * Tests with empty vehicle list.
     */
    @Test
    void testWithEmptyVehicleList() {
        Application app = new Application.Builder()
                .vehicle(new ArrayList<>())
                .build();

        assertNotNull(app.getVehicle());
        assertTrue(app.getVehicle().isEmpty());
    }

    /**
     * Tests with empty driver history.
     */
    @Test
    void testWithEmptyDriverHistory() {
        Application app = new Application.Builder()
                .driverHistory(new ArrayList<>())
                .build();

        assertNotNull(app.getDriverHistory());
        assertTrue(app.getDriverHistory().isEmpty());
    }

    /**
     * Tests with empty vehicle history.
     */
    @Test
    void testWithEmptyVehicleHistory() {
        Application app = new Application.Builder()
                .vehicleHistory(new ArrayList<>())
                .build();

        assertNotNull(app.getVehicleHistory());
        assertTrue(app.getVehicleHistory().isEmpty());
    }

    // ============ Application Scenarios ============

    /**
     * Tests clean driver application (no history).
     */
    @Test
    void testCleanDriverApplication() {
        Application app = new Application.Builder()
                .driver(testDriver)
                .driverLicense(testLicense)
                .vehicle(testVehicles)
                .vehicleInsurance(testInsurance)
                .driverHistory(new ArrayList<>())
                .vehicleHistory(new ArrayList<>())
                .build();

        assertNotNull(app.getDriver());
        assertNotNull(app.getDriverLicense());
        assertTrue(app.getDriverHistory().isEmpty());
        assertTrue(app.getVehicleHistory().isEmpty());
    }

    /**
     * Tests application with incident history.
     */
    @Test
    void testApplicationWithIncidentHistory() {
        Application app = new Application.Builder()
                .driver(testDriver)
                .driverLicense(testLicense)
                .driverHistory(testDriverHistory)
                .vehicleHistory(testVehicleHistory)
                .build();

        assertEquals(2, app.getDriverHistory().size());
        assertEquals(1, app.getVehicleHistory().size());
    }

    /**
     * Tests application with multiple vehicles.
     */
    @Test
    void testApplicationWithMultipleVehicles() {
        Application app = new Application.Builder()
                .vehicle(testVehicles)
                .build();

        assertEquals(2, app.getVehicle().size());
    }

    /**
     * Tests application with single vehicle.
     */
    @Test
    void testApplicationWithSingleVehicle() {
        List<Vehicle> singleVehicle = Collections.singletonList(testVehicles.get(0));

        Application app = new Application.Builder()
                .vehicle(singleVehicle)
                .build();

        assertEquals(1, app.getVehicle().size());
    }

    /**
     * Tests that empty builder creates valid application.
     */
    @Test
    void testEmptyBuilderCreatesValidApplication() {
        Application app = new Application.Builder().build();

        assertNotNull(app);
    }
}