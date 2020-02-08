package net.jackietran.springboottestingdemo;

import net.jackietran.springboottestingdemo.integrationtest.EmbeddedMysqlContainer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(initializers = {SpringBootTestingDemoApplicationTests.Initializer.class})
@SpringBootTest
class SpringBootTestingDemoApplicationTests {

    @Test
    void contextLoads() {
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            EmbeddedMysqlContainer.getInstance().start();
        }
    }

}
