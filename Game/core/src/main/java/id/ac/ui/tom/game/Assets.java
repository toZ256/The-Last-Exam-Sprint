package id.ac.ui.tom.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    private static Assets instance;

    public ShapeRenderer shapeRenderer;
    public BitmapFont font;
    public BitmapFont fontLarge;
    public BitmapFont fontMedium;
    public BitmapFont fontSmall;

    public TextureRegion playerIdle;
    public Animation<TextureRegion> playerRunAnim;
    public Animation<TextureRegion> playerFallAnim;

    private Assets() {
        shapeRenderer = new ShapeRenderer();

        // font default di gameplaynya
        font = new BitmapFont();
        font.getData().setScale(1.5f);
        font.setColor(Color.WHITE);

        // font besar buat judul
        fontLarge = new BitmapFont();
        fontLarge.getData().setScale(3.0f);
        fontLarge.setColor(Color.WHITE);

        // font sedang buat subtitle
        fontMedium = new BitmapFont();
        fontMedium.getData().setScale(2.0f);
        fontMedium.setColor(Color.WHITE);

        // font kecil buat info tambahan
        fontSmall = new BitmapFont();
        fontSmall.getData().setScale(1.0f);
        fontSmall.setColor(Color.WHITE);

        playerIdle = new TextureRegion(new Texture("idle.png"));

        TextureRegion[] runFrames = new TextureRegion[4];
        runFrames[0] = new TextureRegion(new Texture("runnning2.png"));
        runFrames[1] = new TextureRegion(new Texture("running3.png"));
        runFrames[2] = new TextureRegion(new Texture("running4.png"));
        runFrames[3] = new TextureRegion(new Texture("runnning5.png"));

        TextureRegion[] fallFrames = new TextureRegion[8];
        for (int i = 0; i < 8; i++) {
            fallFrames[i] = new TextureRegion(new Texture("fall" + (i + 1) + ".png"));
        }

        playerFallAnim = new Animation<>(0.1f, fallFrames);
        playerRunAnim = new Animation<>(0.1f, runFrames);
    }

    public static Assets getInstance() {
        if (instance == null) {
            instance = new Assets();
        }
        return instance;
    }

    public void dispose() {
        if (shapeRenderer != null) shapeRenderer.dispose();
        if (font != null) font.dispose();
        if (fontLarge != null) fontLarge.dispose();
        if (fontMedium != null) fontMedium.dispose();
        if (fontSmall != null) fontSmall.dispose();

        playerIdle.getTexture().dispose();
        for (TextureRegion frame : playerRunAnim.getKeyFrames()) {
            frame.getTexture().dispose();
        }
        for (TextureRegion frame : playerFallAnim.getKeyFrames()) {
            frame.getTexture().dispose();
        }
    }
}
