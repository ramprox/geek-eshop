package ru.geekbrains.persist.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.persist.model.Product;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

public class ProductSpecifications {
    public static Specification<Product> productNamePrefix(String prefix) {
        return (root, query, builder) -> builder.like(root.get("name"), "%" + prefix + "%");
    }

    public static Specification<Product> minCost(BigDecimal minCost) {
        return (root, query, builder) -> builder.ge(root.get("cost"), minCost);
    }

    public static Specification<Product> maxCost(BigDecimal maxCost) {
        return (root, query, builder) -> builder.le(root.get("cost"), maxCost);
    }

    public static Specification<Product> productCategory(Long categoryId) {
        return (root, query, builder) -> builder.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Product> productBrands(Long brandId) {
        return (root, query, builder) -> builder.equal(root.get("brand").get("id"), brandId);
    }
}
