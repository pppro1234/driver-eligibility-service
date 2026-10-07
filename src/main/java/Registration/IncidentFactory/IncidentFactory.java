package Registration.IncidentFactory;

/**
 * Creates incidents of a category selected by the concrete factory.
 *
 * <p>The supplied factories leave the specific type unset. Callers must configure
 * that type before requesting its description or using the incident in validation.</p>
 */
public abstract class IncidentFactory {

    /**
     * Creates a new incident. The name is retained for all categories, including crashes.
     *
     * @return a new incident whose specific type may still need configuration
     */
    public abstract Incident createViolation();
}