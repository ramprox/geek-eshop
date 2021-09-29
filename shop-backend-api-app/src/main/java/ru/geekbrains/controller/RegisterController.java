package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.controller.dto.RegisterData;
import ru.geekbrains.controller.dto.UserDto;
import ru.geekbrains.controller.exception.UserAlreadyExistException;
import ru.geekbrains.persist.model.Role;
import ru.geekbrains.persist.model.User;
import ru.geekbrains.persist.repositories.RoleRepository;
import ru.geekbrains.persist.repositories.UserRepository;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RequestMapping("/")
@RestController
public class RegisterController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterController(UserRepository userRepository, RoleRepository roleRepository,
                              PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/register", consumes = "application/json")
    public UserDto register(@Valid @RequestBody RegisterData registerData) {
        User user = userRepository.findAllByUsername(registerData.getUsername()).orElse(new User());
        if (user.getId() != null) {
            throw new UserAlreadyExistException("A user with the same name already exists");
        }
        user.setUsername(registerData.getUsername());
        user.setPassword(passwordEncoder.encode(registerData.getPassword()));
        LocalDate now = LocalDate.now();
        LocalDate birthDay = registerData.getBirthDay()
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long years = birthDay.until(now, ChronoUnit.YEARS);
        user.setAge((int) years);
        Role role = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("Role not found"));
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);
        UserDto userDto = new UserDto();
        userDto.setUsername(registerData.getUsername());
        return userDto;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyExistException.class)
    public Map<String, String> handleUserIsExist(UserAlreadyExistException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("username", ex.getMessage());
        return errors;
    }
}
