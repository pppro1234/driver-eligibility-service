package Registration.IncidentFactory;

/**
 * An incident's display description and category used by history validation.
 */
public abstract class Incident {

    /**
     * Returns the human-readable incident type description.
     *
     * @return the specific incident description
     */
    public abstract String getIncidentType();

    /**
     * Returns the category used to select history validation rules.
     *
     * @return the incident category
     */
    public abstract IncidentType getIncidentCategory();
}