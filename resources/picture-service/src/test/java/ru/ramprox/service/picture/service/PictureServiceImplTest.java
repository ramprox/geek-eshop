package ru.ramprox.service.picture.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ramprox.service.picture.database.repository.PictureRepository;

import java.nio.file.Path;

@DisplayName("Unit тесты PictureServiceFileImpl")
@ExtendWith(MockitoExtension.class)
public class PictureServiceImplTest {

    private PictureService pictureService;

    @Mock
    private PictureRepository pictureRepository;

    @TempDir
    Path tempDir;

    @BeforeEach
    public void beforeEach() {

    }

    @Test
    public void saveTest() {
        System.out.println("123123");
    }

}
