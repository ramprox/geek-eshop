package ru.geekbrains.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.geekbrains.controller.dto.BrandDto;
import ru.geekbrains.persist.model.Brand;
import ru.geekbrains.persist.repositories.BrandRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BrandServiceTest {

    private BrandService brandService;

    private BrandRepository brandRepository;

    @BeforeEach
    public void init() {
        brandRepository = mock(BrandRepository.class);
        brandService = new BrandServiceImpl(brandRepository);
    }

    @Test
    public void testFindAll() {
        List<Brand> expectedBrands = new ArrayList<>();
        expectedBrands.add(new Brand(1L, "Brand1"));
        expectedBrands.add(new Brand(2L, "Brand2"));
        expectedBrands.add(new Brand(3L, "Brand3"));

        when(brandRepository.findAll())
                .thenReturn(expectedBrands);

        List<BrandDto> resultBrands = brandService.findAll();

        assertNotNull(resultBrands);
        assertEquals(expectedBrands.size(), resultBrands.size());
        for(Brand expectedBrand : expectedBrands) {
            assertTrue(isContain(resultBrands, expectedBrand));
        }
    }

    private static boolean isContain(List<BrandDto> result, Brand brand) {
        return result.stream()
                .anyMatch(brandDto -> brandDto.getId().equals(brand.getId())
                        && brandDto.getName().equals(brand.getName()));
    }
}
