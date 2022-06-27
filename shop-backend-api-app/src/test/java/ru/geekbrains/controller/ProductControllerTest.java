package ru.geekbrains.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.geekbrains.controller.config.TestConfig;
import ru.geekbrains.controller.dto.ProductDto;
import ru.geekbrains.controller.dto.ProductListParams;
import ru.geekbrains.persist.model.Brand;
import ru.geekbrains.persist.model.Category;
import ru.geekbrains.persist.model.Product;
import ru.geekbrains.persist.repositories.BrandRepository;
import ru.geekbrains.persist.repositories.CategoryRepository;
import ru.geekbrains.persist.repositories.ProductRepository;
import ru.geekbrains.service.ProductService;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(TestConfig.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private SimpMessagingTemplate template;

    @Autowired
    private ProductService productService;

    @BeforeAll
    public void init(@Autowired ProductRepository productRepository,
                     @Autowired BrandRepository brandRepository,
                     @Autowired CategoryRepository categoryRepository) {
        Category category = categoryRepository.save(new Category(null, "Category"));
        Brand brand = brandRepository.save(new Brand(null, "Brand"));
        productRepository.save(new Product(null, "Product", new BigDecimal(1234), category, brand));
    }

    @AfterAll
    public void clearDB(@Autowired ProductRepository productRepository,
                        @Autowired BrandRepository brandRepository,
                        @Autowired CategoryRepository categoryRepository,
                        @Autowired DataSource dataSource) throws SQLException {
        productRepository.deleteAll();
        brandRepository.deleteAll();
        categoryRepository.deleteAll();
        resetIds(dataSource, "products", "brands", "categories");
    }

    private void resetIds(DataSource dataSource, String... tables) throws SQLException {
        try(Connection connection = dataSource.getConnection()) {
            for(String table: tables) {
                PreparedStatement preparedStatement =
                        connection.prepareStatement(String.format("ALTER TABLE %s ALTER COLUMN id RESTART WITH 1;", table));
                preparedStatement.execute();
            }
        }
    }

    @Test
    public void testFindAll() throws Exception {

        ProductListParams params = new ProductListParams();
        params.setCategoryId(1L);
        params.setPage(1);
        params.setSize(5);
        params.setSortBy("id");

        Page<ProductDto> expectedPage = productService.findWithFilter(params);

        mvc.perform(MockMvcRequestBuilders
                .get("/product/all")
                .param("categoryId", params.getCategoryId().toString())
                .param("page", params.getPage().toString())
                .param("size", params.getSize().toString())
                .param("sortBy", params.getSortBy())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.content", hasSize(expectedPage.getNumberOfElements())))
                .andExpect(jsonPath("$.page.content[0].name",
                        is(expectedPage.stream().findFirst().get().getName())))
                .andExpect(jsonPath("$.page.content[0].cost", is(expectedPage.stream().findFirst().get().getCost().toString())));
    }

    @Test
    public void testFindById() throws Exception {
        ProductDto expectedProduct = productService.findAllInfoById(1L).get();
        System.out.println(expectedProduct.getCost());
        mvc.perform(MockMvcRequestBuilders
                .get("/product/" + expectedProduct.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expectedProduct.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(expectedProduct.getName())))
                .andExpect(jsonPath("$.cost", is(expectedProduct.getCost().toString())))
                .andExpect(jsonPath("$.categoryDto.id", is(expectedProduct.getCategoryDto().getId()), Long.class))
                .andExpect(jsonPath("$.categoryDto.name", is(expectedProduct.getCategoryDto().getName())))
                .andExpect(jsonPath("$.brandDto.id", is(expectedProduct.getBrandDto().getId()), Long.class))
                .andExpect(jsonPath("$.brandDto.name", is(expectedProduct.getBrandDto().getName())));

        mvc.perform(MockMvcRequestBuilders
                .get("/product/2")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
