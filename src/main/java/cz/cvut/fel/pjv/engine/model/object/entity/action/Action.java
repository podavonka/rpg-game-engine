package cz.cvut.fel.pjv.engine.model.object.entity.action;

import cz.cvut.fel.pjv.engine.model.game.state.State;
import cz.cvut.fel.pjv.engine.model.object.entity.Entity;

/**
 * Action that can be performed by player
 * or character with artificial intelligence.
 */
public abstract class Action {
    protected boolean interruptible;

    public Action() {
        interruptible = true;
    }

    /**
     * Executes the action until it is done.
     */
    public abstract void update(State state, Entity entity);

    /**
     * Checks if the action has been already done.
     *
     * @return true, if the action is done,
     *         false, if the action is NOT done.
     */
    public abstract boolean isDone();

    /**
     * @return Name of the executing animation.
     */
    public abstract String getAnimationName();

    /**
     * Checks whether the action is interruptible or continuous.
     *
     * @return true, if the action is INTERRUPTIBLE,
     *         false, if the action is CONTINUOUS.
     */
    public boolean isInterruptible() {
        return interruptible;
    }
}