package ru.ramprox.service.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.ramprox.service.order.dto.UserReadOrderDto;
import ru.ramprox.service.order.service.order.OrderService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping(value = "/new", produces = APPLICATION_JSON_VALUE)
    public Mono<UserReadOrderDto> create(@AuthenticationPrincipal OidcUser oidcUser) {
        Long userId = (Long)oidcUser.getAttributes().get("userId");
        return orderService.save(userId);
    }

}
