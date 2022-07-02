package tk.artiquno.warehouse.management.authentication;

import lombok.Getter;
import lombok.NoArgsConstructor;
import tk.artiquno.warehouse.management.exceptions.ConflictException;

@NoArgsConstructor
public class UsernameExistsException extends ConflictException {
    public UsernameExistsException(String message) {
        super(message);
    }
}
