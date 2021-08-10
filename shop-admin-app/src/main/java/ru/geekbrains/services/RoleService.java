package ru.geekbrains.services;


import ru.geekbrains.dto.RoleDto;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<RoleDto> findAll();

    Optional<RoleDto> findByName(String name);
}
