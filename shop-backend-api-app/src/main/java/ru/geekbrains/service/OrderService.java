package ru.geekbrains.service;

import ru.geekbrains.controller.dto.OrderDetails;
import ru.geekbrains.controller.dto.OrderDto;
import ru.geekbrains.service.dto.LineItem;

import java.util.List;

public interface OrderService {

    List<OrderDto> findOrdersByUsername(String username);

    void createOrder(List<LineItem> lineItems, String username);

    OrderDetails findAllInfoByIdAndUsername(Long id, String username);

    void cancelOrder(Long id);
}
