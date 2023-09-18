package cz.cvut.fel.pjv.engine.model.object.entity.ai.action;

import cz.cvut.fel.pjv.engine.model.game.state.State;
import cz.cvut.fel.pjv.engine.model.object.entity.Enemy;
import cz.cvut.fel.pjv.engine.model.object.entity.ai.AITransition;

/**
 * Initializes and executes transitions
 * between actions if it is necessary.
 *
 * Manages actions associated with movement.
 */
public abstract class ActionManager {
    private AITransition transition;

    public ActionManager() {
        this.transition = initializeTransition();
    }

    /**
     * Starts the transition to the next action.
     *
     * @return Transition to the next action.
     */
    protected abstract AITransition initializeTransition();

    /**
     * Executes the current action.
     *
     * @param state State of the game.
     * @param currentEnemy Character whose actions are controlled.
     */
    public abstract void update(State state, Enemy currentEnemy);

    /**
     * Checks whether transition is needed.
     *
     * @return true, if transition is needed,
     *         false, if transition is NOT needed.
     */
    public boolean isNeededTransition(State state, Enemy currentEnemy) {
        return transition.isNeededTransition(state, currentEnemy);
    }

    /**
     * @return Name of the next action.
     */
    public String getNextState() {
        return transition.getNextState();
    }
}
