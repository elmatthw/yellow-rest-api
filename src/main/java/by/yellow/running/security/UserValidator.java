package by.yellow.running.security;

import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    public boolean passwordMatches(String password, String passwordConfirm) {
        return password.equals(passwordConfirm);
    }
}
