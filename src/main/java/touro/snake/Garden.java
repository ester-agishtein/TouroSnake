package touro.snake;

/**
 * A model that contains the Snake and Food and is responsible for logic of moving the Snake,
 * seeing that food has been eaten and generating new food.
 */
public class Garden {

    public static final int WIDTH = 100;
    public static final int HEIGHT = 40;

    private final Snake snake;
    private final FoodFactory foodFactory;
    private Food food;
    private final Snake aiSnake;

    public Garden(Snake snake, Snake aiSnake, FoodFactory foodFactory) throws InterruptedException {
        this.snake = snake;
        this.foodFactory = foodFactory;
        this.aiSnake = aiSnake;
        AIKeyListener aiKeyListener = new AIKeyListener(aiSnake);
    }

    public Snake getSnake() {
        return snake;
    }

    public Snake getAiSnake() {
        return aiSnake;
    }

    public Food getFood() {
        return food;
    }

    /**
     * Moves the snake, checks to see if food has been eaten and creates food if necessary
     *
     * @return true if the snake is still alive, otherwise false.
     */
    public boolean advance(Snake snake) {
        if (moveSnake(snake)) {
            createFoodIfNecessary();
            return true;
        }
        return false;
    }

    public boolean aiAdvance(Snake snake) {
        this.eatFood(snake);
        this.createFoodIfNecessary();
        //This is a boolean for now so as to fit with the garden thread conditional
        return true;
    }


    /**
     * Moves the Snake, eats the Food or collides with the wall (edges of the Garden), or eats self.
     *
     * @return true if the Snake is still alive, otherwise false.
     */
    boolean moveSnake(Snake snake) {
        snake.move();

        //if collides with wall or self
        if (!snake.inBounds() || snake.eatsSelf()) {
            return false;
        }

        this.eatFood(snake);

        return true;
    }

    public void eatFood(Snake snake){
        //if snake eats the food
        if (snake.getHead().equals(food)) {
            //add square to snake
            snake.grow();
            //remove food
            food = null;
        }
    }
    /**
     * Creates a Food if there isn't one, making sure it's not already on a Square occupied by the Snake.
     */
    void createFoodIfNecessary() {
        //if snake ate food, create new one
        if (food == null) {
            food = foodFactory.newInstance();

            //if new food on snake, put it somewhere else
            while (snake.contains(food)) {
                food = foodFactory.newInstance();
            }
        }
    }

}
