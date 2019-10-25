package by.yellow.running.controller;

import by.yellow.running.model.Running;
import by.yellow.running.model.User;
import by.yellow.running.model.WeeklyReportImpl;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class RunningControllerIntegrationTest extends ControllerTest{

    @Test
    public void testExistingRunningByIdShouldReturn200() {
        //Given
        addUsers("elmatthw8@gmail.com", "admin", bCryptPasswordEncoder.encode("admin"));
        User user = addUsers("email@email.com", "username", bCryptPasswordEncoder.encode("password"));

        addRunning(2.2, "2019.12.14 09:20", "2019.12.14 10:50", user);
        addRunning(5D, "2019.12.15 09:00", "2019.12.15 10:10", user);
        addRunning(10D,"2019.12.13 09:20", "2019.12.13 11:00", user);
        Running running = addRunning(7.5,"2019.12.18 12:20", "2019.12.18 13:40", user);
        ResponseEntity<Running> result = authorizedRestTemplate
                .getForEntity("http://localhost:" + port + "/runnings/" + running.getRunningId(), Running.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testAllRunningShouldReturn200() {
        //Given
        addUsers("elmatthw8@gmail.com", "admin", bCryptPasswordEncoder.encode("admin"));
        User user = addUsers("email@email.com", "username", bCryptPasswordEncoder.encode("password"));
        addRunning(2.2, "2019.12.14 09:20", "2019.12.14 10:50", user);
        addRunning(5D, "2019.12.15 09:00", "2019.12.15 10:10", user);
        addRunning(10D,"2019.12.13 09:20", "2019.12.13 11:00", user);
        addRunning(7.5,"2019.12.18 12:20", "2019.12.18 13:40", user);
        ResponseEntity<List<Running>> response = authorizedRestTemplate.exchange(
                "http://localhost:" + port + "/runnings?page=1&limit=10&userId=" + user.getUserId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        List<Running> runnings = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(4, runnings.size());
    }

    @Test
    @Transactional
    public void testPostRunning() {
        //Given
        addUsers("elmatthw8@gmail.com", "admin", bCryptPasswordEncoder.encode("admin"));
        User user = addUsers("email@email.com", "username", bCryptPasswordEncoder.encode("password"));
        addRunning(2.2, "2019.12.14 09:20", "2019.12.14 10:50", user);
        addRunning(5D, "2019.12.15 09:00", "2019.12.15 10:10", user);
        addRunning(10D,"2019.12.13 09:20", "2019.12.13 11:00", user);
        addRunning(7.5,"2019.12.18 12:20", "2019.12.18 13:40", user);

        Running running = new Running(7.2,
                LocalDateTime.parse("2019.12.17 09:20", DateTimeFormatter.ofPattern(dateTimePattern)),
                LocalDateTime.parse("2019.12.17 11:00", DateTimeFormatter.ofPattern(dateTimePattern))
        );
        // when
        ResponseEntity<Running> runningResponse = authorizedRestTemplate
                .exchange("http://localhost:"
                                + port + "/runnings?userId="+user.getUserId(),
                        HttpMethod.POST,
                        new HttpEntity<>(running, headers),
                        Running.class);
        // then
        ResponseEntity<Running> result = authorizedRestTemplate
                .getForEntity("http://localhost:"
                                + port + "/runnings/" + runningResponse.getBody().getRunningId() + "?userId="+user.getUserId(),
                        Running.class);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    public void testPutRunning() {
        //Given
        addUsers("elmatthw8@gmail.com", "admin", bCryptPasswordEncoder.encode("admin"));
        User user = addUsers("email@email.com", "username", bCryptPasswordEncoder.encode("password"));
        addRunning(2.2, "2019.12.14 09:20", "2019.12.14 10:50", user);
        addRunning(5D, "2019.12.15 09:00", "2019.12.15 10:10", user);
        addRunning(10D,"2019.12.13 09:20", "2019.12.13 11:00", user);
        Running runningToEdit = addRunning(7.5,"2019.12.18 12:20", "2019.12.18 13:40", user);
        runningToEdit.setFinishTime(LocalDateTime.parse("2019.12.15 10:40", DateTimeFormatter.ofPattern(dateTimePattern)));
        runningToEdit.setRunningId(runningToEdit.getRunningId());
        // when
        ResponseEntity<Running> runningResponse = authorizedRestTemplate
                .exchange("http://localhost:"
                                + port + "/runnings",
                        HttpMethod.PUT,
                        new HttpEntity<>(runningToEdit, headers),
                        Running.class);
        // then
        Running r = authorizedRestTemplate.getForObject("http://localhost:"
                + port + "/runnings/" + runningToEdit.getRunningId() + "?userId="+user.getUserId(), Running.class);
        assertEquals("2019-12-15T10:40", r.getFinishTime().toString());

    }

    @Test
    @Transactional
    public void testDeleteRunning() {
        //Given
        addUsers("elmatthw8@gmail.com", "admin", bCryptPasswordEncoder.encode("admin"));
        User user = addUsers("email@email.com", "username", bCryptPasswordEncoder.encode("password"));
        addRunning(2.2, "2019.12.14 09:20", "2019.12.14 10:50", user);
        addRunning(5D, "2019.12.15 09:00", "2019.12.15 10:10", user);
        addRunning(10D,"2019.12.13 09:20", "2019.12.13 11:00", user);
        Running running = addRunning(7.5,"2019.12.18 12:20", "2019.12.18 13:40", user);
        // when
        authorizedRestTemplate
                .delete("http://localhost:" + port + "/runnings/" + running.getRunningId());
        // then
        ResponseEntity<List<Running>> response = authorizedRestTemplate.exchange(
                "http://localhost:" + port + "/runnings?page=1&limit=10&userId="+ user.getUserId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        List<Running> runnings = response.getBody();
        assertEquals(3, runnings.size());
    }

    @Test
    public void testGetWeeklyReportForWeek200() {
        //Given
        addUsers("elmatthw8@gmail.com", "admin", bCryptPasswordEncoder.encode("admin"));
        User user = addUsers("email@email.com", "username", bCryptPasswordEncoder.encode("password"));
        addRunning(2.2, "2019.12.14 09:20", "2019.12.14 10:50", user);
        addRunning(5D, "2019.12.15 09:00", "2019.12.15 10:10", user);
        addRunning(10D,"2019.12.13 09:20", "2019.12.13 11:00", user);
        addRunning(7.5,"2019.12.18 12:20", "2019.12.18 13:40", user);
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        // when
        authorizedRestTemplate.setMessageConverters(messageConverters);
        ResponseEntity<WeeklyReportImpl> result = authorizedRestTemplate
                .getForEntity("http://localhost:" + port + "/users/" + user.getUserId() + "/weeklyReports/201949", WeeklyReportImpl.class);
        // then
        assertEquals((Double) 95.0, result.getBody().getAverageTime());
        assertEquals((Double) 12.2, result.getBody().getTotalDistance());
    }
}
