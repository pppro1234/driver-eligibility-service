package Registration.Api;

import org.springframework.http.HttpStatus;

class ApiException extends RuntimeException {
    final HttpStatus status;
    final String code;

    ApiException(HttpStatus status, String code, String message) {
        super(message);
        this.status = status;
        this.code = code;
    }
}
