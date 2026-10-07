package Registration.Validation;

import Registration.Entity.Application;
import Registration.Entity.Vehicle;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Objects;
import java.util.List;

/**
 * Validates the registered vehicles:
 * - Vehicle must not be older than 15 years.
 */
public class VehicleValidationStrategy implements ValidationStrategy {

    private final Clock clock;

    public VehicleValidationStrategy() {
        this(Clock.systemUTC());
    }

    public VehicleValidationStrategy(Clock clock) {
        this.clock = Objects.requireNonNull(clock, "clock");
    }

    @Override
    public ValidationResult validate(Application application) {
        if (application == null) {
            return ValidationResult.fail("Missing application");
        }
        List<Vehicle> vehicles = application.getVehicle();
        if (vehicles == null || vehicles.isEmpty()) {
            return ValidationResult.fail("Missing vehicle information");
        }

        int currentYear = LocalDate.now(clock).getYear();
        for (Vehicle v : vehicles) {
            if (v == null) {
                return ValidationResult.fail("Invalid vehicle information");
            }
            if ((long) currentYear - v.getYear() > 15) {
                return ValidationResult.fail("Vehicle must not be older than 15 years");
            }
        }

        return ValidationResult.ok();
    }
}
