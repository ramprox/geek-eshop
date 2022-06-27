package ru.geekbrains.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.geekbrains.controller.config.TestConfig;
import ru.geekbrains.controller.dto.AddLineItemDto;
import ru.geekbrains.controller.dto.AllCartDto;
import ru.geekbrains.controller.dto.ProductDto;
import ru.geekbrains.persist.model.Brand;
import ru.geekbrains.persist.model.Category;
import ru.geekbrains.persist.model.Product;
import ru.geekbrains.persist.repositories.BrandRepository;
import ru.geekbrains.persist.repositories.CategoryRepository;
import ru.geekbrains.persist.repositories.ProductRepository;
import ru.geekbrains.service.CartService;
import ru.geekbrains.service.CartServiceImpl;
import ru.geekbrains.service.ProductService;
import ru.geekbrains.service.dto.LineItem;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.geekbrains.Util.assertEqualsLineItems;

@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(TestConfig.class)
public class CartControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private SimpMessagingTemplate template;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @BeforeAll
    public void init(@Autowired ProductRepository productRepository,
                     @Autowired BrandRepository brandRepository,
                     @Autowired CategoryRepository categoryRepository) {
        Category category = categoryRepository.save(new Category(null, "Category1"));
        Brand brand = brandRepository.save(new Brand(null, "Brand1"));
        productRepository.save(new Product(null, "Product1", new BigDecimal(1234), category, brand));

        category = categoryRepository.save(new Category(null, "Category2"));
        brand = brandRepository.save(new Brand(null, "Brand2"));
        productRepository.save(new Product(null, "Product2", new BigDecimal(5678), category, brand));
    }

    @BeforeEach
    public void clearCartService() {
        cartService.removeAll();
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
        try (Connection connection = dataSource.getConnection()) {
            for (String table : tables) {
                PreparedStatement preparedStatement =
                        connection.prepareStatement(String.format("ALTER TABLE %s ALTER COLUMN id RESTART WITH 1;", table));
                preparedStatement.execute();
            }
        }
    }

    @Test
    public void testAddToCart() throws Exception {

        CartService etalonCartService = new CartServiceImpl();
        ProductDto productDto = productService.findByIdForCart(1L).get();
        LineItem lineItem = new LineItem(productDto, "color1", "material1", 2);
        etalonCartService.addProductQty(lineItem.getProductDto(), lineItem.getColor(), lineItem.getMaterial(), lineItem.getQty());
        List<LineItem> expectedLineItems = etalonCartService.getLineItems();

        ObjectMapper mapper = new ObjectMapper();
        AddLineItemDto addLineItemDto = new AddLineItemDto(lineItem.getProductId(), lineItem.getQty(), lineItem.getColor(), lineItem.getMaterial());

        mvc.perform(MockMvcRequestBuilders
                .post("/cart")
                .content(mapper.writeValueAsString(addLineItemDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(mvcResult -> {
                    List<LineItem> resultLineItems =
                            mapper.readValue(mvcResult.getResponse().getContentAsString(),
                                    new TypeReference<List<LineItem>>() {
                                    });
                    assertNotNull(resultLineItems);
                    assertEquals(expectedLineItems.size(), resultLineItems.size());
                    assertEqualsLineItems(expectedLineItems, resultLineItems);
                });
    }

    @Test
    public void testFindAll() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        CartService etalonCartService = new CartServiceImpl();
        for (int i = 1; i < 3; i++) {
            ProductDto productDto = productService.findByIdForCart((long) i).get();
            LineItem lineItem = new LineItem(productDto, "color" + i, "material" + i, i + 1);
            cartService.addProductQty(lineItem.getProductDto(), lineItem.getColor(), lineItem.getMaterial(), lineItem.getQty());
            etalonCartService.addProductQty(lineItem.getProductDto(), lineItem.getColor(), lineItem.getMaterial(), lineItem.getQty());
        }

        AllCartDto expectedAllCartDto = new AllCartDto(etalonCartService.getLineItems(), etalonCartService.getSubTotal());

        mvc.perform(MockMvcRequestBuilders
                .get("/cart/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(mvcResult -> {
                    AllCartDto result = mapper.readValue(mvcResult.getResponse().getContentAsString(), AllCartDto.class);
                    assertEqualsAllCartDtos(expectedAllCartDto, result);
                });
    }

    @Test
    public void testDeleteAll() throws Exception {

        for (int i = 1; i < 3; i++) {
            ProductDto productDto = productService.findByIdForCart((long) i).get();
            LineItem lineItem = new LineItem(productDto, "color" + i, "material" + i, i + 1);
            cartService.addProductQty(lineItem.getProductDto(), lineItem.getColor(), lineItem.getMaterial(), lineItem.getQty());
        }

        mvc.perform(MockMvcRequestBuilders
                .delete("/cart"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lineItems", hasSize(0)))
                .andExpect(jsonPath("$.subtotal", is("0")));
    }

    @Test
    public void testDeleteLineItem() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        CartService etalonCartService = new CartServiceImpl();
        int indexDeletingLineItem = 2;

        LineItem deletedLineItem = null;
        for (int i = 1; i < 3; i++) {
            ProductDto productDto = productService.findByIdForCart((long) i).get();
            LineItem lineItem = new LineItem(productDto, "color" + i, "material" + i, i + 1);
            if (i == indexDeletingLineItem) {
                deletedLineItem = lineItem;
            } else {
                etalonCartService.addProductQty(lineItem.getProductDto(), lineItem.getColor(), lineItem.getMaterial(), lineItem.getQty());
            }
            cartService.addProductQty(lineItem.getProductDto(), lineItem.getColor(), lineItem.getMaterial(), lineItem.getQty());
        }

        AllCartDto expectedAllCartDto = new AllCartDto(etalonCartService.getLineItems(), etalonCartService.getSubTotal());

        mvc.perform(MockMvcRequestBuilders
                .delete("/cart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(deletedLineItem)))
                .andExpect(status().isOk())
                .andDo(mvcResult -> {
                    AllCartDto result = mapper.readValue(mvcResult.getResponse().getContentAsString(), AllCartDto.class);
                    assertEqualsAllCartDtos(expectedAllCartDto, result);
                });
    }

    @Test
    public void testUpdateLineItemQty() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        CartService etalonCartService = new CartServiceImpl();

        for (int i = 1; i < 3; i++) {
            ProductDto productDto = productService.findByIdForCart((long) i).get();
            LineItem lineItem = new LineItem(productDto, "color" + i, "material" + i, i + 1);
            cartService.addProductQty(lineItem.getProductDto(), lineItem.getColor(), lineItem.getMaterial(), lineItem.getQty());
            etalonCartService.addProductQty(lineItem.getProductDto(), lineItem.getColor(), lineItem.getMaterial(), lineItem.getQty());
        }

        List<LineItem> lineItems = etalonCartService.getLineItems();
        LineItem updatedLineItem = lineItems.get(1);
        etalonCartService.updateProductQty(updatedLineItem.getProductDto(), updatedLineItem.getColor(), updatedLineItem.getMaterial(), 10);
        AllCartDto expectedAllCartDto = new AllCartDto(etalonCartService.getLineItems(), etalonCartService.getSubTotal());

        mvc.perform(MockMvcRequestBuilders
                .put("/cart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedLineItem)))
                .andExpect(status().isOk())
                .andDo(mvcResult -> {
                    AllCartDto result = mapper.readValue(mvcResult.getResponse().getContentAsString(), AllCartDto.class);
                    assertEqualsAllCartDtos(expectedAllCartDto, result);
                });
    }

    private static void assertEqualsAllCartDtos(AllCartDto expectedAllCartDto, AllCartDto resultAllCartDto) {
        List<LineItem> resultLineItems = resultAllCartDto.getLineItems();
        assertNotNull(resultLineItems);
        List<LineItem> expectedLineItems = expectedAllCartDto.getLineItems();
        assertEquals(expectedLineItems.size(), resultLineItems.size());
        assertEqualsLineItems(expectedLineItems, resultLineItems);
        assertEquals(expectedAllCartDto.getSubtotal(), resultAllCartDto.getSubtotal());
    }
}
