package ru.geekbrains.service;

import org.springframework.data.domain.Page;
import ru.geekbrains.controller.ProductListParams;
import ru.geekbrains.controller.dto.ProductDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductDto> findAll();

    Page<ProductDto> findWithFilter(ProductListParams listParams);

    Optional<ProductDto> findById(Long id);
}
