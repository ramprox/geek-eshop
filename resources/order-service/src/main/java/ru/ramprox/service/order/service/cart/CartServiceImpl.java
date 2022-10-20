package ru.ramprox.service.order.service.cart;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import ru.ramprox.service.order.dto.ProductCartDto;
import ru.ramprox.service.order.dto.ProductDto;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@SessionScope
public class CartServiceImpl implements CartService {

    private final HashMap<Long, ProductCartDto> cart = new LinkedHashMap<>();

    @Override
    public void add(long productId) {
        cart.compute(productId, (key, productCartDto) -> {
            if(productCartDto == null) {
                ProductCartDto productCart = new ProductCartDto();
                productCart.setProductId(productId);
                return productCart;
            }
            int currentQty = productCartDto.getQty();
            productCartDto.setQty(++currentQty);
            return productCartDto;
        });
    }

    @Override
    public Collection<ProductCartDto> getProducts() {
        return cart.values();
    }
}
