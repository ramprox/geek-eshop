package ru.geekbrains.interfaces;

import ru.geekbrains.dto.CategoryDto;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<CategoryDto> findAll();

    void save(CategoryDto categoryDto);

    Optional<CategoryDto> findById(Long id);

    void deleteById(Long id);

    List<CategoryDto> findAllOrderByName();
}
