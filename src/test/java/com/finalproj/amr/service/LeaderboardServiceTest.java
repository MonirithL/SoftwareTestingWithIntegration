package com.finalproj.amr.service;

import com.finalproj.amr.entity.Game;
import com.finalproj.amr.entity.Leaderboard;
import com.finalproj.amr.entity.User;
import com.finalproj.amr.repository.GameRepository;
import com.finalproj.amr.repository.LeaderboardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LeaderboardServiceTest {
    @Mock
    private GameRepository gameRepository;

    @Mock
    private LeaderboardRepository leaderboardRepository;

    @InjectMocks
    private LeaderboardService leaderboardService;

    private User user1;
    private User user2;

    private Game game1;
    private Game game2;

    @BeforeEach
    void setUp() {
        user1 = new User(1, "Ankim", "ankim@gmail.com", "pass123");
        user2 = new User(2, "Rith", "rith@gmail.com", "pass123");

        game1 = new Game(1, 50, "word1", "2025-10-10", user1);
        game2 = new Game(2, 70, "word2", "2025-10-12", user1);
    }

    @Test
    void testUpdateLeaderboard() {
        when(gameRepository.findByUser(user1)).thenReturn(List.of(game1, game2));
        when(leaderboardRepository.findByUser(user1)).thenReturn(Optional.empty());

        leaderboardService.updateLeaderboard(user1);
        // Capture the leaderboard that is saved
        verify(leaderboardRepository, times(1)).save(argThat(leaderboard ->
                leaderboard.getUser().equals(user1) &&
                        leaderboard.getTotalScore() == 120 &&            // 50 + 70
                        leaderboard.getLastUpdated().equals("2025-10-12") // latest date
        ));

        verify(gameRepository, times(1)).findByUser(user1);
    }

    @Test
    void testGetLeaderboard() {
        Leaderboard lb1 = new Leaderboard();
        lb1.setUser(user1);
        lb1.setTotalScore(120);

        Leaderboard lb2 = new Leaderboard();
        lb2.setUser(user2);
        lb2.setTotalScore(69);

        when(leaderboardRepository.findAll(Sort.by(Sort.Direction.DESC, "totalScore"))).thenReturn(List.of(lb1, lb2));
        List<Leaderboard> allLb = leaderboardService.getLeaderBoard();
        assertEquals(2, allLb.size());
        assertEquals(120, allLb.get(0).getTotalScore());
        assertEquals(69, allLb.get(1).getTotalScore());
        verify(leaderboardRepository, times(1))
                .findAll(Sort.by(Sort.Direction.DESC, "totalScore"));
    }
}
