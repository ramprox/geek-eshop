package ru.ramprox.repository.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ramprox.service.product.controller.dto.ProductDto;
import ru.ramprox.persist.model.Product;
import ru.ramprox.persist.repositories.ProductRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;

public class ProductServiceTest {

    private ProductService productService;

    private ProductRepository productRepository;

    @BeforeEach
    public void init() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    @SneakyThrows
    public void testFindAllInfoById() {
//        Category expectedCategory = new Category("Category name");
//        ReflectionUtils.setField(expectedCategory.getClass().getField("id"), expectedCategory, 1L);
//        Brand expectedBrand = new Brand("Brand name");
//        ReflectionUtils.setField(expectedBrand.getClass().getField("id"), expectedBrand, 1L);
//
//        Product expectedProduct = new Product("Product");
//        expectedProduct.setPrice(new BigDecimal("12345.00"));
//        expectedCategory.addProduct(expectedProduct);
//        expectedBrand.addProduct(expectedProduct);
//
//        List<Picture> expectedPictures = new ArrayList<>();
//        Picture picture = new Picture("uuid1", "type1");
//        ReflectionUtils.setField(picture.getClass().getField("id"), picture, 1L);
//        expectedProduct.addPicture(picture);
//        expectedPictures.add(picture);
//        picture = new Picture("uuid2", "type2");
//        ReflectionUtils.setField(picture.getClass().getField("id"), picture, 2L);
//        expectedProduct.addPicture(picture);
//        expectedPictures.add(picture);
//
//        expectedProduct.setShortDescription("Short description");
//        expectedProduct.setDescription("Description");
//
//        when(productRepository.findById(eq(expectedProduct.getId())))
//                .thenReturn(Optional.of(expectedProduct));
//
//        Optional<ProductDto> opt = productService.findAllInfoById(expectedProduct.getId());
//
//        assertTrue(opt.isPresent());
//        ProductDto resultProductDto = opt.get();
//        assertEquals(expectedProduct.getId(), resultProductDto.getId());
//        assertEquals(expectedProduct.getName(), resultProductDto.getName());
//        assertEquals(expectedProduct.getCategory().getId(), resultProductDto.getCategoryDto().getId());
//        assertEquals(expectedProduct.getCategory().getName(), resultProductDto.getCategoryDto().getName());
//        assertEquals(expectedProduct.getBrand().getId(), resultProductDto.getBrandDto().getId());
//        assertEquals(expectedProduct.getBrand().getName(), resultProductDto.getBrandDto().getName());
//        assertEquals(expectedProduct.getPrice().toString(), resultProductDto.getCost());
//        assertEquals(expectedPictures.size(), resultProductDto.getPictureIds().size());
//        assertEquals(expectedProduct.getShortDescription(), resultProductDto.getShortDescription());
//        assertEquals(expectedProduct.getDescription(), resultProductDto.getDescription());
//        for (int i = 0; i < expectedPictures.size(); i++) {
//            assertEquals(expectedPictures.get(i).getId(), resultProductDto.getPictureIds().get(i));
//        }
    }

    @Test
    public void testFindByIdForCart() {
//        Brand brand = new Brand("Brand1");
//        Category category = new Category("Category1");
//        Product expectedProduct = new Product("Product name");
//        expectedProduct.setPrice( new BigDecimal("12345.00"));
//        category.addProduct(expectedProduct);
//        brand.addProduct(expectedProduct);
//
//        when(productRepository.findIdNameCostById(eq(expectedProduct.getId())))
//                .thenReturn(Optional.of(expectedProduct));
//
//        Optional<ProductDto> opt = productService.findByIdForCart(expectedProduct.getId());
//        assertTrue(opt.isPresent());
//        ProductDto resultProductDto = opt.get();
//        assertEquals(expectedProduct.getId(), resultProductDto.getId());
//        assertEquals(expectedProduct.getName(), resultProductDto.getName());
//        assertEquals(expectedProduct.getPrice().toString(), resultProductDto.getCost());
    }

    @Test
    @SneakyThrows
    public void testFindWithFilter() {
//        Brand brand = new Brand("brand1");
//        Category category = new Category("Category1");
//        List<Product> expectedProducts = new ArrayList<>();
//        Product expectedProduct = new Product("name");
//        category.addProduct(expectedProduct);
//        brand.addProduct(expectedProduct);
//        Picture picture = new Picture("uuid", "type");
//        expectedProduct.addPicture(picture);
//        ReflectionUtils.setField(picture.getClass().getField("id"), picture, 1L);
//        expectedProduct.setMainPicture(picture);
//        expectedProduct.setShortDescription("Short description");
//
//        expectedProducts.add(expectedProduct);
//
//        when(productRepository.findAllForBackend(any(Specification.class), any(Pageable.class)))
//                .thenReturn(new PageImpl<>(expectedProducts));
//
//        ProductListParams params = new ProductListParams();
//        params.setCategoryId(1L);
//        params.setMinCost(new BigDecimal(200));
//        params.setMaxCost(new BigDecimal(50000));
//        params.setDirection("asc");
//
//        Page<ProductDto> page = productService.findWithFilter(params);
//
//        assertEquals(Long.valueOf(expectedProducts.size()), page.getTotalElements());
//        assertEqualsProducts(expectedProducts, page.getContent());
    }

    private static void assertEqualsProducts(List<Product> expectedProducts, List<ProductDto> resultProducts) {
        for (int i = 0; i < expectedProducts.size(); i++) {
            Product expectedProduct = expectedProducts.get(i);
            ProductDto resultProduct = resultProducts.get(i);
            assertEquals(expectedProduct.getId(), resultProduct.getId());
            assertEquals(expectedProduct.getName(), resultProduct.getName());
            assertEquals(expectedProduct.getPrice().toString(), resultProduct.getPrice());
//            assertEquals(expectedProduct.getMainPicture().getId(), resultProduct.getMainPictureId());
        }
    }
}
