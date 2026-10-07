package Registration.IncidentFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests non-moving violation creation and configuration through the factory.
 */
class NonMovingViolationFactoryTest {

    private NonMovingViolationFactory factory;

    /**
     * Sets up test fixtures before each test method.
     * Creates a new NonMovingViolationFactory instance for testing.
     */
    @BeforeEach
    void setUp() {
        factory = new NonMovingViolationFactory();
    }

    // ============ Constructor Tests ============

    /**
     * Tests that NonMovingViolationFactory can be instantiated.
     */
    @Test
    void testFactoryInstantiation() {
        NonMovingViolationFactory factory = new NonMovingViolationFactory();

        assertNotNull(factory);
    }

    /**
     * Tests that NonMovingViolationFactory is an instance of IncidentFactory.
     */
    @Test
    void testFactoryExtendsIncidentFactory() {
        assertInstanceOf(IncidentFactory.class, factory);
    }

    // ============ createViolation() Tests ============

    /**
     * Tests that createViolation() returns a non-null incident.
     */
    @Test
    void testCreateViolationReturnsNonNull() {
        Incident incident = factory.createViolation();

        assertNotNull(incident);
    }

    /**
     * Tests that createViolation() returns a NonMovingViolation instance.
     */
    @Test
    void testCreateViolationReturnsNonMovingViolation() {
        Incident incident = factory.createViolation();

        assertInstanceOf(NonMovingViolation.class, incident);
    }

    /**
     * Tests that created non-moving violation is also an Incident.
     */
    @Test
    void testCreatedNonMovingViolationIsIncident() {
        Incident incident = factory.createViolation();

        assertInstanceOf(Incident.class, incident);
    }

    /**
     * Tests that created non-moving violation has null violation type.
     */
    @Test
    void testCreatedNonMovingViolationHasNullViolationType() {
        Incident incident = factory.createViolation();
        NonMovingViolation violation = (NonMovingViolation) incident;

        assertNull(violation.getViolationType());
    }

    /**
     * Tests that created non-moving violation has correct incident category.
     */
    @Test
    void testCreatedNonMovingViolationHasCorrectCategory() {
        Incident incident = factory.createViolation();

        assertEquals(IncidentType.NONMOVING_VIOLATION, incident.getIncidentCategory());
    }

    /**
     * Tests that getIncidentType() throws exception on newly created violation.
     */
    @Test
    void testCreatedNonMovingViolationGetIncidentTypeThrows() {
        Incident incident = factory.createViolation();

        assertThrows(NullPointerException.class, () -> {
            incident.getIncidentType();
        });
    }

    // ============ Multiple Creation Tests ============

    /**
     * Tests that createViolation() can be called multiple times.
     */
    @Test
    void testMultipleCreations() {
        Incident incident1 = factory.createViolation();
        Incident incident2 = factory.createViolation();

        assertNotNull(incident1);
        assertNotNull(incident2);
    }

    /**
     * Tests that each call to createViolation() creates a new instance.
     */
    @Test
    void testCreateViolationCreatesNewInstances() {
        Incident incident1 = factory.createViolation();
        Incident incident2 = factory.createViolation();

        assertNotSame(incident1, incident2);
    }

    /**
     * Tests that multiple created violations are independent.
     */
    @Test
    void testMultipleCreatedViolationsAreIndependent() {
        NonMovingViolation violation1 = (NonMovingViolation) factory.createViolation();
        NonMovingViolation violation2 = (NonMovingViolation) factory.createViolation();

        violation1.setViolationType(NonMovingViolationEnum.PARKING_VIOLATION);

        assertNull(violation2.getViolationType());
    }

    // ============ Polymorphic Usage Tests ============

    /**
     * Tests using NonMovingViolationFactory through IncidentFactory reference.
     */
    @Test
    void testPolymorphicFactoryUsage() {
        IncidentFactory incidentFactory = new NonMovingViolationFactory();
        Incident incident = incidentFactory.createViolation();

        assertNotNull(incident);
        assertInstanceOf(NonMovingViolation.class, incident);
    }

    /**
     * Tests that factory method returns Incident type polymorphically.
     */
    @Test
    void testFactoryMethodReturnsIncidentType() {
        IncidentFactory incidentFactory = factory;
        Incident incident = incidentFactory.createViolation();

        assertInstanceOf(Incident.class, incident);
        assertEquals(IncidentType.NONMOVING_VIOLATION, incident.getIncidentCategory());
    }

    // ============ Configuration After Creation Tests ============

    /**
     * Tests configuring violation after creation with PARKING_VIOLATION.
     */
    @Test
    void testConfigureViolationAfterCreationWithParkingViolation() {
        Incident incident = factory.createViolation();
        NonMovingViolation violation = (NonMovingViolation) incident;

        violation.setViolationType(NonMovingViolationEnum.PARKING_VIOLATION);

        assertEquals(NonMovingViolationEnum.PARKING_VIOLATION, violation.getViolationType());
        assertEquals("Parking Violation", violation.getIncidentType());
    }

    /**
     * Tests configuring violation after creation with PAPERWORK_ISSUE.
     */
    @Test
    void testConfigureViolationAfterCreationWithPaperworkIssue() {
        Incident incident = factory.createViolation();
        NonMovingViolation violation = (NonMovingViolation) incident;

        violation.setViolationType(NonMovingViolationEnum.PAPERWORK_ISSUE);

        assertEquals(NonMovingViolationEnum.PAPERWORK_ISSUE, violation.getViolationType());
        assertEquals("Paper Work Issue", violation.getIncidentType());
    }

    /**
     * Tests configuring violation after creation with VEHICLE_PROBLEM.
     */
    @Test
    void testConfigureViolationAfterCreationWithVehicleProblem() {
        Incident incident = factory.createViolation();
        NonMovingViolation violation = (NonMovingViolation) incident;

        violation.setViolationType(NonMovingViolationEnum.VEHICLE_PROBLEM);

        assertEquals(NonMovingViolationEnum.VEHICLE_PROBLEM, violation.getViolationType());
        assertEquals("Vehicle Problem", violation.getIncidentType());
    }

    /**
     * Tests complete workflow: create, configure, use.
     */
    @Test
    void testCompleteFactoryWorkflow() {
        // Create
        Incident incident = factory.createViolation();

        // Verify initial state
        assertInstanceOf(NonMovingViolation.class, incident);
        assertEquals(IncidentType.NONMOVING_VIOLATION, incident.getIncidentCategory());

        // Configure
        NonMovingViolation violation = (NonMovingViolation) incident;
        violation.setViolationType(NonMovingViolationEnum.PARKING_VIOLATION);

        // Use
        assertEquals("Parking Violation", incident.getIncidentType());
        assertEquals(IncidentType.NONMOVING_VIOLATION, incident.getIncidentCategory());
    }

    // ============ Factory Selection Tests ============

    /**
     * Tests factory in array of factories.
     */
    @Test
    void testFactoryInArray() {
        IncidentFactory[] factories = new IncidentFactory[]{
                new NonMovingViolationFactory(),
                new NonMovingViolationFactory()
        };

        for (IncidentFactory f : factories) {
            Incident incident = f.createViolation();
            assertInstanceOf(NonMovingViolation.class, incident);
        }
    }

    /**
     * Tests selecting a factory by incident type.
     */
    @Test
    void testFactorySelectionPattern() {
        IncidentFactory selectedFactory = selectFactory(IncidentType.NONMOVING_VIOLATION);
        Incident incident = selectedFactory.createViolation();

        assertNotNull(incident);
        assertInstanceOf(NonMovingViolation.class, incident);
        assertEquals(IncidentType.NONMOVING_VIOLATION, incident.getIncidentCategory());
    }

    /**
     * Helper method simulating factory selection logic.
     */
    private IncidentFactory selectFactory(IncidentType category) {
        if (category == IncidentType.NONMOVING_VIOLATION) {
            return new NonMovingViolationFactory();
        }
        throw new IllegalArgumentException("Unsupported category");
    }

    /**
     * Tests batch creation of non-moving violations.
     */
    @Test
    void testBatchNonMovingViolationCreation() {
        int batchSize = 5;
        NonMovingViolation[] violations = new NonMovingViolation[batchSize];

        for (int i = 0; i < batchSize; i++) {
            violations[i] = (NonMovingViolation) factory.createViolation();
        }

        // Verify all violations were created
        for (NonMovingViolation violation : violations) {
            assertNotNull(violation);
            assertNull(violation.getViolationType());
            assertEquals(IncidentType.NONMOVING_VIOLATION, violation.getIncidentCategory());
        }
    }

    // ============ Type Safety Tests ============

    /**
     * Tests that factory method return type is Incident.
     */
    @Test
    void testFactoryMethodReturnType() {
        Incident incident = factory.createViolation();

        // Should compile without cast when assigned to Incident
        assertNotNull(incident);
    }

    /**
     * Tests safe downcasting to NonMovingViolation.
     */
    @Test
    void testSafeDowncasting() {
        Incident incident = factory.createViolation();

        if (incident instanceof NonMovingViolation) {
            NonMovingViolation violation = (NonMovingViolation) incident;
            assertNotNull(violation);
        } else {
            fail("Created incident should be a NonMovingViolation");
        }
    }

    // ============ Consistency Tests ============

    /**
     * Tests that all created violations have consistent category.
     */
    @Test
    void testAllCreatedViolationsHaveConsistentCategory() {
        for (int i = 0; i < 10; i++) {
            Incident incident = factory.createViolation();
            assertEquals(IncidentType.NONMOVING_VIOLATION, incident.getIncidentCategory());
        }
    }

    /**
     * Tests that factory behavior is consistent across multiple instances.
     */
    @Test
    void testMultipleFactoryInstancesConsistency() {
        NonMovingViolationFactory factory1 = new NonMovingViolationFactory();
        NonMovingViolationFactory factory2 = new NonMovingViolationFactory();

        Incident incident1 = factory1.createViolation();
        Incident incident2 = factory2.createViolation();

        assertEquals(incident1.getClass(), incident2.getClass());
        assertEquals(incident1.getIncidentCategory(), incident2.getIncidentCategory());
    }

    // ============ Exception Safety Tests ============

    /**
     * Tests that createViolation() doesn't throw exceptions.
     */
    @Test
    void testCreateViolationDoesNotThrow() {
        assertDoesNotThrow(() -> factory.createViolation());
    }

    /**
     * Tests that factory can be called repeatedly without issues.
     */
    @Test
    void testRepeatedCallsDoNotCauseIssues() {
        assertDoesNotThrow(() -> {
            for (int i = 0; i < 100; i++) {
                factory.createViolation();
            }
        });
    }

    // ============ Integration with NonMovingViolation Tests ============

    /**
     * Tests that created violation works with all NonMovingViolationEnum values.
     */
    @Test
    void testCreatedViolationWorksWithAllViolationTypes() {
        for (NonMovingViolationEnum violationType : NonMovingViolationEnum.values()) {
            NonMovingViolation violation = (NonMovingViolation) factory.createViolation();
            violation.setViolationType(violationType);

            assertEquals(violationType, violation.getViolationType());
            assertNotNull(violation.getIncidentType());
        }
    }

    /**
     * Tests factory-created violation in incident processing workflow.
     */
    @Test
    void testFactoryInIncidentProcessingWorkflow() {
        // Factory selection
        IncidentFactory factory = new NonMovingViolationFactory();

        // Incident creation
        Incident incident = factory.createViolation();

        // Type checking
        assertTrue(incident instanceof NonMovingViolation);

        // Configuration
        NonMovingViolation violation = (NonMovingViolation) incident;
        violation.setViolationType(NonMovingViolationEnum.PARKING_VIOLATION);

        // Validation usage
        assertEquals(IncidentType.NONMOVING_VIOLATION, incident.getIncidentCategory());
        assertEquals("Parking Violation", incident.getIncidentType());
    }

    /**
     * Tests creating violations for different categories.
     */
    @Test
    void testCreateViolationsForDifferentCategories() {
        // Create location-based violation
        NonMovingViolation parking = (NonMovingViolation) factory.createViolation();
        parking.setViolationType(NonMovingViolationEnum.PARKING_VIOLATION);
        assertEquals("Parking Violation", parking.getIncidentType());

        // Create documentation violation
        NonMovingViolation paperwork = (NonMovingViolation) factory.createViolation();
        paperwork.setViolationType(NonMovingViolationEnum.PAPERWORK_ISSUE);
        assertEquals("Paper Work Issue", paperwork.getIncidentType());

        // Create vehicle condition violation
        NonMovingViolation vehicle = (NonMovingViolation) factory.createViolation();
        vehicle.setViolationType(NonMovingViolationEnum.VEHICLE_PROBLEM);
        assertEquals("Vehicle Problem", vehicle.getIncidentType());
    }

    // ============ State Independence Tests ============

    /**
     * Tests that factory instances are stateless.
     */
    @Test
    void testFactoryIsStateless() {
        NonMovingViolationFactory factory1 = new NonMovingViolationFactory();
        NonMovingViolationFactory factory2 = new NonMovingViolationFactory();

        NonMovingViolation violation1 = (NonMovingViolation) factory1.createViolation();
        violation1.setViolationType(NonMovingViolationEnum.PARKING_VIOLATION);

        NonMovingViolation violation2 = (NonMovingViolation) factory2.createViolation();

        // violation2 should not be affected by violation1's configuration
        assertNull(violation2.getViolationType());
    }

    /**
     * Tests that factory doesn't maintain references to created objects.
     */
    @Test
    void testFactoryDoesNotMaintainReferences() {
        NonMovingViolation violation1 = (NonMovingViolation) factory.createViolation();
        violation1.setViolationType(NonMovingViolationEnum.PARKING_VIOLATION);

        NonMovingViolation violation2 = (NonMovingViolation) factory.createViolation();

        // violation2 should be independent
        assertNull(violation2.getViolationType());
        assertEquals(NonMovingViolationEnum.PARKING_VIOLATION, violation1.getViolationType());
    }

    // ============ Method Override Tests ============

    /**
     * Tests that createViolation() properly overrides abstract method.
     */
    @Test
    void testCreateViolationOverridesAbstractMethod() {
        // If this compiles and runs, the override is correct
        IncidentFactory abstractFactory = factory;
        Incident incident = abstractFactory.createViolation();

        assertNotNull(incident);
        assertInstanceOf(NonMovingViolation.class, incident);
    }

    /**
     * Tests that factory implements all required abstract methods.
     */
    @Test
    void testFactoryImplementsAllAbstractMethods() {
        // If NonMovingViolationFactory can be instantiated, all abstract methods are implemented
        assertDoesNotThrow(() -> new NonMovingViolationFactory());
    }

    // ============ Comparison with Other Factory Types ============

    /**
     * Tests that NonMovingViolationFactory creates different type than MovingViolationFactory.
     */
    @Test
    void testNonMovingViolationFactoryVsMovingViolationFactory() {
        NonMovingViolationFactory nonMovingFactory = new NonMovingViolationFactory();
        MovingViolationFactory movingFactory = new MovingViolationFactory();

        Incident nonMovingIncident = nonMovingFactory.createViolation();
        Incident movingIncident = movingFactory.createViolation();

        assertInstanceOf(NonMovingViolation.class, nonMovingIncident);
        assertInstanceOf(MovingViolation.class, movingIncident);
        assertNotEquals(nonMovingIncident.getClass(), movingIncident.getClass());
        assertNotEquals(nonMovingIncident.getIncidentCategory(), movingIncident.getIncidentCategory());
    }

    /**
     * Tests that NonMovingViolationFactory creates different type than CrashFactory.
     */
    @Test
    void testNonMovingViolationFactoryVsCrashFactory() {
        NonMovingViolationFactory nonMovingFactory = new NonMovingViolationFactory();
        CrashFactory crashFactory = new CrashFactory();

        Incident nonMovingIncident = nonMovingFactory.createViolation();
        Incident crashIncident = crashFactory.createViolation();

        assertInstanceOf(NonMovingViolation.class, nonMovingIncident);
        assertInstanceOf(Crash.class, crashIncident);
        assertNotEquals(nonMovingIncident.getClass(), crashIncident.getClass());
        assertNotEquals(nonMovingIncident.getIncidentCategory(), crashIncident.getIncidentCategory());
    }

    /**
     * Tests heterogeneous factory array with different factory types.
     */
    @Test
    void testHeterogeneousFactoryArray() {
        IncidentFactory[] factories = new IncidentFactory[]{
                new NonMovingViolationFactory(),
                new MovingViolationFactory(),
                new CrashFactory()
        };

        Incident nonMoving = factories[0].createViolation();
        Incident moving = factories[1].createViolation();
        Incident crash = factories[2].createViolation();

        assertInstanceOf(NonMovingViolation.class, nonMoving);
        assertInstanceOf(MovingViolation.class, moving);
        assertInstanceOf(Crash.class, crash);
    }

    /**
     * Tests that all three factory types create distinct incident categories.
     */
    @Test
    void testAllFactoryTypesCreateDistinctCategories() {
        NonMovingViolationFactory nonMovingFactory = new NonMovingViolationFactory();
        MovingViolationFactory movingFactory = new MovingViolationFactory();
        CrashFactory crashFactory = new CrashFactory();

        Incident nonMoving = nonMovingFactory.createViolation();
        Incident moving = movingFactory.createViolation();
        Incident crash = crashFactory.createViolation();

        assertEquals(IncidentType.NONMOVING_VIOLATION, nonMoving.getIncidentCategory());
        assertEquals(IncidentType.MOVING_VIOLATION, moving.getIncidentCategory());
        assertEquals(IncidentType.CRASH, crash.getIncidentCategory());

        // Verify all categories are distinct
        assertNotEquals(nonMoving.getIncidentCategory(), moving.getIncidentCategory());
        assertNotEquals(moving.getIncidentCategory(), crash.getIncidentCategory());
        assertNotEquals(crash.getIncidentCategory(), nonMoving.getIncidentCategory());
    }
}