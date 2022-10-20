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
import ru.ramprox.service.product.database.entity.Category;
import ru.ramprox.service.product.dto.CategoryDto;
import ru.ramprox.util.itest.DatabaseFacade;
import ru.ramprox.util.itest.annotations.ControllerITest;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static ru.ramprox.service.product.util.CategoryBuilder.category;

@DisplayName("Интеграционные тесты для CategoryController")
@ControllerITest(profiles = "itest", importConfigs = { ControllerTestConfig.class })
public class CategoryControllerIT {

    @Autowired
    private DatabaseFacade db;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    public void beforeEach() {
        db.cleanDatabase();
    }

    @DisplayName("Извлечение категории по id")
    @Test
    public void getCategoryByIdTest() {
        CategoryDto expectedCategoryDto = new CategoryDto(1L, "Category1");

        db.persist(category().withName(expectedCategoryDto.getName()));

        webTestClient
                .get().uri("/category/1")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CategoryDto.class).isEqualTo(expectedCategoryDto);
    }

    @DisplayName("Извлечение всех категорий")
    @Test
    public void getCategoriesTest() {
        CategoryDto expectedCategory1 = new CategoryDto(1L, "Category3");
        CategoryDto expectedCategory2 = new CategoryDto(2L, "Category1");
        CategoryDto expectedCategory3 = new CategoryDto(3L, "Category2");

        db.persist(
                category().withName(expectedCategory1.getName()),
                category().withName(expectedCategory2.getName()),
                category().withName(expectedCategory3.getName())
        );

        webTestClient
                .get().uri("/category/all")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBodyList(CategoryDto.class)
                .isEqualTo(List.of(expectedCategory2, expectedCategory3, expectedCategory1));
    }

    @DisplayName("Создание категории")
    @Test
    @WithMockUser(roles = "ADMIN")
    public void createTest() {
        CategoryDto categoryDto = new CategoryDto("Category1");

        webTestClient
                .post().uri("/category/new").contentType(APPLICATION_JSON)
                .bodyValue(categoryDto)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(CategoryDto.class)
                .value(actualCategory -> assertThat(actualCategory.getId()).isEqualTo(1L));
    }

    @DisplayName("Изменение категории")
    @Test
    @WithMockUser(roles = "ADMIN")
    public void editCategoryTest() {
        Category category = db.persist(category().withName("Category1"));
        CategoryDto categoryDto = new CategoryDto(category.getId(), "Category2");

        webTestClient
                .put().uri("/category/edit")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .bodyValue(categoryDto)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(CategoryDto.class).isEqualTo(categoryDto);
    }

    @DisplayName("Удаление категории")
    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteCategoryTest() {
        db.persist(category().withName("Category1"));

        webTestClient
                .delete().uri("/category/delete/1")
                .exchange()
                .expectStatus().isOk();
    }

    @DisplayName("Создание категории с некорректным categoryDto " +
            "должен возвращать статус BAD_REQUEST и информацию об ошибках")
    @ParameterizedTest
    @MethodSource("invalidCategoryDtos")
    @WithMockUser(roles = "ADMIN")
    public void createWithInvalidCategoryDtoTest(CategoryDto categoryDto) {
        webTestClient
                .post().uri("/category/new").contentType(APPLICATION_JSON)
                .bodyValue(categoryDto)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(new ParameterizedTypeReference<Map<String, String>>() { })
                .isEqualTo(Map.of("name", "не должно быть пустым"));
    }

    @DisplayName("Изменение категории c несуществующим id " +
            "должно вернуть статус 404 с сообщением об ошибке")
    @Test
    @WithMockUser(roles = "ADMIN")
    public void editNonCorrectCategoryTest() {
        db.persist(category().withName("Category1"));
        CategoryDto categoryDto = new CategoryDto(2L, "Category2");

        webTestClient
                .put().uri("/category/edit")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .bodyValue(categoryDto)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(String.class).isEqualTo("Категория с id = 2 не найдена");
    }

    @DisplayName("Удаление категории c несуществующим id " +
            "должно вернуть статус 404 с сообщением об ошибке")
    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteCategoryWithNotExistIdTest() {
        webTestClient
                .delete().uri("/category/delete/1")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(String.class).isEqualTo("Категория с id = 1 не найдена");
    }

    private static Stream<CategoryDto> invalidCategoryDtos() {
        return Stream.of(
                new CategoryDto(null),
                new CategoryDto(""),
                new CategoryDto("   "));
    }

}
