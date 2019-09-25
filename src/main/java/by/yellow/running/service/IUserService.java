package by.yellow.running.service;

import by.yellow.running.entity.User;
import by.yellow.running.exception.UsernameExistsException;

public interface IUserService {
    User registerNewAccount(User user) throws UsernameExistsException;
    boolean usernameExists(String username);
    /*void save(User user);
    Optional<User> findByUsername(String username);*/
}
