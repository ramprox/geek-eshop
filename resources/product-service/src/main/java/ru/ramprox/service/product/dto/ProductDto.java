package ru.ramprox.service.product.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class ProductDto {

    private Long id;

    @NotBlank
    private String name;

    private String price;

    @Valid
    private DescriptionDto description;

}
