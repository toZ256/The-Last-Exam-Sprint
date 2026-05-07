package id.ac.ui.tom.game.obstacle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class WaterPuddle extends Obstacle {
    public WaterPuddle(int lane, float startY) { super(lane, startY); }

    @Override
    public void draw(ShapeRenderer sr, float x, float skew) {
        sr.setColor(getColor());
        sr.ellipse(x - 20, y, 40, 20); // Bentuk genangan air oval
    }

    @Override public Color getColor() { return Color.BLUE; }
    @Override public boolean needsJump() { return false; }
}
