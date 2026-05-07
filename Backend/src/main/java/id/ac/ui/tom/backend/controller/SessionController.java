package id.ac.ui.tom.backend.controller;

import id.ac.ui.tom.backend.dto.SessionSubmitRequest;
import id.ac.ui.tom.backend.entity.GameSession;
import id.ac.ui.tom.backend.service.GameSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

 // Endpoint
 // POST /api/sessions/submit         — dipanggil kalo game over
 // GET  /api/sessions/leaderboard    — top 10
 // GET  /api/sessions/history/{id}   — history satu player
 // GET  /api/sessions/highscore/{id} — personal best score

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final GameSessionService sessionService;

    @PostMapping("/submit")
    public ResponseEntity<GameSession> submitSession(
            @RequestBody SessionSubmitRequest request) {
        GameSession saved = sessionService.submitSession(request);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<List<GameSession>> leaderboard() {
        return ResponseEntity.ok(sessionService.getLeaderboard());
    }

    @GetMapping("/history/{playerId}")
    public ResponseEntity<List<GameSession>> history(
            @PathVariable Long playerId) {
        return ResponseEntity.ok(sessionService.getPlayerHistory(playerId));
    }

    @GetMapping("/highscore/{playerId}")
    public ResponseEntity<Integer> highScore(
            @PathVariable Long playerId) {
        return ResponseEntity.ok(sessionService.getHighScore(playerId));
    }
}