package ru.geekbrains.persist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.geekbrains.persist.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Modifying
    @Query("update Order o set o.status = :status where o.id = :id")
    void setOrderStatus(@Param("id") Long id, @Param("status") Order.Status status);

    @Query("select distinct o from Order o join fetch o.products where o.user.username = :username")
    List<Order> findAllByUsername(String username);

    @Query("select distinct o from Order o join fetch o.products where o.id = :id and o.user.username = :username")
    Optional<Order> findByIdAndUsername(Long id, String username);

}
