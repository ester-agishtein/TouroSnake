package touro.snake;

import javax.swing.*;
import java.awt.*;

public class GardenView extends JComponent {

    private final Garden garden;
    public static final int CELL_SIZE = 10;

    public GardenView(Garden garden) {
        this.garden = garden;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintGrass(g);
        paintFood(g);
        paintSnake(g);
        paintAISnake(g);
    }

    void paintGrass(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    void paintSnake(Graphics g) {
        g.setColor(Color.RED);
        for (Square s : garden.getSnake().getSquares()) {
            g.fillRect(s.getX()*CELL_SIZE, s.getY()*CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }
    void paintAISnake(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(50,50);
        g2d.setColor(Color.WHITE);
        for (Square s : garden.getAiSnake().getSquares()) {
            g2d.fillRect(s.getX()*CELL_SIZE, s.getY()*CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }
    void paintFood(Graphics g) {
        // Berger
        if (garden.getFood() != null) {
            Food food = garden.getFood();
            g.setColor(Color.LIGHT_GRAY);

            int x = food.getX() * CELL_SIZE;
            int y = food.getY() * CELL_SIZE;
            g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
        }
    }
}
