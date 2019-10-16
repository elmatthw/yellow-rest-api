package by.yellow.running.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PasswordDoesntMatch extends RuntimeException {
    public PasswordDoesntMatch(String message) {
        super(message);
    }
}
