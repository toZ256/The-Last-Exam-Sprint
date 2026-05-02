package id.ac.ui.tom.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import id.ac.ui.tom.game.screen.MainMenuScreen;

public class MainGame extends Game {
    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
       
        // Langsung ke main menu
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        // Biarkan kelas Game yang menangani render screen yang aktif
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
