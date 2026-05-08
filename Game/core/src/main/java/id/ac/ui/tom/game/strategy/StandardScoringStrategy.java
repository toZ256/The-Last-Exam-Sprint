package id.ac.ui.tom.game.strategy;

public class StandardScoringStrategy implements ScoringStrategy {

    @Override
    public float calculateScoreDelta(float currentSpeed, float timeRemaining, float delta) {
        return (currentSpeed / 100f) * delta;
    }
}
