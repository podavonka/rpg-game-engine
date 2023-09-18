package cz.cvut.fel.pjv.engine.model.game;

import cz.cvut.fel.pjv.engine.controller.WindowHandler;
import cz.cvut.fel.pjv.engine.model.game.state.GameState;
import cz.cvut.fel.pjv.engine.view.Window;
import cz.cvut.fel.pjv.engine.model.utils.Size;
import cz.cvut.fel.pjv.engine.controller.InputHandler;
import cz.cvut.fel.pjv.engine.model.game.state.State;
import cz.cvut.fel.pjv.engine.model.game.state.MenuState;

/**
 * Sets the game instance.
 * Configures the parameters needed to run it.
 */
public class Game {

    // Size of one tile
    public static int TILE_SIZE = 16;

    private InputHandler inputHandler;
    private WindowHandler windowHandler;
    private Window window;
    private State state;

    public Game() {
        inputHandler = new InputHandler();
        window = new Window(inputHandler);
        state = new MenuState(inputHandler, new Size(window.WIDTH, window.HEIGHT));
    }

    /**
     * Updates the display of all game objects and UI components.
     * @see State#update(Game)
     */
    public void update() {
        state.update(this);
    }

    /**
     * Visualizes updates and renders objects.
     * @see Window#render(State)
     */
    public void render() {
        window.render(state);
    }

    /**
     * Enters required state.
     *
     * @param nextState State to be entered.
     */
    public void enterState(State nextState) {
        state = nextState;
        if (state instanceof GameState && state.isMultiplayer()){
            windowHandler = new WindowHandler(state, window);
        }
    }
}
