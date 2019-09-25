package by.yellow.running.security;

import by.yellow.running.entity.User;
import by.yellow.running.exception.PasswordDoesntMatch;
import by.yellow.running.exception.UsernameExistsException;

public interface IUserService {
    User registerNewAccount(User user) throws UsernameExistsException, PasswordDoesntMatch;
    boolean usernameExists(String username);
}
