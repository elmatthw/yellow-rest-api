package by.yellow.running.controller;

import by.yellow.running.model.User;
import by.yellow.running.model.WeeklyReportImpl;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserControllerIntegrationTest extends ControllerTest{

    @Test
    public void testNotExistingPersonByIdShouldReturn400() {
        // given
        User user1 = addUsers("elmatthw8@gmail.com", "admin", bCryptPasswordEncoder.encode("admin"));
        User user2 = addUsers("email@email.com", "username", bCryptPasswordEncoder.encode("password"));
        authorizedRestTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("admin", "admin"));
        // when
        try {
            ResponseEntity<User> result = authorizedRestTemplate
                    .getForEntity("http://localhost:" + port + "/users/" + user1.getUserId() + user2.getUserId(), User.class);
        }
        // then
        catch (HttpClientErrorException ex) {
            assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        }
    }

    @Test
    public void testGetAllUsersShouldReturn200() {
        // given
        addUsers("elmatthw8@gmail.com", "admin", bCryptPasswordEncoder.encode("admin"));
        addUsers("email@email.com", "username", bCryptPasswordEncoder.encode("password"));
        authorizedRestTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("admin", "admin"));
        // when
        ResponseEntity<List<User>> response = authorizedRestTemplate.exchange(
                "http://localhost:" + port + "/users?page=1&limit=10",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        List<User> users = response.getBody();
       // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, users.size());
    }

    @Test
    public void testGetUserShouldReturn200() {
        // given
        addUsers("elmatthw8@gmail.com", "admin", bCryptPasswordEncoder.encode("admin"));
        User user = addUsers("email@email.com", "username", bCryptPasswordEncoder.encode("password"));
        authorizedRestTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("admin", "admin"));
        // when
        ResponseEntity<User> result = authorizedRestTemplate
                .getForEntity("http://localhost:" + port + "/users/" + user.getUserId(), User.class);
        // then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("username", result.getBody().getUsername());
    }

    @Test
    public void testDeleteUserShouldReturn200() {
        // given
        User admin = addUsers("elmatthw8@gmail.com", "admin", bCryptPasswordEncoder.encode("admin"));
        User user = addUsers("email@email.com", "username", bCryptPasswordEncoder.encode("password"));
        authorizedRestTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("admin", "admin"));
        // when
        authorizedRestTemplate
                .delete("http://localhost:" + port + "/users/" + user.getUserId());
        try {
            authorizedRestTemplate
                    .getForEntity("http://localhost:" + port +
                            "/users/" + user.getUserId(), User.class);
        }
        // then
        catch (HttpClientErrorException ex){
            assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        }
    }

    @Test
    public void testGetWeeklyReportShouldReturn200() {
        // given
        addUsers("elmatthw8@gmail.com", "admin", bCryptPasswordEncoder.encode("admin"));
        User user = addUsers("email@email.com", "username", bCryptPasswordEncoder.encode("password"));
        authorizedRestTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("admin", "admin"));
        addRunning(2.2, "2019.12.14 09:20", "2019.12.14 10:50", user);
        addRunning(5D, "2019.12.15 09:00", "2019.12.15 10:10", user);
        addRunning(10D,"2019.12.13 09:20", "2019.12.13 11:00", user);
        addRunning(7.5,"2019.12.18 12:20", "2019.12.18 13:40", user);
        // when
        ResponseEntity<List<WeeklyReportImpl>> response = authorizedRestTemplate.exchange(
                "http://localhost:" + port + "/users/" + user.getUserId() + "/weeklyReports?page=1&limit=10",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        // then
        List<WeeklyReportImpl> weeklyReports = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, weeklyReports.size());
    }

    @Test
    public void testGetWeeklyReportFromWeekToWeek200() {
        // given
        addUsers("elmatthw8@gmail.com", "admin", bCryptPasswordEncoder.encode("admin"));
        User user = addUsers("email@email.com", "username", bCryptPasswordEncoder.encode("password"));
        authorizedRestTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("admin", "admin"));
        addRunning(2.2, "2019.12.14 09:20", "2019.12.14 10:50", user);
        addRunning(5D, "2019.12.15 09:00", "2019.12.15 10:10", user);
        addRunning(10D,"2019.12.13 09:20", "2019.12.13 11:00", user);
        addRunning(7.5,"2019.12.18 12:20", "2019.12.18 13:40", user);
        // when
        ResponseEntity<List<WeeklyReportImpl>> response = authorizedRestTemplate.exchange(
                "http://localhost:" + port + "/users/" + user.getUserId() + "/weeklyReports/weeks?from=201945&to=201949&page=1&limit=10",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        // then
        List<WeeklyReportImpl> weeklyReports = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, weeklyReports.size());
    }

    @Test
    public void testRegisterNewUser() {
        //given
        addUsers("elmatthw8@gmail.com", "admin", bCryptPasswordEncoder.encode("admin"));
        addUsers("email@email.com", "username", bCryptPasswordEncoder.encode("password"));
        authorizedRestTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("admin", "admin"));
        User user = new User("newUser@email.com", "newUsername", "password", "password");
        //when
        ResponseEntity<User> response = unauthorizedRestTemplate.exchange(
                "http://localhost:" + port + "/registration",
                HttpMethod.POST,
                new HttpEntity<>(user, headers),
                User.class);
        //then
        ResponseEntity<User> result = authorizedRestTemplate
                .getForEntity("http://localhost:" + port +
                        "/users/" + response.getBody().getUserId(), User.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
