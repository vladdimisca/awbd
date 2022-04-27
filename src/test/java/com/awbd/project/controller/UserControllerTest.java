package com.awbd.project.controller;

import com.awbd.project.model.SexType;
import com.awbd.project.model.UserDetails;
import com.awbd.project.model.security.Authority;
import com.awbd.project.model.security.User;
import com.awbd.project.service.impl.UserServiceImpl;
import com.awbd.project.service.security.JpaUserDetailsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("h2")
class UserControllerTest {

    private static final Long ID = 1L;
    private static final String USER_EMAIL = "email@gmail";
    private static final String PASSWORD = "pass123";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private JpaUserDetailsService jpaUserDetailsService;

    @Test
    @DisplayName("Create user - success")
    void create_success() throws Exception {
        User user = getUser();
        User savedUser = getSavedUser();

        when(userService.create(user)).thenReturn(savedUser);

        mockMvc.perform(post("/users").flashAttr("user", user))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login-form"));
    }

    @Test
    @DisplayName("Update user - success")
    void update_success() throws Exception {
        User updatedUser = getUser();

        when(userService.update(ID, updatedUser)).thenReturn(updatedUser);

        mockMvc.perform(put("/users/{id}", ID).flashAttr("user", updatedUser))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/" + ID));
    }

    @Test
    @DisplayName("Get user by id - success")
    void getById_success() throws Exception {
        User user = getSavedUser();

        when(userService.getById(ID)).thenReturn(user);

        mockMvc.perform(get("/users/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(view().name("user-info"));
    }

    @Test
    @DisplayName("Get current user - success")
    void getCurrentUser_success() throws Exception {
        User user = getSavedUser();

        when(jpaUserDetailsService.getCurrentUserPrincipal()).thenReturn(
                new org.springframework.security.core.userdetails.User(USER_EMAIL, PASSWORD, new HashSet<>()));
        when(userService.getByEmail(USER_EMAIL)).thenReturn(user);

        mockMvc.perform(get("/users/current"))
                .andExpect(status().isOk())
                .andExpect(view().name("user-info"));
    }

    @Test
    @DisplayName("Get all users - success")
    void getAll_success() throws Exception {
        when(userService.getAll(PageRequest.of(0, 15, Sort.by("userDetails.firstName"))))
                .thenReturn(new PageImpl<>(List.of(getSavedUser())));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("users"));
    }

    @Test
    @DisplayName("Delete user by id - success")
    void delete_success() throws Exception {
        mockMvc.perform(delete("/users/{id}", ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users"));
    }

    @Test
    @DisplayName("Get user form - success")
    void getForm_success() throws Exception {
        mockMvc.perform(get("/users/form"))
                .andExpect(status().isOk())
                .andExpect(view().name("register-form"));
    }

    @Test
    @DisplayName("Get update user form - success")
    void getUpdateForm_success() throws Exception {
        when(userService.getById(ID)).thenReturn(getSavedUser());

        mockMvc.perform(get("/users/form/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(view().name("update-user-form"));
    }

    private User getUser() {
        UserDetails userDetails = UserDetails.builder()
                .firstName("test")
                .lastName("test")
                .phoneNumber("0751111222")
                .sex(SexType.MALE)
                .build();

        Authority authority = Authority.builder()
                .id(1)
                .role("ROLE_ADMIN")
                .build();

        return User.builder()
                .email(USER_EMAIL)
                .password(PASSWORD)
                .userDetails(userDetails)
                .authority(authority)
                .build();
    }

    private User getSavedUser() {
        User savedUser = getUser();
        savedUser.setId(ID);

        return savedUser;
    }
}