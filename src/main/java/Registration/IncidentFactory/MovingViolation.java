package Registration.IncidentFactory;

/**
 * Mutable moving violation classified by {@link MovingViolationEnum}.
 *
 * <p>The default constructor leaves the type null; configure it before calling
 * {@link #getIncidentType()}. Instances are not thread-safe.</p>
 */
public class MovingViolation extends Incident {

    /** The specific type of moving violation. */
    private MovingViolationEnum violationType;

    /**
     * Creates an incident with the supplied type.
     *
     * @param violationType the specific type, or null if it will be assigned later
     */
    public MovingViolation(MovingViolationEnum violationType) {
        this.violationType = violationType;
    }

    /**
     * Parses an exact, case-sensitive {@link MovingViolationEnum} constant name.
     *
     * @param violationType the enum constant name, not its display description
     * @throws IllegalArgumentException if the name is not a recognized constant
     * @throws NullPointerException if violationType is null
     */
    public MovingViolation(String violationType) {
        this.violationType = MovingViolationEnum.valueOf(violationType);
    }

    /**
     * Creates an incident with no specific type; {@link #getIncidentType()} will fail
     * until a non-null type is assigned.
     */
    public MovingViolation() {}

    /**
     * Sets the specific moving violation type.
     *
     * @param violationType the {@link MovingViolationEnum} to set as the violation type
     */
    public void setViolationType(MovingViolationEnum violationType) {
        this.violationType = violationType;
    }

    /**
     * Returns the specific moving violation type.
     *
     * @return the {@link MovingViolationEnum} representing the violation type, or null if not set
     */
    public MovingViolationEnum getViolationType() {
        return violationType;
    }

    /**
     * Returns the display description of the configured type.
     *
     * @return the human-readable moving violation description
     * @throws NullPointerException if violationType is null
     */
    @Override
    public String getIncidentType() {
        return violationType.toString();
    }

    /**
     * Returns {@link IncidentType#MOVING_VIOLATION}, even when the specific type is unset.
     *
     * @return the moving violation category
     */
    @Override
    public IncidentType getIncidentCategory() {
        return IncidentType.MOVING_VIOLATION;
    }
}