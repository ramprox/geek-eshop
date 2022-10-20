package ru.ramprox.repository.service;

import ru.ramprox.service.product.controller.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> findAll();
}
