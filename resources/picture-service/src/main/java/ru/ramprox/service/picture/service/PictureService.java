package ru.ramprox.service.picture.service;

import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import ru.ramprox.service.picture.dto.ProductPictures;
import ru.ramprox.service.picture.model.ImageDto;

import java.util.List;

public interface PictureService {

    ImageDto getPictureDataById(Long id);

    List<Long> getPictureIdsByProductId(Long productId);

    Flux<Long> savePictures(ProductPictures productPictures);

    void deleteById(Long id);

}
