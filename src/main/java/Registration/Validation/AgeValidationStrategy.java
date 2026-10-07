package Registration.Validation;

import Registration.Entity.Application;
import Registration.Entity.Date;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Objects;
import java.time.Period;

/**
 * Validates that the driver is at least 21 years old.
 */
public class AgeValidationStrategy implements ValidationStrategy {

    private final Clock clock;

    public AgeValidationStrategy() {
        this(Clock.systemUTC());
    }

    public AgeValidationStrategy(Clock clock) {
        this.clock = Objects.requireNonNull(clock, "clock");
    }

    @Override
    public ValidationResult validate(Application application) {
        if (application == null || application.getDriver() == null) {
            return ValidationResult.fail("Missing driver information");
        }

        Date birth = application.getDriver().getBirthday();
        LocalDate birthDate = DateUtils.toLocalDate(birth);
        if (birthDate == null) {
            return ValidationResult.fail("Invalid driver birthdate");
        }

        int years = Period.between(birthDate, LocalDate.now(clock)).getYears();
        if (years < 21) {
            return ValidationResult.fail("Driver must be at least 21 years old");
        }

        return ValidationResult.ok();
    }
}
