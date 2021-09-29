package ru.geekbrains.persist.projection;

import java.math.BigDecimal;

public interface OrderDetailBackend {
    String getProductName();
    Integer getQty();
    BigDecimal getCost();
}
