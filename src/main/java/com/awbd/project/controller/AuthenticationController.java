package com.awbd.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {

    @GetMapping("/login-form")
    public String showLogInForm() {
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError() {
        return "login-error";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }
}
