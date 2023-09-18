package cz.cvut.fel.pjv.engine.model.object.entity.ai;

import cz.cvut.fel.pjv.engine.model.game.state.State;
import cz.cvut.fel.pjv.engine.model.object.entity.Enemy;

/**
 * Transition from one action to another.
 */
public class AITransition {
    private String nextState;
    private AICondition condition;

    public AITransition(String nextState, AICondition condition) {
        this.nextState = nextState;
        this.condition = condition;
    }

    /**
     * Checks whether transition is needed.
     * @see AICondition#isDone(State, Enemy)
     *
     * @param state State of the game.
     * @param currentEnemy Character whose actions are controlled.
     * @return true, if transition is needed,
     *         false, if transition is NOT needed.
     */
    public boolean isNeededTransition(State state, Enemy currentEnemy) {
        return condition.isDone(state, currentEnemy);
    }

    /**
     * @return Name of the next action.
     */
    public String getNextState() {
        return nextState;
    }
}
