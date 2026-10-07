package Registration.IncidentFactory;

public enum NonMovingViolationEnum {
    PARKING_VIOLATION("Parking Violation"),
    PAPERWORK_ISSUE("Paper Work Issue"),
    VEHICLE_PROBLEM("Vehicle Problem");

    private final String description;
    NonMovingViolationEnum(String description) {
        this.description = description;
    }

    public String toString() {
        return description;
    }
}
