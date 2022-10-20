package ru.ramprox.service.product.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.ramprox.service.product.config.ControllerTestConfig;
import ru.ramprox.service.product.database.entity.Brand;
import ru.ramprox.service.product.database.entity.Category;
import ru.ramprox.service.product.dto.ListProductDto;
import ru.ramprox.service.product.dto.ProductDto;
import ru.ramprox.service.product.dto.SavingProductDto;
import ru.ramprox.service.product.util.ProductBuilder;
import ru.ramprox.util.itest.DatabaseFacade;
import ru.ramprox.util.itest.annotations.ControllerITest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static ru.ramprox.service.product.util.BrandBuilder.brand;
import static ru.ramprox.service.product.util.CategoryBuilder.category;
import static ru.ramprox.service.product.util.ProductBuilder.product;

@DisplayName("Интеграционные тесты для CategoryController")
@ControllerITest(profiles = "itest", importConfigs = { ControllerTestConfig.class })
public class ProductControllerIT {

    @Autowired
    private DatabaseFacade db;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    public void beforeEach() {
        db.cleanDatabase();
    }

    @DisplayName("Извлечение продуктов")
    @Test
    public void findAllTest() {
        ProductBuilder product = product()
                .withBrand(db.persistedOnce(brand()))
                .withCategory(db.persistedOnce(category()));
        db.persist(product.withName("Product1"),
                product.withName("Product2"),
                product.withName("Product3"));

        webTestClient.get().uri("/product/all")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ListProductDto.class)
                .value(listProducts ->
                        assertThat(listProducts)
                                .extracting(ListProductDto::getId)
                                .containsExactly(1L, 2L, 3L));
    }

    @DisplayName("Извлечение продукта по id")
    @Test
    public void getProductByIdTest() {
        db.persist(product()
                .withBrand(db.persisted(brand()))
                .withCategory(db.persisted(category()))
        );

        webTestClient
                .get().uri("/product/1")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductDto.class).value(actualProductDto -> {
                    assertThat(actualProductDto.getId()).isEqualTo(1L);
                });
    }

    @DisplayName("Сохранение продукта")
    @Test
    @WithMockUser(roles = "ADMIN")
    public void saveProductTest() {
        Brand savedBrand = db.persist(brand());
        Category savedCategory = db.persist(category());
        SavingProductDto productDto = new SavingProductDto();
        productDto.setBrandId(savedBrand.getId());
        productDto.setCategoryId(savedCategory.getId());
        productDto.setName("Product1");

        webTestClient.post().uri("/product/new")
                .contentType(APPLICATION_JSON)
                .bodyValue(productDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(SavingProductDto.class)
                .value(actualProductDto ->
                        assertThat(actualProductDto.getId()).isEqualTo(1L));
    }

}
