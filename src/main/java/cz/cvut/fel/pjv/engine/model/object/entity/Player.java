package cz.cvut.fel.pjv.engine.model.object.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.cvut.fel.pjv.engine.model.game.Game;
import cz.cvut.fel.pjv.engine.model.game.state.GameState;
import cz.cvut.fel.pjv.engine.model.game.state.State;
import cz.cvut.fel.pjv.engine.model.net.packets.Packet02Move;
import cz.cvut.fel.pjv.engine.model.object.GameObject;
import cz.cvut.fel.pjv.engine.model.object.TargetZone;
import cz.cvut.fel.pjv.engine.model.utils.Size;
import cz.cvut.fel.pjv.engine.model.utils.Position;
import cz.cvut.fel.pjv.engine.controller.Controller;
import cz.cvut.fel.pjv.engine.model.utils.Motion;
import cz.cvut.fel.pjv.engine.model.object.entity.action.Shoot;
import cz.cvut.fel.pjv.engine.model.object.entity.possession.Coins;
import cz.cvut.fel.pjv.engine.model.object.BagOfCoins;
import cz.cvut.fel.pjv.engine.model.object.entity.possession.Health;
import cz.cvut.fel.pjv.engine.model.object.Heart;
import cz.cvut.fel.pjv.engine.view.animation.SpriteStorage;

import java.util.Comparator;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Describes the main character of the game
 * that the user controls, its actions and features.
 */
public class Player extends Entity {
    private GameState gameState;
    private int lastHealth;
    private Coins coins;
    @JsonIgnore
    private Enemy target;
    @JsonIgnore
    private TargetZone targetZone;
    @JsonIgnore
    private double targetRange;
    protected String username;

    @JsonIgnore
    public Player(GameState gameState,Controller controller, SpriteStorage spriteStorage, TargetZone targetZone){
        super(controller, spriteStorage, 20, 2);
        this.gameState = gameState;
        this.lastHealth = health.getHealthPoints();
        this.coins = new Coins(0);
        this.size = new Size(48,44);
        this.position = (new Position(100,100));
        this.motion = new Motion(2);
        this.targetZone = targetZone;
        this.targetRange = 5 * Game.TILE_SIZE;
    }

    @Override
    public void update(State state) {
        super.update(state);
        if (getMotion().isMoving() && gameState.isMultiplayer()){
            Packet02Move packet = new Packet02Move(this.getUsername(), this.getPosition().parseIntX(), this.getPosition().parseIntY());
            packet.sendData(gameState.socketClient);
        }
        handleTarget(state);
        handleItems(state);
        handleInput(state);
        handleHit();
    }

    /**
     * Assigns {@link TargetZone} to {@link Enemy} that is
     * closest in the target range (field of view).
     *
     * @param state State of the game.
     */
    private void handleTarget(State state) {
        Optional<Enemy> closestEnemy = findClosestEnemy(state);
        if(closestEnemy.isPresent()) {
            Enemy enemy = closestEnemy.get();
            if(!enemy.equals(target)) {
                targetZone.setParent(enemy);
                target = enemy;
            }
        } else {
            targetZone.clearParent();
            target = null;
        }
    }

    /**
     * Finds the closest living enemy in sight.
     *
     * @param state State of the game.
     */
    private Optional<Enemy> findClosestEnemy(State state) {
        return state.getGameObjectsOfClass(Enemy.class).stream()
                .filter(enemy -> getPosition().distanceTo(enemy.getPosition()) < targetRange)
                .filter(enemy -> isFacing(enemy.getPosition()))
                .filter(enemy -> enemy.isAlive())
                .min(Comparator.comparingDouble(npc -> position.distanceTo(npc.getPosition())));
    }

    /**
     * Handles interactions with items randomly spawned on the map.
     * By stepping on this items the player receives a reward.
     *
     * @param state State of the game.
     */
    private void handleItems(State state) {
        if (state.getGameObjectsOfClass(BagOfCoins.class).stream().anyMatch(bagOfCoins -> this.collidesWith(bagOfCoins.getCollisionBox()))){
            state.getGameObjectsOfClass(BagOfCoins.class).stream().filter(bagOfCoins -> this.collidesWith(bagOfCoins.getCollisionBox())).forEach(bagOfCoins -> bagOfCoins.setUsed(true));
            this.getCoins().earnCoins(100);
            Logger.getLogger(this.getClass().getName()).info("Player picked up bag of coins!");
        }

        if (state.getGameObjectsOfClass(Heart.class).stream().anyMatch(heart -> this.collidesWith(heart.getCollisionBox()))){
            state.getGameObjectsOfClass(Heart.class).stream().filter(bagOfCoins -> this.collidesWith(bagOfCoins.getCollisionBox())).forEach(heart -> heart.setUsed(true));
            this.increaseHealth(2);
            Logger.getLogger(this.getClass().getName()).info("Player picked up extra health!");

        }
    }

    /**
     * If the 'Space' key was pressed and there is
     * a target nearby, the character shoots.
     *
     * @param state State of the game.
     */
    private void handleInput(State state) {
        if (controller != null) {
            if (controller.isShoot() && target != null)
                perform(new Shoot(target, this));
        }
    }

    /**
     * If number of health points decreased, the character was injured.
     */
    private void handleHit() {
        if (health.getHealthPoints() < lastHealth){
            Logger.getLogger(this.getClass().getName()).info("Player get hit!");
            animationManager.playAnimation("hit");
            lastHealth = health.getHealthPoints();
        }
    }

    @Override
    protected void handleCollision(GameObject other) {
    }

    /**
     * @param coins Coins to be set.
     */
    public void setCoins(Coins coins) {
        this.coins = coins;
    }

    /**
     * @param lastHealth Health to be set as last.
     */
    public void setLastHealth(int lastHealth) {
        this.lastHealth = lastHealth;
    }

    /**
     * @return Instance of {@link Coins}.
     */
    public Coins getCoins() {
        return coins;
    }

    /**
     * @return Number of available player's coins.
     * @see Coins#getAvailableCoins()
     */
    @JsonIgnore
    public int getNumberOfCoins() {
        return coins.getAvailableCoins();
    }

    /**
     * @return Instance of {@link Health}.
     */
    public Health getHealth() {
        return health;
    }

    /**
     * @return Number of actual player's health points.
     * @see Health#getHealthPoints()
     */
    @JsonIgnore
    public int getHealthPoints() { return health.getHealthPoints(); }

    public String getUsername() {
        return username;
    }
}
