package com.finalproj.amr.service;

import com.finalproj.amr.entity.Game;
import com.finalproj.amr.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GameService {

    private final GameRepository gameRepository;


    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }
    public Game addGame(Game game) {
        return gameRepository.save(game);
    }

    public List<Map<String, Object>> getLeaderBoard() {
        return gameRepository.getLeaderBoard();
    }
}
