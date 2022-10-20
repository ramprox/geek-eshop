package ru.ramprox.service.product.database.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionSystemException;
import ru.ramprox.service.product.database.entity.Description;
import ru.ramprox.service.product.database.entity.Product;
import ru.ramprox.service.product.database.repository.testProj.ProductView;
import ru.ramprox.service.product.util.ProductBuilder;
import ru.ramprox.util.itest.DatabaseFacade;
import ru.ramprox.util.itest.annotations.RepositoryITest;
import ru.ramprox.util.querylogging.ConfigLog4jdbc;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.ramprox.service.product.util.BrandBuilder.brand;
import static ru.ramprox.service.product.util.CategoryBuilder.category;
import static ru.ramprox.service.product.util.ProductBuilder.product;

@DisplayName("Интеграционные тесты для ProductRepository")
@RepositoryITest(profiles = "itest", importConfigs = ConfigLog4jdbc.class)
public class ProductRepositoryIT {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DatabaseFacade db;

    @BeforeEach
    public void beforeEach() {
        db.cleanDatabase();
    }

    @DisplayName("Получение списка продуктов по коллекции id")
    @Test
    public void findByIdInTest() {
        ProductBuilder product = product()
                .withCategory(db.persistedOnce(category()))
                .withBrand(db.persistedOnce(brand()));
        db.persist(
                product.withName("Product1"),
                product.withName("Product2"),
                product.withName("Product3")
        );

        List<Product> actualProducts = productRepository.findByIdIn(List.of(1L, 2L));

        assertThat(actualProducts.size()).isEqualTo(2);
        assertThat(actualProducts)
                .extracting(Product::getId)
                .containsExactly(1L, 2L);
    }

    @DisplayName("Получение проекции продукта по id")
    @Test
    public void findByIdTest() {
        Description description = new Description();
        description.setSmall("Small");
        description.setFull("Full");
        Product product = db.persist(product()
                .withCategory(db.persisted(category()))
                .withBrand(db.persisted(brand()))
                .withDescription(description));

        Optional<ProductView> actualOptProduct = productRepository.findById(product.getId(), ProductView.class);

        assertThat(actualOptProduct.isPresent()).isTrue();
        ProductView actualProduct = actualOptProduct.get();
        assertEquals(product.getId(), actualProduct.getId());
        assertEquals("Small", actualProduct.getDescriptionSmall());
    }

    @DisplayName("Получение проекции продуктов по коллекции id")
    @Test
    public void findByIdInProjectionTest() {
        ProductBuilder product = product()
                .withCategory(db.persistedOnce(category()))
                .withBrand(db.persistedOnce(brand()))
                .withPrice(new BigDecimal(1000));
        List<Product> savedProducts = db.persist(
                product.withDescription(new Description("Small1", "Full1")),
                product.withDescription(new Description("Small2", "Full2")),
                product.withDescription(new Description("Small3", "Full3"))
        );

        List<ProductView> actualProducts = productRepository.findByIdIn(List.of(1L, 2L), ProductView.class);

        assertThat(actualProducts.size()).isEqualTo(2);
        Iterator<Product> savedProductsIt = savedProducts.iterator();
        for (ProductView actualProduct : actualProducts) {
            Product savedProduct = savedProductsIt.next();
            assertEquals(savedProduct.getId(), actualProduct.getId());
            assertEquals(savedProduct.getName(), actualProduct.getName());
            assertThat(actualProduct.getPrice().compareTo(actualProduct.getPrice()) == 0).isTrue();
            assertEquals(savedProduct.getDescription().getSmall(), actualProduct.getDescriptionSmall());
        }
    }

    @DisplayName("Сохранение продукта с brand = null должно привести к исключению")
    @Test
    public void saveWithNullBrandTest() {
        Product product = product()
                .withCategory(db.persisted(category()))
                .withBrand(null).build();

        assertThrows(TransactionSystemException.class,
                () -> productRepository.save(product));
    }

    @DisplayName("Сохранение продукта с category = null должно привести к исключению")
    @Test
    public void saveWithNullCategoryTest() {
        Product product = product()
                .withBrand(db.persistedOnce(brand()))
                .withCategory(null).build();

        assertThrows(TransactionSystemException.class,
                () -> productRepository.save(product));
    }

}
