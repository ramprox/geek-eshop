package ru.geekbrains.interfaces;

import ru.geekbrains.dto.BrandDto;

import java.util.List;
import java.util.Optional;

public interface BrandService {
    List<BrandDto> findAll();

    void save(BrandDto categoryDto);

    Optional<BrandDto> findById(Long id);

    void deleteById(Long id);

    List<BrandDto> findAllOrderByName();
}
