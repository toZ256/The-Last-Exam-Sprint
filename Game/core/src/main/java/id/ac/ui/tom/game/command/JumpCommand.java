package id.ac.ui.tom.game.command;
import id.ac.ui.tom.game.screen.PlayScreen;

public class JumpCommand implements Command {
    private final PlayScreen playScreen;

    public JumpCommand(PlayScreen playScreen) {
        this.playScreen = playScreen;
    }

    @Override
    public void execute() {
        playScreen.jump();
    }
}
