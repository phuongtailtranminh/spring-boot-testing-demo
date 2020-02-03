package net.jackietran.springboottestingdemo.integrationtest.taxcalculator;

import lombok.extern.slf4j.Slf4j;
import net.jackietran.springboottestingdemo.integrationtest.EmbeddedPostgresqlContainer;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class TaxCalculatorRepositoryTest {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = EmbeddedPostgresqlContainer.getInstance();

    @Test
    public void doNothing() {
        log.info("Done");
    }

}
