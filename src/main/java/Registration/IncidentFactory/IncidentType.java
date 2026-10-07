package Registration.IncidentFactory;

/**
 * Incident categories used by history validation.
 */
public enum IncidentType {

    /**
     * Violations involving driving behavior.
     */
    MOVING_VIOLATION,

    /**
     * Violations involving parking, documentation, or vehicle condition.
     */
    NONMOVING_VIOLATION,

    /**
     * Vehicle collisions.
     */
    CRASH;
}