package com.finalproj.amr.repository;

import com.finalproj.amr.entity.Game;
import com.finalproj.amr.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {
    List<Game> findByUser(User user);
//    @Query("""
//                SELECT
//                    g.user.username AS username,
//                    SUM(g.score) AS totalScore,
//                    MAX(g.dateString) AS lastPlayed
//                FROM Game g
//                GROUP BY g.user.username
//                ORDER BY totalScore DESC
//            """)
//    List<Map<String, Object>> getLeaderBoard();
}
