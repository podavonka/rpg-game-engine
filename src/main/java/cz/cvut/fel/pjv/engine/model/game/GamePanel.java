package cz.cvut.fel.pjv.engine.model.game;

import javax.swing.JPanel;

/**
 * Creates a game panel that is responsible
 * for rendering and framerate of the window.
 */
public class GamePanel extends JPanel implements Runnable {

    /**
     * Updates per second.
     */
    public static final int UPS = 60;
    private final double updateRate = 1.0d/60.0d;

    private Game game;
    private boolean isRunning = false;
    private int fps, ups;
    private long nextStatTime;

    public GamePanel(Game game) {
        this.game = game;
    }

    /**
     * Game loop for updates and rendering.
     */
    public void run() {
        isRunning = true;
        double updateTime = 0;
        long currentTime, lastUpdate = System.currentTimeMillis();
        nextStatTime = System.currentTimeMillis() + 1000;

        while(isRunning) {
            currentTime = System.currentTimeMillis();
            double LRT = (currentTime - lastUpdate) / 1000.0d; // last render time in seconds
            updateTime += LRT;
            lastUpdate = currentTime;

            if (updateTime >= updateRate) {
                while (updateTime >= updateRate) {
                    update();
                    updateTime -= updateRate;
                }
                render();
            }
//            printStats();
        }
    }

    /**
     * Updates game state.
     * @see Game#update()
     */
    public void update() {
        game.update();
        ups++;
    }

    /**
     * Renders objects on canvas.
     * @see Game#render()
     */
    public void render() {
        game.render();
        fps++;
    }

    /**
     * Displays statistics based on the comparison of
     * frames and updates per second.
     */
    private void printStats() {
        if (System.currentTimeMillis() > nextStatTime) {
            System.out.format("FPS: %d, UPS: %d\n", fps, ups);
            fps = 0;
            ups = 0;
            nextStatTime = System.currentTimeMillis() + 1000;
        }
    }
}
