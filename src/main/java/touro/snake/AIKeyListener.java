package touro.snake;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Random;

public class AIKeyListener{
    private final Snake aiSnake;
    private final Random rand = new Random();
    private static final int DELAY_MS = 80;
    public AIKeyListener(Snake aiSnake) throws InterruptedException
    {
        this.aiSnake = aiSnake;
        moveSelf(aiSnake);
    }

    public void moveSelf(Snake aiSnake) throws InterruptedException {
        List<String> directions = getDirections();
        int len = directions.size();

        if(aiSnake.inBounds() && !aiSnake.eatsSelf()){
            int randIndex = rand.nextInt(len);
            String dirString = directions.get(randIndex);
            Direction direction = Direction.valueOf(dirString);
            aiSnake.turnTo(direction);
            Thread.sleep(DELAY_MS);
        }
    }

    public List<String> getDirections(){
        List<String> directions = Stream.of(Direction.values())
                .map(Direction::getValue) // map using 'getValue'
                .collect(Collectors.toList());
        return directions;
    }

}
