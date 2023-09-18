package cz.cvut.fel.pjv.engine.view.ui.base.clickable;

import cz.cvut.fel.pjv.engine.model.game.state.State;

/**
 * Contains certain action on mouse click.
 */
public interface ClickAction {

    /**
     * Executes action on click.
     *
     * @param state State of the game.
     */
    void execute(State state);
}
