package tk.artiquno.warehouse.management.exceptions;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends StatusCodeException {
    public ForbiddenException() {
        super(HttpStatus.FORBIDDEN);
    }

    public ForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
