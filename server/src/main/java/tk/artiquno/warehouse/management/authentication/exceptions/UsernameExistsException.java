package tk.artiquno.warehouse.management.authentication.exceptions;

import lombok.NoArgsConstructor;
import tk.artiquno.warehouse.management.exceptions.ConflictException;

@NoArgsConstructor
public class UsernameExistsException extends ConflictException {
    public UsernameExistsException(String message) {
        super(message);
    }
}
