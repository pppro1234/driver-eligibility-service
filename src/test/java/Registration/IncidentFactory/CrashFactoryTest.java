package Registration.IncidentFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests crash creation and configuration through the factory.
 */
class CrashFactoryTest {

    private CrashFactory factory;

    /**
     * Sets up test fixtures before each test method.
     * Creates a new CrashFactory instance for testing.
     */
    @BeforeEach
    void setUp() {
        factory = new CrashFactory();
    }

    // ============ Constructor Tests ============

    /**
     * Tests that CrashFactory can be instantiated.
     * Verifies factory construction works correctly.
     */
    @Test
    void testFactoryInstantiation() {
        CrashFactory factory = new CrashFactory();

        assertNotNull(factory);
    }

    /**
     * Tests that CrashFactory is an instance of IncidentFactory.
     * Verifies proper inheritance hierarchy.
     */
    @Test
    void testFactoryExtendsIncidentFactory() {
        assertInstanceOf(IncidentFactory.class, factory);
    }

    // ============ createViolation() Tests ============

    /**
     * Tests that createViolation() returns a non-null incident.
     * Verifies basic factory method functionality.
     */
    @Test
    void testCreateViolationReturnsNonNull() {
        Incident incident = factory.createViolation();

        assertNotNull(incident);
    }

    /**
     * Tests that createViolation() returns a Crash instance.
     * Verifies factory creates the correct type of incident.
     */
    @Test
    void testCreateViolationReturnsCrash() {
        Incident incident = factory.createViolation();

        assertInstanceOf(Crash.class, incident);
    }

    /**
     * Tests that created crash is also an Incident.
     * Verifies polymorphic behavior.
     */
    @Test
    void testCreatedCrashIsIncident() {
        Incident incident = factory.createViolation();

        assertInstanceOf(Incident.class, incident);
    }

    /**
     * Tests that created crash has null crash type.
     * Verifies factory creates crash in default state.
     */
    @Test
    void testCreatedCrashHasNullCrashType() {
        Incident incident = factory.createViolation();
        Crash crash = (Crash) incident;

        assertNull(crash.getCrashType());
    }

    /**
     * Tests that created crash has correct incident category.
     * Verifies crash category is set correctly even with null crash type.
     */
    @Test
    void testCreatedCrashHasCorrectCategory() {
        Incident incident = factory.createViolation();

        assertEquals(IncidentType.CRASH, incident.getIncidentCategory());
    }

    /**
     * Tests that getIncidentType() throws exception on newly created crash.
     * Verifies crash is in uninitialized state requiring configuration.
     */
    @Test
    void testCreatedCrashGetIncidentTypeThrows() {
        Incident incident = factory.createViolation();

        assertThrows(NullPointerException.class, () -> {
            incident.getIncidentType();
        });
    }

    // ============ Multiple Creation Tests ============

    /**
     * Tests that createViolation() can be called multiple times.
     * Verifies factory can be reused.
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
     * Verifies factory creates distinct objects.
     */
    @Test
    void testCreateViolationCreatesNewInstances() {
        Incident incident1 = factory.createViolation();
        Incident incident2 = factory.createViolation();

        assertNotSame(incident1, incident2);
    }

    /**
     * Tests that multiple created crashes are independent.
     * Verifies no shared state between created objects.
     */
    @Test
    void testMultipleCreatedCrashesAreIndependent() {
        Crash crash1 = (Crash) factory.createViolation();
        Crash crash2 = (Crash) factory.createViolation();

        crash1.setCrashType(CrashEnum.FENDER_BENDER);

        assertNull(crash2.getCrashType());
    }

    // ============ Polymorphic Usage Tests ============

    /**
     * Tests using CrashFactory through IncidentFactory reference.
     * Verifies polymorphic factory usage.
     */
    @Test
    void testPolymorphicFactoryUsage() {
        IncidentFactory incidentFactory = new CrashFactory();
        Incident incident = incidentFactory.createViolation();

        assertNotNull(incident);
        assertInstanceOf(Crash.class, incident);
    }

    /**
     * Tests that factory method returns Incident type polymorphically.
     * Verifies return type compatibility.
     */
    @Test
    void testFactoryMethodReturnsIncidentType() {
        IncidentFactory incidentFactory = factory;
        Incident incident = incidentFactory.createViolation();

        assertInstanceOf(Incident.class, incident);
        assertEquals(IncidentType.CRASH, incident.getIncidentCategory());
    }

    // ============ Configuration After Creation Tests ============

    /**
     * Tests configuring crash after creation with FENDER_BENDER.
     * Verifies the incident type after configuration.
     */
    @Test
    void testConfigureCrashAfterCreationWithFenderBender() {
        Incident incident = factory.createViolation();
        Crash crash = (Crash) incident;

        crash.setCrashType(CrashEnum.FENDER_BENDER);

        assertEquals(CrashEnum.FENDER_BENDER, crash.getCrashType());
        assertEquals("Fender bender", crash.getIncidentType());
    }

    /**
     * Tests configuring crash after creation with NO_INJURY_CRASH.
     * Verifies the incident type after configuration.
     */
    @Test
    void testConfigureCrashAfterCreationWithNoInjuryCrash() {
        Incident incident = factory.createViolation();
        Crash crash = (Crash) incident;

        crash.setCrashType(CrashEnum.NO_INJURY_CRASH);

        assertEquals(CrashEnum.NO_INJURY_CRASH, crash.getCrashType());
        assertEquals("No Injury Crash", crash.getIncidentType());
    }

    /**
     * Tests configuring crash after creation with INJURY_CRASH.
     * Verifies the incident type after configuration.
     */
    @Test
    void testConfigureCrashAfterCreationWithInjuryCrash() {
        Incident incident = factory.createViolation();
        Crash crash = (Crash) incident;

        crash.setCrashType(CrashEnum.INJURY_CRASH);

        assertEquals(CrashEnum.INJURY_CRASH, crash.getCrashType());
        assertEquals("Injury Crash", crash.getIncidentType());
    }

    /**
     * Tests factory creation followed by crash configuration.
     */
    @Test
    void testCompleteFactoryWorkflow() {
        // Create
        Incident incident = factory.createViolation();

        // Verify initial state
        assertInstanceOf(Crash.class, incident);
        assertEquals(IncidentType.CRASH, incident.getIncidentCategory());

        // Configure
        Crash crash = (Crash) incident;
        crash.setCrashType(CrashEnum.FENDER_BENDER);

        // Use
        assertEquals("Fender bender", incident.getIncidentType());
        assertEquals(IncidentType.CRASH, incident.getIncidentCategory());
    }

    // ============ Factory Selection Tests ============

    /**
     * Tests factory in array of factories.
     * Verifies factory can be used in collections.
     */
    @Test
    void testFactoryInArray() {
        IncidentFactory[] factories = new IncidentFactory[]{
                new CrashFactory(),
                new CrashFactory()
        };

        for (IncidentFactory f : factories) {
            Incident incident = f.createViolation();
            assertInstanceOf(Crash.class, incident);
        }
    }

    /**
     * Tests selecting a factory by incident type.
     */
    @Test
    void testFactorySelectionPattern() {
        IncidentFactory selectedFactory = selectFactory(IncidentType.CRASH);
        Incident incident = selectedFactory.createViolation();

        assertNotNull(incident);
        assertInstanceOf(Crash.class, incident);
        assertEquals(IncidentType.CRASH, incident.getIncidentCategory());
    }

    /**
     * Helper method simulating factory selection logic.
     */
    private IncidentFactory selectFactory(IncidentType category) {
        if (category == IncidentType.CRASH) {
            return new CrashFactory();
        }
        throw new IllegalArgumentException("Unsupported category");
    }

    /**
     * Tests batch creation of crashes.
     * Verifies factory handles multiple creation requests.
     */
    @Test
    void testBatchCrashCreation() {
        int batchSize = 5;
        Crash[] crashes = new Crash[batchSize];

        for (int i = 0; i < batchSize; i++) {
            crashes[i] = (Crash) factory.createViolation();
        }

        // Verify all crashes were created
        for (Crash crash : crashes) {
            assertNotNull(crash);
            assertNull(crash.getCrashType());
            assertEquals(IncidentType.CRASH, crash.getIncidentCategory());
        }
    }

    // ============ Type Safety Tests ============

    /**
     * Tests that factory method return type is Incident.
     * Verifies compile-time type safety.
     */
    @Test
    void testFactoryMethodReturnType() {
        Incident incident = factory.createViolation();

        // Should compile without cast when assigned to Incident
        assertNotNull(incident);
    }

    /**
     * Tests safe downcasting to Crash.
     * Verifies type checking before casting.
     */
    @Test
    void testSafeDowncasting() {
        Incident incident = factory.createViolation();

        if (incident instanceof Crash) {
            Crash crash = (Crash) incident;
            assertNotNull(crash);
        } else {
            fail("Created incident should be a Crash");
        }
    }

    // ============ Consistency Tests ============

    /**
     * Tests that all created crashes have consistent category.
     * Verifies factory always creates crashes with CRASH category.
     */
    @Test
    void testAllCreatedCrashesHaveConsistentCategory() {
        for (int i = 0; i < 10; i++) {
            Incident incident = factory.createViolation();
            assertEquals(IncidentType.CRASH, incident.getIncidentCategory());
        }
    }

    /**
     * Tests that factory behavior is consistent across multiple instances.
     * Verifies different factory instances behave identically.
     */
    @Test
    void testMultipleFactoryInstancesConsistency() {
        CrashFactory factory1 = new CrashFactory();
        CrashFactory factory2 = new CrashFactory();

        Incident incident1 = factory1.createViolation();
        Incident incident2 = factory2.createViolation();

        assertEquals(incident1.getClass(), incident2.getClass());
        assertEquals(incident1.getIncidentCategory(), incident2.getIncidentCategory());
    }

    // ============ Exception Safety Tests ============

    /**
     * Tests that createViolation() doesn't throw exceptions.
     * Verifies basic exception safety.
     */
    @Test
    void testCreateViolationDoesNotThrow() {
        assertDoesNotThrow(() -> factory.createViolation());
    }

    /**
     * Tests that factory can be called repeatedly without issues.
     * Verifies robustness under repeated use.
     */
    @Test
    void testRepeatedCallsDoNotCauseIssues() {
        assertDoesNotThrow(() -> {
            for (int i = 0; i < 100; i++) {
                factory.createViolation();
            }
        });
    }

    // ============ Integration with Crash Tests ============

    /**
     * Tests that created crash works with all CrashEnum values.
     * Verifies factory creates crashes compatible with all crash types.
     */
    @Test
    void testCreatedCrashWorksWithAllCrashTypes() {
        for (CrashEnum crashType : CrashEnum.values()) {
            Crash crash = (Crash) factory.createViolation();
            crash.setCrashType(crashType);

            assertEquals(crashType, crash.getCrashType());
            assertNotNull(crash.getIncidentType());
        }
    }

    /**
     * Tests factory-created crash in incident processing workflow.
     * Verifies realistic end-to-end usage scenario.
     */
    @Test
    void testFactoryInIncidentProcessingWorkflow() {
        // Factory selection
        IncidentFactory factory = new CrashFactory();

        // Incident creation
        Incident incident = factory.createViolation();

        // Type checking
        assertTrue(incident instanceof Crash);

        // Configuration
        Crash crash = (Crash) incident;
        crash.setCrashType(CrashEnum.NO_INJURY_CRASH);

        // Validation usage
        assertEquals(IncidentType.CRASH, incident.getIncidentCategory());
        assertEquals("No Injury Crash", incident.getIncidentType());
    }

    /**
     * Tests creating crashes for different severity levels.
     * Verifies factory supports full range of crash types.
     */
    @Test
    void testCreateCrashesForDifferentSeverities() {
        // Create minor crash
        Crash minor = (Crash) factory.createViolation();
        minor.setCrashType(CrashEnum.FENDER_BENDER);
        assertEquals("Fender bender", minor.getIncidentType());

        // Create moderate crash
        Crash moderate = (Crash) factory.createViolation();
        moderate.setCrashType(CrashEnum.NO_INJURY_CRASH);
        assertEquals("No Injury Crash", moderate.getIncidentType());

        // Create severe crash
        Crash severe = (Crash) factory.createViolation();
        severe.setCrashType(CrashEnum.INJURY_CRASH);
        assertEquals("Injury Crash", severe.getIncidentType());
    }

    // ============ State Independence Tests ============

    /**
     * Tests that factory instances are stateless.
     * Verifies factory has no shared state.
     */
    @Test
    void testFactoryIsStateless() {
        CrashFactory factory1 = new CrashFactory();
        CrashFactory factory2 = new CrashFactory();

        Crash crash1 = (Crash) factory1.createViolation();
        crash1.setCrashType(CrashEnum.FENDER_BENDER);

        Crash crash2 = (Crash) factory2.createViolation();

        // crash2 should not be affected by crash1's configuration
        assertNull(crash2.getCrashType());
    }

    /**
     * Tests that factory doesn't maintain references to created objects.
     * Verifies factory doesn't track created instances.
     */
    @Test
    void testFactoryDoesNotMaintainReferences() {
        Crash crash1 = (Crash) factory.createViolation();
        crash1.setCrashType(CrashEnum.FENDER_BENDER);

        Crash crash2 = (Crash) factory.createViolation();

        // crash2 should be independent
        assertNull(crash2.getCrashType());
        assertEquals(CrashEnum.FENDER_BENDER, crash1.getCrashType());
    }

    // ============ Method Override Tests ============

    /**
     * Tests that createViolation() properly overrides abstract method.
     * Verifies method signature and behavior match contract.
     */
    @Test
    void testCreateViolationOverridesAbstractMethod() {
        // If this compiles and runs, the override is correct
        IncidentFactory abstractFactory = factory;
        Incident incident = abstractFactory.createViolation();

        assertNotNull(incident);
        assertInstanceOf(Crash.class, incident);
    }

    /**
     * Tests that factory implements all required abstract methods.
     * Verifies complete implementation of abstract contract.
     */
    @Test
    void testFactoryImplementsAllAbstractMethods() {
        // If CrashFactory can be instantiated, all abstract methods are implemented
        assertDoesNotThrow(() -> new CrashFactory());
    }
}