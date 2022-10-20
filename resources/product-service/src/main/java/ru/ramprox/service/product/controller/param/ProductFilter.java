package ru.ramprox.service.product.controller.param;

import lombok.Getter;
import lombok.Setter;
import ru.ramprox.service.product.util.constraint.NullOrNotBlank;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.groups.ConvertGroup;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProductFilter {

    @NullOrNotBlank
    private String productName;

    @Valid
    @ConvertGroup(to = Price.PriceValidationSequence.class)
    private Price price;

    @Min(1)
    private Integer page = 1;

    @Min(1)
    private Integer size = 10;

    private String sortBy = "id";

    private String direction = "asc";

    @Min(1)
    private Long categoryId;

    private List<@Min(1) Long> brandIds = new ArrayList<>();

}
