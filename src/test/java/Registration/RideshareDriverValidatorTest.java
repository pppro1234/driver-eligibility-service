package Registration;

import Registration.Pool.DriverPool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RideshareDriverValidatorTest {
    @TempDir Path directory;
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream output;

    @BeforeEach void setUp() {
        DriverPool.getInstance().clear();
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
        System.setIn(new ByteArrayInputStream("Demo\nexit\n".getBytes(StandardCharsets.UTF_8)));
    }

    @AfterEach void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
        DriverPool.getInstance().clear();
    }

    private String application() {
        return """
                {"driver":{"firstName":"Alex","lastName":"Demo","birthDate":"1990-05-20"},
                 "license":{"number":"DEMO-LICENSE-1","firstName":"Alex","lastName":"Demo",
                   "address":"123 Example Street","birthDate":"1990-05-20","countryAndState":"US, MA",
                   "issuanceDate":"2020-01-01","expirationDate":"2028-12-31"},
                 "vehicles":[{"make":"Honda","model":"Accord","year":2023,"owner":"Alex Demo","plate":"DEMO-PLATE-1"}],
                 "insurance":{"owner":{"firstName":"Alex","lastName":"Demo"},"expirationDate":"2028-12-31"},
                 "driverHistory":[],"vehicleHistory":[]}
                """;
    }

    @Test void realImportRulesAndPoolWorkAtRepeatableDate() throws Exception {
        String accepted = application();
        String merged = accepted.replace("DEMO-PLATE-1", "DEMO-PLATE-2");
        String separate = accepted.replace("DEMO-LICENSE-1", "DEMO-LICENSE-2");
        String rejected = accepted.replace("1990-05-20", "2010-05-20");
        Path file = directory.resolve("drivers.json");
        Files.writeString(file, "[" + String.join(",", accepted, accepted, merged, separate, rejected) + "]");

        RideshareDriverValidator.main(new String[]{file.toString(), "--date", "2026-09-22"});

        String text = output.toString(StandardCharsets.UTF_8);
        assertTrue(text.contains("Evaluation date (UTC): 2026-09-22"), text);
        assertTrue(text.contains("Prospective drivers loaded: 5"), text);
        assertTrue(text.contains("Accepted applications: 4"), text);
        assertTrue(text.contains("Rejected applications: 1"), text);
        assertTrue(text.contains("New driver entries: 2"), text);
        assertTrue(text.contains("Existing drivers with added vehicles: 1"), text);
        assertTrue(text.contains("Duplicate driver/vehicle applications: 1"), text);
        assertTrue(text.contains("Demo, Alex"), text);
        assertTrue(text.contains("DEMO-PLATE-2"), text);
    }

    @Test void malformedHistoryIsReportedWithoutAcceptingDrivers() throws Exception {
        Path file = directory.resolve("invalid.json");
        Files.writeString(file, "[" + application().replace("\"driverHistory\":[]", "\"driverHistory\":{}") + "]");
        RideshareDriverValidator.main(new String[]{file.toString(), "--date", "2026-09-22"});
        assertTrue(output.toString(StandardCharsets.UTF_8).contains("Invalid file format:"));
    }

    @Test void impossibleDemoDateIsRejected() {
        RideshareDriverValidator.main(new String[]{"unused.json", "--date", "2026-02-30"});
        assertTrue(output.toString(StandardCharsets.UTF_8).contains("Invalid date:"));
    }

    @Test void missingDemoDateValueIsReported() {
        RideshareDriverValidator.main(new String[]{"unused.json", "--date"});
        assertTrue(output.toString(StandardCharsets.UTF_8).contains("--date requires YYYY-MM-DD"));
    }

    @Test void redirectedInputMayEndWithoutExitCommand() throws Exception {
        Path file = directory.resolve("empty.json");
        Files.writeString(file, "[]");
        System.setIn(new ByteArrayInputStream(new byte[0]));
        RideshareDriverValidator.main(new String[]{file.toString()});
        assertTrue(output.toString(StandardCharsets.UTF_8).contains("Validation complete."));
    }
}
