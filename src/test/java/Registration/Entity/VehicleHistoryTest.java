package Registration.Entity;

import Registration.IncidentFactory.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests vehicle history data and paired incident lists.
 */
class VehicleHistoryTest {

    private List<Date> testDates;
    private List<Incident> testIncidents;
    private List<Name> testDrivers;

    /**
     * Sets up test fixtures before each test method.
     * Creates sample data for dates, incidents, and drivers.
     */
    @BeforeEach
    void setUp() {
        // Create test dates
        testDates = new ArrayList<>();
        testDates.add(new Date.Builder(2023, 1, 15).build());
        testDates.add(new Date.Builder(2023, 6, 20).build());
        testDates.add(new Date.Builder(2023, 9, 10).build());

        // Create test incidents
        testIncidents = new ArrayList<>();
        testIncidents.add(new MovingViolation(MovingViolationEnum.SPEEDING));
        testIncidents.add(new Crash(CrashEnum.FENDER_BENDER));
        testIncidents.add(new NonMovingViolation(NonMovingViolationEnum.PARKING_VIOLATION));

        // Create test drivers
        testDrivers = new ArrayList<>();
        testDrivers.add(new Name("John", "Doe"));
        testDrivers.add(new Name("Jane", "Smith"));
        testDrivers.add(new Name("Bob", "Johnson"));
    }

    // ============ Constructor Tests ============

    /**
     * Tests constructor with all valid lists.
     */
    @Test
    void testConstructorWithValidLists() {
        VehicleHistory history = new VehicleHistory(testDates, testIncidents, testDrivers);

        assertNotNull(history);
        assertEquals(testDates, history.getIncidentDates());
        assertEquals(testIncidents, history.getIncidents());
        assertEquals(testDrivers, history.getOffendingDrivers());
    }

    /**
     * Tests constructor with empty lists.
     */
    @Test
    void testConstructorWithEmptyLists() {
        List<Date> emptyDates = new ArrayList<>();
        List<Incident> emptyIncidents = new ArrayList<>();
        List<Name> emptyDrivers = new ArrayList<>();

        VehicleHistory history = new VehicleHistory(emptyDates, emptyIncidents, emptyDrivers);

        assertNotNull(history);
        assertTrue(history.getIncidentDates().isEmpty());
        assertTrue(history.getIncidents().isEmpty());
        assertTrue(history.getOffendingDrivers().isEmpty());
    }

    /**
     * Tests constructor with null lists.
     */
    @Test
    void testConstructorWithNullLists() {
        VehicleHistory history = new VehicleHistory(null, null, null);

        assertNotNull(history);
        assertNull(history.getIncidentDates());
        assertNull(history.getIncidents());
        assertNull(history.getOffendingDrivers());
    }

    /**
     * Tests constructor with single element lists.
     */
    @Test
    void testConstructorWithSingleElementLists() {
        List<Date> singleDate = Collections.singletonList(testDates.get(0));
        List<Incident> singleIncident = Collections.singletonList(testIncidents.get(0));
        List<Name> singleDriver = Collections.singletonList(testDrivers.get(0));

        VehicleHistory history = new VehicleHistory(singleDate, singleIncident, singleDriver);

        assertEquals(1, history.getIncidentDates().size());
        assertEquals(1, history.getIncidents().size());
        assertEquals(1, history.getOffendingDrivers().size());
    }

    /**
     * Tests constructor with mismatched list sizes (no validation enforced).
     */
    @Test
    void testConstructorWithMismatchedListSizes() {
        List<Date> dates = Arrays.asList(testDates.get(0));
        List<Incident> incidents = Arrays.asList(testIncidents.get(0), testIncidents.get(1));
        List<Name> drivers = Arrays.asList(testDrivers.get(0), testDrivers.get(1), testDrivers.get(2));

        VehicleHistory history = new VehicleHistory(dates, incidents, drivers);

        // Constructor accepts mismatched sizes
        assertNotNull(history);
        assertEquals(1, history.getIncidentDates().size());
        assertEquals(2, history.getIncidents().size());
        assertEquals(3, history.getOffendingDrivers().size());
    }

    // ============ Getter Tests ============

    /**
     * Tests getIncidentDates() returns correct list.
     */
    @Test
    void testGetIncidentDates() {
        VehicleHistory history = new VehicleHistory(testDates, testIncidents, testDrivers);

        List<Date> dates = history.getIncidentDates();

        assertNotNull(dates);
        assertEquals(3, dates.size());
        assertEquals(testDates, dates);
    }

    /**
     * Tests getIncidents() returns correct list.
     */
    @Test
    void testGetIncidents() {
        VehicleHistory history = new VehicleHistory(testDates, testIncidents, testDrivers);

        List<Incident> incidents = history.getIncidents();

        assertNotNull(incidents);
        assertEquals(3, incidents.size());
        assertEquals(testIncidents, incidents);
    }

    /**
     * Tests getOffendingDrivers() returns correct list.
     */
    @Test
    void testGetOffendingDrivers() {
        VehicleHistory history = new VehicleHistory(testDates, testIncidents, testDrivers);

        List<Name> drivers = history.getOffendingDrivers();

        assertNotNull(drivers);
        assertEquals(3, drivers.size());
        assertEquals(testDrivers, drivers);
    }

    /**
     * Tests getters return the same references passed to constructor.
     */
    @Test
    void testGettersReturnSameReferences() {
        VehicleHistory history = new VehicleHistory(testDates, testIncidents, testDrivers);

        assertSame(testDates, history.getIncidentDates());
        assertSame(testIncidents, history.getIncidents());
        assertSame(testDrivers, history.getOffendingDrivers());
    }

    // ============ Setter Tests ============

    /**
     * Tests setIncidentDates() updates the list.
     */
    @Test
    void testSetIncidentDates() {
        VehicleHistory history = new VehicleHistory(testDates, testIncidents, testDrivers);

        List<Date> newDates = new ArrayList<>();
        newDates.add(new Date.Builder(2024, 1, 1).build());

        history.setIncidentDates(newDates);

        assertEquals(newDates, history.getIncidentDates());
        assertEquals(1, history.getIncidentDates().size());
    }

    /**
     * Tests setIncidents() updates the list.
     */
    @Test
    void testSetIncidents() {
        VehicleHistory history = new VehicleHistory(testDates, testIncidents, testDrivers);

        List<Incident> newIncidents = new ArrayList<>();
        newIncidents.add(new MovingViolation(MovingViolationEnum.DUI));

        history.setIncidents(newIncidents);

        assertEquals(newIncidents, history.getIncidents());
        assertEquals(1, history.getIncidents().size());
    }

    /**
     * Tests setOffendingDrivers() updates the list.
     */
    @Test
    void testSetOffendingDrivers() {
        VehicleHistory history = new VehicleHistory(testDates, testIncidents, testDrivers);

        List<Name> newDrivers = new ArrayList<>();
        newDrivers.add(new Name("Alice", "Williams"));

        history.setOffendingDrivers(newDrivers);

        assertEquals(newDrivers, history.getOffendingDrivers());
        assertEquals(1, history.getOffendingDrivers().size());
    }

    /**
     * Tests setters with null values.
     */
    @Test
    void testSettersWithNull() {
        VehicleHistory history = new VehicleHistory(testDates, testIncidents, testDrivers);

        history.setIncidentDates(null);
        history.setIncidents(null);
        history.setOffendingDrivers(null);

        assertNull(history.getIncidentDates());
        assertNull(history.getIncidents());
        assertNull(history.getOffendingDrivers());
    }

    /**
     * Tests setters with empty lists.
     */
    @Test
    void testSettersWithEmptyLists() {
        VehicleHistory history = new VehicleHistory(testDates, testIncidents, testDrivers);

        history.setIncidentDates(new ArrayList<>());
        history.setIncidents(new ArrayList<>());
        history.setOffendingDrivers(new ArrayList<>());

        assertTrue(history.getIncidentDates().isEmpty());
        assertTrue(history.getIncidents().isEmpty());
        assertTrue(history.getOffendingDrivers().isEmpty());
    }

    /**
     * Tests setters replace existing lists.
     */
    @Test
    void testSettersReplaceExistingLists() {
        VehicleHistory history = new VehicleHistory(testDates, testIncidents, testDrivers);

        List<Date> newDates = Arrays.asList(new Date.Builder(2024, 5, 15).build());
        List<Incident> newIncidents = Arrays.asList(new Crash(CrashEnum.INJURY_CRASH));
        List<Name> newDrivers = Arrays.asList(new Name("Charlie", "Brown"));

        history.setIncidentDates(newDates);
        history.setIncidents(newIncidents);
        history.setOffendingDrivers(newDrivers);

        assertSame(newDates, history.getIncidentDates());
        assertSame(newIncidents, history.getIncidents());
        assertSame(newDrivers, history.getOffendingDrivers());
    }

    // ============ Parallel List Tests ============

    /**
     * Tests accessing parallel lists by index.
     */
    @Test
    void testParallelListAccess() {
        VehicleHistory history = new VehicleHistory(testDates, testIncidents, testDrivers);

        // Access first incident
        Date date0 = history.getIncidentDates().get(0);
        Incident incident0 = history.getIncidents().get(0);
        Name driver0 = history.getOffendingDrivers().get(0);

        assertNotNull(date0);
        assertNotNull(incident0);
        assertNotNull(driver0);
        assertEquals(MovingViolationEnum.SPEEDING, ((MovingViolation) incident0).getViolationType());

        // Access second incident
        Date date1 = history.getIncidentDates().get(1);
        Incident incident1 = history.getIncidents().get(1);
        Name driver1 = history.getOffendingDrivers().get(1);

        assertNotNull(date1);
        assertNotNull(incident1);
        assertNotNull(driver1);
        assertEquals(CrashEnum.FENDER_BENDER, ((Crash) incident1).getCrashType());
    }

    /**
     * Tests iterating through parallel lists together.
     */
    @Test
    void testIteratingParallelLists() {
        VehicleHistory history = new VehicleHistory(testDates, testIncidents, testDrivers);

        for (int i = 0; i < history.getIncidents().size(); i++) {
            Date date = history.getIncidentDates().get(i);
            Incident incident = history.getIncidents().get(i);
            Name driver = history.getOffendingDrivers().get(i);

            assertNotNull(date);
            assertNotNull(incident);
            assertNotNull(driver);
        }
    }

    // ============ List Modification Tests ============

    /**
     * Tests modifying the returned list from getter affects the history.
     */
    @Test
    void testModifyingReturnedListAffectsHistory() {
        VehicleHistory history = new VehicleHistory(testDates, testIncidents, testDrivers);

        List<Date> dates = history.getIncidentDates();
        dates.add(new Date.Builder(2024, 12, 31).build());

        // Modification affects the history
        assertEquals(4, history.getIncidentDates().size());
    }

    /**
     * Tests that lists are mutable through getters.
     */
    @Test
    void testListsMutableThroughGetters() {
        VehicleHistory history = new VehicleHistory(testDates, testIncidents, testDrivers);

        history.getIncidentDates().clear();
        history.getIncidents().clear();
        history.getOffendingDrivers().clear();

        assertTrue(history.getIncidentDates().isEmpty());
        assertTrue(history.getIncidents().isEmpty());
        assertTrue(history.getOffendingDrivers().isEmpty());
    }

    // ============ Integration Tests ============

    /**
     * Tests complete lifecycle: create, access, modify.
     */
    @Test
    void testCompleteLifecycle() {
        // Create
        VehicleHistory history = new VehicleHistory(testDates, testIncidents, testDrivers);

        // Access
        assertEquals(3, history.getIncidents().size());

        // Modify through setter
        List<Date> newDates = Arrays.asList(new Date.Builder(2024, 1, 1).build());
        history.setIncidentDates(newDates);

        // Verify
        assertEquals(1, history.getIncidentDates().size());
        assertEquals(3, history.getIncidents().size()); // Others unchanged
    }

    /**
     * Tests with different incident types.
     */
    @Test
    void testWithDifferentIncidentTypes() {
        List<Incident> mixedIncidents = new ArrayList<>();
        mixedIncidents.add(new MovingViolation(MovingViolationEnum.SPEEDING));
        mixedIncidents.add(new MovingViolation(MovingViolationEnum.DUI));
        mixedIncidents.add(new NonMovingViolation(NonMovingViolationEnum.PARKING_VIOLATION));
        mixedIncidents.add(new NonMovingViolation(NonMovingViolationEnum.VEHICLE_PROBLEM));
        mixedIncidents.add(new Crash(CrashEnum.INJURY_CRASH));

        List<Date> dates = new ArrayList<>();
        List<Name> drivers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            dates.add(new Date.Builder(2023, i + 1, 15).build());
            drivers.add(new Name("Driver" + i, "Test"));
        }

        VehicleHistory history = new VehicleHistory(dates, mixedIncidents, drivers);

        assertEquals(5, history.getIncidents().size());
        assertEquals(5, history.getIncidentDates().size());
        assertEquals(5, history.getOffendingDrivers().size());
    }

    /**
     * Tests with unmodifiable lists.
     */
    @Test
    void testWithUnmodifiableLists() {
        List<Date> unmodifiableDates = Collections.unmodifiableList(testDates);
        List<Incident> unmodifiableIncidents = Collections.unmodifiableList(testIncidents);
        List<Name> unmodifiableDrivers = Collections.unmodifiableList(testDrivers);

        VehicleHistory history = new VehicleHistory(
                unmodifiableDates, unmodifiableIncidents, unmodifiableDrivers);

        assertNotNull(history);
        assertEquals(3, history.getIncidents().size());

        // Attempting to modify should throw exception
        assertThrows(UnsupportedOperationException.class, () -> {
            history.getIncidentDates().add(new Date.Builder(2024, 1, 1).build());
        });
    }

    /**
     * Tests building incrementally.
     */
    @Test
    void testBuildingIncrementally() {
        List<Date> dates = new ArrayList<>();
        List<Incident> incidents = new ArrayList<>();
        List<Name> drivers = new ArrayList<>();

        VehicleHistory history = new VehicleHistory(dates, incidents, drivers);

        // Add incidents incrementally
        dates.add(new Date.Builder(2023, 1, 1).build());
        incidents.add(new MovingViolation(MovingViolationEnum.SPEEDING));
        drivers.add(new Name("Test", "Driver"));

        assertEquals(1, history.getIncidents().size());

        dates.add(new Date.Builder(2023, 2, 1).build());
        incidents.add(new Crash(CrashEnum.FENDER_BENDER));
        drivers.add(new Name("Another", "Driver"));

        assertEquals(2, history.getIncidents().size());
    }

    /**
     * Tests replacing all lists at once.
     */
    @Test
    void testReplacingAllListsAtOnce() {
        VehicleHistory history = new VehicleHistory(testDates, testIncidents, testDrivers);

        List<Date> newDates = Arrays.asList(new Date.Builder(2024, 1, 1).build());
        List<Incident> newIncidents = Arrays.asList(new Crash(CrashEnum.INJURY_CRASH));
        List<Name> newDrivers = Arrays.asList(new Name("New", "Driver"));

        history.setIncidentDates(newDates);
        history.setIncidents(newIncidents);
        history.setOffendingDrivers(newDrivers);

        assertEquals(1, history.getIncidentDates().size());
        assertEquals(1, history.getIncidents().size());
        assertEquals(1, history.getOffendingDrivers().size());
    }

    /**
     * Tests with large number of incidents.
     */
    @Test
    void testWithLargeNumberOfIncidents() {
        List<Date> dates = new ArrayList<>();
        List<Incident> incidents = new ArrayList<>();
        List<Name> drivers = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            dates.add(new Date.Builder(2023, (i % 12) + 1, (i % 28) + 1).build());
            incidents.add(new MovingViolation(MovingViolationEnum.SPEEDING));
            drivers.add(new Name("Driver" + i, "Test"));
        }

        VehicleHistory history = new VehicleHistory(dates, incidents, drivers);

        assertEquals(100, history.getIncidents().size());
        assertEquals(100, history.getIncidentDates().size());
        assertEquals(100, history.getOffendingDrivers().size());
    }

    /**
     * Tests that getters always return current state.
     */
    @Test
    void testGettersReturnCurrentState() {
        VehicleHistory history = new VehicleHistory(testDates, testIncidents, testDrivers);

        assertEquals(3, history.getIncidents().size());

        history.setIncidents(new ArrayList<>());

        assertEquals(0, history.getIncidents().size());
    }
}