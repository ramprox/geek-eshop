package ru.ramprox.repository.service;

import ru.ramprox.service.product.controller.dto.OrderDetails;
import ru.ramprox.service.product.controller.dto.OrderDto;
import ru.ramprox.repository.service.dto.LineItem;

import java.util.List;

public interface OrderService {

    List<OrderDto> findOrdersByUsername(String username);

    void createOrder(List<LineItem> lineItems, String username);

    OrderDetails findAllInfoByIdAndUsername(Long id, String username);

    void cancelOrder(Long id);
}
