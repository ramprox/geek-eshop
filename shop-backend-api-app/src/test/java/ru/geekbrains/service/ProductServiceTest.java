package ru.geekbrains.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.controller.dto.ProductDto;
import ru.geekbrains.controller.dto.ProductListParams;
import ru.geekbrains.persist.model.Brand;
import ru.geekbrains.persist.model.Category;
import ru.geekbrains.persist.model.Picture;
import ru.geekbrains.persist.model.Product;
import ru.geekbrains.persist.repositories.ProductRepository;
import ru.geekbrains.service.dto.LineItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductServiceTest {

    private ProductService productService;

    private ProductRepository productRepository;

    @BeforeEach
    public void init() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    public void testFindAllInfoById() {
        Category expectedCategory = new Category();
        expectedCategory.setId(1L);
        expectedCategory.setName("Category name");

        Brand expectedBrand = new Brand();
        expectedBrand.setId(1L);
        expectedBrand.setName("Brand name");

        List<Picture> expectedPictures = new ArrayList<>();
        expectedPictures.add(new Picture(1L));
        expectedPictures.add(new Picture(2L));


        Product expectedProduct = new Product();
        expectedProduct.setId(1L);
        expectedProduct.setName("Product name");
        expectedProduct.setCategory(expectedCategory);
        expectedProduct.setBrand(expectedBrand);
        expectedProduct.setPictures(expectedPictures);
        expectedProduct.setCost(new BigDecimal("12345.00"));
        expectedProduct.setShortDescription("Short description");
        expectedProduct.setDescription("Description");


        when(productRepository.findAllInfoById(eq(expectedProduct.getId())))
                .thenReturn(Optional.of(expectedProduct));

        Optional<ProductDto> opt = productService.findAllInfoById(expectedProduct.getId());

        assertTrue(opt.isPresent());
        ProductDto resultProductDto = opt.get();
        assertEquals(expectedProduct.getId(), resultProductDto.getId());
        assertEquals(expectedProduct.getName(), resultProductDto.getName());
        assertEquals(expectedProduct.getCategory().getId(), resultProductDto.getCategoryDto().getId());
        assertEquals(expectedProduct.getCategory().getName(), resultProductDto.getCategoryDto().getName());
        assertEquals(expectedProduct.getBrand().getId(), resultProductDto.getBrandDto().getId());
        assertEquals(expectedProduct.getBrand().getName(), resultProductDto.getBrandDto().getName());
        assertEquals(expectedProduct.getCost(), resultProductDto.getCost());
        assertEquals(expectedPictures.size(), resultProductDto.getPictureIds().size());
        assertEquals(expectedProduct.getShortDescription(), resultProductDto.getShortDescription());
        assertEquals(expectedProduct.getDescription(), resultProductDto.getDescription());
        for (int i = 0; i < expectedPictures.size(); i++) {
            assertEquals(expectedPictures.get(i).getId(), resultProductDto.getPictureIds().get(i));
        }
    }

    @Test
    public void testFindByIdForCart() {
        Product expectedProduct = new Product();
        expectedProduct.setId(1L);
        expectedProduct.setName("Product name");
        expectedProduct.setCost(new BigDecimal("12345.00"));

        when(productRepository.findByIdForCart(eq(expectedProduct.getId())))
                .thenReturn(Optional.of(expectedProduct));

        Optional<ProductDto> opt = productService.findByIdForCart(expectedProduct.getId());
        assertTrue(opt.isPresent());
        ProductDto resultProductDto = opt.get();
        assertEquals(expectedProduct.getId(), resultProductDto.getId());
        assertEquals(expectedProduct.getName(), resultProductDto.getName());
        assertEquals(expectedProduct.getCost(), resultProductDto.getCost());
    }

    @Test
    public void testFindWithFilter() {

        List<Product> expectedProducts = new ArrayList<>();

        Product expectedProduct = new Product();
        expectedProduct.setId(1L);
        expectedProduct.setName("Product name");
        expectedProduct.setMainPicture(new Picture(1L));
        expectedProduct.setCost(new BigDecimal("12345.00"));
        expectedProduct.setShortDescription("Short description");

        expectedProducts.add(expectedProduct);

        when(productRepository.findAllForBackend(any(Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(expectedProducts));

        ProductListParams params = new ProductListParams();
        params.setCategoryId(1L);
        params.setMinCost(new BigDecimal(200));
        params.setMaxCost(new BigDecimal(50000));
        params.setDirection("asc");

        Page<ProductDto> page = productService.findWithFilter(params);

        assertEquals(1L, page.getTotalElements());
        assertTrue(isContain(page, expectedProduct));
    }

    private static boolean isContain(Page<ProductDto> page, Product product) {
        return page.stream()
                .anyMatch(productDto -> productDto.getId().equals(product.getId())
                        && productDto.getName().equals(product.getName())
                        && productDto.getCost().equals(product.getCost())
                        && productDto.getMainPictureId().equals(product.getMainPicture().getId())
                        && productDto.getShortDescription().equals(product.getShortDescription()));
    }
}
