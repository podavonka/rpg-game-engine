package cz.cvut.fel.pjv.engine.model.object.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.cvut.fel.pjv.engine.model.game.state.State;
import cz.cvut.fel.pjv.engine.controller.Controller;
import cz.cvut.fel.pjv.engine.model.object.GameObject;
import cz.cvut.fel.pjv.engine.model.object.entity.action.Action;
import cz.cvut.fel.pjv.engine.model.object.entity.possession.Health;
import cz.cvut.fel.pjv.engine.view.animation.AnimationManager;
import cz.cvut.fel.pjv.engine.view.animation.SpriteStorage;
import cz.cvut.fel.pjv.engine.model.utils.*;

import java.awt.*;
import java.util.Optional;

/**
 * Describes every entity, or creature, in the game.
 * Each entity has its own controller and animation sprite set.
 */
public abstract class Entity extends GameObject {
    protected Motion motion;
    protected Direction direction;
    @JsonIgnore
    protected Vector directionVector;
    @JsonIgnore
    protected Controller controller;
    protected Optional<Action> action;
    @JsonIgnore
    protected Size collisionBoxSize;
    @JsonIgnore
    private boolean isColliding = false;
    protected Health health;
    @JsonIgnore
    private boolean isKilled = false;
    protected AnimationManager animationManager;

    @JsonIgnore
    public Entity(Controller controller, SpriteStorage spriteStorage, int healthPoints, double speed) {
        super();
        setMovementElements(controller, speed);
        setCollisionBox();
        this.health = new Health(healthPoints);
        this.animationManager = new AnimationManager(this, spriteStorage.getUnit("steve"));
        this.renderOffset = new Position(size.getWidth() / 2 - 2, size.getHeight() - 4);
    }

    private void setMovementElements(Controller controller, double speed) {
        this.motion = new Motion(speed);
        this.direction = Direction.S;
        this.directionVector = new Vector(0, 0);
        this.controller = controller;
        this.action = Optional.empty();
    }

    private void setCollisionBox() {
        this.collisionBoxSize = new Size(15, 25);
        this.collisionBoxOffset = new Position(collisionBoxSize.getWidth() / 2, collisionBoxSize.getHeight());
    }

    /**
     * Finds out whether the entity is in motion or standing still.
     * Selects animation for a certain action.
     */
    private void decideAnimation() {
        if(action.isPresent())
            animationManager.playAnimation(action.get().getAnimationName());
        else if (motion.isMoving())
            animationManager.playAnimation("walk");
        else
            animationManager.playAnimation("stand");
    }

    /**
     * If there is an action waiting to be performed, executes it.
     *
     * @param state State of the game.
     */
    private void handleAction(State state) {
        if(action.isPresent())
            action.get().update(state, this);
    }

    /**
     * Checks whether the required action is interruptible
     * or continuous and performs it.
     *
     * @param action Action to be executed.
     */
    public void perform(Action action) {
        if(this.action.isPresent() && !this.action.get().isInterruptible())
            return;
        this.action = Optional.of(action);
    }

    /**
     * @param other Position we want to check.
     *
     * @return true, if this position is facing other position.
     *         false, if this position is NOT facing other position.
     */
    public boolean isFacing(Position other) {
        Vector direction = Vector.directionBetweenPositions(other, getPosition());
        double dotProduct = Vector.dotProduct(direction, directionVector);
        return dotProduct > 0;
    }

    /**
     * If the action is to be performed, the movement stops.
     */
    protected void handleMotion() {
        if(action.isPresent())
            motion.stop(true, true);
    }

    /**
     * Determines direction of the entity's movement.
     */
    private void manageDirection() {
        if (motion.isMoving()) {
            this.direction = Direction.fromMotion(motion);
            this.directionVector = motion.getDirection();
        }
    }

    /**
     * Increases number of health points.
     */
    public void increaseHealth(int healthPoints) {
        health.setHealthPoints(health.getHealthPoints() + healthPoints);
    }

    /**
     * Decreases number of health points.
     */
    public void decreaseHealth(int damage) {
        int finalHealthPoints = health.getHealthPoints() - damage;
        if(finalHealthPoints <= 0)
            health.setAlive(false);
        health.setHealthPoints(finalHealthPoints);
    }

    /**
     * Kills the character and cleans up its actions.
     */
    public void kill() {
        isKilled = true;
        cleanup();
    }

    /**
     * Cleans up the character's current action.
     */
    private void cleanup() {
        if(action.isPresent() && action.get().isDone())
            action = Optional.empty();
    }

    /**
     * Handles collisions for each colliding game object.
     */
    private void handleCollisions(State state) {
        state.getCollidingGameObjects(this).forEach(this::handleCollision);
    }

    /**
     * Handles collision of the character with another game object.
     *
     * @param other The game object that the character colliding with.
     */
    protected abstract void handleCollision(GameObject other);

    /**
     * Handles collision with unwalkable tiles.
     *
     * @param state State of the game.
     */
    protected void handleTileCollision(State state) {
        state.getTileManager().getCollidingUnwalkableTileBoxes(getCollisionBox())
                .forEach(tileCollisionBox -> motion.stop(collidesWithTile(tileCollisionBox), collidesWithTile(tileCollisionBox)));
    }

    @Override
    public void update(State state) {
        if(isAlive()) {
            motion.update(controller);
            handleMotion();
            animationManager.update(direction);

            handleCollisions(state);
            handleTileCollision(state);

            manageDirection();
            decideAnimation();
            position.makeMove(motion);
            handleAction(state);

            cleanup();
        } else {
            handleMotion();
            animationManager.playAnimation("dead");
            handleAction(state);
            animationManager.update(direction);
            cleanup();
        }
    }

    /**
     * @see Health#isAlive()
     */
    @JsonIgnore
    public boolean isAlive() {
        return health.isAlive();
    }

    /**
     * Checks whether the character is killed.
     *
     * @return true, if the character is killed,
     *         false, if the character is NOT killed.
     */
    @JsonIgnore
    public boolean isKilled() {
        return isKilled;
    }

    /**
     * Checks whether the character is colliding with another game object.
     *
     * @return true, if the character is colliding with something,
     *         false, if the character is NOT colliding with something.
     */
    @JsonIgnore
    public boolean isColliding(){
        return isColliding;
    }

    @Override
    public boolean collidesWith(CollisionBox otherBox) {
        if (!getCollisionBox().collidesWith(otherBox))
            this.isColliding = false;
        return getCollisionBox().collidesWith(otherBox);
    }

    /**
     * Checks whether collision box of this character
     * collides with collision box of unwalkable tile.
     *
     * @param otherBox Collision box of unwalkable tile.
     * @return true, if the character collides with unwalkable tile,
     *         false, if the character DOES NOT collides with unwalkable tile.
     */
    public boolean collidesWithTile(CollisionBox otherBox) {
        if (getCollisionBox().collidesWith(otherBox))
            this.isColliding = true;
        else
            this.isColliding = false;
        return getCollisionBox().collidesWith(otherBox);
    }

    @Override
    @JsonIgnore
    public CollisionBox getCollisionBox() {
        Position positionWithMotion = Position.copyOf(getPosition());
        positionWithMotion.apply(motion);
        positionWithMotion.subtract(collisionBoxOffset);

        return new CollisionBox(
                new Rectangle(
                        positionWithMotion.parseIntX(),
                        positionWithMotion.parseIntY(),
                        collisionBoxSize.getWidth(),
                        collisionBoxSize.getHeight()
                )
        );
    }

    /**
     * @param newDirection Direction to be set.
     */
    public void setDirection(Direction newDirection) {
        direction = newDirection;
    }

    /**
     * @param directionVector Direction vector to be set.
     */
    public void setDirectionVector(Vector directionVector) {
        this.directionVector = directionVector;
    }

    /**
     * @return Controller of this entity.
     */
    public Controller getController() {
        return controller;
    }

    /**
     * @return Actual action.
     */
    public Optional<Action> getAction() {
        return action;
    }

    /**
     * @return Actual motion.
     */
    public Motion getMotion() {
        return motion;
    }

    /**
     * @return Direction of the actual motion.
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * @return Vector that indicates the direction
     *         and length of actual motion.
     */
    public Vector getDirectionVector() {
        return directionVector;
    }

    /**
     * @return Manager that controls animations of the character.
     */
    public AnimationManager getAnimationManager() {
        return animationManager;
    }

    @Override
    @JsonIgnore
    public Image getSprite() {
        return animationManager.getSprite();
    }
}
