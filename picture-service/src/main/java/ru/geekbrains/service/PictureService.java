package ru.geekbrains.service;

import ru.geekbrains.persist.model.Picture;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PictureService {
    Optional<String> getPictureContentTypeById(long id);

    Optional<byte[]> getPictureDataById(long id);

    String createPicture(byte[] picture);

    void deleteById(Long id) throws IOException;

    Optional<Picture> findById(Long id);

    List<Picture> findByProductId(Long id);
}
