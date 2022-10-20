package ru.ramprox.service.product.service.category;

import ru.ramprox.service.product.dto.CategoryDto;
import ru.ramprox.service.product.dto.ReadCategoryDto;

import java.util.List;

public interface CategoryService {

    List<ReadCategoryDto> findAllByCategoryIdOrderByName(Long categoryId);

    CategoryDto findById(Long id);

    CategoryDto save(CategoryDto categoryDto);

    CategoryDto update(CategoryDto categoryDto);

    void deleteById(Long id);

}
