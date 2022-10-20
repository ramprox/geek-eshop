package ru.ramprox.service.product.service.product;

import org.springframework.data.domain.Page;
import ru.ramprox.service.product.controller.param.ProductFilter;
import ru.ramprox.service.product.dto.ListProductDto;
import ru.ramprox.service.product.dto.ProductDto;
import ru.ramprox.service.product.dto.SavingProductDto;

import java.util.List;

public interface ProductService {

    List<ListProductDto> findAll(ProductFilter params);

    ProductDto findById(Long id);

    SavingProductDto save(SavingProductDto productDto);

    SavingProductDto update(SavingProductDto productDto);

    void deleteById(Long id);

}
