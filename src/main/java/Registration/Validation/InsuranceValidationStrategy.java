package Registration.Validation;

import Registration.Entity.Application;
import Registration.Entity.Date;
import Registration.Entity.Driver;
import Registration.Entity.Name;
import Registration.Entity.VehicleInsurance;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Objects;
import java.util.List;

/**
 * Validates insurance rules:
 * - Insurance must not be expired
 * - If driver is not the policy owner, they must be listed as an insured driver
 */
public class InsuranceValidationStrategy implements ValidationStrategy {

    private final Clock clock;

    public InsuranceValidationStrategy() {
        this(Clock.systemUTC());
    }

    public InsuranceValidationStrategy(Clock clock) {
        this.clock = Objects.requireNonNull(clock, "clock");
    }

    @Override
    public ValidationResult validate(Application application) {
        if (application == null || application.getDriver() == null) {
            return ValidationResult.fail("Missing insurance/driver information");
        }

        VehicleInsurance insurance = application.getVehicleInsurance();
        if (insurance == null) {
            return ValidationResult.fail("Missing vehicle insurance information");
        }

        // Insurance must not be expired
        Date exp = insurance.getExpirationDate();
        LocalDate expDate = DateUtils.toLocalDate(exp);
        if (expDate == null) {
            return ValidationResult.fail("Invalid insurance expiration date");
        }
        if (expDate.isBefore(LocalDate.now(clock))) {
            return ValidationResult.fail("Insurance must not be expired");
        }

        Name driverName = application.getDriver().getName();
        if (driverName == null) {
            return ValidationResult.fail("Missing driver name");
        }

        // Driver coverage
        Name policyOwner = insurance.getNameOnPolicy();
        if (policyOwner != null && driverName.equals(policyOwner)) {
            return ValidationResult.ok();
        }

        List<Driver> insuredDrivers = insurance.getDrivers();
        boolean covered = insuredDrivers != null && insuredDrivers.stream()
                .filter(d -> d != null && d.getName() != null)
                .anyMatch(d -> driverName.equals(d.getName()));

        if (!covered) {
            return ValidationResult.fail("Driver must be listed on the insurance policy");
        }

        return ValidationResult.ok();
    }
}
