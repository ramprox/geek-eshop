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
import ru.ramprox.service.product.dto.ReadCategoryDto;
import ru.ramprox.service.product.util.CategoryBuilder;
import ru.ramprox.util.itest.DatabaseFacade;
import ru.ramprox.util.itest.EntityBuilder;
import ru.ramprox.util.itest.annotations.RepositoryITest;
import ru.ramprox.util.querylogging.ConfigLog4jdbc;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.ramprox.service.product.util.BrandBuilder.brand;
import static ru.ramprox.service.product.util.CategoryBuilder.category;
import static ru.ramprox.service.product.util.ProductBuilder.product;

@DisplayName("Интеграционные тесты для CategoryRepository")
@RepositoryITest(profiles = "itest", importConfigs = ConfigLog4jdbc.class)
public class CategoryRepositoryIT {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DatabaseFacade db;

    @BeforeEach
    public void beforeEach() {
        db.cleanDatabase();
    }

    @DisplayName("Удаление категории вместе с продуктами должно привести к исключению PersistenceException")
    @Test
    public void deleteTest() {
        EntityBuilder<Brand> brand = db.persisted(brand());
        EntityBuilder<Category> category = db.persisted(category());
        Product savedProduct = db.persist(product().withBrand(brand).withCategory(category));

        assertThrows(DataIntegrityViolationException.class,
                () -> categoryRepository.deleteById(savedProduct.getCategory().getId()));
    }

    @DisplayName("Нахождение всех категорий, отсортированных в порядке возрастания имени")
    @Test
    public void findAllByOrderByNameTest() {
        CategoryBuilder category = category();
        db.persist(
                category.withName("Category3"),
                category.withName("Category1"),
                category.withName("Category2")
        );

        List<Category> actualCategories = categoryRepository.findAllByOrderByName();

        assertThat(actualCategories)
                .extracting(Category::getName)
                .containsExactly("Category1", "Category2", "Category3");
    }

    @DisplayName("Извлечение корневых категорий")
    @Test
    public void findByCategoryIdOrderByNameWhereCategoryIdNullTest() {
        CategoryBuilder category = category();
        List<Category> expectedCategories = db.persist(
                category.withName("Category 1"),
                category.withName("Category 2"),
                category.withName("Category 3")
        );

        List<ReadCategoryDto> actualCategories = categoryRepository.findRootCategories();

        assertThat(actualCategories)
                .extracting(ReadCategoryDto::getId)
                .containsExactly(
                        expectedCategories.get(0).getId(),
                        expectedCategories.get(1).getId(),
                        expectedCategories.get(2).getId()
                );
    }

    @DisplayName("Извлечение категорий по родительскому categoryId")
    @Test
    public void findByCategoryIdOrderByNameTest() {
        CategoryBuilder category = category();
        EntityBuilder<Category> parentCategory = db.persistedOnce(category.withName("Parent category"));
        List<Category> childCategories = db.persist(
                category.withName("Child category 2").withParentCategory(parentCategory),
                category.withName("Child category 3").withParentCategory(parentCategory),
                category.withName("Child category 1").withParentCategory(parentCategory)
        );

        List<ReadCategoryDto> actualCategories = categoryRepository.findByParentCategoryId(1L);

        assertThat(actualCategories)
                .extracting(ReadCategoryDto::getId)
                .containsExactly(
                        childCategories.get(2).getId(),
                        childCategories.get(0).getId(),
                        childCategories.get(1).getId()
                );
    }

    @DisplayName("Сохранение категорий с одинаковыми именами должно привести к исключению")
    @Test
    public void saveBrandsWithTheSameNamesTest() {
        db.persist(category().withName("Category"));

        Category category2 = category().withName("Category").build();
        assertThrows(DataIntegrityViolationException.class,
                () -> categoryRepository.save(category2));
    }

    @DisplayName("Сохранение категории с недопустимыми значениями должно привести к исключению")
    @ParameterizedTest
    @MethodSource("invalidNames")
    public void saveWithInvalidNameTest(String name) {
        Category category = category().withName(name).build();
        assertThrows(TransactionSystemException.class,
                () -> categoryRepository.save(category));
    }

    private static Stream<String> invalidNames() {
        return Stream.of(null, "", "   ");
    }

}
