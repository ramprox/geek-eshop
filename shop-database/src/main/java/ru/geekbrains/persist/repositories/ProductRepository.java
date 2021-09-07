package ru.geekbrains.persist.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.geekbrains.persist.model.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>,
        JpaSpecificationExecutor<Product>, ProductRepositoryCustom {

    @EntityGraph(attributePaths = {"category", "brand", "pictures"})
    Page<Product> findAll(Specification<Product> spec, Pageable pageable);

    @Query("select p from Product p " +
            "join fetch p.brand " +
            "join fetch p.category " +
            "left join fetch p.pictures " +
            "where p.id = :id")
    Optional<Product> findAllInfoById(@Param("id") Long id);
}
