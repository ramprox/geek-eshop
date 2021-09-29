package ru.geekbrains.persist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.geekbrains.persist.model.OrderDetail;
import ru.geekbrains.persist.projection.OrderInfoBackend;
import ru.geekbrains.persist.projection.OrderDetailBackend;

import java.util.List;
import java.util.Optional;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    @Query("select " +
            "od.order.id as id, od.order.createdAt as createdAt, od.order.status as status, " +
            "SUM(od.cost * od.qty) as cost " +
            "from OrderDetail od " +
            "where od.order.user.username = :username " +
            "group by od.order.id")
    List<OrderInfoBackend> findOrdersByUsername(String username);

    @Query("select " +
            "od.order.id as id, od.order.createdAt as createdAt, od.order.status as status, " +
            "SUM(od.cost * od.qty) as cost " +
            "from OrderDetail od " +
            "where od.order.user.username = :username " +
            "and od.order.id = :id")
    Optional<OrderInfoBackend> findOrderByIdAndUsername(Long id, String username);


    List<OrderDetailBackend> findByOrderId(Long id);
}
