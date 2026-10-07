package Registration.IncidentFactory;

/**
 * Creates {@link NonMovingViolation} instances with no specific type configured.
 */
public class NonMovingViolationFactory extends IncidentFactory {

    /**
     * Creates an incident whose type must be assigned before validation or description lookup.
     *
     * @return a new {@link NonMovingViolation} with a null specific type
     */
    @Override
    public Incident createViolation() {
        return new NonMovingViolation();
    }
}