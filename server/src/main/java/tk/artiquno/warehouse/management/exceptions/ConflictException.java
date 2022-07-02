package tk.artiquno.warehouse.management.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ConflictException extends StatusCodeException {
    public ConflictException() {
        super(HttpStatus.CONFLICT);
    }

    public ConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
