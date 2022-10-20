package ru.ramprox.service.product.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class OrderProductDto {

    private Long id;

    private String name;

    private BigDecimal price;

}
