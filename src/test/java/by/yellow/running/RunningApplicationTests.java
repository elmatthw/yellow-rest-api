package by.yellow.running;

import by.yellow.running.util.MySQLContainerConfig;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.MySQLContainer;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(initializers = {RunningApplicationTests.Initializer.class})
public class RunningApplicationTests {

    @ClassRule
    public static MySQLContainer mySQL = MySQLContainerConfig.getInstance();

    @Test
    public void contextLoads() {
    }

    public static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + mySQL.getJdbcUrl(),
                    "spring.datasource.password=" + mySQL.getPassword(),
                    "spring.datasource.username=" + mySQL.getUsername()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
