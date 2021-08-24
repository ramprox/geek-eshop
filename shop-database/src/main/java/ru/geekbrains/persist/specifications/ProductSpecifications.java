package ru.geekbrains.persist.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.persist.model.Product;
import java.math.BigDecimal;

import static org.springframework.data.jpa.domain.Specification.where;

public class ProductSpecifications {
    public static Specification<Product> productNamePrefix(String prefix) {
        return (root, query, builder) -> builder.like(root.get("name"), prefix + "%");
    }

    public static Specification<Product> minCost(BigDecimal minCost) {
        return (root, query, builder) -> builder.ge(root.get("cost"), minCost);
    }

    public static Specification<Product> maxCost(BigDecimal maxCost) {
        return (root, query, builder) -> builder.le(root.get("cost"), maxCost);
    }
}
