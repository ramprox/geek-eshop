package ru.ramprox.service.product.database.repository.testProj;

import java.math.BigDecimal;

public interface ProductView {

    Long getId();

    String getName();

    BigDecimal getPrice();

    String getDescriptionSmall();

}
