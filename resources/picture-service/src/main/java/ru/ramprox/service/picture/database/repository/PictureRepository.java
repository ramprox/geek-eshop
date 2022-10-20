package ru.ramprox.service.picture.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ramprox.service.picture.database.entity.Picture;

import java.util.List;
import java.util.Optional;

public interface PictureRepository extends JpaRepository<Picture, Long> {

    <T> List<T> findByProductId(Long productId, Class<T> type);

}
