package ru.geekbrains.services;

import org.springframework.data.domain.Page;
import ru.geekbrains.dto.ProductDto;
import ru.geekbrains.dto.ProductListParams;
import ru.geekbrains.persist.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductDto> findAll();

    Page<ProductDto> findWithFilter(ProductListParams listParams);

    void save(ProductDto product);

    Optional<ProductDto> findById(Long id);

    void deleteById(Long id);
}
