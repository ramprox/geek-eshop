package ru.geekbrains.persist.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_product",
        indexes = {@Index(name = "order_product_uq", columnList = "order_id, product_id", unique = true)})
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, foreignKey = @ForeignKey(name = "fk_order_product_order_id"))
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, foreignKey = @ForeignKey(name = "fk_order_product_product_id"))
    private Product product;

    @Column(name = "qty", nullable = false)
    private Integer qty;

    @Column(name = "cost", nullable = false)
    private BigDecimal cost;

    public OrderDetail() {
    }

    public OrderDetail(Order order, Product product, Integer qty, BigDecimal cost) {
        this.product = product;
        this.order = order;
        this.qty = qty;
        this.cost = cost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
