package id.ac.ui.tom.game.command;
import id.ac.ui.tom.game.screen.PlayScreen;

public class MoveLaneCommand implements Command {
    private final PlayScreen playScreen;
    private final int direction; // -1 untuk Atas/Kiri, 1 untuk Bawah/Kanan

    public MoveLaneCommand(PlayScreen playScreen, int direction) {
        this.playScreen = playScreen;
        this.direction = direction;
    }

    @Override
    public void execute() {
        playScreen.shiftLane(direction);
    }
}
