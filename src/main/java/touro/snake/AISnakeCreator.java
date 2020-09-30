package touro.snake;
import java.util.Random;

public class AISnakeCreator {
    private final Snake aiSnake;

    public AISnakeCreator(Snake aiSnake) {
        this.aiSnake = aiSnake;
        moveSelf(aiSnake);
    }

    public void moveSelf(Snake aiSnake) {

        if (aiSnake.inBounds() && !aiSnake.eatsSelf()) {
            Direction direction = this.randomDirection();
            aiSnake.turnTo(direction);
        }
    }

    private Direction randomDirection() {
        int pick = new Random().nextInt(Direction.values().length);
        return Direction.values()[pick];
    }

}
