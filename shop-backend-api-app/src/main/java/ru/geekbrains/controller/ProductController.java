package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.controller.dto.ProductDto;
import ru.geekbrains.controller.dto.ProductListDto;
import ru.geekbrains.controller.dto.ProductListParams;
import ru.geekbrains.service.BrandService;
import ru.geekbrains.service.CategoryService;
import ru.geekbrains.service.ProductService;

@RequestMapping("/product")
@RestController
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;
    private final BrandService brandService;
    private final CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService,
                             BrandService brandService,
                             CategoryService categoryService) {
        this.productService = productService;
        this.brandService = brandService;
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/all", produces = "application/json")
    public ProductListDto findAll(ProductListParams productListParams) {
        logger.info("Products requested");
        Page<ProductDto> page = productService.findWithFilter(productListParams);
        ProductListDto productListDto = new ProductListDto();
        productListDto.setPage(page);
        productListDto.setBrands(brandService.findAll());
        productListDto.setCategories(categoryService.findAll());
        return productListDto;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ProductDto findById(@PathVariable("id") Long id) {
        logger.info(String.format("Product with id %d requested", id));
        return productService.findAllInfoById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
