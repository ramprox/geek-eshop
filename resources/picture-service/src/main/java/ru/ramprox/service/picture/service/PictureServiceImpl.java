package ru.ramprox.service.picture.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import ru.ramprox.service.picture.database.entity.Picture;
import ru.ramprox.service.picture.database.repository.PictureRepository;
import ru.ramprox.service.picture.dto.PictureId;
import ru.ramprox.service.picture.dto.ProductPictures;
import ru.ramprox.service.picture.model.ImageDto;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PictureServiceImpl implements PictureService {

    private final String storagePath;

    private final PictureRepository pictureRepository;

    public PictureServiceImpl(PictureRepository pictureRepository,
                              @Value("${picture.storage.path}") String storagePath) {
        this.pictureRepository = pictureRepository;
        this.storagePath = storagePath;
    }

    @PostConstruct
    public void init() throws IOException {
        Path rootStoragePath = Paths.get(storagePath);
        if(!Files.exists(rootStoragePath)) {
            Files.createDirectory(rootStoragePath);
        }
    }

    @Override
    public ImageDto getPictureDataById(Long pictureId) {
        Picture picture = pictureRepository.findById(pictureId)
                .orElseThrow(() -> new RuntimeException(String.format("Картинка с id = %s не найдена", pictureId)));
        try {
            ImageDto imageDto = new ImageDto();
            imageDto.setMediaType(picture.getContentType());
            imageDto.setContent(Files.readAllBytes(Paths.get(storagePath, picture.getStorageUUID())));
            return imageDto;
        } catch (IOException ex) {
            log.error("Не найден файл для картинки с id {}. {}", pictureId, ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Long> getPictureIdsByProductId(Long productId) {
        return pictureRepository.findByProductId(productId, PictureId.class)
                .stream().map(PictureId::getId).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Flux<Long> savePictures(ProductPictures productPictures) {
        Long productId = productPictures.getProductId();
        return productPictures.getFiles()
                .flatMap(file -> {
                    String uuid = UUID.randomUUID().toString();
                    return savePictureInStorage(uuid, file)
                            .publish(v -> {
                                List<String> strings = file.headers().get("Content-Type");
                                Picture picture = new Picture(uuid, strings.get(0), productId);
                                picture.setName(file.filename());
                                return Mono.just(pictureRepository.save(picture).getId());
                            });
                });
    }

    private Mono<Void> savePictureInStorage(String filename, FilePart pictureFile) {
        Path path = Path.of(storagePath, filename);
//        try {
//            pictureFile.transferTo(path);
//        } catch (IOException ex) {
//            log.error("Can't write file", ex);
//            throw new RuntimeException(ex);
//        }
        return pictureFile.transferTo(path);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        Optional<Picture> pictureOpt = pictureRepository.findById(id);
        pictureOpt.ifPresentOrElse(picture -> {
            deleteFile(picture.getStorageUUID());
            pictureRepository.deleteById(id);
        }, () -> {
            String message = String.format("Изображение с id = %d не найдено", id);
            throw new RuntimeException(message);
        });
    }

    private void deleteFile(String pictureUUID) {
        try {
            Files.deleteIfExists(Paths.get(storagePath, pictureUUID));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
