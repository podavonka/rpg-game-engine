package cz.cvut.fel.pjv.engine.model.object.entity.ai;

import cz.cvut.fel.pjv.engine.model.game.state.State;
import cz.cvut.fel.pjv.engine.model.object.entity.Enemy;

/**
 * The status of the current action.
 */
public interface AICondition {

    /**
     * Checks whether the current character has completed the action.
     *
     * @param state State of the game.
     * @param currentEnemy Character whose actions are controlled.
     */
    boolean isDone(State state, Enemy currentEnemy);
}
