package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/login")
    public String loginPage() {
        logger.info("Login page requested");
        return "login_page";
    }

    @GetMapping("/access_denied")
    public String accessDenied() {
        return "access_denied";
    }
}
