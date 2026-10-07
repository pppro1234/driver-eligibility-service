package Registration.IncidentFactory;

/**
 * Supported moving violation types and their display descriptions.
 *
 * <p>Constant names are used for parsing; {@link #toString()} returns the display description.
 * Eligibility rules are defined by the validation strategies.</p>
 */
public enum MovingViolationEnum {

    /**
     * Driving while attention is diverted from the road.
     */
    DISTRACTED_DRIVING("Distracted Driving"),

    /**
     * Driving with disregard for the safety of persons or property.
     */
    RECKLESS_DRIVING("Reckless Driving"),

    /**
     * Driving above the applicable speed limit.
     */
    SPEEDING("Speeding"),

    /**
     * Driving under the influence of alcohol or drugs.
     */
    DUI("Driving Under Influence"),

    /**
     * Failure to follow traffic signs or signals.
     */
    FAILURE_TO_OBEY_SIGNS("Failure to Obey Traffic Signs"),

    /**
     * Driving without a valid license or insurance.
     */
    DRIVING_WITHOUT_VALID_LICENSE_INSURANCE("Driving Without Valid License/Insurance");

    /** The human-readable description for this moving violation type. */
    private final String description;

    /**
     * Associates a violation type with its display description.
     *
     * @param description the display description
     */
    MovingViolationEnum(String description) {
        this.description = description;
    }

    /**
     * Returns the display description rather than the enum constant name.
     *
     * @return the human-readable violation type
     */
    @Override
    public String toString() {
        return description;
    }
}