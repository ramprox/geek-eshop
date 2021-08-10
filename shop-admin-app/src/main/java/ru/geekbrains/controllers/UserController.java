package ru.geekbrains.controllers;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.geekbrains.dto.*;
import ru.geekbrains.exceptions.NotFoundException;
import ru.geekbrains.persist.*;
import ru.geekbrains.services.RoleService;
import ru.geekbrains.services.UserService;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String listPage(Model model,
                           UserListParams userListParams) {
        logger.info("User list page requested");

        model.addAttribute("users", userService.findWithFilter(userListParams));
        return "users";
    }

    @GetMapping("/new")
    public String newUserForm(Model model) {
        logger.info("New user page requested");

        model.addAttribute("user", new UserDto());
        model.addAttribute("roles", roleService.findAll().stream()
                .map(role -> new RoleDto(role.getId(), role.getName()))
                .collect(Collectors.toList()));
        return "user_form";
    }

    @GetMapping("/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        logger.info("Edit user page requested");

        model.addAttribute("user", userService.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found")));
        model.addAttribute("roles", roleService.findAll());
        return "user_form";
    }

    @PostMapping
    public String update(@Valid @ModelAttribute("user") UserDto user, BindingResult result, Model model) {
        logger.info("Saving user");

        if (result.hasErrors()) {
            model.addAttribute("roles", roleService.findAll());
            return "user_form";
        }

        if(!user.getPassword().equals(user.getMatchingPassword())) {
            model.addAttribute("roles", roleService.findAll());
            result.rejectValue("password", "", "Incorrect repeated password");
            return "user_form";
        }

        userService.save(user);
        return "redirect:/user";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        logger.info("Deleting user with id {}", id);
        userService.deleteById(id);
        return "redirect:/user";
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.addObject("message", ex.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
