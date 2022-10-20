package ru.ramprox.service.product.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.ramprox.service.product.config.ControllerTestConfig;
import ru.ramprox.service.product.database.entity.Brand;
import ru.ramprox.service.product.dto.BrandDto;
import ru.ramprox.service.product.util.BrandBuilder;
import ru.ramprox.util.itest.DatabaseFacade;
import ru.ramprox.util.itest.annotations.ControllerITest;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static ru.ramprox.service.product.util.BrandBuilder.brand;

@DisplayName("Интеграционные тесты для BrandController")
@ControllerITest(profiles = "itest", importConfigs = { ControllerTestConfig.class })
public class BrandControllerIT {

    @Autowired
    private DatabaseFacade db;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    public void beforeEach() {
        db.cleanDatabase();
    }

    @DisplayName("Извлечение всех брэндов")
    @Test
    public void getBrandsTest() {
        List<BrandDto> expectedBrands = List.of(
                new BrandDto(2L, "Brand1"),
                new BrandDto(3L, "Brand2"),
                new BrandDto(1L, "Brand3")
        );
        BrandBuilder brand = brand();
        db.persist(
                brand.withName(expectedBrands.get(2).getName()),
                brand.withName(expectedBrands.get(0).getName()),
                brand.withName(expectedBrands.get(1).getName())
        );

        webTestClient
                .get().uri("/brand/all")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBodyList(BrandDto.class)
                .isEqualTo(expectedBrands);
    }

    @DisplayName("Извлечение брэнда по id")
    @Test
    public void getBrandByIdTest() {
        BrandDto expectedBrandDto = new BrandDto(1L, "Brand1");

        db.persist(brand().withName(expectedBrandDto.getName()));

        webTestClient
                .get().uri("/brand/1")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(BrandDto.class).isEqualTo(expectedBrandDto);
    }

    @DisplayName("Создание брэнда")
    @Test
    @WithMockUser(roles = "ADMIN")
    public void createTest() {
        BrandDto brandDto = new BrandDto("Brand1");

        webTestClient
                .post().uri("/brand/new")
                .contentType(APPLICATION_JSON)
                .bodyValue(brandDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BrandDto.class)
                .value(resultDto -> assertThat(resultDto.getId()).isEqualTo(1L));
    }

    @DisplayName("Изменение брэнда")
    @Test
    @WithMockUser(roles = "ADMIN")
    public void editBrandTest() {
        Brand brand = db.persist(brand().withName("Brand1"));
        BrandDto brandDto = new BrandDto(brand.getId(), "Brand2");

        webTestClient
                .put().uri("/brand/edit").contentType(APPLICATION_JSON)
                .bodyValue(brandDto)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(BrandDto.class).isEqualTo(brandDto);
    }

    @DisplayName("Удаление брэнда")
    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteBrandTest() {
        db.persist(brand().withName("Brand1"));

        webTestClient
                .delete().uri("/brand/delete/1")
                .exchange()
                .expectStatus().isOk();
    }

    @DisplayName("Создание брэнда с некорректным brandDto " +
            "должен возвращать статус BAD_REQUEST и информацию об ошибках")
    @ParameterizedTest
    @MethodSource("invalidBrandDtos")
    @WithMockUser(roles = "ADMIN")
    public void createWithInvalidBrandDtoTest(BrandDto brandDto) {
        webTestClient
                .post().uri("/brand/new")
                .contentType(APPLICATION_JSON)
                .bodyValue(brandDto)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(new ParameterizedTypeReference<Map<String, String>>() { })
                .isEqualTo(Map.of("name", "не должно быть пустым"));
    }

    @DisplayName("Изменение брэнда c несуществующим id " +
            "должно вернуть статус 404 с сообщением об ошибке")
    @Test
    @WithMockUser(roles = "ADMIN")
    public void editNonCorrectBrandTest() {
        db.persist(brand().withName("Brand1"));
        BrandDto brandDto = new BrandDto(2L, "Brand2");

        webTestClient
                .put().uri("/brand/edit").contentType(APPLICATION_JSON)
                .bodyValue(brandDto)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(String.class).isEqualTo("Брэнд с id = 2 не найден");
    }

    @DisplayName("Удаление брэнда c несуществующим id " +
            "должно вернуть статус 404 с сообщением об ошибке")
    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteBrandWithNotExistIdTest() {
        webTestClient
                .delete().uri("/brand/delete/1")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(String.class).isEqualTo("Брэнд с id = 1 не найден");
    }

    private static Stream<BrandDto> invalidBrandDtos() {
        return Stream.of(
                new BrandDto(null),
                new BrandDto(""),
                new BrandDto("   "));
    }

}
