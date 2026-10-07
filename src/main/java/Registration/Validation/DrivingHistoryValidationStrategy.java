package Registration.Validation;

import Registration.Entity.Application;
import Registration.IncidentFactory.Incident;
import Registration.IncidentFactory.MovingViolation;
import Registration.IncidentFactory.MovingViolationEnum;

import java.util.EnumSet;
import java.util.List;

/**
 * Validates the driver's moving violation history.
 * Disqualifying moving violations:
 * - Reckless driving
 * - Speeding
 * - DUI
 * - Driving without valid license/insurance
 */
public class DrivingHistoryValidationStrategy implements ValidationStrategy {

    private static final EnumSet<MovingViolationEnum> DISQUALIFYING = EnumSet.of(
            MovingViolationEnum.RECKLESS_DRIVING,
            MovingViolationEnum.SPEEDING,
            MovingViolationEnum.DUI,
            MovingViolationEnum.DRIVING_WITHOUT_VALID_LICENSE_INSURANCE
    );

    @Override
    public ValidationResult validate(Application application) {
        if (application == null) {
            return ValidationResult.fail("Missing application");
        }

        List<Incident> history = application.getDriverHistory();
        if (history == null || history.isEmpty()) {
            return ValidationResult.ok();
        }

        for (Incident incident : history) {
            if (incident instanceof MovingViolation) {
                MovingViolation mv = (MovingViolation) incident;
                MovingViolationEnum type = mv.getViolationType();
                if (type != null && DISQUALIFYING.contains(type)) {
                    return ValidationResult.fail("Driver has disqualifying moving violation: " + mv.getIncidentType());
                }
            }
        }

        return ValidationResult.ok();
    }
}
