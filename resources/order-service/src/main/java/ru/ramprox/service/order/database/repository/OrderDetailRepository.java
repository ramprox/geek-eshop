package ru.ramprox.service.order.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.ramprox.service.order.database.entity.OrderDetail;
import ru.ramprox.service.order.database.entity.OrderDetailID;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailID> {

    List<OrderDetail> findByOrderDetailIDOrderId(Long orderId);

}
