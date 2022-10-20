package ru.ramprox.service.order.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    @Bean
    public WebClient productWebClient(@Value("${productService.baseUrl}") String productServiceBaseUrl) {
        return WebClient.builder().baseUrl(productServiceBaseUrl).build();
    }

}
