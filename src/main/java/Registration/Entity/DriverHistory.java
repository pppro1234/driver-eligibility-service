package Registration.Entity;

import Registration.IncidentFactory.Incident;

public class DriverHistory {
    private Date violationDate;
    private Incident violationType;

    public DriverHistory(Date violationDate, Incident violationType) {
        this.violationDate = violationDate;
        this.violationType = violationType;
    }

    public Date getViolationDate() {
        return violationDate;
    }

    public void setViolationDate(Date violationDate) {
        this.violationDate = violationDate;
    }

    public String getViolationType() {
        return violationType.getIncidentType();
    }

    public void setViolationType(Incident incidentType) {
        this.violationType = incidentType;
    }

    public Incident getViolationIncident() {
        return violationType;
    }
}
