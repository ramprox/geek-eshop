package ru.ramprox.service.product.controller.dto;

public class OrderDto {
    private Long id;
    private String createdAt;
    private String price;
    private String status;

    public OrderDto() {
    }

    public OrderDto(Long id, String createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }

    public OrderDto(Long id, String createdAt, String price, String status) {
        this(id, createdAt);
        this.price = price;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
