package ru.geekbrains.persist.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ActiveProfiles;
import ru.geekbrains.persist.model.Order;
import ru.geekbrains.persist.model.OrderDetail;
import ru.geekbrains.persist.model.Product;
import ru.geekbrains.persist.model.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findAllByUsernameTest() {
        User user = userRepository.findById(2L).orElseThrow();
        List<Product> products = productRepository.findAllByIdIn(List.of(1L, 2L, 3L));
        Order order = new Order();
        List<OrderDetail> expectedOrderDetails = products.stream()
                .map(product -> {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setCost(product.getCost());
                    orderDetail.setQty(1);
                    orderDetail.setProduct(product);
                    orderDetail.setOrder(order);
                    return orderDetail;
                }).collect(Collectors.toList());
        order.setProducts(expectedOrderDetails);
        order.setStatus(Order.Status.NEW);
        order.setUser(user);
        LocalDateTime dateTime = LocalDateTime.now();
        order.setCreatedAt(dateTime);
        Order expectedOrder = orderRepository.save(order);

        List<Order> orders = orderRepository.findAllByUsername(user.getUsername());

        assertEquals(1, orders.size());
        Order resultOrder = orders.get(0);
        assertEquals(expectedOrder.getCreatedAt(), resultOrder.getCreatedAt());
        assertEquals(expectedOrder.getStatus(), resultOrder.getStatus());
        assertEquals(expectedOrder.getUser().getUsername(), resultOrder.getUser().getUsername());
        List<OrderDetail> resultOrderDetails = resultOrder.getProducts();
        assertEquals(expectedOrderDetails.size(), resultOrderDetails.size());
        for(int i = 0; i < expectedOrderDetails.size(); i++) {
            OrderDetail expectedOrderDetail = expectedOrderDetails.get(i);
            OrderDetail resultOrderDetail = resultOrderDetails.get(i);
            assertEquals(expectedOrderDetail.getCost(), resultOrderDetail.getCost());
            assertEquals(expectedOrderDetail.getQty(), resultOrderDetail.getQty());
            assertEquals(expectedOrderDetail.getProduct().getId(), resultOrderDetail.getProduct().getId());
        }
    }



}
