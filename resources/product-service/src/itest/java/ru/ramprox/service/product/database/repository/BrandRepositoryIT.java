package ru.ramprox.service.product.database.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.TransactionSystemException;
import ru.ramprox.service.product.database.entity.Brand;
import ru.ramprox.service.product.database.entity.Category;
import ru.ramprox.service.product.database.entity.Product;
import ru.ramprox.service.product.util.BrandBuilder;
import ru.ramprox.util.itest.DatabaseFacade;
import ru.ramprox.util.itest.EntityBuilder;
import ru.ramprox.util.itest.annotations.RepositoryITest;
import ru.ramprox.util.querylogging.ConfigLog4jdbc;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static ru.ramprox.service.product.util.BrandBuilder.brand;
import static ru.ramprox.service.product.util.CategoryBuilder.category;
import static ru.ramprox.service.product.util.ProductBuilder.product;

@DisplayName("Интеграционные тесты для BrandRepository")
@RepositoryITest(profiles = "itest", importConfigs = ConfigLog4jdbc.class)
public class BrandRepositoryIT {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private DatabaseFacade db;

    @BeforeEach
    public void beforeEach() {
        db.cleanDatabase();
    }

    @DisplayName("Удаление брэнда вместе с продуктом")
    @Test
    public void deleteWithProductTest() {
        EntityBuilder<Brand> brand = db.persisted(brand());
        EntityBuilder<Category> category = db.persisted(category());
        Product savedProduct = db.persist(product().withBrand(brand).withCategory(category));

        brandRepository.deleteById(savedProduct.getBrand().getId());

        Product actualProduct = db.find(Product.class, savedProduct.getId());
        assertThat(actualProduct).isNull();
    }

    @DisplayName("Нахождение всех брэндов, отсортированных в порядке возрастания имени")
    @Test
    public void findAllByOrderByNameTest() {
        BrandBuilder brand = brand();
        db.persist(
                brand.withName("Brand3"),
                brand.withName("Brand1"),
                brand.withName("Brand2")
        );

        List<Brand> actualBrands = brandRepository.findAllByOrderByName();

        assertThat(actualBrands)
                .extracting(Brand::getName)
                .containsExactly("Brand1", "Brand2", "Brand3");
    }

    @DisplayName("Сохранение брэндов с одинаковыми именами должно привести к исключению")
    @Test
    public void saveBrandsWithTheSameNamesTest() {
        db.persist(brand().withName("Brand"));

        Brand brand = brand().withName("Brand").build();
        assertThrows(DataIntegrityViolationException.class,
                () -> brandRepository.save(brand));
    }

    @DisplayName("Сохранение брэнда с недопустимыми значениями должно привести к исключению")
    @ParameterizedTest
    @MethodSource("invalidNames")
    public void saveWithInvalidNameTest(String name) {
        Brand brand = brand().withName(name).build();
        assertThrows(TransactionSystemException.class,
                () -> brandRepository.save(brand));
    }

    private static Stream<String> invalidNames() {
        return Stream.of(null, "", "   ");
    }
}
