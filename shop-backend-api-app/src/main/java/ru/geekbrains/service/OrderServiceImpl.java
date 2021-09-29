package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.controller.dto.OrderDetails;
import ru.geekbrains.controller.dto.OrderDto;
import ru.geekbrains.controller.dto.ProductDto;
import ru.geekbrains.persist.model.Order;
import ru.geekbrains.persist.model.Product;
import ru.geekbrains.persist.model.User;
import ru.geekbrains.persist.projection.ProductSaveOrder;
import ru.geekbrains.persist.repositories.OrderDetailRepository;
import ru.geekbrains.persist.repositories.OrderRepository;
import ru.geekbrains.persist.repositories.ProductRepository;
import ru.geekbrains.persist.repositories.UserRepository;
import ru.geekbrains.service.dto.LineItem;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository,
                            UserRepository userRepository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public List<OrderDto> findOrdersByUsername(String username) {
        return orderDetailRepository.findOrdersByUsername(username).stream()
                .map(order -> new OrderDto(order.getId(),
                        order.getCreatedAt(), order.getCost(),
                        order.getStatus().getDescription()))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void createOrder(List<LineItem> lineItems, String username) {
        List<ProductSaveOrder> products = productRepository.findAllByIdIn(lineItems.stream()
                .map(LineItem::getProductId)
                .collect(Collectors.toList()));
        User user = userRepository.findAllByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Order order = new Order(user, Order.Status.NEW);
        lineItems.forEach(lineItem -> {
            Product product = products.stream()
                    .filter(prod -> prod.getId().equals(lineItem.getProductId()))
                    .findFirst()
                    .map(productSaveOrder -> new Product(productSaveOrder.getId(), productSaveOrder.getCost()))
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            order.addProduct(product, lineItem.getQty());
        });
        orderRepository.save(order);
    }

    @Override
    public OrderDetails findAllInfoById(Long id, String username) {
        OrderDto orderDto = orderDetailRepository
                .findOrderByIdAndUsername(id, username)
                .map(orderInfoBackend -> new OrderDto(orderInfoBackend.getId(),
                        orderInfoBackend.getCreatedAt(),
                        orderInfoBackend.getCost(),
                        orderInfoBackend.getStatus().getDescription()))
                .orElseThrow(() -> new RuntimeException("Order not found"));

        List<LineItem> orderLineItems = orderDetailRepository.findByOrderId(id).stream()
                .map(orderDetailBackend -> {
                    ProductDto productDto = new ProductDto(orderDetailBackend.getProductName(),
                            orderDetailBackend.getCost());
                    LineItem lineItem = new LineItem(productDto, "", "");
                    lineItem.setQty(orderDetailBackend.getQty());
                    return lineItem;
                }).collect(Collectors.toList());
        return new OrderDetails(orderDto, orderLineItems);
    }

    @Transactional
    @Override
    public void cancelOrder(Long id) {
        orderRepository.setOrderStatus(id, Order.Status.CANCELED);
    }
}