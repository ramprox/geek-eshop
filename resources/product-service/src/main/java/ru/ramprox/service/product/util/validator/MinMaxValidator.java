package ru.ramprox.service.product.util.validator;

import ru.ramprox.service.product.controller.param.Price;
import ru.ramprox.service.product.util.constraint.MinMax;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class MinMaxValidator implements ConstraintValidator<MinMax, Price> {

    @Override
    public boolean isValid(Price priceFilter, ConstraintValidatorContext constraintValidatorContext) {
        if(priceFilter.getMax() != null && priceFilter.getMin() != null) {
            BigDecimal max = priceFilter.getMax();
            BigDecimal min = priceFilter.getMin();
            return max.compareTo(min) >= 0;
        }
        return true;
    }

}
