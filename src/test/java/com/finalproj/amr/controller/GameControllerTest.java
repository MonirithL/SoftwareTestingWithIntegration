package com.finalproj.amr.controller;


import com.finalproj.amr.entity.Game;
import com.finalproj.amr.entity.User;
import com.finalproj.amr.service.GameService;
import com.finalproj.amr.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;


@WebMvcTest(controllers = GameController.class)
public class GameControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private GameService gameService;

    @MockitoBean
    private WordController wordController;

    @Test
    void testSaveGame() throws Exception {
        User user = new User(1, "Ankim", "ankim@gmail.com", "pass123");
        Game game = new Game(100, "Example", "2025-10-13", user);

        when(userService.getUserById(1)).thenReturn(Optional.of(user));
        when(gameService.addGame(any(Game.class))).thenReturn(game);

        String jsonBody = """
                {
                    "score": 100,
                    "word": "Example",
                    "dateString": "2025-10-13"
                }
                """;

        mockMvc.perform(post("/api/game/save")
                        .requestAttr("user_id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.score", is(100)))
                .andExpect(jsonPath("$.word", is("Example")))
                .andExpect(jsonPath("$.dateString", is("2025-10-13")))
                .andExpect(jsonPath("$.user.username", is("Ankim")));

        verify(userService, times(1)).getUserById(1);
        verify(gameService, times(1)).addGame(any(Game.class));
    }
}
