package ru.geekbrains.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import ru.geekbrains.controller.dto.OrderDto;
import ru.geekbrains.controller.dto.ProductDto;
import ru.geekbrains.persist.OrderStatusConverter;
import ru.geekbrains.persist.model.Order;
import ru.geekbrains.persist.model.OrderDetail;
import ru.geekbrains.persist.model.Product;
import ru.geekbrains.persist.model.User;
import ru.geekbrains.persist.projection.OrderInfoBackend;
import ru.geekbrains.persist.projection.ProductSaveOrder;
import ru.geekbrains.persist.repositories.OrderDetailRepository;
import ru.geekbrains.persist.repositories.OrderRepository;
import ru.geekbrains.persist.repositories.ProductRepository;
import ru.geekbrains.persist.repositories.UserRepository;
import ru.geekbrains.service.dto.LineItem;
import ru.geekbrains.service.util.DateTimeService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static ru.geekbrains.Util.assertEqualsOrderDtos;
import static ru.geekbrains.Util.assertEqualsOrders;

public class OrderServiceTest {

    private OrderService orderService;

    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;
    private OrderDetailRepository orderDetailRepository;
    private SimpMessagingTemplate template;
    private RabbitTemplate rabbitTemplate;
    private DateTimeService dateTimeService;

    @BeforeEach
    public void init() {
        orderRepository = mock(OrderRepository.class);
        productRepository = mock(ProductRepository.class);
        userRepository = mock(UserRepository.class);
        orderDetailRepository = mock(OrderDetailRepository.class);
        rabbitTemplate = mock(RabbitTemplate.class);
        template = mock(SimpMessagingTemplate.class);
        dateTimeService = mock(DateTimeService.class);
        orderService = new OrderServiceImpl(
                orderRepository,
                productRepository,
                userRepository,
                orderDetailRepository,
                rabbitTemplate,
                template, dateTimeService);
    }

    @Test
    public void testFindOrdersByUsername() {
        String username = "user";
        List<OrderDto> expectedOrderDtos = new ArrayList<>();
        expectedOrderDtos.add(new OrderDto(1L, LocalDateTime.now().toString(), "1234", Order.Status.NEW.getDescription()));
        expectedOrderDtos.add(new OrderDto(2L, LocalDateTime.now().toString(), "56789", Order.Status.NEW.getDescription()));

        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        List<OrderInfoBackend> orders = new ArrayList<>();
        for (OrderDto orderDto : expectedOrderDtos) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", orderDto.getId());
            map.put("createdAt", orderDto.getCreatedAt());
            map.put("status", new OrderStatusConverter().convertToEntityAttribute(orderDto.getStatus()));
            map.put("cost", orderDto.getPrice());
            OrderInfoBackend orderInfoBackend = factory.createProjection(OrderInfoBackend.class, map);
            orders.add(orderInfoBackend);
        }

        when(orderDetailRepository.findOrdersByUsername(username))
                .thenReturn(orders);

        List<OrderDto> result = orderService.findOrdersByUsername(username);

        assertNotNull(result);
        assertEquals(expectedOrderDtos.size(), result.size());
        assertEqualsOrderDtos(expectedOrderDtos, result);
    }

    @Test
    public void testCreateOrder() {
        List<LineItem> lineItems = new ArrayList<>();
        ProductDto productDto = new ProductDto(1L, "Product1", "1234");
        LineItem lineItem = new LineItem(productDto, "color1", "material1", 2);
        lineItems.add(lineItem);
        productDto = new ProductDto(2L, "Product2", "45689");
        lineItem = new LineItem(productDto, "color2", "material2", 5);
        lineItems.add(lineItem);

        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        List<ProductSaveOrder> products = new ArrayList<>();
        for (LineItem li : lineItems) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", li.getProductDto().getId());
            map.put("cost", li.getProductDto().getCost());
            ProductSaveOrder orderInfoBackend = factory.createProjection(ProductSaveOrder.class, map);
            products.add(orderInfoBackend);
        }
        when(productRepository.findAllByIdIn(Arrays.asList(1L, 2L)))
                .thenReturn(products);
        User user = new User();
        user.setId(1L);
        user.setUsername("user");
        when(userRepository.findAllByUsername(user.getUsername()))
                .thenReturn(Optional.of(user));

        LocalDateTime expectedCreatedAt = LocalDateTime.now();
        Order expectedOrder = new Order();
        when(dateTimeService.now()).thenReturn(expectedCreatedAt);
        expectedOrder.setProducts(lineItems.stream().map(li -> {
            Product product = new Product(li.getProductDto().getId(),
                    li.getProductDto().getName(), new BigDecimal(li.getProductDto().getCost()));
            return new OrderDetail(expectedOrder, product, li.getQty(), product.getCost());
        }).collect(Collectors.toList()));
        expectedOrder.setStatus(Order.Status.NEW);
        expectedOrder.setUser(user);
        expectedOrder.setCreatedAt(expectedCreatedAt);

        orderService.createOrder(lineItems, user.getUsername());

        ArgumentCaptor<Order> captorOrder = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(captorOrder.capture());
        Order resultOrder = captorOrder.getValue();
        assertNotNull(resultOrder);
        assertEqualsOrders(Arrays.asList(expectedOrder), Arrays.asList(resultOrder));
    }
}
