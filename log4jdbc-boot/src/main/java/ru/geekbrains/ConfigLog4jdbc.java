package ru.geekbrains;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:exclude.properties")
public class ConfigLog4jdbc {

    @Bean
    public DevLog4jdbcBeanPostProcessor devLog4jdbcBeanPostProcessor() {
        return new DevLog4jdbcBeanPostProcessor();
    }
}
