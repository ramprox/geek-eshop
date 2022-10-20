package ru.ramprox.service.product.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.ramprox.service.product.dto.BrandDto;
import ru.ramprox.service.product.service.brand.BrandService;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping("/brand")
public class BrandController {

    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping(value = "/all")
    public Flux<BrandDto> getBrands() {
        log.info("Запрошены все брэнды");
        return Flux.fromIterable(brandService.findAllOrderByName());
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public Mono<BrandDto> findById(@PathVariable Long id) {
        log.info("Запрошен брэнд с id = {}", id);
        return Mono.just(brandService.findById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/new", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Mono<BrandDto> create(@Valid @RequestBody BrandDto brandDto) {
        log.info("Сохранение брэнда {}", brandDto.getName());
        return Mono.just(brandService.save(brandDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/edit", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Mono<BrandDto> edit(@Valid @RequestBody BrandDto brandDto) {
        log.info("Изменение брэнда {}", brandDto.getName());
        return Mono.just(brandService.update(brandDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/delete/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
        log.info("Удаление брэнда c id {}", id);
        brandService.deleteById(id);
        return Mono.just(ResponseEntity.ok().build());
    }

}
