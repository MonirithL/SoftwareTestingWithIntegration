package com.finalproj.amr.controller;

import com.finalproj.amr.entity.Word;
import com.finalproj.amr.entity.Game;
import com.finalproj.amr.entity.User;
import com.finalproj.amr.jsonEntity.UserJwt;
import com.finalproj.amr.service.GameService;
import com.finalproj.amr.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    //get user id from jwt and add to db
    @PostMapping("/save")
    public Game saveGame(@RequestBody Map<String, Object> body, HttpServletRequest request) {
        String userIdObj = request.getAttribute("user_id").toString();
        int score = (int) body.get("score");
        String word = (String) body.get("word");
        String dateString = (String) body.get("dateString");
        int userId = Integer.parseInt(userIdObj);

        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Game game = new Game(score, word, dateString, user);
        return gameService.addGame(game);
    }
}
