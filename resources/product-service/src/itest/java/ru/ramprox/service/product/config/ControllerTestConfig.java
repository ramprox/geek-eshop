package ru.ramprox.service.product.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import ru.ramprox.util.querylogging.ConfigLog4jdbc;

@TestConfiguration
@Import(ConfigLog4jdbc.class)
public class ControllerTestConfig {

    @MockBean
    private ReactiveClientRegistrationRepository clientRegistrationRepository;

}
