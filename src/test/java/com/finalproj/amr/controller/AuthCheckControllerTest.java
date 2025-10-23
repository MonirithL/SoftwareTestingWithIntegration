package com.finalproj.amr.controller;

import com.finalproj.amr.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthCheckController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AuthCheckControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void authCheck_validToken() throws Exception {

        mockMvc.perform(get("/api/authCheck"))
                .andExpect(status().isOk())
                .andExpect(content().string("Valid token"));
    }

    @Test
    void authCheck_noJwt() throws Exception {
        mockMvc.perform(get("/api/authCheck"))
                .andExpect(status().isOk())
                .andExpect(content().string("Valid token"));
    }

    @Test
    void authCheck_invalidJwt() throws Exception {
        mockMvc.perform(get("/api/authCheck")
                        .header("Authorization", "Bearer invalidtoken"))
                .andExpect(status().isOk())
                .andExpect(content().string("Valid token"));
    }
}
