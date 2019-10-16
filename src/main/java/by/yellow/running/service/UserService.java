package by.yellow.running.service;

import by.yellow.running.entity.UserEntity;
import by.yellow.running.exception.PasswordDoesntMatch;
import by.yellow.running.exception.UsernameExistsException;
import by.yellow.running.mapper.RoleMapper;
import by.yellow.running.mapper.UserMapper;
import by.yellow.running.model.Role;
import by.yellow.running.model.User;
import by.yellow.running.repository.RoleRepository;
import by.yellow.running.repository.UserRepository;
import by.yellow.running.security.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
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
        if (!userValidator.passwordMatches(account.getPassword(), account.getPasswordConfirm())) {
            throw new PasswordDoesntMatch("Passwords doesn't match");
        }
        if (usernameExists(account.getUsername())) {
            throw new UsernameExistsException("There is an account with that username: " + account.getUsername());
        }
        Set<Role> roles = new HashSet<>();
        roles.add(RoleMapper.INSTANCE.entityToModel(roleRepository.findRoleByName("user")));
        account.setRoles(roles);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        UserEntity accountEntity = userRepository.save(UserMapper.INSTANCE.modelToEntity(account));
        return UserMapper.INSTANCE.entityToModel(accountEntity);
    }

    @Override
    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public User findById(Long id) {
        return UserMapper.INSTANCE.entityToModel(userRepository.findById(id).get());
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = userRepository.save(UserMapper.INSTANCE.modelToEntity(user));
        return UserMapper.INSTANCE.entityToModel(userEntity);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Collection<User> findAll() {
        Collection<User> users = null;
        for (UserEntity userEntity: userRepository.findAll()) {
            users.add(UserMapper.INSTANCE.entityToModel(userEntity));
        }
        return users;
    }
}
