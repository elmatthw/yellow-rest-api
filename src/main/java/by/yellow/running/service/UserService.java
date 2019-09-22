package by.yellow.running.service;

import by.yellow.running.entity.User;

import java.util.Optional;

public interface UserService {
    void save(User user);
    Optional<User> findByUsername(String username);
}
