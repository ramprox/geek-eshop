package ru.ramprox.service.product.database.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.ramprox.service.product.database.entity.Product;
import ru.ramprox.service.product.database.entity.Brand_;
import ru.ramprox.service.product.database.entity.Category_;
import ru.ramprox.service.product.database.entity.Product_;

import java.math.BigDecimal;

public class ProductSpecifications {

    public static Specification<Product> productNamePrefix(String prefix) {
        return (root, query, builder) ->
                builder.like(root.get(Product_.NAME), "%" + prefix + "%");
    }

    public static Specification<Product> minPrice(BigDecimal minPrice) {
        return (root, query, builder) ->
                builder.ge(root.get(Product_.PRICE), minPrice);
    }

    public static Specification<Product> maxPrice(BigDecimal maxPrice) {
        return (root, query, builder) ->
                builder.le(root.get(Product_.PRICE), maxPrice);
    }

    public static Specification<Product> productCategory(Long categoryId) {
        return (root, query, builder) ->
                builder.equal(root.get(Product_.CATEGORY).get(Category_.ID), categoryId);
    }

    public static Specification<Product> productBrands(Long brandId) {
        return (root, query, builder) ->
                builder.equal(root.get(Product_.BRAND).get(Brand_.ID), brandId);
    }

}
