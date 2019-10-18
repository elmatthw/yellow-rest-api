package by.yellow.running.service;

import by.yellow.running.exception.PasswordDoesntMatch;
import by.yellow.running.exception.UsernameExistsException;
import by.yellow.running.model.User;

import java.util.Collection;

public interface UserService {
    User registerNewAccount(User user) throws UsernameExistsException, PasswordDoesntMatch;
    boolean usernameExists(String username);
    User findById(Long id);
    User save(User user);
    void deleteById(Long id);
    Collection<User> findAll(int page, int amountOfElements);
}
