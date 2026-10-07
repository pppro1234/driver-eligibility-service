package Registration.Entity;

import Registration.IncidentFactory.Incident;

/**
 * Vehicle incident with non-null date, incident type, and driver references.
 *
 * <p>The references cannot be reassigned, but their objects remain mutable and are
 * returned directly by getters.</p>
 */
public final class VehicleIncident {
  private final Date incidentDate;
  private final Incident incidentType;
  private final Name offendingDriver;

  public VehicleIncident(Date incidentDate, Incident incidentType, Name offendingDriver) {
    if (incidentDate == null) {
      throw new IllegalArgumentException("incidentDate must not be null");
    }
    if (incidentType == null) {
      throw new IllegalArgumentException("incidentType must not be null");
    }
    if (offendingDriver == null) {
      throw new IllegalArgumentException("offendingDriver must not be null");
    }
    this.incidentDate = incidentDate;
    this.incidentType = incidentType;
    this.offendingDriver = offendingDriver;
  }

  public Date getIncidentDate() {
    return incidentDate;
  }

  public Incident getIncidentType() {
    return incidentType;
  }

  public Name getOffendingDriver() {
    return offendingDriver;
  }
}
