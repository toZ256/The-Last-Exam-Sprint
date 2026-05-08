package id.ac.ui.tom.game.observer;

import java.util.ArrayList;
import java.util.List;

public class GameEventPublisher {

    private final List<GameEventListener> listeners = new ArrayList<>();

    public void addListener(GameEventListener listener) {
        listeners.add(listener);
    }

    public void removeListener(GameEventListener listener) {
        listeners.remove(listener);
    }

    public void notifyGameOver(int score, float distanceMeters, int durationSeconds) {
        for (GameEventListener listener : listeners) {
            listener.onGameOver(score, distanceMeters, durationSeconds);
        }
    }

    public void notifyPlayerSlip(float slipDuration) {
        for (GameEventListener listener : listeners) {
            listener.onPlayerSlip(slipDuration);
        }
    }
}
