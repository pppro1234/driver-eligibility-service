package Registration.IncidentFactory;

/**
 * Mutable crash classified by {@link CrashEnum}.
 *
 * <p>The default constructor leaves the type null; configure it before calling
 * {@link #getIncidentType()}. Instances are not thread-safe.</p>
 */
public class Crash extends Incident {

    /** The specific type of crash incident. */
    private CrashEnum crashType;

    /**
     * Creates an incident with no specific type; {@link #getIncidentType()} will fail
     * until a non-null type is assigned.
     */
    public Crash() {}

    /**
     * Creates an incident with the supplied type.
     *
     * @param crashType the specific type, or null if it will be assigned later
     */
    public Crash(CrashEnum crashType) {
        this.crashType = crashType;
    }

    /**
     * Parses an exact, case-sensitive {@link CrashEnum} constant name.
     *
     * @param crashType the enum constant name, not its display description
     * @throws IllegalArgumentException if the name is not a recognized constant
     * @throws NullPointerException if crashType is null
     */
    public Crash(String crashType) {
        this.crashType = CrashEnum.valueOf(crashType);
    }

    /**
     * Returns the specific crash type.
     *
     * @return the {@link CrashEnum} representing the crash type, or null if not set
     */
    public CrashEnum getCrashType() {
        return crashType;
    }

    /**
     * Sets the specific crash type.
     *
     * @param crashType the {@link CrashEnum} to set as the crash type
     */
    public void setCrashType(CrashEnum crashType) {
        this.crashType = crashType;
    }

    /**
     * Returns the display description of the configured type.
     *
     * @return the human-readable crash description
     * @throws NullPointerException if crashType is null
     */
    @Override
    public String getIncidentType() {
        return crashType.toString();
    }

    /**
     * Returns {@link IncidentType#CRASH}, even when the specific type is unset.
     *
     * @return the crash category
     */
    @Override
    public IncidentType getIncidentCategory() {
        return IncidentType.CRASH;
    }
}