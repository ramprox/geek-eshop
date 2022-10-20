package ru.ramprox.repository.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import ru.ramprox.repository.service.util.DateTimeService;
import ru.ramprox.persist.repositories.OrderRepository;
import ru.ramprox.persist.repositories.ProductRepository;
import ru.ramprox.persist.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    private OrderService orderService;

    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;
    private SimpMessagingTemplate template;
    private RabbitTemplate rabbitTemplate;
    private DateTimeService dateTimeService;

    @BeforeEach
    public void init() {
        orderRepository = mock(OrderRepository.class);
        productRepository = mock(ProductRepository.class);
        userRepository = mock(UserRepository.class);
        rabbitTemplate = mock(RabbitTemplate.class);
        template = mock(SimpMessagingTemplate.class);
        dateTimeService = mock(DateTimeService.class);
        orderService = new OrderServiceImpl(
                orderRepository,
                productRepository,
                userRepository,
                rabbitTemplate,
                template, dateTimeService);
    }

    @Test
    @SneakyThrows
    public void testFindOrdersByUsername() {
//        String username = "user";
//        List<OrderDto> expectedOrderDtos = new ArrayList<>();
//        expectedOrderDtos.add(new OrderDto(1L, LocalDateTime.now().toString(), "1234", Order.Status.NEW.getDescription()));
//        expectedOrderDtos.add(new OrderDto(2L, LocalDateTime.now().toString(), "1234", Order.Status.NEW.getDescription()));
//
//        User user = new User(username, "password");
//        List<Order> orders = new ArrayList<>();
//        for (OrderDto orderDto : expectedOrderDtos) {
//            Order order = new Order();
//            user.addOrder(order);
//            ReflectionUtils.setField(order.getClass().getField("id"), order, 1L);
//            order.setCreatedAt(LocalDateTime.parse(orderDto.getCreatedAt()));
//            order.setStatus(Order.Status.valueOf(orderDto.getStatus().toUpperCase()));
//            Brand brand = new Brand("brand1");
//            Category category = new Category("category1");
//            Product product = new Product("name");
//            category.addProduct(product);
//            brand.addProduct(product);
//            List.of(new OrderDetail(product, 1, new BigDecimal("1234"))).forEach(orderDetail -> {
//                order.addOrderDetail(orderDetail);
//                product.addOrderDetail(orderDetail);
//            });
//            orders.add(order);
//        }

//        when(orderRepository.findAllByUsername(username))
//                .thenReturn(orders);
//
//        List<OrderDto> result = orderService.findOrdersByUsername(username);
//
//        assertNotNull(result);
//        assertEquals(expectedOrderDtos.size(), result.size());
//        assertEqualsOrderDtos(expectedOrderDtos, result);
    }

    @Test
    @SneakyThrows
    public void testCreateOrder() {
//        List<LineItem> lineItems = new ArrayList<>();
//        ProductDto productDto = new ProductDto(1L, "Product1", "1234");
//        LineItem lineItem = new LineItem(productDto, "color1", "material1", 2);
//        lineItems.add(lineItem);
//        productDto = new ProductDto(2L, "Product2", "45689");
//        lineItem = new LineItem(productDto, "color2", "material2", 5);
//        lineItems.add(lineItem);
//
//        List<Product> products = new ArrayList<>();
//        for (LineItem li : lineItems) {
//            Product product = new Product(li.getProductDto().getName());
//            ReflectionUtils.setField(product.getClass().getField("id"), product, li.getProductDto().getId());
//            product.setPrice(new BigDecimal(li.getProductDto().getCost()));
//            products.add(product);
//        }
//        when(productRepository.findAllByIdIn(Arrays.asList(1L, 2L)))
//                .thenReturn(products);
//        User user = new User("user", "password");
//        ReflectionUtils.setField(user.getClass().getField("id"), user, 1L);
//        when(userRepository.findAllByUsername(user.getUsername()))
//                .thenReturn(Optional.of(user));
//
//        LocalDateTime expectedCreatedAt = LocalDateTime.now();
//        Order expectedOrder = new Order();
//        user.addOrder(expectedOrder);
//        when(dateTimeService.now()).thenReturn(expectedCreatedAt);
//        expectedOrder.setProducts(lineItems.stream().map(li -> {
//            Product product = new Product(li.getProductDto().getName());
//            ReflectionUtils.setField(product.getClass().getField("id"), product, li.getProductDto().getId());
//            product.setCost(new BigDecimal(li.getProductDto().getCost()));
//            return new OrderDetail(expectedOrder, product, li.getQty(), product.getCost());
//        }).collect(Collectors.toList()));
//        expectedOrder.setStatus(Order.Status.NEW);
//        expectedOrder.setUser(user);
//        expectedOrder.setCreatedAt(expectedCreatedAt);
//
//        orderService.createOrder(lineItems, user.getUsername());
//
//        ArgumentCaptor<Order> captorOrder = ArgumentCaptor.forClass(Order.class);
//        verify(orderRepository).save(captorOrder.capture());
//        Order resultOrder = captorOrder.getValue();
//        assertNotNull(resultOrder);
//        assertEqualsOrders(Arrays.asList(expectedOrder), Arrays.asList(resultOrder));
    }
}
