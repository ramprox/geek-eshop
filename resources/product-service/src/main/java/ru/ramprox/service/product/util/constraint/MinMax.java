package ru.ramprox.service.product.util.constraint;

import ru.ramprox.service.product.util.validator.MinMaxValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD })
@Constraint(validatedBy = MinMaxValidator.class)
public @interface MinMax {
    String message() default "min must be less than or equal max";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
