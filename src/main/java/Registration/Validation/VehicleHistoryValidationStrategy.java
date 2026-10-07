package Registration.Validation;

import Registration.Entity.Application;
import Registration.Entity.Date;
import Registration.Entity.VehicleHistory;
import Registration.IncidentFactory.Incident;
import Registration.IncidentFactory.IncidentType;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Objects;
import java.util.List;

/**
 * Validates vehicle history:
 * - No crashes or moving violations in the last 6 months.
 */
public class VehicleHistoryValidationStrategy implements ValidationStrategy {

    private final Clock clock;

    public VehicleHistoryValidationStrategy() {
        this(Clock.systemUTC());
    }

    public VehicleHistoryValidationStrategy(Clock clock) {
        this.clock = Objects.requireNonNull(clock, "clock");
    }

    @Override
    public ValidationResult validate(Application application) {
        if (application == null) {
            return ValidationResult.fail("Missing application");
        }

        List<VehicleHistory> histories = application.getVehicleHistoryRecords();
        if (histories == null || histories.isEmpty()) {
            // No vehicle history provided is treated as "no incidents".
            return ValidationResult.ok();
        }

        LocalDate cutoff = LocalDate.now(clock).minusMonths(6);

        for (VehicleHistory history : histories) {
            if (history == null) {
                continue;
            }
            List<Date> dates = history.getIncidentDates();
            List<Incident> incidents = history.getIncidents();
            if (dates == null || incidents == null) {
                continue;
            }

            int count = Math.min(dates.size(), incidents.size());
            for (int i = 0; i < count; i++) {
                LocalDate incidentDate = DateUtils.toLocalDate(dates.get(i));
                if (incidentDate == null) {
                    return ValidationResult.fail("Invalid vehicle incident date");
                }
                if (incidentDate.isBefore(cutoff)) {
                    continue;
                }

                Incident incident = incidents.get(i);
                if (incident == null) {
                    continue;
                }

                IncidentType category = incident.getIncidentCategory();
                if (category == IncidentType.CRASH || category == IncidentType.MOVING_VIOLATION) {
                    return ValidationResult.fail("Vehicle has recent disqualifying incident: " + incident.getIncidentType());
                }
            }
        }

        return ValidationResult.ok();
    }
}
