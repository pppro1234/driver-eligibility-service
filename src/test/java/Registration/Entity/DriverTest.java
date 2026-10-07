package Registration.Entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests driver construction and field access.
 */
class DriverTest {

    private Name testName;
    private Name alternativeName;
    private Date testBirthday;
    private Date alternativeBirthday;
    private DriverLicense testLicense;
    private DriverLicense alternativeLicense;

    /**
     * Sets up test fixtures before each test method.
     * Creates test objects for Name, Date, and DriverLicense using proper Builder patterns.
     */
    @BeforeEach
    void setUp() {
        // Create test names
        testName = new Name("John", "Doe");
        alternativeName = new Name("Jane", "Smith");

        // Create test birthdays (year, month, day)
        testBirthday = new Date.Builder(1990, 1, 15).build();
        alternativeBirthday = new Date.Builder(1985, 6, 20).build();

        // Create test licenses with complete information
        testLicense = new DriverLicense.Builder()
                .licenseNumber("DL123456")
                .name(testName)
                .address("123 Main St, San Francisco, CA 94102")
                .birthDate(testBirthday)
                .countryAndState("USA-CA")
                .issuanceDate(new Date.Builder(2020, 1, 1).build())
                .experienceDate(new Date.Builder(2028, 1, 1).build())
                .build();

        alternativeLicense = new DriverLicense.Builder()
                .licenseNumber("DL789012")
                .name(alternativeName)
                .address("456 Oak Ave, Los Angeles, CA 90001")
                .birthDate(alternativeBirthday)
                .countryAndState("USA-NY")
                .issuanceDate(new Date.Builder(2019, 6, 1).build())
                .experienceDate(new Date.Builder(2027, 6, 1).build())
                .build();
    }

    // ============ Builder Constructor Tests - Required Fields ============

    /**
     * Tests creating a Driver with required fields only (no license).
     * Verifies that a driver can be created with just name and birthday.
     */
    @Test
    void testBuilderWithRequiredFieldsOnly() {
        Driver driver = new Driver.Builder(testName, testBirthday).build();

        assertNotNull(driver);
        assertEquals(testName, driver.getName());
        assertEquals(testBirthday, driver.getBirthday());
        assertNull(driver.getLicense());
    }

    /**
     * Tests creating a Driver with all fields including license.
     * Verifies that the builder correctly sets all properties.
     */
    @Test
    void testBuilderWithAllFields() {
        Driver driver = new Driver.Builder(testName, testBirthday)
                .driverLicense(testLicense)
                .build();

        assertNotNull(driver);
        assertEquals(testName, driver.getName());
        assertEquals(testBirthday, driver.getBirthday());
        assertEquals(testLicense, driver.getLicense());
    }

    /**
     * Tests Builder constructor with null name.
     * Verifies that null name is accepted (no validation).
     */
    @Test
    void testBuilderConstructorWithNullName() {
        Driver driver = new Driver.Builder(null, testBirthday).build();

        assertNotNull(driver);
        assertNull(driver.getName());
        assertEquals(testBirthday, driver.getBirthday());
    }

    /**
     * Tests Builder constructor with null birthday.
     * Verifies that null birthday is accepted (no validation).
     */
    @Test
    void testBuilderConstructorWithNullBirthday() {
        Driver driver = new Driver.Builder(testName, null).build();

        assertNotNull(driver);
        assertEquals(testName, driver.getName());
        assertNull(driver.getBirthday());
    }

    /**
     * Tests Builder constructor with both required fields null.
     */
    @Test
    void testBuilderConstructorWithBothRequiredFieldsNull() {
        Driver driver = new Driver.Builder(null, null).build();

        assertNotNull(driver);
        assertNull(driver.getName());
        assertNull(driver.getBirthday());
        assertNull(driver.getLicense());
    }

    // ============ Builder Method Chaining Tests ============

    /**
     * Tests builder name() method chaining.
     * Verifies that the name() method returns the builder and updates the field.
     */
    @Test
    void testBuilderNameMethodChaining() {
        Driver driver = new Driver.Builder(testName, testBirthday)
                .name(alternativeName)
                .build();

        assertEquals(alternativeName, driver.getName());
        assertEquals(testBirthday, driver.getBirthday());
    }

    /**
     * Tests builder birthday() method chaining.
     * Verifies that the birthday() method returns the builder and updates the field.
     */
    @Test
    void testBuilderBirthdayMethodChaining() {
        Driver driver = new Driver.Builder(testName, testBirthday)
                .birthday(alternativeBirthday)
                .build();

        assertEquals(testName, driver.getName());
        assertEquals(alternativeBirthday, driver.getBirthday());
    }

    /**
     * Tests builder driverLicense() method chaining.
     * Verifies that the driverLicense() method returns the builder and sets the license.
     */
    @Test
    void testBuilderDriverLicenseMethodChaining() {
        Driver driver = new Driver.Builder(testName, testBirthday)
                .driverLicense(testLicense)
                .build();

        assertEquals(testLicense, driver.getLicense());
    }

    /**
     * Tests complete builder method chaining with all methods.
     * Verifies that all builder methods can be chained together.
     */
    @Test
    void testBuilderCompleteMethodChaining() {
        Driver driver = new Driver.Builder(testName, testBirthday)
                .name(alternativeName)
                .birthday(alternativeBirthday)
                .driverLicense(testLicense)
                .build();

        assertEquals(alternativeName, driver.getName());
        assertEquals(alternativeBirthday, driver.getBirthday());
        assertEquals(testLicense, driver.getLicense());
    }

    /**
     * Tests builder with null license explicitly set.
     * Verifies that setting a null license works correctly.
     */
    @Test
    void testBuilderWithNullLicense() {
        Driver driver = new Driver.Builder(testName, testBirthday)
                .driverLicense(null)
                .build();

        assertNull(driver.getLicense());
    }

    /**
     * Tests builder method chaining returns builder instance.
     */
    @Test
    void testBuilderMethodsReturnBuilder() {
        Driver.Builder builder = new Driver.Builder(testName, testBirthday);

        assertSame(builder, builder.name(alternativeName));
        assertSame(builder, builder.birthday(alternativeBirthday));
        assertSame(builder, builder.driverLicense(testLicense));
    }

    /**
     * Tests builder with different method ordering.
     */
    @Test
    void testBuilderWithDifferentMethodOrdering() {
        Driver driver = new Driver.Builder(testName, testBirthday)
                .driverLicense(testLicense)
                .birthday(alternativeBirthday)
                .name(alternativeName)
                .build();

        assertEquals(alternativeName, driver.getName());
        assertEquals(alternativeBirthday, driver.getBirthday());
        assertEquals(testLicense, driver.getLicense());
    }

    /**
     * Tests builder overwriting required fields through method chaining.
     */
    @Test
    void testBuilderOverwritingRequiredFields() {
        Driver driver = new Driver.Builder(testName, testBirthday)
                .name(alternativeName)
                .birthday(alternativeBirthday)
                .build();

        // Should use the overwritten values
        assertEquals(alternativeName, driver.getName());
        assertEquals(alternativeBirthday, driver.getBirthday());
    }

    // ============ Getter Tests ============

    /**
     * Tests getName() method.
     * Verifies that the name getter returns the correct value.
     */
    @Test
    void testGetName() {
        Driver driver = new Driver.Builder(testName, testBirthday).build();
        assertEquals(testName, driver.getName());
    }

    /**
     * Tests getBirthday() method.
     * Verifies that the birthday getter returns the correct value.
     */
    @Test
    void testGetBirthday() {
        Driver driver = new Driver.Builder(testName, testBirthday).build();
        assertEquals(testBirthday, driver.getBirthday());
    }

    /**
     * Tests getLicense() method with a set license.
     * Verifies that the license getter returns the correct value.
     */
    @Test
    void testGetLicenseWithValue() {
        Driver driver = new Driver.Builder(testName, testBirthday)
                .driverLicense(testLicense)
                .build();
        assertEquals(testLicense, driver.getLicense());
    }

    /**
     * Tests getLicense() method when license is null.
     * Verifies that the license getter returns null when no license is set.
     */
    @Test
    void testGetLicenseWhenNull() {
        Driver driver = new Driver.Builder(testName, testBirthday).build();
        assertNull(driver.getLicense());
    }

    // ============ Setter Tests ============

    /**
     * Tests setName() method.
     * Verifies that the name setter correctly updates the name field.
     */
    @Test
    void testSetName() {
        Driver driver = new Driver.Builder(testName, testBirthday).build();

        driver.setName(alternativeName);

        assertEquals(alternativeName, driver.getName());
    }

    /**
     * Tests setName() with null value.
     */
    @Test
    void testSetNameToNull() {
        Driver driver = new Driver.Builder(testName, testBirthday).build();

        driver.setName(null);

        assertNull(driver.getName());
    }

    /**
     * Tests setBirthday() method.
     * Verifies that the birthday setter correctly updates the birthday field.
     */
    @Test
    void testSetBirthday() {
        Driver driver = new Driver.Builder(testName, testBirthday).build();

        driver.setBirthday(alternativeBirthday);

        assertEquals(alternativeBirthday, driver.getBirthday());
    }

    /**
     * Tests setBirthday() with null value.
     */
    @Test
    void testSetBirthdayToNull() {
        Driver driver = new Driver.Builder(testName, testBirthday).build();

        driver.setBirthday(null);

        assertNull(driver.getBirthday());
    }

    /**
     * Tests setLicense() method with a valid license.
     * Verifies that the license setter correctly updates the license field.
     */
    @Test
    void testSetLicense() {
        Driver driver = new Driver.Builder(testName, testBirthday).build();

        driver.setLicense(testLicense);

        assertEquals(testLicense, driver.getLicense());
    }

    /**
     * Tests setLicense() method to update an existing license.
     * Verifies that the license can be changed from one value to another.
     */
    @Test
    void testSetLicenseUpdateExisting() {
        Driver driver = new Driver.Builder(testName, testBirthday)
                .driverLicense(testLicense)
                .build();

        driver.setLicense(alternativeLicense);

        assertEquals(alternativeLicense, driver.getLicense());
    }

    /**
     * Tests setLicense() method with null value.
     * Verifies that the license can be set to null.
     */
    @Test
    void testSetLicenseToNull() {
        Driver driver = new Driver.Builder(testName, testBirthday)
                .driverLicense(testLicense)
                .build();

        driver.setLicense(null);

        assertNull(driver.getLicense());
    }

    // ============ Integration Tests ============

    /**
     * Tests multiple setter calls in sequence.
     * Verifies that all setters work correctly when called multiple times.
     */
    @Test
    void testMultipleSetterCalls() {
        Driver driver = new Driver.Builder(testName, testBirthday).build();

        driver.setName(alternativeName);
        driver.setBirthday(alternativeBirthday);
        driver.setLicense(testLicense);

        assertEquals(alternativeName, driver.getName());
        assertEquals(alternativeBirthday, driver.getBirthday());
        assertEquals(testLicense, driver.getLicense());
    }

    /**
     * Tests creating a driver and then modifying all fields.
     * Verifies complete lifecycle of builder creation and subsequent modifications.
     */
    @Test
    void testCompleteDriverLifecycle() {
        // Create driver with builder
        Driver driver = new Driver.Builder(testName, testBirthday)
                .driverLicense(testLicense)
                .build();

        // Verify initial state
        assertEquals(testName, driver.getName());
        assertEquals(testBirthday, driver.getBirthday());
        assertEquals(testLicense, driver.getLicense());

        // Modify all fields
        driver.setName(alternativeName);
        driver.setBirthday(alternativeBirthday);
        driver.setLicense(alternativeLicense);

        // Verify modified state
        assertEquals(alternativeName, driver.getName());
        assertEquals(alternativeBirthday, driver.getBirthday());
        assertEquals(alternativeLicense, driver.getLicense());
    }

    /**
     * Tests that builder creates independent instances.
     * Verifies that multiple drivers created from the same builder setup are independent.
     */
    @Test
    void testBuilderCreatesIndependentInstances() {
        Driver.Builder builder = new Driver.Builder(testName, testBirthday);

        Driver driver1 = builder.build();
        Driver driver2 = builder.build();

        // Modify driver1
        driver1.setName(alternativeName);

        // Verify driver2 is not affected
        assertEquals(testName, driver2.getName());
        assertNotSame(driver1, driver2);
    }

    /**
     * Tests builder overwriting required fields through method chaining.
     */
    @Test
    void testBuilderOverwritingRequiredFieldsThroughChaining() {
        Driver driver = new Driver.Builder(testName, testBirthday)
                .name(alternativeName)
                .birthday(alternativeBirthday)
                .build();

        // Should use the overwritten values
        assertEquals(alternativeName, driver.getName());
        assertEquals(alternativeBirthday, driver.getBirthday());
    }

    /**
     * Tests creating driver with minimal information.
     */
    @Test
    void testDriverWithMinimalInformation() {
        Name minimalName = new Name("Alice", "Williams");
        Date minimalBirthday = new Date.Builder(1992, 3, 10).build();

        Driver driver = new Driver.Builder(minimalName, minimalBirthday).build();

        assertNotNull(driver);
        assertEquals(minimalName, driver.getName());
        assertEquals(minimalBirthday, driver.getBirthday());
        assertNull(driver.getLicense());
    }

    /**
     * Tests creating driver with complete information.
     */
    @Test
    void testDriverWithCompleteInformation() {
        Driver driver = new Driver.Builder(testName, testBirthday)
                .driverLicense(testLicense)
                .build();

        assertNotNull(driver);
        assertNotNull(driver.getName());
        assertNotNull(driver.getBirthday());
        assertNotNull(driver.getLicense());
    }

    /**
     * Tests that getters return same references (no defensive copying).
     */
    @Test
    void testGettersReturnSameReferences() {
        Driver driver = new Driver.Builder(testName, testBirthday)
                .driverLicense(testLicense)
                .build();

        assertSame(testName, driver.getName());
        assertSame(testBirthday, driver.getBirthday());
        assertSame(testLicense, driver.getLicense());
    }

    /**
     * Tests realistic driver creation scenario for ride share application.
     */
    @Test
    void testRealisticDriverCreationForRideShare() {
        // Create a realistic driver for ride share registration
        Name driverName = new Name("Michael", "Rodriguez");
        Date driverBirthday = new Date.Builder(1987, 7, 22).build();

        DriverLicense driverLicense = new DriverLicense.Builder()
                .licenseNumber("CA87654321")
                .name(driverName)
                .address("567 Market Street, San Francisco, CA 94103")
                .birthDate(driverBirthday)
                .countryAndState("USA-CA")
                .issuanceDate(new Date.Builder(2018, 7, 22).build())
                .experienceDate(new Date.Builder(2026, 7, 22).build())
                .build();

        Driver driver = new Driver.Builder(driverName, driverBirthday)
                .driverLicense(driverLicense)
                .build();

        // Verify all information is correctly set
        assertEquals("Michael", driver.getName().getFirstName());
        assertEquals("Rodriguez", driver.getName().getLastName());
        assertEquals(1987, driver.getBirthday().getYear());
        assertEquals(7, driver.getBirthday().getMonth());
        assertEquals(22, driver.getBirthday().getDay());
        assertEquals("CA87654321", driver.getLicense().getLicenseNumber());
        assertEquals("USA-CA", driver.getLicense().getCountryAndState());
    }

    /**
     * Tests creating multiple drivers with different data.
     */
    @Test
    void testMultipleDriversWithDifferentData() {
        Driver driver1 = new Driver.Builder(
                new Name("John", "Doe"),
                new Date.Builder(1990, 1, 15).build()
        ).build();

        Driver driver2 = new Driver.Builder(
                new Name("Jane", "Smith"),
                new Date.Builder(1985, 6, 20).build()
        ).build();

        Driver driver3 = new Driver.Builder(
                new Name("Bob", "Johnson"),
                new Date.Builder(1995, 12, 10).build()
        ).build();

        // All should be distinct instances
        assertNotSame(driver1, driver2);
        assertNotSame(driver2, driver3);
        assertNotSame(driver1, driver3);

        // All should have different data
        assertNotEquals(driver1.getName().getFirstName(), driver2.getName().getFirstName());
        assertNotEquals(driver2.getBirthday().getYear(), driver3.getBirthday().getYear());
    }

    /**
     * Tests setter and getter consistency for all fields.
     */
    @Test
    void testSetterGetterConsistency() {
        Driver driver = new Driver.Builder(testName, testBirthday).build();

        // Test name setter/getter
        driver.setName(alternativeName);
        assertEquals(alternativeName, driver.getName());

        // Test birthday setter/getter
        driver.setBirthday(alternativeBirthday);
        assertEquals(alternativeBirthday, driver.getBirthday());

        // Test license setter/getter
        driver.setLicense(testLicense);
        assertEquals(testLicense, driver.getLicense());
    }

    /**
     * Tests that Driver object maintains state correctly over multiple operations.
     */
    @Test
    void testDriverStateMaintenance() {
        Driver driver = new Driver.Builder(testName, testBirthday).build();

        // Initial state
        assertEquals(testName, driver.getName());
        assertNull(driver.getLicense());

        // Add license
        driver.setLicense(testLicense);
        assertEquals(testLicense, driver.getLicense());
        assertEquals(testName, driver.getName()); // Name unchanged

        // Change name
        driver.setName(alternativeName);
        assertEquals(alternativeName, driver.getName());
        assertEquals(testLicense, driver.getLicense()); // License unchanged
    }

    /**
     * Tests driver with matching license information.
     */
    @Test
    void testDriverWithMatchingLicenseInformation() {
        Name driverName = new Name("Sarah", "Connor");
        Date birthday = new Date.Builder(1984, 5, 13).build();

        DriverLicense matchingLicense = new DriverLicense.Builder()
                .licenseNumber("CA11111111")
                .name(driverName)
                .birthDate(birthday)
                .countryAndState("USA-CA")
                .issuanceDate(new Date.Builder(2019, 5, 13).build())
                .experienceDate(new Date.Builder(2027, 5, 13).build())
                .build();

        Driver driver = new Driver.Builder(driverName, birthday)
                .driverLicense(matchingLicense)
                .build();

        // Verify name matches between driver and license
        assertEquals(driver.getName().getFirstName(),
                driver.getLicense().getName().getFirstName());
        assertEquals(driver.getName().getLastName(),
                driver.getLicense().getName().getLastName());

        // Verify birthday matches
        assertEquals(driver.getBirthday().getYear(),
                driver.getLicense().getBirthDate().getYear());
        assertEquals(driver.getBirthday().getMonth(),
                driver.getLicense().getBirthDate().getMonth());
        assertEquals(driver.getBirthday().getDay(),
                driver.getLicense().getBirthDate().getDay());
    }

    /**
     * Tests driver used in Application context.
     */
    @Test
    void testDriverInApplicationContext() {
        Driver driver = new Driver.Builder(testName, testBirthday)
                .driverLicense(testLicense)
                .build();

        Application application = new Application.Builder()
                .driver(driver)
                .driverLicense(testLicense)
                .build();

        assertSame(driver, application.getDriver());
        assertEquals(testName, application.getDriver().getName());
    }

    /**
     * Tests builder with all null values.
     */
    @Test
    void testBuilderWithAllNullValues() {
        Driver driver = new Driver.Builder(null, null)
                .name(null)
                .birthday(null)
                .driverLicense(null)
                .build();

        assertNotNull(driver); // Driver object created
        assertNull(driver.getName());
        assertNull(driver.getBirthday());
        assertNull(driver.getLicense());
    }

    /**
     * Tests driver creation for different age groups.
     */
    @Test
    void testDriversOfDifferentAges() {
        // Young driver (25 years old)
        Driver youngDriver = new Driver.Builder(
                new Name("Young", "Driver"),
                new Date.Builder(1999, 1, 1).build()
        ).build();

        // Middle-aged driver (40 years old)
        Driver middleDriver = new Driver.Builder(
                new Name("Middle", "Driver"),
                new Date.Builder(1984, 6, 15).build()
        ).build();

        // Senior driver (65 years old)
        Driver seniorDriver = new Driver.Builder(
                new Name("Senior", "Driver"),
                new Date.Builder(1959, 12, 31).build()
        ).build();

        // All valid drivers with different ages
        assertEquals(1999, youngDriver.getBirthday().getYear());
        assertEquals(1984, middleDriver.getBirthday().getYear());
        assertEquals(1959, seniorDriver.getBirthday().getYear());
    }

    /**
     * Tests updating driver information after initial creation.
     */
    @Test
    void testUpdatingDriverInformation() {
        // Initial driver creation
        Driver driver = new Driver.Builder(
                new Name("Old", "Name"),
                new Date.Builder(1990, 1, 1).build()
        ).build();

        // Update to new information (e.g., name change after marriage)
        Name newName = new Name("New", "Name");
        driver.setName(newName);

        assertEquals("New", driver.getName().getFirstName());
        assertEquals("Name", driver.getName().getLastName());
    }

    /**
     * Tests that setter changes are reflected in subsequent getter calls.
     */
    @Test
    void testSetterChangesReflectedInGetters() {
        Driver driver = new Driver.Builder(testName, testBirthday).build();

        // Initial state
        assertEquals(testName, driver.getName());

        // Change name
        driver.setName(alternativeName);

        // Verify change is reflected
        assertEquals(alternativeName, driver.getName());
        assertNotEquals(testName, driver.getName());
    }
}