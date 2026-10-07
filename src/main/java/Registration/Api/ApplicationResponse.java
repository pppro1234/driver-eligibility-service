package Registration.Api;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ApplicationResponse(UUID id, ApplicationStatus status, List<String> failureReasons,
                                  Instant submittedAt, Instant evaluatedAt, LocalDate evaluationDate,
                                  String ruleVersion, ApplicationSnapshot application) { }
