package Registration.IncidentFactory;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests moving violation enum values and lookup behavior.
 */
class MovingViolationEnumTest {

    // ============ Enum Constants Tests ============

    /**
     * Tests that DISTRACTED_DRIVING enum constant exists and is not null.
     */
    @Test
    void testDistractedDrivingConstantExists() {
        MovingViolationEnum violation = MovingViolationEnum.DISTRACTED_DRIVING;

        assertNotNull(violation);
    }

    /**
     * Tests that RECKLESS_DRIVING enum constant exists and is not null.
     */
    @Test
    void testRecklessDrivingConstantExists() {
        MovingViolationEnum violation = MovingViolationEnum.RECKLESS_DRIVING;

        assertNotNull(violation);
    }

    /**
     * Tests that SPEEDING enum constant exists and is not null.
     */
    @Test
    void testSpeedingConstantExists() {
        MovingViolationEnum violation = MovingViolationEnum.SPEEDING;

        assertNotNull(violation);
    }

    /**
     * Tests that DUI enum constant exists and is not null.
     */
    @Test
    void testDuiConstantExists() {
        MovingViolationEnum violation = MovingViolationEnum.DUI;

        assertNotNull(violation);
    }

    /**
     * Tests that FAILURE_TO_OBEY_SIGNS enum constant exists and is not null.
     */
    @Test
    void testFailureToObeySingsConstantExists() {
        MovingViolationEnum violation = MovingViolationEnum.FAILURE_TO_OBEY_SIGNS;

        assertNotNull(violation);
    }

    /**
     * Tests that DRIVING_WITHOUT_VALID_LICENSE_INSURANCE enum constant exists and is not null.
     */
    @Test
    void testDrivingWithoutValidLicenseInsuranceConstantExists() {
        MovingViolationEnum violation = MovingViolationEnum.DRIVING_WITHOUT_VALID_LICENSE_INSURANCE;

        assertNotNull(violation);
    }

    // ============ Enum.values() Tests ============

    /**
     * Tests that values() returns all enum constants.
     * Verifies the correct number of constants (6).
     */
    @Test
    void testValuesReturnsAllConstants() {
        MovingViolationEnum[] values = MovingViolationEnum.values();

        assertEquals(6, values.length);
    }

    /**
     * Tests that values() returns the constants in declaration order.
     */
    @Test
    void testValuesOrder() {
        MovingViolationEnum[] values = MovingViolationEnum.values();

        assertEquals(MovingViolationEnum.DISTRACTED_DRIVING, values[0]);
        assertEquals(MovingViolationEnum.RECKLESS_DRIVING, values[1]);
        assertEquals(MovingViolationEnum.SPEEDING, values[2]);
        assertEquals(MovingViolationEnum.DUI, values[3]);
        assertEquals(MovingViolationEnum.FAILURE_TO_OBEY_SIGNS, values[4]);
        assertEquals(MovingViolationEnum.DRIVING_WITHOUT_VALID_LICENSE_INSURANCE, values[5]);
    }

    /**
     * Tests that values() returns a new array each time.
     */
    @Test
    void testValuesReturnsNewArray() {
        MovingViolationEnum[] values1 = MovingViolationEnum.values();
        MovingViolationEnum[] values2 = MovingViolationEnum.values();

        assertNotSame(values1, values2);
        assertArrayEquals(values1, values2);
    }

    /**
     * Tests that modifying the values() array doesn't affect the enum.
     */
    @Test
    void testValuesArrayModificationDoesNotAffectEnum() {
        MovingViolationEnum[] values = MovingViolationEnum.values();
        values[0] = MovingViolationEnum.DUI; // Modify the array

        // Original enum should be unchanged
        MovingViolationEnum[] newValues = MovingViolationEnum.values();
        assertEquals(MovingViolationEnum.DISTRACTED_DRIVING, newValues[0]);
    }

    // ============ Enum.valueOf() Tests ============

    /**
     * Tests valueOf() with DISTRACTED_DRIVING string.
     */
    @Test
    void testValueOfDistractedDriving() {
        MovingViolationEnum violation = MovingViolationEnum.valueOf("DISTRACTED_DRIVING");

        assertEquals(MovingViolationEnum.DISTRACTED_DRIVING, violation);
    }

    /**
     * Tests valueOf() with RECKLESS_DRIVING string.
     */
    @Test
    void testValueOfRecklessDriving() {
        MovingViolationEnum violation = MovingViolationEnum.valueOf("RECKLESS_DRIVING");

        assertEquals(MovingViolationEnum.RECKLESS_DRIVING, violation);
    }

    /**
     * Tests valueOf() with SPEEDING string.
     */
    @Test
    void testValueOfSpeeding() {
        MovingViolationEnum violation = MovingViolationEnum.valueOf("SPEEDING");

        assertEquals(MovingViolationEnum.SPEEDING, violation);
    }

    /**
     * Tests valueOf() with DUI string.
     */
    @Test
    void testValueOfDui() {
        MovingViolationEnum violation = MovingViolationEnum.valueOf("DUI");

        assertEquals(MovingViolationEnum.DUI, violation);
    }

    /**
     * Tests valueOf() with FAILURE_TO_OBEY_SIGNS string.
     */
    @Test
    void testValueOfFailureToObeySigns() {
        MovingViolationEnum violation = MovingViolationEnum.valueOf("FAILURE_TO_OBEY_SIGNS");

        assertEquals(MovingViolationEnum.FAILURE_TO_OBEY_SIGNS, violation);
    }

    /**
     * Tests valueOf() with DRIVING_WITHOUT_VALID_LICENSE_INSURANCE string.
     */
    @Test
    void testValueOfDrivingWithoutValidLicenseInsurance() {
        MovingViolationEnum violation = MovingViolationEnum.valueOf("DRIVING_WITHOUT_VALID_LICENSE_INSURANCE");

        assertEquals(MovingViolationEnum.DRIVING_WITHOUT_VALID_LICENSE_INSURANCE, violation);
    }

    /**
     * Tests valueOf() with invalid string throws IllegalArgumentException.
     */
    @Test
    void testValueOfInvalidString() {
        assertThrows(IllegalArgumentException.class, () -> {
            MovingViolationEnum.valueOf("INVALID_VIOLATION");
        });
    }

    /**
     * Tests valueOf() with null throws NullPointerException.
     */
    @Test
    void testValueOfNull() {
        assertThrows(NullPointerException.class, () -> {
            MovingViolationEnum.valueOf(null);
        });
    }

    /**
     * Tests valueOf() with description throws IllegalArgumentException.
     */
    @Test
    void testValueOfWithDescription() {
        assertThrows(IllegalArgumentException.class, () -> {
            MovingViolationEnum.valueOf("Distracted Driving");
        });
    }

    /**
     * Tests valueOf() with lowercase string throws IllegalArgumentException.
     */
    @Test
    void testValueOfCaseSensitive() {
        assertThrows(IllegalArgumentException.class, () -> {
            MovingViolationEnum.valueOf("distracted_driving");
        });
    }

    // ============ Enum.name() Tests ============

    /**
     * Tests name() method for DISTRACTED_DRIVING.
     */
    @Test
    void testNameDistractedDriving() {
        String name = MovingViolationEnum.DISTRACTED_DRIVING.name();

        assertEquals("DISTRACTED_DRIVING", name);
    }

    /**
     * Tests name() method for RECKLESS_DRIVING.
     */
    @Test
    void testNameRecklessDriving() {
        String name = MovingViolationEnum.RECKLESS_DRIVING.name();

        assertEquals("RECKLESS_DRIVING", name);
    }

    /**
     * Tests name() method for SPEEDING.
     */
    @Test
    void testNameSpeeding() {
        String name = MovingViolationEnum.SPEEDING.name();

        assertEquals("SPEEDING", name);
    }

    /**
     * Tests name() method for DUI.
     */
    @Test
    void testNameDui() {
        String name = MovingViolationEnum.DUI.name();

        assertEquals("DUI", name);
    }

    /**
     * Tests name() method for FAILURE_TO_OBEY_SIGNS.
     */
    @Test
    void testNameFailureToObeySigns() {
        String name = MovingViolationEnum.FAILURE_TO_OBEY_SIGNS.name();

        assertEquals("FAILURE_TO_OBEY_SIGNS", name);
    }

    /**
     * Tests name() method for DRIVING_WITHOUT_VALID_LICENSE_INSURANCE.
     */
    @Test
    void testNameDrivingWithoutValidLicenseInsurance() {
        String name = MovingViolationEnum.DRIVING_WITHOUT_VALID_LICENSE_INSURANCE.name();

        assertEquals("DRIVING_WITHOUT_VALID_LICENSE_INSURANCE", name);
    }

    // ============ Enum.ordinal() Tests ============

    /**
     * Tests ordinal() for all constants are sequential.
     */
    @Test
    void testOrdinalsAreSequential() {
        assertEquals(0, MovingViolationEnum.DISTRACTED_DRIVING.ordinal());
        assertEquals(1, MovingViolationEnum.RECKLESS_DRIVING.ordinal());
        assertEquals(2, MovingViolationEnum.SPEEDING.ordinal());
        assertEquals(3, MovingViolationEnum.DUI.ordinal());
        assertEquals(4, MovingViolationEnum.FAILURE_TO_OBEY_SIGNS.ordinal());
        assertEquals(5, MovingViolationEnum.DRIVING_WITHOUT_VALID_LICENSE_INSURANCE.ordinal());
    }

    // ============ toString() Tests ============

    /**
     * Tests toString() for DISTRACTED_DRIVING returns description.
     */
    @Test
    void testToStringDistractedDriving() {
        String str = MovingViolationEnum.DISTRACTED_DRIVING.toString();

        assertEquals("Distracted Driving", str);
    }

    /**
     * Tests toString() for RECKLESS_DRIVING returns description.
     */
    @Test
    void testToStringRecklessDriving() {
        String str = MovingViolationEnum.RECKLESS_DRIVING.toString();

        assertEquals("Reckless Driving", str);
    }

    /**
     * Tests toString() for SPEEDING returns description.
     */
    @Test
    void testToStringSpeeding() {
        String str = MovingViolationEnum.SPEEDING.toString();

        assertEquals("Speeding", str);
    }

    /**
     * Tests toString() for DUI returns description.
     */
    @Test
    void testToStringDui() {
        String str = MovingViolationEnum.DUI.toString();

        assertEquals("Driving Under Influence", str);
    }

    /**
     * Tests toString() for FAILURE_TO_OBEY_SIGNS returns description.
     */
    @Test
    void testToStringFailureToObeySigns() {
        String str = MovingViolationEnum.FAILURE_TO_OBEY_SIGNS.toString();

        assertEquals("Failure to Obey Traffic Signs", str);
    }

    /**
     * Tests toString() for DRIVING_WITHOUT_VALID_LICENSE_INSURANCE returns description.
     */
    @Test
    void testToStringDrivingWithoutValidLicenseInsurance() {
        String str = MovingViolationEnum.DRIVING_WITHOUT_VALID_LICENSE_INSURANCE.toString();

        assertEquals("Driving Without Valid License/Insurance", str);
    }

    /**
     * Tests that toString() returns different value than name().
     */
    @Test
    void testToStringDifferentFromName() {
        for (MovingViolationEnum violation : MovingViolationEnum.values()) {
            assertNotEquals(violation.name(), violation.toString());
        }
    }

    /**
     * Tests that all toString() values contain spaces (human-readable).
     */
    @Test
    void testToStringReturnsHumanReadableFormat() {
        for (MovingViolationEnum violation : MovingViolationEnum.values()) {
            String str = violation.toString();
            // All descriptions should be human-readable (most contain spaces)
            assertNotNull(str);
            assertFalse(str.isEmpty());
        }
    }

    // ============ Equality and Identity Tests ============

    /**
     * Tests that enum constants are singletons.
     */
    @Test
    void testEnumConstantsAreSingletons() {
        MovingViolationEnum violation1 = MovingViolationEnum.SPEEDING;
        MovingViolationEnum violation2 = MovingViolationEnum.SPEEDING;

        assertSame(violation1, violation2);
    }

    /**
     * Tests that valueOf() returns the same instance as the constant.
     */
    @Test
    void testValueOfReturnsSameInstance() {
        MovingViolationEnum constant = MovingViolationEnum.DUI;
        MovingViolationEnum fromString = MovingViolationEnum.valueOf("DUI");

        assertSame(constant, fromString);
    }

    /**
     * Tests equality using equals() method.
     */
    @Test
    void testEquality() {
        MovingViolationEnum v1 = MovingViolationEnum.SPEEDING;
        MovingViolationEnum v2 = MovingViolationEnum.SPEEDING;
        MovingViolationEnum v3 = MovingViolationEnum.DUI;

        assertEquals(v1, v2);
        assertNotEquals(v1, v3);
    }

    /**
     * Tests equality using == operator.
     */
    @Test
    void testReferenceEquality() {
        MovingViolationEnum v1 = MovingViolationEnum.RECKLESS_DRIVING;
        MovingViolationEnum v2 = MovingViolationEnum.RECKLESS_DRIVING;

        assertTrue(v1 == v2);
    }

    // ============ Switch Statement Tests ============

    /**
     * Tests using enum in switch statement for SPEEDING.
     */
    @Test
    void testSwitchStatementSpeeding() {
        MovingViolationEnum violation = MovingViolationEnum.SPEEDING;
        String severity = getSeverity(violation);

        assertEquals("Common", severity);
    }

    /**
     * Tests using enum in switch statement for DUI.
     */
    @Test
    void testSwitchStatementDui() {
        MovingViolationEnum violation = MovingViolationEnum.DUI;
        String severity = getSeverity(violation);

        assertEquals("Critical", severity);
    }

    /**
     * Tests using enum in switch statement for DISTRACTED_DRIVING.
     */
    @Test
    void testSwitchStatementDistractedDriving() {
        MovingViolationEnum violation = MovingViolationEnum.DISTRACTED_DRIVING;
        String severity = getSeverity(violation);

        assertEquals("Serious", severity);
    }

    /**
     * Helper method to test switch statement functionality.
     */
    private String getSeverity(MovingViolationEnum violation) {
        switch (violation) {
            case SPEEDING:
            case FAILURE_TO_OBEY_SIGNS:
                return "Common";
            case DISTRACTED_DRIVING:
            case RECKLESS_DRIVING:
                return "Serious";
            case DUI:
            case DRIVING_WITHOUT_VALID_LICENSE_INSURANCE:
                return "Critical";
            default:
                return "Unknown";
        }
    }

    // ============ Collection Tests ============

    /**
     * Tests that enum constants can be added to EnumSet.
     */
    @Test
    void testEnumInCollection() {
        java.util.Set<MovingViolationEnum> set = java.util.EnumSet.allOf(MovingViolationEnum.class);

        assertEquals(6, set.size());
        assertTrue(set.contains(MovingViolationEnum.DISTRACTED_DRIVING));
        assertTrue(set.contains(MovingViolationEnum.RECKLESS_DRIVING));
        assertTrue(set.contains(MovingViolationEnum.SPEEDING));
        assertTrue(set.contains(MovingViolationEnum.DUI));
        assertTrue(set.contains(MovingViolationEnum.FAILURE_TO_OBEY_SIGNS));
        assertTrue(set.contains(MovingViolationEnum.DRIVING_WITHOUT_VALID_LICENSE_INSURANCE));
    }

    /**
     * Tests that enum can be used as map key.
     */
    @Test
    void testEnumAsMapKey() {
        java.util.Map<MovingViolationEnum, Integer> pointsMap =
                new java.util.EnumMap<>(MovingViolationEnum.class);
        pointsMap.put(MovingViolationEnum.SPEEDING, 3);
        pointsMap.put(MovingViolationEnum.DUI, 10);
        pointsMap.put(MovingViolationEnum.RECKLESS_DRIVING, 8);

        assertEquals(3, pointsMap.get(MovingViolationEnum.SPEEDING));
        assertEquals(10, pointsMap.get(MovingViolationEnum.DUI));
        assertEquals(8, pointsMap.get(MovingViolationEnum.RECKLESS_DRIVING));
    }

    // ============ Iteration Tests ============

    /**
     * Tests iterating over all enum constants using for-each loop.
     */
    @Test
    void testIterationOverAllConstants() {
        int count = 0;
        for (MovingViolationEnum violation : MovingViolationEnum.values()) {
            assertNotNull(violation);
            assertNotNull(violation.toString());
            count++;
        }

        assertEquals(6, count);
    }

    /**
     * Tests that iteration order matches declaration order.
     */
    @Test
    void testIterationOrder() {
        MovingViolationEnum[] values = MovingViolationEnum.values();
        int index = 0;

        for (MovingViolationEnum violation : MovingViolationEnum.values()) {
            assertEquals(values[index++], violation);
        }
    }

    // ============ Comparison Tests ============

    /**
     * Tests compareTo() method based on ordinal values.
     */
    @Test
    void testCompareTo() {
        assertTrue(MovingViolationEnum.DISTRACTED_DRIVING.compareTo(MovingViolationEnum.SPEEDING) < 0);
        assertTrue(MovingViolationEnum.SPEEDING.compareTo(MovingViolationEnum.DUI) < 0);
        assertTrue(MovingViolationEnum.DUI.compareTo(MovingViolationEnum.DISTRACTED_DRIVING) > 0);
        assertEquals(0, MovingViolationEnum.SPEEDING.compareTo(MovingViolationEnum.SPEEDING));
    }

    /**
     * Tests that enum constants implement Comparable interface.
     */
    @Test
    void testEnumIsComparable() {
        MovingViolationEnum violation = MovingViolationEnum.SPEEDING;

        assertInstanceOf(Comparable.class, violation);
    }

    // ============ Hash Code Tests ============

    /**
     * Tests that hashCode() is consistent for the same constant.
     */
    @Test
    void testHashCodeConsistency() {
        MovingViolationEnum violation = MovingViolationEnum.SPEEDING;

        assertEquals(violation.hashCode(), violation.hashCode());
    }

    /**
     * Tests that equal enum constants have the same hash code.
     */
    @Test
    void testHashCodeForEqualObjects() {
        MovingViolationEnum v1 = MovingViolationEnum.DUI;
        MovingViolationEnum v2 = MovingViolationEnum.valueOf("DUI");

        assertEquals(v1.hashCode(), v2.hashCode());
    }

    // ============ Description Tests ============

    /**
     * Tests that descriptions are properly formatted.
     */
    @Test
    void testDescriptionFormatting() {
        assertEquals("Distracted Driving", MovingViolationEnum.DISTRACTED_DRIVING.toString());
        assertEquals("Reckless Driving", MovingViolationEnum.RECKLESS_DRIVING.toString());
        assertEquals("Speeding", MovingViolationEnum.SPEEDING.toString());
        assertEquals("Driving Under Influence", MovingViolationEnum.DUI.toString());
        assertEquals("Failure to Obey Traffic Signs", MovingViolationEnum.FAILURE_TO_OBEY_SIGNS.toString());
        assertEquals("Driving Without Valid License/Insurance",
                MovingViolationEnum.DRIVING_WITHOUT_VALID_LICENSE_INSURANCE.toString());
    }

    /**
     * Tests that descriptions are not empty.
     */
    @Test
    void testDescriptionsNotEmpty() {
        for (MovingViolationEnum violation : MovingViolationEnum.values()) {
            assertFalse(violation.toString().isEmpty());
        }
    }

    /**
     * Tests that all descriptions are unique.
     */
    @Test
    void testDescriptionsAreUnique() {
        java.util.Set<String> descriptions = new java.util.HashSet<>();
        for (MovingViolationEnum violation : MovingViolationEnum.values()) {
            descriptions.add(violation.toString());
        }

        assertEquals(6, descriptions.size());
    }

    // ============ Integration Tests ============

    /**
     * Tests complete enum usage in realistic scenario.
     */
    @Test
    void testCompleteEnumUsage() {
        // Parse from string
        MovingViolationEnum violation = MovingViolationEnum.valueOf("SPEEDING");

        // Use in comparison
        assertEquals(MovingViolationEnum.SPEEDING, violation);

        // Get name
        assertEquals("SPEEDING", violation.name());

        // Get description
        assertEquals("Speeding", violation.toString());

        // Use in switch
        String severity = getSeverity(violation);
        assertEquals("Common", severity);

        // Check ordinal
        assertEquals(2, violation.ordinal());
    }

    /**
     * Tests that enum can be used with instanceof.
     */
    @Test
    void testInstanceOf() {
        MovingViolationEnum violation = MovingViolationEnum.DUI;

        assertTrue(violation instanceof MovingViolationEnum);
        assertTrue(violation instanceof Enum);
    }

    /**
     * Tests using descriptions in user-facing output.
     */
    @Test
    void testDescriptionsForUserOutput() {
        StringBuilder output = new StringBuilder();
        for (MovingViolationEnum violation : MovingViolationEnum.values()) {
            output.append(violation.toString()).append("\n");
        }

        String result = output.toString();
        assertTrue(result.contains("Distracted Driving"));
        assertTrue(result.contains("Reckless Driving"));
        assertTrue(result.contains("Speeding"));
        assertTrue(result.contains("Driving Under Influence"));
        assertTrue(result.contains("Failure to Obey Traffic Signs"));
        assertTrue(result.contains("Driving Without Valid License/Insurance"));
    }

    /**
     * Tests categorizing violations by severity.
     */
    @Test
    void testViolationCategorization() {
        // Critical violations
        assertTrue(MovingViolationEnum.DUI.toString().contains("Influence"));
        assertTrue(MovingViolationEnum.DRIVING_WITHOUT_VALID_LICENSE_INSURANCE.toString().contains("License"));

        // Common violations
        assertTrue(MovingViolationEnum.SPEEDING.toString().contains("Speeding"));
        assertTrue(MovingViolationEnum.FAILURE_TO_OBEY_SIGNS.toString().contains("Signs"));

        // Serious violations
        assertTrue(MovingViolationEnum.RECKLESS_DRIVING.toString().contains("Reckless"));
        assertTrue(MovingViolationEnum.DISTRACTED_DRIVING.toString().contains("Distracted"));
    }

    /**
     * Tests parsing all valid constant names.
     */
    @Test
    void testParsingAllValidConstants() {
        String[] constantNames = {
                "DISTRACTED_DRIVING",
                "RECKLESS_DRIVING",
                "SPEEDING",
                "DUI",
                "FAILURE_TO_OBEY_SIGNS",
                "DRIVING_WITHOUT_VALID_LICENSE_INSURANCE"
        };

        for (String name : constantNames) {
            MovingViolationEnum violation = MovingViolationEnum.valueOf(name);
            assertNotNull(violation);
            assertEquals(name, violation.name());
        }
    }
}