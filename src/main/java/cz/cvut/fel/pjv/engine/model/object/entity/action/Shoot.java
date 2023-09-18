package cz.cvut.fel.pjv.engine.model.object.entity.action;

import cz.cvut.fel.pjv.engine.model.game.state.State;
import cz.cvut.fel.pjv.engine.model.game.GamePanel;
import cz.cvut.fel.pjv.engine.model.object.entity.Entity;
import cz.cvut.fel.pjv.engine.model.object.entity.Player;

import java.util.logging.Logger;

/**
 * When character is in another character's target zone,
 * 'Shoot' action can starts. When the action is completed,
 * the target character will lose one health point.
 *
 * If the target character was killed, performs new {@link Die} action.
 *
 * If the shooter is an instance of {@link Player}, he can control action
 * using PlayerController, more exactly he can press 'Space' key
 * to execute 'Shoot' action. After target's death he gains coins.
 *
 * If the shooter is an instance of other entity, he can control action
 * using EnemyController. He starts shooting as soon as target appears.
 */
public class Shoot extends Action {
    private int lifeSpanInUpdates;
    private Entity target;
    private Entity shooter;

    public Shoot(Entity target, Entity shooter) {
        lifeSpanInUpdates = GamePanel.UPS;
        this.target = target;
        this.shooter = shooter;
        interruptible = false;
    }

    @Override
    public void update(State state, Entity entity) {
        lifeSpanInUpdates--;
        if (isDone())
            target.decreaseHealth(1);
        if (!target.isAlive()) {
            Logger.getLogger(this.getClass().getName()).info("Entity is dead!");
            target.kill();
            if (shooter instanceof Player)
                ((Player) shooter).getCoins().earnCoins(50);
        }
    }

    @Override
    public boolean isDone() {
        return lifeSpanInUpdates == 0;
    }

    @Override
    public String getAnimationName() {
        return "shoot";
    }
}
