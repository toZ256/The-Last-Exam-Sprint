package id.ac.ui.tom.game.obstacle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class Obstacle {
    public float y;
    public int lane;
    public float width = 30;
    public float height = 30;

    public Obstacle(int lane, float startY) {
        this.lane = lane;
        this.y = startY;
    }

    public abstract void draw(ShapeRenderer sr, float x, float skew);
    public abstract Color getColor();

    public abstract boolean needsJump();
}
