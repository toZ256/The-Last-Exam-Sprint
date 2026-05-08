package id.ac.ui.tom.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import id.ac.ui.tom.game.Assets;
import id.ac.ui.tom.game.MainGame;
import id.ac.ui.tom.game.observer.GameEventPublisher;
import id.ac.ui.tom.game.observer.GameLogObserver;
import id.ac.ui.tom.game.observer.ScoreSubmitObserver;
import id.ac.ui.tom.game.strategy.ScoringStrategy;
import id.ac.ui.tom.game.strategy.StandardScoringStrategy;

public class PlayScreen implements Screen {
    private final MainGame game;

    // Logika Lari
    private float currentSpeed = 0;
    private int lastKeyUsed = -1;
    private final float FRICTION  = 120f;
    private final float ACCEL     = 60f;
    private float score = 0;

    // Logika Isometrik & Lajur
    private int currentLane     = 1;
    private float playerX       = 300;
    private final float playerY = 150;
    private float playerZ       = 0;
    private float jumpVelocity  = 0;
    private final float GRAVITY = -1200f;

    private final float[] BASE_LANE_X = {50f, 250f, 450f};
    private final float SLANT_RATIO   = 0.4f;

    // Logika Obstacle
    private id.ac.ui.tom.game.obstacle.Obstacle currentObstacle;

    // Status Game
    private boolean gameOver       = false;
    private boolean eventPublished = false;

    // Sistem Waktu dan Terpeleset
    private float timeRemaining = 60f;
    private boolean isSlipping  = false;
    private float slipTimer     = 0f;

    private final ScoringStrategy scoringStrategy = new StandardScoringStrategy();
    private final GameEventPublisher eventPublisher = new GameEventPublisher();

    public PlayScreen(MainGame game) {
        this.game = game;
        Gdx.input.setInputProcessor(new id.ac.ui.tom.game.command.GameInputHandler(this));
        currentObstacle = id.ac.ui.tom.game.obstacle.ObstacleFactory.createRandomObstacle(800);

        eventPublisher.addListener(new GameLogObserver());
        eventPublisher.addListener(new ScoreSubmitObserver());
    }

    // Method yang dieksekusi oleh JumpCommand
    public void jump() {
        if (!gameOver && !isSlipping && playerZ <= 0) {
            jumpVelocity = 500;
        }
    }

    // Method yang dieksekusi oleh MoveLaneCommand
    public void shiftLane(int direction) {
        if (!gameOver && !isSlipping) {
            currentLane = Math.max(0, Math.min(2, currentLane + direction));
        }
    }

    // Method yang dieksekusi oleh SprintCommand
    public void sprint(int keycode) {
        if (!gameOver && !isSlipping) {
            if (keycode != lastKeyUsed) {
                currentSpeed += ACCEL;
                lastKeyUsed = keycode;
            }
        }
    }

    private float getIsometricX(int lane, float y) {
        return BASE_LANE_X[lane] + (y * SLANT_RATIO);
    }

    @Override
    public void render(float delta) {
        if (!gameOver) {
            // Kurangi waktu
            timeRemaining -= delta;
            if (timeRemaining <= 0) {
                timeRemaining = 0;
                triggerGameOver();
            }

            // Logika kepeleset
            if (isSlipping) {
                slipTimer -= delta;
                if (slipTimer <= 0) {
                    isSlipping = false;
                }
            } else {
                currentSpeed -= FRICTION * delta;
                if (currentSpeed < 0) currentSpeed = 0;

                if (currentSpeed >= 750) {
                    isSlipping   = true;
                    slipTimer    = 3.0f;
                    currentSpeed = 0;
                    jumpVelocity = 0;
                    playerZ      = 0;

                    eventPublisher.notifyPlayerSlip(slipTimer);
                }
            }

            score += scoringStrategy.calculateScoreDelta(currentSpeed, timeRemaining, delta);

            // Update posisi isometrik karakter
            float targetX = getIsometricX(currentLane, playerY);
            playerX += (targetX - playerX) * 15 * delta;

            // Fisika lompat
            if (playerZ > 0 || jumpVelocity > 0) {
                jumpVelocity += GRAVITY * delta;
                playerZ      += jumpVelocity * delta;
                if (playerZ < 0) {
                    playerZ      = 0;
                    jumpVelocity = 0;
                }
            }

            // Pergerakan Obstacle
            currentObstacle.y -= currentSpeed * delta;
            if (currentObstacle.y < -50) {
                currentObstacle = id.ac.ui.tom.game.obstacle.ObstacleFactory.createRandomObstacle(800);
            }

            // Deteksi Tabrakan
            if (currentLane == currentObstacle.lane && Math.abs(currentObstacle.y - playerY) < 30) {
                if (currentObstacle.needsJump() && playerZ < 40) {
                    triggerGameOver();
                } else if (!currentObstacle.needsJump() && playerZ < 5) {
                    triggerGameOver();
                }
            }
        }

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Assets.getInstance().shapeRenderer.setProjectionMatrix(game.batch.getProjectionMatrix());
        Assets.getInstance().shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        Assets.getInstance().shapeRenderer.setColor(Color.DARK_GRAY);
        for (int i = 0; i < 3; i++) {
            float startX = getIsometricX(i, 0);
            float endX   = getIsometricX(i, Gdx.graphics.getHeight());
            Assets.getInstance().shapeRenderer.rectLine(startX, 0, endX, Gdx.graphics.getHeight(), 5);
        }

        // Gambar obstacle
        float obsX = getIsometricX(currentObstacle.lane, currentObstacle.y);
        currentObstacle.draw(Assets.getInstance().shapeRenderer, obsX, 10);

        // Gambar pemain
        Assets.getInstance().shapeRenderer.setColor(isSlipping ? Color.ORANGE : Color.CYAN);
        drawSkewedBox(playerX, playerY + playerZ, 40, 50, 15);

        Assets.getInstance().shapeRenderer.end();

        game.batch.begin();
        Assets.getInstance().font.setColor(Color.WHITE);
        Assets.getInstance().font.draw(game.batch, "KECEPATAN: " + (int) currentSpeed,
            20, Gdx.graphics.getHeight() - 20);
        Assets.getInstance().font.draw(game.batch, "JARAK LARI: " + (int) score + " m",
            20, Gdx.graphics.getHeight() - 50);

        Assets.getInstance().font.setColor(timeRemaining < 10 ? Color.RED : Color.WHITE);
        Assets.getInstance().font.draw(game.batch, "WAKTU: " + (int) timeRemaining + " s",
            Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 20);

        if (isSlipping) {
            Assets.getInstance().font.setColor(Color.ORANGE);
            Assets.getInstance().font.draw(game.batch,
                "TERPELESET! TUNGGU " + String.format("%.1f", slipTimer) + " s",
                Gdx.graphics.getWidth() / 2f - 150, Gdx.graphics.getHeight() / 2f + 100);
        } else if (currentSpeed > 600) {
            Assets.getInstance().font.setColor(Color.RED);
            Assets.getInstance().font.draw(game.batch, "AWAS TERPELESET!",
                20, Gdx.graphics.getHeight() - 80);
        }

        if (gameOver) {
            Assets.getInstance().font.setColor(Color.YELLOW);
            String msg = timeRemaining <= 0
                ? "GAME OVER! TERLAMBAT MASUK KELAS!"
                : "GAME OVER! MENABRAK!";
            Assets.getInstance().font.draw(game.batch, msg,
                Gdx.graphics.getWidth() / 2f - 200, Gdx.graphics.getHeight() / 2f);
        }
        game.batch.end();
    }

    private void triggerGameOver() {
        if (eventPublished) return;
        gameOver       = true;
        eventPublished = true;

        int durationSpent = 60 - (int) timeRemaining;
        eventPublisher.notifyGameOver((int) score, score, durationSpent);
    }

    private void drawSkewedBox(float x, float y, float width, float height, float skew) {
        float x1 = x - width / 2;
        float x2 = x + width / 2;
        float y2 = y + height;
        Assets.getInstance().shapeRenderer.triangle(x1, y, x2, y, x2 + skew, y2);
        Assets.getInstance().shapeRenderer.triangle(x1, y, x2 + skew, y2, x1 + skew, y2);
    }

    @Override public void dispose() { }
    @Override public void show() { }
    @Override public void resize(int width, int height) { }
    @Override public void pause() { }
    @Override public void resume() { }
    @Override public void hide() { }
}
