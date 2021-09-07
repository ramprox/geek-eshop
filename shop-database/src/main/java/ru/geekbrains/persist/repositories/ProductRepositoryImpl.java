package ru.geekbrains.persist.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import ru.geekbrains.persist.model.Brand;
import ru.geekbrains.persist.model.Category;
import ru.geekbrains.persist.model.Picture;
import ru.geekbrains.persist.model.Product;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Product> findAllForBackend(Specification<Product> spec, Pageable pageable) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createTupleQuery();
        Root<Product> productRoot = query.from(Product.class);

        productRoot.join("category", JoinType.INNER);
        productRoot.join("brand", JoinType.INNER);

        Path<Long> id = productRoot.get("id");
        Path<String> name = productRoot.get("name");
        Path<BigDecimal> cost = productRoot.get("cost");
        Path<String> shortDescription = productRoot.get("shortDescription");
        Path<Long> mainPictureId = productRoot.join("mainPicture", JoinType.LEFT).get("id");

        query.multiselect(id, name, cost, shortDescription, mainPictureId);
        Predicate predicate = spec.toPredicate(productRoot, query, builder);
        if (predicate != null) {
            query.where(predicate);
        }
        query.orderBy(QueryUtils.toOrders(pageable.getSort(), productRoot, builder));

        List<Product> result = em.createQuery(query)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList()
                .stream()
                .map(tuple -> {
                    Product product = new Product(tuple.get(id),
                            tuple.get(name),
                            tuple.get(cost),
                            tuple.get(shortDescription));
                    Long mainPictId = tuple.get(mainPictureId);
                    if(mainPictId != null) {
                        Picture mainPict = new Picture();
                        mainPict.setId(mainPictId);
                        product.setMainPicture(mainPict);
                    }
                    return product;
                }).collect(Collectors.toList());

        Long count = queryCount(predicate);

        return new PageImpl<>(result, pageable, count);
    }

    @Override
    public Page<Product> findAllForAdmin(Specification<Product> spec, Pageable pageable) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createTupleQuery();
        Root<Product> productRoot = query.from(Product.class);

        Join<Product, Category> joinCategory = productRoot.join("category", JoinType.INNER);
        Join<Product, Brand> joinBrand = productRoot.join("brand", JoinType.INNER);

        Path<Long> id = productRoot.get("id");
        Path<String> name = productRoot.get("name");
        Path<BigDecimal> cost = productRoot.get("cost");

        Path<Long> mainPictureId = productRoot.join("mainPicture", JoinType.LEFT).get("id");

        query.multiselect(id, name, cost, mainPictureId);
        Predicate predicate = spec.toPredicate(productRoot, query, builder);
        if (predicate != null) {
            query.where(predicate);
        }
        query.orderBy(QueryUtils.toOrders(pageable.getSort(), productRoot, builder));

        List<Product> result = em.createQuery(query)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList()
                .stream()
                .map(tuple -> {
                    Picture pict = new Picture();
                    pict.setId(tuple.get(mainPictureId));
                    return new Product(tuple.get(id), tuple.get(name), tuple.get(cost), pict);
                })
                .collect(Collectors.toList());

        Long count = queryCount(predicate);

        return new PageImpl<>(result, pageable, count);
    }

    private Long queryCount(Predicate predicate) {
        CriteriaBuilder builderCount = em.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = builderCount.createQuery(Long.class);
        if (predicate != null) {
            countQuery.where(predicate);
        }
        countQuery.select(builderCount.count(countQuery.from(Product.class)));
        return em.createQuery(countQuery).getSingleResult();
    }
}
