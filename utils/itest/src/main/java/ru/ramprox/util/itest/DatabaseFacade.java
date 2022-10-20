package ru.ramprox.util.itest;

import java.util.List;

public interface DatabaseFacade {

    <T> EntityBuilder<T> persisted(EntityBuilder<T> builder);

    <T> T persist(T entity);

    <T> T persist(EntityBuilder<T> builder);

    <T> List<T> persist(EntityBuilder<T>... builders);

    <T> EntityBuilder<T> persistedOnce(EntityBuilder<T> builder);

    void cleanDatabase();

    void dropAll();

    <T> T executeNativeQuery(String query);

    <T> T find(Class<T> entityClass, Object id);

}
