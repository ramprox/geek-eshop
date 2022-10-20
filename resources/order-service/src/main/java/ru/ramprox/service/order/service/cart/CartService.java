package ru.ramprox.service.order.service.cart;

import ru.ramprox.service.order.dto.ProductCartDto;

import java.util.Collection;
import java.util.List;

public interface CartService {

    void add(long productId);

    Collection<ProductCartDto> getProducts();

}
