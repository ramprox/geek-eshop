package ru.ramprox.service.product.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ramprox.service.product.database.entity.Brand;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    List<Brand> findAllByOrderByName();

}
