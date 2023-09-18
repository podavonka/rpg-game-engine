package cz.cvut.fel.pjv.engine.model.object.entity;

import cz.cvut.fel.pjv.engine.model.game.state.State;
import cz.cvut.fel.pjv.engine.controller.Controller;
import cz.cvut.fel.pjv.engine.model.object.GameObject;
import cz.cvut.fel.pjv.engine.model.object.TargetZone;
import cz.cvut.fel.pjv.engine.model.object.entity.ai.AIManager;
import cz.cvut.fel.pjv.engine.model.object.entity.action.Shoot;
import cz.cvut.fel.pjv.engine.view.animation.SpriteStorage;

import java.util.Comparator;
import java.util.Optional;

/**
 * Describes all enemies as a whole.
 */
public abstract class Enemy extends Entity {
    private int lastHealth;
    private AIManager ai;
    private Player target;
    private TargetZone targetZone;
    protected double targetRange;

    public Enemy(Controller controller, SpriteStorage spriteStorage, int healthPoints, TargetZone targetZone, double speed) {
        super(controller, spriteStorage, healthPoints, speed);
        this.lastHealth = health.getHealthPoints();
        this.ai = new AIManager();
        this.targetZone = targetZone;
    }

    @Override
    public void update(State state) {
        super.update(state);
        handleTarget(state);
        handleHit();
        if (target != null && target.isAlive())
            perform(new Shoot(target, this));
        ai.update(state, this);
    }

    /**
     * If number of health points decreased, the character was injured.
     */
    private void handleHit() {
        if (health.getHealthPoints() < lastHealth) {
            animationManager.playAnimation("hit");
            lastHealth = health.getHealthPoints();
        }
    }

    /**
     * Assigns {@link TargetZone} to {@link Player} that is
     * closest in the target range (field of view).
     *
     * @param state State of the game.
     */
    private void handleTarget(State state) {
        Optional<Player> closestPlayer = findClosestPlayer(state);
        if(closestPlayer.isPresent()) {
            Player player = closestPlayer.get();
            if(!player.equals(target)) {
                ai.transitionToChase(player);
                targetZone.setParent(player);
                target = player;
            }
        } else {
            targetZone.clearParent();
            target = null;
        }
    }

    /**
     * Finds the closest living player in sight.
     *
     * @param state State of the game.
     */
    private Optional<Player> findClosestPlayer(State state) {
        return state.getGameObjectsOfClass(Player.class).stream()
                .filter(player -> getPosition().distanceTo(player.getPosition()) < targetRange)
                .filter(player -> isFacing(player.getPosition()))
                .filter(player -> isAlive())
                .min(Comparator.comparingDouble(npc -> position.distanceTo(npc.getPosition())));
    }

    @Override
    protected void handleCollision(GameObject other) {
        if(other instanceof Player)
            motion.stop(true,true);
    }
}