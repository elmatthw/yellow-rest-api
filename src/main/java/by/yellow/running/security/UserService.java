package by.yellow.running.security;

import by.yellow.running.entity.Role;
import by.yellow.running.entity.User;
import by.yellow.running.exception.PasswordDoesntMatch;
import by.yellow.running.exception.UsernameExistsException;
import by.yellow.running.repository.RoleRepository;
import by.yellow.running.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements IUserService {
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public RoleRepository roleRepository;
    @Autowired
    public PasswordEncoder passwordEncoder;
    @Autowired
    public UserValidator userValidator;
    @Transactional
    @Override
    public User registerNewAccount(User account) throws UsernameExistsException, PasswordDoesntMatch {
        if (usernameExists(account.getUsername())) {
            throw new UsernameExistsException("There is an account with that username: " + account.getUsername());
        }
        if (!userValidator.passwordMatches(account.getPassword(), account.getPasswordConfirm())) {
            throw new PasswordDoesntMatch("Passwords doesn't match");
        }
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findRolesByName("user").get());
        account.setRoles(roles);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return userRepository.save(account);
    }

    @Override
    public boolean usernameExists(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent();
    }
}
