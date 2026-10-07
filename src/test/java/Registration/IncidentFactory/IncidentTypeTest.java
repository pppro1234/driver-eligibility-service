package Registration.IncidentFactory;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests incident type enum values and lookup behavior.
 */
class IncidentTypeTest {

    // ============ Enum Constants Tests ============

    /**
     * Tests that MOVING_VIOLATION enum constant exists and is not null.
     * Verifies the enum constant can be accessed.
     */
    @Test
    void testMovingViolationConstantExists() {
        IncidentType type = IncidentType.MOVING_VIOLATION;

        assertNotNull(type);
    }

    /**
     * Tests that NONMOVING_VIOLATION enum constant exists and is not null.
     * Verifies the enum constant can be accessed.
     */
    @Test
    void testNonMovingViolationConstantExists() {
        IncidentType type = IncidentType.NONMOVING_VIOLATION;

        assertNotNull(type);
    }

    /**
     * Tests that CRASH enum constant exists and is not null.
     * Verifies the enum constant can be accessed.
     */
    @Test
    void testCrashConstantExists() {
        IncidentType type = IncidentType.CRASH;

        assertNotNull(type);
    }

    // ============ Enum.values() Tests ============

    /**
     * Tests that values() returns all enum constants.
     * Verifies the correct number of constants exists.
     */
    @Test
    void testValuesReturnsAllConstants() {
        IncidentType[] values = IncidentType.values();

        assertEquals(3, values.length);
    }

    /**
     * Tests that values() returns the constants in declaration order.
     * Verifies the order matches the enum definition.
     */
    @Test
    void testValuesOrder() {
        IncidentType[] values = IncidentType.values();

        assertEquals(IncidentType.MOVING_VIOLATION, values[0]);
        assertEquals(IncidentType.NONMOVING_VIOLATION, values[1]);
        assertEquals(IncidentType.CRASH, values[2]);
    }

    /**
     * Tests that values() returns a new array each time.
     * Verifies defensive copying behavior.
     */
    @Test
    void testValuesReturnsNewArray() {
        IncidentType[] values1 = IncidentType.values();
        IncidentType[] values2 = IncidentType.values();

        assertNotSame(values1, values2);
        assertArrayEquals(values1, values2);
    }

    /**
     * Tests that modifying the values() array doesn't affect the enum.
     * Verifies immutability of the enum.
     */
    @Test
    void testValuesArrayModificationDoesNotAffectEnum() {
        IncidentType[] values = IncidentType.values();
        values[0] = IncidentType.CRASH; // Modify the array

        // Original enum should be unchanged
        IncidentType[] newValues = IncidentType.values();
        assertEquals(IncidentType.MOVING_VIOLATION, newValues[0]);
    }

    // ============ Enum.valueOf() Tests ============

    /**
     * Tests valueOf() with MOVING_VIOLATION string.
     * Verifies string-to-enum conversion works correctly.
     */
    @Test
    void testValueOfMovingViolation() {
        IncidentType type = IncidentType.valueOf("MOVING_VIOLATION");

        assertEquals(IncidentType.MOVING_VIOLATION, type);
    }

    /**
     * Tests valueOf() with NONMOVING_VIOLATION string.
     * Verifies string-to-enum conversion works correctly.
     */
    @Test
    void testValueOfNonMovingViolation() {
        IncidentType type = IncidentType.valueOf("NONMOVING_VIOLATION");

        assertEquals(IncidentType.NONMOVING_VIOLATION, type);
    }

    /**
     * Tests valueOf() with CRASH string.
     * Verifies string-to-enum conversion works correctly.
     */
    @Test
    void testValueOfCrash() {
        IncidentType type = IncidentType.valueOf("CRASH");

        assertEquals(IncidentType.CRASH, type);
    }

    /**
     * Tests valueOf() with invalid string throws IllegalArgumentException.
     * Verifies proper exception handling for invalid input.
     */
    @Test
    void testValueOfInvalidString() {
        assertThrows(IllegalArgumentException.class, () -> {
            IncidentType.valueOf("INVALID_TYPE");
        });
    }

    /**
     * Tests valueOf() with null throws NullPointerException.
     * Verifies proper exception handling for null input.
     */
    @Test
    void testValueOfNull() {
        assertThrows(NullPointerException.class, () -> {
            IncidentType.valueOf(null);
        });
    }

    /**
     * Tests valueOf() with lowercase string throws IllegalArgumentException.
     * Verifies case-sensitivity of valueOf().
     */
    @Test
    void testValueOfCaseSensitive() {
        assertThrows(IllegalArgumentException.class, () -> {
            IncidentType.valueOf("moving_violation");
        });
    }

    /**
     * Tests valueOf() with extra spaces throws IllegalArgumentException.
     * Verifies that valueOf() doesn't trim whitespace.
     */
    @Test
    void testValueOfWithSpaces() {
        assertThrows(IllegalArgumentException.class, () -> {
            IncidentType.valueOf(" MOVING_VIOLATION ");
        });
    }

    // ============ Enum.name() Tests ============

    /**
     * Tests name() method for MOVING_VIOLATION.
     * Verifies that name() returns the correct string.
     */
    @Test
    void testNameMovingViolation() {
        String name = IncidentType.MOVING_VIOLATION.name();

        assertEquals("MOVING_VIOLATION", name);
    }

    /**
     * Tests name() method for NONMOVING_VIOLATION.
     * Verifies that name() returns the correct string.
     */
    @Test
    void testNameNonMovingViolation() {
        String name = IncidentType.NONMOVING_VIOLATION.name();

        assertEquals("NONMOVING_VIOLATION", name);
    }

    /**
     * Tests name() method for CRASH.
     * Verifies that name() returns the correct string.
     */
    @Test
    void testNameCrash() {
        String name = IncidentType.CRASH.name();

        assertEquals("CRASH", name);
    }

    // ============ Enum.ordinal() Tests ============

    /**
     * Tests ordinal() method for MOVING_VIOLATION.
     * Verifies the correct ordinal position (0).
     */
    @Test
    void testOrdinalMovingViolation() {
        int ordinal = IncidentType.MOVING_VIOLATION.ordinal();

        assertEquals(0, ordinal);
    }

    /**
     * Tests ordinal() method for NONMOVING_VIOLATION.
     * Verifies the correct ordinal position (1).
     */
    @Test
    void testOrdinalNonMovingViolation() {
        int ordinal = IncidentType.NONMOVING_VIOLATION.ordinal();

        assertEquals(1, ordinal);
    }

    /**
     * Tests ordinal() method for CRASH.
     * Verifies the correct ordinal position (2).
     */
    @Test
    void testOrdinalCrash() {
        int ordinal = IncidentType.CRASH.ordinal();

        assertEquals(2, ordinal);
    }

    /**
     * Tests that ordinals are unique and sequential.
     * Verifies the ordinal contract.
     */
    @Test
    void testOrdinalsAreUniqueAndSequential() {
        IncidentType[] values = IncidentType.values();

        for (int i = 0; i < values.length; i++) {
            assertEquals(i, values[i].ordinal());
        }
    }

    // ============ Enum.toString() Tests ============

    /**
     * Tests toString() method for MOVING_VIOLATION.
     * Verifies that toString() returns the correct string.
     */
    @Test
    void testToStringMovingViolation() {
        String str = IncidentType.MOVING_VIOLATION.toString();

        assertEquals("MOVING_VIOLATION", str);
    }

    /**
     * Tests toString() method for NONMOVING_VIOLATION.
     * Verifies that toString() returns the correct string.
     */
    @Test
    void testToStringNonMovingViolation() {
        String str = IncidentType.NONMOVING_VIOLATION.toString();

        assertEquals("NONMOVING_VIOLATION", str);
    }

    /**
     * Tests toString() method for CRASH.
     * Verifies that toString() returns the correct string.
     */
    @Test
    void testToStringCrash() {
        String str = IncidentType.CRASH.toString();

        assertEquals("CRASH", str);
    }

    /**
     * Tests that name() and toString() return the same value.
     * Verifies consistency between name() and toString().
     */
    @Test
    void testNameAndToStringAreEqual() {
        for (IncidentType type : IncidentType.values()) {
            assertEquals(type.name(), type.toString());
        }
    }

    // ============ Equality and Identity Tests ============

    /**
     * Tests that enum constants are singletons.
     * Verifies that the same constant always refers to the same instance.
     */
    @Test
    void testEnumConstantsAreSingletons() {
        IncidentType type1 = IncidentType.MOVING_VIOLATION;
        IncidentType type2 = IncidentType.MOVING_VIOLATION;

        assertSame(type1, type2);
    }

    /**
     * Tests that valueOf() returns the same instance as the constant.
     * Verifies singleton behavior with valueOf().
     */
    @Test
    void testValueOfReturnsSameInstance() {
        IncidentType constant = IncidentType.CRASH;
        IncidentType fromString = IncidentType.valueOf("CRASH");

        assertSame(constant, fromString);
    }

    /**
     * Tests equality using equals() method.
     * Verifies that enum equality works correctly.
     */
    @Test
    void testEquality() {
        IncidentType type1 = IncidentType.MOVING_VIOLATION;
        IncidentType type2 = IncidentType.MOVING_VIOLATION;
        IncidentType type3 = IncidentType.CRASH;

        assertEquals(type1, type2);
        assertNotEquals(type1, type3);
    }

    /**
     * Tests equality using == operator.
     * Verifies that reference equality works for enums.
     */
    @Test
    void testReferenceEquality() {
        IncidentType type1 = IncidentType.MOVING_VIOLATION;
        IncidentType type2 = IncidentType.MOVING_VIOLATION;

        assertTrue(type1 == type2);
    }

    /**
     * Tests that different constants are not equal.
     * Verifies distinction between enum constants.
     */
    @Test
    void testDifferentConstantsNotEqual() {
        assertNotEquals(IncidentType.MOVING_VIOLATION, IncidentType.NONMOVING_VIOLATION);
        assertNotEquals(IncidentType.NONMOVING_VIOLATION, IncidentType.CRASH);
        assertNotEquals(IncidentType.CRASH, IncidentType.MOVING_VIOLATION);
    }

    // ============ Switch Statement Tests ============

    /**
     * Tests using enum in switch statement for MOVING_VIOLATION.
     * Verifies enum can be used in switch expressions.
     */
    @Test
    void testSwitchStatementMovingViolation() {
        IncidentType type = IncidentType.MOVING_VIOLATION;
        String result = getSwitchResult(type);

        assertEquals("Moving Violation", result);
    }

    /**
     * Tests using enum in switch statement for NONMOVING_VIOLATION.
     * Verifies enum can be used in switch expressions.
     */
    @Test
    void testSwitchStatementNonMovingViolation() {
        IncidentType type = IncidentType.NONMOVING_VIOLATION;
        String result = getSwitchResult(type);

        assertEquals("Non-Moving Violation", result);
    }

    /**
     * Tests using enum in switch statement for CRASH.
     * Verifies enum can be used in switch expressions.
     */
    @Test
    void testSwitchStatementCrash() {
        IncidentType type = IncidentType.CRASH;
        String result = getSwitchResult(type);

        assertEquals("Crash", result);
    }

    /**
     * Helper method to test switch statement functionality.
     */
    private String getSwitchResult(IncidentType type) {
        switch (type) {
            case MOVING_VIOLATION:
                return "Moving Violation";
            case NONMOVING_VIOLATION:
                return "Non-Moving Violation";
            case CRASH:
                return "Crash";
            default:
                return "Unknown";
        }
    }

    // ============ Collection Tests ============

    /**
     * Tests that enum constants can be added to collections.
     * Verifies enum compatibility with Java Collections Framework.
     */
    @Test
    void testEnumInCollection() {
        java.util.Set<IncidentType> set = java.util.EnumSet.allOf(IncidentType.class);

        assertEquals(3, set.size());
        assertTrue(set.contains(IncidentType.MOVING_VIOLATION));
        assertTrue(set.contains(IncidentType.NONMOVING_VIOLATION));
        assertTrue(set.contains(IncidentType.CRASH));
    }

    /**
     * Tests that enum can be used as map key.
     * Verifies enum can be used in EnumMap.
     */
    @Test
    void testEnumAsMapKey() {
        java.util.Map<IncidentType, String> map = new java.util.EnumMap<>(IncidentType.class);
        map.put(IncidentType.MOVING_VIOLATION, "Moving");
        map.put(IncidentType.NONMOVING_VIOLATION, "Non-Moving");
        map.put(IncidentType.CRASH, "Crash");

        assertEquals("Moving", map.get(IncidentType.MOVING_VIOLATION));
        assertEquals("Non-Moving", map.get(IncidentType.NONMOVING_VIOLATION));
        assertEquals("Crash", map.get(IncidentType.CRASH));
    }

    // ============ Iteration Tests ============

    /**
     * Tests iterating over all enum constants using for-each loop.
     * Verifies that all constants can be iterated.
     */
    @Test
    void testIterationOverAllConstants() {
        int count = 0;
        for (IncidentType type : IncidentType.values()) {
            assertNotNull(type);
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
        IncidentType[] values = IncidentType.values();
        int index = 0;

        for (IncidentType type : IncidentType.values()) {
            assertEquals(values[index++], type);
        }
    }

    // ============ Comparison Tests ============

    /**
     * Tests compareTo() method based on ordinal values.
     * Verifies natural ordering of enum constants.
     */
    @Test
    void testCompareTo() {
        assertTrue(IncidentType.MOVING_VIOLATION.compareTo(IncidentType.NONMOVING_VIOLATION) < 0);
        assertTrue(IncidentType.NONMOVING_VIOLATION.compareTo(IncidentType.CRASH) < 0);
        assertTrue(IncidentType.CRASH.compareTo(IncidentType.MOVING_VIOLATION) > 0);
        assertEquals(0, IncidentType.MOVING_VIOLATION.compareTo(IncidentType.MOVING_VIOLATION));
    }

    /**
     * Tests that enum constants implement Comparable interface.
     * Verifies that enums can be sorted.
     */
    @Test
    void testEnumIsComparable() {
        IncidentType type = IncidentType.MOVING_VIOLATION;

        assertInstanceOf(Comparable.class, type);
    }

    // ============ Hash Code Tests ============

    /**
     * Tests that hashCode() is consistent for the same constant.
     * Verifies hashCode contract.
     */
    @Test
    void testHashCodeConsistency() {
        IncidentType type = IncidentType.MOVING_VIOLATION;

        assertEquals(type.hashCode(), type.hashCode());
    }

    /**
     * Tests that equal enum constants have the same hash code.
     * Verifies hashCode equals contract.
     */
    @Test
    void testHashCodeForEqualObjects() {
        IncidentType type1 = IncidentType.CRASH;
        IncidentType type2 = IncidentType.valueOf("CRASH");

        assertEquals(type1.hashCode(), type2.hashCode());
    }

    // ============ Integration Tests ============

    /**
     * Tests complete enum usage in realistic scenario.
     * Verifies enum works correctly in typical use cases.
     */
    @Test
    void testCompleteEnumUsage() {
        // Parse from string
        IncidentType type = IncidentType.valueOf("MOVING_VIOLATION");

        // Use in comparison
        assertEquals(IncidentType.MOVING_VIOLATION, type);

        // Get name
        assertEquals("MOVING_VIOLATION", type.name());

        // Use in switch
        String description = getSwitchResult(type);
        assertEquals("Moving Violation", description);

        // Check ordinal
        assertEquals(0, type.ordinal());
    }

    /**
     * Tests that enum can be used with instanceof.
     * Verifies type checking with enum.
     */
    @Test
    void testInstanceOf() {
        IncidentType type = IncidentType.CRASH;

        assertTrue(type instanceof IncidentType);
        assertTrue(type instanceof Enum);
    }
}