package ru.geekbrains.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.geekbrains.dto.*;
import ru.geekbrains.interfaces.UserService;
import ru.geekbrains.persist.model.Role;
import ru.geekbrains.persist.model.User;
import ru.geekbrains.persist.repositories.UserRepository;
import ru.geekbrains.persist.specifications.UserSpecifications;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(user -> new UserDto(user.getId(),
                        user.getUsername(),
                        user.getAge(), null))
                .collect(Collectors.toList());
    }

    @Override
    public Page<UserDto> findWithFilter(UserListParams userListParams) {
        Specification<User> spec = Specification.where(null);
        String username = userListParams.getUsernameFilter();
        if (username != null && !username.isEmpty()) {
            spec = spec.and(UserSpecifications.usernamePrefix(username));
        }
        if (userListParams.getMinAge() != null) {
            spec = spec.and(UserSpecifications.minAge(userListParams.getMinAge()));
        }
        if (userListParams.getMaxAge() != null ) {
            spec = spec.and(UserSpecifications.maxAge(userListParams.getMaxAge()));
        }
        Sort sortedBy;
        if(userListParams.getSortBy() != null && !userListParams.getSortBy().isEmpty()) {
            sortedBy = Sort.by(userListParams.getSortBy());
        } else {
            sortedBy = Sort.by("id");
        }
        if(userListParams.getDirection() != null && !userListParams.getDirection().isEmpty() && userListParams.getDirection().equals("desc")) {
            sortedBy = sortedBy.descending();
        } else {
            sortedBy = sortedBy.ascending();
        }

        return userRepository.findAll(spec,
                PageRequest.of(
                        Optional.ofNullable(userListParams.getPage()).orElse(1) - 1,
                        Optional.ofNullable(userListParams.getSize()).orElse(3), sortedBy))
                .map(user -> new UserDto(user.getId(), user.getUsername(), user.getAge(), null));
    }

    @Override
    public Optional<UserDto> findById(Long id) {
        return userRepository.findById(id)
                .map(user -> new UserDto(user.getId(),
                        user.getUsername(),
                        user.getAge(),
                        mapRolesDto(user)));
    }

    @Override
    public void save(UserDto userDto) {
        User user = new User(
                userDto.getId(),
                userDto.getUsername(),
                passwordEncoder.encode(userDto.getPassword()),
                userDto.getAge(),
                userDto.getRoles().stream()
                        .map(roleDto -> new Role(roleDto.getId(), roleDto.getName()))
                        .collect(Collectors.toSet()));
        userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    private static Set<RoleDto> mapRolesDto(User user) {
        return user.getRoles().stream()
                .map(role -> new RoleDto(role.getId(), role.getName()))
                .collect(Collectors.toSet());
    }
}
