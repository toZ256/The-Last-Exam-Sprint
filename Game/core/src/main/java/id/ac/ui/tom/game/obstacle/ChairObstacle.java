package id.ac.ui.tom.game.obstacle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ChairObstacle extends Obstacle {
    public ChairObstacle(int lane, float startY) { super(lane, startY); }

    @Override
    public void draw(ShapeRenderer sr, float x, float skew) {
        sr.setColor(getColor());
        sr.rect(x - 15, y, 30, 40);
    }

    @Override public Color getColor() { return Color.BROWN; }
    @Override public boolean needsJump() { return true; }
}
