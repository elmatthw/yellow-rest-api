package by.yellow.running.controller;

import by.yellow.running.RunningApplicationTests;
import by.yellow.running.model.User;
import by.yellow.running.util.MySQLContainerConfig;
import by.yellow.running.util.WeeklyReportImpl;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.MySQLContainer;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {RunningApplicationTests.Initializer.class})
public class UserControllerIntegrationTest {

    @ClassRule
    public static MySQLContainer mySQL = MySQLContainerConfig.getInstance();
    @Autowired
    private Environment environment;
    private String port;
    private TestRestTemplate testRestTemplate = new TestRestTemplate();

    @Test
    public void testNotExistingPersonByIdShouldReturn400() {
        port = environment.getProperty("local.server.port");
        ResponseEntity<User> result = testRestTemplate
                .withBasicAuth("username", "password")
                .getForEntity("http://localhost:" + port +
                        "/users/3", User.class);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertNull(result.getBody().getUsername());
    }

    @Test
    public void testNotExistingMethodByIdShouldReturn405() {
        port = environment.getProperty("local.server.port");
        ResponseEntity<String> result = testRestTemplate.getForEntity("http://localhost:" + port +
                "/registration", String.class);
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, result.getStatusCode());
    }

    @Test
    public void testGetAllUsersShouldReturn200() {
        port = environment.getProperty("local.server.port");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("username", "password"));
        ResponseEntity<List<User>> response = restTemplate.exchange(
                "http://localhost:" + port + "/users?page=1&limit=10",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        List<User> users = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, users.size());
    }

    @Test
    public void testGetUserShouldReturn200() {
        port = environment.getProperty("local.server.port");
        ResponseEntity<User> result = testRestTemplate
                .withBasicAuth("username", "password")
                .getForEntity("http://localhost:" + port +
                        "/users/1", User.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("admin", result.getBody().getUsername());
    }

    @Test
    public void testDeleteUserShouldReturn200() {
        port = environment.getProperty("local.server.port");
        testRestTemplate
                .withBasicAuth("username", "password")
                .delete("http://localhost:" + port +
                        "/users/2");
        ResponseEntity<User> result = testRestTemplate
                .withBasicAuth("username", "password")
                .getForEntity("http://localhost:" + port +
                        "/users/2", User.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testGetWeeklyReportShouldReturn200() {
        port = environment.getProperty("local.server.port");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("username", "password"));
        ResponseEntity<List<WeeklyReportImpl>> response = restTemplate.exchange(
                "http://localhost:" + port + "/users/2/weeklyReports?page=1&limit=10",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        List<WeeklyReportImpl> weeklyReports = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, weeklyReports.size());
    }

    @Test
    public void testGetWeeklyReportFromWeekToWeek200() {
        port = environment.getProperty("local.server.port");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("username", "password"));
        ResponseEntity<List<WeeklyReportImpl>> response = restTemplate.exchange(
                "http://localhost:" + port + "/users/2/weeklyReports/weeks?from=201945&to=201949&page=1&limit=10",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        List<WeeklyReportImpl> weeklyReports = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, weeklyReports.size());
    }

    @Test
    public void testRegisterNewUser() {
        port = environment.getProperty("local.server.port");
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("HeaderName", "value");
        headers.add("Content-Type", "application/json");
        User user = new User("newUser@email.com", "newUsername", "password", "password");
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        restTemplate.exchange(
                "http://localhost:" + port + "/registration",
                HttpMethod.POST,
                request,
                String.class);
        ResponseEntity<User> result = testRestTemplate
                .withBasicAuth("newUsername", "password")
                .getForEntity("http://localhost:" + port +
                        "/users/2", User.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
