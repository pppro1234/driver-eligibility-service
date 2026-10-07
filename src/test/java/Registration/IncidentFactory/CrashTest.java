package Registration.IncidentFactory;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests crash construction, field access, and incident methods.
 */
class CrashTest {

    // ============ Default Constructor Tests ============

    /**
     * Tests default constructor creates a Crash instance.
     * Verifies that default constructor works and initializes with null crashType.
     */
    @Test
    void testDefaultConstructor() {
        Crash crash = new Crash();

        assertNotNull(crash);
        assertNull(crash.getCrashType());
    }

    /**
     * Tests that default constructor creates an instance of Incident.
     * Verifies proper inheritance.
     */
    @Test
    void testDefaultConstructorCreatesIncidentInstance() {
        Crash crash = new Crash();

        assertInstanceOf(Incident.class, crash);
    }

    // ============ CrashEnum Constructor Tests ============

    /**
     * Tests constructor with FENDER_BENDER crash type.
     * Verifies correct initialization with enum value.
     */
    @Test
    void testConstructorWithFenderBender() {
        Crash crash = new Crash(CrashEnum.FENDER_BENDER);

        assertNotNull(crash);
        assertEquals(CrashEnum.FENDER_BENDER, crash.getCrashType());
    }

    /**
     * Tests constructor with NO_INJURY_CRASH crash type.
     * Verifies correct initialization with enum value.
     */
    @Test
    void testConstructorWithNoInjuryCrash() {
        Crash crash = new Crash(CrashEnum.NO_INJURY_CRASH);

        assertNotNull(crash);
        assertEquals(CrashEnum.NO_INJURY_CRASH, crash.getCrashType());
    }

    /**
     * Tests constructor with INJURY_CRASH crash type.
     * Verifies correct initialization with enum value.
     */
    @Test
    void testConstructorWithInjuryCrash() {
        Crash crash = new Crash(CrashEnum.INJURY_CRASH);

        assertNotNull(crash);
        assertEquals(CrashEnum.INJURY_CRASH, crash.getCrashType());
    }

    /**
     * Tests constructor with null CrashEnum.
     * Verifies that null is accepted as a valid parameter.
     */
    @Test
    void testConstructorWithNullEnum() {
        Crash crash = new Crash((CrashEnum) null);

        assertNotNull(crash);
        assertNull(crash.getCrashType());
    }

    // ============ String Constructor Tests ============

    /**
     * Tests constructor with "FENDER_BENDER" string.
     * Verifies correct parsing from string to enum.
     */
    @Test
    void testConstructorWithFenderBenderString() {
        Crash crash = new Crash("FENDER_BENDER");

        assertNotNull(crash);
        assertEquals(CrashEnum.FENDER_BENDER, crash.getCrashType());
    }

    /**
     * Tests constructor with "NO_INJURY_CRASH" string.
     * Verifies correct parsing from string to enum.
     */
    @Test
    void testConstructorWithNoInjuryCrashString() {
        Crash crash = new Crash("NO_INJURY_CRASH");

        assertNotNull(crash);
        assertEquals(CrashEnum.NO_INJURY_CRASH, crash.getCrashType());
    }

    /**
     * Tests constructor with "INJURY_CRASH" string.
     * Verifies correct parsing from string to enum.
     */
    @Test
    void testConstructorWithInjuryCrashString() {
        Crash crash = new Crash("INJURY_CRASH");

        assertNotNull(crash);
        assertEquals(CrashEnum.INJURY_CRASH, crash.getCrashType());
    }

    /**
     * Tests constructor with invalid string throws IllegalArgumentException.
     * Verifies proper exception handling for invalid input.
     */
    @Test
    void testConstructorWithInvalidString() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Crash("INVALID_CRASH_TYPE");
        });
    }

    /**
     * Tests constructor with null string throws NullPointerException.
     * Verifies proper exception handling for null input.
     */
    @Test
    void testConstructorWithNullString() {
        assertThrows(NullPointerException.class, () -> {
            new Crash((String) null);
        });
    }

    /**
     * Tests constructor with lowercase string throws IllegalArgumentException.
     * Verifies case sensitivity in string parsing.
     */
    @Test
    void testConstructorWithLowercaseString() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Crash("fender_bender");
        });
    }

    /**
     * Tests constructor with display name throws IllegalArgumentException.
     * Verifies that display names are not valid for construction.
     */
    @Test
    void testConstructorWithDisplayName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Crash("Fender bender");
        });
    }

    // ============ Getter Tests ============

    /**
     * Tests getCrashType() returns correct value.
     * Verifies getter returns the crash type set in constructor.
     */
    @Test
    void testGetCrashType() {
        Crash crash = new Crash(CrashEnum.INJURY_CRASH);

        assertEquals(CrashEnum.INJURY_CRASH, crash.getCrashType());
    }

    /**
     * Tests getCrashType() returns null when not set.
     * Verifies getter returns null for default-constructed instance.
     */
    @Test
    void testGetCrashTypeWhenNull() {
        Crash crash = new Crash();

        assertNull(crash.getCrashType());
    }

    // ============ Setter Tests ============

    /**
     * Tests setCrashType() with FENDER_BENDER.
     * Verifies setter correctly updates the crash type.
     */
    @Test
    void testSetCrashTypeWithFenderBender() {
        Crash crash = new Crash();

        crash.setCrashType(CrashEnum.FENDER_BENDER);

        assertEquals(CrashEnum.FENDER_BENDER, crash.getCrashType());
    }

    /**
     * Tests setCrashType() with NO_INJURY_CRASH.
     * Verifies setter correctly updates the crash type.
     */
    @Test
    void testSetCrashTypeWithNoInjuryCrash() {
        Crash crash = new Crash();

        crash.setCrashType(CrashEnum.NO_INJURY_CRASH);

        assertEquals(CrashEnum.NO_INJURY_CRASH, crash.getCrashType());
    }

    /**
     * Tests setCrashType() with INJURY_CRASH.
     * Verifies setter correctly updates the crash type.
     */
    @Test
    void testSetCrashTypeWithInjuryCrash() {
        Crash crash = new Crash();

        crash.setCrashType(CrashEnum.INJURY_CRASH);

        assertEquals(CrashEnum.INJURY_CRASH, crash.getCrashType());
    }

    /**
     * Tests setCrashType() with null.
     * Verifies setter accepts null value.
     */
    @Test
    void testSetCrashTypeToNull() {
        Crash crash = new Crash(CrashEnum.FENDER_BENDER);

        crash.setCrashType(null);

        assertNull(crash.getCrashType());
    }

    /**
     * Tests setCrashType() overwrites existing value.
     * Verifies setter can change the crash type.
     */
    @Test
    void testSetCrashTypeOverwritesExisting() {
        Crash crash = new Crash(CrashEnum.FENDER_BENDER);

        crash.setCrashType(CrashEnum.INJURY_CRASH);

        assertEquals(CrashEnum.INJURY_CRASH, crash.getCrashType());
    }

    // ============ getIncidentType() Tests ============

    /**
     * Tests getIncidentType() returns display name for FENDER_BENDER.
     * Verifies incident type returns human-readable format.
     */
    @Test
    void testGetIncidentTypeForFenderBender() {
        Crash crash = new Crash(CrashEnum.FENDER_BENDER);

        assertEquals("Fender bender", crash.getIncidentType());
    }

    /**
     * Tests getIncidentType() returns display name for NO_INJURY_CRASH.
     * Verifies incident type returns human-readable format.
     */
    @Test
    void testGetIncidentTypeForNoInjuryCrash() {
        Crash crash = new Crash(CrashEnum.NO_INJURY_CRASH);

        assertEquals("No Injury Crash", crash.getIncidentType());
    }

    /**
     * Tests getIncidentType() returns display name for INJURY_CRASH.
     * Verifies incident type returns human-readable format.
     */
    @Test
    void testGetIncidentTypeForInjuryCrash() {
        Crash crash = new Crash(CrashEnum.INJURY_CRASH);

        assertEquals("Injury Crash", crash.getIncidentType());
    }

    /**
     * Tests getIncidentType() throws NullPointerException when crash type is null.
     * Verifies proper exception when called on uninitialized crash.
     */
    @Test
    void testGetIncidentTypeThrowsWhenCrashTypeIsNull() {
        Crash crash = new Crash();

        assertThrows(NullPointerException.class, () -> {
            crash.getIncidentType();
        });
    }

    /**
     * Tests getIncidentType() after setCrashType().
     * Verifies incident type reflects changes made via setter.
     */
    @Test
    void testGetIncidentTypeAfterSetCrashType() {
        Crash crash = new Crash(CrashEnum.FENDER_BENDER);

        crash.setCrashType(CrashEnum.INJURY_CRASH);

        assertEquals("Injury Crash", crash.getIncidentType());
    }

    // ============ getIncidentCategory() Tests ============

    /**
     * Tests getIncidentCategory() always returns CRASH.
     * Verifies category is always CRASH regardless of crash type.
     */
    @Test
    void testGetIncidentCategoryReturnsCrash() {
        Crash crash = new Crash(CrashEnum.FENDER_BENDER);

        assertEquals(IncidentType.CRASH, crash.getIncidentCategory());
    }

    /**
     * Tests getIncidentCategory() returns CRASH for all crash types.
     * Verifies consistent category across all crash types.
     */
    @Test
    void testGetIncidentCategoryForAllCrashTypes() {
        for (CrashEnum crashType : CrashEnum.values()) {
            Crash crash = new Crash(crashType);
            assertEquals(IncidentType.CRASH, crash.getIncidentCategory());
        }
    }

    /**
     * Tests getIncidentCategory() returns CRASH even when crash type is null.
     * Verifies category is independent of crash type initialization.
     */
    @Test
    void testGetIncidentCategoryWhenCrashTypeIsNull() {
        Crash crash = new Crash();

        assertEquals(IncidentType.CRASH, crash.getIncidentCategory());
    }

    /**
     * Tests getIncidentCategory() after setCrashType().
     * Verifies category remains CRASH after changes.
     */
    @Test
    void testGetIncidentCategoryAfterSetCrashType() {
        Crash crash = new Crash(CrashEnum.FENDER_BENDER);
        crash.setCrashType(CrashEnum.INJURY_CRASH);

        assertEquals(IncidentType.CRASH, crash.getIncidentCategory());
    }

    // ============ Inheritance Tests ============

    /**
     * Tests that Crash is an instance of Incident.
     * Verifies proper inheritance hierarchy.
     */
    @Test
    void testCrashIsInstanceOfIncident() {
        Crash crash = new Crash(CrashEnum.FENDER_BENDER);

        assertInstanceOf(Incident.class, crash);
    }

    /**
     * Tests that Crash can be assigned to Incident variable.
     * Verifies polymorphic usage.
     */
    @Test
    void testCrashCanBeAssignedToIncidentVariable() {
        Incident incident = new Crash(CrashEnum.NO_INJURY_CRASH);

        assertNotNull(incident);
        assertEquals(IncidentType.CRASH, incident.getIncidentCategory());
        assertEquals("No Injury Crash", incident.getIncidentType());
    }

    /**
     * Tests that abstract methods are properly implemented.
     * Verifies both abstract methods can be called without error.
     */
    @Test
    void testAbstractMethodsImplemented() {
        Crash crash = new Crash(CrashEnum.INJURY_CRASH);

        assertDoesNotThrow(() -> crash.getIncidentType());
        assertDoesNotThrow(() -> crash.getIncidentCategory());
    }

    // ============ Constructor Equivalence Tests ============

    /**
     * Tests that CrashEnum and String constructors produce equivalent results.
     * Verifies both construction methods create identical crash objects.
     */
    @Test
    void testEnumAndStringConstructorEquivalence() {
        Crash enumCrash = new Crash(CrashEnum.FENDER_BENDER);
        Crash stringCrash = new Crash("FENDER_BENDER");

        assertEquals(enumCrash.getCrashType(), stringCrash.getCrashType());
        assertEquals(enumCrash.getIncidentType(), stringCrash.getIncidentType());
        assertEquals(enumCrash.getIncidentCategory(), stringCrash.getIncidentCategory());
    }

    /**
     * Tests equivalence for all crash types.
     * Verifies both constructors work identically for all enum values.
     */
    @Test
    void testConstructorEquivalenceForAllTypes() {
        for (CrashEnum type : CrashEnum.values()) {
            Crash enumCrash = new Crash(type);
            Crash stringCrash = new Crash(type.name());

            assertEquals(enumCrash.getCrashType(), stringCrash.getCrashType());
        }
    }

    // ============ Integration Tests ============

    /**
     * Tests complete lifecycle: create, modify, verify.
     * Verifies all operations work correctly in sequence.
     */
    @Test
    void testCompleteLifecycle() {
        // Create with default constructor
        Crash crash = new Crash();
        assertNull(crash.getCrashType());

        // Set crash type
        crash.setCrashType(CrashEnum.FENDER_BENDER);
        assertEquals(CrashEnum.FENDER_BENDER, crash.getCrashType());
        assertEquals("Fender bender", crash.getIncidentType());

        // Change crash type
        crash.setCrashType(CrashEnum.INJURY_CRASH);
        assertEquals(CrashEnum.INJURY_CRASH, crash.getCrashType());
        assertEquals("Injury Crash", crash.getIncidentType());

        // Verify category remains constant
        assertEquals(IncidentType.CRASH, crash.getIncidentCategory());
    }

    /**
     * Tests usage in polymorphic context.
     * Verifies crash works correctly when used as Incident.
     */
    @Test
    void testPolymorphicUsage() {
        Incident incident = new Crash(CrashEnum.NO_INJURY_CRASH);

        assertEquals(IncidentType.CRASH, incident.getIncidentCategory());
        assertEquals("No Injury Crash", incident.getIncidentType());
    }

    /**
     * Tests creating multiple crashes with different types.
     * Verifies independent instances work correctly.
     */
    @Test
    void testMultipleCrashInstances() {
        Crash crash1 = new Crash(CrashEnum.FENDER_BENDER);
        Crash crash2 = new Crash(CrashEnum.NO_INJURY_CRASH);
        Crash crash3 = new Crash(CrashEnum.INJURY_CRASH);

        assertNotSame(crash1, crash2);
        assertNotSame(crash2, crash3);

        assertEquals("Fender bender", crash1.getIncidentType());
        assertEquals("No Injury Crash", crash2.getIncidentType());
        assertEquals("Injury Crash", crash3.getIncidentType());
    }

    /**
     * Tests parsing from external data (simulating JSON).
     * Verifies string constructor usage in realistic scenario.
     */
    @Test
    void testParsingFromExternalData() {
        // Simulate reading crash type strings from JSON
        String[] crashTypes = {"FENDER_BENDER", "NO_INJURY_CRASH", "INJURY_CRASH"};

        for (String typeString : crashTypes) {
            Crash crash = new Crash(typeString);
            assertNotNull(crash.getCrashType());
            assertEquals(IncidentType.CRASH, crash.getIncidentCategory());
        }
    }

    /**
     * Tests that setter and getter work correctly together.
     * Verifies state management.
     */
    @Test
    void testSetterAndGetterConsistency() {
        Crash crash = new Crash();

        crash.setCrashType(CrashEnum.FENDER_BENDER);
        assertEquals(CrashEnum.FENDER_BENDER, crash.getCrashType());

        crash.setCrashType(CrashEnum.NO_INJURY_CRASH);
        assertEquals(CrashEnum.NO_INJURY_CRASH, crash.getCrashType());

        crash.setCrashType(CrashEnum.INJURY_CRASH);
        assertEquals(CrashEnum.INJURY_CRASH, crash.getCrashType());
    }

    /**
     * Tests crash in array of incidents.
     * Verifies heterogeneous collection usage.
     */
    @Test
    void testCrashInIncidentArray() {
        Incident[] incidents = new Incident[]{
                new Crash(CrashEnum.FENDER_BENDER),
                new Crash(CrashEnum.NO_INJURY_CRASH),
                new Crash(CrashEnum.INJURY_CRASH)
        };

        for (Incident incident : incidents) {
            assertEquals(IncidentType.CRASH, incident.getIncidentCategory());
            assertNotNull(incident.getIncidentType());
        }
    }

    /**
     * Tests that incident type changes when crash type changes.
     * Verifies dynamic behavior of getIncidentType().
     */
    @Test
    void testIncidentTypeReflectsCrashTypeChanges() {
        Crash crash = new Crash(CrashEnum.FENDER_BENDER);
        assertEquals("Fender bender", crash.getIncidentType());

        crash.setCrashType(CrashEnum.NO_INJURY_CRASH);
        assertEquals("No Injury Crash", crash.getIncidentType());

        crash.setCrashType(CrashEnum.INJURY_CRASH);
        assertEquals("Injury Crash", crash.getIncidentType());
    }

    /**
     * Tests crash construction from all possible enum values.
     * Verifies comprehensive enum support.
     */
    @Test
    void testConstructionFromAllEnumValues() {
        for (CrashEnum type : CrashEnum.values()) {
            Crash crash = new Crash(type);
            assertEquals(type, crash.getCrashType());
            assertNotNull(crash.getIncidentType());
            assertEquals(IncidentType.CRASH, crash.getIncidentCategory());
        }
    }

    /**
     * Tests that crashes created from strings match expected display names.
     * Verifies end-to-end string parsing to display name conversion.
     */
    @Test
    void testStringConstructorProducesCorrectDisplayNames() {
        Crash crash1 = new Crash("FENDER_BENDER");
        assertEquals("Fender bender", crash1.getIncidentType());

        Crash crash2 = new Crash("NO_INJURY_CRASH");
        assertEquals("No Injury Crash", crash2.getIncidentType());

        Crash crash3 = new Crash("INJURY_CRASH");
        assertEquals("Injury Crash", crash3.getIncidentType());
    }
}