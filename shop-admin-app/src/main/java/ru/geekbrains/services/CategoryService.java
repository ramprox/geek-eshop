package ru.geekbrains.services;

import ru.geekbrains.dto.CategoryDto;
import ru.geekbrains.persist.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<CategoryDto> findAll();

    void save(CategoryDto categoryDto);

    Optional<CategoryDto> findById(Long id);

    void deleteById(Long id);

    List<CategoryDto> findAllOrderByName();
}
