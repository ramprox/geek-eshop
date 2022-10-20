package ru.ramprox.service.product.controller.dto;

public class AddLineItemDto {

    private Long productId;

    private Integer qty;

    private String color;

    private String material;

    public AddLineItemDto() {
    }

    public AddLineItemDto(Long productId, Integer qty, String color, String material) {
        this.productId = productId;
        this.qty = qty;
        this.color = color;
        this.material = material;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
