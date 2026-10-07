package Registration.Validation;

import Registration.Entity.Application;

import java.time.Clock;
import java.util.Arrays;
import java.util.Objects;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Coordinates validation by running a list of ValidationStrategy instances.
 */
public class Validator {
    private final List<ValidationStrategy> strategies;
    private final Clock clock;

    public Validator(List<ValidationStrategy> strategies) {
        this.strategies = List.copyOf(strategies);
        this.clock = null;
    }

    /**
     * Creates the six-rule validator using the current UTC date.
     */
    public static Validator defaultValidator() {
        return defaultValidator(Clock.systemUTC());
    }

    /** Uses one time snapshot for all six rules on each evaluation. */
    public static Validator defaultValidator(Clock clock) {
        return new Validator(clock);
    }

    private Validator(Clock clock) {
        this.strategies = List.of();
        this.clock = Objects.requireNonNull(clock, "clock");
    }

    private static List<ValidationStrategy> defaultStrategies(Clock clock) {
        return Arrays.asList(
                new AgeValidationStrategy(clock),
                new LicenseValidationStrategy(clock),
                new VehicleValidationStrategy(clock),
                new InsuranceValidationStrategy(clock),
                new DrivingHistoryValidationStrategy(),
                new VehicleHistoryValidationStrategy(clock)
        );
    }

    /**
     * Validates an application and returns a report with all failure reasons.
     */
    public ValidationReport validate(Application application) {
        List<ValidationStrategy> evaluationStrategies = clock == null ? strategies
                : defaultStrategies(Clock.fixed(clock.instant(), clock.getZone()));
        List<String> failures = evaluationStrategies.stream()
                .map(s -> s.validate(application))
                .filter(r -> r != null && !r.isValid())
                .map(ValidationResult::getMessage)
                .collect(Collectors.toList());

        return new ValidationReport(failures.isEmpty(), failures);
    }
}
