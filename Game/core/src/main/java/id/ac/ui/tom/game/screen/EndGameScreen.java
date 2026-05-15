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

public class EndGameScreen implements Screen {
    private final MainGame game;
    private final int finalScore;
    private final int distanceRun;
    private final int timeSpent;
    private final String gameOverReason;

    private float animationTime = 0;
    private boolean canReturn = false;
    private float returnDelay = 1.5f;
    private final GlyphLayout layout = new GlyphLayout();

    public EndGameScreen(MainGame game, int finalScore, int distanceRun, int timeSpent, String gameOverReason) {
        this.game = game;
        this.finalScore = finalScore;
        this.distanceRun = distanceRun;
        this.timeSpent = timeSpent;
        this.gameOverReason = gameOverReason;
    }

    @Override
    public void render(float delta) {
        animationTime += delta;

        if (!canReturn) {
            returnDelay -= delta;
            if (returnDelay <= 0) canReturn = true;
        }

        Gdx.gl.glClearColor(0.05f, 0.05f, 0.12f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float centerY = screenHeight / 2f;

        Assets.getInstance().shapeRenderer.setProjectionMatrix(game.batch.getProjectionMatrix());
        Assets.getInstance().shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Assets.getInstance().shapeRenderer.setColor(new Color(0.1f, 0.1f, 0.2f, 0.8f));
        Assets.getInstance().shapeRenderer.rect(screenWidth * 0.15f, screenHeight * 0.15f, screenWidth * 0.7f, screenHeight * 0.7f);
        Assets.getInstance().shapeRenderer.end();

        game.batch.begin();

        float blink = (float) Math.abs(Math.sin(animationTime * 1.5));
        drawCenteredText(Assets.getInstance().fontLarge, "GAME OVER!", screenWidth, screenHeight - 80, new Color(1, 0.2f, 0.2f, 0.5f + blink * 0.5f));
        drawCenteredText(Assets.getInstance().fontMedium, gameOverReason, screenWidth, screenHeight - 160, Color.YELLOW);

        // ngasih tau statistik
        drawCenteredText(Assets.getInstance().fontMedium, "STATISTIK PERMAINAN", screenWidth, centerY + 50, Color.CYAN);
        drawCenteredText(Assets.getInstance().font, "Skor Final: " + finalScore + " poin", screenWidth, centerY, Color.WHITE);
        drawCenteredText(Assets.getInstance().font, "Jarak Lari: " + distanceRun + " meter", screenWidth, centerY - 40, Color.WHITE);
        drawCenteredText(Assets.getInstance().font, "Durasi: " + timeSpent + " detik", screenWidth, centerY - 80, Color.WHITE);

        String performance;
        Color performanceColor;
        if (finalScore >= 1500) {
            performance = "S+ RATING - LUAR BIASA!";
            performanceColor = new Color(1, 0.84f, 0, 1);
        } else if (finalScore >= 1000) {
            performance = "A RATING - SANGAT BAGUS!";
            performanceColor = new Color(0, 1, 0, 1);
        } else if (finalScore >= 500) {
            performance = "B RATING - BAGUS";
            performanceColor = new Color(0, 1, 1, 1);
        } else {
            performance = "C RATING - COBA LAGI";
            performanceColor = new Color(1, 0.5f, 0, 1);
        }
        drawCenteredText(Assets.getInstance().fontMedium, performance, screenWidth, centerY - 150, performanceColor);

        // ngeklik lagi untuk balik ke menu
        if (canReturn) {
            float returnBlink = (float) Math.abs(Math.sin(animationTime * 4));
            drawCenteredText(Assets.getInstance().fontSmall, "SENTUH LAYAR UNTUK KEMBALI KE MENU", screenWidth, 50, new Color(1, 1, 1, returnBlink));
        } else {
            drawCenteredText(Assets.getInstance().fontSmall, "Menyimpan skor... " + String.format("%.1f", returnDelay) + "s", screenWidth, 50, Color.GRAY);
        }

        game.batch.end();

        if (canReturn && Gdx.input.isTouched()) {
            game.setScreen(new MainMenuScreen(game));
            this.dispose();
        }
    }

    private void drawCenteredText(BitmapFont font, String text, float screenWidth, float y, Color color) {
        font.setColor(color);
        layout.setText(font, text);
        font.draw(game.batch, layout, (screenWidth - layout.width) / 2f, y);
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}
