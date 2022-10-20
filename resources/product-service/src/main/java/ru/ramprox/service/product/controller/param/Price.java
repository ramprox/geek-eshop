package ru.ramprox.service.product.controller.param;

import lombok.Getter;
import lombok.Setter;
import ru.ramprox.service.product.util.constraint.MinMax;

import javax.validation.GroupSequence;
import javax.validation.constraints.Min;
import javax.validation.groups.Default;
import java.math.BigDecimal;

@Setter
@Getter
@MinMax(groups = Price.MinMaxGroup.class)
public class Price {

    @Min(0)
    private BigDecimal min;

    @Min(0)
    private BigDecimal max;

    @GroupSequence({Default.class, MinMaxGroup.class})
    interface PriceValidationSequence { }

    interface MinMaxGroup {}

}
