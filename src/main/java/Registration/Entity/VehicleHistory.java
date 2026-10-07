package Registration.Entity;

import Registration.IncidentFactory.Incident;

import java.util.List;

/**
 * Vehicle incidents represented by three parallel lists.
 *
 * <p>Elements at the same index describe one event: its date, incident, and driver.
 * Callers must maintain equal list lengths and matching positions; this class does
 * not validate alignment or make defensive copies. Getters expose the stored lists.
 * Instances and their lists are not thread-safe.</p>
 */
public class VehicleHistory {

    /** List of dates when incidents occurred, parallel to incidents and offendingDrivers. */
    private List<Date> incidentDates;

    /** List of incidents that occurred, parallel to incidentDates and offendingDrivers. */
    private List<Incident> incidents;

    /** List of drivers who caused the incidents, parallel to incidentDates and incidents. */
    private List<Name> offendingDrivers;

    /**
     * Stores the supplied parallel lists without validation or copying.
     *
     * @param incidentDates dates in the same order as incidents
     * @param incidents incidents in the same order as dates and drivers
     * @param offendingDrivers drivers in the same order as incidents
     */
    public VehicleHistory(List<Date> incidentDates, List<Incident> incidents, List<Name> offendingDrivers) {
        this.incidentDates = incidentDates;
        this.incidents = incidents;
        this.offendingDrivers = offendingDrivers;
    }

    /**
     * Returns the list of incident dates.
     * <p>
     * The returned list contains the dates when each incident occurred, in parallel
     * with the incidents and offending drivers lists.
     * </p>
     *
     * @return the list of incident dates
     */
    public List<Date> getIncidentDates() {
        return incidentDates;
    }

    /**
     * Sets the list of incident dates.
     * <p>
     * <b>Warning:</b> When updating incident dates, ensure the new list has the same
     * size as the incidents and offending drivers lists to maintain data integrity.
     * </p>
     *
     * @param incidentDates the new list of incident dates
     */
    public void setIncidentDates(List<Date> incidentDates) {
        this.incidentDates = incidentDates;
    }

    /**
     * Returns the list of incidents.
     * <p>
     * The returned list contains the incidents that occurred, in parallel
     * with the incident dates and offending drivers lists.
     * </p>
     *
     * @return the list of incidents
     */
    public List<Incident> getIncidents() {
        return incidents;
    }

    /**
     * Sets the list of incidents.
     * <p>
     * <b>Warning:</b> When updating incidents, ensure the new list has the same
     * size as the incident dates and offending drivers lists to maintain data integrity.
     * </p>
     *
     * @param incidents the new list of incidents
     */
    public void setIncidents(List<Incident> incidents) {
        this.incidents = incidents;
    }

    /**
     * Returns the list of offending drivers.
     * <p>
     * The returned list contains the names of drivers who caused each incident,
     * in parallel with the incident dates and incidents lists.
     * </p>
     *
     * @return the list of offending drivers
     */
    public List<Name> getOffendingDrivers() {
        return offendingDrivers;
    }

    /**
     * Sets the list of offending drivers.
     * <p>
     * <b>Warning:</b> When updating offending drivers, ensure the new list has the same
     * size as the incident dates and incidents lists to maintain data integrity.
     * </p>
     *
     * @param offendingDrivers the new list of offending drivers
     */
    public void setOffendingDrivers(List<Name> offendingDrivers) {
        this.offendingDrivers = offendingDrivers;
    }
}