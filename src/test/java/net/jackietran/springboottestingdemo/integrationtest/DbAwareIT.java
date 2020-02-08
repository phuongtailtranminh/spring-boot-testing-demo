package net.jackietran.springboottestingdemo.integrationtest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public abstract class DbAwareIT {

    @BeforeAll
    static void beforeAll() {
        EmbeddedMysqlContainer.getInstance().start();
    }

}
