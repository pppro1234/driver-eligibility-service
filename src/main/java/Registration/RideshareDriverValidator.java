package Registration;

import Registration.Entity.Application;
import Registration.IO.FileParseException;
import Registration.IO.ProspectiveDriverFileParser;
import Registration.Pool.DriverPool;
import Registration.Validation.ValidationReport;
import Registration.Validation.Validator;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Imports and validates application files, then queries accepted drivers in the CLI pool.
 */
public class RideshareDriverValidator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String filePath = null;
        Clock clock = Clock.systemUTC();
        try {
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    String arg = args[i];
                    if ("--date".equals(arg)) {
                        if (++i >= args.length) {
                            throw new IllegalArgumentException("--date requires YYYY-MM-DD");
                        }
                        LocalDate date = LocalDate.parse(args[i]);
                        clock = Clock.fixed(date.atStartOfDay(ZoneOffset.UTC).toInstant(), ZoneOffset.UTC);
                    } else if (arg != null && !arg.isBlank() && filePath == null && !arg.startsWith("--")) {
                        filePath = arg;
                    } else {
                        throw new IllegalArgumentException("Unexpected argument: " + arg);
                    }
                }
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date: use a real calendar date in YYYY-MM-DD format.");
            return;
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid arguments: " + e.getMessage());
            System.out.println("Usage: RideshareDriverValidator [file.json] [--date YYYY-MM-DD]");
            return;
        }
        if (filePath == null) {
            System.out.print("Enter prospective driver file path: ");
            if (!scanner.hasNextLine()) {
                return;
            }
            filePath = scanner.nextLine().trim();
        }

        ProspectiveDriverFileParser parser = new ProspectiveDriverFileParser();
        List<Application> applications;

        try {
            applications = parser.parse(filePath);
            System.out.println("File loaded successfully.");
            System.out.println("Prospective drivers loaded: " + applications.size());
        } catch (IOException e) {
            System.out.println("Failed to read file: " + e.getMessage());
            return;
        } catch (FileParseException e) {
            System.out.println("Invalid file format: " + e.getMessage());
            return;
        }

        // Use the same eligibility rules as the API.
        Validator validator = Validator.defaultValidator(clock);
        System.out.println("Evaluation date (UTC): " + LocalDate.now(clock));
        DriverPool pool = DriverPool.getInstance();

        AtomicInteger accepted = new AtomicInteger(0);
        AtomicInteger rejected = new AtomicInteger(0);
        AtomicInteger added = new AtomicInteger(0);
        AtomicInteger merged = new AtomicInteger(0);
        AtomicInteger duplicates = new AtomicInteger(0);
        AtomicInteger conflicts = new AtomicInteger(0);

        // Pool updates and summary counters support concurrent application processing.
        applications.parallelStream().forEach(app -> {
            ValidationReport report = validator.validate(app);
            if (report.isValid()) {
                accepted.incrementAndGet();
                switch (pool.addApplication(app)) {
                    case ADDED -> added.incrementAndGet();
                    case MERGED -> merged.incrementAndGet();
                    case DUPLICATE -> duplicates.incrementAndGet();
                    case CONFLICT -> conflicts.incrementAndGet();
                }
            } else {
                rejected.incrementAndGet();
            }
        });

        System.out.println("Validation complete.");
        System.out.println("Accepted applications: " + accepted.get());
        System.out.println("Rejected applications: " + rejected.get());
        System.out.println("New driver entries: " + added.get());
        System.out.println("Existing drivers with added vehicles: " + merged.get());
        System.out.println("Duplicate driver/vehicle applications: " + duplicates.get());
        System.out.println("Conflicting license-key applications not added: " + conflicts.get());

        // Query accepted drivers until exit or end of input.
        while (true) {
            System.out.print("Enter last name to query (or 'exit'): ");
            if (!scanner.hasNextLine()) {
                break;
            }
            String lastName = scanner.nextLine().trim();
            if (lastName.equalsIgnoreCase("exit")) {
                break;
            }
            System.out.println(pool.provideDriverInfo(lastName));
        }
    }
}
