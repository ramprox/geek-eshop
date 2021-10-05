package ru.geekbrains;

import com.integralblue.log4jdbc.spring.Log4jdbcBeanPostProcessor;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.Lazy;

public class DevLog4jdbcBeanPostProcessor extends Log4jdbcBeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public DevLog4jdbcBeanPostProcessor() {
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return super.postProcessBeforeInitialization(bean, beanName);
    }
}
