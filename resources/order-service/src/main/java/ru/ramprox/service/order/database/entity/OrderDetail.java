package ru.ramprox.service.order.database.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "order_product",
        indexes = @Index(name = "order_product_order_id_product_id_uq",
                columnList = "order_id, product_id"))
public class OrderDetail {

    @Setter(AccessLevel.NONE)
    @EmbeddedId
    private OrderDetailID orderDetailID;

    @Column(name = "qty", nullable = false)
    private Integer qty;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public OrderDetail(OrderDetailID orderDetailID, Integer qty, BigDecimal price) {
        this.orderDetailID = orderDetailID;
        this.qty = qty;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetail other = (OrderDetail) o;
        return Objects.equals(orderDetailID, other.orderDetailID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderDetailID);
    }

}
