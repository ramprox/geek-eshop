package ru.ramprox.service.order.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.ramprox.service.order.config.ControllerTestConfig;
import ru.ramprox.service.order.database.entity.Order;
import ru.ramprox.service.order.dto.ProductCartDto;
import ru.ramprox.service.order.dto.ProductDto;
import ru.ramprox.service.order.dto.UserReadOrderDto;
import ru.ramprox.service.order.service.cart.CartService;
import ru.ramprox.util.itest.DatabaseFacade;
import ru.ramprox.util.itest.annotations.ControllerITest;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.OidcLoginMutator;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOidcLogin;

@DisplayName("Интеграционные тесты для OrderController")
//@SpringBootTest
//@AutoConfigureWebTestClient
//@Import(ITestConfig.class)
//@ActiveProfiles("itest")
@ControllerITest(profiles = "itest", importConfigs = { ControllerTestConfig.class })
public class OrderControllerIT {

    @Autowired
    private DatabaseFacade db;

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CartService cartService;

    private ClientAndServer mockServer;

    private final Consumer<OidcIdToken.Builder> userToken =
            token -> token.claim("userId", 1L);

    private final OidcLoginMutator userOidcLogin = mockOidcLogin()
            .authorities(new SimpleGrantedAuthority("ROLE_USER"))
            .idToken(userToken);

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void beforeEach() {
        db.cleanDatabase();
        mockServer = ClientAndServer.startClientAndServer(8081);
    }

    @AfterEach
    public void afterEach() {
        mockServer.stop();
    }

    @DisplayName("Создание заказа")
    @Test
    public void createTest() throws JsonProcessingException {
        ProductDto productDto = new ProductDto(1L, "100");

        when(cartService.getProducts())
                .thenReturn(List.of(new ProductCartDto(productDto.getId(), 2)));

        mockServer
                .when(request()
                        .withMethod("GET")
                        .withPath("/api/v1/products/product/1"))
                        .respond(response()
                                .withBody(objectMapper.writeValueAsString(productDto))
                                .withContentType(APPLICATION_JSON)
                                .withStatusCode(200));

        webTestClient
                .mutateWith(userOidcLogin)
                .post()
                .uri("/order/new")
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserReadOrderDto.class)
                .value(userReadOrderDto -> {
                    assertThat(userReadOrderDto.getId()).isEqualTo(1L);
                    assertThat(new BigDecimal(userReadOrderDto.getPrice())).isEqualTo(new BigDecimal(200));
                    assertThat(userReadOrderDto.getStatus()).isEqualTo(Order.Status.NEW);
                });
    }

}
