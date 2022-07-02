package tk.artiquno.warehouse.management.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
public class StatusCodeException extends RuntimeException {
    @Getter
    @Setter
    private HttpStatus status;

    public StatusCodeException(String message) {
        super(message);
        status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public StatusCodeException(HttpStatus status) {
        super();
        this.status = status;
    }

    public StatusCodeException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
