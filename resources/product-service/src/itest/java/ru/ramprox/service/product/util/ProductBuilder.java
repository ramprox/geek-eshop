package ru.ramprox.service.product.util;

import ru.ramprox.service.product.database.entity.Brand;
import ru.ramprox.service.product.database.entity.Category;
import ru.ramprox.service.product.database.entity.Product;
import ru.ramprox.service.product.database.entity.Description;
import ru.ramprox.util.itest.EntityBuilder;

import java.math.BigDecimal;

import static ru.ramprox.service.product.util.CategoryBuilder.category;
import static ru.ramprox.service.product.util.BrandBuilder.brand;

public class ProductBuilder implements EntityBuilder<Product> {

    private String name = "Product";

    private EntityBuilder<Brand> brand = brand();

    private EntityBuilder<Category> category = category();

    private BigDecimal price;

    private Description description = new Description();

    public static ProductBuilder product() {
        return new ProductBuilder();
    }

    private ProductBuilder() { }

    private ProductBuilder(ProductBuilder builder) {
        this.name = builder.name;
        this.brand = builder.brand;
        this.category = builder.category;
        this.price = builder.price;
        this.description = builder.description;
    }

    public ProductBuilder withName(String name) {
        ProductBuilder builder = new ProductBuilder(this);
        builder.name = name;
        return builder;
    }

    public ProductBuilder withBrand(EntityBuilder<Brand> brandBuilder) {
        ProductBuilder builder = new ProductBuilder(this);
        builder.brand = brandBuilder;
        return builder;
    }

    public ProductBuilder withCategory(EntityBuilder<Category> categoryBuilder) {
        ProductBuilder builder = new ProductBuilder(this);
        builder.category = categoryBuilder;
        return builder;
    }

    public ProductBuilder withPrice(BigDecimal price) {
        ProductBuilder builder = new ProductBuilder(this);
        builder.price = price;
        return builder;
    }

    public ProductBuilder withDescription(Description description) {
        ProductBuilder builder = new ProductBuilder(this);
        builder.description = description;
        return builder;
    }

    @Override
    public Product build() {
        Brand brand = this.brand != null ? this.brand.build() : null;
        Category category = this.category != null ? this.category.build() : null;
        Product product = new Product(name, brand, category);
        product.setPrice(price);
        product.setDescription(description);
        return product;
    }
}
