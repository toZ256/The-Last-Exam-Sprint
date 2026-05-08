package id.ac.ui.tom.game.strategy;

public class BonusScoringStrategy implements ScoringStrategy {

    private static final float ADRENALINE_THRESHOLD = 15f; // detik
    private static final float BONUS_MULTIPLIER      = 2f;

    @Override
    public float calculateScoreDelta(float currentSpeed, float timeRemaining, float delta) {
        float base = (currentSpeed / 100f) * delta;
        if (timeRemaining <= ADRENALINE_THRESHOLD) {
            return base * BONUS_MULTIPLIER;
        }
        return base;
    }
}
