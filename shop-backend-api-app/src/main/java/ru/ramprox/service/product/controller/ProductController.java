package ru.ramprox.service.product.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ramprox.service.product.controller.dto.ProductDto;
import ru.ramprox.service.product.controller.dto.ProductListParams;
import ru.ramprox.service.product.controller.exception.ProductNotFoundException;
import ru.ramprox.repository.service.ProductService;

import javax.validation.Valid;

@RequestMapping("/product")
@RestController
@Slf4j
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ProductDto> findAll(@Valid ProductListParams productListParams) {
        log.info("Products requested");
        return productService.findAll(productListParams);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDto findById(@PathVariable Long id) {
        log.info("Product with id {} requested", id);
        return productService.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }
}
