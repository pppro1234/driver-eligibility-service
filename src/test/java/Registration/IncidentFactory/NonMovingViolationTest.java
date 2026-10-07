package Registration.IncidentFactory;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests non-moving violation construction, field access, and incident methods.
 */
class NonMovingViolationTest {

    // ============ Default Constructor Tests ============

    /**
     * Tests default constructor creates a NonMovingViolation instance.
     */
    @Test
    void testDefaultConstructor() {
        NonMovingViolation violation = new NonMovingViolation();

        assertNotNull(violation);
        assertNull(violation.getViolationType());
    }

    /**
     * Tests that default constructor creates an instance of Incident.
     */
    @Test
    void testDefaultConstructorCreatesIncidentInstance() {
        NonMovingViolation violation = new NonMovingViolation();

        assertInstanceOf(Incident.class, violation);
    }

    // ============ NonMovingViolationEnum Constructor Tests ============

    /**
     * Tests constructor with PARKING_VIOLATION violation type.
     */
    @Test
    void testConstructorWithParkingViolation() {
        NonMovingViolation violation = new NonMovingViolation(NonMovingViolationEnum.PARKING_VIOLATION);

        assertNotNull(violation);
        assertEquals(NonMovingViolationEnum.PARKING_VIOLATION, violation.getViolationType());
    }

    /**
     * Tests constructor with PAPERWORK_ISSUE violation type.
     */
    @Test
    void testConstructorWithPaperworkIssue() {
        NonMovingViolation violation = new NonMovingViolation(NonMovingViolationEnum.PAPERWORK_ISSUE);

        assertNotNull(violation);
        assertEquals(NonMovingViolationEnum.PAPERWORK_ISSUE, violation.getViolationType());
    }

    /**
     * Tests constructor with VEHICLE_PROBLEM violation type.
     */
    @Test
    void testConstructorWithVehicleProblem() {
        NonMovingViolation violation = new NonMovingViolation(NonMovingViolationEnum.VEHICLE_PROBLEM);

        assertNotNull(violation);
        assertEquals(NonMovingViolationEnum.VEHICLE_PROBLEM, violation.getViolationType());
    }

    /**
     * Tests constructor with null NonMovingViolationEnum.
     */
    @Test
    void testConstructorWithNullEnum() {
        NonMovingViolation violation = new NonMovingViolation((NonMovingViolationEnum) null);

        assertNotNull(violation);
        assertNull(violation.getViolationType());
    }

    // ============ String Constructor Tests ============

    /**
     * Tests constructor with "PARKING_VIOLATION" string.
     */
    @Test
    void testConstructorWithParkingViolationString() {
        NonMovingViolation violation = new NonMovingViolation("PARKING_VIOLATION");

        assertNotNull(violation);
        assertEquals(NonMovingViolationEnum.PARKING_VIOLATION, violation.getViolationType());
    }

    /**
     * Tests constructor with "PAPERWORK_ISSUE" string.
     */
    @Test
    void testConstructorWithPaperworkIssueString() {
        NonMovingViolation violation = new NonMovingViolation("PAPERWORK_ISSUE");

        assertNotNull(violation);
        assertEquals(NonMovingViolationEnum.PAPERWORK_ISSUE, violation.getViolationType());
    }

    /**
     * Tests constructor with "VEHICLE_PROBLEM" string.
     */
    @Test
    void testConstructorWithVehicleProblemString() {
        NonMovingViolation violation = new NonMovingViolation("VEHICLE_PROBLEM");

        assertNotNull(violation);
        assertEquals(NonMovingViolationEnum.VEHICLE_PROBLEM, violation.getViolationType());
    }

    /**
     * Tests constructor with invalid string throws IllegalArgumentException.
     */
    @Test
    void testConstructorWithInvalidString() {
        assertThrows(IllegalArgumentException.class, () -> {
            new NonMovingViolation("INVALID_VIOLATION");
        });
    }

    /**
     * Tests constructor with null string throws NullPointerException.
     */
    @Test
    void testConstructorWithNullString() {
        assertThrows(NullPointerException.class, () -> {
            new NonMovingViolation((String) null);
        });
    }

    /**
     * Tests constructor with lowercase string throws IllegalArgumentException.
     */
    @Test
    void testConstructorWithLowercaseString() {
        assertThrows(IllegalArgumentException.class, () -> {
            new NonMovingViolation("parking_violation");
        });
    }

    /**
     * Tests constructor with description throws IllegalArgumentException.
     */
    @Test
    void testConstructorWithDescription() {
        assertThrows(IllegalArgumentException.class, () -> {
            new NonMovingViolation("Parking Violation");
        });
    }

    // ============ Getter Tests ============

    /**
     * Tests getViolationType() returns correct value.
     */
    @Test
    void testGetViolationType() {
        NonMovingViolation violation = new NonMovingViolation(NonMovingViolationEnum.PARKING_VIOLATION);

        assertEquals(NonMovingViolationEnum.PARKING_VIOLATION, violation.getViolationType());
    }

    /**
     * Tests getViolationType() returns null when not set.
     */
    @Test
    void testGetViolationTypeWhenNull() {
        NonMovingViolation violation = new NonMovingViolation();

        assertNull(violation.getViolationType());
    }

    // ============ Setter Tests ============

    /**
     * Tests setViolationType() with PARKING_VIOLATION.
     */
    @Test
    void testSetViolationTypeWithParkingViolation() {
        NonMovingViolation violation = new NonMovingViolation();

        violation.setViolationType(NonMovingViolationEnum.PARKING_VIOLATION);

        assertEquals(NonMovingViolationEnum.PARKING_VIOLATION, violation.getViolationType());
    }

    /**
     * Tests setViolationType() with PAPERWORK_ISSUE.
     */
    @Test
    void testSetViolationTypeWithPaperworkIssue() {
        NonMovingViolation violation = new NonMovingViolation();

        violation.setViolationType(NonMovingViolationEnum.PAPERWORK_ISSUE);

        assertEquals(NonMovingViolationEnum.PAPERWORK_ISSUE, violation.getViolationType());
    }

    /**
     * Tests setViolationType() with VEHICLE_PROBLEM.
     */
    @Test
    void testSetViolationTypeWithVehicleProblem() {
        NonMovingViolation violation = new NonMovingViolation();

        violation.setViolationType(NonMovingViolationEnum.VEHICLE_PROBLEM);

        assertEquals(NonMovingViolationEnum.VEHICLE_PROBLEM, violation.getViolationType());
    }

    /**
     * Tests setViolationType() with null.
     */
    @Test
    void testSetViolationTypeToNull() {
        NonMovingViolation violation = new NonMovingViolation(NonMovingViolationEnum.PARKING_VIOLATION);

        violation.setViolationType(null);

        assertNull(violation.getViolationType());
    }

    /**
     * Tests setViolationType() overwrites existing value.
     */
    @Test
    void testSetViolationTypeOverwritesExisting() {
        NonMovingViolation violation = new NonMovingViolation(NonMovingViolationEnum.PARKING_VIOLATION);

        violation.setViolationType(NonMovingViolationEnum.VEHICLE_PROBLEM);

        assertEquals(NonMovingViolationEnum.VEHICLE_PROBLEM, violation.getViolationType());
    }

    // ============ getIncidentType() Tests ============

    /**
     * Tests getIncidentType() returns description for PARKING_VIOLATION.
     */
    @Test
    void testGetIncidentTypeForParkingViolation() {
        NonMovingViolation violation = new NonMovingViolation(NonMovingViolationEnum.PARKING_VIOLATION);

        assertEquals("Parking Violation", violation.getIncidentType());
    }

    /**
     * Tests getIncidentType() returns description for PAPERWORK_ISSUE.
     */
    @Test
    void testGetIncidentTypeForPaperworkIssue() {
        NonMovingViolation violation = new NonMovingViolation(NonMovingViolationEnum.PAPERWORK_ISSUE);

        assertEquals("Paper Work Issue", violation.getIncidentType());
    }

    /**
     * Tests getIncidentType() returns description for VEHICLE_PROBLEM.
     */
    @Test
    void testGetIncidentTypeForVehicleProblem() {
        NonMovingViolation violation = new NonMovingViolation(NonMovingViolationEnum.VEHICLE_PROBLEM);

        assertEquals("Vehicle Problem", violation.getIncidentType());
    }

    /**
     * Tests getIncidentType() throws NullPointerException when violation type is null.
     */
    @Test
    void testGetIncidentTypeThrowsWhenViolationTypeIsNull() {
        NonMovingViolation violation = new NonMovingViolation();

        assertThrows(NullPointerException.class, () -> {
            violation.getIncidentType();
        });
    }

    /**
     * Tests getIncidentType() after setViolationType().
     */
    @Test
    void testGetIncidentTypeAfterSetViolationType() {
        NonMovingViolation violation = new NonMovingViolation(NonMovingViolationEnum.PARKING_VIOLATION);

        violation.setViolationType(NonMovingViolationEnum.PAPERWORK_ISSUE);

        assertEquals("Paper Work Issue", violation.getIncidentType());
    }

    // ============ getIncidentCategory() Tests ============

    /**
     * Tests getIncidentCategory() always returns NONMOVING_VIOLATION.
     */
    @Test
    void testGetIncidentCategoryReturnsNonMovingViolation() {
        NonMovingViolation violation = new NonMovingViolation(NonMovingViolationEnum.PARKING_VIOLATION);

        assertEquals(IncidentType.NONMOVING_VIOLATION, violation.getIncidentCategory());
    }

    /**
     * Tests getIncidentCategory() returns NONMOVING_VIOLATION for all violation types.
     */
    @Test
    void testGetIncidentCategoryForAllViolationTypes() {
        for (NonMovingViolationEnum type : NonMovingViolationEnum.values()) {
            NonMovingViolation violation = new NonMovingViolation(type);
            assertEquals(IncidentType.NONMOVING_VIOLATION, violation.getIncidentCategory());
        }
    }

    /**
     * Tests getIncidentCategory() returns NONMOVING_VIOLATION even when violation type is null.
     */
    @Test
    void testGetIncidentCategoryWhenViolationTypeIsNull() {
        NonMovingViolation violation = new NonMovingViolation();

        assertEquals(IncidentType.NONMOVING_VIOLATION, violation.getIncidentCategory());
    }

    /**
     * Tests getIncidentCategory() after setViolationType().
     */
    @Test
    void testGetIncidentCategoryAfterSetViolationType() {
        NonMovingViolation violation = new NonMovingViolation(NonMovingViolationEnum.PARKING_VIOLATION);
        violation.setViolationType(NonMovingViolationEnum.VEHICLE_PROBLEM);

        assertEquals(IncidentType.NONMOVING_VIOLATION, violation.getIncidentCategory());
    }

    // ============ Inheritance Tests ============

    /**
     * Tests that NonMovingViolation is an instance of Incident.
     */
    @Test
    void testNonMovingViolationIsInstanceOfIncident() {
        NonMovingViolation violation = new NonMovingViolation(NonMovingViolationEnum.PARKING_VIOLATION);

        assertInstanceOf(Incident.class, violation);
    }

    /**
     * Tests that NonMovingViolation can be assigned to Incident variable.
     */
    @Test
    void testNonMovingViolationCanBeAssignedToIncidentVariable() {
        Incident incident = new NonMovingViolation(NonMovingViolationEnum.PAPERWORK_ISSUE);

        assertNotNull(incident);
        assertEquals(IncidentType.NONMOVING_VIOLATION, incident.getIncidentCategory());
        assertEquals("Paper Work Issue", incident.getIncidentType());
    }

    /**
     * Tests that abstract methods are properly implemented.
     */
    @Test
    void testAbstractMethodsImplemented() {
        NonMovingViolation violation = new NonMovingViolation(NonMovingViolationEnum.PARKING_VIOLATION);

        assertDoesNotThrow(() -> violation.getIncidentType());
        assertDoesNotThrow(() -> violation.getIncidentCategory());
    }

    // ============ Constructor Equivalence Tests ============

    /**
     * Tests that NonMovingViolationEnum and String constructors produce equivalent results.
     */
    @Test
    void testEnumAndStringConstructorEquivalence() {
        NonMovingViolation enumViolation = new NonMovingViolation(NonMovingViolationEnum.PARKING_VIOLATION);
        NonMovingViolation stringViolation = new NonMovingViolation("PARKING_VIOLATION");

        assertEquals(enumViolation.getViolationType(), stringViolation.getViolationType());
        assertEquals(enumViolation.getIncidentType(), stringViolation.getIncidentType());
        assertEquals(enumViolation.getIncidentCategory(), stringViolation.getIncidentCategory());
    }

    /**
     * Tests equivalence for all violation types.
     */
    @Test
    void testConstructorEquivalenceForAllTypes() {
        for (NonMovingViolationEnum type : NonMovingViolationEnum.values()) {
            NonMovingViolation enumViolation = new NonMovingViolation(type);
            NonMovingViolation stringViolation = new NonMovingViolation(type.name());

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
        NonMovingViolation violation = new NonMovingViolation();
        assertNull(violation.getViolationType());

        // Set violation type
        violation.setViolationType(NonMovingViolationEnum.PARKING_VIOLATION);
        assertEquals(NonMovingViolationEnum.PARKING_VIOLATION, violation.getViolationType());
        assertEquals("Parking Violation", violation.getIncidentType());

        // Change violation type
        violation.setViolationType(NonMovingViolationEnum.VEHICLE_PROBLEM);
        assertEquals(NonMovingViolationEnum.VEHICLE_PROBLEM, violation.getViolationType());
        assertEquals("Vehicle Problem", violation.getIncidentType());

        // Verify category remains constant
        assertEquals(IncidentType.NONMOVING_VIOLATION, violation.getIncidentCategory());
    }

    /**
     * Tests usage in polymorphic context.
     */
    @Test
    void testPolymorphicUsage() {
        Incident incident = new NonMovingViolation(NonMovingViolationEnum.PAPERWORK_ISSUE);

        assertEquals(IncidentType.NONMOVING_VIOLATION, incident.getIncidentCategory());
        assertEquals("Paper Work Issue", incident.getIncidentType());
    }

    /**
     * Tests creating multiple violations with different types.
     */
    @Test
    void testMultipleViolationInstances() {
        NonMovingViolation v1 = new NonMovingViolation(NonMovingViolationEnum.PARKING_VIOLATION);
        NonMovingViolation v2 = new NonMovingViolation(NonMovingViolationEnum.PAPERWORK_ISSUE);
        NonMovingViolation v3 = new NonMovingViolation(NonMovingViolationEnum.VEHICLE_PROBLEM);

        assertNotSame(v1, v2);
        assertNotSame(v2, v3);

        assertEquals("Parking Violation", v1.getIncidentType());
        assertEquals("Paper Work Issue", v2.getIncidentType());
        assertEquals("Vehicle Problem", v3.getIncidentType());
    }

    /**
     * Tests parsing from external data (simulating JSON).
     */
    @Test
    void testParsingFromExternalData() {
        // Simulate reading violation type strings from JSON
        String[] violationTypes = {
                "PARKING_VIOLATION",
                "PAPERWORK_ISSUE",
                "VEHICLE_PROBLEM"
        };

        for (String typeString : violationTypes) {
            NonMovingViolation violation = new NonMovingViolation(typeString);
            assertNotNull(violation.getViolationType());
            assertEquals(IncidentType.NONMOVING_VIOLATION, violation.getIncidentCategory());
        }
    }

    /**
     * Tests that setter and getter work correctly together.
     */
    @Test
    void testSetterAndGetterConsistency() {
        NonMovingViolation violation = new NonMovingViolation();

        violation.setViolationType(NonMovingViolationEnum.PARKING_VIOLATION);
        assertEquals(NonMovingViolationEnum.PARKING_VIOLATION, violation.getViolationType());

        violation.setViolationType(NonMovingViolationEnum.PAPERWORK_ISSUE);
        assertEquals(NonMovingViolationEnum.PAPERWORK_ISSUE, violation.getViolationType());

        violation.setViolationType(NonMovingViolationEnum.VEHICLE_PROBLEM);
        assertEquals(NonMovingViolationEnum.VEHICLE_PROBLEM, violation.getViolationType());
    }

    /**
     * Tests violation in array of incidents.
     */
    @Test
    void testViolationInIncidentArray() {
        Incident[] incidents = new Incident[]{
                new NonMovingViolation(NonMovingViolationEnum.PARKING_VIOLATION),
                new NonMovingViolation(NonMovingViolationEnum.PAPERWORK_ISSUE),
                new NonMovingViolation(NonMovingViolationEnum.VEHICLE_PROBLEM)
        };

        for (Incident incident : incidents) {
            assertEquals(IncidentType.NONMOVING_VIOLATION, incident.getIncidentCategory());
            assertNotNull(incident.getIncidentType());
        }
    }

    /**
     * Tests that incident type changes when violation type changes.
     */
    @Test
    void testIncidentTypeReflectsViolationTypeChanges() {
        NonMovingViolation violation = new NonMovingViolation(NonMovingViolationEnum.PARKING_VIOLATION);
        assertEquals("Parking Violation", violation.getIncidentType());

        violation.setViolationType(NonMovingViolationEnum.PAPERWORK_ISSUE);
        assertEquals("Paper Work Issue", violation.getIncidentType());

        violation.setViolationType(NonMovingViolationEnum.VEHICLE_PROBLEM);
        assertEquals("Vehicle Problem", violation.getIncidentType());
    }

    /**
     * Tests violation construction from all possible enum values.
     */
    @Test
    void testConstructionFromAllEnumValues() {
        for (NonMovingViolationEnum type : NonMovingViolationEnum.values()) {
            NonMovingViolation violation = new NonMovingViolation(type);
            assertEquals(type, violation.getViolationType());
            assertNotNull(violation.getIncidentType());
            assertEquals(IncidentType.NONMOVING_VIOLATION, violation.getIncidentCategory());
        }
    }

    /**
     * Tests that violations created from strings match expected descriptions.
     */
    @Test
    void testStringConstructorProducesCorrectDescriptions() {
        NonMovingViolation v1 = new NonMovingViolation("PARKING_VIOLATION");
        assertEquals("Parking Violation", v1.getIncidentType());

        NonMovingViolation v2 = new NonMovingViolation("PAPERWORK_ISSUE");
        assertEquals("Paper Work Issue", v2.getIncidentType());

        NonMovingViolation v3 = new NonMovingViolation("VEHICLE_PROBLEM");
        assertEquals("Vehicle Problem", v3.getIncidentType());
    }

    /**
     * Tests categorizing violations by type (using descriptions).
     */
    @Test
    void testViolationTypeCategorization() {
        NonMovingViolation parking = new NonMovingViolation(NonMovingViolationEnum.PARKING_VIOLATION);
        NonMovingViolation paperwork = new NonMovingViolation(NonMovingViolationEnum.PAPERWORK_ISSUE);
        NonMovingViolation vehicle = new NonMovingViolation(NonMovingViolationEnum.VEHICLE_PROBLEM);

        // All should have same category but different types
        assertEquals(IncidentType.NONMOVING_VIOLATION, parking.getIncidentCategory());
        assertEquals(IncidentType.NONMOVING_VIOLATION, paperwork.getIncidentCategory());
        assertEquals(IncidentType.NONMOVING_VIOLATION, vehicle.getIncidentCategory());

        // But different violation types
        assertNotEquals(parking.getViolationType(), paperwork.getViolationType());
        assertNotEquals(paperwork.getViolationType(), vehicle.getViolationType());
    }

    /**
     * Tests that non-moving violations are distinct from moving violations.
     */
    @Test
    void testNonMovingViolationDistinctFromMovingViolation() {
        NonMovingViolation nonMoving = new NonMovingViolation(NonMovingViolationEnum.PARKING_VIOLATION);
        MovingViolation moving = new MovingViolation(MovingViolationEnum.SPEEDING);

        assertNotEquals(nonMoving.getIncidentCategory(), moving.getIncidentCategory());
        assertEquals(IncidentType.NONMOVING_VIOLATION, nonMoving.getIncidentCategory());
        assertEquals(IncidentType.MOVING_VIOLATION, moving.getIncidentCategory());
    }

    /**
     * Tests heterogeneous incident array with both moving and non-moving violations.
     */
    @Test
    void testHeterogeneousIncidentArray() {
        Incident[] incidents = new Incident[]{
                new NonMovingViolation(NonMovingViolationEnum.PARKING_VIOLATION),
                new MovingViolation(MovingViolationEnum.SPEEDING)
        };

        assertEquals(IncidentType.NONMOVING_VIOLATION, incidents[0].getIncidentCategory());
        assertEquals(IncidentType.MOVING_VIOLATION, incidents[1].getIncidentCategory());
    }
}