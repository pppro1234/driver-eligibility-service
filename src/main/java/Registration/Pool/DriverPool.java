package Registration.Pool;

import Registration.Entity.*;
import Registration.IncidentFactory.Incident;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

/**
 * Shared in-memory pool for CLI driver and vehicle queries.
 * Read/write locking serializes entry updates with queries; vehicle claims use a concurrent set.
 */
public class DriverPool {

    // Singleton instance with volatile for thread safety
    private static volatile DriverPool instance;

    // Lock for thread-safe operations
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    // Local CLI grouping key only; this is not real-world identity verification.
    private final ConcurrentHashMap<LicenseKey, DriverEntry> driverEntries;

    // Tracks which vehicles are currently in use (for concurrent access prevention)
    private final Set<String> vehiclesInUse;

    private DriverPool() {
        this.driverEntries = new ConcurrentHashMap<>();
        this.vehiclesInUse = ConcurrentHashMap.newKeySet();
    }

    /**
     * Returns the lazily initialized shared pool.
     */
    public static DriverPool getInstance() {
        if (instance == null) {
            synchronized (DriverPool.class) {
                if (instance == null) {
                    instance = new DriverPool();
                }
            }
        }
        return instance;
    }

    public enum AddResult {
        ADDED, MERGED, DUPLICATE, CONFLICT
    }

    /** Backward-compatible result: true only if a new driver or vehicle was added. */
    public boolean addDriver(Application application) {
        AddResult result = addApplication(application);
        return result == AddResult.ADDED || result == AddResult.MERGED;
    }

    /**
     * Groups accepted CLI applications by issuing jurisdiction and license number.
     * Repeating a key adds previously unseen plates, retaining the original driver
     * snapshot. Conflicting name/birthdate data is reported without modifying the pool.
     */
    public AddResult addApplication(Application application) {
        Objects.requireNonNull(application, "application");
        Driver driver = Objects.requireNonNull(application.getDriver(), "driver");
        LicenseKey driverKey = generateDriverKey(application.getDriverLicense());
        List<Vehicle> vehicles = application.getVehicle();
        if (vehicles == null || vehicles.isEmpty() || vehicles.stream().anyMatch(v ->
                v == null || v.getPlateNumber() == null || v.getPlateNumber().isBlank())) {
            throw new IllegalArgumentException("Accepted applications require vehicles with plate numbers");
        }
        lock.writeLock().lock();
        try {
            DriverEntry entry = driverEntries.get(driverKey);
            boolean isNew = entry == null;
            if (isNew) {
                entry = new DriverEntry(driver, application.getDriverHistory());
                driverEntries.put(driverKey, entry);
            } else if (!Objects.equals(entry.getDriver().getName(), driver.getName())
                    || !Objects.equals(entry.getDriver().getBirthday(), driver.getBirthday())) {
                return AddResult.CONFLICT;
            }

            boolean addedVehicle = false;
            for (Vehicle vehicle : vehicles) {
                if (!entry.hasVehicle(generateVehicleKey(vehicle))) {
                    entry.addVehicle(vehicle);
                    addedVehicle = true;
                }
            }
            return isNew ? AddResult.ADDED : addedVehicle ? AddResult.MERGED : AddResult.DUPLICATE;
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Queries driver information by last name.
     * Thread-safe with read lock.
     */
    public String provideDriverInfo(String lastName) {
        lock.readLock().lock();
        try {
            // Find all drivers with matching last name (functional style)
            List<DriverEntry> matchingDrivers = driverEntries.values().stream()
                    .filter(entry -> entry.getDriver().getName().getLastName()
                            .equalsIgnoreCase(lastName))
                    .sorted(Comparator.comparing(e -> e.getDriver().getName().getFirstName()))
                    .collect(Collectors.toList());

            if (matchingDrivers.isEmpty()) {
                return "No registered driver found";
            }

            StringBuilder sb = new StringBuilder();
            for (DriverEntry entry : matchingDrivers) {
                sb.append(formatDriverInfo(entry));
            }
            return sb.toString().trim();
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Formats driver info according to specification.
     */
    private String formatDriverInfo(DriverEntry entry) {
        StringBuilder sb = new StringBuilder();
        Name name = entry.getDriver().getName();
        sb.append(name.getLastName()).append(", ").append(name.getFirstName()).append("\n");

        // List vehicles
        for (Vehicle v : entry.getVehicles()) {
            sb.append("  ").append(v.getYear()).append(" ")
                    .append(v.getMake()).append(" ").append(v.getModel())
                    .append(", ").append(v.getPlateNumber()).append("\n");
        }

        // List violations if any
        List<Incident> violations = entry.getViolations();
        if (violations != null && !violations.isEmpty()) {
            sb.append("  Driving violations:\n");
            violations.forEach(v ->
                    sb.append("    ").append(v.getIncidentType()).append("\n"));
        }
        return sb.toString();
    }

    /**
     * Marks a vehicle as in use (for concurrent access prevention).
     */
    public boolean claimVehicle(String plateNumber) {
        return vehiclesInUse.add(plateNumber);
    }

    /**
     * Releases a vehicle from use.
     */
    public void releaseVehicle(String plateNumber) {
        vehiclesInUse.remove(plateNumber);
    }

    /**
     * Checks if a vehicle is currently in use.
     */
    public boolean isVehicleInUse(String plateNumber) {
        return vehiclesInUse.contains(plateNumber);
    }

    public int getDriverCount() {
        lock.readLock().lock();
        try {
            return driverEntries.size();
        } finally {
            lock.readLock().unlock();
        }
    }

    public int getTotalVehicleCount() {
        lock.readLock().lock();
        try {
            return driverEntries.values().stream()
                    .mapToInt(e -> e.getVehicles().size())
                    .sum();
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Clears the pool (useful for testing).
     */
    public void clear() {
        lock.writeLock().lock();
        try {
            driverEntries.clear();
            vehiclesInUse.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }

    private LicenseKey generateDriverKey(DriverLicense license) {
        if (license == null || license.getCountryAndState() == null || license.getCountryAndState().isBlank()
                || license.getLicenseNumber() == null || license.getLicenseNumber().isBlank()) {
            throw new IllegalArgumentException("Accepted applications require license jurisdiction and number");
        }
        return new LicenseKey(license.getCountryAndState().trim().toUpperCase(Locale.ROOT),
                license.getLicenseNumber().trim().toUpperCase(Locale.ROOT));
    }

    private record LicenseKey(String jurisdiction, String number) {
    }

    private String generateVehicleKey(Vehicle vehicle) {
        return vehicle.getPlateNumber();
    }

    /**
     * Inner class representing a driver entry in the pool.
     */
    private static class DriverEntry {
        private final Driver driver;
        private final List<Vehicle> vehicles;
        private final List<Incident> violations;
        private final Set<String> vehiclePlates;

        public DriverEntry(Driver driver, List<Incident> violations) {
            this.driver = driver;
            this.vehicles = new ArrayList<>();
            this.violations = violations != null ? new ArrayList<>(violations) : new ArrayList<>();
            this.vehiclePlates = new HashSet<>();
        }

        public Driver getDriver() {
            return driver;
        }

        public List<Vehicle> getVehicles() {
            return new ArrayList<>(vehicles);
        }

        public List<Incident> getViolations() {
            return new ArrayList<>(violations);
        }

        public boolean hasVehicle(String plateNumber) {
            return vehiclePlates.contains(plateNumber);
        }

        public void addVehicle(Vehicle vehicle) {
            vehicles.add(vehicle);
            vehiclePlates.add(vehicle.getPlateNumber());
        }
    }
}