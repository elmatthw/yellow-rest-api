package by.yellow.running.service;

import by.yellow.running.entity.RoleEntity;
import by.yellow.running.entity.UserEntity;
import by.yellow.running.exception.PasswordDoesntMatch;
import by.yellow.running.exception.UserDoesNotExist;
import by.yellow.running.exception.UsernameExistsException;
import by.yellow.running.mapper.UserMapper;
import by.yellow.running.model.User;
import by.yellow.running.repository.RoleRepository;
import by.yellow.running.repository.UserRepository;
import by.yellow.running.security.UserValidator;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserValidator userValidator;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserValidator userValidator, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userValidator = userValidator;
        this.userMapper = userMapper;
    }

    @Transactional
    @Override
    public User registerNewAccount(User account) {
        if (!userValidator.passwordMatches(account.getPassword(), account.getPasswordConfirm())) {
            throw new PasswordDoesntMatch("Passwords doesn't match");
        }
        if (usernameExists(account.getUsername())) {
            throw new UsernameExistsException("There is an account with that username: " + account.getUsername());
        }
        UserEntity userEntity = userMapper.modelToEntity(account);
        RoleEntity roleEntity = roleRepository.findRoleByName("user");
        userEntity.setRoles(new HashSet<>(Collections.singletonList(roleEntity)));
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);
        return userMapper.entityToModel(userEntity);
    }

    @Override
    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public User findById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserDoesNotExist("User doesn't exist"));
        return userMapper.entityToModel(userEntity);
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = userRepository.save(userMapper.modelToEntity(user));
        return userMapper.entityToModel(userEntity);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Collection<User> findAll(int page, int amountOfElements) {
        Pageable pageable = PageRequest.of(page - 1, amountOfElements);
        return userRepository.findAll(pageable)
                .stream()
                .map(userMapper::entityToModel)
                .collect(Collectors.toList());
    }

}
