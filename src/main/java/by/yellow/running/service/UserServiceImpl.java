/*
package by.yellow.running.service;

import by.yellow.running.entity.Role;
import by.yellow.running.entity.User;
import by.yellow.running.repository.RoleRepository;
import by.yellow.running.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    */
/*@Autowired
    private BCryptPasswordEncoder encoder;*//*


    @Override
    public void save(User user) {
        */
/*user.setPassword(encoder.encode(user.getPassword()));*//*

        user.setRoles(new HashSet<>((Collection<Role>) roleRepository.findAll()));
        userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
*/
