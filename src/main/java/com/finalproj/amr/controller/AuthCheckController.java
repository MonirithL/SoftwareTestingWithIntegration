package com.finalproj.amr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthCheckController {
    public AuthCheckController() {
    }
    //for auth checking, it needs jwt filter OK for it to return
    @GetMapping("/api/authCheck")
    public String Check(){
        return "Valid token";
    }
}
