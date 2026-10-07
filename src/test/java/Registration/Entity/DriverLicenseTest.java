package Registration.Entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests driver license construction and field access.
 */
class DriverLicenseTest {

    private String testLicenseNumber;
    private Name testName;
    private String testAddress;
    private Date testBirthDate;
    private String testCountryAndState;
    private Date testIssuanceDate;
    private Date testExpirationDate;

    /**
     * Sets up test fixtures before each test method.
     */
    @BeforeEach
    void setUp() {
        testLicenseNumber = "DL123456";
        testName = new Name("John", "Doe");
        testAddress = "123 Main St, San Francisco, CA 94102";
        testBirthDate = new Date.Builder(1990, 5, 15).build();
        testCountryAndState = "USA-CA";
        testIssuanceDate = new Date.Builder(2020, 1, 1).build();
        testExpirationDate = new Date.Builder(2028, 1, 1).build();
    }

    // ============ Builder Tests - All Fields ============

    /**
     * Tests building with all fields set.
     */
    @Test
    void testBuilderWithAllFields() {
        DriverLicense license = new DriverLicense.Builder()
                .licenseNumber(testLicenseNumber)
                .name(testName)
                .address(testAddress)
                .birthDate(testBirthDate)
                .countryAndState(testCountryAndState)
                .issuanceDate(testIssuanceDate)
                .experienceDate(testExpirationDate)
                .build();

        assertNotNull(license);
        assertEquals(testLicenseNumber, license.getLicenseNumber());
        assertEquals(testName, license.getName());
        assertEquals(testAddress, license.getAddress());
        assertEquals(testBirthDate, license.getBirthDate());
        assertEquals(testCountryAndState, license.getCountryAndState());
        assertEquals(testIssuanceDate, license.getIssuanceDate());
        assertEquals(testExpirationDate, license.getExpiratonDate());
    }

    /**
     * Tests building with no fields set.
     */
    @Test
    void testBuilderWithNoFields() {
        DriverLicense license = new DriverLicense.Builder().build();

        assertNotNull(license);
        assertNull(license.getLicenseNumber());
        assertNull(license.getName());
        assertNull(license.getAddress());
        assertNull(license.getBirthDate());
        assertNull(license.getCountryAndState());
        assertNull(license.getIssuanceDate());
        assertNull(license.getExpiratonDate());
    }

    // ============ Builder Method Chaining Tests ============

    /**
     * Tests licenseNumber() method chaining.
     */
    @Test
    void testBuilderLicenseNumberMethodChaining() {
        DriverLicense.Builder builder = new DriverLicense.Builder();
        DriverLicense.Builder result = builder.licenseNumber(testLicenseNumber);

        assertSame(builder, result);
    }

    /**
     * Tests name() method chaining.
     */
    @Test
    void testBuilderNameMethodChaining() {
        DriverLicense.Builder builder = new DriverLicense.Builder();
        DriverLicense.Builder result = builder.name(testName);

        assertSame(builder, result);
    }

    /**
     * Tests address() method chaining.
     */
    @Test
    void testBuilderAddressMethodChaining() {
        DriverLicense.Builder builder = new DriverLicense.Builder();
        DriverLicense.Builder result = builder.address(testAddress);

        assertSame(builder, result);
    }

    /**
     * Tests birthDate() method chaining.
     */
    @Test
    void testBuilderBirthDateMethodChaining() {
        DriverLicense.Builder builder = new DriverLicense.Builder();
        DriverLicense.Builder result = builder.birthDate(testBirthDate);

        assertSame(builder, result);
    }

    /**
     * Tests countryAndState() method chaining.
     */
    @Test
    void testBuilderCountryAndStateMethodChaining() {
        DriverLicense.Builder builder = new DriverLicense.Builder();
        DriverLicense.Builder result = builder.countryAndState(testCountryAndState);

        assertSame(builder, result);
    }

    /**
     * Tests issuanceDate() method chaining.
     */
    @Test
    void testBuilderIssuanceDateMethodChaining() {
        DriverLicense.Builder builder = new DriverLicense.Builder();
        DriverLicense.Builder result = builder.issuanceDate(testIssuanceDate);

        assertSame(builder, result);
    }

    /**
     * Tests experienceDate() method chaining.
     * Note: Parameter name is a typo (should be expirationDate).
     */
    @Test
    void testBuilderExperienceDateMethodChaining() {
        DriverLicense.Builder builder = new DriverLicense.Builder();
        DriverLicense.Builder result = builder.experienceDate(testExpirationDate);

        assertSame(builder, result);
    }

    /**
     * Tests complete method chaining.
     */
    @Test
    void testBuilderCompleteMethodChaining() {
        DriverLicense license = new DriverLicense.Builder()
                .licenseNumber(testLicenseNumber)
                .name(testName)
                .address(testAddress)
                .birthDate(testBirthDate)
                .countryAndState(testCountryAndState)
                .issuanceDate(testIssuanceDate)
                .experienceDate(testExpirationDate)
                .build();

        assertEquals(testLicenseNumber, license.getLicenseNumber());
        assertEquals(testExpirationDate, license.getExpiratonDate());
    }

    /**
     * Tests builder with different method ordering.
     */
    @Test
    void testBuilderWithDifferentMethodOrder() {
        DriverLicense license = new DriverLicense.Builder()
                .experienceDate(testExpirationDate)
                .address(testAddress)
                .licenseNumber(testLicenseNumber)
                .countryAndState(testCountryAndState)
                .birthDate(testBirthDate)
                .name(testName)
                .issuanceDate(testIssuanceDate)
                .build();

        assertEquals(testLicenseNumber, license.getLicenseNumber());
        assertEquals(testAddress, license.getAddress());
    }

    /**
     * Tests builder with null values explicitly set.
     */
    @Test
    void testBuilderWithNullValues() {
        DriverLicense license = new DriverLicense.Builder()
                .licenseNumber(null)
                .name(null)
                .address(null)
                .birthDate(null)
                .countryAndState(null)
                .issuanceDate(null)
                .experienceDate(null)
                .build();

        assertNull(license.getLicenseNumber());
        assertNull(license.getName());
        assertNull(license.getExpiratonDate());
    }

    // ============ Getter Tests ============

    /**
     * Tests getLicenseNumber() returns correct value.
     */
    @Test
    void testGetLicenseNumber() {
        DriverLicense license = new DriverLicense.Builder()
                .licenseNumber(testLicenseNumber)
                .build();

        assertEquals(testLicenseNumber, license.getLicenseNumber());
    }

    /**
     * Tests getName() returns correct value.
     */
    @Test
    void testGetName() {
        DriverLicense license = new DriverLicense.Builder()
                .name(testName)
                .build();

        assertEquals(testName, license.getName());
    }

    /**
     * Tests getAddress() returns correct value.
     */
    @Test
    void testGetAddress() {
        DriverLicense license = new DriverLicense.Builder()
                .address(testAddress)
                .build();

        assertEquals(testAddress, license.getAddress());
    }

    /**
     * Tests getBirthDate() returns correct value.
     */
    @Test
    void testGetBirthDate() {
        DriverLicense license = new DriverLicense.Builder()
                .birthDate(testBirthDate)
                .build();

        assertEquals(testBirthDate, license.getBirthDate());
    }

    /**
     * Tests getCountryAndState() returns correct value.
     */
    @Test
    void testGetCountryAndState() {
        DriverLicense license = new DriverLicense.Builder()
                .countryAndState(testCountryAndState)
                .build();

        assertEquals(testCountryAndState, license.getCountryAndState());
    }

    /**
     * Tests getIssuanceDate() returns correct value.
     */
    @Test
    void testGetIssuanceDate() {
        DriverLicense license = new DriverLicense.Builder()
                .issuanceDate(testIssuanceDate)
                .build();

        assertEquals(testIssuanceDate, license.getIssuanceDate());
    }

    /**
     * Tests getExpiratonDate() returns correct value.
     * Note: Method name contains typo (Expiraton vs Expiration).
     */
    @Test
    void testGetExpiratonDate() {
        DriverLicense license = new DriverLicense.Builder()
                .experienceDate(testExpirationDate)
                .build();

        assertEquals(testExpirationDate, license.getExpiratonDate());
    }

    // ============ Setter Tests ============

    /**
     * Tests setLicenseNumber() updates the value.
     */
    @Test
    void testSetLicenseNumber() {
        DriverLicense license = new DriverLicense.Builder().build();

        license.setLicenseNumber(testLicenseNumber);

        assertEquals(testLicenseNumber, license.getLicenseNumber());
    }

    /**
     * Tests setName() updates the value.
     */
    @Test
    void testSetName() {
        DriverLicense license = new DriverLicense.Builder().build();

        license.setName(testName);

        assertEquals(testName, license.getName());
    }

    /**
     * Tests setAddress() updates the value.
     */
    @Test
    void testSetAddress() {
        DriverLicense license = new DriverLicense.Builder().build();

        license.setAddress(testAddress);

        assertEquals(testAddress, license.getAddress());
    }

    /**
     * Tests setBirthDate() updates the value.
     */
    @Test
    void testSetBirthDate() {
        DriverLicense license = new DriverLicense.Builder().build();

        license.setBirthDate(testBirthDate);

        assertEquals(testBirthDate, license.getBirthDate());
    }

    /**
     * Tests setCountryAndState() updates the value.
     */
    @Test
    void testSetCountryAndState() {
        DriverLicense license = new DriverLicense.Builder().build();

        license.setCountryAndState(testCountryAndState);

        assertEquals(testCountryAndState, license.getCountryAndState());
    }

    /**
     * Tests setIssuanceDate() updates the value.
     */
    @Test
    void testSetIssuanceDate() {
        DriverLicense license = new DriverLicense.Builder().build();

        license.setIssuanceDate(testIssuanceDate);

        assertEquals(testIssuanceDate, license.getIssuanceDate());
    }

    /**
     * Tests setExpiratonDate() updates the value.
     * Note: Method name contains typo (Expiraton vs Expiration).
     */
    @Test
    void testSetExpiratonDate() {
        DriverLicense license = new DriverLicense.Builder().build();

        license.setExpiratonDate(testExpirationDate);

        assertEquals(testExpirationDate, license.getExpiratonDate());
    }

    /**
     * Tests setters with null values.
     */
    @Test
    void testSettersWithNull() {
        DriverLicense license = new DriverLicense.Builder()
                .licenseNumber(testLicenseNumber)
                .name(testName)
                .address(testAddress)
                .birthDate(testBirthDate)
                .countryAndState(testCountryAndState)
                .issuanceDate(testIssuanceDate)
                .experienceDate(testExpirationDate)
                .build();

        license.setLicenseNumber(null);
        license.setName(null);
        license.setAddress(null);
        license.setBirthDate(null);
        license.setCountryAndState(null);
        license.setIssuanceDate(null);
        license.setExpiratonDate(null);

        assertNull(license.getLicenseNumber());
        assertNull(license.getName());
        assertNull(license.getAddress());
        assertNull(license.getBirthDate());
        assertNull(license.getCountryAndState());
        assertNull(license.getIssuanceDate());
        assertNull(license.getExpiratonDate());
    }

    /**
     * Tests setters overwrite existing values.
     */
    @Test
    void testSettersOverwriteExistingValues() {
        DriverLicense license = new DriverLicense.Builder()
                .licenseNumber(testLicenseNumber)
                .name(testName)
                .build();

        String newLicenseNumber = "DL999999";
        Name newName = new Name("Jane", "Smith");

        license.setLicenseNumber(newLicenseNumber);
        license.setName(newName);

        assertEquals(newLicenseNumber, license.getLicenseNumber());
        assertEquals(newName, license.getName());
    }

    // ============ Integration Tests ============

    /**
     * Tests complete lifecycle: build, access, modify.
     */
    @Test
    void testCompleteLifecycle() {
        // Build
        DriverLicense license = new DriverLicense.Builder()
                .licenseNumber(testLicenseNumber)
                .name(testName)
                .address(testAddress)
                .birthDate(testBirthDate)
                .countryAndState(testCountryAndState)
                .issuanceDate(testIssuanceDate)
                .experienceDate(testExpirationDate)
                .build();

        // Access
        assertEquals(testLicenseNumber, license.getLicenseNumber());
        assertEquals(testName, license.getName());

        // Modify
        String newAddress = "456 Oak Ave, Los Angeles, CA 90001";
        license.setAddress(newAddress);

        // Verify
        assertEquals(newAddress, license.getAddress());
        assertEquals(testLicenseNumber, license.getLicenseNumber()); // Others unchanged
    }

    /**
     * Tests realistic driver license scenario.
     */
    @Test
    void testRealisticDriverLicense() {
        Name licenseName = new Name("Alice", "Johnson");
        Date birthDate = new Date.Builder(1988, 3, 15).build();
        Date issuanceDate = new Date.Builder(2020, 3, 15).build();
        Date expirationDate = new Date.Builder(2028, 3, 15).build();

        DriverLicense license = new DriverLicense.Builder()
                .licenseNumber("CA12345678")
                .name(licenseName)
                .address("789 Pine Street, San Francisco, CA 94102")
                .birthDate(birthDate)
                .countryAndState("USA-CA")
                .issuanceDate(issuanceDate)
                .experienceDate(expirationDate)
                .build();

        assertEquals("CA12345678", license.getLicenseNumber());
        assertEquals("Alice", license.getName().getFirstName());
        assertEquals("Johnson", license.getName().getLastName());
        assertEquals(1988, license.getBirthDate().getYear());
        assertEquals("USA-CA", license.getCountryAndState());
        assertEquals(2028, license.getExpiratonDate().getYear());
    }

    /**
     * Tests that builder creates independent instances.
     */
    @Test
    void testBuilderCreatesIndependentInstances() {
        DriverLicense.Builder builder = new DriverLicense.Builder()
                .licenseNumber(testLicenseNumber)
                .name(testName);

        DriverLicense license1 = builder.build();
        DriverLicense license2 = builder.build();

        assertNotSame(license1, license2);

        // Modify license1
        license1.setLicenseNumber("DIFFERENT");

        // license2 should have original value
        assertEquals(testLicenseNumber, license2.getLicenseNumber());
    }

    /**
     * Tests updating all fields via setters.
     */
    @Test
    void testUpdatingAllFieldsViaSetters() {
        DriverLicense license = new DriverLicense.Builder().build();

        license.setLicenseNumber(testLicenseNumber);
        license.setName(testName);
        license.setAddress(testAddress);
        license.setBirthDate(testBirthDate);
        license.setCountryAndState(testCountryAndState);
        license.setIssuanceDate(testIssuanceDate);
        license.setExpiratonDate(testExpirationDate);

        assertEquals(testLicenseNumber, license.getLicenseNumber());
        assertEquals(testName, license.getName());
        assertEquals(testAddress, license.getAddress());
        assertEquals(testBirthDate, license.getBirthDate());
        assertEquals(testCountryAndState, license.getCountryAndState());
        assertEquals(testIssuanceDate, license.getIssuanceDate());
        assertEquals(testExpirationDate, license.getExpiratonDate());
    }

    /**
     * Tests getter and setter consistency for all fields.
     */
    @Test
    void testGetterSetterConsistency() {
        DriverLicense license = new DriverLicense.Builder().build();

        license.setLicenseNumber(testLicenseNumber);
        assertEquals(testLicenseNumber, license.getLicenseNumber());

        license.setName(testName);
        assertEquals(testName, license.getName());

        license.setAddress(testAddress);
        assertEquals(testAddress, license.getAddress());

        license.setBirthDate(testBirthDate);
        assertEquals(testBirthDate, license.getBirthDate());

        license.setCountryAndState(testCountryAndState);
        assertEquals(testCountryAndState, license.getCountryAndState());

        license.setIssuanceDate(testIssuanceDate);
        assertEquals(testIssuanceDate, license.getIssuanceDate());

        license.setExpiratonDate(testExpirationDate);
        assertEquals(testExpirationDate, license.getExpiratonDate());
    }

    /**
     * Tests that getters return same references (no defensive copying).
     */
    @Test
    void testGettersReturnSameReferences() {
        DriverLicense license = new DriverLicense.Builder()
                .name(testName)
                .birthDate(testBirthDate)
                .issuanceDate(testIssuanceDate)
                .experienceDate(testExpirationDate)
                .build();

        assertSame(testName, license.getName());
        assertSame(testBirthDate, license.getBirthDate());
        assertSame(testIssuanceDate, license.getIssuanceDate());
        assertSame(testExpirationDate, license.getExpiratonDate());
    }

    /**
     * Tests different jurisdiction formats.
     */
    @Test
    void testDifferentJurisdictions() {
        DriverLicense usLicense = new DriverLicense.Builder()
                .countryAndState("USA-CA")
                .build();

        DriverLicense canadaLicense = new DriverLicense.Builder()
                .countryAndState("CAN-ON")
                .build();

        assertEquals("USA-CA", usLicense.getCountryAndState());
        assertEquals("CAN-ON", canadaLicense.getCountryAndState());
    }

    /**
     * Tests expired vs valid license dates.
     */
    @Test
    void testExpiredVsValidLicenseDates() {
        Date pastExpiration = new Date.Builder(2020, 1, 1).build();
        Date futureExpiration = new Date.Builder(2030, 12, 31).build();

        DriverLicense expiredLicense = new DriverLicense.Builder()
                .experienceDate(pastExpiration)
                .build();

        DriverLicense validLicense = new DriverLicense.Builder()
                .experienceDate(futureExpiration)
                .build();

        assertEquals(2020, expiredLicense.getExpiratonDate().getYear());
        assertEquals(2030, validLicense.getExpiratonDate().getYear());
    }

    /**
     * Tests license with partial information.
     */
    @Test
    void testLicenseWithPartialInformation() {
        DriverLicense license = new DriverLicense.Builder()
                .licenseNumber(testLicenseNumber)
                .name(testName)
                .build();

        assertEquals(testLicenseNumber, license.getLicenseNumber());
        assertEquals(testName, license.getName());
        assertNull(license.getAddress());
        assertNull(license.getBirthDate());
        assertNull(license.getCountryAndState());
    }

    /**
     * Tests that empty builder creates valid object.
     */
    @Test
    void testEmptyBuilderCreatesValidObject() {
        DriverLicense license = new DriverLicense.Builder().build();

        assertNotNull(license);
        assertNull(license.getLicenseNumber());
        assertNull(license.getName());
        assertNull(license.getAddress());
        assertNull(license.getBirthDate());
        assertNull(license.getCountryAndState());
        assertNull(license.getIssuanceDate());
        assertNull(license.getExpiratonDate());
    }
}