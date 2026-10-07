package Registration.Validation;

import Registration.Entity.Application;

/**
 * Strategy interface for validating an application.
 */
public interface ValidationStrategy {

    /**
     * Validates an application and returns a ValidationResult.
     */
    ValidationResult validate(Application application);
}
