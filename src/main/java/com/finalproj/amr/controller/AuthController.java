package com.finalproj.amr.controller;

import com.finalproj.amr.entity.User;
import com.finalproj.amr.jsonObject.UserJwt;
import com.finalproj.amr.service.UserService;
import com.finalproj.amr.utils.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final JwtUtils jwtUtils;

    public AuthController(UserService userService, JwtUtils jwtUtils) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public String Register(@RequestParam String username, @RequestParam String email, @RequestParam String password, HttpServletResponse res){
        User newUser = new User(username, email,password);
        User createdUser = userService.addUser(newUser);
        res.setStatus(200);
        return "registered user";
    }
    @PostMapping("/login")
    public String Login(@RequestParam String email, @RequestParam String password, HttpServletResponse response){
        Optional<User> user = userService.checkUserLogin(email,password);
        if (user.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "Invalid email or password";
        }
        User actualUser = user.get();
        UserJwt userJwt = new UserJwt(actualUser.getId(), actualUser.getUsername(), actualUser.getEmail());
        String token = jwtUtils.generateToken(userJwt);
        Cookie cookie = new Cookie("access-token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60);
        response.addCookie(cookie);
        return "Login successful";
    }
    @GetMapping("/logout")
    public String Logout(HttpServletResponse response){
        Cookie cookie = new Cookie("access-token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/api");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "Logged out";
    }
}
