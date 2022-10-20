package ru.ramprox.service.product.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.ramprox.service.product.database.entity.Product;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    List<Product> findByIdIn(Collection<Long> ids);

    <T> List<T> findByIdIn(Collection<Long> ids, Class<T> projectionType);

    <T> Optional<T> findById(Long id, Class<T> projectionType);

}
