package com.finalproj.amr.service;

import com.finalproj.amr.entity.Game;
import com.finalproj.amr.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final LeaderboardService leaderboardService;


    public GameService(GameRepository gameRepository, LeaderboardService leaderboardService) {
        this.gameRepository = gameRepository;
        this.leaderboardService = leaderboardService;
    }

    public Game addGame(Game game) {
        Game savedGame = gameRepository.save(game);
        leaderboardService.updateLeaderboard(savedGame.getUser());
        return savedGame;
    }

//    public List<Map<String, Object>> getLeaderBoard() {
//        return gameRepository.getLeaderBoard();
//    }
}
