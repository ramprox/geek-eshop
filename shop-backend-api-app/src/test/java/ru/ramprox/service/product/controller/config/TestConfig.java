//package ru.ramprox.service1.product.controller.config;
//
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.beans.factory.config.CustomScopeConfigurer;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Import;
//import org.springframework.context.annotation.Primary;
//import org.springframework.context.support.SimpleThreadScope;
//import ru.geekbrains.ConfigLog4jdbc;
//
//import static org.mockito.Mockito.mock;
//
//@TestConfiguration
//@Import(ConfigLog4jdbc.class)
//public class TestConfig {
//
//    @Bean
//    public CustomScopeConfigurer customScopeConfigurer() {
//        CustomScopeConfigurer configurer = new CustomScopeConfigurer();
//        configurer.addScope("session", new SimpleThreadScope());
//        return configurer;
//    }
//
//    @Bean
//    @Primary
//    public CachingConnectionFactory rabbitAdmin() {
//        return mock(CachingConnectionFactory.class);
//    }
//
////    @Bean
////    public Saver saver() {
////        return new Saver();
////    }
//
//}
