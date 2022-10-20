package ru.ramprox.service.product.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.ramprox.service.product.database.entity.Category;
import ru.ramprox.service.product.dto.ReadCategoryDto;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByOrderByName();

    @Query(value = "SELECT id, name, EXISTS(SELECT * FROM categories WHERE category_id = cat.id) as hasChilds" +
            " FROM categories as cat WHERE cat.category_id = :categoryId ORDER BY name",
            nativeQuery = true)
    List<ReadCategoryDto> findByParentCategoryId(Long categoryId);

    @Query(value = "SELECT id, name, EXISTS(SELECT * FROM categories WHERE category_id = cat.id) as hasChilds" +
            " FROM categories as cat WHERE cat.category_id IS NULL ORDER BY name",
            nativeQuery = true)
    List<ReadCategoryDto> findRootCategories();

}
