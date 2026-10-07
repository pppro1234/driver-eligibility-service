package Registration.IncidentFactory;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests incident creation and factory selection using concrete test factories.
 */
class IncidentFactoryTest {

    /**
     * Concrete test factory implementation for crash incidents.
     * Creates incidents representing crashes without bodily injuries.
     */
    private static class TestCrashFactory extends IncidentFactory {
        @Override
        public Incident createViolation() {
            return new TestCrashIncident();
        }
    }

    /**
     * Concrete test factory implementation for moving violation incidents.
     * Creates incidents representing distracted driving violations.
     */
    private static class TestMovingViolationFactory extends IncidentFactory {
        @Override
        public Incident createViolation() {
            return new TestMovingViolationIncident();
        }
    }

    /**
     * Concrete test factory implementation for non-moving violation incidents.
     * Creates incidents representing parking violations.
     */
    private static class TestNonMovingViolationFactory extends IncidentFactory {
        @Override
        public Incident createViolation() {
            return new TestNonMovingViolationIncident();
        }
    }

    /**
     * Test factory that creates incidents from configured type and severity.
     */
    private static class TestParameterizedFactory extends IncidentFactory {
        private final String incidentType;
        private final IncidentType category;

        public TestParameterizedFactory(String incidentType, IncidentType category) {
            this.incidentType = incidentType;
            this.category = category;
        }

        @Override
        public Incident createViolation() {
            return new TestParameterizedIncident(incidentType, category);
        }
    }

    /**
     * Concrete test factory that returns null (edge case).
     * Tests factory behavior when createViolation returns null.
     */
    private static class TestNullFactory extends IncidentFactory {
        @Override
        public Incident createViolation() {
            return null;
        }
    }

    /**
     * Concrete test factory that always creates new instances.
     * Tests that each call creates a distinct object.
     */
    private static class TestNewInstanceFactory extends IncidentFactory {
        @Override
        public Incident createViolation() {
            return new TestCrashIncident();
        }
    }

    // ============ Test Incident Implementations ============

    /**
     * Test incident representing a crash.
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
     * Test incident representing a moving violation.
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
     * Test incident representing a non-moving violation.
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
     * Test incident with parameterized data.
     */
    private static class TestParameterizedIncident extends Incident {
        private final String type;
        private final IncidentType category;

        public TestParameterizedIncident(String type, IncidentType category) {
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

    // ============ Basic Factory Creation Tests ============

    /**
     * Tests that a crash factory can be instantiated and creates crash incidents.
     * Verifies basic factory instantiation and usage.
     */
    @Test
    void testCrashFactoryCreatesIncident() {
        IncidentFactory factory = new TestCrashFactory();
        Incident incident = factory.createViolation();

        assertNotNull(incident);
        assertEquals("Crash without bodily injuries", incident.getIncidentType());
        assertEquals(IncidentType.CRASH, incident.getIncidentCategory());
    }

    /**
     * Tests that a moving violation factory creates appropriate incidents.
     * Verifies factory creates correct incident type.
     */
    @Test
    void testMovingViolationFactoryCreatesIncident() {
        IncidentFactory factory = new TestMovingViolationFactory();
        Incident incident = factory.createViolation();

        assertNotNull(incident);
        assertEquals("Distracted driving", incident.getIncidentType());
        assertEquals(IncidentType.MOVING_VIOLATION, incident.getIncidentCategory());
    }

    /**
     * Tests that a non-moving violation factory creates appropriate incidents.
     * Verifies factory creates correct incident type.
     */
    @Test
    void testNonMovingViolationFactoryCreatesIncident() {
        IncidentFactory factory = new TestNonMovingViolationFactory();
        Incident incident = factory.createViolation();

        assertNotNull(incident);
        assertEquals("Parking violation", incident.getIncidentType());
        assertEquals(IncidentType.NONMOVING_VIOLATION, incident.getIncidentCategory());
    }

    /**
     * Tests that createViolation() can be called multiple times.
     * Verifies factory can be reused.
     */
    @Test
    void testCreateViolationCanBeCalledMultipleTimes() {
        IncidentFactory factory = new TestCrashFactory();

        Incident incident1 = factory.createViolation();
        Incident incident2 = factory.createViolation();

        assertNotNull(incident1);
        assertNotNull(incident2);
    }

    /**
     * Tests that each call to createViolation() creates a new instance.
     * Verifies factory creates distinct objects.
     */
    @Test
    void testCreateViolationCreatesNewInstances() {
        IncidentFactory factory = new TestNewInstanceFactory();

        Incident incident1 = factory.createViolation();
        Incident incident2 = factory.createViolation();

        assertNotSame(incident1, incident2);
    }

    // ============ Parameterized Factory Tests ============

    /**
     * Tests parameterized factory with crash type.
     * Verifies factory can use configuration data.
     */
    @Test
    void testParameterizedFactoryWithCrash() {
        IncidentFactory factory = new TestParameterizedFactory(
                "Fender bender", IncidentType.CRASH);
        Incident incident = factory.createViolation();

        assertNotNull(incident);
        assertEquals("Fender bender", incident.getIncidentType());
        assertEquals(IncidentType.CRASH, incident.getIncidentCategory());
    }

    /**
     * Tests parameterized factory with moving violation type.
     * Verifies factory configuration flexibility.
     */
    @Test
    void testParameterizedFactoryWithMovingViolation() {
        IncidentFactory factory = new TestParameterizedFactory(
                "Speeding", IncidentType.MOVING_VIOLATION);
        Incident incident = factory.createViolation();

        assertNotNull(incident);
        assertEquals("Speeding", incident.getIncidentType());
        assertEquals(IncidentType.MOVING_VIOLATION, incident.getIncidentCategory());
    }

    /**
     * Tests incident creation with different factory configurations.
     */
    @Test
    void testMultipleParameterizedFactories() {
        IncidentFactory factory1 = new TestParameterizedFactory(
                "Type A", IncidentType.CRASH);
        IncidentFactory factory2 = new TestParameterizedFactory(
                "Type B", IncidentType.MOVING_VIOLATION);

        Incident incident1 = factory1.createViolation();
        Incident incident2 = factory2.createViolation();

        assertEquals("Type A", incident1.getIncidentType());
        assertEquals("Type B", incident2.getIncidentType());
        assertNotEquals(incident1.getIncidentCategory(), incident2.getIncidentCategory());
    }

    /**
     * Tests parameterized factory with null values.
     * Verifies factory handles null configuration.
     */
    @Test
    void testParameterizedFactoryWithNullValues() {
        IncidentFactory factory = new TestParameterizedFactory(null, null);
        Incident incident = factory.createViolation();

        assertNotNull(incident);
        assertNull(incident.getIncidentType());
        assertNull(incident.getIncidentCategory());
    }

    // ============ Edge Case Tests ============

    /**
     * Tests factory that returns null from createViolation().
     * Verifies null return value is allowed (though not recommended).
     */
    @Test
    void testFactoryReturningNull() {
        IncidentFactory factory = new TestNullFactory();
        Incident incident = factory.createViolation();

        assertNull(incident);
    }

    /**
     * Tests that createViolation() doesn't throw exceptions.
     * Verifies basic exception safety.
     */
    @Test
    void testCreateViolationDoesNotThrowException() {
        IncidentFactory factory = new TestCrashFactory();

        assertDoesNotThrow(() -> factory.createViolation());
    }

    // ============ Polymorphism Tests ============

    /**
     * Tests polymorphic usage of different factory implementations.
     * Verifies factories can be used through abstract base type.
     */
    @Test
    void testPolymorphicFactoryUsage() {
        IncidentFactory[] factories = new IncidentFactory[]{
                new TestCrashFactory(),
                new TestMovingViolationFactory(),
                new TestNonMovingViolationFactory()
        };

        for (IncidentFactory factory : factories) {
            Incident incident = factory.createViolation();
            assertNotNull(incident);
            assertNotNull(incident.getIncidentType());
            assertNotNull(incident.getIncidentCategory());
        }
    }

    /**
     * Tests that different factories create incidents with different types.
     * Verifies factory diversity.
     */
    @Test
    void testDifferentFactoriesCreateDifferentIncidents() {
        IncidentFactory crashFactory = new TestCrashFactory();
        IncidentFactory violationFactory = new TestMovingViolationFactory();

        Incident crash = crashFactory.createViolation();
        Incident violation = violationFactory.createViolation();

        assertNotEquals(crash.getIncidentCategory(), violation.getIncidentCategory());
    }

    /**
     * Tests that all factory implementations are instances of IncidentFactory.
     * Verifies proper inheritance hierarchy.
     */
    @Test
    void testAllFactoriesAreInstancesOfIncidentFactory() {
        IncidentFactory crash = new TestCrashFactory();
        IncidentFactory moving = new TestMovingViolationFactory();
        IncidentFactory nonMoving = new TestNonMovingViolationFactory();
        IncidentFactory parameterized = new TestParameterizedFactory("test", IncidentType.CRASH);

        assertInstanceOf(IncidentFactory.class, crash);
        assertInstanceOf(IncidentFactory.class, moving);
        assertInstanceOf(IncidentFactory.class, nonMoving);
        assertInstanceOf(IncidentFactory.class, parameterized);
    }

    // ============ Factory Selection Tests ============

    /**
     * Tests factory selection by incident category.
     */
    @Test
    void testFactorySelectionPattern() {
        // Simulate selecting factory based on incident category
        IncidentType desiredCategory = IncidentType.CRASH;
        IncidentFactory factory = getFactoryForCategory(desiredCategory);

        Incident incident = factory.createViolation();

        assertNotNull(incident);
        assertEquals(desiredCategory, incident.getIncidentCategory());
    }

    /**
     * Helper method simulating factory selection logic.
     */
    private IncidentFactory getFactoryForCategory(IncidentType category) {
        switch (category) {
            case CRASH:
                return new TestCrashFactory();
            case MOVING_VIOLATION:
                return new TestMovingViolationFactory();
            case NONMOVING_VIOLATION:
                return new TestNonMovingViolationFactory();
            default:
                throw new IllegalArgumentException("Unknown category");
        }
    }

    /**
     * Tests factory selection for each supported incident category.
     */
    @Test
    void testFactorySelectionForAllCategories() {
        for (IncidentType category : IncidentType.values()) {
            IncidentFactory factory = getFactoryForCategory(category);
            Incident incident = factory.createViolation();

            assertNotNull(incident);
            assertEquals(category, incident.getIncidentCategory());
        }
    }

    // ============ Type Safety Tests ============

    /**
     * Tests that factory returns correct incident type.
     * Verifies type contract.
     */
    @Test
    void testFactoryReturnsIncidentType() {
        IncidentFactory factory = new TestCrashFactory();
        Object result = factory.createViolation();

        assertInstanceOf(Incident.class, result);
    }

    /**
     * Tests type safety with variable assignment.
     * Verifies compile-time type safety.
     */
    @Test
    void testTypeSafetyWithVariableAssignment() {
        IncidentFactory factory = new TestCrashFactory();
        Incident incident = factory.createViolation(); // Should compile without cast

        assertNotNull(incident);
    }

    // ============ State Independence Tests ============

    /**
     * Tests that factory instances are independent.
     * Verifies no shared state between factory instances.
     */
    @Test
    void testFactoryInstancesAreIndependent() {
        IncidentFactory factory1 = new TestParameterizedFactory("Type 1", IncidentType.CRASH);
        IncidentFactory factory2 = new TestParameterizedFactory("Type 2", IncidentType.MOVING_VIOLATION);

        Incident incident1 = factory1.createViolation();
        Incident incident2 = factory2.createViolation();

        assertEquals("Type 1", incident1.getIncidentType());
        assertEquals("Type 2", incident2.getIncidentType());
    }

    /**
     * Tests that created incidents are independent.
     * Verifies no shared state between created incidents.
     */
    @Test
    void testCreatedIncidentsAreIndependent() {
        IncidentFactory factory = new TestParameterizedFactory("Test", IncidentType.CRASH);

        Incident incident1 = factory.createViolation();
        Incident incident2 = factory.createViolation();

        // Both should have same type but be different instances
        assertEquals(incident1.getIncidentType(), incident2.getIncidentType());
        assertNotSame(incident1, incident2);
    }

    // ============ Integration Tests ============

    /**
     * Tests factory selection, incident creation, and resulting properties.
     */
    @Test
    void testCompleteFactoryWorkflow() {
        // 1. Select factory
        IncidentFactory factory = new TestCrashFactory();

        // 2. Create incident
        Incident incident = factory.createViolation();

        // 3. Use incident
        assertNotNull(incident);
        String type = incident.getIncidentType();
        IncidentType category = incident.getIncidentCategory();

        // 4. Verify incident properties
        assertNotNull(type);
        assertNotNull(category);
        assertEquals(IncidentType.CRASH, category);
    }

    /**
     * Tests factory with batch incident creation.
     * Verifies factory can handle multiple creation requests.
     */
    @Test
    void testBatchIncidentCreation() {
        IncidentFactory factory = new TestCrashFactory();
        int batchSize = 10;
        Incident[] incidents = new Incident[batchSize];

        for (int i = 0; i < batchSize; i++) {
            incidents[i] = factory.createViolation();
        }

        // Verify all incidents were created
        for (Incident incident : incidents) {
            assertNotNull(incident);
            assertEquals(IncidentType.CRASH, incident.getIncidentCategory());
        }
    }

    /**
     * Tests dynamic factory selection for a sequence of incident categories.
     */
    @Test
    void testDynamicFactorySelection() {
        String[] typeDescriptions = {
                "Crash without bodily injuries",
                "Distracted driving",
                "Parking violation"
        };

        IncidentType[] categories = {
                IncidentType.CRASH,
                IncidentType.MOVING_VIOLATION,
                IncidentType.NONMOVING_VIOLATION
        };

        for (int i = 0; i < typeDescriptions.length; i++) {
            IncidentFactory factory = new TestParameterizedFactory(
                    typeDescriptions[i], categories[i]);
            Incident incident = factory.createViolation();

            assertEquals(typeDescriptions[i], incident.getIncidentType());
            assertEquals(categories[i], incident.getIncidentCategory());
        }
    }
}