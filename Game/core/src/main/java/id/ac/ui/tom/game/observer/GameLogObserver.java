package id.ac.ui.tom.game.observer;

import com.badlogic.gdx.Gdx;

public class GameLogObserver implements GameEventListener {

    @Override
    public void onGameOver(int score, float distanceMeters, int durationSeconds) {
        Gdx.app.log("GameLogObserver",
            String.format("GAME OVER — skor=%d | jarak=%.1fm | durasi=%ds",
                score, distanceMeters, durationSeconds));
    }

    @Override
    public void onPlayerSlip(float slipDuration) {
        Gdx.app.log("GameLogObserver",
            String.format("TERPELESET — berhenti selama %.1f detik", slipDuration));
    }
}
