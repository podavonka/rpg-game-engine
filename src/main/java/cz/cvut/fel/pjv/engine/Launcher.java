package cz.cvut.fel.pjv.engine;

import cz.cvut.fel.pjv.engine.model.game.Game;
import cz.cvut.fel.pjv.engine.model.game.GamePanel;

/**
 * Launches the window.
 * Starts the game.
 */
public class Launcher {

    /**
     * Creates a thread in which all processes in the game are running.
     */
    public static void main(String[] args) {
        new Thread(new GamePanel(new Game())).start();
    }
}
