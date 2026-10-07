package Registration.Api;

import Registration.IO.FileParseException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {
    private final ApplicationService service;

    public ApplicationController(ApplicationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApplicationResponse> submit(
            @RequestHeader("Idempotency-Key") String requestKey, @RequestBody JsonNode body)
            throws FileParseException {
        ApplicationResponse response = service.submit(requestKey, body);
        return ResponseEntity.created(URI.create("/api/applications/" + response.id())).body(response);
    }

    @GetMapping("/{id}")
    public ApplicationResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping
    public ApplicationPage list(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "20") int size,
                                @RequestParam(required = false) ApplicationStatus status) {
        return service.list(page, size, status);
    }
}
