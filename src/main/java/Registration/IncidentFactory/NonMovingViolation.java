package Registration.IncidentFactory;

/**
 * Mutable non-moving violation classified by {@link NonMovingViolationEnum}.
 *
 * <p>The default constructor leaves the type null; configure it before calling
 * {@link #getIncidentType()}. Instances are not thread-safe.</p>
 */
public class NonMovingViolation extends Incident {

    /** The specific type of non-moving violation. */
    private NonMovingViolationEnum violationType;

    /**
     * Creates an incident with the supplied type.
     *
     * @param violationType the specific type, or null if it will be assigned later
     */
    public NonMovingViolation(NonMovingViolationEnum violationType) {
        this.violationType = violationType;
    }

    /**
     * Parses an exact, case-sensitive {@link NonMovingViolationEnum} constant name.
     *
     * @param violationType the enum constant name, not its display description
     * @throws IllegalArgumentException if the name is not a recognized constant
     * @throws NullPointerException if violationType is null
     */
    public NonMovingViolation(String violationType) {
        this.violationType = NonMovingViolationEnum.valueOf(violationType);
    }

    /**
     * Creates an incident with no specific type; {@link #getIncidentType()} will fail
     * until a non-null type is assigned.
     */
    public NonMovingViolation() {}

    /**
     * Sets the specific non-moving violation type.
     *
     * @param violationType the {@link NonMovingViolationEnum} to set as the violation type
     */
    public void setViolationType(NonMovingViolationEnum violationType) {
        this.violationType = violationType;
    }

    /**
     * Returns the specific non-moving violation type.
     *
     * @return the {@link NonMovingViolationEnum} representing the violation type, or null if not set
     */
    public NonMovingViolationEnum getViolationType() {
        return violationType;
    }

    /**
     * Returns the display description of the configured type.
     *
     * @return the human-readable non-moving violation description
     * @throws NullPointerException if violationType is null
     */
    @Override
    public String getIncidentType() {
        return this.violationType.toString();
    }

    /**
     * Returns {@link IncidentType#NONMOVING_VIOLATION}, even when the specific type is unset.
     *
     * @return the non-moving violation category
     */
    @Override
    public IncidentType getIncidentCategory() {
        return IncidentType.NONMOVING_VIOLATION;
    }
}