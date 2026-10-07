package Registration.IncidentFactory;

/**
 * Creates {@link MovingViolation} instances with no specific type configured.
 */
public class MovingViolationFactory extends IncidentFactory {

    /**
     * Creates an incident whose type must be assigned before validation or description lookup.
     *
     * @return a new {@link MovingViolation} with a null specific type
     */
    @Override
    public Incident createViolation() {
        return new MovingViolation();
    }
}