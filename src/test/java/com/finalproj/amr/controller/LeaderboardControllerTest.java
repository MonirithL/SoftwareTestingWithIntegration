package com.finalproj.amr.controller;

import com.finalproj.amr.entity.Leaderboard;
import com.finalproj.amr.entity.User;
import com.finalproj.amr.service.LeaderboardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = LeaderboardController.class)
public class LeaderboardControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LeaderboardService leaderboardService;

    @Test
    void getLeaderBoard() throws Exception {
        User user1 = new User(1, "Ankim", "ankim@gmail.com", "pass123");
        User user2 = new User(2, "Rith", "rith@gmail.com", "pass123");

        Leaderboard lb1 = new Leaderboard();
        lb1.setUser(user1);
        lb1.setTotalScore(120);

        Leaderboard lb2 = new Leaderboard();
        lb2.setUser(user2);
        lb2.setTotalScore(69);

        when(leaderboardService.getLeaderBoard()).thenReturn(List.of(lb1, lb2));

        mockMvc.perform(get("/api/leaderboard")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))) // ✅ two results
                .andExpect(jsonPath("$[0].user.username", is("Ankim")))
                .andExpect(jsonPath("$[0].totalScore", is(120)))
                .andExpect(jsonPath("$[1].user.username", is("Rith")))
                .andExpect(jsonPath("$[1].totalScore", is(69)));

    }
}
