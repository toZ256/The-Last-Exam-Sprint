package id.ac.ui.tom.game.strategy;

public interface ScoringStrategy {
    float calculateScoreDelta(float currentSpeed, float timeRemaining, float delta);
}
