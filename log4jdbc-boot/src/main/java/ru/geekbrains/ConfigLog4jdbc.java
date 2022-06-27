package ru.geekbrains;

import com.integralblue.log4jdbc.spring.Log4jdbcAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = Log4jdbcAutoConfiguration.class)
public class ConfigLog4jdbc {

    @Bean
    public static DevLog4jdbcBeanPostProcessor devLog4jdbcBeanPostProcessor() {
        return new DevLog4jdbcBeanPostProcessor();
    }
}
