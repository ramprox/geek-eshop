package ru.ramprox.service.product.service.brand;

import ru.ramprox.service.product.dto.BrandDto;

import java.util.List;

public interface BrandService {

    List<BrandDto> findAllOrderByName();

    BrandDto findById(Long id);

    BrandDto save(BrandDto brandDto);

    BrandDto update(BrandDto brandDto);

    void deleteById(Long id);

}
