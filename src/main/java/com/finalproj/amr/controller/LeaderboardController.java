package com.finalproj.amr.controller;

import com.finalproj.amr.entity.Leaderboard;
import com.finalproj.amr.service.GameService;
import com.finalproj.amr.service.LeaderboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/leaderboard")
public class LeaderboardController {
//    private final GameService gameService;
    private final LeaderboardService leaderboardService;

    public LeaderboardController(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }
    @GetMapping
    public List<Leaderboard> getLeaderBoard() {
        return leaderboardService.getLeaderBoard();
    }
//    public LeaderboardController(GameService gameService) {
//        this.gameService = gameService;
//    }
//
//    @GetMapping
//    public List<Map<String, Object>> getLeaderBoard() {
//        return gameService.getLeaderBoard();
//    }

}
