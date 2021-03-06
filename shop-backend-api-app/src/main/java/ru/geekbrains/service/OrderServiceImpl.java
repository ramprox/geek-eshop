package ru.geekbrains.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.controller.dto.OrderDetails;
import ru.geekbrains.controller.dto.OrderDto;
import ru.geekbrains.controller.dto.ProductDto;
import ru.geekbrains.persist.model.Order;
import ru.geekbrains.persist.model.OrderDetail;
import ru.geekbrains.persist.model.Product;
import ru.geekbrains.persist.model.User;
import ru.geekbrains.persist.repositories.OrderRepository;
import ru.geekbrains.persist.repositories.ProductRepository;
import ru.geekbrains.persist.repositories.UserRepository;
import ru.geekbrains.service.dto.LineItem;
import ru.geekbrains.service.dto.OrderMessage;
import ru.geekbrains.service.util.DateTimeService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final RabbitTemplate rabbitTemplate;
    private final SimpMessagingTemplate template;
    private final DateTimeService dateTimeService;

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository,
                            UserRepository userRepository,
                            RabbitTemplate rabbitTemplate, SimpMessagingTemplate template,
                            DateTimeService dateTimeService) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.template = template;
        this.dateTimeService = dateTimeService;
    }

    @Override
    public List<OrderDto> findOrdersByUsername(String username) {
        return orderRepository.findAllByUsername(username).stream()
                .map(order -> new OrderDto(order.getId(),
                        order.getCreatedAt().toString(), getCost(order.getProducts()).toString(),
                        order.getStatus().getDescription()))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void createOrder(List<LineItem> lineItems, String username) {
        List<Product> products = productRepository.findAllByIdIn(lineItems.stream()
                .map(LineItem::getProductId)
                .collect(Collectors.toList()));
        User user = userRepository.findAllByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Order order = new Order(user, Order.Status.NEW);
        order.setCreatedAt(dateTimeService.now());
        lineItems.forEach(lineItem -> {
            Product product = products.stream()
                    .filter(prod -> prod.getId().equals(lineItem.getProductId()))
                    .findFirst()
                    .map(productSaveOrder -> {
                        Product product1 = new Product();
                        product1.setId(productSaveOrder.getId());
                        product1.setCost(productSaveOrder.getCost());
                        return product1;
                    })
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            order.addProduct(product, lineItem.getQty());
        });
        orderRepository.save(order);
        rabbitTemplate.convertAndSend("order.exchange", "new_order", new OrderMessage(order.getId(), order.getStatus().name()));
    }

    @Override
    public OrderDetails findAllInfoByIdAndUsername(Long id, String username) {
        return orderRepository
                .findByIdAndUsername(id, username)
                .map(order -> {
                    OrderDto orderDto = new OrderDto(order.getId(),
                            order.getCreatedAt().toString(),
                            getCost(order.getProducts()).toString(),
                            order.getStatus().getDescription());
                    List<LineItem> orderLineItems = order.getProducts().stream()
                            .map(orderDetailBackend -> {
                                ProductDto productDto = new ProductDto(orderDetailBackend.getProduct().getName(),
                                        orderDetailBackend.getCost().toString());
                                LineItem lineItem = new LineItem(productDto, "", "");
                                lineItem.setQty(orderDetailBackend.getQty());
                                return lineItem;
                            }).collect(Collectors.toList());
                    return new OrderDetails(orderDto, orderLineItems);
                })
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    private BigDecimal getCost(List<OrderDetail> orderDetails) {
        return orderDetails.stream()
                .map(OrderDetail::getCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transactional
    @Override
    public void cancelOrder(Long id) {
        orderRepository.setOrderStatus(id, Order.Status.CANCELED);
    }

    @Transactional
    @RabbitListener(queues = "processed.order.queue")
    public void receive(OrderMessage order) {
        Order.Status newStatus = Order.Status.valueOf(order.getState());
        orderRepository.setOrderStatus(order.getId(), newStatus);
        logger.info("Order with id {} change state to {}", order.getId(), order.getState());
        order.setState(newStatus.getDescription());
        template.convertAndSend("/order_out/order", order);
    }
}
