package touro.snake;

/**
 * The possible directions that the Snake's head can be in.
 */
public enum Direction {
    North("North"), East("East"), South("South"), West("West");

    private String direction;

    Direction(String direction){this.direction = direction;}

    String getValue(){return direction;}
}
