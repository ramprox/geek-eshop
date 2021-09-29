package ru.geekbrains.persist.repositories;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import ru.geekbrains.persist.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    @Query("select distinct u from User u left join fetch u.roles where u.username = :username")
    Optional<User> findAllByUsername(@Param("username") String username);

    @Query("select u from User u join fetch u.roles where u.id = :id")
    Optional<User> findFetchRolesById(Long id);
}
