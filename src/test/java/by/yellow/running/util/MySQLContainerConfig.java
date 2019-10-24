package by.yellow.running.util;

import org.testcontainers.containers.MySQLContainer;

public class MySQLContainerConfig extends MySQLContainer<MySQLContainerConfig> {
    private static final String IMAGE_VERSION = "mysql:8.0.15";
    private static  MySQLContainerConfig container;

    public MySQLContainerConfig() {
        super(IMAGE_VERSION);
    }

    public static MySQLContainerConfig getInstance() {
        if (container == null)
            container = new MySQLContainerConfig();
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {

    }
}
