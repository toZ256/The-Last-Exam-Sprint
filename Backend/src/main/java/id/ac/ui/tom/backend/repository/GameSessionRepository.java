package id.ac.ui.tom.backend.repository;

import id.ac.ui.tom.backend.entity.GameSession;
import id.ac.ui.tom.backend.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameSessionRepository extends JpaRepository<GameSession, Long> {

    // History player
    List<GameSession> findByPlayerOrderByPlayedAtDesc(Player player);

    // Data leaderboard
    List<GameSession> findTop10ByOrderByScoreDesc();

    // Highscore
    @Query("SELECT MAX(gs.score) FROM GameSession gs WHERE gs.player = :player")
    Integer findHighScoreByPlayer(@Param("player") Player player);

    // Total sesi yang dimainkan player
    long countByPlayer(Player player);
}