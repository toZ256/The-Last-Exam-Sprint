package id.ac.ui.tom.backend.service;

import id.ac.ui.tom.backend.dto.SessionSubmitRequest;
import id.ac.ui.tom.backend.entity.Achievement;
import id.ac.ui.tom.backend.entity.GameSession;
import id.ac.ui.tom.backend.entity.Player;
import id.ac.ui.tom.backend.repository.AchievementRepository;
import id.ac.ui.tom.backend.repository.GameSessionRepository;
import id.ac.ui.tom.backend.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameSessionService {

    private final GameSessionRepository sessionRepo;
    private final PlayerRepository      playerRepo;
    private final AchievementRepository achievementRepo;


    @Transactional
    public GameSession submitSession(SessionSubmitRequest req) {
     
        Player player = playerRepo.findById(req.getPlayerId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Player not found with id: " + req.getPlayerId()));

        GameSession session = new GameSession();
        session.setPlayer(player);
        session.setScore(req.getScore());
        session.setDistanceMeters(req.getDistanceMeters());
        session.setDurationSeconds(req.getDurationSeconds());
        session.setPlayedAt(LocalDateTime.now());

        GameSession saved = sessionRepo.save(session);

        log.info("Session saved — player='{}' score={} distance={}m duration={}s",
                player.getUsername(), req.getScore(),
                req.getDistanceMeters(), req.getDurationSeconds());

        evaluateAchievements(player, req.getScore());

        return saved;
    }

    @Transactional(readOnly = true)
    public List<GameSession> getLeaderboard() {
        return sessionRepo.findTop10ByOrderByScoreDesc();
    }

    @Transactional(readOnly = true)
    public List<GameSession> getPlayerHistory(Long playerId) {
        Player player = playerRepo.findById(playerId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Player not found with id: " + playerId));
        return sessionRepo.findByPlayerOrderByPlayedAtDesc(player);
    }

    @Transactional(readOnly = true)
    public Integer getHighScore(Long playerId) {
        Player player = playerRepo.findById(playerId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Player not found with id: " + playerId));
        Integer hs = sessionRepo.findHighScoreByPlayer(player);
        return (hs != null) ? hs : 0;
    }


    private void evaluateAchievements(Player player, int score) {
        List<Achievement> unlocked = achievementRepo
                .findByMinScoreRequiredLessThanEqualOrderByMinScoreRequiredDesc(score);

        if (!unlocked.isEmpty()) {
            String titles = unlocked.stream()
                    .map(Achievement::getTitle)
                    .collect(Collectors.joining(", "));
            log.info("Achievements unlocked for '{}': [{}]", player.getUsername(), titles);
        }
    }
}