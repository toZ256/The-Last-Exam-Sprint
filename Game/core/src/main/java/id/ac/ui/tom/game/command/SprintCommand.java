package id.ac.ui.tom.game.command;
import id.ac.ui.tom.game.screen.PlayScreen;

public class SprintCommand implements Command {
    private final PlayScreen playScreen;
    private final int keycode;

    public SprintCommand(PlayScreen playScreen, int keycode) {
        this.playScreen = playScreen;
        this.keycode = keycode;
    }

    @Override
    public void execute() {
        playScreen.sprint(keycode);
    }
}
