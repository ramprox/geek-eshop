package ru.ramprox.repository.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ramprox.service.product.controller.dto.CategoryDto;
import ru.ramprox.persist.model.Category;
import ru.ramprox.persist.repositories.CategoryRepository;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class CategoryServiceTest {

    private CategoryService categoryService;

    private CategoryRepository categoryRepository;

    @BeforeEach
    public void init() {
        categoryRepository = mock(CategoryRepository.class);
        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    @SneakyThrows
    public void testFindAll() {
//        List<Category> expectedCategories = new ArrayList<>();
//        Category category = new Category("Category1");
//        ReflectionUtils.setField(category.getClass().getField("id"), category, 1L);
//        expectedCategories.add(category);
//        category = new Category("Category2");
//        ReflectionUtils.setField(category.getClass().getField("id"), category, 2L);
//        expectedCategories.add(category);
//        category = new Category("Category2");
//        ReflectionUtils.setField(category.getClass().getField("id"), category, 3L);
//        expectedCategories.add(category);
//
//        when(categoryRepository.findAll())
//                .thenReturn(expectedCategories);
//
//        List<CategoryDto> resultCategories = categoryService.findAll();
//
//        assertNotNull(resultCategories);
//        assertEquals(expectedCategories.size(), resultCategories.size());
//        assertEqualsCategories(expectedCategories, resultCategories);
    }

    private static void assertEqualsCategories(List<Category> expectedCategories, List<CategoryDto> resultCategories) {
        Iterator<Category> expectedCategoriesIterator = expectedCategories.iterator();
        Iterator<CategoryDto> resultCategoriesIterator = resultCategories.iterator();
        while(expectedCategoriesIterator.hasNext()) {
            Category expectedCategory = expectedCategoriesIterator.next();
            CategoryDto resultCategory = resultCategoriesIterator.next();
            assertEquals(expectedCategory.getId(), resultCategory.getId());
            assertEquals(expectedCategory.getName(), resultCategory.getName());
        }
    }
}
