package ru.geekbrains.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.geekbrains.controller.dto.BrandDto;
import ru.geekbrains.persist.model.Brand;
import ru.geekbrains.persist.repositories.BrandRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        assertEqualsBrands(expectedBrands, resultBrands);
    }

    private static void assertEqualsBrands(List<Brand> expectedBrands, List<BrandDto> resultBrands) {
        for(int i = 0; i < expectedBrands.size(); i++) {
            Brand expectedBrand = expectedBrands.get(i);
            BrandDto resultBrand = resultBrands.get(i);
            assertEquals(expectedBrand.getId(), resultBrand.getId());
            assertEquals(expectedBrand.getName(), resultBrand.getName());
        }
    }
}
