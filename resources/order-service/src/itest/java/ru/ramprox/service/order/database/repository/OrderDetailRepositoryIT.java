package ru.ramprox.service.order.database.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ramprox.service.order.database.entity.OrderDetail;
import ru.ramprox.service.order.util.OrderDetailBuilder;
import ru.ramprox.util.itest.DatabaseFacade;
import ru.ramprox.util.itest.annotations.RepositoryITest;
import ru.ramprox.util.querylogging.ConfigLog4jdbc;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ramprox.service.order.util.OrderDetailBuilder.orderDetail;

@DisplayName("Интеграционные тесты для OrderRepository")
@RepositoryITest(profiles = "itest", importConfigs = ConfigLog4jdbc.class)
public class OrderDetailRepositoryIT {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private DatabaseFacade db;

    @DisplayName("Извлечение детальной информации о заказе по его id")
    @Test
    public void findByOrderIdTest() {
        OrderDetailBuilder orderDetail = orderDetail().withOrderId(1L);
        List<OrderDetail> expectedOrderDetails = db.persist(
                orderDetail.withProductId(1L).withPrice(new BigDecimal("100")),
                orderDetail.withProductId(2L).withPrice(new BigDecimal("100"))
        );

        List<OrderDetail> actualOrderDetails = orderDetailRepository.findByOrderDetailIDOrderId(1L);

        assertThat(actualOrderDetails)
                .extracting(OrderDetail::getOrderDetailID)
                .containsExactly(expectedOrderDetails.get(0).getOrderDetailID(),
                        expectedOrderDetails.get(1).getOrderDetailID());
    }

}
