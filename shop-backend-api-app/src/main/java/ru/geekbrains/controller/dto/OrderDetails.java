package ru.geekbrains.controller.dto;

import ru.geekbrains.service.dto.LineItem;

import java.util.List;

public class OrderDetails {

    private OrderDto orderDto;

    private List<LineItem> lineItems;

    public OrderDetails() {
    }

    public OrderDetails(OrderDto orderDto, List<LineItem> lineItems) {
        this.orderDto = orderDto;
        this.lineItems = lineItems;
    }

    public OrderDto getOrderDto() {
        return orderDto;
    }

    public void setOrderDto(OrderDto orderDto) {
        this.orderDto = orderDto;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }
}
