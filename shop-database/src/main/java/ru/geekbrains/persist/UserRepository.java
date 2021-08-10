package ru.geekbrains.persist;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    @Query("select distinct u from User u left join fetch u.roles where u.username = :username")
    Optional<User> findAllByUsername(@Param("username") String username);
}
