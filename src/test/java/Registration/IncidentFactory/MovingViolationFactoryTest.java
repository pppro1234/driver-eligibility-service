package Registration.IncidentFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests moving violation creation and configuration through the factory.
 */
class MovingViolationFactoryTest {

    private MovingViolationFactory factory;

    /**
     * Sets up test fixtures before each test method.
     * Creates a new MovingViolationFactory instance for testing.
     */
    @BeforeEach
    void setUp() {
        factory = new MovingViolationFactory();
    }

    // ============ Constructor Tests ============

    /**
     * Tests that MovingViolationFactory can be instantiated.
     */
    @Test
    void testFactoryInstantiation() {
        MovingViolationFactory factory = new MovingViolationFactory();

        assertNotNull(factory);
    }

    /**
     * Tests that MovingViolationFactory is an instance of IncidentFactory.
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
     * Tests that createViolation() returns a MovingViolation instance.
     */
    @Test
    void testCreateViolationReturnsMovingViolation() {
        Incident incident = factory.createViolation();

        assertInstanceOf(MovingViolation.class, incident);
    }

    /**
     * Tests that created moving violation is also an Incident.
     */
    @Test
    void testCreatedMovingViolationIsIncident() {
        Incident incident = factory.createViolation();

        assertInstanceOf(Incident.class, incident);
    }

    /**
     * Tests that created moving violation has null violation type.
     */
    @Test
    void testCreatedMovingViolationHasNullViolationType() {
        Incident incident = factory.createViolation();
        MovingViolation violation = (MovingViolation) incident;

        assertNull(violation.getViolationType());
    }

    /**
     * Tests that created moving violation has correct incident category.
     */
    @Test
    void testCreatedMovingViolationHasCorrectCategory() {
        Incident incident = factory.createViolation();

        assertEquals(IncidentType.MOVING_VIOLATION, incident.getIncidentCategory());
    }

    /**
     * Tests that getIncidentType() throws exception on newly created violation.
     */
    @Test
    void testCreatedMovingViolationGetIncidentTypeThrows() {
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
        MovingViolation violation1 = (MovingViolation) factory.createViolation();
        MovingViolation violation2 = (MovingViolation) factory.createViolation();

        violation1.setViolationType(MovingViolationEnum.SPEEDING);

        assertNull(violation2.getViolationType());
    }

    // ============ Polymorphic Usage Tests ============

    /**
     * Tests using MovingViolationFactory through IncidentFactory reference.
     */
    @Test
    void testPolymorphicFactoryUsage() {
        IncidentFactory incidentFactory = new MovingViolationFactory();
        Incident incident = incidentFactory.createViolation();

        assertNotNull(incident);
        assertInstanceOf(MovingViolation.class, incident);
    }

    /**
     * Tests that factory method returns Incident type polymorphically.
     */
    @Test
    void testFactoryMethodReturnsIncidentType() {
        IncidentFactory incidentFactory = factory;
        Incident incident = incidentFactory.createViolation();

        assertInstanceOf(Incident.class, incident);
        assertEquals(IncidentType.MOVING_VIOLATION, incident.getIncidentCategory());
    }

    // ============ Configuration After Creation Tests ============

    /**
     * Tests configuring violation after creation with SPEEDING.
     */
    @Test
    void testConfigureViolationAfterCreationWithSpeeding() {
        Incident incident = factory.createViolation();
        MovingViolation violation = (MovingViolation) incident;

        violation.setViolationType(MovingViolationEnum.SPEEDING);

        assertEquals(MovingViolationEnum.SPEEDING, violation.getViolationType());
        assertEquals("Speeding", violation.getIncidentType());
    }

    /**
     * Tests configuring violation after creation with DUI.
     */
    @Test
    void testConfigureViolationAfterCreationWithDui() {
        Incident incident = factory.createViolation();
        MovingViolation violation = (MovingViolation) incident;

        violation.setViolationType(MovingViolationEnum.DUI);

        assertEquals(MovingViolationEnum.DUI, violation.getViolationType());
        assertEquals("Driving Under Influence", violation.getIncidentType());
    }

    /**
     * Tests configuring violation after creation with DISTRACTED_DRIVING.
     */
    @Test
    void testConfigureViolationAfterCreationWithDistractedDriving() {
        Incident incident = factory.createViolation();
        MovingViolation violation = (MovingViolation) incident;

        violation.setViolationType(MovingViolationEnum.DISTRACTED_DRIVING);

        assertEquals(MovingViolationEnum.DISTRACTED_DRIVING, violation.getViolationType());
        assertEquals("Distracted Driving", violation.getIncidentType());
    }

    /**
     * Tests configuring violation after creation with RECKLESS_DRIVING.
     */
    @Test
    void testConfigureViolationAfterCreationWithRecklessDriving() {
        Incident incident = factory.createViolation();
        MovingViolation violation = (MovingViolation) incident;

        violation.setViolationType(MovingViolationEnum.RECKLESS_DRIVING);

        assertEquals(MovingViolationEnum.RECKLESS_DRIVING, violation.getViolationType());
        assertEquals("Reckless Driving", violation.getIncidentType());
    }

    /**
     * Tests configuring violation after creation with FAILURE_TO_OBEY_SIGNS.
     */
    @Test
    void testConfigureViolationAfterCreationWithFailureToObeySigns() {
        Incident incident = factory.createViolation();
        MovingViolation violation = (MovingViolation) incident;

        violation.setViolationType(MovingViolationEnum.FAILURE_TO_OBEY_SIGNS);

        assertEquals(MovingViolationEnum.FAILURE_TO_OBEY_SIGNS, violation.getViolationType());
        assertEquals("Failure to Obey Traffic Signs", violation.getIncidentType());
    }

    /**
     * Tests configuring violation after creation with DRIVING_WITHOUT_VALID_LICENSE_INSURANCE.
     */
    @Test
    void testConfigureViolationAfterCreationWithDrivingWithoutValidLicenseInsurance() {
        Incident incident = factory.createViolation();
        MovingViolation violation = (MovingViolation) incident;

        violation.setViolationType(MovingViolationEnum.DRIVING_WITHOUT_VALID_LICENSE_INSURANCE);

        assertEquals(MovingViolationEnum.DRIVING_WITHOUT_VALID_LICENSE_INSURANCE, violation.getViolationType());
        assertEquals("Driving Without Valid License/Insurance", violation.getIncidentType());
    }

    /**
     * Tests complete workflow: create, configure, use.
     */
    @Test
    void testCompleteFactoryWorkflow() {
        // Create
        Incident incident = factory.createViolation();

        // Verify initial state
        assertInstanceOf(MovingViolation.class, incident);
        assertEquals(IncidentType.MOVING_VIOLATION, incident.getIncidentCategory());

        // Configure
        MovingViolation violation = (MovingViolation) incident;
        violation.setViolationType(MovingViolationEnum.SPEEDING);

        // Use
        assertEquals("Speeding", incident.getIncidentType());
        assertEquals(IncidentType.MOVING_VIOLATION, incident.getIncidentCategory());
    }

    // ============ Factory Selection Tests ============

    /**
     * Tests factory in array of factories.
     */
    @Test
    void testFactoryInArray() {
        IncidentFactory[] factories = new IncidentFactory[]{
                new MovingViolationFactory(),
                new MovingViolationFactory()
        };

        for (IncidentFactory f : factories) {
            Incident incident = f.createViolation();
            assertInstanceOf(MovingViolation.class, incident);
        }
    }

    /**
     * Tests selecting a factory by incident type.
     */
    @Test
    void testFactorySelectionPattern() {
        IncidentFactory selectedFactory = selectFactory(IncidentType.MOVING_VIOLATION);
        Incident incident = selectedFactory.createViolation();

        assertNotNull(incident);
        assertInstanceOf(MovingViolation.class, incident);
        assertEquals(IncidentType.MOVING_VIOLATION, incident.getIncidentCategory());
    }

    /**
     * Helper method simulating factory selection logic.
     */
    private IncidentFactory selectFactory(IncidentType category) {
        if (category == IncidentType.MOVING_VIOLATION) {
            return new MovingViolationFactory();
        }
        throw new IllegalArgumentException("Unsupported category");
    }

    /**
     * Tests batch creation of moving violations.
     */
    @Test
    void testBatchMovingViolationCreation() {
        int batchSize = 5;
        MovingViolation[] violations = new MovingViolation[batchSize];

        for (int i = 0; i < batchSize; i++) {
            violations[i] = (MovingViolation) factory.createViolation();
        }

        // Verify all violations were created
        for (MovingViolation violation : violations) {
            assertNotNull(violation);
            assertNull(violation.getViolationType());
            assertEquals(IncidentType.MOVING_VIOLATION, violation.getIncidentCategory());
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
     * Tests safe downcasting to MovingViolation.
     */
    @Test
    void testSafeDowncasting() {
        Incident incident = factory.createViolation();

        if (incident instanceof MovingViolation) {
            MovingViolation violation = (MovingViolation) incident;
            assertNotNull(violation);
        } else {
            fail("Created incident should be a MovingViolation");
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
            assertEquals(IncidentType.MOVING_VIOLATION, incident.getIncidentCategory());
        }
    }

    /**
     * Tests that factory behavior is consistent across multiple instances.
     */
    @Test
    void testMultipleFactoryInstancesConsistency() {
        MovingViolationFactory factory1 = new MovingViolationFactory();
        MovingViolationFactory factory2 = new MovingViolationFactory();

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

    // ============ Integration with MovingViolation Tests ============

    /**
     * Tests that created violation works with all MovingViolationEnum values.
     */
    @Test
    void testCreatedViolationWorksWithAllViolationTypes() {
        for (MovingViolationEnum violationType : MovingViolationEnum.values()) {
            MovingViolation violation = (MovingViolation) factory.createViolation();
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
        IncidentFactory factory = new MovingViolationFactory();

        // Incident creation
        Incident incident = factory.createViolation();

        // Type checking
        assertTrue(incident instanceof MovingViolation);

        // Configuration
        MovingViolation violation = (MovingViolation) incident;
        violation.setViolationType(MovingViolationEnum.DUI);

        // Validation usage
        assertEquals(IncidentType.MOVING_VIOLATION, incident.getIncidentCategory());
        assertEquals("Driving Under Influence", incident.getIncidentType());
    }

    /**
     * Tests creating violations for different severity levels.
     */
    @Test
    void testCreateViolationsForDifferentSeverities() {
        // Create common violation
        MovingViolation common = (MovingViolation) factory.createViolation();
        common.setViolationType(MovingViolationEnum.SPEEDING);
        assertEquals("Speeding", common.getIncidentType());

        // Create serious violation
        MovingViolation serious = (MovingViolation) factory.createViolation();
        serious.setViolationType(MovingViolationEnum.RECKLESS_DRIVING);
        assertEquals("Reckless Driving", serious.getIncidentType());

        // Create critical violation
        MovingViolation critical = (MovingViolation) factory.createViolation();
        critical.setViolationType(MovingViolationEnum.DUI);
        assertEquals("Driving Under Influence", critical.getIncidentType());
    }

    // ============ State Independence Tests ============

    /**
     * Tests that factory instances are stateless.
     */
    @Test
    void testFactoryIsStateless() {
        MovingViolationFactory factory1 = new MovingViolationFactory();
        MovingViolationFactory factory2 = new MovingViolationFactory();

        MovingViolation violation1 = (MovingViolation) factory1.createViolation();
        violation1.setViolationType(MovingViolationEnum.SPEEDING);

        MovingViolation violation2 = (MovingViolation) factory2.createViolation();

        // violation2 should not be affected by violation1's configuration
        assertNull(violation2.getViolationType());
    }

    /**
     * Tests that factory doesn't maintain references to created objects.
     */
    @Test
    void testFactoryDoesNotMaintainReferences() {
        MovingViolation violation1 = (MovingViolation) factory.createViolation();
        violation1.setViolationType(MovingViolationEnum.SPEEDING);

        MovingViolation violation2 = (MovingViolation) factory.createViolation();

        // violation2 should be independent
        assertNull(violation2.getViolationType());
        assertEquals(MovingViolationEnum.SPEEDING, violation1.getViolationType());
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
        assertInstanceOf(MovingViolation.class, incident);
    }

    /**
     * Tests that factory implements all required abstract methods.
     */
    @Test
    void testFactoryImplementsAllAbstractMethods() {
        // If MovingViolationFactory can be instantiated, all abstract methods are implemented
        assertDoesNotThrow(() -> new MovingViolationFactory());
    }

    // ============ Comparison with Other Factory Types ============

    /**
     * Tests that MovingViolationFactory creates different type than CrashFactory.
     */
    @Test
    void testMovingViolationFactoryVsCrashFactory() {
        MovingViolationFactory movingFactory = new MovingViolationFactory();
        CrashFactory crashFactory = new CrashFactory();

        Incident movingIncident = movingFactory.createViolation();
        Incident crashIncident = crashFactory.createViolation();

        assertInstanceOf(MovingViolation.class, movingIncident);
        assertInstanceOf(Crash.class, crashIncident);
        assertNotEquals(movingIncident.getClass(), crashIncident.getClass());
        assertNotEquals(movingIncident.getIncidentCategory(), crashIncident.getIncidentCategory());
    }

    /**
     * Tests heterogeneous factory array with different factory types.
     */
    @Test
    void testHeterogeneousFactoryArray() {
        IncidentFactory[] factories = new IncidentFactory[]{
                new MovingViolationFactory(),
                new CrashFactory()
        };

        Incident violation = factories[0].createViolation();
        Incident crash = factories[1].createViolation();

        assertInstanceOf(MovingViolation.class, violation);
        assertInstanceOf(Crash.class, crash);
    }
}