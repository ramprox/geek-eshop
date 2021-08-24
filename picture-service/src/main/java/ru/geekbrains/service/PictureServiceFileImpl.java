package ru.geekbrains.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.geekbrains.persist.model.Picture;
import ru.geekbrains.persist.repositories.PictureRepository;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PictureServiceFileImpl implements PictureService {

    @Value("${picture.storage.path}")
    private String storagePath;

    private final PictureRepository pictureRepository;

    private static final Logger logger = LoggerFactory.getLogger(PictureServiceFileImpl.class);

    @Autowired
    public PictureServiceFileImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    @PostConstruct
    public void init() throws IOException {
        Path rootStoragePath = Paths.get(storagePath);
        if(!Files.exists(rootStoragePath)) {
            Files.createDirectory(rootStoragePath);
        }
    }

    @Override
    public Optional<String> getPictureContentTypeById(long id) {
        return pictureRepository.findById(id)
                .map(Picture::getContentType);
    }

    @Override
    public Optional<byte[]> getPictureDataById(long id) {
        return pictureRepository.findById(id)
                .map(picture -> Paths.get(storagePath, picture.getStorageUUID()))
                .filter(path -> Files.exists(path))
                .map(path -> {
                    try {
                        return Files.readAllBytes(path);
                    } catch (IOException ex) {
                        logger.error("Can't read file for picture with id " + id, ex);
                        throw new RuntimeException(ex);
                    }
                });
    }

    @Override
    public String createPicture(byte[] picture) {
        String fileName = UUID.randomUUID().toString();
        try(OutputStream os = Files.newOutputStream(Paths.get(storagePath, fileName))) {
            os.write(picture);
        } catch (IOException ex) {
            logger.error("Can't write file", ex);
            throw new RuntimeException(ex);
        }
        return fileName;
    }

    @Override
    public void deleteById(Long id) throws IOException {
        Optional<Picture> picture = findById(id);
        if(picture.isPresent()) {
            deleteFile(picture.get().getStorageUUID());
            pictureRepository.deleteById(id);
        }
    }

    private void deleteFile(String pictureUUID) throws IOException {
        Files.deleteIfExists(Paths.get(storagePath, pictureUUID));
    }

    public Optional<Picture> findById(Long id) {
        return pictureRepository.findById(id);
    }

    @Override
    public List<Picture> findByProductId(Long id) {
        return pictureRepository.findByProductId(id);
    }
}
