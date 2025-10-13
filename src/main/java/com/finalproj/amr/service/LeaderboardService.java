package com.finalproj.amr.service;

import com.finalproj.amr.entity.Game;
import com.finalproj.amr.entity.Leaderboard;
import com.finalproj.amr.entity.User;
import com.finalproj.amr.repository.GameRepository;
import com.finalproj.amr.repository.LeaderboardRepository;
import com.finalproj.amr.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LeaderboardService {

//    @Autowired
//    private LeaderboardRepository leaderboardRepository;
//
//    @Autowired
//    private GameRepository gameRepository;
//
//    @Autowired
//    private UserRepository userRepository;

    private final LeaderboardRepository leaderboardRepository;
    private final GameRepository gameRepository;

    public LeaderboardService(LeaderboardRepository leaderboardRepository, GameRepository gameRepository) {
        this.leaderboardRepository = leaderboardRepository;
        this.gameRepository = gameRepository;
    }
    //update to existing leaderboard
    public void updateLeaderboard(User user) {
        List<Game> userGames = gameRepository.findByUser(user);

        int totalScore = userGames.stream()
                .mapToInt(Game::getScore)
                .sum();

        String lastUpdated = userGames.stream()
                .map(Game::getDateString)
                .max(String::compareTo)
                .orElse("N/A");

        Leaderboard leaderboard = leaderboardRepository.findByUser(user)
                .orElse(new Leaderboard());

        leaderboard.setUser(user);
        leaderboard.setTotalScore(totalScore);
        leaderboard.setLastUpdated(lastUpdated);

        leaderboardRepository.save(leaderboard);
    }
    //get all board
    public List<Leaderboard> getLeaderBoard() {
        return leaderboardRepository.findAll(Sort.by(Sort.Direction.DESC, "totalScore"));
    }
}

