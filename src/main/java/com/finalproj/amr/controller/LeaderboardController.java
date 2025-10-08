package com.finalproj.amr.controller;

import com.finalproj.amr.service.GameService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/leaderboard")
public class LeaderboardController {
    private final GameService gameService;

    public LeaderboardController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public List<Map<String, Object>> getLeaderBoard() {
        return gameService.getLeaderBoard();
    }

}
