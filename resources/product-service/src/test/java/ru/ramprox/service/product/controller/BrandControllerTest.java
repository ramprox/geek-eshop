package ru.ramprox.service.product.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import ru.ramprox.service.product.dto.BrandDto;
import ru.ramprox.service.product.service.brand.BrandService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("Unit тесты для BrandController")
@ExtendWith(MockitoExtension.class)
public class BrandControllerTest {

    private BrandController brandController;

    @Mock
    private BrandService brandService;

    @BeforeEach
    public void beforeEach() {
        brandController = new BrandController(brandService);
    }

    @DisplayName("Извлечение всех брэндов")
    @Test
    public void getBrandsTest() {
        when(brandService.findAllOrderByName())
                .thenReturn(
                        List.of(new BrandDto(1L, "Brand1"),
                                new BrandDto(2L, "Brand2"),
                                new BrandDto(3L, "Brand3"))
                );

        Flux<BrandDto> actualBrands = brandController.getBrands();

        StepVerifier.create(actualBrands)
                .expectNextMatches(brandDto -> brandDto.getId().equals(1L))
                .expectNextMatches(brandDto -> brandDto.getId().equals(2L))
                .expectNextMatches(brandDto -> brandDto.getId().equals(3L))
                .expectComplete()
                .verify();
    }

}
