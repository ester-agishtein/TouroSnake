package touro.snake;
import java.util.Random;

public class AutomateSnake {
    private final Random rand = new Random();
    private final SnakeHeadStateMachine snakeHeadStateMachine = new SnakeHeadStateMachine(Direction.East);
    public Snake snake = new Snake(snakeHeadStateMachine);

    public AutomateSnake() {
        changeDirection(snake);
    }

    public void changeDirection(Snake aiSnake) {
        if (aiSnake.inBounds() && !aiSnake.eatsSelf()) {
            Direction direction = this.randomDirection();
            aiSnake.turnTo(direction);
        }
    }

    private Direction randomDirection() {
        int pick = rand.nextInt(Direction.values().length);
        return Direction.values()[pick];
    }
    public Snake getAiSnake(){return snake;}

}
