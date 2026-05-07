package id.ac.ui.tom.game.obstacle;

import java.util.Random;

public class ObstacleFactory {
    private static final Random random = new Random();

    public static Obstacle createRandomObstacle(float startY) {
        int lane = random.nextInt(3);
        int type = random.nextInt(2); // 0 atau 1

        if (type == 0) {
            return new WaterPuddle(lane, startY);
        } else {
            return new ChairObstacle(lane, startY);
        }
    }
}
