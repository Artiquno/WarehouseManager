package tk.artiquno.warehouse.management.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends StatusCodeException {
    public BadRequestException() {
        super(HttpStatus.BAD_REQUEST);
    }

    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
