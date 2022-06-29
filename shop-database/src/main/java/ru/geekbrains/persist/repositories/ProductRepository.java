package ru.geekbrains.persist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.geekbrains.persist.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>,
        JpaSpecificationExecutor<Product>, ProductRepositoryCustom {

    @Query("select p from Product p " +
            "join fetch p.brand " +
            "join fetch p.category " +
            "left join fetch p.mainPicture " +
            "left join fetch p.pictures " +
            "where p.id = :id")
    Optional<Product> findAllInfoById(Long id);

    @Query("select new Product(p.id, p.name, p.cost) from Product p where p.id in :ids")
    List<Product> findAllByIdIn(List<Long> ids);

    @Query("select new Product(p.id, p.name, p.cost) from Product p where p.id = :id")
    Optional<Product> findByIdForCart(Long id);
}
