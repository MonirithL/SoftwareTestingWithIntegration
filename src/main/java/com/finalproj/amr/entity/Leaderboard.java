package com.finalproj.amr.entity;

import jakarta.persistence.*;

@Entity
public class Leaderboard {
    @Id
    @GeneratedValue
    private int id;

    private int total_score;
    private String lastUpdated;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Leaderboard() {
    }

    public Leaderboard(int total_score, String lastUpdated, User user) {
        this.total_score = total_score;
        this.lastUpdated = lastUpdated;
        this.user = user;
    }

    public Leaderboard(int id, int total_score, String lastUpdated, User user) {
        this.id = id;
        this.total_score = total_score;
        this.lastUpdated = lastUpdated;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public int getTotal_score() {
        return total_score;
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

    public void setTotal_score(int total_score) {
        this.total_score = total_score;
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
                ", total_score=" + total_score +
                ", lastUpdated='" + lastUpdated + '\'' +
                ", user=" + (user!=null?user.getId():"user invalid") +
                '}';
    }
}
