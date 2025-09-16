package com.finalproj.amr.controller;

import com.finalproj.amr.entity.User;
import com.finalproj.amr.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user){
        User new_user = userService.addUser(user);
        return new ResponseEntity<>(new_user, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<> loginUser(@RequestBody User user){
        Optional<User> user_exists = userService.findUserByEmail(user.getEmail());
        if(user_exists.isPresent()){
            //do sth
        }
    }
}
