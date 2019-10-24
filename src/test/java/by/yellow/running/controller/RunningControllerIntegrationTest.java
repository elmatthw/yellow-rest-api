package by.yellow.running.controller;

import by.yellow.running.RunningApplicationTests;
import by.yellow.running.model.Running;
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
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.MySQLContainer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {RunningApplicationTests.Initializer.class})
public class RunningControllerIntegrationTest {

    @ClassRule
    public static MySQLContainer mySQL = MySQLContainerConfig.getInstance();
    @Autowired
    private Environment environment;
    private String port;
    private TestRestTemplate testRestTemplate = new TestRestTemplate();

    @Test
    public void testExistingRunningByIdShouldReturn200() {
        port = environment.getProperty("local.server.port");
        ResponseEntity<Running> result = testRestTemplate
                .withBasicAuth("username", "password")
                .getForEntity("http://localhost:" + port +
                        "/runnings/2", Running.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testAllRunningShouldReturn200() {
        port = environment.getProperty("local.server.port");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("username", "password"));
        ResponseEntity<List<Running>> response = restTemplate.exchange(
                "http://localhost:" + port + "/runnings?page=1&limit=10&userId=2",
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
        port = environment.getProperty("local.server.port");
        String dateTimePattern="yyyy.MM.dd HH:mm";
        long id = 6;
        Running running = new Running(7.2,
                LocalDateTime.parse("2019.12.17 09:20", DateTimeFormatter.ofPattern(dateTimePattern)),
                LocalDateTime.parse("2019.12.17 11:00", DateTimeFormatter.ofPattern(dateTimePattern))
        );
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("HeaderName", "value");
        headers.add("Content-Type", "application/json");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        HttpEntity<Running> request = new HttpEntity<>(running, headers);

        restTemplate
                .getInterceptors()
                .add(new BasicAuthenticationInterceptor(
                        "username",
                        "password")
                );
        ResponseEntity<String> result = restTemplate
                .postForEntity("http://localhost:"
                                + port + "/runnings?userId=2",
                        request,
                        String.class);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    public void testPutRunning() {
        port = environment.getProperty("local.server.port");
        String dateTimePattern="yyyy.MM.dd HH:mm";
        long id = 2;
        Running running = new Running(id, 5,
                LocalDateTime.parse("2019.12.15 09:00", DateTimeFormatter.ofPattern(dateTimePattern)),
                LocalDateTime.parse("2019.12.15 10:40", DateTimeFormatter.ofPattern(dateTimePattern))
        );
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("HeaderName", "value");
        headers.add("Content-Type", "application/json");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        HttpEntity<Running> request = new HttpEntity<>(running, headers);
        restTemplate
                .getInterceptors()
                .add(new BasicAuthenticationInterceptor(
                        "username",
                        "password")
                );
        restTemplate
                .exchange("http://localhost:"
                                + port + "/runnings",
                        HttpMethod.PUT,
                        request,
                        Void.class);

        Running r = restTemplate.getForObject("http://localhost:"
                + port + "/runnings/2?userId=2", Running.class);
        assertEquals("2019-12-15T10:40", r.getFinishTime().toString());

    }

    @Test
    public void testDeleteRunning() {
        port = environment.getProperty("local.server.port");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate
                .getInterceptors()
                .add(new BasicAuthenticationInterceptor(
                        "username",
                        "password")
                );
        restTemplate
                .delete("http://localhost:" + port + "/runnings/4");
        ResponseEntity<List<Running>> response = restTemplate.exchange(
                "http://localhost:" + port + "/runnings?page=1&limit=10&userId=2",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        List<Running> runnings = response.getBody();
        assertEquals(3, runnings.size());
    }

    @Test
    public void testGetWeeklyReportForWeek200() {
        port = environment.getProperty("local.server.port");
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
        restTemplate
                .getInterceptors()
                .add(new BasicAuthenticationInterceptor(
                        "username",
                        "password")
                );
        ResponseEntity<WeeklyReportImpl> result = restTemplate
                .getForEntity("http://localhost:" + port +
                        "/users/2/weeklyReports/201949", WeeklyReportImpl.class);
        assertEquals((Double) 95.0, result.getBody().getAverageTime());
        assertEquals((Double) 12.2, result.getBody().getTotalDistance());
    }
}
