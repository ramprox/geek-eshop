package ru.geekbrains.persist.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.persist.model.Product;

import javax.persistence.criteria.JoinType;
import java.math.BigDecimal;

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

    public static Specification<Product> fetchCategory() {
        return (root, query, builder) -> {
            if(query.getResultType() != Long.class) {
                root.fetch("category");
                root.fetch("brand");
            }
            return null;
        };
    }
}
