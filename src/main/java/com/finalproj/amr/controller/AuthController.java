package com.finalproj.amr.controller;

import com.finalproj.amr.entity.User;
import com.finalproj.amr.jsonObject.UserJwt;
import com.finalproj.amr.service.UserService;
import com.finalproj.amr.utils.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
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

    private void addJwtCookie(HttpServletResponse response, User user) {
        UserJwt userJwt = new UserJwt(user.getId(), user.getUsername(), user.getEmail());
        String token = jwtUtils.generateToken(userJwt);
        Cookie cookie = new Cookie("access-token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/api");
        cookie.setMaxAge(24 * 60 * 60);
        response.addCookie(cookie);
    }

    @PostMapping("/register")
    public User Register(@RequestBody Map<String, String> body, HttpServletResponse res) {
        User newUser = new User(body.get("username"), body.get("email"), body.get("password"));
        User createdUser = userService.addUser(newUser);
        addJwtCookie(res, createdUser);
        res.setStatus(200);
        return createdUser;
    }

    @PostMapping("/login")
    public String Login(@RequestBody Map<String, String> body, HttpServletResponse response) {
        Optional<User> user = userService.checkUserLogin(body.get("email"), body.get("password"));
        if (user.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "Invalid email or password";
        }
        addJwtCookie(response, user.get());

        return "Login successful ";
    }

    @PostMapping("/logout")
    public String Logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("access-token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/api");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "Logged out";
    }
}
