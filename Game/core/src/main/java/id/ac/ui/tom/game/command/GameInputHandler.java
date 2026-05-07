package id.ac.ui.tom.game.command;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import id.ac.ui.tom.game.screen.PlayScreen;
import java.util.HashMap;
import java.util.Map;

public class GameInputHandler extends InputAdapter {
    private final Map<Integer, Command> keyBindings = new HashMap<>();

    public GameInputHandler(PlayScreen screen) {
        keyBindings.put(Input.Keys.SPACE, new JumpCommand(screen));

        keyBindings.put(Input.Keys.UP, new MoveLaneCommand(screen, -1));
        keyBindings.put(Input.Keys.A, new MoveLaneCommand(screen, -1));

        keyBindings.put(Input.Keys.DOWN, new MoveLaneCommand(screen, 1));
        keyBindings.put(Input.Keys.D, new MoveLaneCommand(screen, 1));

        keyBindings.put(Input.Keys.LEFT, new SprintCommand(screen, Input.Keys.LEFT));
        keyBindings.put(Input.Keys.RIGHT, new SprintCommand(screen, Input.Keys.RIGHT));
    }

    @Override
    public boolean keyDown(int keycode) {
        Command command = keyBindings.get(keycode);
        if (command != null) {
            command.execute();
            return true;
        }
        return false;
    }
}
