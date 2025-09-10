package com.finalproj.amr.entity;

import jakarta.persistence.*;

@Entity
public class Game {
    @Id
    @GeneratedValue
    private int id;

    private int score;
    private String word;
    private String dateString;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Game() {
    }

    public Game(int score, String word, String dateString, User user) {
        this.score = score;
        this.word = word;
        this.dateString = dateString;
        this.user = user;
    }

    public Game(int id, int score, String word, String dateString, User user) {
        this.id = id;
        this.score = score;
        this.word = word;
        this.dateString = dateString;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public String getWord() {
        return word;
    }

    public String getDateString() {
        return dateString;
    }

    public User getUser() {
        return user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", score=" + score +
                ", word='" + word + '\'' +
                ", dateString='" + dateString + '\'' +
                ", user_id=" + (user != null? user.getId():"user_invalid") +
                '}';
    }
}
