package ru.geekbrains.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.geekbrains.controller.dto.ProductDto;
import ru.geekbrains.service.dto.LineItem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CartServiceTest {

    private CartService cartService;

    @BeforeEach
    public void init() {
        cartService = new CartServiceImpl();
    }

    @Test
    public void testIfNewCartIsEmpty() {
        assertNotNull(cartService.getLineItems());
        assertEquals(0, cartService.getLineItems().size());
        assertEquals(BigDecimal.ZERO, cartService.getSubTotal());
    }

    @Test
    public void testAddProduct() {
        ProductDto expectedProduct = new ProductDto();
        expectedProduct.setId(1L);
        expectedProduct.setCost(new BigDecimal(123));
        expectedProduct.setName("Product name");

        cartService.addProductQty(expectedProduct, "color", "material", 2);

        List<LineItem> lineItems = cartService.getLineItems();
        assertNotNull(lineItems);
        assertEquals(1, lineItems.size());
        assertTrue(isContain(lineItems, expectedProduct, "color", "material", 2));
        assertEquals(new BigDecimal(123).multiply(new BigDecimal(2)).setScale(2, RoundingMode.HALF_UP), cartService.getSubTotal());
    }

    @Test
    public void testUpdateProductQty() {
        ProductDto expectedProduct = new ProductDto();
        expectedProduct.setId(1L);
        expectedProduct.setCost(new BigDecimal(123));
        expectedProduct.setName("Product name");

        cartService.addProductQty(expectedProduct, "color", "material", 2);

        cartService.updateProductQty(expectedProduct, "color", "material", 5);

        List<LineItem> lineItems = cartService.getLineItems();
        assertNotNull(lineItems);
        assertEquals(1, lineItems.size());
        assertTrue(isContain(lineItems, expectedProduct, "color", "material", 5));

        assertEquals(new BigDecimal(123).multiply(new BigDecimal(5)).setScale(2, RoundingMode.HALF_UP), cartService.getSubTotal());
    }

    @Test
    public void testRemoveProduct() {
        ProductDto expectedProduct1 = new ProductDto(1L, "Product 1", new BigDecimal(123));
        ProductDto expectedProduct2 = new ProductDto(2L, "Product 2", new BigDecimal(456));
        ProductDto expectedProduct3 = new ProductDto(3L, "Product 3", new BigDecimal(789));

        cartService.addProductQty(expectedProduct1, "color1", "material1", 2);
        cartService.addProductQty(expectedProduct2, "color2", "material2", 3);
        cartService.addProductQty(expectedProduct3, "color3", "material3", 4);

        cartService.removeProduct(expectedProduct2, "color2", "material2");

        List<LineItem> lineItems = cartService.getLineItems();
        assertNotNull(lineItems);
        assertEquals(2, lineItems.size());
        assertTrue(isContain(lineItems, expectedProduct1, "color1", "material1", 2));
        assertFalse(isContain(lineItems, expectedProduct2, "color2", "material2", 3));
        assertTrue(isContain(lineItems, expectedProduct3, "color3", "material3", 4));

        BigDecimal totalPrice = expectedProduct1.getCost().multiply(new BigDecimal(2));
        totalPrice = totalPrice.add(expectedProduct3.getCost().multiply(new BigDecimal(4)));
        assertEquals(totalPrice, cartService.getSubTotal());
    }

    @Test
    public void testRemoveAll() {
        ProductDto expectedProduct1 = new ProductDto(1L, "Product 1", new BigDecimal(123));
        ProductDto expectedProduct2 = new ProductDto(2L, "Product 2", new BigDecimal(456));
        ProductDto expectedProduct3 = new ProductDto(3L, "Product 3", new BigDecimal(789));

        cartService.addProductQty(expectedProduct1, "color1", "material1", 2);
        cartService.addProductQty(expectedProduct2, "color2", "material2", 2);
        cartService.addProductQty(expectedProduct3, "color3", "material3", 2);

        cartService.removeAll();

        List<LineItem> lineItems = cartService.getLineItems();
        assertNotNull(lineItems);
        assertEquals(0, lineItems.size());
        assertEquals(BigDecimal.ZERO, cartService.getSubTotal());
    }

    @Test
    public void testGetLineItems() {
        ProductDto expectedProduct1 = new ProductDto(1L, "Product 1", new BigDecimal(123));
        ProductDto expectedProduct2 = new ProductDto(2L, "Product 2", new BigDecimal(456));
        ProductDto expectedProduct3 = new ProductDto(3L, "Product 3", new BigDecimal(789));

        cartService.addProductQty(expectedProduct1, "color1", "material1", 2);
        cartService.addProductQty(expectedProduct2, "color2", "material2", 3);
        cartService.addProductQty(expectedProduct3, "color3", "material3", 4);

        List<LineItem> resultLineItems = cartService.getLineItems();
        assertNotNull(resultLineItems);
        assertEquals(3, resultLineItems.size());
        assertTrue(isContain(resultLineItems, expectedProduct1, "color1", "material1", 2));
        assertTrue(isContain(resultLineItems, expectedProduct2, "color2", "material2", 3));
        assertTrue(isContain(resultLineItems, expectedProduct3, "color3", "material3", 4));
    }

    @Test
    public void testGetSubTotal() {
        ProductDto expectedProduct1 = new ProductDto(1L, "Product 1", new BigDecimal(123));
        ProductDto expectedProduct2 = new ProductDto(2L, "Product 2", new BigDecimal(456));
        ProductDto expectedProduct3 = new ProductDto(3L, "Product 3", new BigDecimal(789));

        cartService.addProductQty(expectedProduct1, "color1", "material1", 2);
        cartService.addProductQty(expectedProduct2, "color2", "material2", 3);
        cartService.addProductQty(expectedProduct3, "color3", "material3", 4);

        BigDecimal totalPrice = expectedProduct1.getCost().multiply(new BigDecimal(2));
        totalPrice = totalPrice.add(expectedProduct2.getCost().multiply(new BigDecimal(3)));
        totalPrice = totalPrice.add(expectedProduct3.getCost().multiply(new BigDecimal(4)));

        assertEquals(totalPrice, cartService.getSubTotal());
    }

    private static boolean isContain(List<LineItem> lineItems,
                                     ProductDto productDto, String color, String material, Integer qty) {
        return lineItems.stream()
                .anyMatch(lineItem -> lineItem.getProductId().equals(productDto.getId())
                        && lineItem.getColor().equals(color)
                        && lineItem.getMaterial().equals(material)
                        && lineItem.getQty().equals(qty)
                        && lineItem.getProductDto().getName().equals(productDto.getName()));
    }

}
