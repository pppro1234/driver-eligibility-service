package Registration.IncidentFactory;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the incident contract using concrete test implementations.
 */
class IncidentTest {

    /**
     * Concrete test implementation of Incident for testing purposes.
     * Represents a crash-type incident.
     */
    private static class TestCrashIncident extends Incident {
        @Override
        public String getIncidentType() {
            return "Crash without bodily injuries";
        }

        @Override
        public IncidentType getIncidentCategory() {
            return IncidentType.CRASH;
        }
    }

    /**
     * Concrete test implementation of Incident for testing purposes.
     * Represents a moving violation incident.
     */
    private static class TestMovingViolationIncident extends Incident {
        @Override
        public String getIncidentType() {
            return "Distracted driving";
        }

        @Override
        public IncidentType getIncidentCategory() {
            return IncidentType.MOVING_VIOLATION;
        }
    }

    /**
     * Concrete test implementation of Incident for testing purposes.
     * Represents a non-moving violation incident.
     */
    private static class TestNonMovingViolationIncident extends Incident {
        @Override
        public String getIncidentType() {
            return "Parking violation";
        }

        @Override
        public IncidentType getIncidentCategory() {
            return IncidentType.NONMOVING_VIOLATION;
        }
    }

    /**
     * Concrete test implementation with null return values for edge case testing.
     */
    private static class TestNullIncident extends Incident {
        @Override
        public String getIncidentType() {
            return null;
        }

        @Override
        public IncidentType getIncidentCategory() {
            return null;
        }
    }

    /**
     * Concrete test implementation with empty string type.
     */
    private static class TestEmptyTypeIncident extends Incident {
        @Override
        public String getIncidentType() {
            return "";
        }

        @Override
        public IncidentType getIncidentCategory() {
            return IncidentType.CRASH;
        }
    }

    /**
     * Concrete test implementation with state to verify instance behavior.
     */
    private static class TestStatefulIncident extends Incident {
        private final String type;
        private final IncidentType category;

        public TestStatefulIncident(String type, IncidentType category) {
            this.type = type;
            this.category = category;
        }

        @Override
        public String getIncidentType() {
            return type;
        }

        @Override
        public IncidentType getIncidentCategory() {
            return category;
        }
    }

    // ============ Basic Implementation Tests ============

    /**
     * Tests that a concrete crash incident implementation works correctly.
     * Verifies getIncidentType() returns the correct type string.
     */
    @Test
    void testCrashIncidentGetIncidentType() {
        Incident incident = new TestCrashIncident();

        assertEquals("Crash without bodily injuries", incident.getIncidentType());
    }

    /**
     * Tests that a concrete crash incident implementation works correctly.
     * Verifies getIncidentCategory() returns the correct category.
     */
    @Test
    void testCrashIncidentGetIncidentCategory() {
        Incident incident = new TestCrashIncident();

        assertEquals(IncidentType.CRASH, incident.getIncidentCategory());
    }

    /**
     * Tests that a moving violation incident implementation works correctly.
     * Verifies getIncidentType() returns the correct type string.
     */
    @Test
    void testMovingViolationIncidentGetIncidentType() {
        Incident incident = new TestMovingViolationIncident();

        assertEquals("Distracted driving", incident.getIncidentType());
    }

    /**
     * Tests that a moving violation incident implementation works correctly.
     * Verifies getIncidentCategory() returns the correct category.
     */
    @Test
    void testMovingViolationIncidentGetIncidentCategory() {
        Incident incident = new TestMovingViolationIncident();

        assertEquals(IncidentType.MOVING_VIOLATION, incident.getIncidentCategory());
    }

    /**
     * Tests that a non-moving violation incident implementation works correctly.
     * Verifies getIncidentType() returns the correct type string.
     */
    @Test
    void testNonMovingViolationIncidentGetIncidentType() {
        Incident incident = new TestNonMovingViolationIncident();

        assertEquals("Parking violation", incident.getIncidentType());
    }

    /**
     * Tests that a non-moving violation incident implementation works correctly.
     * Verifies getIncidentCategory() returns the correct category.
     */
    @Test
    void testNonMovingViolationIncidentGetIncidentCategory() {
        Incident incident = new TestNonMovingViolationIncident();

        assertEquals(IncidentType.NONMOVING_VIOLATION, incident.getIncidentCategory());
    }

    // ============ Edge Case Tests ============

    /**
     * Tests that implementations can return null for incident type.
     * Verifies null handling in getIncidentType().
     */
    @Test
    void testNullIncidentType() {
        Incident incident = new TestNullIncident();

        assertNull(incident.getIncidentType());
    }

    /**
     * Tests that implementations can return null for incident category.
     * Verifies null handling in getIncidentCategory().
     */
    @Test
    void testNullIncidentCategory() {
        Incident incident = new TestNullIncident();

        assertNull(incident.getIncidentCategory());
    }

    /**
     * Tests that implementations can return empty string for incident type.
     * Verifies empty string handling in getIncidentType().
     */
    @Test
    void testEmptyIncidentType() {
        Incident incident = new TestEmptyTypeIncident();

        assertEquals("", incident.getIncidentType());
    }

    /**
     * Tests that both abstract methods can be called on the same instance.
     * Verifies complete functionality of the abstract class contract.
     */
    @Test
    void testBothMethodsOnSameInstance() {
        Incident incident = new TestCrashIncident();

        String type = incident.getIncidentType();
        IncidentType category = incident.getIncidentCategory();

        assertNotNull(type);
        assertNotNull(category);
        assertEquals("Crash without bodily injuries", type);
        assertEquals(IncidentType.CRASH, category);
    }

    // ============ Polymorphism Tests ============

    /**
     * Tests polymorphic behavior with different incident implementations.
     * Verifies that different subclasses can be used through the Incident interface.
     */
    @Test
    void testPolymorphicBehavior() {
        Incident crash = new TestCrashIncident();
        Incident movingViolation = new TestMovingViolationIncident();
        Incident nonMovingViolation = new TestNonMovingViolationIncident();

        // All can be treated as Incident
        assertInstanceOf(Incident.class, crash);
        assertInstanceOf(Incident.class, movingViolation);
        assertInstanceOf(Incident.class, nonMovingViolation);

        // Each returns different values
        assertNotEquals(crash.getIncidentType(), movingViolation.getIncidentType());
        assertNotEquals(crash.getIncidentCategory(), movingViolation.getIncidentCategory());
    }

    /**
     * Tests that incident instances can be stored in an array.
     * Verifies array polymorphism with Incident base type.
     */
    @Test
    void testIncidentArray() {
        Incident[] incidents = new Incident[]{
                new TestCrashIncident(),
                new TestMovingViolationIncident(),
                new TestNonMovingViolationIncident()
        };

        assertEquals(3, incidents.length);
        assertNotNull(incidents[0].getIncidentType());
        assertNotNull(incidents[1].getIncidentType());
        assertNotNull(incidents[2].getIncidentType());
    }

    /**
     * Tests that abstract methods are properly abstract and must be implemented.
     * Verifies that the class contract is enforced.
     */
    @Test
    void testAbstractMethodsMustBeImplemented() {
        // This test verifies compilation - if this compiles, abstract methods are implemented
        Incident incident = new TestCrashIncident();

        // These calls should not throw any exceptions
        assertDoesNotThrow(() -> incident.getIncidentType());
        assertDoesNotThrow(() -> incident.getIncidentCategory());
    }

    // ============ Stateful Implementation Tests ============

    /**
     * Tests that implementations can maintain state.
     * Verifies that incident data can be stored and retrieved consistently.
     */
    @Test
    void testStatefulIncidentWithDifferentValues() {
        Incident incident1 = new TestStatefulIncident("Custom Type 1", IncidentType.CRASH);
        Incident incident2 = new TestStatefulIncident("Custom Type 2", IncidentType.MOVING_VIOLATION);

        assertEquals("Custom Type 1", incident1.getIncidentType());
        assertEquals(IncidentType.CRASH, incident1.getIncidentCategory());

        assertEquals("Custom Type 2", incident2.getIncidentType());
        assertEquals(IncidentType.MOVING_VIOLATION, incident2.getIncidentCategory());
    }

    /**
     * Tests that stateful implementations maintain consistency across multiple calls.
     * Verifies that getters return consistent values.
     */
    @Test
    void testStatefulIncidentConsistency() {
        Incident incident = new TestStatefulIncident("Consistent Type", IncidentType.CRASH);

        // Multiple calls should return the same values
        assertEquals("Consistent Type", incident.getIncidentType());
        assertEquals("Consistent Type", incident.getIncidentType());
        assertEquals(IncidentType.CRASH, incident.getIncidentCategory());
        assertEquals(IncidentType.CRASH, incident.getIncidentCategory());
    }

    /**
     * Tests stateful incident with null values.
     * Verifies that stateful implementations can handle null state.
     */
    @Test
    void testStatefulIncidentWithNulls() {
        Incident incident = new TestStatefulIncident(null, null);

        assertNull(incident.getIncidentType());
        assertNull(incident.getIncidentCategory());
    }

    // ============ Type Checking Tests ============

    /**
     * Tests that all implementations are instances of Incident.
     * Verifies proper inheritance hierarchy.
     */
    @Test
    void testInstanceOfIncident() {
        Incident crash = new TestCrashIncident();
        Incident movingViolation = new TestMovingViolationIncident();
        Incident nonMovingViolation = new TestNonMovingViolationIncident();
        Incident nullIncident = new TestNullIncident();

        assertTrue(crash instanceof Incident);
        assertTrue(movingViolation instanceof Incident);
        assertTrue(nonMovingViolation instanceof Incident);
        assertTrue(nullIncident instanceof Incident);
    }

    /**
     * Tests that different implementations are different classes.
     * Verifies class distinction between implementations.
     */
    @Test
    void testDifferentImplementationsAreDifferentClasses() {
        Incident crash = new TestCrashIncident();
        Incident movingViolation = new TestMovingViolationIncident();

        assertNotEquals(crash.getClass(), movingViolation.getClass());
    }

    // ============ Integration Tests ============

    /**
     * Tests complete usage scenario with multiple incident types.
     * Verifies that the abstract class works correctly in realistic scenarios.
     */
    @Test
    void testCompleteUsageScenario() {
        // Create different incident types
        Incident[] incidents = {
                new TestCrashIncident(),
                new TestMovingViolationIncident(),
                new TestNonMovingViolationIncident()
        };

        // Verify each incident has proper type and category
        for (Incident incident : incidents) {
            assertNotNull(incident.getIncidentType());
            assertNotNull(incident.getIncidentCategory());
            assertFalse(incident.getIncidentType().isEmpty());
        }

        // Verify different incidents have different types
        assertEquals(3, incidents.length);
        assertNotEquals(incidents[0].getIncidentCategory(), incidents[1].getIncidentCategory());
        assertNotEquals(incidents[1].getIncidentCategory(), incidents[2].getIncidentCategory());
    }

    /**
     * Tests that abstract methods work correctly with method references.
     * Verifies functional programming compatibility.
     */
    @Test
    void testMethodReferences() {
        Incident incident = new TestCrashIncident();

        // These lambda expressions verify that methods can be used as method references
        java.util.function.Supplier<String> typeSupplier = incident::getIncidentType;
        java.util.function.Supplier<IncidentType> categorySupplier = incident::getIncidentCategory;

        assertEquals("Crash without bodily injuries", typeSupplier.get());
        assertEquals(IncidentType.CRASH, categorySupplier.get());
    }

    /**
     * Tests creating multiple instances of the same concrete class.
     * Verifies that multiple instances can coexist independently.
     */
    @Test
    void testMultipleInstancesOfSameClass() {
        Incident crash1 = new TestCrashIncident();
        Incident crash2 = new TestCrashIncident();

        // Both should return the same values
        assertEquals(crash1.getIncidentType(), crash2.getIncidentType());
        assertEquals(crash1.getIncidentCategory(), crash2.getIncidentCategory());

        // But they should be different objects
        assertNotSame(crash1, crash2);
    }
}