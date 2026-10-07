package Registration.Validation;

import Registration.Entity.Application;
import Registration.Entity.Date;
import Registration.Entity.DriverLicense;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Validates driver license rules:
 * - Name matches application exactly
 * - Birthdate matches exactly
 * - Issued in US or Canada
 * - Issued at least 6 months ago
 * - Not expired
 */
public class LicenseValidationStrategy implements ValidationStrategy {

    private final Clock clock;

    public LicenseValidationStrategy() {
        this(Clock.systemUTC());
    }

    public LicenseValidationStrategy(Clock clock) {
        this.clock = Objects.requireNonNull(clock, "clock");
    }

    @Override
    public ValidationResult validate(Application application) {
        if (application == null || application.getDriver() == null || application.getDriverLicense() == null) {
            return ValidationResult.fail("Missing driver license information");
        }

        DriverLicense license = application.getDriverLicense();

        // Name consistency (exact match)
        if (license.getName() == null || application.getDriver().getName() == null
                || !application.getDriver().getName().equals(license.getName())) {
            return ValidationResult.fail("Name on application must match name on license exactly");
        }

        // Birthdate consistency (exact match)
        if (license.getBirthDate() == null || application.getDriver().getBirthday() == null
                || !application.getDriver().getBirthday().equals(license.getBirthDate())) {
            return ValidationResult.fail("Birthdate on application must match birthdate on license exactly");
        }

        // Country of issuance (US or Canada)
        String country = extractCountry(license.getCountryAndState());
        if (!isUsOrCanada(country)) {
            return ValidationResult.fail("License must be issued in the US or Canada");
        }

        LocalDate today = LocalDate.now(clock);

        // License age: issuance >= 6 months
        Date issuance = license.getIssuanceDate();
        LocalDate issuanceDate = DateUtils.toLocalDate(issuance);
        if (issuanceDate == null) {
            return ValidationResult.fail("Invalid license issuance date");
        }
        if (issuanceDate.isAfter(today.minusMonths(6))) {
            return ValidationResult.fail("License must have been issued at least 6 months ago");
        }

        // Not expired (expiration date must be today or in the future)
        Date exp = license.getExpiratonDate();
        LocalDate expDate = DateUtils.toLocalDate(exp);
        if (expDate == null) {
            return ValidationResult.fail("Invalid license expiration date");
        }
        if (expDate.isBefore(today)) {
            return ValidationResult.fail("License must not be expired");
        }

        return ValidationResult.ok();
    }

    private static String extractCountry(String countryAndState) {
        if (countryAndState == null) {
            return "";
        }
        String trimmed = countryAndState.trim();
        if (trimmed.isEmpty()) {
            return "";
        }
        // Take token before comma if present.
        int comma = trimmed.indexOf(',');
        if (comma >= 0) {
            return trimmed.substring(0, comma).trim();
        }
        // Otherwise take first whitespace-separated token.
        String[] parts = trimmed.split("\\s+");
        return parts.length == 0 ? "" : parts[0].trim();
    }

    private static boolean isUsOrCanada(String country) {
        if (country == null) {
            return false;
        }
        String normalized = country.toUpperCase().replaceAll("[^A-Z]", "");
        return normalized.equals("US")
                || normalized.equals("USA")
                || normalized.equals("UNITEDSTATES")
                || normalized.equals("UNITEDSTATESOFAMERICA")
                || normalized.equals("CANADA");
    }
}
