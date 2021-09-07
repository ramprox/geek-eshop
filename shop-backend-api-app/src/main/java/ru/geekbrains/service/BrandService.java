package ru.geekbrains.service;

import ru.geekbrains.controller.dto.BrandDto;

import java.util.List;

public interface BrandService {
    List<BrandDto> findAll();
}
