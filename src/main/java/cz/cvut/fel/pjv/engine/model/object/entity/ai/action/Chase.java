package cz.cvut.fel.pjv.engine.model.object.entity.ai.action;

import cz.cvut.fel.pjv.engine.model.game.state.State;
import cz.cvut.fel.pjv.engine.model.object.entity.ai.AITransition;
import cz.cvut.fel.pjv.engine.model.object.entity.Player;
import cz.cvut.fel.pjv.engine.controller.EnemyController;
import cz.cvut.fel.pjv.engine.model.object.entity.Enemy;

/**
 * If the player is not in the not-movement range and
 * if the player is in the visibility zone (target range),
 * the character starts chasing him.
 */
public class Chase extends ActionManager{
    private Player player;

    public Chase(Player player) {
        this.player = player;
    }

    /**
     * Checks whether the player's position has been reached.
     *
     * @param currentEnemy The character that executes this action.
     * @return true, if the character caught up with the player,
     *         false, if the character DID NOT catch up with the player.
     */
    private boolean hasArrived(Enemy currentEnemy) {
        return currentEnemy.getPosition().isInRange(player.getPosition());
    }

    @Override
    protected AITransition initializeTransition() {
        return new AITransition("stand", ((state, currentEnemy) -> hasArrived(currentEnemy)));
    }

    @Override
    public void update(State state, Enemy currentEnemy) {
        EnemyController controller = (EnemyController) currentEnemy.getController();
        controller.toTarget(player.getPosition(), currentEnemy.getPosition());
    }
}
