package ru.ramprox.service.order.database.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
public class OrderDetailID implements Serializable {

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "order_id")
    private Long orderId;

    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        OrderDetailID other = (OrderDetailID) o;
        return Objects.equals(productId, other.productId) &&
                Objects.equals(orderId, other.orderId);
    }

    public int hashCode() {
        return Objects.hash(productId, orderId);
    }

}
