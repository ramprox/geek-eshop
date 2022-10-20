package ru.ramprox.service.order.database.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ramprox.service.order.database.entity.Order;
import ru.ramprox.service.order.util.OrderBuilder;
import ru.ramprox.util.itest.DatabaseFacade;
import ru.ramprox.util.itest.annotations.RepositoryITest;
import ru.ramprox.util.querylogging.ConfigLog4jdbc;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ramprox.service.order.util.OrderBuilder.order;

@DisplayName("Интеграционные тесты для OrderRepository")
@RepositoryITest(profiles = "itest", importConfigs = ConfigLog4jdbc.class)
public class OrderRepositoryIT {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DatabaseFacade db;

    @DisplayName("Извлечение всех заказов по id пользователя")
    @Test
    public void findAllByUserIdTest() {
        OrderBuilder order = order().withUserId(1L);
        List<Order> expectedOrders = db.persist(
                order.withPrice(new BigDecimal("100")),
                order.withPrice(new BigDecimal("200"))
        );

        List<Order> actualOrders = orderRepository.findAllByUserId(1L);

        assertThat(actualOrders)
                .extracting(Order::getId)
                .containsExactly(expectedOrders.get(0).getId(),
                        expectedOrders.get(1).getId());
    }

}
