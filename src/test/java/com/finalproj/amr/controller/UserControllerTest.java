package com.finalproj.amr.controller;

import com.finalproj.amr.entity.User;
import com.finalproj.amr.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void getUsername_success() throws Exception {
        // Arrange
        User fakeUser = new User();
        fakeUser.setId(1);
        fakeUser.setUsername("guest1");

        given(userService.getUserById(1)).willReturn(Optional.of(fakeUser));

        // Act & Assert
        mockMvc.perform(get("/api/user/username")
                        .requestAttr("user_id", 1))
                .andExpect(status().isOk())
                .andExpect(content().string("guest1"));
    }

    @Test
    void getUsername_unauthenticated() throws Exception {
        mockMvc.perform(get("/api/user/username"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("User not authenticated"));
    }

    @Test
    void getUsername_invalidId() throws Exception {
        mockMvc.perform(get("/api/user/username")
                        .requestAttr("user_id", "abc"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid user id"));
    }

    @Test
    void getUsername_notFound() throws Exception {
        given(userService.getUserById(999)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/user/username")
                        .requestAttr("user_id", 999))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found"));
    }
}
