package ru.ramprox.service.order.util;

import ru.ramprox.service.order.database.entity.OrderDetail;
import ru.ramprox.service.order.database.entity.OrderDetailID;
import ru.ramprox.util.itest.EntityBuilder;

import java.math.BigDecimal;

public class OrderDetailBuilder implements EntityBuilder<OrderDetail> {

    private Long orderId = 1L;

    private Long productId = 1L;

    private Integer qty = 1;

    private BigDecimal price = BigDecimal.ONE;

    public static OrderDetailBuilder orderDetail() {
        return new OrderDetailBuilder();
    }

    private OrderDetailBuilder() {}

    private OrderDetailBuilder(OrderDetailBuilder orderDetailBuilder) {
        this.orderId = orderDetailBuilder.orderId;
        this.productId = orderDetailBuilder.productId;
        this.qty = orderDetailBuilder.qty;
        this.price = orderDetailBuilder.price;
    }

    public OrderDetailBuilder withOrderId(Long orderId) {
        OrderDetailBuilder builder = new OrderDetailBuilder(this);
        builder.orderId = orderId;
        return builder;
    }

    public OrderDetailBuilder withProductId(Long productId) {
        OrderDetailBuilder builder = new OrderDetailBuilder(this);
        builder.productId = productId;
        return builder;
    }

    public OrderDetailBuilder withQty(Integer qty) {
        OrderDetailBuilder builder = new OrderDetailBuilder(this);
        builder.qty = qty;
        return builder;
    }

    public OrderDetailBuilder withPrice(BigDecimal price) {
        OrderDetailBuilder builder = new OrderDetailBuilder(this);
        builder.price = price;
        return builder;
    }

    @Override
    public OrderDetail build() {
//        return new OrderDetail(productId, orderId, qty, price);
        return new OrderDetail(new OrderDetailID(productId, orderId), qty, price);
    }
}
