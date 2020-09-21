package touro.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

public class SnakeFrame extends JFrame {

    public SnakeFrame(
            GardenView gardenView,
            SnakeKeyListener snakeKeyListener,
            AIKeyListener aiKeyListener

    ) throws HeadlessException {
        super();

        setSize(1000,400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Snake");
        setLayout(new BorderLayout());

        add(gardenView, BorderLayout.CENTER);
        addKeyListener(snakeKeyListener);
        addKeyListener(aiKeyListener);
    }
}
