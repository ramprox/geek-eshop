package ru.geekbrains.persist.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.persist.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryCustom {
    Page<Product> findAllForBackend(Specification<Product> spec, Pageable pageable);

    Page<Product> findAllForAdmin(Specification<Product> spec, Pageable pageable);

    Optional<Product> findByIdForCart(Long id);

    List<Product> findProductIdsWhereIdIn(List<Long> ids);
}
