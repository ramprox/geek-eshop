package ru.geekbrains;

import com.integralblue.log4jdbc.spring.Log4jdbcBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:log4jdbcBoot.properties")
public class ConfigLog4jdbc {

    @Bean
    public static Log4jdbcBeanPostProcessor devLog4jdbcBeanPostProcessor() {
        return new DevLog4jdbcBeanPostProcessor();
    }
}
