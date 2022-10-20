package ru.ramprox.repository.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ramprox.service.product.controller.dto.BrandDto;
import ru.ramprox.persist.model.Brand;
import ru.ramprox.persist.repositories.BrandRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class BrandServiceTest {

    private BrandService brandService;

    private BrandRepository brandRepository;

    @BeforeEach
    public void init() {
        brandRepository = mock(BrandRepository.class);
        brandService = new BrandServiceImpl(brandRepository);
    }

    @Test
    @SneakyThrows
    public void testFindAll() {
//        List<Brand> expectedBrands = new ArrayList<>();
//        Brand brand = new Brand("Brand1");
//        ReflectionUtils.setField(brand.getClass().getField("id"), brand, 1L);
//        expectedBrands.add(brand);
//        brand = new Brand("Brand2");
//        ReflectionUtils.setField(brand.getClass().getField("id"), brand, 2L);
//        expectedBrands.add(brand);
//        brand = new Brand("Brand3");
//        ReflectionUtils.setField(brand.getClass().getField("id"), brand, 3L);
//        expectedBrands.add(brand);
//
//        when(brandRepository.findAll())
//                .thenReturn(expectedBrands);
//
//        List<BrandDto> resultBrands = brandService.findAll();
//
//        assertNotNull(resultBrands);
//        assertEquals(expectedBrands.size(), resultBrands.size());
//        assertEqualsBrands(expectedBrands, resultBrands);
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
