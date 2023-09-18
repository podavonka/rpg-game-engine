package cz.cvut.fel.pjv.engine.model.object.entity.ai.action;

import cz.cvut.fel.pjv.engine.controller.EnemyController;
import cz.cvut.fel.pjv.engine.model.object.entity.Enemy;
import cz.cvut.fel.pjv.engine.model.object.entity.ai.AITransition;
import cz.cvut.fel.pjv.engine.model.game.state.State;
import cz.cvut.fel.pjv.engine.model.utils.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * The character is walking for 5 seconds and then stops.
 */
public class Walk extends ActionManager {
    private List<Position> possibleTargets;
    private int updatesCounter;

    public Walk() {
        super();
        possibleTargets = new ArrayList<>();
    }

    /**
     * Checks whether the character has reached the specified point.
     *
     * @param currentEnemy The character that executes this action.
     * @return true, if the character has reached the point,
     *         false, if the character has NOT reached the point.
     */
    private boolean hasArrived(Enemy currentEnemy) {
        return currentEnemy.getPosition().isInRange(possibleTargets.get(0));
    }

    @Override
    protected AITransition initializeTransition() {
        return new AITransition("stand",
                ((state, currentEnemy) -> updatesCounter >= state.getTime().getUFS(5)));
    }

    @Override
    public void update(State state, Enemy currentEnemy) {
        updatesCounter++;

        if(possibleTargets.isEmpty())
            possibleTargets.add(state.getRandomPosition());

        EnemyController controller = (EnemyController) currentEnemy.getController();
        controller.toTarget(possibleTargets.get(0), currentEnemy.getPosition());

        if (currentEnemy.isColliding()) {
            possibleTargets.clear();
            possibleTargets.add(state.getRandomPosition());
        } else if (hasArrived(currentEnemy))
            controller.stop();
    }
}
