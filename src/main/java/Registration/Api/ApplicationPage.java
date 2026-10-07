package Registration.Api;

import java.util.List;

public record ApplicationPage(List<ApplicationResponse> content, int number, int size,
                              long totalElements, int totalPages) { }
