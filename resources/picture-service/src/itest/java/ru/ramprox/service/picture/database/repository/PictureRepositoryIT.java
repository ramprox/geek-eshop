package ru.ramprox.service.picture.database.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ramprox.service.picture.database.entity.Picture;
import ru.ramprox.service.picture.util.PictureBuilder;
import ru.ramprox.util.itest.DatabaseFacade;
import ru.ramprox.util.itest.annotations.RepositoryITest;
import ru.ramprox.util.querylogging.ConfigLog4jdbc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ramprox.service.picture.util.PictureBuilder.picture;
import static ru.ramprox.service.picture.util.PictureBuilder.toUuid;

@DisplayName("интеграционные тесты PictureRepository")
@RepositoryITest(profiles = "itest", importConfigs = ConfigLog4jdbc.class)
public class PictureRepositoryIT {

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private DatabaseFacade db;

    @BeforeEach
    public void beforeEach() {
        db.cleanDatabase();
    }

    @DisplayName("Извлечение изображения по id продукта")
    @Test
    public void findByProductIdTest() {
        PictureBuilder picture = picture().withProductId(1L);
        List<Picture> expectedPictures = db.persist(
                picture.withStorageUUID(toUuid(1)),
                picture.withStorageUUID(toUuid(2)),
                picture.withStorageUUID(toUuid(3)));

        List<Picture> actualPictures = pictureRepository.findByProductId(1L, Picture.class);

        assertThat(actualPictures.size()).isEqualTo(expectedPictures.size());
        assertThat(actualPictures)
                .containsExactlyElementsOf(expectedPictures);
    }

}
