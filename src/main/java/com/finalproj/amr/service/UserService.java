package com.finalproj.amr.service;

import com.finalproj.amr.entity.User;
import com.finalproj.amr.jsonEntity.UserJwt;
import com.finalproj.amr.repository.UserRepository;
import com.finalproj.amr.utils.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public UserService(UserRepository userRepository,JwtUtils jwtUtils) {

        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> checkUserLogin(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return Optional.empty();
        }
        User actualUser = user.get();
        if (!password.equals(actualUser.getPassword())) {
            return Optional.empty();
        } else {
            return Optional.of(actualUser);
        }

    }
    public void addJwtCookie(HttpServletResponse response, User user) {
        UserJwt userJwt = new UserJwt(user.getId(), user.getUsername(), user.getEmail());
        String token = jwtUtils.generateToken(userJwt);
        Cookie cookie = new Cookie("access-token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/api");
        cookie.setMaxAge(24 * 60 * 60);
        response.addCookie(cookie);
    }
}
