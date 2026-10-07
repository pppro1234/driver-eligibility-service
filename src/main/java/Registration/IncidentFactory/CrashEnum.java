package Registration.IncidentFactory;

/**
 * Supported crash types and their display labels.
 *
 * <p>Constant names are used for parsing; {@link #toString()} returns the display label.
 * Eligibility rules are defined by the validation strategies, not enum order.</p>
 */
public enum CrashEnum {

    /**
     * A minor vehicle collision.
     */
    FENDER_BENDER("Fender bender"),

    /**
     * A crash without bodily injuries.
     */
    NO_INJURY_CRASH("No Injury Crash"),

    /**
     * A crash involving bodily injuries.
     */
    INJURY_CRASH("Injury Crash");

    /** The human-readable display name for this crash type. */
    private final String name;

    /**
     * Associates a crash type with its display label.
     *
     * @param name the display label
     */
    CrashEnum(String name) {
        this.name = name;
    }

    /**
     * Returns the display label rather than the enum constant name.
     *
     * @return the human-readable crash type
     */
    @Override
    public String toString() {
        return name;
    }
}