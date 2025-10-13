package com.finalproj.amr.jsonEntity;

public class UserJwt {
    private int id;
    private String username;
    private String email;

    public UserJwt(int id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

}

