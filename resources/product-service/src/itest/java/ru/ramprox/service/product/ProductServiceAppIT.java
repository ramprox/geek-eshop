package ru.ramprox.service.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import ru.ramprox.service.product.config.ControllerTestConfig;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("itest")
@DisplayName("Тест запуска приложения")
@Import(ControllerTestConfig.class)
public class ProductServiceAppIT {

    @Autowired
    private ApplicationContext applicationContext;

    @DisplayName("Загрузка контекста")
    @Test
    public void contextLoads() {
        assertNotNull(applicationContext);
    }

}
