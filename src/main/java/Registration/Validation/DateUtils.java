package Registration.Validation;

import Registration.Entity.Date;

import java.time.DateTimeException;
import java.time.LocalDate;

/**
 * Small utility methods for converting the project's Date to java.time types.
 */
public final class DateUtils {

    private DateUtils() {
    }

    /**
     * Converts a project Date (year/month/day) to LocalDate.
     * Returns null if the input is null or invalid.
     */
    public static LocalDate toLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        try {
            return LocalDate.of(date.getYear(), date.getMonth(), date.getDay());
        } catch (DateTimeException e) {
            return null;
        }
    }
}
