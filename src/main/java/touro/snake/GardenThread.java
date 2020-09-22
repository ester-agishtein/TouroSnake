package touro.snake;

/**
 * Thread responsible for advancing the Garden and updating GardenView.
 */
public class GardenThread extends Thread {

    private static final int DELAY_MS = 80;
    private final Garden garden;
    private final GardenView gardenView;

    public GardenThread(Garden garden, GardenView gardenView) {
        this.garden = garden;
        this.gardenView = gardenView;
    }

    /**
     * Every second, advance the Garden (moving the Snake, eating, death) and repaint the Garden
     */
    public void run() {
        while (garden.advance(garden.getSnake()) && garden.aiAdvance(garden.getAiSnake())) {
            try {
                gardenView.repaint();
                garden.advance(garden.getAiSnake());
                AISnakeCreator aiSnakeCreator = new AISnakeCreator(garden.getAiSnake());
                aiSnakeCreator.moveSelf(garden.getAiSnake());
                Thread.sleep(DELAY_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
