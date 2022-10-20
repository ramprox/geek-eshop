package ru.ramprox.util.itest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import ru.ramprox.util.itest.DatabaseFacade;
import ru.ramprox.util.itest.DatabaseFacadeImpl;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@TestConfiguration
@Import(TestDatasourceConfig.class)
public class DataJpaTestContainersConfig {

    @Bean
    public DatabaseFacade db(EntityManager entityManager,
                             TransactionTemplate transactionTemplate,
                             @Value("${autoclean.tables:#{null}}") Optional<List<String>> tables,
                             @Value("${autoclean.sequences:#{null}}")Optional<List<String>> sequences) {
        DatabaseFacadeImpl db = new DatabaseFacadeImpl(entityManager, transactionTemplate);
        tables.ifPresent(db::setTables);
        sequences.ifPresent(db::setSequences);
        return db;
    }

}
