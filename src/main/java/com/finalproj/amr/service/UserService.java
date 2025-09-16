package com.finalproj.amr.service;

import com.finalproj.amr.entity.User;
import com.finalproj.amr.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(User user){
        return userRepository.save(user);
    }
    public Optional<User> findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
