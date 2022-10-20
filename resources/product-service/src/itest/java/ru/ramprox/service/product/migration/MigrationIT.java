package ru.ramprox.service.product.migration;

import liquibase.Liquibase;
import liquibase.exception.LiquibaseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ramprox.service.product.config.ControllerTestConfig;
import ru.ramprox.util.itest.DatabaseFacade;
import ru.ramprox.util.itest.annotations.ControllerITest;
import ru.ramprox.util.itest.config.MigrationConfig;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест миграции")
@ControllerITest(profiles = { "itest", "migration" },
        importConfigs = { ControllerTestConfig.class, MigrationConfig.class })
public class MigrationIT {

    @Autowired
    private Liquibase liquibaseMigration;

    @Autowired
    private DatabaseFacade db;

    private static final String lastTagQuery = "SELECT TAG FROM DATABASECHANGELOG order by ORDEREXECUTED DESC LIMIT 1";

    @BeforeEach
    public void beforeEach() {
        db.dropAll();
    }

    @AfterEach
    public void afterEach() throws LiquibaseException {
        liquibaseMigration.rollback("db_init", "");
    }

    @DisplayName("Тест миграции")
    @Test
    public void migrationTest() throws LiquibaseException {
        liquibaseMigration.update("v-1.0", "");
        String lastTag = db.executeNativeQuery(lastTagQuery);
        assertThat(lastTag).isEqualTo("v-1.0");
    }

}
