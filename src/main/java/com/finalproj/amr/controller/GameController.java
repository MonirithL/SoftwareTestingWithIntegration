package com.finalproj.amr.controller;

import com.finalproj.amr.Word;
import com.finalproj.amr.entity.Game;
import com.finalproj.amr.entity.User;
import com.finalproj.amr.service.GameService;
import com.finalproj.amr.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;
    private final UserService userService;
    private final WordController wordController;

    public GameController(GameService gameService, UserService userService, WordController wordController) {
        this.gameService = gameService;
        this.userService = userService;
        this.wordController = wordController;
    }

    @GetMapping("/start")
    public Map<String, Object> startGame() {
        Word word = wordController.getWord();
        Map<String, Object> res = new HashMap<>();
        res.put("masked", "_".repeat(word.getWord().length()));
        res.put("definition", word.getDefinition());
        res.put("fullWord", word.getWord()); // optional for testing only
        return res;
    }

    @PostMapping("/guess")
    public Map<String, Object> guessLetter(@RequestBody Map<String, String> body) {
        String word = body.get("word");
        String guessedSoFar = body.get("current");
        String guessedLetter = body.get("letter");

        StringBuilder newState = new StringBuilder(guessedSoFar);
        boolean correct = false;

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == guessedLetter.charAt(0)) {
                newState.setCharAt(i, guessedLetter.charAt(0));
                correct = true;
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("current", newState.toString());
        result.put("correct", correct);
        result.put("finisehd", !newState.toString().contains("_"));
        return result;
    }

    @PostMapping("/save")
    public Game saveGame(@RequestBody Map<String, Object> body) {
        int score = (int) body.get("score");
        String word = (String) body.get("word");
        String dateString = (String) body.get("dateString");
        int userId = (int) body.get("userId");

        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Game game = new Game(score, word, dateString, user);
        return gameService.addGame(game);
    }
}
