package Registration.IncidentFactory;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests moving violation construction, field access, and incident methods.
 */
class MovingViolationTest {

    // ============ Default Constructor Tests ============

    /**
     * Tests default constructor creates a MovingViolation instance.
     */
    @Test
    void testDefaultConstructor() {
        MovingViolation violation = new MovingViolation();

        assertNotNull(violation);
        assertNull(violation.getViolationType());
    }

    /**
     * Tests that default constructor creates an instance of Incident.
     */
    @Test
    void testDefaultConstructorCreatesIncidentInstance() {
        MovingViolation violation = new MovingViolation();

        assertInstanceOf(Incident.class, violation);
    }

    // ============ MovingViolationEnum Constructor Tests ============

    /**
     * Tests constructor with SPEEDING violation type.
     */
    @Test
    void testConstructorWithSpeeding() {
        MovingViolation violation = new MovingViolation(MovingViolationEnum.SPEEDING);

        assertNotNull(violation);
        assertEquals(MovingViolationEnum.SPEEDING, violation.getViolationType());
    }

    /**
     * Tests constructor with DUI violation type.
     */
    @Test
    void testConstructorWithDui() {
        MovingViolation violation = new MovingViolation(MovingViolationEnum.DUI);

        assertNotNull(violation);
        assertEquals(MovingViolationEnum.DUI, violation.getViolationType());
    }

    /**
     * Tests constructor with DISTRACTED_DRIVING violation type.
     */
    @Test
    void testConstructorWithDistractedDriving() {
        MovingViolation violation = new MovingViolation(MovingViolationEnum.DISTRACTED_DRIVING);

        assertNotNull(violation);
        assertEquals(MovingViolationEnum.DISTRACTED_DRIVING, violation.getViolationType());
    }

    /**
     * Tests constructor with RECKLESS_DRIVING violation type.
     */
    @Test
    void testConstructorWithRecklessDriving() {
        MovingViolation violation = new MovingViolation(MovingViolationEnum.RECKLESS_DRIVING);

        assertNotNull(violation);
        assertEquals(MovingViolationEnum.RECKLESS_DRIVING, violation.getViolationType());
    }

    /**
     * Tests constructor with FAILURE_TO_OBEY_SIGNS violation type.
     */
    @Test
    void testConstructorWithFailureToObeySigns() {
        MovingViolation violation = new MovingViolation(MovingViolationEnum.FAILURE_TO_OBEY_SIGNS);

        assertNotNull(violation);
        assertEquals(MovingViolationEnum.FAILURE_TO_OBEY_SIGNS, violation.getViolationType());
    }

    /**
     * Tests constructor with DRIVING_WITHOUT_VALID_LICENSE_INSURANCE violation type.
     */
    @Test
    void testConstructorWithDrivingWithoutValidLicenseInsurance() {
        MovingViolation violation = new MovingViolation(MovingViolationEnum.DRIVING_WITHOUT_VALID_LICENSE_INSURANCE);

        assertNotNull(violation);
        assertEquals(MovingViolationEnum.DRIVING_WITHOUT_VALID_LICENSE_INSURANCE, violation.getViolationType());
    }

    /**
     * Tests constructor with null MovingViolationEnum.
     */
    @Test
    void testConstructorWithNullEnum() {
        MovingViolation violation = new MovingViolation((MovingViolationEnum) null);

        assertNotNull(violation);
        assertNull(violation.getViolationType());
    }

    // ============ String Constructor Tests ============

    /**
     * Tests constructor with "SPEEDING" string.
     */
    @Test
    void testConstructorWithSpeedingString() {
        MovingViolation violation = new MovingViolation("SPEEDING");

        assertNotNull(violation);
        assertEquals(MovingViolationEnum.SPEEDING, violation.getViolationType());
    }

    /**
     * Tests constructor with "DUI" string.
     */
    @Test
    void testConstructorWithDuiString() {
        MovingViolation violation = new MovingViolation("DUI");

        assertNotNull(violation);
        assertEquals(MovingViolationEnum.DUI, violation.getViolationType());
    }

    /**
     * Tests constructor with "DISTRACTED_DRIVING" string.
     */
    @Test
    void testConstructorWithDistractedDrivingString() {
        MovingViolation violation = new MovingViolation("DISTRACTED_DRIVING");

        assertNotNull(violation);
        assertEquals(MovingViolationEnum.DISTRACTED_DRIVING, violation.getViolationType());
    }

    /**
     * Tests constructor with "RECKLESS_DRIVING" string.
     */
    @Test
    void testConstructorWithRecklessDrivingString() {
        MovingViolation violation = new MovingViolation("RECKLESS_DRIVING");

        assertNotNull(violation);
        assertEquals(MovingViolationEnum.RECKLESS_DRIVING, violation.getViolationType());
    }

    /**
     * Tests constructor with "FAILURE_TO_OBEY_SIGNS" string.
     */
    @Test
    void testConstructorWithFailureToObeySignsString() {
        MovingViolation violation = new MovingViolation("FAILURE_TO_OBEY_SIGNS");

        assertNotNull(violation);
        assertEquals(MovingViolationEnum.FAILURE_TO_OBEY_SIGNS, violation.getViolationType());
    }

    /**
     * Tests constructor with "DRIVING_WITHOUT_VALID_LICENSE_INSURANCE" string.
     */
    @Test
    void testConstructorWithDrivingWithoutValidLicenseInsuranceString() {
        MovingViolation violation = new MovingViolation("DRIVING_WITHOUT_VALID_LICENSE_INSURANCE");

        assertNotNull(violation);
        assertEquals(MovingViolationEnum.DRIVING_WITHOUT_VALID_LICENSE_INSURANCE, violation.getViolationType());
    }

    /**
     * Tests constructor with invalid string throws IllegalArgumentException.
     */
    @Test
    void testConstructorWithInvalidString() {
        assertThrows(IllegalArgumentException.class, () -> {
            new MovingViolation("INVALID_VIOLATION");
        });
    }

    /**
     * Tests constructor with null string throws NullPointerException.
     */
    @Test
    void testConstructorWithNullString() {
        assertThrows(NullPointerException.class, () -> {
            new MovingViolation((String) null);
        });
    }

    /**
     * Tests constructor with lowercase string throws IllegalArgumentException.
     */
    @Test
    void testConstructorWithLowercaseString() {
        assertThrows(IllegalArgumentException.class, () -> {
            new MovingViolation("speeding");
        });
    }

    /**
     * Tests constructor with description throws IllegalArgumentException.
     */
    @Test
    void testConstructorWithDescription() {
        assertThrows(IllegalArgumentException.class, () -> {
            new MovingViolation("Speeding");
        });
    }

    // ============ Getter Tests ============

    /**
     * Tests getViolationType() returns correct value.
     */
    @Test
    void testGetViolationType() {
        MovingViolation violation = new MovingViolation(MovingViolationEnum.SPEEDING);

        assertEquals(MovingViolationEnum.SPEEDING, violation.getViolationType());
    }

    /**
     * Tests getViolationType() returns null when not set.
     */
    @Test
    void testGetViolationTypeWhenNull() {
        MovingViolation violation = new MovingViolation();

        assertNull(violation.getViolationType());
    }

    // ============ Setter Tests ============

    /**
     * Tests setViolationType() with SPEEDING.
     */
    @Test
    void testSetViolationTypeWithSpeeding() {
        MovingViolation violation = new MovingViolation();

        violation.setViolationType(MovingViolationEnum.SPEEDING);

        assertEquals(MovingViolationEnum.SPEEDING, violation.getViolationType());
    }

    /**
     * Tests setViolationType() with DUI.
     */
    @Test
    void testSetViolationTypeWithDui() {
        MovingViolation violation = new MovingViolation();

        violation.setViolationType(MovingViolationEnum.DUI);

        assertEquals(MovingViolationEnum.DUI, violation.getViolationType());
    }

    /**
     * Tests setViolationType() with DISTRACTED_DRIVING.
     */
    @Test
    void testSetViolationTypeWithDistractedDriving() {
        MovingViolation violation = new MovingViolation();

        violation.setViolationType(MovingViolationEnum.DISTRACTED_DRIVING);

        assertEquals(MovingViolationEnum.DISTRACTED_DRIVING, violation.getViolationType());
    }

    /**
     * Tests setViolationType() with null.
     */
    @Test
    void testSetViolationTypeToNull() {
        MovingViolation violation = new MovingViolation(MovingViolationEnum.SPEEDING);

        violation.setViolationType(null);

        assertNull(violation.getViolationType());
    }

    /**
     * Tests setViolationType() overwrites existing value.
     */
    @Test
    void testSetViolationTypeOverwritesExisting() {
        MovingViolation violation = new MovingViolation(MovingViolationEnum.SPEEDING);

        violation.setViolationType(MovingViolationEnum.DUI);

        assertEquals(MovingViolationEnum.DUI, violation.getViolationType());
    }

    // ============ getIncidentType() Tests ============

    /**
     * Tests getIncidentType() returns description for SPEEDING.
     */
    @Test
    void testGetIncidentTypeForSpeeding() {
        MovingViolation violation = new MovingViolation(MovingViolationEnum.SPEEDING);

        assertEquals("Speeding", violation.getIncidentType());
    }

    /**
     * Tests getIncidentType() returns description for DUI.
     */
    @Test
    void testGetIncidentTypeForDui() {
        MovingViolation violation = new MovingViolation(MovingViolationEnum.DUI);

        assertEquals("Driving Under Influence", violation.getIncidentType());
    }

    /**
     * Tests getIncidentType() returns description for DISTRACTED_DRIVING.
     */
    @Test
    void testGetIncidentTypeForDistractedDriving() {
        MovingViolation violation = new MovingViolation(MovingViolationEnum.DISTRACTED_DRIVING);

        assertEquals("Distracted Driving", violation.getIncidentType());
    }

    /**
     * Tests getIncidentType() returns description for RECKLESS_DRIVING.
     */
    @Test
    void testGetIncidentTypeForRecklessDriving() {
        MovingViolation violation = new MovingViolation(MovingViolationEnum.RECKLESS_DRIVING);

        assertEquals("Reckless Driving", violation.getIncidentType());
    }

    /**
     * Tests getIncidentType() returns description for FAILURE_TO_OBEY_SIGNS.
     */
    @Test
    void testGetIncidentTypeForFailureToObeySigns() {
        MovingViolation violation = new MovingViolation(MovingViolationEnum.FAILURE_TO_OBEY_SIGNS);

        assertEquals("Failure to Obey Traffic Signs", violation.getIncidentType());
    }

    /**
     * Tests getIncidentType() returns description for DRIVING_WITHOUT_VALID_LICENSE_INSURANCE.
     */
    @Test
    void testGetIncidentTypeForDrivingWithoutValidLicenseInsurance() {
        MovingViolation violation = new MovingViolation(MovingViolationEnum.DRIVING_WITHOUT_VALID_LICENSE_INSURANCE);

        assertEquals("Driving Without Valid License/Insurance", violation.getIncidentType());
    }

    /**
     * Tests getIncidentType() throws NullPointerException when violation type is null.
     */
    @Test
    void testGetIncidentTypeThrowsWhenViolationTypeIsNull() {
        MovingViolation violation = new MovingViolation();

        assertThrows(NullPointerException.class, () -> {
            violation.getIncidentType();
        });
    }

    /**
     * Tests getIncidentType() after setViolationType().
     */
    @Test
    void testGetIncidentTypeAfterSetViolationType() {
        MovingViolation violation = new MovingViolation(MovingViolationEnum.SPEEDING);

        violation.setViolationType(MovingViolationEnum.DUI);

        assertEquals("Driving Under Influence", violation.getIncidentType());
    }

    // ============ getIncidentCategory() Tests ============

    /**
     * Tests getIncidentCategory() always returns MOVING_VIOLATION.
     */
    @Test
    void testGetIncidentCategoryReturnsMovingViolation() {
        MovingViolation violation = new MovingViolation(MovingViolationEnum.SPEEDING);

        assertEquals(IncidentType.MOVING_VIOLATION, violation.getIncidentCategory());
    }

    /**
     * Tests getIncidentCategory() returns MOVING_VIOLATION for all violation types.
     */
    @Test
    void testGetIncidentCategoryForAllViolationTypes() {
        for (MovingViolationEnum type : MovingViolationEnum.values()) {
            MovingViolation violation = new MovingViolation(type);
            assertEquals(IncidentType.MOVING_VIOLATION, violation.getIncidentCategory());
        }
    }

    /**
     * Tests getIncidentCategory() returns MOVING_VIOLATION even when violation type is null.
     */
    @Test
    void testGetIncidentCategoryWhenViolationTypeIsNull() {
        MovingViolation violation = new MovingViolation();

        assertEquals(IncidentType.MOVING_VIOLATION, violation.getIncidentCategory());
    }

    /**
     * Tests getIncidentCategory() after setViolationType().
     */
    @Test
    void testGetIncidentCategoryAfterSetViolationType() {
        MovingViolation violation = new MovingViolation(MovingViolationEnum.SPEEDING);
        violation.setViolationType(MovingViolationEnum.DUI);

        assertEquals(IncidentType.MOVING_VIOLATION, violation.getIncidentCategory());
    }

    // ============ Inheritance Tests ============

    /**
     * Tests that MovingViolation is an instance of Incident.
     */
    @Test
    void testMovingViolationIsInstanceOfIncident() {
        MovingViolation violation = new MovingViolation(MovingViolationEnum.SPEEDING);

        assertInstanceOf(Incident.class, violation);
    }

    /**
     * Tests that MovingViolation can be assigned to Incident variable.
     */
    @Test
    void testMovingViolationCanBeAssignedToIncidentVariable() {
        Incident incident = new MovingViolation(MovingViolationEnum.DUI);

        assertNotNull(incident);
        assertEquals(IncidentType.MOVING_VIOLATION, incident.getIncidentCategory());
        assertEquals("Driving Under Influence", incident.getIncidentType());
    }

    /**
     * Tests that abstract methods are properly implemented.
     */
    @Test
    void testAbstractMethodsImplemented() {
        MovingViolation violation = new MovingViolation(MovingViolationEnum.SPEEDING);

        assertDoesNotThrow(() -> violation.getIncidentType());
        assertDoesNotThrow(() -> violation.getIncidentCategory());
    }

    // ============ Constructor Equivalence Tests ============

    /**
     * Tests that MovingViolationEnum and String constructors produce equivalent results.
     */
    @Test
    void testEnumAndStringConstructorEquivalence() {
        MovingViolation enumViolation = new MovingViolation(MovingViolationEnum.SPEEDING);
        MovingViolation stringViolation = new MovingViolation("SPEEDING");

        assertEquals(enumViolation.getViolationType(), stringViolation.getViolationType());
        assertEquals(enumViolation.getIncidentType(), stringViolation.getIncidentType());
        assertEquals(enumViolation.getIncidentCategory(), stringViolation.getIncidentCategory());
    }

    /**
     * Tests equivalence for all violation types.
     */
    @Test
    void testConstructorEquivalenceForAllTypes() {
        for (MovingViolationEnum type : MovingViolationEnum.values()) {
            MovingViolation enumViolation = new MovingViolation(type);
            MovingViolation stringViolation = new MovingViolation(type.name());

            assertEquals(enumViolation.getViolationType(), stringViolation.getViolationType());
        }
    }

    // ============ Integration Tests ============

    /**
     * Tests complete lifecycle: create, modify, verify.
     */
    @Test
    void testCompleteLifecycle() {
        // Create with default constructor
        MovingViolation violation = new MovingViolation();
        assertNull(violation.getViolationType());

        // Set violation type
        violation.setViolationType(MovingViolationEnum.SPEEDING);
        assertEquals(MovingViolationEnum.SPEEDING, violation.getViolationType());
        assertEquals("Speeding", violation.getIncidentType());

        // Change violation type
        violation.setViolationType(MovingViolationEnum.DUI);
        assertEquals(MovingViolationEnum.DUI, violation.getViolationType());
        assertEquals("Driving Under Influence", violation.getIncidentType());

        // Verify category remains constant
        assertEquals(IncidentType.MOVING_VIOLATION, violation.getIncidentCategory());
    }

    /**
     * Tests usage in polymorphic context.
     */
    @Test
    void testPolymorphicUsage() {
        Incident incident = new MovingViolation(MovingViolationEnum.DISTRACTED_DRIVING);

        assertEquals(IncidentType.MOVING_VIOLATION, incident.getIncidentCategory());
        assertEquals("Distracted Driving", incident.getIncidentType());
    }

    /**
     * Tests creating multiple violations with different types.
     */
    @Test
    void testMultipleViolationInstances() {
        MovingViolation v1 = new MovingViolation(MovingViolationEnum.SPEEDING);
        MovingViolation v2 = new MovingViolation(MovingViolationEnum.DUI);
        MovingViolation v3 = new MovingViolation(MovingViolationEnum.RECKLESS_DRIVING);

        assertNotSame(v1, v2);
        assertNotSame(v2, v3);

        assertEquals("Speeding", v1.getIncidentType());
        assertEquals("Driving Under Influence", v2.getIncidentType());
        assertEquals("Reckless Driving", v3.getIncidentType());
    }

    /**
     * Tests parsing from external data (simulating JSON).
     */
    @Test
    void testParsingFromExternalData() {
        // Simulate reading violation type strings from JSON
        String[] violationTypes = {
                "SPEEDING",
                "DUI",
                "DISTRACTED_DRIVING",
                "RECKLESS_DRIVING",
                "FAILURE_TO_OBEY_SIGNS",
                "DRIVING_WITHOUT_VALID_LICENSE_INSURANCE"
        };

        for (String typeString : violationTypes) {
            MovingViolation violation = new MovingViolation(typeString);
            assertNotNull(violation.getViolationType());
            assertEquals(IncidentType.MOVING_VIOLATION, violation.getIncidentCategory());
        }
    }

    /**
     * Tests that setter and getter work correctly together.
     */
    @Test
    void testSetterAndGetterConsistency() {
        MovingViolation violation = new MovingViolation();

        violation.setViolationType(MovingViolationEnum.SPEEDING);
        assertEquals(MovingViolationEnum.SPEEDING, violation.getViolationType());

        violation.setViolationType(MovingViolationEnum.DUI);
        assertEquals(MovingViolationEnum.DUI, violation.getViolationType());

        violation.setViolationType(MovingViolationEnum.DISTRACTED_DRIVING);
        assertEquals(MovingViolationEnum.DISTRACTED_DRIVING, violation.getViolationType());
    }

    /**
     * Tests violation in array of incidents.
     */
    @Test
    void testViolationInIncidentArray() {
        Incident[] incidents = new Incident[]{
                new MovingViolation(MovingViolationEnum.SPEEDING),
                new MovingViolation(MovingViolationEnum.DUI),
                new MovingViolation(MovingViolationEnum.RECKLESS_DRIVING)
        };

        for (Incident incident : incidents) {
            assertEquals(IncidentType.MOVING_VIOLATION, incident.getIncidentCategory());
            assertNotNull(incident.getIncidentType());
        }
    }

    /**
     * Tests that incident type changes when violation type changes.
     */
    @Test
    void testIncidentTypeReflectsViolationTypeChanges() {
        MovingViolation violation = new MovingViolation(MovingViolationEnum.SPEEDING);
        assertEquals("Speeding", violation.getIncidentType());

        violation.setViolationType(MovingViolationEnum.DUI);
        assertEquals("Driving Under Influence", violation.getIncidentType());

        violation.setViolationType(MovingViolationEnum.DISTRACTED_DRIVING);
        assertEquals("Distracted Driving", violation.getIncidentType());
    }

    /**
     * Tests violation construction from all possible enum values.
     */
    @Test
    void testConstructionFromAllEnumValues() {
        for (MovingViolationEnum type : MovingViolationEnum.values()) {
            MovingViolation violation = new MovingViolation(type);
            assertEquals(type, violation.getViolationType());
            assertNotNull(violation.getIncidentType());
            assertEquals(IncidentType.MOVING_VIOLATION, violation.getIncidentCategory());
        }
    }

    /**
     * Tests that violations created from strings match expected descriptions.
     */
    @Test
    void testStringConstructorProducesCorrectDescriptions() {
        MovingViolation v1 = new MovingViolation("SPEEDING");
        assertEquals("Speeding", v1.getIncidentType());

        MovingViolation v2 = new MovingViolation("DUI");
        assertEquals("Driving Under Influence", v2.getIncidentType());

        MovingViolation v3 = new MovingViolation("DISTRACTED_DRIVING");
        assertEquals("Distracted Driving", v3.getIncidentType());

        MovingViolation v4 = new MovingViolation("RECKLESS_DRIVING");
        assertEquals("Reckless Driving", v4.getIncidentType());

        MovingViolation v5 = new MovingViolation("FAILURE_TO_OBEY_SIGNS");
        assertEquals("Failure to Obey Traffic Signs", v5.getIncidentType());

        MovingViolation v6 = new MovingViolation("DRIVING_WITHOUT_VALID_LICENSE_INSURANCE");
        assertEquals("Driving Without Valid License/Insurance", v6.getIncidentType());
    }

    /**
     * Tests categorizing violations by severity (using switch pattern).
     */
    @Test
    void testViolationSeverityCategorization() {
        MovingViolation speeding = new MovingViolation(MovingViolationEnum.SPEEDING);
        MovingViolation dui = new MovingViolation(MovingViolationEnum.DUI);
        MovingViolation reckless = new MovingViolation(MovingViolationEnum.RECKLESS_DRIVING);

        // All should have same category but different types
        assertEquals(IncidentType.MOVING_VIOLATION, speeding.getIncidentCategory());
        assertEquals(IncidentType.MOVING_VIOLATION, dui.getIncidentCategory());
        assertEquals(IncidentType.MOVING_VIOLATION, reckless.getIncidentCategory());

        // But different violation types
        assertNotEquals(speeding.getViolationType(), dui.getViolationType());
        assertNotEquals(dui.getViolationType(), reckless.getViolationType());
    }
}