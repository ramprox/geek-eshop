//package ru.ramprox.service1.product.controller;
//
//import org.junit.jupiter.api.*;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import ru.ramprox.service1.product.controller.config.TestConfig;
//import ru.ramprox.persist.model.*;
//import ru.ramprox.repository.service.CartService;
//import ru.ramprox.repository.service.OrderService;
//import ru.ramprox.repository.service.ProductService;
//import ru.ramprox.repository.service.util.DateTimeService;
//import ru.ramprox.persist.repositories.*;
//
//import javax.sql.DataSource;
//import javax.transaction.Transactional;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
////@ActiveProfiles("test")
////@TestPropertySource(locations = "classpath:application-test.properties")
////@AutoConfigureMockMvc
////@SpringBootTest
////@TestInstance(TestInstance.Lifecycle.PER_CLASS)
////@Import(TestConfig.class)
//public class OrderControllerTest {
//    @Autowired private MockMvc mvc;
//
//    @MockBean private RabbitTemplate rabbitTemplate;
//
//    @MockBean private SimpMessagingTemplate template;
//
//    @Autowired private CartService cartService;
//
//    @Autowired private OrderService orderService;
//
//    @Autowired private ProductRepository productRepository;
//
//    @Autowired private CategoryRepository categoryRepository;
//
//    @Autowired private BrandRepository brandRepository;
//
//    @Autowired private RoleRepository roleRepository;
//
//    @Autowired private UserRepository userRepository;
//
//    @Autowired private OrderRepository orderRepository;
//
//    @MockBean private DateTimeService dateTimeService;
//
//    @BeforeAll
//    public void init() {
//        saveBrandsAndCategories();
//        saveProducts();
//        saveRolesAndUsers();
//    }
//
//    @BeforeEach
//    public void beforeEach(@Autowired DataSource dataSource) throws SQLException {
//        orderRepository.deleteAll();
//        resetIds(dataSource, "orders", "order_product");
//    }
//
//    @AfterAll
//    public void clearDB(@Autowired DataSource dataSource) throws SQLException {
//        productRepository.deleteAll();
//        brandRepository.deleteAll();
//        categoryRepository.deleteAll();
//
//        resetIds(dataSource, "products", "brands", "categories");
//    }
//
//    private void resetIds(DataSource dataSource, String... tables) throws SQLException {
//        try (Connection connection = dataSource.getConnection()) {
//            for (String table : tables) {
//                PreparedStatement preparedStatement =
//                        connection.prepareStatement(String.format("ALTER TABLE %s ALTER COLUMN id RESTART WITH 1;", table));
//                preparedStatement.execute();
//            }
//        }
//    }
//
//    private void saveBrandsAndCategories() {
//        for (int i = 1; i <= 3; i++) {
//            categoryRepository.save(new Category("Category" + i));
//            brandRepository.save(new Brand("Brand" + i));
//        }
//    }
//
//    private void saveProducts() {
////        Random rand = new Random();
////        for (int i = 1; i <= 6; i++) {
////            Category category = categoryRepository.findById((i + 1) / 2L).get();
////            Brand brand = brandRepository.findById((i + 1) / 2L).get();
////            Product product = new Product("Product" + i);
////            product.setPrice(BigDecimal.valueOf(rand.nextInt(5000) + 5000 / 100.0));
////            productRepository.save(product);
////        }
//    }
//
//    private void saveRolesAndUsers() {
////        Set<Role> roles = Set.of(new Role("ROLE_ADMIN"));
////        roleRepository.saveAll(roles);
////        User user = new User("admin", "admin");
////        user.setAge(30);
////        roles.forEach(user::addRole);
////        userRepository.save(user);
//    }
//
//    @Test
//    public void testFindOrdersByUserUnauthorized() throws Exception {
////        mvc.perform(MockMvcRequestBuilders
////                .get("/order")
////                .contentType(MediaType.APPLICATION_JSON))
////                .andExpect(status().isUnauthorized());
//    }
//
////    @WithMockUser(value = "admin", password = "admin", roles = {"ADMIN"})
////    @Test
////    @Transactional
////    public void testFindOrdersByUserAuthorized() throws Exception {
//////        User user = userRepository.findAllByUsername("admin").get();
//////        for (int i = 1; i <= 2; i++) {
//////            Order order = new Order();
//////            order.setUser(user);
//////            int qty = i + 2;
//////            List<OrderDetail> orderDetails = productRepository.findAllByIdIn(Arrays.asList((long) i, i + 1L))
//////                    .stream()
//////                    .map(product -> {
//////                        OrderDetail orderDetail = new OrderDetail(product, qty, product.getPrice());
//////                        orderDetail.setOrder(order);
//////                        return orderDetail;
//////                    })
//////                    .collect(Collectors.toList());
//////            orderRepository.save(order);
//////        }
//////        List<OrderDto> expectedOrders = orderService.findOrdersByUsername("admin");
//////
//////        ObjectMapper mapper = new ObjectMapper();
//////        mvc.perform(MockMvcRequestBuilders
//////                .get("/order")
//////                .contentType(MediaType.APPLICATION_JSON))
//////                .andExpect(status().isOk())
//////                .andDo(mvcResult -> {
//////                    List<OrderDto> result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
//////                            new TypeReference<List<OrderDto>>() {
//////                            });
//////                    assertNotNull(result);
//////                    assertEquals(expectedOrders.size(), result.size());
//////                    assertEqualsOrderDtos(expectedOrders, result);
//////                });
////    }
////
////    @Test
////    public void testCreateOrderUnauthorized(@Autowired ProductService productService) throws Exception {
////        mvc.perform(MockMvcRequestBuilders
////                .post("/order/create")
////                .contentType(MediaType.APPLICATION_JSON)
////                .with(csrf().asHeader()))
////                .andExpect(status().isUnauthorized());
////    }
//
////    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
////    @Test
////    @Transactional
////    public void testCreateOrderAuthorized(@Autowired ProductService productService) throws Exception {
//////        User user = userRepository.findAllByUsername("admin").get();
//////        for (int i = 1; i <= 2; i++) {
//////            ProductDto productDto = productService.findByIdForCart((long) i).get();
//////            cartService.addProductQty(productDto, "color" + i, "material" + i, i + 1);
//////        }
//////        List<LineItem> lineItems = cartService.getLineItems();
//////        Order expectedOrder = new Order();
//////        user.addOrder(expectedOrder);
//////        ReflectionUtils.setField(expectedOrder.getClass().getField("id"), expectedOrder, 1L);
//////        LocalDateTime expectedCreatedAt = LocalDateTime.now();
//////        when(dateTimeService.now()).thenReturn(expectedCreatedAt);
//////        List<OrderDetail> orderDetails = new ArrayList<>();
//////        for (int i = 0; i < lineItems.size(); i++) {
//////            LineItem lineItem = lineItems.get(i);
//////            Product product = productRepository.findById(lineItem.getProductId()).get();
//////            OrderDetail orderDetail = new OrderDetail(product,
//////                    lineItem.getQty(), product.getPrice());
//////            ReflectionUtils.setField(orderDetail.getClass().getField("id"), orderDetail, i + 1L);
//////            expectedOrder.addOrderDetail(orderDetail);
//////            product.addOrderDetail(orderDetail);
//////            orderDetails.add(orderDetail);
//////        }
//////        expectedOrder.setCreatedAt(expectedCreatedAt);
//////
//////        mvc.perform(MockMvcRequestBuilders
//////                .post("/order/create")
//////                .contentType(MediaType.APPLICATION_JSON)
//////                .with(csrf().asHeader()))
//////                .andExpect(status().isOk());
//////        assertEquals(0, cartService.getLineItems().size());
//////
//////        List<Order> resultOrders = orderRepository.findAll();
//////        assertEquals(1, resultOrders.size());
//////
//////        assertEqualsOrders(Arrays.asList(expectedOrder), resultOrders);
////    }
//}
