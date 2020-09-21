package touro.snake;

import java.awt.*;

public class SnakeMain {

    public static void main(String[] args) throws InterruptedException {

        // Set up all class dependencies here.

        SnakeHeadStateMachine snakeHeadStateMachine = new SnakeHeadStateMachine(Direction.West);
        Snake snake = new Snake(snakeHeadStateMachine);
        FoodFactory foodFactory = new FoodFactory();

        SnakeHeadStateMachine aiSnakeHeadStateMachine = new SnakeHeadStateMachine(Direction.East);
        Snake aiSnake = new Snake(aiSnakeHeadStateMachine);
        AIKeyListener aiKeyListener = new AIKeyListener(aiSnake);

        Garden garden = new Garden(snake, aiSnake, foodFactory);
        GardenView gardenView = new GardenView(garden);
        SnakeKeyListener snakeKeyListener = new SnakeKeyListener(snake);

        GardenThread thread = new GardenThread(garden, gardenView);
        thread.start();

        new SnakeFrame(gardenView, snakeKeyListener, aiKeyListener).setVisible(true);
    }

}
