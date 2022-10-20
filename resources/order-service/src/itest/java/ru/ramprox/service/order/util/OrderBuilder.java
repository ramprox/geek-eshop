package ru.ramprox.service.order.util;

import ru.ramprox.service.order.database.entity.Order;
import ru.ramprox.util.itest.EntityBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderBuilder implements EntityBuilder<Order> {

    private Long userId = 1L;

    private LocalDateTime createdAt = LocalDateTime.of(1970, 1, 1, 0, 0, 0);

    private BigDecimal price = BigDecimal.ONE;

    private Order.Status status = Order.Status.NEW;

    public static OrderBuilder order() {
        return new OrderBuilder();
    }

    private OrderBuilder() { }

    private OrderBuilder(OrderBuilder orderBuilder) {
        this.userId = orderBuilder.userId;
        this.createdAt = orderBuilder.createdAt;
        this.price = orderBuilder.price;
        this.status = orderBuilder.status;
    }

    public OrderBuilder withUserId(Long userId) {
        OrderBuilder builder = new OrderBuilder(this);
        builder.userId = userId;
        return builder;
    }

    public OrderBuilder withCreatedAt(LocalDateTime createdAt) {
        OrderBuilder builder = new OrderBuilder(this);
        builder.createdAt = createdAt;
        return builder;
    }

    public OrderBuilder withPrice(BigDecimal price) {
        OrderBuilder builder = new OrderBuilder(this);
        builder.price = price;
        return builder;
    }

    public OrderBuilder withStatus(Order.Status status) {
        OrderBuilder builder = new OrderBuilder(this);
        builder.status = status;
        return builder;
    }

    @Override
    public Order build() {
        Order order = new Order(price, createdAt, userId);
        order.setStatus(status);
        return order;
    }
}
