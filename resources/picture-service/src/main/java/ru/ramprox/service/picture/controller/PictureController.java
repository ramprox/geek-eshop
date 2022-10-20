package ru.ramprox.service.picture.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.ramprox.service.picture.dto.ProductPictures;
import ru.ramprox.service.picture.model.ImageDto;
import ru.ramprox.service.picture.service.PictureService;

import java.util.List;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/picture")
public class PictureController {

    private final PictureService pictureService;

    public PictureController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @GetMapping(value = "/{pictureId}")
    public Mono<ResponseEntity<byte[]>> getPictureContent(@PathVariable Long pictureId) {
        ImageDto image = pictureService.getPictureDataById(pictureId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, image.getMediaType());
        return Mono.just(new ResponseEntity<>(image.getContent(), headers, HttpStatus.OK));
    }

    @GetMapping
    public Flux<Long> getPictureIds(@RequestParam Long productId) {
        return Flux.fromIterable(pictureService.getPictureIdsByProductId(productId));
    }

    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE, produces = APPLICATION_JSON_VALUE)
    public Flux<Long> savePictures(@RequestParam Long productId,
                                   @RequestPart("file") Flux<FilePart> file) {
        ProductPictures productPictures = new ProductPictures();
        productPictures.setProductId(productId);
        productPictures.setFiles(Flux.from(file));
        return pictureService.savePictures(productPictures);
    }

    @DeleteMapping("/{pictureId}")
    public Mono<ResponseEntity<Void>> deletePicture(@PathVariable Long pictureId) {
        pictureService.deleteById(pictureId);
        return Mono.just(ResponseEntity.ok().build());
    }
}
