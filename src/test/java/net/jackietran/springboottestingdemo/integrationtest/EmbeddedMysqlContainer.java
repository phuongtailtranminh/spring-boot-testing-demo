package net.jackietran.springboottestingdemo.integrationtest;

import org.testcontainers.containers.MySQLContainer;

public class EmbeddedMysqlContainer extends MySQLContainer<EmbeddedMysqlContainer> {

    private static final String IMAGE_VERSION = "mysql:5";
    private static EmbeddedMysqlContainer container;

    EmbeddedMysqlContainer() {
        super(IMAGE_VERSION);
    }

    public static EmbeddedMysqlContainer getInstance() {
        if (container == null)
            container = new EmbeddedMysqlContainer();
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

}
