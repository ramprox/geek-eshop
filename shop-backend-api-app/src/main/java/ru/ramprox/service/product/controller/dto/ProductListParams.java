package ru.ramprox.service.product.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductListParams {

    private String productName;

    @Min(0)
    private BigDecimal minPrice;

    @Min(0)
    private BigDecimal maxPrice;

    @Min(1)
    private Integer page = 1;

    @Min(1)
    private Integer size = 10;

    private String sortBy = "id";

    private String direction = "asc";

    private Long categoryId;

    private List<Long> brandIds = new ArrayList<>();

}
