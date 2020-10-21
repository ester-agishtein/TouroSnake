package touro.snake;

/**
 * Thread responsible for advancing the Garden and updating GardenView.
 */
public class GardenThread extends Thread {

    private static final int DELAY_MS = 80;
    private final Garden garden;
    private final GardenView gardenView;
    private AutomateSnake automateSnake;

    public GardenThread(Garden garden, GardenView gardenView) {
        this.garden = garden;
        this.gardenView = gardenView;
        this.automateSnake = new AutomateSnake();
    }

    /**
     * Every second, advance the Garden (moving the Snake, eating, death) and repaint the Garden
     */
    public void run() {
            while (garden.advance(garden.getSnake())) {
                try {
                    gardenView.repaint();
                    garden.advance(garden.getAiSnake());
                    automateSnake.changeDirection(garden.getAiSnake());
                    Thread.sleep(DELAY_MS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

    }
}
