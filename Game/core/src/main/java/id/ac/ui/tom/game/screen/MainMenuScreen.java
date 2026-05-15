package id.ac.ui.tom.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import id.ac.ui.tom.game.Assets;
import id.ac.ui.tom.game.MainGame;

public class MainMenuScreen implements Screen {
    private final MainGame game;
    private float animationTime = 0;
    private final GlyphLayout layout = new GlyphLayout(); // Tool untuk mengukur lebar teks

    public MainMenuScreen(MainGame game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        animationTime += delta;

        Gdx.gl.glClearColor(0.05f, 0.05f, 0.12f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // garis buat pemisah
        Assets.getInstance().shapeRenderer.setProjectionMatrix(game.batch.getProjectionMatrix());
        Assets.getInstance().shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Assets.getInstance().shapeRenderer.setColor(Color.WHITE);
        Assets.getInstance().shapeRenderer.rect(screenWidth * 0.2f, screenHeight - 270, screenWidth * 0.6f, 2);
        Assets.getInstance().shapeRenderer.end();

        game.batch.begin();

        // judul game
        drawCenteredText(Assets.getInstance().fontLarge, "THE LAST EXAM", screenWidth, screenHeight - 120, Color.CYAN);
        drawCenteredText(Assets.getInstance().fontMedium, "Jgn ampe telat ujian, ntar ga lulus wkwkw", screenWidth, screenHeight - 200, Color.LIGHT_GRAY);

        // objektif game
        drawCenteredText(Assets.getInstance().fontMedium, "OBJEKTIF", screenWidth, screenHeight - 530, Color.LIME);
        drawCenteredText(Assets.getInstance().fontSmall, "Lari ke kelas secepat mungkin, jangan ampe nabrak nanti meninggoy!", screenWidth, screenHeight - 570, Color.WHITE);

        // teks buat nandain kalo mo mulai
        float blink = (float) Math.abs(Math.sin(animationTime * 3));
        drawCenteredText(Assets.getInstance().fontMedium, "SENTUH LAYAR UNTUK MULAI", screenWidth, 80, new Color(1, 1, 1, blink));

        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new PlayScreen(game));
            this.dispose();
        }
    }

    // method buat tengahin teksnya
    private void drawCenteredText(BitmapFont font, String text, float screenWidth, float y, Color color) {
        font.setColor(color);
        layout.setText(font, text);
        float x = (screenWidth - layout.width) / 2f;
        font.draw(game.batch, layout, x, y);
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}
