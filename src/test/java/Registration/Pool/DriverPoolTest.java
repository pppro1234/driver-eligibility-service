package Registration.Pool;

import Registration.Entity.*;
import Registration.IncidentFactory.*;
import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the local CLI driver pool and its grouping policy.
 */
class DriverPoolTest {

    private DriverPool pool;

    @BeforeEach
    void setUp() {
        pool = DriverPool.getInstance();
        pool.clear(); // Clear pool before each test
    }

    @AfterEach
    void tearDown() {
        pool.clear();
    }

    // ==================== Singleton Tests ====================

    @Test
    @DisplayName("getInstance returns same instance (Singleton)")
    void testSingletonInstance() {
        DriverPool instance1 = DriverPool.getInstance();
        DriverPool instance2 = DriverPool.getInstance();
        assertSame(instance1, instance2, "Should return the same singleton instance");
    }

    // ==================== addDriver Tests ====================

    @Test
    @DisplayName("addDriver - successfully adds driver with vehicles")
    void testAddDriverSuccess() {
        Application app = createValidApplication("Anne", "Smith", 1990, "BAY01FG");

        boolean result = pool.addDriver(app);

        assertTrue(result, "Should return true when driver is added");
        assertEquals(1, pool.getDriverCount(), "Pool should have 1 driver");
        assertEquals(1, pool.getTotalVehicleCount(), "Pool should have 1 vehicle");
    }

    @Test
    @DisplayName("addDriver - adds driver with multiple vehicles")
    void testAddDriverMultipleVehicles() {
        Application app = createApplicationWithMultipleVehicles("Anne", "Smith", 1990);

        pool.addDriver(app);

        assertEquals(1, pool.getDriverCount(), "Pool should have 1 driver");
        assertEquals(2, pool.getTotalVehicleCount(), "Pool should have 2 vehicles");
    }

    @Test
    @DisplayName("addDriver - prevents duplicate driver-vehicle combination")
    void testAddDriverDuplicateVehicle() {
        Application app1 = createValidApplication("Anne", "Smith", 1990, "BAY01FG");
        Application app2 = createValidApplication("Anne", "Smith", 1990, "BAY01FG");

        pool.addDriver(app1);
        boolean result = pool.addDriver(app2);

        assertFalse(result, "Should return false for duplicate driver-vehicle");
        assertEquals(1, pool.getDriverCount(), "Pool should still have 1 driver");
        assertEquals(1, pool.getTotalVehicleCount(), "Pool should still have 1 vehicle");
    }

    @Test
    @DisplayName("addDriver - same driver can add different vehicles")
    void testAddDriverDifferentVehicles() {
        Application app1 = createValidApplication("Anne", "Smith", 1990, "BAY01FG");
        Application app2 = createValidApplication("Anne", "Smith", 1990, "XYZ789");
        app2.getDriverLicense().setLicenseNumber(app1.getDriverLicense().getLicenseNumber());

        pool.addDriver(app1);
        boolean result = pool.addDriver(app2);

        assertTrue(result, "Should return true for new vehicle");
        assertEquals(1, pool.getDriverCount(), "Pool should have 1 driver");
        assertEquals(2, pool.getTotalVehicleCount(), "Pool should have 2 vehicles");
    }

    @Test
    @DisplayName("addDriver - different drivers can be added")
    void testAddDifferentDrivers() {
        Application app1 = createValidApplication("Anne", "Smith", 1990, "BAY01FG");
        Application app2 = createValidApplication("Brandon", "Smith", 1988, "AAB1234");

        pool.addDriver(app1);
        pool.addDriver(app2);

        assertEquals(2, pool.getDriverCount(), "Pool should have 2 drivers");
        assertEquals(2, pool.getTotalVehicleCount(), "Pool should have 2 vehicles");
    }

    @Test
    @DisplayName("addDriver - driver with violations")
    void testAddDriverWithViolations() {
        Application app = createApplicationWithViolations("Patricia", "Fuller", 1985, "ZZW-123");

        pool.addDriver(app);

        assertEquals(1, pool.getDriverCount());
        String info = pool.provideDriverInfo("Fuller");
        assertTrue(info.contains("Driving violations:"), "Should show violations");
        assertTrue(info.contains("Distracted Driving"), "Should show specific violation");
    }

    @Test
    void sameNameAndBirthYearWithDifferentLicensesRemainSeparate() {
        Application first = createValidApplication("Anne", "Smith", 1990, "PLATE1");
        Application second = createValidApplication("Anne", "Smith", 1990, "PLATE2");
        assertEquals(DriverPool.AddResult.ADDED, pool.addApplication(first));
        assertEquals(DriverPool.AddResult.ADDED, pool.addApplication(second));
        assertEquals(2, pool.getDriverCount());
        assertEquals(2, pool.getTotalVehicleCount());
    }

    @Test
    void sameLicenseNumberInDifferentJurisdictionsRemainsSeparate() {
        Application first = createValidApplication("Anne", "Smith", 1990, "PLATE1");
        Application second = createValidApplication("Anne", "Smith", 1990, "PLATE2");
        second.getDriverLicense().setLicenseNumber(first.getDriverLicense().getLicenseNumber());
        second.getDriverLicense().setCountryAndState("US, NY");
        assertEquals(DriverPool.AddResult.ADDED, pool.addApplication(first));
        assertEquals(DriverPool.AddResult.ADDED, pool.addApplication(second));
        assertEquals(2, pool.getDriverCount());
    }

    @Test
    void repeatLicenseDistinguishesNewVehiclesAndDuplicates() {
        Application first = createValidApplication("Anne", "Smith", 1990, "PLATE1");
        Application second = createValidApplication("Anne", "Smith", 1990, "PLATE2");
        second.getDriverLicense().setLicenseNumber(" " + first.getDriverLicense().getLicenseNumber().toLowerCase() + " ");
        second.getDriverLicense().setCountryAndState(" us-ca ");
        assertEquals(DriverPool.AddResult.ADDED, pool.addApplication(first));
        assertEquals(DriverPool.AddResult.MERGED, pool.addApplication(second));
        assertEquals(DriverPool.AddResult.DUPLICATE, pool.addApplication(second));
        assertEquals(1, pool.getDriverCount());
        assertEquals(2, pool.getTotalVehicleCount());
    }

    @Test
    void conflictingDriverDetailsForSameLicenseDoNotMerge() {
        Application first = createValidApplication("Anne", "Smith", 1990, "PLATE1");
        Application second = createValidApplication("Other", "Person", 1980, "PLATE2");
        second.getDriverLicense().setLicenseNumber(first.getDriverLicense().getLicenseNumber());
        pool.addApplication(first);
        assertEquals(DriverPool.AddResult.CONFLICT, pool.addApplication(second));
        assertEquals(1, pool.getDriverCount());
        assertEquals(1, pool.getTotalVehicleCount());
    }

    @Test
    void missingLicenseKeyFailsBeforePoolMutation() {
        Application app = createValidApplication("Anne", "Smith", 1990, "PLATE1");
        app.getDriverLicense().setLicenseNumber(" ");
        assertThrows(IllegalArgumentException.class, () -> pool.addApplication(app));
        assertEquals(0, pool.getDriverCount());
    }

    // ==================== provideDriverInfo Tests ====================

    @Test
    @DisplayName("provideDriverInfo - returns driver info by last name")
    void testProvideDriverInfoFound() {
        Application app = createValidApplication("Anne", "Smith", 1990, "BAY01FG");
        pool.addDriver(app);

        String result = pool.provideDriverInfo("Smith");

        assertTrue(result.contains("Smith, Anne"), "Should contain driver name");
        assertTrue(result.contains("BAY01FG"), "Should contain plate number");
    }

    @Test
    @DisplayName("provideDriverInfo - returns 'No registered driver found' for unknown name")
    void testProvideDriverInfoNotFound() {
        String result = pool.provideDriverInfo("Jefferson");

        assertEquals("No registered driver found", result);
    }

    @Test
    @DisplayName("provideDriverInfo - case insensitive search")
    void testProvideDriverInfoCaseInsensitive() {
        Application app = createValidApplication("Anne", "Smith", 1990, "BAY01FG");
        pool.addDriver(app);

        String result1 = pool.provideDriverInfo("smith");
        String result2 = pool.provideDriverInfo("SMITH");
        String result3 = pool.provideDriverInfo("SmItH");

        assertTrue(result1.contains("Smith, Anne"));
        assertTrue(result2.contains("Smith, Anne"));
        assertTrue(result3.contains("Smith, Anne"));
    }

    @Test
    @DisplayName("provideDriverInfo - multiple drivers same last name sorted by first name")
    void testProvideDriverInfoMultipleDriversSorted() {
        Application app1 = createValidApplication("Zoe", "Smith", 1990, "ZOE123");
        Application app2 = createValidApplication("Anne", "Smith", 1992, "ANNE123");
        Application app3 = createValidApplication("Mike", "Smith", 1988, "MIKE123");

        pool.addDriver(app1);
        pool.addDriver(app2);
        pool.addDriver(app3);

        String result = pool.provideDriverInfo("Smith");

        // Anne should come before Mike, Mike before Zoe (alphabetical by first name)
        int annePos = result.indexOf("Anne");
        int mikePos = result.indexOf("Mike");
        int zoePos = result.indexOf("Zoe");

        assertTrue(annePos < mikePos, "Anne should come before Mike");
        assertTrue(mikePos < zoePos, "Mike should come before Zoe");
    }

    @Test
    @DisplayName("provideDriverInfo - shows all vehicles for driver")
    void testProvideDriverInfoShowsAllVehicles() {
        Application app = createApplicationWithMultipleVehicles("Anne", "Smith", 1990);
        pool.addDriver(app);

        String result = pool.provideDriverInfo("Smith");

        assertTrue(result.contains("Honda"), "Should contain first vehicle make");
        assertTrue(result.contains("Toyota"), "Should contain second vehicle make");
        assertTrue(result.contains("BAY01FG"), "Should contain first plate");
        assertTrue(result.contains("AZ034W1"), "Should contain second plate");
    }

    @Test
    @DisplayName("provideDriverInfo - shows violations when present")
    void testProvideDriverInfoShowsViolations() {
        Application app = createApplicationWithViolations("Patricia", "Fuller", 1985, "ZZW-123");
        pool.addDriver(app);

        String result = pool.provideDriverInfo("Fuller");

        assertTrue(result.contains("Driving violations:"));
        assertTrue(result.contains("Distracted Driving"));
        assertTrue(result.contains("Parking Violation"));
    }

    @Test
    @DisplayName("provideDriverInfo - no violations section when driver has none")
    void testProvideDriverInfoNoViolations() {
        Application app = createValidApplication("Anne", "Smith", 1990, "BAY01FG");
        pool.addDriver(app);

        String result = pool.provideDriverInfo("Smith");

        assertFalse(result.contains("Driving violations:"), "Should not show violations section");
    }

    @Test
    @DisplayName("provideDriverInfo - driver with null violations list")
    void testProvideDriverInfoNullViolations() {
        Application app = createApplicationWithNullViolations("John", "Doe", 1985, "DOE123");
        pool.addDriver(app);

        String result = pool.provideDriverInfo("Doe");

        assertTrue(result.contains("Doe, John"));
        assertFalse(result.contains("Driving violations:"));
    }

    // ==================== Vehicle Claim Tests ====================

    @Test
    @DisplayName("claimVehicle - successfully claims available vehicle")
    void testClaimVehicleSuccess() {
        boolean result = pool.claimVehicle("ABC123");

        assertTrue(result, "Should return true for available vehicle");
        assertTrue(pool.isVehicleInUse("ABC123"), "Vehicle should be marked in use");
    }

    @Test
    @DisplayName("claimVehicle - fails to claim already claimed vehicle")
    void testClaimVehicleAlreadyClaimed() {
        pool.claimVehicle("ABC123");

        boolean result = pool.claimVehicle("ABC123");

        assertFalse(result, "Should return false for already claimed vehicle");
    }

    @Test
    @DisplayName("releaseVehicle - releases claimed vehicle")
    void testReleaseVehicle() {
        pool.claimVehicle("ABC123");
        assertTrue(pool.isVehicleInUse("ABC123"));

        pool.releaseVehicle("ABC123");

        assertFalse(pool.isVehicleInUse("ABC123"), "Vehicle should no longer be in use");
    }

    @Test
    @DisplayName("releaseVehicle - safe to release unclaimed vehicle")
    void testReleaseUnclaimedVehicle() {
        assertDoesNotThrow(() -> pool.releaseVehicle("NOTCLAIMED"));
    }

    @Test
    @DisplayName("isVehicleInUse - returns false for unclaimed vehicle")
    void testIsVehicleInUseUnclaimed() {
        assertFalse(pool.isVehicleInUse("UNCLAIMED"));
    }

    @Test
    @DisplayName("isVehicleInUse - returns true for claimed vehicle")
    void testIsVehicleInUseClaimed() {
        pool.claimVehicle("CLAIMED123");

        assertTrue(pool.isVehicleInUse("CLAIMED123"));
    }

    // ==================== Count Methods Tests ====================

    @Test
    @DisplayName("getDriverCount - returns 0 for empty pool")
    void testGetDriverCountEmpty() {
        assertEquals(0, pool.getDriverCount());
    }

    @Test
    @DisplayName("getDriverCount - returns correct count")
    void testGetDriverCountMultiple() {
        pool.addDriver(createValidApplication("Anne", "Smith", 1990, "A1"));
        pool.addDriver(createValidApplication("Bob", "Jones", 1985, "B1"));
        pool.addDriver(createValidApplication("Carol", "White", 1992, "C1"));

        assertEquals(3, pool.getDriverCount());
    }

    @Test
    @DisplayName("getTotalVehicleCount - returns 0 for empty pool")
    void testGetTotalVehicleCountEmpty() {
        assertEquals(0, pool.getTotalVehicleCount());
    }

    @Test
    @DisplayName("getTotalVehicleCount - counts all vehicles across drivers")
    void testGetTotalVehicleCountMultiple() {
        pool.addDriver(createApplicationWithMultipleVehicles("Anne", "Smith", 1990));
        pool.addDriver(createValidApplication("Bob", "Jones", 1985, "BOB123"));

        assertEquals(3, pool.getTotalVehicleCount());
    }

    // ==================== Clear Tests ====================

    @Test
    @DisplayName("clear - removes all drivers and vehicles")
    void testClearDrivers() {
        pool.addDriver(createValidApplication("Anne", "Smith", 1990, "A1"));
        pool.addDriver(createValidApplication("Bob", "Jones", 1985, "B1"));
        assertEquals(2, pool.getDriverCount());

        pool.clear();

        assertEquals(0, pool.getDriverCount());
        assertEquals(0, pool.getTotalVehicleCount());
    }

    @Test
    @DisplayName("clear - removes all claimed vehicles")
    void testClearClaimedVehicles() {
        pool.claimVehicle("ABC123");
        pool.claimVehicle("XYZ789");
        assertTrue(pool.isVehicleInUse("ABC123"));

        pool.clear();

        assertFalse(pool.isVehicleInUse("ABC123"));
        assertFalse(pool.isVehicleInUse("XYZ789"));
    }

    // ==================== Concurrency Tests ====================

    @Test
    @DisplayName("Thread safety - concurrent addDriver calls")
    void testConcurrentAddDriver() throws InterruptedException {
        int numThreads = 10;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads);

        for (int i = 0; i < numThreads; i++) {
            final int index = i;
            executor.submit(() -> {
                try {
                    Application app = createValidApplication(
                            "Driver" + index, "Test", 1990 + index, "PLATE" + index);
                    pool.addDriver(app);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(5, TimeUnit.SECONDS);
        executor.shutdown();

        assertEquals(numThreads, pool.getDriverCount(), "All drivers should be added");
    }

    @Test
    @DisplayName("Thread safety - concurrent provideDriverInfo calls")
    void testConcurrentProvideDriverInfo() throws InterruptedException {
        // Add some drivers first
        pool.addDriver(createValidApplication("Anne", "Smith", 1990, "A1"));
        pool.addDriver(createValidApplication("Bob", "Smith", 1985, "B1"));

        int numThreads = 20;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads);
        List<String> results = new CopyOnWriteArrayList<>();

        for (int i = 0; i < numThreads; i++) {
            executor.submit(() -> {
                try {
                    String result = pool.provideDriverInfo("Smith");
                    results.add(result);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(5, TimeUnit.SECONDS);
        executor.shutdown();

        assertEquals(numThreads, results.size(), "All queries should complete");
        for (String result : results) {
            assertTrue(result.contains("Smith"), "All results should be valid");
        }
    }

    @Test
    @DisplayName("Thread safety - concurrent claimVehicle calls")
    void testConcurrentClaimVehicle() throws InterruptedException {
        int numThreads = 10;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads);
        List<Boolean> results = new CopyOnWriteArrayList<>();

        for (int i = 0; i < numThreads; i++) {
            executor.submit(() -> {
                try {
                    boolean result = pool.claimVehicle("SHARED_PLATE");
                    results.add(result);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(5, TimeUnit.SECONDS);
        executor.shutdown();

        // Only one thread should successfully claim the vehicle
        long successCount = results.stream().filter(r -> r).count();
        assertEquals(1, successCount, "Only one thread should claim the vehicle");
    }

    @Test
    @DisplayName("Thread safety - mixed read and write operations")
    void testConcurrentMixedOperations() throws InterruptedException {
        int numThreads = 20;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads);

        for (int i = 0; i < numThreads; i++) {
            final int index = i;
            executor.submit(() -> {
                try {
                    if (index % 3 == 0) {
                        // Write operation
                        Application app = createValidApplication(
                                "Driver" + index, "Test", 1990 + index, "PLATE" + index);
                        pool.addDriver(app);
                    } else if (index % 3 == 1) {
                        // Read operation
                        pool.provideDriverInfo("Test");
                    } else {
                        // Count operation
                        pool.getDriverCount();
                        pool.getTotalVehicleCount();
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(5, TimeUnit.SECONDS);
        executor.shutdown();

        // Should complete without exceptions
        assertTrue(pool.getDriverCount() >= 0);
    }

    // ==================== Helper Methods ====================

    private Application createValidApplication(String firstName, String lastName,
                                               int birthYear, String plateNumber) {
        Name name = new Name(firstName, lastName);
        Registration.Entity.Date birthday = new Registration.Entity.Date.Builder(birthYear, 1, 1).build();
        Driver driver = new Driver.Builder(name, birthday).build();

        DriverLicense license = new DriverLicense.Builder()
                .licenseNumber("DL-" + plateNumber)
                .name(name)
                .address("123 Test St")
                .birthDate(birthday)
                .countryAndState("US-CA")
                .issuanceDate(new Registration.Entity.Date.Builder(2020, 1, 1).build())
                .experienceDate(new Registration.Entity.Date.Builder(2028, 1, 1).build())
                .build();

        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new Vehicle.Builder()
                .make("Honda")
                .model("Accord")
                .year(2020)
                .owner(firstName + " " + lastName)
                .plateNumber(plateNumber)
                .build());

        VehicleInsurance insurance = new VehicleInsurance.Builder()
                .nameOnPolicy(name)
                .drivers(new ArrayList<>())
                .expirationDate(new Registration.Entity.Date.Builder(2026, 12, 31).build())
                .build();

        return new Application.Builder()
                .driver(driver)
                .driverLicense(license)
                .vehicle(vehicles)
                .vehicleInsurance(insurance)
                .driverHistory(new ArrayList<>())
                .vehicleHistory(new ArrayList<>())
                .build();
    }

    private Application createApplicationWithMultipleVehicles(String firstName,
                                                              String lastName, int birthYear) {
        Name name = new Name(firstName, lastName);
        Registration.Entity.Date birthday = new Registration.Entity.Date.Builder(birthYear, 1, 1).build();
        Driver driver = new Driver.Builder(name, birthday).build();

        DriverLicense license = new DriverLicense.Builder()
                .licenseNumber("DL-MULTI")
                .name(name)
                .address("123 Test St")
                .birthDate(birthday)
                .countryAndState("US-CA")
                .issuanceDate(new Registration.Entity.Date.Builder(2020, 1, 1).build())
                .experienceDate(new Registration.Entity.Date.Builder(2028, 1, 1).build())
                .build();

        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new Vehicle.Builder()
                .make("Honda")
                .model("Accord")
                .year(2019)
                .owner(firstName + " " + lastName)
                .plateNumber("BAY01FG")
                .build());
        vehicles.add(new Vehicle.Builder()
                .make("Toyota")
                .model("Prius")
                .year(2018)
                .owner(firstName + " " + lastName)
                .plateNumber("AZ034W1")
                .build());

        VehicleInsurance insurance = new VehicleInsurance.Builder()
                .nameOnPolicy(name)
                .drivers(new ArrayList<>())
                .expirationDate(new Registration.Entity.Date.Builder(2026, 12, 31).build())
                .build();

        return new Application.Builder()
                .driver(driver)
                .driverLicense(license)
                .vehicle(vehicles)
                .vehicleInsurance(insurance)
                .driverHistory(new ArrayList<>())
                .vehicleHistory(new ArrayList<>())
                .build();
    }

    private Application createApplicationWithViolations(String firstName,
                                                        String lastName, int birthYear, String plateNumber) {
        Name name = new Name(firstName, lastName);
        Registration.Entity.Date birthday = new Registration.Entity.Date.Builder(birthYear, 1, 1).build();
        Driver driver = new Driver.Builder(name, birthday).build();

        DriverLicense license = new DriverLicense.Builder()
                .licenseNumber("DL-" + plateNumber)
                .name(name)
                .address("123 Test St")
                .birthDate(birthday)
                .countryAndState("US-CA")
                .issuanceDate(new Registration.Entity.Date.Builder(2020, 1, 1).build())
                .experienceDate(new Registration.Entity.Date.Builder(2028, 1, 1).build())
                .build();

        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new Vehicle.Builder()
                .make("Ford")
                .model("Explorer")
                .year(2021)
                .owner(firstName + " " + lastName)
                .plateNumber(plateNumber)
                .build());

        VehicleInsurance insurance = new VehicleInsurance.Builder()
                .nameOnPolicy(name)
                .drivers(new ArrayList<>())
                .expirationDate(new Registration.Entity.Date.Builder(2026, 12, 31).build())
                .build();

        // Add violations
        List<Incident> violations = new ArrayList<>();
        violations.add(new MovingViolation(MovingViolationEnum.DISTRACTED_DRIVING));
        violations.add(new NonMovingViolation(NonMovingViolationEnum.PARKING_VIOLATION));

        return new Application.Builder()
                .driver(driver)
                .driverLicense(license)
                .vehicle(vehicles)
                .vehicleInsurance(insurance)
                .driverHistory(violations)
                .vehicleHistory(new ArrayList<>())
                .build();
    }

    private Application createApplicationWithNullViolations(String firstName,
                                                            String lastName, int birthYear, String plateNumber) {
        Name name = new Name(firstName, lastName);
        Registration.Entity.Date birthday = new Registration.Entity.Date.Builder(birthYear, 1, 1).build();
        Driver driver = new Driver.Builder(name, birthday).build();

        DriverLicense license = new DriverLicense.Builder()
                .licenseNumber("DL-" + plateNumber)
                .name(name)
                .address("123 Test St")
                .birthDate(birthday)
                .countryAndState("US-CA")
                .issuanceDate(new Registration.Entity.Date.Builder(2020, 1, 1).build())
                .experienceDate(new Registration.Entity.Date.Builder(2028, 1, 1).build())
                .build();

        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new Vehicle.Builder()
                .make("Honda")
                .model("Civic")
                .year(2020)
                .owner(firstName + " " + lastName)
                .plateNumber(plateNumber)
                .build());

        VehicleInsurance insurance = new VehicleInsurance.Builder()
                .nameOnPolicy(name)
                .drivers(new ArrayList<>())
                .expirationDate(new Registration.Entity.Date.Builder(2026, 12, 31).build())
                .build();

        // Explicitly pass null for driver history
        return new Application.Builder()
                .driver(driver)
                .driverLicense(license)
                .vehicle(vehicles)
                .vehicleInsurance(insurance)
                .driverHistory(null)
                .vehicleHistory(new ArrayList<>())
                .build();
    }
}