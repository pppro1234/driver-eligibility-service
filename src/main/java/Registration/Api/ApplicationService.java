package Registration.Api;

import Registration.Entity.Application;
import Registration.IO.FileParseException;
import Registration.IO.ProspectiveDriverFileParser;
import Registration.Validation.ValidationReport;
import Registration.Validation.Validator;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class ApplicationService {
    private final ApplicationRepository repository;
    private final ProspectiveDriverFileParser parser;
    private final Clock evaluationClock;

    public ApplicationService(ApplicationRepository repository, ProspectiveDriverFileParser parser,
                              Clock evaluationClock) {
        this.repository = repository;
        this.parser = parser;
        this.evaluationClock = evaluationClock;
    }

    @Transactional(rollbackFor = FileParseException.class)
    public ApplicationResponse submit(String requestKey, JsonNode body) throws FileParseException {
        if (requestKey == null || !requestKey.matches("[A-Za-z0-9._-]{1,100}")) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "INVALID_REQUEST",
                    "Idempotency-Key must contain 1-100 ASCII letters, digits, dot, underscore or hyphen");
        }
        // Always check input first; a malformed resubmission is still an invalid request.
        Application application = parser.parseApplication(body);
        if (repository.existsByRequestKey(requestKey)) {
            throw new ApiException(HttpStatus.CONFLICT, "DUPLICATE_REQUEST",
                    "This Idempotency-Key was already used; use the original application's Location");
        }
        Instant submitted = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);
        Clock fixedClock = Clock.fixed(evaluationClock.instant(), evaluationClock.getZone());
        ValidationReport report = Validator.defaultValidator(fixedClock).validate(application);
        StoredApplication stored = StoredApplication.from(application, requestKey, report,
                submitted, Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS), LocalDate.now(fixedClock));
        // Flush child collections here so a constraint violation fails the entire transaction.
        return repository.saveAndFlush(stored).toResponse();
    }

    @Transactional(readOnly = true)
    public ApplicationResponse get(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,
                "NOT_FOUND", "Application not found")).toResponse();
    }

    @Transactional(readOnly = true)
    public ApplicationPage list(int page, int size, ApplicationStatus status) {
        if (page < 0 || size < 1 || size > 100 || (long) page * size > Integer.MAX_VALUE) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "INVALID_REQUEST",
                    "page must be >= 0, size must be 1-100, and page * size must not exceed 2147483647");
        }
        PageRequest paging = PageRequest.of(page, size, Sort.by(
                Sort.Order.desc("submittedAt"), Sort.Order.desc("id")));
        Page<StoredApplication> result = status == null ? repository.findAll(paging)
                : repository.findByStatus(status, paging);
        return new ApplicationPage(result.getContent().stream().map(StoredApplication::toResponse).toList(),
                result.getNumber(), result.getSize(), result.getTotalElements(), result.getTotalPages());
    }
}
