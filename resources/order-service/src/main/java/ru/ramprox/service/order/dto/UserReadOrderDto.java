package ru.ramprox.service.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ramprox.service.order.database.entity.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserReadOrderDto {

    private Long id;

    private LocalDateTime createdAt;

    private String price;

    private Order.Status status;

}
