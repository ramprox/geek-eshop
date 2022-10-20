package ru.ramprox.service.picture.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.util.List;

@Getter
@Setter
public class ProductPictures {

    private Long productId;

    private Flux<FilePart> files;

}
