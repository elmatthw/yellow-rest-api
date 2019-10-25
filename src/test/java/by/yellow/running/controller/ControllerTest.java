package by.yellow.running.controller;

import by.yellow.running.RunningApplicationTests;
import by.yellow.running.entity.RunningEntity;
import by.yellow.running.entity.UserEntity;
import by.yellow.running.mapper.RoleMapper;
import by.yellow.running.mapper.RunningMapper;
import by.yellow.running.mapper.UserMapper;
import by.yellow.running.model.Running;
import by.yellow.running.model.User;
import by.yellow.running.repository.RoleRepository;
import by.yellow.running.repository.RunningRepository;
import by.yellow.running.repository.UserRepository;
import by.yellow.running.util.MySQLContainerConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.MySQLContainer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {RunningApplicationTests.Initializer.class})
public class ControllerTest {
    @ClassRule
    public static MySQLContainer mySQL = MySQLContainerConfig.getInstance();
    static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    static final String dateTimePattern="yyyy.MM.dd HH:mm";
    @LocalServerPort
    String port;
    static RestTemplate authorizedRestTemplate = new RestTemplate();
    static {
        authorizedRestTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("admin", "admin"));
        authorizedRestTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }
    static RestTemplate unauthorizedRestTemplate = new RestTemplate();
    static MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
    static {
        headers.add("HeaderName", "value");
        headers.add("Content-Type", "application/json");
    }
    @Autowired
    UserRepository userRepository;
    @Autowired
    RunningRepository runningRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserMapper userMapper;
    @Autowired
    RunningMapper runningMapper;
    @Autowired
    RoleMapper roleMapper;

    public void deleteAllFromDatabase(){
        userRepository.deleteAll();
        runningRepository.deleteAll();
    }

    @Before
    public void beforeEachTest(){
        deleteAllFromDatabase();
    }

    @After
    public void afterEachTest(){
        deleteAllFromDatabase();
    }

    @Transactional
    public User addUsers(String email, String username, String password){
        User user = new User(email, username, password);
        user.setRoles(new HashSet<>(Collections.singletonList(roleMapper.entityToModel(roleRepository.findRoleByName("admin")))));
        UserEntity userEntity = userRepository.save(userMapper.modelToEntity(user));
        return userMapper.entityToModel(userEntity);
    }

    @Transactional
    public Running addRunning(Double distance, String startTime, String finishTime, User user){
        String dateTimePattern="yyyy.MM.dd HH:mm";
        RunningEntity runningEntity = runningRepository.save(
                        runningMapper.modelToEntity(
                                new Running(distance,
                                        LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern(dateTimePattern)),
                                        LocalDateTime.parse(finishTime, DateTimeFormatter.ofPattern(dateTimePattern))), user
                        ));
        return runningMapper.entityToModel(runningEntity);
    }
}
