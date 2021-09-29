package ru.geekbrains.persist.projection;

import ru.geekbrains.persist.model.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface OrderInfoBackend {
    Long getId();
    LocalDateTime getCreatedAt();
    Order.Status getStatus();
    BigDecimal getCost();
}
