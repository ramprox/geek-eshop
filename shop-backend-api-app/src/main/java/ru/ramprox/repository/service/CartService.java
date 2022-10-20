package ru.ramprox.repository.service;

import ru.ramprox.service.product.controller.dto.ProductDto;
import ru.ramprox.repository.service.dto.LineItem;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {

    void addProductQty(ProductDto productDto, String color, String material, int qty);

    void updateProductQty(ProductDto productDto, String color, String material, int qty);

    void removeProduct(ProductDto productDto, String color, String material);

    void removeAll();

    List<LineItem> getLineItems();

    BigDecimal getSubTotal();
}
