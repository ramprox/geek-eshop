package ru.geekbrains.controller.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.SimpleThreadScope;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class TestConfig {
    @Bean
    public CustomScopeConfigurer customScopeConfigurer() {
        CustomScopeConfigurer configurer = new CustomScopeConfigurer();
        configurer.addScope("session", new SimpleThreadScope());
        return configurer;
    }

    @Bean
    @Primary
    public CachingConnectionFactory rabbitAdmin() {
        return mock(CachingConnectionFactory.class);
    }
}
