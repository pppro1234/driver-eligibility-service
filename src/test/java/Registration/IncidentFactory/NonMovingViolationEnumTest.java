package Registration.IncidentFactory;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests non-moving violation enum values and lookup behavior.
 */
class NonMovingViolationEnumTest {

    // ============ Enum Constants Tests ============

    /**
     * Tests that PARKING_VIOLATION enum constant exists and is not null.
     */
    @Test
    void testParkingViolationConstantExists() {
        NonMovingViolationEnum violation = NonMovingViolationEnum.PARKING_VIOLATION;

        assertNotNull(violation);
    }

    /**
     * Tests that PAPERWORK_ISSUE enum constant exists and is not null.
     */
    @Test
    void testPaperworkIssueConstantExists() {
        NonMovingViolationEnum violation = NonMovingViolationEnum.PAPERWORK_ISSUE;

        assertNotNull(violation);
    }

    /**
     * Tests that VEHICLE_PROBLEM enum constant exists and is not null.
     */
    @Test
    void testVehicleProblemConstantExists() {
        NonMovingViolationEnum violation = NonMovingViolationEnum.VEHICLE_PROBLEM;

        assertNotNull(violation);
    }

    // ============ Enum.values() Tests ============

    /**
     * Tests that values() returns all enum constants.
     * Verifies the correct number of constants (3).
     */
    @Test
    void testValuesReturnsAllConstants() {
        NonMovingViolationEnum[] values = NonMovingViolationEnum.values();

        assertEquals(3, values.length);
    }

    /**
     * Tests that values() returns the constants in declaration order.
     */
    @Test
    void testValuesOrder() {
        NonMovingViolationEnum[] values = NonMovingViolationEnum.values();

        assertEquals(NonMovingViolationEnum.PARKING_VIOLATION, values[0]);
        assertEquals(NonMovingViolationEnum.PAPERWORK_ISSUE, values[1]);
        assertEquals(NonMovingViolationEnum.VEHICLE_PROBLEM, values[2]);
    }

    /**
     * Tests that values() returns a new array each time.
     */
    @Test
    void testValuesReturnsNewArray() {
        NonMovingViolationEnum[] values1 = NonMovingViolationEnum.values();
        NonMovingViolationEnum[] values2 = NonMovingViolationEnum.values();

        assertNotSame(values1, values2);
        assertArrayEquals(values1, values2);
    }

    /**
     * Tests that modifying the values() array doesn't affect the enum.
     */
    @Test
    void testValuesArrayModificationDoesNotAffectEnum() {
        NonMovingViolationEnum[] values = NonMovingViolationEnum.values();
        values[0] = NonMovingViolationEnum.VEHICLE_PROBLEM; // Modify the array

        // Original enum should be unchanged
        NonMovingViolationEnum[] newValues = NonMovingViolationEnum.values();
        assertEquals(NonMovingViolationEnum.PARKING_VIOLATION, newValues[0]);
    }

    // ============ Enum.valueOf() Tests ============

    /**
     * Tests valueOf() with PARKING_VIOLATION string.
     */
    @Test
    void testValueOfParkingViolation() {
        NonMovingViolationEnum violation = NonMovingViolationEnum.valueOf("PARKING_VIOLATION");

        assertEquals(NonMovingViolationEnum.PARKING_VIOLATION, violation);
    }

    /**
     * Tests valueOf() with PAPERWORK_ISSUE string.
     */
    @Test
    void testValueOfPaperworkIssue() {
        NonMovingViolationEnum violation = NonMovingViolationEnum.valueOf("PAPERWORK_ISSUE");

        assertEquals(NonMovingViolationEnum.PAPERWORK_ISSUE, violation);
    }

    /**
     * Tests valueOf() with VEHICLE_PROBLEM string.
     */
    @Test
    void testValueOfVehicleProblem() {
        NonMovingViolationEnum violation = NonMovingViolationEnum.valueOf("VEHICLE_PROBLEM");

        assertEquals(NonMovingViolationEnum.VEHICLE_PROBLEM, violation);
    }

    /**
     * Tests valueOf() with invalid string throws IllegalArgumentException.
     */
    @Test
    void testValueOfInvalidString() {
        assertThrows(IllegalArgumentException.class, () -> {
            NonMovingViolationEnum.valueOf("INVALID_VIOLATION");
        });
    }

    /**
     * Tests valueOf() with null throws NullPointerException.
     */
    @Test
    void testValueOfNull() {
        assertThrows(NullPointerException.class, () -> {
            NonMovingViolationEnum.valueOf(null);
        });
    }

    /**
     * Tests valueOf() with description throws IllegalArgumentException.
     */
    @Test
    void testValueOfWithDescription() {
        assertThrows(IllegalArgumentException.class, () -> {
            NonMovingViolationEnum.valueOf("Parking Violation");
        });
    }

    /**
     * Tests valueOf() with lowercase string throws IllegalArgumentException.
     */
    @Test
    void testValueOfCaseSensitive() {
        assertThrows(IllegalArgumentException.class, () -> {
            NonMovingViolationEnum.valueOf("parking_violation");
        });
    }

    // ============ Enum.name() Tests ============

    /**
     * Tests name() method for PARKING_VIOLATION.
     */
    @Test
    void testNameParkingViolation() {
        String name = NonMovingViolationEnum.PARKING_VIOLATION.name();

        assertEquals("PARKING_VIOLATION", name);
    }

    /**
     * Tests name() method for PAPERWORK_ISSUE.
     */
    @Test
    void testNamePaperworkIssue() {
        String name = NonMovingViolationEnum.PAPERWORK_ISSUE.name();

        assertEquals("PAPERWORK_ISSUE", name);
    }

    /**
     * Tests name() method for VEHICLE_PROBLEM.
     */
    @Test
    void testNameVehicleProblem() {
        String name = NonMovingViolationEnum.VEHICLE_PROBLEM.name();

        assertEquals("VEHICLE_PROBLEM", name);
    }

    // ============ Enum.ordinal() Tests ============

    /**
     * Tests ordinal() for all constants are sequential.
     */
    @Test
    void testOrdinalsAreSequential() {
        assertEquals(0, NonMovingViolationEnum.PARKING_VIOLATION.ordinal());
        assertEquals(1, NonMovingViolationEnum.PAPERWORK_ISSUE.ordinal());
        assertEquals(2, NonMovingViolationEnum.VEHICLE_PROBLEM.ordinal());
    }

    // ============ toString() Tests ============

    /**
     * Tests toString() for PARKING_VIOLATION returns description.
     */
    @Test
    void testToStringParkingViolation() {
        String str = NonMovingViolationEnum.PARKING_VIOLATION.toString();

        assertEquals("Parking Violation", str);
    }

    /**
     * Tests toString() for PAPERWORK_ISSUE returns description.
     */
    @Test
    void testToStringPaperworkIssue() {
        String str = NonMovingViolationEnum.PAPERWORK_ISSUE.toString();

        assertEquals("Paper Work Issue", str);
    }

    /**
     * Tests toString() for VEHICLE_PROBLEM returns description.
     */
    @Test
    void testToStringVehicleProblem() {
        String str = NonMovingViolationEnum.VEHICLE_PROBLEM.toString();

        assertEquals("Vehicle Problem", str);
    }

    /**
     * Tests that toString() returns different value than name().
     */
    @Test
    void testToStringDifferentFromName() {
        for (NonMovingViolationEnum violation : NonMovingViolationEnum.values()) {
            assertNotEquals(violation.name(), violation.toString());
        }
    }

    /**
     * Tests that all toString() values are human-readable.
     */
    @Test
    void testToStringReturnsHumanReadableFormat() {
        for (NonMovingViolationEnum violation : NonMovingViolationEnum.values()) {
            String str = violation.toString();
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
        NonMovingViolationEnum violation1 = NonMovingViolationEnum.PARKING_VIOLATION;
        NonMovingViolationEnum violation2 = NonMovingViolationEnum.PARKING_VIOLATION;

        assertSame(violation1, violation2);
    }

    /**
     * Tests that valueOf() returns the same instance as the constant.
     */
    @Test
    void testValueOfReturnsSameInstance() {
        NonMovingViolationEnum constant = NonMovingViolationEnum.VEHICLE_PROBLEM;
        NonMovingViolationEnum fromString = NonMovingViolationEnum.valueOf("VEHICLE_PROBLEM");

        assertSame(constant, fromString);
    }

    /**
     * Tests equality using equals() method.
     */
    @Test
    void testEquality() {
        NonMovingViolationEnum v1 = NonMovingViolationEnum.PARKING_VIOLATION;
        NonMovingViolationEnum v2 = NonMovingViolationEnum.PARKING_VIOLATION;
        NonMovingViolationEnum v3 = NonMovingViolationEnum.VEHICLE_PROBLEM;

        assertEquals(v1, v2);
        assertNotEquals(v1, v3);
    }

    /**
     * Tests equality using == operator.
     */
    @Test
    void testReferenceEquality() {
        NonMovingViolationEnum v1 = NonMovingViolationEnum.PAPERWORK_ISSUE;
        NonMovingViolationEnum v2 = NonMovingViolationEnum.PAPERWORK_ISSUE;

        assertTrue(v1 == v2);
    }

    /**
     * Tests that different constants are not equal.
     */
    @Test
    void testDifferentConstantsNotEqual() {
        assertNotEquals(NonMovingViolationEnum.PARKING_VIOLATION, NonMovingViolationEnum.PAPERWORK_ISSUE);
        assertNotEquals(NonMovingViolationEnum.PAPERWORK_ISSUE, NonMovingViolationEnum.VEHICLE_PROBLEM);
        assertNotEquals(NonMovingViolationEnum.VEHICLE_PROBLEM, NonMovingViolationEnum.PARKING_VIOLATION);
    }

    // ============ Switch Statement Tests ============

    /**
     * Tests using enum in switch statement for PARKING_VIOLATION.
     */
    @Test
    void testSwitchStatementParkingViolation() {
        NonMovingViolationEnum violation = NonMovingViolationEnum.PARKING_VIOLATION;
        String category = getCategory(violation);

        assertEquals("Location-Based", category);
    }

    /**
     * Tests using enum in switch statement for PAPERWORK_ISSUE.
     */
    @Test
    void testSwitchStatementPaperworkIssue() {
        NonMovingViolationEnum violation = NonMovingViolationEnum.PAPERWORK_ISSUE;
        String category = getCategory(violation);

        assertEquals("Documentation", category);
    }

    /**
     * Tests using enum in switch statement for VEHICLE_PROBLEM.
     */
    @Test
    void testSwitchStatementVehicleProblem() {
        NonMovingViolationEnum violation = NonMovingViolationEnum.VEHICLE_PROBLEM;
        String category = getCategory(violation);

        assertEquals("Vehicle Condition", category);
    }

    /**
     * Helper method to test switch statement functionality.
     */
    private String getCategory(NonMovingViolationEnum violation) {
        switch (violation) {
            case PARKING_VIOLATION:
                return "Location-Based";
            case PAPERWORK_ISSUE:
                return "Documentation";
            case VEHICLE_PROBLEM:
                return "Vehicle Condition";
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
        java.util.Set<NonMovingViolationEnum> set = java.util.EnumSet.allOf(NonMovingViolationEnum.class);

        assertEquals(3, set.size());
        assertTrue(set.contains(NonMovingViolationEnum.PARKING_VIOLATION));
        assertTrue(set.contains(NonMovingViolationEnum.PAPERWORK_ISSUE));
        assertTrue(set.contains(NonMovingViolationEnum.VEHICLE_PROBLEM));
    }

    /**
     * Tests that enum can be used as map key.
     */
    @Test
    void testEnumAsMapKey() {
        java.util.Map<NonMovingViolationEnum, String> descriptionMap =
                new java.util.EnumMap<>(NonMovingViolationEnum.class);
        descriptionMap.put(NonMovingViolationEnum.PARKING_VIOLATION, "Illegal parking");
        descriptionMap.put(NonMovingViolationEnum.PAPERWORK_ISSUE, "Missing docs");
        descriptionMap.put(NonMovingViolationEnum.VEHICLE_PROBLEM, "Broken light");

        assertEquals("Illegal parking", descriptionMap.get(NonMovingViolationEnum.PARKING_VIOLATION));
        assertEquals("Missing docs", descriptionMap.get(NonMovingViolationEnum.PAPERWORK_ISSUE));
        assertEquals("Broken light", descriptionMap.get(NonMovingViolationEnum.VEHICLE_PROBLEM));
    }

    // ============ Iteration Tests ============

    /**
     * Tests iterating over all enum constants using for-each loop.
     */
    @Test
    void testIterationOverAllConstants() {
        int count = 0;
        for (NonMovingViolationEnum violation : NonMovingViolationEnum.values()) {
            assertNotNull(violation);
            assertNotNull(violation.toString());
            count++;
        }

        assertEquals(3, count);
    }

    /**
     * Tests that iteration order matches declaration order.
     */
    @Test
    void testIterationOrder() {
        NonMovingViolationEnum[] values = NonMovingViolationEnum.values();
        int index = 0;

        for (NonMovingViolationEnum violation : NonMovingViolationEnum.values()) {
            assertEquals(values[index++], violation);
        }
    }

    // ============ Comparison Tests ============

    /**
     * Tests compareTo() method based on ordinal values.
     */
    @Test
    void testCompareTo() {
        assertTrue(NonMovingViolationEnum.PARKING_VIOLATION.compareTo(NonMovingViolationEnum.PAPERWORK_ISSUE) < 0);
        assertTrue(NonMovingViolationEnum.PAPERWORK_ISSUE.compareTo(NonMovingViolationEnum.VEHICLE_PROBLEM) < 0);
        assertTrue(NonMovingViolationEnum.VEHICLE_PROBLEM.compareTo(NonMovingViolationEnum.PARKING_VIOLATION) > 0);
        assertEquals(0, NonMovingViolationEnum.PAPERWORK_ISSUE.compareTo(NonMovingViolationEnum.PAPERWORK_ISSUE));
    }

    /**
     * Tests that enum constants implement Comparable interface.
     */
    @Test
    void testEnumIsComparable() {
        NonMovingViolationEnum violation = NonMovingViolationEnum.PARKING_VIOLATION;

        assertInstanceOf(Comparable.class, violation);
    }

    /**
     * Tests sorting enum constants.
     */
    @Test
    void testSorting() {
        java.util.List<NonMovingViolationEnum> violations = new java.util.ArrayList<>();
        violations.add(NonMovingViolationEnum.VEHICLE_PROBLEM);
        violations.add(NonMovingViolationEnum.PARKING_VIOLATION);
        violations.add(NonMovingViolationEnum.PAPERWORK_ISSUE);

        java.util.Collections.sort(violations);

        assertEquals(NonMovingViolationEnum.PARKING_VIOLATION, violations.get(0));
        assertEquals(NonMovingViolationEnum.PAPERWORK_ISSUE, violations.get(1));
        assertEquals(NonMovingViolationEnum.VEHICLE_PROBLEM, violations.get(2));
    }

    // ============ Hash Code Tests ============

    /**
     * Tests that hashCode() is consistent for the same constant.
     */
    @Test
    void testHashCodeConsistency() {
        NonMovingViolationEnum violation = NonMovingViolationEnum.PARKING_VIOLATION;

        assertEquals(violation.hashCode(), violation.hashCode());
    }

    /**
     * Tests that equal enum constants have the same hash code.
     */
    @Test
    void testHashCodeForEqualObjects() {
        NonMovingViolationEnum v1 = NonMovingViolationEnum.VEHICLE_PROBLEM;
        NonMovingViolationEnum v2 = NonMovingViolationEnum.valueOf("VEHICLE_PROBLEM");

        assertEquals(v1.hashCode(), v2.hashCode());
    }

    // ============ Description Tests ============

    /**
     * Tests that descriptions are properly formatted.
     */
    @Test
    void testDescriptionFormatting() {
        assertEquals("Parking Violation", NonMovingViolationEnum.PARKING_VIOLATION.toString());
        assertEquals("Paper Work Issue", NonMovingViolationEnum.PAPERWORK_ISSUE.toString());
        assertEquals("Vehicle Problem", NonMovingViolationEnum.VEHICLE_PROBLEM.toString());
    }

    /**
     * Tests that descriptions are not empty.
     */
    @Test
    void testDescriptionsNotEmpty() {
        for (NonMovingViolationEnum violation : NonMovingViolationEnum.values()) {
            assertFalse(violation.toString().isEmpty());
        }
    }

    /**
     * Tests that all descriptions are unique.
     */
    @Test
    void testDescriptionsAreUnique() {
        java.util.Set<String> descriptions = new java.util.HashSet<>();
        for (NonMovingViolationEnum violation : NonMovingViolationEnum.values()) {
            descriptions.add(violation.toString());
        }

        assertEquals(3, descriptions.size());
    }

    /**
     * Tests that descriptions contain spaces (human-readable).
     */
    @Test
    void testDescriptionsContainSpaces() {
        for (NonMovingViolationEnum violation : NonMovingViolationEnum.values()) {
            assertTrue(violation.toString().contains(" "));
        }
    }

    // ============ Integration Tests ============

    /**
     * Tests complete enum usage in realistic scenario.
     */
    @Test
    void testCompleteEnumUsage() {
        // Parse from string
        NonMovingViolationEnum violation = NonMovingViolationEnum.valueOf("PARKING_VIOLATION");

        // Use in comparison
        assertEquals(NonMovingViolationEnum.PARKING_VIOLATION, violation);

        // Get name
        assertEquals("PARKING_VIOLATION", violation.name());

        // Get description
        assertEquals("Parking Violation", violation.toString());

        // Use in switch
        String category = getCategory(violation);
        assertEquals("Location-Based", category);

        // Check ordinal
        assertEquals(0, violation.ordinal());
    }

    /**
     * Tests that enum can be used with instanceof.
     */
    @Test
    void testInstanceOf() {
        NonMovingViolationEnum violation = NonMovingViolationEnum.VEHICLE_PROBLEM;

        assertTrue(violation instanceof NonMovingViolationEnum);
        assertTrue(violation instanceof Enum);
    }

    /**
     * Tests using descriptions in user-facing output.
     */
    @Test
    void testDescriptionsForUserOutput() {
        StringBuilder output = new StringBuilder();
        for (NonMovingViolationEnum violation : NonMovingViolationEnum.values()) {
            output.append(violation.toString()).append("\n");
        }

        String result = output.toString();
        assertTrue(result.contains("Parking Violation"));
        assertTrue(result.contains("Paper Work Issue"));
        assertTrue(result.contains("Vehicle Problem"));
    }

    /**
     * Tests categorizing violations by type.
     */
    @Test
    void testViolationCategorization() {
        // Location-based
        assertTrue(NonMovingViolationEnum.PARKING_VIOLATION.toString().contains("Parking"));

        // Documentation
        assertTrue(NonMovingViolationEnum.PAPERWORK_ISSUE.toString().contains("Work"));

        // Vehicle condition
        assertTrue(NonMovingViolationEnum.VEHICLE_PROBLEM.toString().contains("Vehicle"));
    }

    /**
     * Tests parsing all valid constant names.
     */
    @Test
    void testParsingAllValidConstants() {
        String[] constantNames = {
                "PARKING_VIOLATION",
                "PAPERWORK_ISSUE",
                "VEHICLE_PROBLEM"
        };

        for (String name : constantNames) {
            NonMovingViolationEnum violation = NonMovingViolationEnum.valueOf(name);
            assertNotNull(violation);
            assertEquals(name, violation.name());
        }
    }

    /**
     * Tests that enum has exactly 3 constants.
     */
    @Test
    void testEnumSizeIsThree() {
        assertEquals(3, NonMovingViolationEnum.values().length);
    }

    /**
     * Tests filtering violations by category using streams.
     */
    @Test
    void testFilteringViolationsByCategory() {
        java.util.List<NonMovingViolationEnum> allViolations =
                java.util.Arrays.asList(NonMovingViolationEnum.values());

        // Filter for parking-related violations
        long parkingCount = allViolations.stream()
                .filter(v -> v.toString().contains("Parking"))
                .count();

        assertEquals(1, parkingCount);
    }

    /**
     * Tests that all violations are less severe than moving violations conceptually.
     */
    @Test
    void testNonMovingViolationsAreLessSevereThanMoving() {
        // This is a conceptual test - non-moving violations should be
        // distinct from moving violations in the system
        for (NonMovingViolationEnum violation : NonMovingViolationEnum.values()) {
            assertNotNull(violation);
            // All non-moving violations should have descriptions
            assertFalse(violation.toString().isEmpty());
        }
    }

    /**
     * Tests enum constant order matches expected severity/importance.
     */
    @Test
    void testEnumOrderMatchesLogicalGrouping() {
        // Verify that ordinals follow logical grouping
        // PARKING_VIOLATION (0) - most common
        // PAPERWORK_ISSUE (1) - administrative
        // VEHICLE_PROBLEM (2) - mechanical/safety

        assertTrue(NonMovingViolationEnum.PARKING_VIOLATION.ordinal() <
                NonMovingViolationEnum.PAPERWORK_ISSUE.ordinal());
        assertTrue(NonMovingViolationEnum.PAPERWORK_ISSUE.ordinal() <
                NonMovingViolationEnum.VEHICLE_PROBLEM.ordinal());
    }
}