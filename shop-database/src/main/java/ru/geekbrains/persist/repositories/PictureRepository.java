package ru.geekbrains.persist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.geekbrains.persist.model.Picture;

import java.util.List;

public interface PictureRepository extends JpaRepository<Picture, Long> {
    @Query("select p from Picture p where p.product.id = :productId")
    List<Picture> findByProductId(@Param("productId") Long productId);
}
