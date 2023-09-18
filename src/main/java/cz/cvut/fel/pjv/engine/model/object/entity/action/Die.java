package cz.cvut.fel.pjv.engine.model.object.entity.action;

import cz.cvut.fel.pjv.engine.model.game.state.State;
import cz.cvut.fel.pjv.engine.model.game.GamePanel;
import cz.cvut.fel.pjv.engine.model.object.entity.Entity;

import java.util.logging.Logger;

/**
 * When character is killed, 'Die' action starts.
 * After that character disappears from the map.
 */
public class Die extends Action {
    private int lifeSpanInUpdates;

    public Die() {
        this.lifeSpanInUpdates = GamePanel.UPS;
        interruptible = false;
    }

    @Override
    public void update(State state, Entity entity) {
        lifeSpanInUpdates--;
        if (isDone()) {
            Logger.getLogger(this.getClass().getName()).info("Entity is dead!");
            entity.kill();
        }
    }

    @Override
    public boolean isDone() {
        return lifeSpanInUpdates == 0;
    }

    @Override
    public String getAnimationName() {
        return "dead";
    }
}
