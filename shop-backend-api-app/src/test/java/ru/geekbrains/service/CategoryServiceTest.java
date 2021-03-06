package ru.geekbrains.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.geekbrains.controller.dto.BrandDto;
import ru.geekbrains.controller.dto.CategoryDto;
import ru.geekbrains.persist.model.Brand;
import ru.geekbrains.persist.model.Category;
import ru.geekbrains.persist.repositories.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {

    private CategoryService categoryService;

    private CategoryRepository categoryRepository;

    @BeforeEach
    public void init() {
        categoryRepository = mock(CategoryRepository.class);
        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    public void testFindAll() {
        List<Category> expectedCategories = new ArrayList<>();
        expectedCategories.add(new Category(1L, "Category1"));
        expectedCategories.add(new Category(2L, "Category2"));
        expectedCategories.add(new Category(3L, "Category3"));

        when(categoryRepository.findAll())
                .thenReturn(expectedCategories);

        List<CategoryDto> resultCategories = categoryService.findAll();

        assertNotNull(resultCategories);
        assertEquals(expectedCategories.size(), resultCategories.size());
        assertEqualsCategories(expectedCategories, resultCategories);
    }

    private static void assertEqualsCategories(List<Category> expectedCategories, List<CategoryDto> resultCategories) {
        for(int i = 0; i < expectedCategories.size(); i++) {
            Category expectedCategory = expectedCategories.get(i);
            CategoryDto resultCategory = resultCategories.get(i);
            assertEquals(expectedCategory.getId(), resultCategory.getId());
            assertEquals(expectedCategory.getName(), resultCategory.getName());
        }
    }
}
