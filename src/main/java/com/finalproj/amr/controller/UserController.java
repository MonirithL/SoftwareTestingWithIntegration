package com.finalproj.amr.controller;

import com.finalproj.amr.entity.User;
import com.finalproj.amr.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        return userService.getUserById(id).map(user -> ResponseEntity.ok(user)).orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user){
        User new_user = userService.addUser(user);
        return new ResponseEntity<>(new_user, HttpStatus.CREATED);
    }

    @GetMapping("/test")
    public ResponseEntity<String> testing(){
        return new ResponseEntity<>("hello", HttpStatus.ACCEPTED);
    }

    @GetMapping("/username")
    public ResponseEntity<String> getUsername(HttpServletRequest request) {
        Object userIdObj = request.getAttribute("user_id");
        if (userIdObj == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        int userId;
        try {
            userId = Integer.parseInt(userIdObj.toString());
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user id");
        }

        Optional<User> userOpt = userService.getUserById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        String username = userOpt.get().getUsername();
        return ResponseEntity.ok(username);
    }
}
