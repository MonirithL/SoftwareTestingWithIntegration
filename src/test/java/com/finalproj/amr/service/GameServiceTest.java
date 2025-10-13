package com.finalproj.amr.service;

import com.finalproj.amr.entity.Game;
import com.finalproj.amr.entity.User;
import com.finalproj.amr.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {
    @Mock
    private GameRepository gameRepository;
    @Mock
    private LeaderboardService leaderboardService;

    @InjectMocks
    private GameService gameService;

    private User user;
    private Game game;

    @BeforeEach
    void setUp() {
        user = new User(1, "Ankim", "ankim@gmail.com", "pass123");
        game = new Game(1, 100, "Example", "2025-10-13", user);
    }

    @Test
    void testAddGame() {
        when(gameRepository.save(game)).thenReturn(game);
        Game savedGame = gameService.addGame(game);

        assertNotNull(savedGame);
        assertEquals(100, savedGame.getScore());
        assertEquals("Example", savedGame.getWord());
        assertEquals("2025-10-13", savedGame.getDateString());
        assertEquals(user, savedGame.getUser());

        verify(gameRepository, times(1)).save(game);
        verify(leaderboardService, times(1)).updateLeaderboard(user);
    }
}
