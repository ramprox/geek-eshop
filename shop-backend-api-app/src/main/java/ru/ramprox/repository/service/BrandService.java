package ru.ramprox.repository.service;

import ru.ramprox.service.product.controller.dto.BrandDto;

import java.util.List;

public interface BrandService {
    List<BrandDto> findAll();
}
