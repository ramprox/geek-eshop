package ru.ramprox.service.picture.util;

import ru.ramprox.service.picture.database.entity.Picture;
import ru.ramprox.util.itest.EntityBuilder;

import java.util.UUID;

public class PictureBuilder implements EntityBuilder<Picture> {

    private String storageUUID = toUuid(1);

    private String name = "Picture";

    private String contentType = "image/jpeg";

    private Long productId = 1L;

    private PictureBuilder() {}

    public static PictureBuilder picture() {
        return new PictureBuilder();
    }

    private PictureBuilder(PictureBuilder builder) {
        this.storageUUID = builder.storageUUID;
        this.name = builder.name;
        this.contentType = builder.contentType;
        this.productId = builder.productId;
    }

    public PictureBuilder withStorageUUID(String storageUUID) {
        PictureBuilder builder = new PictureBuilder(this);
        builder.storageUUID = storageUUID;
        return builder;
    }

    public PictureBuilder withName(String name) {
        PictureBuilder builder = new PictureBuilder(this);
        builder.name = name;
        return builder;
    }

    public PictureBuilder withContentType(String contentType) {
        PictureBuilder builder = new PictureBuilder(this);
        builder.contentType = contentType;
        return builder;
    }

    public PictureBuilder withProductId(Long productId) {
        PictureBuilder builder = new PictureBuilder(this);
        builder.productId = productId;
        return builder;
    }

    @Override
    public Picture build() {
        Picture picture = new Picture(storageUUID, contentType, productId);
        picture.setName(name);
        return picture;
    }

    public static String toUuid(int i) {
        String uuidFormat = String.format("00000000-0000-0000-a000-%011d", i);
        return UUID.fromString(uuidFormat).toString();
    }
}
