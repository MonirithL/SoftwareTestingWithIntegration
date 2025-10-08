package com.finalproj.amr.controller;

import com.finalproj.amr.entity.User;
import com.finalproj.amr.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
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
}
