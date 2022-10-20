package ru.ramprox.service.picture.config;

import org.junit.jupiter.api.io.TempDir;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import ru.ramprox.service.picture.database.repository.PictureRepository;
import ru.ramprox.service.picture.service.PictureService;
import ru.ramprox.service.picture.service.PictureServiceImpl;
import ru.ramprox.util.querylogging.ConfigLog4jdbc;

import java.nio.file.Path;

@TestConfiguration
@Import(ConfigLog4jdbc.class)
public class ControllerTestConfig {

    @MockBean
    private ReactiveClientRegistrationRepository clientRegistrationRepository;

}
