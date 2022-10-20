package ru.ramprox.service.product.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.ramprox.service.product.dto.CategoryDto;
import ru.ramprox.service.product.dto.ReadCategoryDto;
import ru.ramprox.service.product.service.category.CategoryService;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/all")
    public Flux<ReadCategoryDto> getCategories(@RequestParam(required = false) Long categoryId) {
        if(categoryId == null) {
            log.info("Запрошены корневые категории");
        } else {
            log.info("Запрошены категории с родительским id = {}", categoryId);
        }
        return Flux.fromIterable(categoryService.findAllByCategoryIdOrderByName(categoryId));
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public Mono<CategoryDto> findById(@PathVariable Long id) {
        log.info("Запрошена категория с id = {}", id);
        return Mono.just(categoryService.findById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/new", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Mono<CategoryDto> create(@Valid @RequestBody CategoryDto categoryDto) {
        log.info("Сохранение категории {}", categoryDto.getName());
        return Mono.just(categoryService.save(categoryDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/edit", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Mono<CategoryDto> edit(@Valid @RequestBody CategoryDto categoryDto) {
        log.info("Изменение категории {}", categoryDto.getName());
        return Mono.just(categoryService.update(categoryDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/delete/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
        log.info("Удаление категории c id {}", id);
        categoryService.deleteById(id);
        return Mono.just(ResponseEntity.ok().build());
    }

}
