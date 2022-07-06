package tk.artiquno.warehouse.management.configurations.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import tk.artiquno.warehouse.management.exceptions.StatusCodeException;
import tk.artiquno.warehouse.management.swagger.dto.ErrorResponse;

import javax.persistence.EntityNotFoundException;

/**
 * A "configuration" class holding exception handlers so Spring can respond
 * with the right HTTP code and a reason
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleException(EntityNotFoundException ex) {
        return new ErrorResponse()
                .message(ex.getMessage());
    }

    @ExceptionHandler(StatusCodeException.class)
    public ResponseEntity<ErrorResponse> handleException(StatusCodeException ex) {
        ErrorResponse errorResponse = new ErrorResponse()
                .message(ex.getMessage());
        return ResponseEntity.status(ex.getStatus())
                .body(errorResponse);
    }
}
