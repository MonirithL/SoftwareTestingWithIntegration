package com.finalproj.amr.controller;

import com.finalproj.amr.entity.User;
import com.finalproj.amr.jsonEntity.UserJwt;
import com.finalproj.amr.service.UserService;
import com.finalproj.amr.utils.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }


    //take data, return user, add cookie
    @PostMapping("/register")
    public User Register(@RequestBody Map<String, String> body, HttpServletResponse res) {
        User newUser = new User(body.get("username"), body.get("email"), body.get("password"));
        User createdUser = userService.addUser(newUser);
        userService.addJwtCookie(res, createdUser);
        res.setStatus(200);
        return createdUser;
    }
    //on success, add cookie
    @PostMapping("/login")
    public String Login(@RequestBody Map<String, String> body, HttpServletResponse response) {
        Optional<User> user = userService.checkUserLogin(body.get("email"), body.get("password"));
        if (user.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "Invalid email or password";
        }
        userService.addJwtCookie(response, user.get());

        return "Login successful ";
    }

    //remove cookie, so user is logged out
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
