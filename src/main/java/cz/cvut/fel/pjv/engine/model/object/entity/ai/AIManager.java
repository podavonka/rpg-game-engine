package cz.cvut.fel.pjv.engine.model.object.entity.ai;

import cz.cvut.fel.pjv.engine.model.game.state.State;
import cz.cvut.fel.pjv.engine.model.object.entity.Player;
import cz.cvut.fel.pjv.engine.model.object.entity.Enemy;
import cz.cvut.fel.pjv.engine.model.object.entity.ai.action.ActionManager;
import cz.cvut.fel.pjv.engine.model.object.entity.ai.action.Chase;
import cz.cvut.fel.pjv.engine.model.object.entity.ai.action.Stand;
import cz.cvut.fel.pjv.engine.model.object.entity.ai.action.Walk;

/**
 * Manages actions that are unique to characters
 * with artificial intelligence, and automated
 * transitions between them.
 */
public class AIManager {
    private ActionManager currentState;

    public AIManager() {
        transitionTo("stand");
    }

    /**
     * Sets what the next action will be.
     *
     * @param nextState Next action to be executed.
     */
    private void transitionTo(String nextState) {
        switch(nextState) {
            case "walk":
                currentState = new Walk();
                return;
            case "stand":
            default:
                currentState = new Stand();
        }
    }

    /**
     * Changes any action to 'Chase'.
     *
     * @param player Character to be chased.
     */
    public void transitionToChase(Player player){
        currentState = new Chase(player);
    }

    /**
     * Updates the current action.
     * Checks whether transition to next action is needed.
     *
     * @param state State of the game.
     * @param currentEnemy Character whose actions are controlled.
     */
    public void update(State state, Enemy currentEnemy) {
        currentState.update(state, currentEnemy);
        if(currentState.isNeededTransition(state, currentEnemy))
            transitionTo(currentState.getNextState());
    }
}
