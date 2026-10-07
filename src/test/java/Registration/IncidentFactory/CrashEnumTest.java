package Registration.IncidentFactory;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests crash enum values and lookup behavior.
 */
class CrashEnumTest {

    // ============ Enum Constants Tests ============

    /**
     * Tests that FENDER_BENDER enum constant exists and is not null.
     * Verifies the enum constant can be accessed.
     */
    @Test
    void testFenderBenderConstantExists() {
        CrashEnum crash = CrashEnum.FENDER_BENDER;

        assertNotNull(crash);
    }

    /**
     * Tests that NO_INJURY_CRASH enum constant exists and is not null.
     * Verifies the enum constant can be accessed.
     */
    @Test
    void testNoInjuryCrashConstantExists() {
        CrashEnum crash = CrashEnum.NO_INJURY_CRASH;

        assertNotNull(crash);
    }

    /**
     * Tests that INJURY_CRASH enum constant exists and is not null.
     * Verifies the enum constant can be accessed.
     */
    @Test
    void testInjuryCrashConstantExists() {
        CrashEnum crash = CrashEnum.INJURY_CRASH;

        assertNotNull(crash);
    }

    // ============ Enum.values() Tests ============

    /**
     * Tests that values() returns all enum constants.
     * Verifies the correct number of constants exists.
     */
    @Test
    void testValuesReturnsAllConstants() {
        CrashEnum[] values = CrashEnum.values();

        assertEquals(3, values.length);
    }

    /**
     * Tests that values() returns the constants in declaration order.
     * Verifies the order matches the enum definition.
     */
    @Test
    void testValuesOrder() {
        CrashEnum[] values = CrashEnum.values();

        assertEquals(CrashEnum.FENDER_BENDER, values[0]);
        assertEquals(CrashEnum.NO_INJURY_CRASH, values[1]);
        assertEquals(CrashEnum.INJURY_CRASH, values[2]);
    }

    /**
     * Tests that values() returns a new array each time.
     * Verifies defensive copying behavior.
     */
    @Test
    void testValuesReturnsNewArray() {
        CrashEnum[] values1 = CrashEnum.values();
        CrashEnum[] values2 = CrashEnum.values();

        assertNotSame(values1, values2);
        assertArrayEquals(values1, values2);
    }

    /**
     * Tests that modifying the values() array doesn't affect the enum.
     * Verifies immutability of the enum.
     */
    @Test
    void testValuesArrayModificationDoesNotAffectEnum() {
        CrashEnum[] values = CrashEnum.values();
        values[0] = CrashEnum.INJURY_CRASH; // Modify the array

        // Original enum should be unchanged
        CrashEnum[] newValues = CrashEnum.values();
        assertEquals(CrashEnum.FENDER_BENDER, newValues[0]);
    }

    // ============ Enum.valueOf() Tests ============

    /**
     * Tests valueOf() with FENDER_BENDER string.
     * Verifies string-to-enum conversion works correctly.
     */
    @Test
    void testValueOfFenderBender() {
        CrashEnum crash = CrashEnum.valueOf("FENDER_BENDER");

        assertEquals(CrashEnum.FENDER_BENDER, crash);
    }

    /**
     * Tests valueOf() with NO_INJURY_CRASH string.
     * Verifies string-to-enum conversion works correctly.
     */
    @Test
    void testValueOfNoInjuryCrash() {
        CrashEnum crash = CrashEnum.valueOf("NO_INJURY_CRASH");

        assertEquals(CrashEnum.NO_INJURY_CRASH, crash);
    }

    /**
     * Tests valueOf() with INJURY_CRASH string.
     * Verifies string-to-enum conversion works correctly.
     */
    @Test
    void testValueOfInjuryCrash() {
        CrashEnum crash = CrashEnum.valueOf("INJURY_CRASH");

        assertEquals(CrashEnum.INJURY_CRASH, crash);
    }

    /**
     * Tests valueOf() with invalid string throws IllegalArgumentException.
     * Verifies proper exception handling for invalid input.
     */
    @Test
    void testValueOfInvalidString() {
        assertThrows(IllegalArgumentException.class, () -> {
            CrashEnum.valueOf("INVALID_CRASH");
        });
    }

    /**
     * Tests valueOf() with null throws NullPointerException.
     * Verifies proper exception handling for null input.
     */
    @Test
    void testValueOfNull() {
        assertThrows(NullPointerException.class, () -> {
            CrashEnum.valueOf(null);
        });
    }

    /**
     * Tests valueOf() with display name throws IllegalArgumentException.
     * Verifies that valueOf() expects constant name, not display name.
     */
    @Test
    void testValueOfWithDisplayName() {
        assertThrows(IllegalArgumentException.class, () -> {
            CrashEnum.valueOf("Fender bender");
        });
    }

    /**
     * Tests valueOf() with lowercase string throws IllegalArgumentException.
     * Verifies case-sensitivity of valueOf().
     */
    @Test
    void testValueOfCaseSensitive() {
        assertThrows(IllegalArgumentException.class, () -> {
            CrashEnum.valueOf("fender_bender");
        });
    }

    // ============ Enum.name() Tests ============

    /**
     * Tests name() method for FENDER_BENDER.
     * Verifies that name() returns the constant name, not display name.
     */
    @Test
    void testNameFenderBender() {
        String name = CrashEnum.FENDER_BENDER.name();

        assertEquals("FENDER_BENDER", name);
    }

    /**
     * Tests name() method for NO_INJURY_CRASH.
     * Verifies that name() returns the constant name.
     */
    @Test
    void testNameNoInjuryCrash() {
        String name = CrashEnum.NO_INJURY_CRASH.name();

        assertEquals("NO_INJURY_CRASH", name);
    }

    /**
     * Tests name() method for INJURY_CRASH.
     * Verifies that name() returns the constant name.
     */
    @Test
    void testNameInjuryCrash() {
        String name = CrashEnum.INJURY_CRASH.name();

        assertEquals("INJURY_CRASH", name);
    }

    // ============ Enum.ordinal() Tests ============

    /**
     * Tests ordinal() method for FENDER_BENDER.
     * Verifies the correct ordinal position (0).
     */
    @Test
    void testOrdinalFenderBender() {
        int ordinal = CrashEnum.FENDER_BENDER.ordinal();

        assertEquals(0, ordinal);
    }

    /**
     * Tests ordinal() method for NO_INJURY_CRASH.
     * Verifies the correct ordinal position (1).
     */
    @Test
    void testOrdinalNoInjuryCrash() {
        int ordinal = CrashEnum.NO_INJURY_CRASH.ordinal();

        assertEquals(1, ordinal);
    }

    /**
     * Tests ordinal() method for INJURY_CRASH.
     * Verifies the correct ordinal position (2).
     */
    @Test
    void testOrdinalInjuryCrash() {
        int ordinal = CrashEnum.INJURY_CRASH.ordinal();

        assertEquals(2, ordinal);
    }

    /**
     * Tests that ordinals are unique and sequential.
     * Verifies the ordinal contract.
     */
    @Test
    void testOrdinalsAreUniqueAndSequential() {
        CrashEnum[] values = CrashEnum.values();

        for (int i = 0; i < values.length; i++) {
            assertEquals(i, values[i].ordinal());
        }
    }

    // ============ toString() Tests ============

    /**
     * Tests toString() method for FENDER_BENDER.
     * Verifies that toString() returns the display name, not constant name.
     */
    @Test
    void testToStringFenderBender() {
        String str = CrashEnum.FENDER_BENDER.toString();

        assertEquals("Fender bender", str);
    }

    /**
     * Tests toString() method for NO_INJURY_CRASH.
     * Verifies that toString() returns the display name.
     */
    @Test
    void testToStringNoInjuryCrash() {
        String str = CrashEnum.NO_INJURY_CRASH.toString();

        assertEquals("No Injury Crash", str);
    }

    /**
     * Tests toString() method for INJURY_CRASH.
     * Verifies that toString() returns the display name.
     */
    @Test
    void testToStringInjuryCrash() {
        String str = CrashEnum.INJURY_CRASH.toString();

        assertEquals("Injury Crash", str);
    }

    /**
     * Tests that toString() returns different value than name().
     * Verifies that toString() is properly overridden.
     */
    @Test
    void testToStringDifferentFromName() {
        for (CrashEnum crash : CrashEnum.values()) {
            assertNotEquals(crash.name(), crash.toString());
        }
    }

    /**
     * Tests that toString() returns human-readable format.
     * Verifies display name formatting.
     */
    @Test
    void testToStringReturnsHumanReadableFormat() {
        // Display names should have spaces and proper capitalization
        assertTrue(CrashEnum.FENDER_BENDER.toString().contains(" "));
        assertTrue(CrashEnum.NO_INJURY_CRASH.toString().contains(" "));
        assertTrue(CrashEnum.INJURY_CRASH.toString().contains(" "));
    }

    // ============ Equality and Identity Tests ============

    /**
     * Tests that enum constants are singletons.
     * Verifies that the same constant always refers to the same instance.
     */
    @Test
    void testEnumConstantsAreSingletons() {
        CrashEnum crash1 = CrashEnum.FENDER_BENDER;
        CrashEnum crash2 = CrashEnum.FENDER_BENDER;

        assertSame(crash1, crash2);
    }

    /**
     * Tests that valueOf() returns the same instance as the constant.
     * Verifies singleton behavior with valueOf().
     */
    @Test
    void testValueOfReturnsSameInstance() {
        CrashEnum constant = CrashEnum.INJURY_CRASH;
        CrashEnum fromString = CrashEnum.valueOf("INJURY_CRASH");

        assertSame(constant, fromString);
    }

    /**
     * Tests equality using equals() method.
     * Verifies that enum equality works correctly.
     */
    @Test
    void testEquality() {
        CrashEnum crash1 = CrashEnum.FENDER_BENDER;
        CrashEnum crash2 = CrashEnum.FENDER_BENDER;
        CrashEnum crash3 = CrashEnum.INJURY_CRASH;

        assertEquals(crash1, crash2);
        assertNotEquals(crash1, crash3);
    }

    /**
     * Tests equality using == operator.
     * Verifies that reference equality works for enums.
     */
    @Test
    void testReferenceEquality() {
        CrashEnum crash1 = CrashEnum.NO_INJURY_CRASH;
        CrashEnum crash2 = CrashEnum.NO_INJURY_CRASH;

        assertTrue(crash1 == crash2);
    }

    /**
     * Tests that different constants are not equal.
     * Verifies distinction between enum constants.
     */
    @Test
    void testDifferentConstantsNotEqual() {
        assertNotEquals(CrashEnum.FENDER_BENDER, CrashEnum.NO_INJURY_CRASH);
        assertNotEquals(CrashEnum.NO_INJURY_CRASH, CrashEnum.INJURY_CRASH);
        assertNotEquals(CrashEnum.INJURY_CRASH, CrashEnum.FENDER_BENDER);
    }

    // ============ Switch Statement Tests ============

    /**
     * Tests using enum in switch statement for FENDER_BENDER.
     * Verifies enum can be used in switch expressions.
     */
    @Test
    void testSwitchStatementFenderBender() {
        CrashEnum crash = CrashEnum.FENDER_BENDER;
        String severity = getSeverity(crash);

        assertEquals("Minor", severity);
    }

    /**
     * Tests using enum in switch statement for NO_INJURY_CRASH.
     * Verifies enum can be used in switch expressions.
     */
    @Test
    void testSwitchStatementNoInjuryCrash() {
        CrashEnum crash = CrashEnum.NO_INJURY_CRASH;
        String severity = getSeverity(crash);

        assertEquals("Moderate", severity);
    }

    /**
     * Tests using enum in switch statement for INJURY_CRASH.
     * Verifies enum can be used in switch expressions.
     */
    @Test
    void testSwitchStatementInjuryCrash() {
        CrashEnum crash = CrashEnum.INJURY_CRASH;
        String severity = getSeverity(crash);

        assertEquals("Severe", severity);
    }

    /**
     * Helper method to test switch statement functionality.
     */
    private String getSeverity(CrashEnum crash) {
        switch (crash) {
            case FENDER_BENDER:
                return "Minor";
            case NO_INJURY_CRASH:
                return "Moderate";
            case INJURY_CRASH:
                return "Severe";
            default:
                return "Unknown";
        }
    }

    // ============ Collection Tests ============

    /**
     * Tests that enum constants can be added to EnumSet.
     * Verifies enum compatibility with Java Collections Framework.
     */
    @Test
    void testEnumInCollection() {
        java.util.Set<CrashEnum> set = java.util.EnumSet.allOf(CrashEnum.class);

        assertEquals(3, set.size());
        assertTrue(set.contains(CrashEnum.FENDER_BENDER));
        assertTrue(set.contains(CrashEnum.NO_INJURY_CRASH));
        assertTrue(set.contains(CrashEnum.INJURY_CRASH));
    }

    /**
     * Tests that enum can be used as map key.
     * Verifies enum can be used in EnumMap.
     */
    @Test
    void testEnumAsMapKey() {
        java.util.Map<CrashEnum, Integer> severityMap = new java.util.EnumMap<>(CrashEnum.class);
        severityMap.put(CrashEnum.FENDER_BENDER, 1);
        severityMap.put(CrashEnum.NO_INJURY_CRASH, 2);
        severityMap.put(CrashEnum.INJURY_CRASH, 3);

        assertEquals(1, severityMap.get(CrashEnum.FENDER_BENDER));
        assertEquals(2, severityMap.get(CrashEnum.NO_INJURY_CRASH));
        assertEquals(3, severityMap.get(CrashEnum.INJURY_CRASH));
    }

    // ============ Iteration Tests ============

    /**
     * Tests iterating over all enum constants using for-each loop.
     * Verifies that all constants can be iterated.
     */
    @Test
    void testIterationOverAllConstants() {
        int count = 0;
        for (CrashEnum crash : CrashEnum.values()) {
            assertNotNull(crash);
            assertNotNull(crash.toString());
            count++;
        }

        assertEquals(3, count);
    }

    /**
     * Tests that iteration order matches declaration order.
     * Verifies consistent iteration order.
     */
    @Test
    void testIterationOrder() {
        CrashEnum[] values = CrashEnum.values();
        int index = 0;

        for (CrashEnum crash : CrashEnum.values()) {
            assertEquals(values[index++], crash);
        }
    }

    // ============ Comparison Tests ============

    /**
     * Tests compareTo() method based on ordinal values.
     * Verifies natural ordering of enum constants (severity order).
     */
    @Test
    void testCompareTo() {
        assertTrue(CrashEnum.FENDER_BENDER.compareTo(CrashEnum.NO_INJURY_CRASH) < 0);
        assertTrue(CrashEnum.NO_INJURY_CRASH.compareTo(CrashEnum.INJURY_CRASH) < 0);
        assertTrue(CrashEnum.INJURY_CRASH.compareTo(CrashEnum.FENDER_BENDER) > 0);
        assertEquals(0, CrashEnum.NO_INJURY_CRASH.compareTo(CrashEnum.NO_INJURY_CRASH));
    }

    /**
     * Tests that enum constants implement Comparable interface.
     * Verifies that enums can be sorted.
     */
    @Test
    void testEnumIsComparable() {
        CrashEnum crash = CrashEnum.FENDER_BENDER;

        assertInstanceOf(Comparable.class, crash);
    }

    /**
     * Tests sorting enum constants.
     * Verifies natural ordering for sorting.
     */
    @Test
    void testSorting() {
        java.util.List<CrashEnum> crashes = new java.util.ArrayList<>();
        crashes.add(CrashEnum.INJURY_CRASH);
        crashes.add(CrashEnum.FENDER_BENDER);
        crashes.add(CrashEnum.NO_INJURY_CRASH);

        java.util.Collections.sort(crashes);

        assertEquals(CrashEnum.FENDER_BENDER, crashes.get(0));
        assertEquals(CrashEnum.NO_INJURY_CRASH, crashes.get(1));
        assertEquals(CrashEnum.INJURY_CRASH, crashes.get(2));
    }

    // ============ Hash Code Tests ============

    /**
     * Tests that hashCode() is consistent for the same constant.
     * Verifies hashCode contract.
     */
    @Test
    void testHashCodeConsistency() {
        CrashEnum crash = CrashEnum.FENDER_BENDER;

        assertEquals(crash.hashCode(), crash.hashCode());
    }

    /**
     * Tests that equal enum constants have the same hash code.
     * Verifies hashCode equals contract.
     */
    @Test
    void testHashCodeForEqualObjects() {
        CrashEnum crash1 = CrashEnum.INJURY_CRASH;
        CrashEnum crash2 = CrashEnum.valueOf("INJURY_CRASH");

        assertEquals(crash1.hashCode(), crash2.hashCode());
    }

    // ============ Display Name Tests ============

    /**
     * Tests that display names are properly formatted.
     * Verifies display name content and formatting.
     */
    @Test
    void testDisplayNameFormatting() {
        // Test proper case and spacing
        assertEquals("Fender bender", CrashEnum.FENDER_BENDER.toString());
        assertEquals("No Injury Crash", CrashEnum.NO_INJURY_CRASH.toString());
        assertEquals("Injury Crash", CrashEnum.INJURY_CRASH.toString());
    }

    /**
     * Tests that display names are not empty.
     * Verifies all display names have content.
     */
    @Test
    void testDisplayNamesNotEmpty() {
        for (CrashEnum crash : CrashEnum.values()) {
            assertFalse(crash.toString().isEmpty());
        }
    }

    /**
     * Tests that all display names are unique.
     * Verifies no duplicate display names.
     */
    @Test
    void testDisplayNamesAreUnique() {
        java.util.Set<String> displayNames = new java.util.HashSet<>();
        for (CrashEnum crash : CrashEnum.values()) {
            displayNames.add(crash.toString());
        }

        assertEquals(3, displayNames.size());
    }

    // ============ Integration Tests ============

    /**
     * Tests complete enum usage in realistic scenario.
     * Verifies enum works correctly in typical use cases.
     */
    @Test
    void testCompleteEnumUsage() {
        // Parse from string
        CrashEnum crash = CrashEnum.valueOf("FENDER_BENDER");

        // Use in comparison
        assertEquals(CrashEnum.FENDER_BENDER, crash);

        // Get name
        assertEquals("FENDER_BENDER", crash.name());

        // Get display name
        assertEquals("Fender bender", crash.toString());

        // Use in switch
        String severity = getSeverity(crash);
        assertEquals("Minor", severity);

        // Check ordinal
        assertEquals(0, crash.ordinal());
    }

    /**
     * Tests that enum can be used with instanceof.
     * Verifies type checking with enum.
     */
    @Test
    void testInstanceOf() {
        CrashEnum crash = CrashEnum.INJURY_CRASH;

        assertTrue(crash instanceof CrashEnum);
        assertTrue(crash instanceof Enum);
    }

    /**
     * Tests using display names in user-facing output.
     * Verifies display names are suitable for UI/reporting.
     */
    @Test
    void testDisplayNamesForUserOutput() {
        StringBuilder output = new StringBuilder();
        for (CrashEnum crash : CrashEnum.values()) {
            output.append(crash.toString()).append("\n");
        }

        String result = output.toString();
        assertTrue(result.contains("Fender bender"));
        assertTrue(result.contains("No Injury Crash"));
        assertTrue(result.contains("Injury Crash"));
    }

    /**
     * Tests severity-based logic using enum ordering.
     * Verifies ordinal can represent severity levels.
     */
    @Test
    void testSeverityOrdering() {
        // FENDER_BENDER is least severe (ordinal 0)
        // INJURY_CRASH is most severe (ordinal 2)
        assertTrue(CrashEnum.FENDER_BENDER.ordinal() < CrashEnum.NO_INJURY_CRASH.ordinal());
        assertTrue(CrashEnum.NO_INJURY_CRASH.ordinal() < CrashEnum.INJURY_CRASH.ordinal());
    }
}