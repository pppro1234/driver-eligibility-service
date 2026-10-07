package Registration.Entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests vehicle insurance construction and field access.
 */
class VehicleInsuranceTest {

    private Name testName;
    private List<Driver> testDrivers;
    private Date testExpirationDate;

    /**
     * Sets up test fixtures before each test method.
     */
    @BeforeEach
    void setUp() {
        testName = new Name("John", "Doe");

        Driver driver1 = new Driver.Builder(new Name("John", "Doe"),
                new Date.Builder(1990, 5, 15).build()).build();
        Driver driver2 = new Driver.Builder(new Name("Jane", "Doe"),
                new Date.Builder(1992, 8, 20).build()).build();
        testDrivers = new ArrayList<>(Arrays.asList(driver1, driver2));

        testExpirationDate = new Date.Builder(2025, 12, 31).build();
    }

    // ============ Builder Constructor Tests ============

    /**
     * Tests building with all fields set.
     */
    @Test
    void testBuilderWithAllFields() {
        VehicleInsurance insurance = new VehicleInsurance.Builder()
                .nameOnPolicy(testName)
                .drivers(testDrivers)
                .expirationDate(testExpirationDate)
                .build();

        assertNotNull(insurance);
        assertEquals(testName, insurance.getNameOnPolicy());
        assertEquals(testDrivers, insurance.getDrivers());
        assertEquals(testExpirationDate, insurance.getExpirationDate());
    }

    /**
     * Tests building with no fields set.
     */
    @Test
    void testBuilderWithNoFields() {
        VehicleInsurance insurance = new VehicleInsurance.Builder().build();

        assertNotNull(insurance);
        assertNull(insurance.getNameOnPolicy());
        assertNull(insurance.getDrivers());
        assertNull(insurance.getExpirationDate());
    }

    /**
     * Tests building with only nameOnPolicy set.
     */
    @Test
    void testBuilderWithOnlyNameOnPolicy() {
        VehicleInsurance insurance = new VehicleInsurance.Builder()
                .nameOnPolicy(testName)
                .build();

        assertNotNull(insurance);
        assertEquals(testName, insurance.getNameOnPolicy());
        assertNull(insurance.getDrivers());
        assertNull(insurance.getExpirationDate());
    }

    /**
     * Tests building with only drivers set.
     */
    @Test
    void testBuilderWithOnlyDrivers() {
        VehicleInsurance insurance = new VehicleInsurance.Builder()
                .drivers(testDrivers)
                .build();

        assertNotNull(insurance);
        assertNull(insurance.getNameOnPolicy());
        assertEquals(testDrivers, insurance.getDrivers());
        assertNull(insurance.getExpirationDate());
    }

    /**
     * Tests building with only expirationDate set.
     */
    @Test
    void testBuilderWithOnlyExpirationDate() {
        VehicleInsurance insurance = new VehicleInsurance.Builder()
                .expirationDate(testExpirationDate)
                .build();

        assertNotNull(insurance);
        assertNull(insurance.getNameOnPolicy());
        assertNull(insurance.getDrivers());
        assertEquals(testExpirationDate, insurance.getExpirationDate());
    }

    /**
     * Tests building with nameOnPolicy and drivers.
     */
    @Test
    void testBuilderWithNameAndDrivers() {
        VehicleInsurance insurance = new VehicleInsurance.Builder()
                .nameOnPolicy(testName)
                .drivers(testDrivers)
                .build();

        assertEquals(testName, insurance.getNameOnPolicy());
        assertEquals(testDrivers, insurance.getDrivers());
        assertNull(insurance.getExpirationDate());
    }

    /**
     * Tests building with nameOnPolicy and expirationDate.
     */
    @Test
    void testBuilderWithNameAndExpirationDate() {
        VehicleInsurance insurance = new VehicleInsurance.Builder()
                .nameOnPolicy(testName)
                .expirationDate(testExpirationDate)
                .build();

        assertEquals(testName, insurance.getNameOnPolicy());
        assertNull(insurance.getDrivers());
        assertEquals(testExpirationDate, insurance.getExpirationDate());
    }

    /**
     * Tests building with drivers and expirationDate.
     */
    @Test
    void testBuilderWithDriversAndExpirationDate() {
        VehicleInsurance insurance = new VehicleInsurance.Builder()
                .drivers(testDrivers)
                .expirationDate(testExpirationDate)
                .build();

        assertNull(insurance.getNameOnPolicy());
        assertEquals(testDrivers, insurance.getDrivers());
        assertEquals(testExpirationDate, insurance.getExpirationDate());
    }

    // ============ Builder Method Chaining Tests ============

    /**
     * Tests nameOnPolicy() method chaining.
     */
    @Test
    void testBuilderNameOnPolicyMethodChaining() {
        VehicleInsurance.Builder builder = new VehicleInsurance.Builder();
        VehicleInsurance.Builder result = builder.nameOnPolicy(testName);

        assertSame(builder, result);
    }

    /**
     * Tests drivers() method chaining.
     */
    @Test
    void testBuilderDriversMethodChaining() {
        VehicleInsurance.Builder builder = new VehicleInsurance.Builder();
        VehicleInsurance.Builder result = builder.drivers(testDrivers);

        assertSame(builder, result);
    }

    /**
     * Tests expirationDate() method chaining.
     */
    @Test
    void testBuilderExpirationDateMethodChaining() {
        VehicleInsurance.Builder builder = new VehicleInsurance.Builder();
        VehicleInsurance.Builder result = builder.expirationDate(testExpirationDate);

        assertSame(builder, result);
    }

    /**
     * Tests complete method chaining.
     */
    @Test
    void testBuilderCompleteMethodChaining() {
        VehicleInsurance insurance = new VehicleInsurance.Builder()
                .nameOnPolicy(testName)
                .drivers(testDrivers)
                .expirationDate(testExpirationDate)
                .build();

        assertNotNull(insurance);
        assertEquals(testName, insurance.getNameOnPolicy());
        assertEquals(testDrivers, insurance.getDrivers());
        assertEquals(testExpirationDate, insurance.getExpirationDate());
    }

    /**
     * Tests builder with different method ordering.
     */
    @Test
    void testBuilderWithDifferentMethodOrder() {
        VehicleInsurance insurance = new VehicleInsurance.Builder()
                .expirationDate(testExpirationDate)
                .nameOnPolicy(testName)
                .drivers(testDrivers)
                .build();

        assertEquals(testName, insurance.getNameOnPolicy());
        assertEquals(testDrivers, insurance.getDrivers());
        assertEquals(testExpirationDate, insurance.getExpirationDate());
    }

    /**
     * Tests builder with null values explicitly set.
     */
    @Test
    void testBuilderWithNullValues() {
        VehicleInsurance insurance = new VehicleInsurance.Builder()
                .nameOnPolicy(null)
                .drivers(null)
                .expirationDate(null)
                .build();

        assertNull(insurance.getNameOnPolicy());
        assertNull(insurance.getDrivers());
        assertNull(insurance.getExpirationDate());
    }

    /**
     * Tests builder overwriting values.
     */
    @Test
    void testBuilderOverwritingValues() {
        Name altName = new Name("Jane", "Smith");

        VehicleInsurance insurance = new VehicleInsurance.Builder()
                .nameOnPolicy(testName)
                .nameOnPolicy(altName)
                .build();

        assertEquals(altName, insurance.getNameOnPolicy());
    }

    // ============ Getter Tests ============

    /**
     * Tests getNameOnPolicy() returns correct value.
     */
    @Test
    void testGetNameOnPolicy() {
        VehicleInsurance insurance = new VehicleInsurance.Builder()
                .nameOnPolicy(testName)
                .build();

        assertEquals(testName, insurance.getNameOnPolicy());
    }

    /**
     * Tests getDrivers() returns correct value.
     */
    @Test
    void testGetDrivers() {
        VehicleInsurance insurance = new VehicleInsurance.Builder()
                .drivers(testDrivers)
                .build();

        assertEquals(testDrivers, insurance.getDrivers());
    }

    /**
     * Tests getExpirationDate() returns correct value.
     */
    @Test
    void testGetExpirationDate() {
        VehicleInsurance insurance = new VehicleInsurance.Builder()
                .expirationDate(testExpirationDate)
                .build();

        assertEquals(testExpirationDate, insurance.getExpirationDate());
    }

    /**
     * Tests getters return null when not set.
     */
    @Test
    void testGettersReturnNullWhenNotSet() {
        VehicleInsurance insurance = new VehicleInsurance.Builder().build();

        assertNull(insurance.getNameOnPolicy());
        assertNull(insurance.getDrivers());
        assertNull(insurance.getExpirationDate());
    }

    // ============ Setter Tests ============

    /**
     * Tests setNameOnPolicy() updates the value.
     */
    @Test
    void testSetNameOnPolicy() {
        VehicleInsurance insurance = new VehicleInsurance.Builder().build();

        insurance.setNameOnPolicy(testName);

        assertEquals(testName, insurance.getNameOnPolicy());
    }

    /**
     * Tests setDrivers() updates the value.
     */
    @Test
    void testSetDrivers() {
        VehicleInsurance insurance = new VehicleInsurance.Builder().build();

        insurance.setDrivers(testDrivers);

        assertEquals(testDrivers, insurance.getDrivers());
    }

    /**
     * Tests setExpirationDate() updates the value.
     */
    @Test
    void testSetExpirationDate() {
        VehicleInsurance insurance = new VehicleInsurance.Builder().build();

        insurance.setExpirationDate(testExpirationDate);

        assertEquals(testExpirationDate, insurance.getExpirationDate());
    }

    /**
     * Tests setters with null values.
     */
    @Test
    void testSettersWithNull() {
        VehicleInsurance insurance = new VehicleInsurance.Builder()
                .nameOnPolicy(testName)
                .drivers(testDrivers)
                .expirationDate(testExpirationDate)
                .build();

        insurance.setNameOnPolicy(null);
        insurance.setDrivers(null);
        insurance.setExpirationDate(null);

        assertNull(insurance.getNameOnPolicy());
        assertNull(insurance.getDrivers());
        assertNull(insurance.getExpirationDate());
    }

    /**
     * Tests setters overwrite existing values.
     */
    @Test
    void testSettersOverwriteExistingValues() {
        VehicleInsurance insurance = new VehicleInsurance.Builder()
                .nameOnPolicy(testName)
                .drivers(testDrivers)
                .expirationDate(testExpirationDate)
                .build();

        Name newName = new Name("Alice", "Johnson");
        List<Driver> newDrivers = new ArrayList<>();
        Date newDate = new Date.Builder(2026, 6, 30).build();

        insurance.setNameOnPolicy(newName);
        insurance.setDrivers(newDrivers);
        insurance.setExpirationDate(newDate);

        assertEquals(newName, insurance.getNameOnPolicy());
        assertEquals(newDrivers, insurance.getDrivers());
        assertEquals(newDate, insurance.getExpirationDate());
    }

    // ============ Empty and Single Driver Lists Tests ============

    /**
     * Tests with empty driver list.
     */
    @Test
    void testWithEmptyDriverList() {
        List<Driver> emptyDrivers = new ArrayList<>();

        VehicleInsurance insurance = new VehicleInsurance.Builder()
                .drivers(emptyDrivers)
                .build();

        assertNotNull(insurance.getDrivers());
        assertTrue(insurance.getDrivers().isEmpty());
    }

    /**
     * Tests with single driver.
     */
    @Test
    void testWithSingleDriver() {
        Driver singleDriver = new Driver.Builder(testName,
                new Date.Builder(1990, 1, 1).build()).build();
        List<Driver> singleDriverList = Collections.singletonList(singleDriver);

        VehicleInsurance insurance = new VehicleInsurance.Builder()
                .drivers(singleDriverList)
                .build();

        assertEquals(1, insurance.getDrivers().size());
        assertEquals(singleDriver, insurance.getDrivers().get(0));
    }

    /**
     * Tests with multiple drivers.
     */
    @Test
    void testWithMultipleDrivers() {
        VehicleInsurance insurance = new VehicleInsurance.Builder()
                .drivers(testDrivers)
                .build();

        assertEquals(2, insurance.getDrivers().size());
    }

    // ============ Integration Tests ============

    /**
     * Tests complete lifecycle: build, access, modify.
     */
    @Test
    void testCompleteLifecycle() {
        // Build
        VehicleInsurance insurance = new VehicleInsurance.Builder()
                .nameOnPolicy(testName)
                .drivers(testDrivers)
                .expirationDate(testExpirationDate)
                .build();

        // Access
        assertEquals(testName, insurance.getNameOnPolicy());
        assertEquals(2, insurance.getDrivers().size());

        // Modify
        Name newName = new Name("Bob", "Smith");
        insurance.setNameOnPolicy(newName);

        // Verify
        assertEquals(newName, insurance.getNameOnPolicy());
        assertEquals(testDrivers, insurance.getDrivers()); // Others unchanged
    }

    /**
     * Tests that builder creates independent instances.
     */
    @Test
    void testBuilderCreatesIndependentInstances() {
        VehicleInsurance.Builder builder = new VehicleInsurance.Builder()
                .nameOnPolicy(testName)
                .drivers(testDrivers);

        VehicleInsurance insurance1 = builder.build();
        VehicleInsurance insurance2 = builder.build();

        assertNotSame(insurance1, insurance2);

        // Modify insurance1
        insurance1.setNameOnPolicy(new Name("Different", "Person"));

        // insurance2 should have original value
        assertEquals(testName, insurance2.getNameOnPolicy());
    }

    /**
     * Tests modifying driver list after creation.
     */
    @Test
    void testModifyingDriverListAfterCreation() {
        VehicleInsurance insurance = new VehicleInsurance.Builder()
                .drivers(testDrivers)
                .build();

        assertEquals(2, insurance.getDrivers().size());

        // Modify the list
        Driver newDriver = new Driver.Builder(new Name("New", "Driver"),
                new Date.Builder(1995, 3, 10).build()).build();
        insurance.getDrivers().add(newDriver);

        assertEquals(3, insurance.getDrivers().size());
    }

    /**
     * Tests with different expiration dates.
     */
    @Test
    void testWithDifferentExpirationDates() {
        Date pastDate = new Date.Builder(2020, 1, 1).build();
        Date futureDate = new Date.Builder(2030, 12, 31).build();

        VehicleInsurance expiredInsurance = new VehicleInsurance.Builder()
                .expirationDate(pastDate)
                .build();

        VehicleInsurance validInsurance = new VehicleInsurance.Builder()
                .expirationDate(futureDate)
                .build();

        assertNotNull(expiredInsurance.getExpirationDate());
        assertNotNull(validInsurance.getExpirationDate());
        assertNotEquals(expiredInsurance.getExpirationDate(), validInsurance.getExpirationDate());
    }

    /**
     * Tests realistic insurance scenario.
     */
    @Test
    void testRealisticInsuranceScenario() {
        // Policy holder
        Name holder = new Name("John", "Smith");

        // Covered drivers (policy holder and spouse)
        Driver primaryDriver = new Driver.Builder(
                new Name("John", "Smith"),
                new Date.Builder(1980, 5, 15).build()
        ).build();

        Driver secondaryDriver = new Driver.Builder(
                new Name("Mary", "Smith"),
                new Date.Builder(1982, 8, 22).build()
        ).build();

        List<Driver> drivers = Arrays.asList(primaryDriver, secondaryDriver);

        // Expiration in one year
        Date expiration = new Date.Builder(2025, 11, 30).build();

        // Create insurance
        VehicleInsurance insurance = new VehicleInsurance.Builder()
                .nameOnPolicy(holder)
                .drivers(drivers)
                .expirationDate(expiration)
                .build();

        // Verify
        assertNotNull(insurance);
        assertEquals("John", insurance.getNameOnPolicy().getFirstName());
        assertEquals(2, insurance.getDrivers().size());
        assertEquals(2025, insurance.getExpirationDate().getYear());
    }

    /**
     * Tests updating all fields via setters.
     */
    @Test
    void testUpdatingAllFieldsViaSetters() {
        VehicleInsurance insurance = new VehicleInsurance.Builder().build();

        insurance.setNameOnPolicy(testName);
        insurance.setDrivers(testDrivers);
        insurance.setExpirationDate(testExpirationDate);

        assertEquals(testName, insurance.getNameOnPolicy());
        assertEquals(testDrivers, insurance.getDrivers());
        assertEquals(testExpirationDate, insurance.getExpirationDate());
    }

    /**
     * Tests getter and setter consistency.
     */
    @Test
    void testGetterSetterConsistency() {
        VehicleInsurance insurance = new VehicleInsurance.Builder().build();

        insurance.setNameOnPolicy(testName);
        assertEquals(testName, insurance.getNameOnPolicy());

        insurance.setDrivers(testDrivers);
        assertEquals(testDrivers, insurance.getDrivers());

        insurance.setExpirationDate(testExpirationDate);
        assertEquals(testExpirationDate, insurance.getExpirationDate());
    }

    /**
     * Tests that getters return same references (no defensive copying).
     */
    @Test
    void testGettersReturnSameReferences() {
        VehicleInsurance insurance = new VehicleInsurance.Builder()
                .nameOnPolicy(testName)
                .drivers(testDrivers)
                .expirationDate(testExpirationDate)
                .build();

        assertSame(testName, insurance.getNameOnPolicy());
        assertSame(testDrivers, insurance.getDrivers());
        assertSame(testExpirationDate, insurance.getExpirationDate());
    }

    /**
     * Tests that empty builder creates valid object.
     */
    @Test
    void testEmptyBuilderCreatesValidObject() {
        VehicleInsurance insurance = new VehicleInsurance.Builder().build();

        assertNotNull(insurance);
        // All fields should be null
        assertNull(insurance.getNameOnPolicy());
        assertNull(insurance.getDrivers());
        assertNull(insurance.getExpirationDate());
    }
}