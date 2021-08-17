package ru.geekbrains.interfaces;

import org.springframework.data.domain.Page;
import ru.geekbrains.dto.UserDto;
import ru.geekbrains.dto.UserListParams;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDto> findAll();

    Page<UserDto> findWithFilter(UserListParams userListParams);

    Optional<UserDto> findById(Long id);

    void save(UserDto user);

    void deleteById(Long id);
}
