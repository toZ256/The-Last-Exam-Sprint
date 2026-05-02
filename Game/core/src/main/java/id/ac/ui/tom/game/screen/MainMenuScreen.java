package id.ac.ui.tom.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import id.ac.ui.tom.game.MainGame;

public class MainMenuScreen implements Screen {
    private final MainGame game;

    public MainMenuScreen(MainGame game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        // Membersihkan layar dengan warna (misal: biru tua)
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        // Nanti ada teks judul gamenya di sini
        game.batch.end();

        // Logika sementara, klik layar buat pindah ke game-nya
        if (Gdx.input.isTouched()) {
            Gdx.app.log("Menu", "Layar disentuh! Siap-siap lari ke Plaza Quantum.");
            game.setScreen(new PlayScreen(game));
            this.dispose();
        }
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}
