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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;


    @Test
    void AuthRegisterReturnsUser() throws Exception{
        String json = """
                {
                "username":"guest1",
                "password":"12345678",
                "email":"guest1@gmail.com"
                }
                """;
        ResultActions response = mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        response.andExpect(status().isOk());

    }
    @Test
    void loginTestOK() throws Exception{
        String json = """
                {
                "password":"12345678",
                "email":"guest1@gmail.com"
                }
                """;
        User fakeUser = new User();
        fakeUser.setEmail("guest1@gmail.com");

        // mock behavior
        given(userService.checkUserLogin("guest1@gmail.com", "12345678")).willReturn(Optional.of(new User()));
        ResultActions response = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        response.andExpect(status().isOk());
    }
    @Test
    void loginTestWrongPassword() throws Exception{
        String json = """
                {
                "password":"123456",
                "email":"guest1@gmail.com"
                }
                """;
        User fakeUser = new User();
        fakeUser.setEmail("guest1@gmail.com");

        // mock behavior
        given(userService.checkUserLogin("guest1@gmail.com", "12345678")).willReturn(Optional.of(new User()));
        ResultActions response = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        response.andExpect(status().isUnauthorized());
    }
    @Test
    void loginTestWrongEmail() throws Exception{
        String json = """
                {
                "password":"12345678",
                "email":"guest12@gmail.com"
                }
                """;
        User fakeUser = new User();
        fakeUser.setEmail("guest1@gmail.com");

        // mock behavior
        given(userService.checkUserLogin("guest1@gmail.com", "12345678")).willReturn(Optional.of(new User()));
        ResultActions response = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        response.andExpect(status().isUnauthorized());
    }
    @Test
    void loginTestWrongUser() throws Exception{
        String json = """
                {
                "password":"123456",
                "email":"guest13@gmail.com"
                }
                """;
        User fakeUser = new User();
        fakeUser.setEmail("guest1@gmail.com");

        // mock behavior
        given(userService.checkUserLogin("guest1@gmail.com", "12345678")).willReturn(Optional.of(new User()));
        ResultActions response = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        response.andExpect(status().isUnauthorized());
    }
    @Test
    void logoutTestOK() throws Exception {
        // Perform POST /logout
        mockMvc.perform(post("/auth/logout"))
                .andDo(print()) //
                .andExpect(status().isOk())
                .andExpect(content().string("Logged out"))
                .andExpect(cookie().value("access-token", (String) null))
                .andExpect(cookie().maxAge("access-token", 0))
                .andExpect(cookie().path("access-token", "/api"));
    }
}
