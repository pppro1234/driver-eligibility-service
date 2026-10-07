package Registration.Entity;


import Registration.IncidentFactory.Incident;

import java.util.List;

public class Application {
    private Driver driver;
    private DriverLicense driverLicense;
    private List<Vehicle> vehicle;
    private VehicleInsurance vehicleInsurance;
    private List<Incident> driverHistory;
    private List<Incident> vehicleHistory;
    private List<DriverHistory> driverHistoryDetails;
    private List<VehicleHistory> vehicleHistoryRecords;

    public Application(Builder builder) {
        this.driver = builder.driver;
        this.driverLicense = builder.driverLicense;
        this.vehicle = builder.vehicle;
        this.vehicleInsurance = builder.vehicleInsurance;
        this.driverHistory = builder.driverHistory;
        this.vehicleHistory = builder.vehicleHistory;
        this.driverHistoryDetails = builder.driverHistoryDetails;
        this.vehicleHistoryRecords = builder.vehicleHistoryRecords;
    }

    public static class Builder{
        private Driver driver;
        private DriverLicense driverLicense;
        private List<Vehicle> vehicle=null;
        private VehicleInsurance vehicleInsurance=null;
        private List<Incident> driverHistory=null;
        private List<Incident> vehicleHistory=null;
        private List<DriverHistory> driverHistoryDetails = null;
        private List<VehicleHistory> vehicleHistoryRecords = null;

        public Builder driver(Driver driver) {
            this.driver = driver;
            return this;
        }

        public Builder driverLicense(DriverLicense driverLicense) {
            this.driverLicense = driverLicense;
            return this;
        }

        public Builder vehicle(List<Vehicle> vehicle) {
            this.vehicle = vehicle;
            return this;
        }

        public Builder vehicleInsurance(VehicleInsurance vehicleInsurance) {
            this.vehicleInsurance = vehicleInsurance;
            return this;
        }

        public Builder driverHistory(List<Incident> driverHistory) {
            this.driverHistory = driverHistory;
            return this;
        }
        public Builder vehicleHistory(List<Incident> vehicleHistory) {
            this.vehicleHistory = vehicleHistory;
            return this;
        }


        public Builder driverHistoryDetails(List<DriverHistory> driverHistoryDetails) {
            this.driverHistoryDetails = driverHistoryDetails;
            return this;
        }

        public Builder vehicleHistoryRecords(List<VehicleHistory> vehicleHistoryRecords) {
            this.vehicleHistoryRecords = vehicleHistoryRecords;
            return this;
        }

        public Application build(){
            return new Application(this);
        }
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public DriverLicense getDriverLicense() {
        return driverLicense;
    }

    public void setDriverLicense(DriverLicense driverLicense) {
        this.driverLicense = driverLicense;
    }

    public List<Vehicle> getVehicle() {
        return vehicle;
    }

    public void setVehicle(List<Vehicle> vehicle) {
        this.vehicle = vehicle;
    }

    public VehicleInsurance getVehicleInsurance() {
        return vehicleInsurance;
    }

    public void setVehicleInsurance(VehicleInsurance vehicleInsurance) {
        this.vehicleInsurance = vehicleInsurance;
    }

    public List<Incident> getDriverHistory() {
        return driverHistory;
    }

    public void setDriverHistory(List<Incident> driverHistory) {
        this.driverHistory = driverHistory;
    }

    public List<Incident> getVehicleHistory() {
        return vehicleHistory;
    }

    public void setVehicleHistory(List<Incident> vehicleHistory) {
        this.vehicleHistory = vehicleHistory;
    }

    public List<DriverHistory> getDriverHistoryDetails() {
        return driverHistoryDetails;
    }

    public void setDriverHistoryDetails(List<DriverHistory> driverHistoryDetails) {
        this.driverHistoryDetails = driverHistoryDetails;
    }

    public List<VehicleHistory> getVehicleHistoryRecords() {
        return vehicleHistoryRecords;
    }

    public void setVehicleHistoryRecords(List<VehicleHistory> vehicleHistoryRecords) {
        this.vehicleHistoryRecords = vehicleHistoryRecords;
    }
}
