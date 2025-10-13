package com.finalproj.amr.repository;

import com.finalproj.amr.entity.Leaderboard;
import com.finalproj.amr.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeaderboardRepository extends JpaRepository<Leaderboard, Integer> {
    Optional<Leaderboard> findByUser(User user);
}
