package net.jackietran.springboottestingdemo.integrationtest;

import org.testcontainers.containers.PostgreSQLContainer;

public class EmbeddedPostgresqlContainer extends PostgreSQLContainer<EmbeddedPostgresqlContainer> {

    private static final String IMAGE_VERSION = "postgres:11.1";
    private static EmbeddedPostgresqlContainer container;

    EmbeddedPostgresqlContainer() {
        super(IMAGE_VERSION);
    }

    public static EmbeddedPostgresqlContainer getInstance() {
        if (container == null)
            container = new EmbeddedPostgresqlContainer();
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
