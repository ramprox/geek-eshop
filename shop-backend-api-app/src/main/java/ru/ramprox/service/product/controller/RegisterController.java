package ru.ramprox.service.product.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.ramprox.service.product.controller.dto.RegisterData;
import ru.ramprox.service.product.controller.dto.UserDto;
import ru.ramprox.service.product.controller.exception.UserAlreadyExistException;
import ru.ramprox.persist.model.Role;
import ru.ramprox.persist.model.User;
import ru.ramprox.persist.repositories.RoleRepository;
import ru.ramprox.persist.repositories.UserRepository;

import javax.validation.Valid;
import java.util.*;

@RequestMapping("/")
@RestController
public class RegisterController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public RegisterController(UserRepository userRepository, RoleRepository roleRepository,
                              PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/register", consumes = "application/json")
    public UserDto register(@Valid @RequestBody RegisterData registerData) {
        Optional<User> optUser = userRepository.findAllByUsername(registerData.getUsername());
        optUser.ifPresent(user -> { throw new UserAlreadyExistException("A user with the same name already exists"); });

        User user = modelMapper.map(registerData, User.class);
//        optUser.setUsername(registerData.getUsername());
//        optUser.setPassword(passwordEncoder.encode(registerData.getPassword()));
//        LocalDate now = LocalDate.now();
//        LocalDate birthDay = registerData.getBirthDay()
//                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        long years = birthDay.until(now, ChronoUnit.YEARS);
//        optUser.setAge((int) years);
        user.setPassword(passwordEncoder.encode(registerData.getPassword()));
        Role role = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("Role not found"));
        user.addRole(role);
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
