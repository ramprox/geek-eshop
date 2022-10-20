package ru.ramprox.service.product.util;

import ru.ramprox.service.product.database.entity.Brand;
import ru.ramprox.util.itest.EntityBuilder;

public class BrandBuilder implements EntityBuilder<Brand> {

    private String name = "Brand";

    public static BrandBuilder brand() {
        return new BrandBuilder();
    }

    private BrandBuilder() { }

    private BrandBuilder(BrandBuilder brandBuilder) {
        this.name = brandBuilder.name;
    }

    public BrandBuilder withName(String name) {
        BrandBuilder builder = new BrandBuilder(this);
        builder.name = name;
        return builder;
    }

    @Override
    public Brand build() {
        return new Brand(name);
    }
}
