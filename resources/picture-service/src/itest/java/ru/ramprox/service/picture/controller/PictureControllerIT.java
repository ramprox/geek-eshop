package ru.ramprox.service.picture.controller;

import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.entity.mime.MultipartPartBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import ru.ramprox.service.picture.config.ControllerTestConfig;
import ru.ramprox.service.picture.database.entity.Picture;
import ru.ramprox.service.picture.database.repository.PictureRepository;
import ru.ramprox.service.picture.dto.ProductPictures;
import ru.ramprox.service.picture.service.PictureService;
import ru.ramprox.service.picture.service.PictureServiceImpl;
import ru.ramprox.service.picture.util.PictureBuilder;
import ru.ramprox.util.itest.DatabaseFacade;
import ru.ramprox.util.itest.annotations.ControllerITest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.*;
import static ru.ramprox.service.picture.util.PictureBuilder.picture;
import static ru.ramprox.service.picture.util.PictureBuilder.toUuid;

@DisplayName("Интеграционные тесты PictureController")
@ControllerITest(profiles = "itest",
        properties = "spring.main.allow-bean-definition-overriding=true",
        importConfigs = { ControllerTestConfig.class, PictureControllerIT.SetStoragePath.class})
public class PictureControllerIT {

    @Autowired
    private DatabaseFacade db;

    @Autowired
    private WebTestClient webTestClient;

    @TempDir
    static Path storagePath;

    @BeforeEach
    public void beforeEach() {
        db.cleanDatabase();
    }

    @DisplayName("Извлечение id изображений для продукта по его id")
    @Test
    public void getPictureIdsTest() {
        Long productId = 1L;
        List<Picture> savedPictures = db.persist(
                picture().withStorageUUID(toUuid(1)),
                picture().withStorageUUID(toUuid(2)),
                picture().withStorageUUID(toUuid(3))
        );

        webTestClient.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/picture")
                                .queryParam("productId", productId)
                                .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Long.class)
                .contains(savedPictures.stream().map(Picture::getId).toArray(Long[]::new));
    }

    @DisplayName("Извлечение байтов изображения по его id")
    @Test
    public void getPictureContentTest() throws IOException {
        String uuid = toUuid(1);
        byte[] expectedContent = randomBytes(20);
        Files.write(storagePath.resolve(uuid), expectedContent);
        Picture savedPicture = db.persist(picture().withStorageUUID(uuid));

        webTestClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/picture/{pictureId}")
                                .build(savedPicture.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.IMAGE_JPEG)
                .expectBody(byte[].class)
                .isEqualTo(expectedContent);
    }

    @DisplayName("Сохранение изображений для продукта")
    @Test
    @WithMockUser(roles = "ADMIN")
    public void savePicturesTest() {
        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
        List<byte[]> expectedContents = List.of(randomBytes(10), randomBytes(10));
        for(int i = 0; i < expectedContents.size(); i++) {
            multipartBodyBuilder
                    .part("file", expectedContents.get(i))
                    .contentType(IMAGE_JPEG)
                    .header("Content-Disposition", "form-data; name=file; filename=filename" + (i + 1));
        }
        webTestClient
                .post()
                .uri(uriBuilder -> uriBuilder.path("/picture").queryParam("productId", 1L).build())
                .contentType(MULTIPART_FORM_DATA)
                .bodyValue(multipartBodyBuilder.build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Long.class)
                .value(actualIds -> assertThat(actualIds).containsExactly(1L, 2L));

        for(int i = 0; i < expectedContents.size(); i++) {
            webTestClient
                    .get()
                    .uri("/picture/" + (i + 1))
                    .exchange()
                    .expectBody(byte[].class)
                    .isEqualTo(expectedContents.get(i));
        }
    }

    private static byte[] randomBytes(int length) {
        Random random = new Random();
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return bytes;
    }

    @TestConfiguration
    static class SetStoragePath {

        @Bean
        public PictureService pictureServiceImpl(PictureRepository pictureRepository) {
            return new PictureServiceImpl(pictureRepository, storagePath.toString());
        }

    }

}
