package ru.ramprox.util.itest;

import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DatabaseFacadeImpl implements DatabaseFacade {

    private Collection<String> dbTables = Collections.emptyList();

    private Collection<String> sequences = Collections.emptyList();

    protected static final String DELETE_ALL_FROM_TABLE_QUERY = "DELETE FROM %s";

    protected static final String RESET_SEQUENCE_TO_1_QUERY = "ALTER SEQUENCE %s RESTART WITH 1";

    protected static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS %s";

    protected static final String DROP_SEQUENCE_QUERY = "DROP SEQUENCE IF EXISTS %s";

    public void setTables(Collection<String> tables) {
        this.dbTables = tables;
    }

    public void setSequences(Collection<String> sequences) {
        this.sequences = sequences;
    }

    private final EntityManager entityManager;

    private final TransactionTemplate transactionTemplate;

    public DatabaseFacadeImpl(EntityManager entityManager,
                                     TransactionTemplate transactionTemplate) {
        this.entityManager = entityManager;
        this.transactionTemplate = transactionTemplate;
        this.transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    }

    @Override
    public <T> EntityBuilder<T> persisted(EntityBuilder<T> builder) {
        return () -> persist(builder.build());
    }

    @Override
    public <T> T persist(T entity) {
        return transactionTemplate.execute(status -> {
            entityManager.persist(entity);
            return entity;
        });
    }

    @Override
    public <T> T persist(EntityBuilder<T> builder) {
        return transactionTemplate.execute(status -> persist(builder.build()));
    }

    @SafeVarargs
    @Override
    public final <T> List<T> persist(EntityBuilder<T>... builders) {
        return transactionTemplate.execute(status ->
                Stream.of(builders)
                        .map(this::persist)
                        .collect(Collectors.toList()));
    }

    @Override
    public <T> EntityBuilder<T> persistedOnce(EntityBuilder<T> builder) {
        return new EntityBuilder<>() {

            private T entity;

            @Override
            public T build() {
                if (entity == null) {
                    entity = persisted(builder).build();
                }
                return entity;
            }
        };
    }

    @Override
    public void cleanDatabase() {
        transactionTemplate.execute(status -> {
            if(!dbTables.isEmpty()) {
                executeQuery(dbTables, DELETE_ALL_FROM_TABLE_QUERY);
            }
            if(!sequences.isEmpty()) {
                executeQuery(sequences, RESET_SEQUENCE_TO_1_QUERY);
            }
            return null;
        });
    }

    @Override
    public void dropAll() {
        transactionTemplate.execute(status -> {
            if(!dbTables.isEmpty()) {
                executeQuery(dbTables, DROP_TABLE_QUERY);
            }
            if(!sequences.isEmpty()) {
                executeQuery(sequences, DROP_SEQUENCE_QUERY);
            }
            return null;
        });
    }

    private void executeQuery(Collection<String> entityNames, String query) {
        entityNames.forEach(entityName ->
                entityManager
                        .createNativeQuery(String.format(query, entityName))
                        .executeUpdate());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T executeNativeQuery(String query) {
        return transactionTemplate.execute(status ->
                (T)entityManager
                        .createNativeQuery(query)
                        .getSingleResult());
    }

    @Override
    public <T> T find(Class<T> entityClass, Object id) {
        return entityManager.find(entityClass, id);
    }

}
