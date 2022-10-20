package ru.ramprox.repository.service;

import org.springframework.data.domain.Page;
import ru.ramprox.service.product.controller.dto.ProductListParams;
import ru.ramprox.service.product.controller.dto.ProductDto;

import java.util.Optional;

public interface ProductService {

    Page<ProductDto> findAll(ProductListParams listParams);

    Optional<ProductDto> findById(Long id);

    Optional<ProductDto> findByIdForCart(Long id);

}
