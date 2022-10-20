package ru.ramprox.repository.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ramprox.service.product.controller.dto.ProductDto;
import ru.ramprox.repository.service.dto.LineItem;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
//        ProductDto expectedProduct = new ProductDto(1L, "Product name", "123");
//        List<LineItem> expectedLineItems = Arrays.asList(new LineItem(expectedProduct,
//                "color", "material", 2));
//        cartService.addProductQty(expectedProduct, "color", "material", 2);
//
//        List<LineItem> resultLineItems = cartService.getLineItems();
//        assertNotNull(resultLineItems);
//        assertEquals(expectedLineItems.size(), resultLineItems.size());
//        assertEqualsLineItems(expectedLineItems, resultLineItems);
    }

    @Test
    public void testUpdateProductQty() {
//        ProductDto expectedProduct = new ProductDto(1L, "Product name", "123");
//        List<LineItem> expectedLineItems = Arrays.asList(new LineItem(expectedProduct,
//                "color", "material", 5));
//
//        cartService.addProductQty(expectedProduct, "color", "material", 2);
//
//        cartService.updateProductQty(expectedProduct, "color", "material", 5);
//
//        List<LineItem> resultLineItems = cartService.getLineItems();
//        assertNotNull(resultLineItems);
//        assertEquals(expectedLineItems.size(), resultLineItems.size());
//        assertEqualsLineItems(expectedLineItems, resultLineItems);
    }

    @Test
    public void testRemoveProduct() {
//        int indexDeletingProduct = 1;
//        List<ProductDto> expectedProducts = getExpectedProducts();
//        List<LineItem> expectedLineItems = new ArrayList<>();
//        for (int i = 0; i < expectedProducts.size(); i++) {
//            String color = "color" + (i + 1);
//            String material = "material" + (i + 1);
//            int qty = i + 2;
//            if (i != indexDeletingProduct) {
//                expectedLineItems.add(new LineItem(expectedProducts.get(i), color, material, qty));
//            }
//            cartService.addProductQty(expectedProducts.get(i), color, material, qty);
//        }
//        cartService.removeProduct(expectedProducts.get(indexDeletingProduct),
//                "color" + (indexDeletingProduct + 1), "material" + (indexDeletingProduct + 1));
//
//        List<LineItem> resultLineItems = cartService.getLineItems();
//        assertNotNull(resultLineItems);
//        assertEquals(expectedLineItems.size(), resultLineItems.size());
//        assertEqualsLineItems(expectedLineItems, resultLineItems);
    }

    @Test
    public void testRemoveAll() {
//        List<ProductDto> expectedProducts = getExpectedProducts();
//        for (int i = 0; i < expectedProducts.size(); i++) {
//            cartService.addProductQty(expectedProducts.get(0),
//                    "color" + (i + 1), "material" + (i + 1), 2);
//        }
//        cartService.removeAll();
//
//        List<LineItem> resultLineItems = cartService.getLineItems();
//        assertNotNull(resultLineItems);
//        assertEquals(0, resultLineItems.size());
//        assertEquals(BigDecimal.ZERO, cartService.getSubTotal());
    }

    @Test
    public void testGetLineItems() {
//        List<ProductDto> expectedProducts = getExpectedProducts();
//        List<LineItem> expectedLineItems = new ArrayList<>();
//        for (int i = 0; i < expectedProducts.size(); i++) {
//            String color = "color" + (i + 1);
//            String material = "material" + (i + 1);
//            int qty = i + 2;
//            expectedLineItems.add(new LineItem(expectedProducts.get(i), color, material, qty));
//            cartService.addProductQty(expectedProducts.get(i), color, material, qty);
//        }
//
//        List<LineItem> resultLineItems = cartService.getLineItems();
//        resultLineItems.sort(Comparator.comparingLong(LineItem::getProductId));
//        assertNotNull(resultLineItems);
//        assertEquals(expectedLineItems.size(), resultLineItems.size());
//        assertEqualsLineItems(expectedLineItems, resultLineItems);
    }

    @Test
    public void testGetSubTotal() {
//        List<ProductDto> expectedProducts = getExpectedProducts();
//        List<LineItem> expectedLineItems = new ArrayList<>();
//        for (int i = 0; i < expectedProducts.size(); i++) {
//            String color = "color" + (i + 1);
//            String material = "material" + (i + 1);
//            int qty = i + 2;
//            cartService.addProductQty(expectedProducts.get(i), color, material, qty);
//            expectedLineItems.add(new LineItem(expectedProducts.get(i), color, material, qty));
//        }
//
//        BigDecimal totalPrice = getExpectedSubTotal(expectedLineItems);
//        assertEquals(totalPrice, cartService.getSubTotal());
    }

    private static List<ProductDto> getExpectedProducts() {
//        List<ProductDto> result = new ArrayList<>();
//        result.add(new ProductDto(1L, "Product 1", "123"));
//        result.add(new ProductDto(2L, "Product 2", "456"));
//        result.add(new ProductDto(3L, "Product 3", "789"));
//        return result;
        return null;
    }

    private static BigDecimal getExpectedSubTotal(List<LineItem> expectedLineItems) {
        return expectedLineItems.stream()
                .map(LineItem::getItemTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
