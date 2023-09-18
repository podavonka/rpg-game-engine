package cz.cvut.fel.pjv.engine.model.object.entity.ai.action;

import cz.cvut.fel.pjv.engine.model.game.state.State;
import cz.cvut.fel.pjv.engine.model.object.entity.ai.AITransition;
import cz.cvut.fel.pjv.engine.model.object.entity.Enemy;

/**
 * The character stands for a second and then starts moving.
 */
public class Stand extends ActionManager {
    private int updatesCounter;

    @Override
    protected AITransition initializeTransition() {
        return new AITransition("walk",
                ((state, currentCharacter) -> updatesCounter >= state.getTime().getUFS(1)));
    }

    @Override
    public void update(State state, Enemy currentEnemy) {
        updatesCounter++;
    }
}
