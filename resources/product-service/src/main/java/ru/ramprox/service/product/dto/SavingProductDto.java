package ru.ramprox.service.product.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SavingProductDto extends ProductDto {

    @Min(1)
    private Long brandId;

    @Min(1)
    private Long categoryId;

}
