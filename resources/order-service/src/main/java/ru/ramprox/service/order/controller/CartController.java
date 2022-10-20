package ru.ramprox.service.order.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import ru.ramprox.service.order.dto.ProductCartDto;
import ru.ramprox.service.order.service.cart.CartService;

@Slf4j
@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping(value = "/add")
    public void addToCart(@RequestBody long productId) {
        log.info("Запрос на добавление продукта с id = {} в корзину", productId);
        cartService.add(productId);
    }

    @GetMapping(value = "/all")
    public Flux<ProductCartDto> getProductsFromCart() {
        log.info("Запрошены все товары из корзины");
        return Flux.fromIterable(cartService.getProducts());
    }

}
