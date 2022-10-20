package ru.ramprox.util.itest.config;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.sql.SQLException;

@TestConfiguration
@Import({ LiquibaseAutoConfiguration.LiquibaseConfiguration.class})
public class MigrationConfig {

    @Bean
    public Liquibase liquibaseMigration(SpringLiquibase liquibase) throws SQLException, LiquibaseException {
        return new Liquibase(liquibase.getChangeLog(),
                new ClassLoaderResourceAccessor(),
                new JdbcConnection(liquibase.getDataSource().getConnection()));
    }

}
