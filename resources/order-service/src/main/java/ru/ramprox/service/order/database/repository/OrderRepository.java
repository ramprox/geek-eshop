package ru.ramprox.service.order.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.ramprox.service.order.database.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Modifying
    @Query("update Order o set o.status = :status where o.id = :id")
    void setOrderStatus(Long id, Order.Status status);

    @Query("select distinct o from Order o where o.userId = :userId")
    List<Order> findAllByUserId(Long userId);

    @Query("select distinct o from Order o where o.id = :id and o.userId = :userId")
    Optional<Order> findByIdAndUserId(Long id, Long userId);

}
