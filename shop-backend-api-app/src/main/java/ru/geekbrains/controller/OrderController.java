package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.controller.dto.OrderDetails;
import ru.geekbrains.controller.dto.OrderDto;
import ru.geekbrains.service.CartService;
import ru.geekbrains.service.OrderService;
import ru.geekbrains.service.dto.LineItem;

import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/order")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;
    private final CartService cartService;

    @Autowired
    public OrderController(OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    @GetMapping(produces = "application/json")
    public List<OrderDto> findOrdersByUser(Authentication auth) {
        String username = ((User)auth.getPrincipal()).getUsername();
        logger.info("Find all orders for user {}", username);
        return orderService.findOrdersByUsername(username);
    }

    @PostMapping(value = "/create")
    public void createOrder(Authentication auth) {
        String username = ((User)auth.getPrincipal()).getUsername();
        logger.info("Create order by user {}", username);
        List<LineItem> lineItems = cartService.getLineItems();
        if(lineItems.size() == 0) {
            return;
        }
        orderService.createOrder(lineItems, username);
        cartService.removeAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public OrderDetails getOrderDetails(@PathVariable("id") Long id, Authentication auth) {
        String username = ((User)auth.getPrincipal()).getUsername();
        logger.info("Order details with id {} requested", id);
        return orderService.findAllInfoByIdAndUsername(id, username);
    }

    @DeleteMapping(consumes = "application/json")
    public void cancelOrder(@RequestBody Long id) {
        logger.info("Cancel order with id = {}", id);
        orderService.cancelOrder(id);
    }
}
