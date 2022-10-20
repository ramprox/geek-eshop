//package ru.ramprox.service1.product.controller;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.*;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import ru.ramprox.service1.product.controller.config.TestConfig;
//import ru.ramprox.service1.product.controller.dto.ProductDto;
//import ru.ramprox.persist.repositories.*;
//import ru.ramprox.repository.service.ProductService;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.util.Map;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.hamcrest.Matchers.hasSize;
//import static org.hamcrest.Matchers.is;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@AutoConfigureMockMvc
//@SpringBootTest
//@Import(TestConfig.class)
//@ActiveProfiles("test")
//public class ProductControllerTest {
//
//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private RabbitTemplate rabbitTemplate;
//
//    @MockBean
//    private SimpMessagingTemplate template;
//
//    @Autowired
//    private ProductService productService;
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @BeforeEach
//    public void init(@Autowired BrandRepository brandRepository,
//                     @Autowired CategoryRepository categoryRepository) {
//    }
//
//    @AfterEach
//    public void clearDB(@Autowired ProductRepository productRepository,
//                        @Autowired BrandRepository brandRepository,
//                        @Autowired CategoryRepository categoryRepository,
//                        @Autowired DataSource dataSource) throws SQLException {
//        productRepository.deleteAllInBatch();
//        brandRepository.deleteAllInBatch();
//        categoryRepository.deleteAllInBatch();
//        resetIds(dataSource, "products", "brands", "categories", "product_details_info");
//    }
//
//    private void resetIds(DataSource dataSource, String... tables) throws SQLException {
//        try(Connection connection = dataSource.getConnection()) {
//            for(String table: tables) {
//                PreparedStatement preparedStatement =
//                        connection.prepareStatement(String.format("ALTER TABLE %s AUTO_INCREMENT = 1", table));
//                preparedStatement.execute();
//            }
//        }
//    }
//
//    @DisplayName("Получение всех продуктов с фильтрацией по умолчанию")
//    @Test
//    public void testFindAll() throws Exception {
////        MvcResult result = mvc.perform(MockMvcRequestBuilders
////                .get("/product/all"))
////                .andDo(MockMvcResultHandlers.print())
////                .andExpect(status().isOk())
////                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
////                .andReturn();
////        RestPageImpl<ProductDto> pageProductDto = objectMapper.readValue(result.getResponse().getContentAsString(),
////                new TypeReference<>() {});
////        assertThat(pageProductDto.getTotalElements()).isEqualTo(1);
////        ProductDto productDto = pageProductDto.get().collect(Collectors.toList()).get(0);
////        assertThat(productDto.getId()).isEqualTo(1L);
////        assertThat(productDto.getName()).isEqualTo("Product");
////        assertThat(productDto.getPrice()).isEqualTo("1234.00");
//    }
//
//    @DisplayName("Получение полной информации о продукте")
//    @Test
//    public void testFindById() throws Exception {
////        MvcResult result = mvc.perform(MockMvcRequestBuilders
////                .get("/product/1"))
////                .andDo(MockMvcResultHandlers.print())
////                .andExpect(status().isOk())
////                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
////                .andReturn();
////        ProductDto actualProductDto = objectMapper.readValue(result.getResponse().getContentAsString(),
////                new TypeReference<>() {});
////        assertThat(actualProductDto).isNotNull();
////        assertThat(actualProductDto.getId()).isEqualTo(1);
////        assertThat(actualProductDto.getName()).isEqualTo("Product");
////        assertThat(actualProductDto.getPrice()).isEqualTo("1234.00");
////        Map<String, String> productDetails = actualProductDto.getProductDetails();
////        assertThat(productDetails.get("Description")).isEqualTo("description");
//    }
//}
