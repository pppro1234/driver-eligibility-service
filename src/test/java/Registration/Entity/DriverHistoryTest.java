package Registration.Entity;

import Registration.IncidentFactory.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests driver history data and incident handling.
 */
class DriverHistoryTest {

    private Date testDate;
    private Date alternativeDate;
    private Incident movingViolation;
    private Incident nonMovingViolation;
    private Incident crashIncident;

    /**
     * Sets up test fixtures before each test method.
     */
    @BeforeEach
    void setUp() {
        testDate = new Date.Builder(2023, 6, 15).build();
        alternativeDate = new Date.Builder(2022, 3, 10).build();

        movingViolation = new MovingViolation(MovingViolationEnum.SPEEDING);
        nonMovingViolation = new NonMovingViolation(NonMovingViolationEnum.PARKING_VIOLATION);
        crashIncident = new Crash(CrashEnum.FENDER_BENDER);
    }

    // ============ Constructor Tests ============

    /**
     * Tests constructor with moving violation.
     */
    @Test
    void testConstructorWithMovingViolation() {
        DriverHistory history = new DriverHistory(testDate, movingViolation);

        assertNotNull(history);
        assertEquals(testDate, history.getViolationDate());
        assertEquals("Speeding", history.getViolationType());
    }

    /**
     * Tests constructor with non-moving violation.
     */
    @Test
    void testConstructorWithNonMovingViolation() {
        DriverHistory history = new DriverHistory(testDate, nonMovingViolation);

        assertNotNull(history);
        assertEquals(testDate, history.getViolationDate());
        assertEquals("Parking Violation", history.getViolationType());
    }

    /**
     * Tests constructor with crash incident.
     */
    @Test
    void testConstructorWithCrash() {
        DriverHistory history = new DriverHistory(testDate, crashIncident);

        assertNotNull(history);
        assertEquals(testDate, history.getViolationDate());
        assertEquals("Fender bender", history.getViolationType());
    }

    /**
     * Tests constructor with null date.
     */
    @Test
    void testConstructorWithNullDate() {
        DriverHistory history = new DriverHistory(null, movingViolation);

        assertNotNull(history);
        assertNull(history.getViolationDate());
        assertEquals("Speeding", history.getViolationType());
    }

    /**
     * Tests constructor with null incident.
     */
    @Test
    void testConstructorWithNullIncident() {
        DriverHistory history = new DriverHistory(testDate, null);

        assertNotNull(history);
        assertEquals(testDate, history.getViolationDate());
    }

    /**
     * Tests constructor with both parameters null.
     */
    @Test
    void testConstructorWithBothParametersNull() {
        DriverHistory history = new DriverHistory(null, null);

        assertNotNull(history);
        assertNull(history.getViolationDate());
    }

    // ============ Getter Tests ============

    /**
     * Tests getViolationDate() returns correct value.
     */
    @Test
    void testGetViolationDate() {
        DriverHistory history = new DriverHistory(testDate, movingViolation);

        assertEquals(testDate, history.getViolationDate());
    }

    /**
     * Tests getViolationDate() returns null when not set.
     */
    @Test
    void testGetViolationDateWhenNull() {
        DriverHistory history = new DriverHistory(null, movingViolation);

        assertNull(history.getViolationDate());
    }

    /**
     * Tests getViolationType() returns incident description for moving violation.
     */
    @Test
    void testGetViolationTypeForMovingViolation() {
        DriverHistory history = new DriverHistory(testDate, movingViolation);

        assertEquals("Speeding", history.getViolationType());
    }

    /**
     * Tests getViolationType() returns incident description for non-moving violation.
     */
    @Test
    void testGetViolationTypeForNonMovingViolation() {
        DriverHistory history = new DriverHistory(testDate, nonMovingViolation);

        assertEquals("Parking Violation", history.getViolationType());
    }

    /**
     * Tests getViolationType() returns incident description for crash.
     */
    @Test
    void testGetViolationTypeForCrash() {
        DriverHistory history = new DriverHistory(testDate, crashIncident);

        assertEquals("Fender bender", history.getViolationType());
    }

    /**
     * Tests getViolationType() throws NullPointerException when incident is null.
     */
    @Test
    void testGetViolationTypeThrowsWhenIncidentIsNull() {
        DriverHistory history = new DriverHistory(testDate, null);

        assertThrows(NullPointerException.class, () -> {
            history.getViolationType();
        });
    }

    /**
     * Tests getViolationType() for all moving violation types.
     */
    @Test
    void testGetViolationTypeForAllMovingViolationTypes() {
        for (MovingViolationEnum type : MovingViolationEnum.values()) {
            Incident incident = new MovingViolation(type);
            DriverHistory history = new DriverHistory(testDate, incident);

            assertNotNull(history.getViolationType());
            assertEquals(type.toString(), history.getViolationType());
        }
    }

    /**
     * Tests getViolationType() for all crash types.
     */
    @Test
    void testGetViolationTypeForAllCrashTypes() {
        for (CrashEnum type : CrashEnum.values()) {
            Incident incident = new Crash(type);
            DriverHistory history = new DriverHistory(testDate, incident);

            assertNotNull(history.getViolationType());
            assertEquals(type.toString(), history.getViolationType());
        }
    }

    /**
     * Tests getViolationType() for all non-moving violation types.
     */
    @Test
    void testGetViolationTypeForAllNonMovingViolationTypes() {
        for (NonMovingViolationEnum type : NonMovingViolationEnum.values()) {
            Incident incident = new NonMovingViolation(type);
            DriverHistory history = new DriverHistory(testDate, incident);

            assertNotNull(history.getViolationType());
            assertEquals(type.toString(), history.getViolationType());
        }
    }

    // ============ Setter Tests ============

    /**
     * Tests setViolationDate() updates the value.
     */
    @Test
    void testSetViolationDate() {
        DriverHistory history = new DriverHistory(testDate, movingViolation);

        history.setViolationDate(alternativeDate);

        assertEquals(alternativeDate, history.getViolationDate());
    }

    /**
     * Tests setViolationDate() with null value.
     */
    @Test
    void testSetViolationDateToNull() {
        DriverHistory history = new DriverHistory(testDate, movingViolation);

        history.setViolationDate(null);

        assertNull(history.getViolationDate());
    }

    /**
     * Tests setViolationType() with moving violation.
     */
    @Test
    void testSetViolationTypeWithMovingViolation() {
        DriverHistory history = new DriverHistory(testDate, crashIncident);

        history.setViolationType(movingViolation);

        assertEquals("Speeding", history.getViolationType());
    }

    /**
     * Tests setViolationType() with non-moving violation.
     */
    @Test
    void testSetViolationTypeWithNonMovingViolation() {
        DriverHistory history = new DriverHistory(testDate, movingViolation);

        history.setViolationType(nonMovingViolation);

        assertEquals("Parking Violation", history.getViolationType());
    }

    /**
     * Tests setViolationType() with crash.
     */
    @Test
    void testSetViolationTypeWithCrash() {
        DriverHistory history = new DriverHistory(testDate, movingViolation);

        history.setViolationType(crashIncident);

        assertEquals("Fender bender", history.getViolationType());
    }

    /**
     * Tests setViolationType() with null incident.
     */
    @Test
    void testSetViolationTypeToNull() {
        DriverHistory history = new DriverHistory(testDate, movingViolation);

        history.setViolationType(null);

        // getViolationType() should now throw
        assertThrows(NullPointerException.class, () -> {
            history.getViolationType();
        });
    }

    /**
     * Tests setViolationType() overwrites existing incident.
     */
    @Test
    void testSetViolationTypeOverwritesExisting() {
        DriverHistory history = new DriverHistory(testDate, movingViolation);
        assertEquals("Speeding", history.getViolationType());

        Incident duiIncident = new MovingViolation(MovingViolationEnum.DUI);
        history.setViolationType(duiIncident);

        assertEquals("Driving Under Influence", history.getViolationType());
    }

    // ============ Integration Tests ============

    /**
     * Tests complete lifecycle: create, access, modify.
     */
    @Test
    void testCompleteLifecycle() {
        // Create
        DriverHistory history = new DriverHistory(testDate, movingViolation);

        // Access
        assertEquals(testDate, history.getViolationDate());
        assertEquals("Speeding", history.getViolationType());

        // Modify date
        history.setViolationDate(alternativeDate);
        assertEquals(alternativeDate, history.getViolationDate());

        // Modify incident
        history.setViolationType(crashIncident);
        assertEquals("Fender bender", history.getViolationType());
    }

    /**
     * Tests changing between different incident categories.
     */
    @Test
    void testChangingBetweenIncidentCategories() {
        DriverHistory history = new DriverHistory(testDate, movingViolation);

        // Start with moving violation
        assertEquals("Speeding", history.getViolationType());

        // Change to crash
        history.setViolationType(crashIncident);
        assertEquals("Fender bender", history.getViolationType());

        // Change to non-moving violation
        history.setViolationType(nonMovingViolation);
        assertEquals("Parking Violation", history.getViolationType());
    }

    /**
     * Tests that getters return same references (no defensive copying).
     */
    @Test
    void testGettersReturnSameReferences() {
        DriverHistory history = new DriverHistory(testDate, movingViolation);

        assertSame(testDate, history.getViolationDate());
    }

    /**
     * Tests creating multiple history records with different data.
     */
    @Test
    void testMultipleHistoryRecordsWithDifferentData() {
        DriverHistory history1 = new DriverHistory(
                new Date.Builder(2023, 1, 1).build(),
                new MovingViolation(MovingViolationEnum.SPEEDING)
        );

        DriverHistory history2 = new DriverHistory(
                new Date.Builder(2023, 6, 15).build(),
                new Crash(CrashEnum.NO_INJURY_CRASH)
        );

        DriverHistory history3 = new DriverHistory(
                new Date.Builder(2023, 12, 31).build(),
                new NonMovingViolation(NonMovingViolationEnum.VEHICLE_PROBLEM)
        );

        // All should be distinct
        assertNotSame(history1, history2);
        assertNotSame(history2, history3);

        // All should have different violation types
        assertEquals("Speeding", history1.getViolationType());
        assertEquals("No Injury Crash", history2.getViolationType());
        assertEquals("Vehicle Problem", history3.getViolationType());
    }

    /**
     * Tests realistic driver history scenario.
     */
    @Test
    void testRealisticDriverHistoryScenario() {
        // Recent speeding violation
        Date recentDate = new Date.Builder(2024, 10, 1).build();
        Incident speeding = new MovingViolation(MovingViolationEnum.SPEEDING);
        DriverHistory recentViolation = new DriverHistory(recentDate, speeding);

        assertEquals(2024, recentViolation.getViolationDate().getYear());
        assertEquals(10, recentViolation.getViolationDate().getMonth());
        assertEquals("Speeding", recentViolation.getViolationType());
    }

    /**
     * Tests serious violation (DUI).
     */
    @Test
    void testSeriousViolation() {
        Incident dui = new MovingViolation(MovingViolationEnum.DUI);
        DriverHistory history = new DriverHistory(testDate, dui);

        assertEquals("Driving Under Influence", history.getViolationType());
    }

    /**
     * Tests crash with bodily injuries.
     */
    @Test
    void testCrashWithBodilyInjuries() {
        Incident injuryCrash = new Crash(CrashEnum.INJURY_CRASH);
        DriverHistory history = new DriverHistory(testDate, injuryCrash);

        assertEquals("Injury Crash", history.getViolationType());
    }

    /**
     * Tests getter and setter consistency for date.
     */
    @Test
    void testGetterSetterConsistencyForDate() {
        DriverHistory history = new DriverHistory(testDate, movingViolation);

        assertEquals(testDate, history.getViolationDate());

        history.setViolationDate(alternativeDate);
        assertEquals(alternativeDate, history.getViolationDate());
    }

    /**
     * Tests getter and setter consistency for incident.
     */
    @Test
    void testGetterSetterConsistencyForIncident() {
        DriverHistory history = new DriverHistory(testDate, movingViolation);

        assertEquals("Speeding", history.getViolationType());

        history.setViolationType(crashIncident);
        assertEquals("Fender bender", history.getViolationType());
    }

    /**
     * Tests updating both fields via setters.
     */
    @Test
    void testUpdatingBothFieldsViaSetters() {
        DriverHistory history = new DriverHistory(testDate, movingViolation);

        history.setViolationDate(alternativeDate);
        history.setViolationType(crashIncident);

        assertEquals(alternativeDate, history.getViolationDate());
        assertEquals("Fender bender", history.getViolationType());
    }

    /**
     * Tests that changing incident type changes returned violation type.
     */
    @Test
    void testChangingIncidentTypeChangesViolationType() {
        DriverHistory history = new DriverHistory(testDate, movingViolation);
        assertEquals("Speeding", history.getViolationType());

        Incident reckless = new MovingViolation(MovingViolationEnum.RECKLESS_DRIVING);
        history.setViolationType(reckless);
        assertEquals("Reckless Driving", history.getViolationType());

        Incident paperwork = new NonMovingViolation(NonMovingViolationEnum.PAPERWORK_ISSUE);
        history.setViolationType(paperwork);
        assertEquals("Paper Work Issue", history.getViolationType());
    }

    /**
     * Tests driver history with various incident severities.
     */
    @Test
    void testDriverHistoryWithVariousSeverities() {
        // Minor violation
        Incident minor = new NonMovingViolation(NonMovingViolationEnum.PARKING_VIOLATION);
        DriverHistory minorHistory = new DriverHistory(testDate, minor);
        assertEquals("Parking Violation", minorHistory.getViolationType());

        // Moderate violation
        Incident moderate = new MovingViolation(MovingViolationEnum.SPEEDING);
        DriverHistory moderateHistory = new DriverHistory(testDate, moderate);
        assertEquals("Speeding", moderateHistory.getViolationType());

        // Serious violation
        Incident serious = new MovingViolation(MovingViolationEnum.DUI);
        DriverHistory seriousHistory = new DriverHistory(testDate, serious);
        assertEquals("Driving Under Influence", seriousHistory.getViolationType());

        // Critical incident
        Incident critical = new Crash(CrashEnum.INJURY_CRASH);
        DriverHistory criticalHistory = new DriverHistory(testDate, critical);
        assertEquals("Injury Crash", criticalHistory.getViolationType());
    }

    /**
     * Tests creating history for incidents at different times.
     */
    @Test
    void testHistoryAtDifferentTimes() {
        Date oldDate = new Date.Builder(2020, 1, 1).build();
        Date recentDate = new Date.Builder(2024, 11, 30).build();

        DriverHistory oldHistory = new DriverHistory(oldDate, movingViolation);
        DriverHistory recentHistory = new DriverHistory(recentDate, crashIncident);

        assertEquals(2020, oldHistory.getViolationDate().getYear());
        assertEquals(2024, recentHistory.getViolationDate().getYear());
    }

    /**
     * Tests that date remains unchanged when incident is modified.
     */
    @Test
    void testDateUnchangedWhenIncidentModified() {
        DriverHistory history = new DriverHistory(testDate, movingViolation);

        history.setViolationType(crashIncident);

        assertEquals(testDate, history.getViolationDate());
    }

    /**
     * Tests that incident type changes when incident is modified.
     */
    @Test
    void testIncidentTypeChangesWhenIncidentModified() {
        DriverHistory history = new DriverHistory(testDate, movingViolation);
        assertEquals("Speeding", history.getViolationType());

        history.setViolationType(nonMovingViolation);
        assertEquals("Parking Violation", history.getViolationType());
    }

    /**
     * Tests getter returns current state after modifications.
     */
    @Test
    void testGetterReturnsCurrentStateAfterModifications() {
        DriverHistory history = new DriverHistory(testDate, movingViolation);

        // Initial state
        assertEquals("Speeding", history.getViolationType());

        // Modify
        Incident newIncident = new MovingViolation(MovingViolationEnum.DUI);
        history.setViolationType(newIncident);

        // Should reflect new state
        assertEquals("Driving Under Influence", history.getViolationType());
    }
}