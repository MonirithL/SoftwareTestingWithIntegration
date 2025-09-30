package com.finalproj.amr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthCheckController {
    public AuthCheckController() {
    }

    @GetMapping("/api/authCheck")
    public String Check(){
        return "Valid token";
    }
}
