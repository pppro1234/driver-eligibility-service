package Registration.IncidentFactory;

/**
 * Creates {@link Crash} instances with no specific type configured.
 */
public class CrashFactory extends IncidentFactory {

    /**
     * Creates an incident whose type must be assigned before validation or description lookup.
     *
     * @return a new {@link Crash} with a null specific type
     */
    @Override
    public Incident createViolation() {
        return new Crash();
    }
}