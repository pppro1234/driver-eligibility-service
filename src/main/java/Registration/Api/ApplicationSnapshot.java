package Registration.Api;

import java.time.LocalDate;
import java.util.List;

/** Stable API view of the normalized application; no JPA or mutable domain objects escape. */
public record ApplicationSnapshot(DriverData driver, LicenseData license, List<VehicleData> vehicles,
                                  InsuranceData insurance, List<IncidentData> driverHistory,
                                  List<IncidentData> vehicleHistory) {
    public record NameData(String firstName, String lastName) { }
    public record DriverData(String firstName, String lastName, LocalDate birthDate) { }
    public record LicenseData(String number, String firstName, String lastName, String address,
                              LocalDate birthDate, String countryAndState, LocalDate issuanceDate,
                              LocalDate expirationDate) { }
    public record VehicleData(int year, String color, String make, String model, String ownerName,
                              String plate) { }
    public record InsuranceData(NameData owner, LocalDate expirationDate, List<NameData> drivers) { }
    public record IncidentData(String plate, LocalDate date, String category, String type, NameData offender) { }
}
