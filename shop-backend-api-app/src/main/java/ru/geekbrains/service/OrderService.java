package ru.geekbrains.service;

import ru.geekbrains.controller.dto.OrderDetails;
import ru.geekbrains.controller.dto.OrderDto;
import ru.geekbrains.service.dto.LineItem;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<OrderDto> findOrdersByUsername(String username);

    void createOrder(List<LineItem> lineItems, String username);

    OrderDetails findAllInfoById(Long id, String username);

    void cancelOrder(Long id);
}
