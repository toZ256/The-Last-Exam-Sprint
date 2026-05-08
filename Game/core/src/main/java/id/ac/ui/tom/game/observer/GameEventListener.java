package id.ac.ui.tom.game.observer;

public interface GameEventListener {
    void onGameOver(int score, float distanceMeters, int durationSeconds);
    void onPlayerSlip(float slipDuration);
}
