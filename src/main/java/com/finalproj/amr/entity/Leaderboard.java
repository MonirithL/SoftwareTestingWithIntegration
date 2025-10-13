package com.finalproj.amr.entity;

import jakarta.persistence.*;

@Entity
public class Leaderboard {
    @Id
    @GeneratedValue
    private int id;

    private int totalScore;
    private String lastUpdated;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Leaderboard() {
    }

    public Leaderboard(int totalScore, String lastUpdated, User user) {
        this.totalScore = totalScore;
        this.lastUpdated = lastUpdated;
        this.user = user;
    }

    public Leaderboard(int id, int totalScore, String lastUpdated, User user) {
        this.id = id;
        this.totalScore = totalScore;
        this.lastUpdated = lastUpdated;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public User getUser() {
        return user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Leaderboard{" +
                "id=" + id +
                ", totalScore=" + totalScore +
                ", lastUpdated='" + lastUpdated + '\'' +
                ", user=" + (user!=null?user.getId():"user invalid") +
                '}';
    }
}
