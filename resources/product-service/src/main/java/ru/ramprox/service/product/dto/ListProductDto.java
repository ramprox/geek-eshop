package ru.ramprox.service.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListProductDto {

    private Long id;

    private String name;

    private String price;

    private String shortDesc;

}
