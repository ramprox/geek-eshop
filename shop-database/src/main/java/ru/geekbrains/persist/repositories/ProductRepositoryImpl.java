package ru.geekbrains.persist.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import ru.geekbrains.persist.model.Product;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.stream.Collectors;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Product> findAllForBackend(Specification<Product> spec, Pageable pageable) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Product> query = builder.createQuery(Product.class);
        Root<Product> productRoot = query.from(Product.class);
        List<String> selectionFields = List.of("id", "name", "cost", "shortDescription");
        List<Selection<?>> selections = getSelections(selectionFields, productRoot);
        selections.add(productRoot
                .join("mainPicture", JoinType.LEFT)
                .get("picture")
                .get("id"));
        query.multiselect(selections);
        Predicate predicate = spec.toPredicate(productRoot, query, builder);
        if (predicate != null) {
            query.where(predicate);
        }
        query.orderBy(QueryUtils.toOrders(pageable.getSort(), productRoot, builder));
        List<Product> result = em.createQuery(query)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        Long count = queryCount(predicate);
        return new PageImpl<>(result, pageable, count);
    }

    @Override
    public Page<Product> findAllForAdmin(Specification<Product> spec, Pageable pageable) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Product> query = builder.createQuery(Product.class);
        Root<Product> productRoot = query.from(Product.class);
        List<String> selectionFields = List.of("id", "name", "cost");
        List<Selection<?>> selections = getSelections(selectionFields, productRoot);
        selections.add(productRoot
                .join("mainPicture", JoinType.LEFT)
                .get("picture")
                .get("id"));
        query.multiselect(selections);
        Predicate predicate = spec.toPredicate(productRoot, query, builder);
        if (predicate != null) {
            query.where(predicate);
        }
        query.orderBy(QueryUtils.toOrders(pageable.getSort(), productRoot, builder));
        List<Product> result = em.createQuery(query)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        Long count = queryCount(predicate);
        return new PageImpl<>(result, pageable, count);
    }

    private List<Selection<?>> getSelections(List<String> fields,
                                             Root<Product> root) {
        return fields.stream()
                .map(root::get)
                .collect(Collectors.toList());
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
