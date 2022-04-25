package com.awbd.project.controller;

import com.awbd.project.model.UserDetails;
import com.awbd.project.model.security.User;
import com.awbd.project.service.UserService;
import com.awbd.project.service.security.JpaUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JpaUserDetailsService jpaUserDetailsService;

    @PostMapping
    public String create(@Valid @ModelAttribute User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register-form";
        }

        userService.create(user);
        return "redirect:/login-form";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") Long id, @Valid @ModelAttribute User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "update-user-form";
        }

        User updatedUser = userService.update(id, user);
        return "redirect:/users/" + updatedUser.getId();
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("users", userService.getAll());
        return "users";
    }

    @GetMapping("/current")
    public String getCurrentUser(Model model) {
        User user = userService.getByEmail(jpaUserDetailsService.getCurrentUserPrincipal().getUsername());
        model.addAttribute("user", user);
        return "user-info";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "user-info";
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/users";
    }

    @GetMapping("/form")
    public String userForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            User user = new User();
            user.setUserDetails(new UserDetails());
            model.addAttribute("user", user);
            return "register-form";
        }

        return "redirect:/index";
    }

    @GetMapping("/form/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "update-user-form";
    }
}
