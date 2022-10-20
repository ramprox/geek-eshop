package ru.ramprox.service.product.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.ramprox.service.product.controller.param.ProductFilter;
import ru.ramprox.service.product.dto.ListProductDto;
import ru.ramprox.service.product.dto.ProductDto;
import ru.ramprox.service.product.dto.SavingProductDto;
import ru.ramprox.service.product.service.product.ProductService;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/all")
    public Flux<ListProductDto> findAll(@Valid ProductFilter productFilter) {
        log.info("Запрошены продукты");
        return Flux.fromIterable(productService.findAll(productFilter));
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public Mono<ProductDto> findById(@PathVariable Long id) {
        log.info("Запрошен продукт с id = {}", id);
        return Mono.just(productService.findById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/new", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Mono<SavingProductDto> create(@RequestBody @Valid SavingProductDto productDto) {
        log.info("Создание продукта {}", productDto);
        return Mono.just(productService.save(productDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/edit", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public SavingProductDto edit(@RequestBody @Valid SavingProductDto productDto) {
        log.info("Изменение продукта {}", productDto);
        return productService.update(productDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        log.info("Удаление продукта с id = {}", id);
        productService.deleteById(id);
    }

}
