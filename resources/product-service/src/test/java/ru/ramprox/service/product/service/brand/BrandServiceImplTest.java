package ru.ramprox.service.product.service.brand;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ramprox.service.product.database.entity.Brand;
import ru.ramprox.service.product.database.repository.BrandRepository;
import ru.ramprox.service.product.dto.BrandDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("Unit тесты для BrandService")
@ExtendWith(MockitoExtension.class)
public class BrandServiceImplTest {

    private BrandService brandService;

    @Mock
    private BrandRepository brandRepository;

    @BeforeEach
    public void beforeEach() {
        brandService = new BrandServiceImpl(brandRepository);
    }

    @DisplayName("Извлечение брэндов отсортированных по имени")
    @Test
    public void findAllByOrderByNameTest() {
        when(brandRepository.findAllByOrderByName()).thenReturn(
                List.of(new Brand("Brand1"),
                        new Brand("Brand2"),
                        new Brand("Brand3"))
        );

        List<BrandDto> actualBrandDtos = brandService.findAllOrderByName();

        assertThat(actualBrandDtos)
                .extracting(BrandDto::getName)
                .containsExactly("Brand1", "Brand2", "Brand3");
    }

}
