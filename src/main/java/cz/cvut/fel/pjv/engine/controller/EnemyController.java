package cz.cvut.fel.pjv.engine.controller;

import cz.cvut.fel.pjv.engine.model.utils.Position;

/**
 * Controls movement of enemies without input.
 * Implements methods of {@link Controller}.
 */
public class EnemyController implements Controller {
    private final boolean STOP = false;

    private boolean moveUp;
    private boolean moveDown;
    private boolean moveLeft;
    private boolean moveRight;

    /**
     * Calculates which direction the character should move in.
     * Sets true value in boolean variables corresponding to the direction.
     *
     * @param targetPosition Position where the character should end up.
     * @param currentPosition Position where the character is located.
     */
    public void toTarget(Position targetPosition, Position currentPosition) {
        double deltaX = targetPosition.getX() - currentPosition.getX();
        double deltaY = targetPosition.getY() - currentPosition.getY();

        moveUp = deltaY < 0 && shouldMove(deltaY);
        moveDown = deltaY > 0 && shouldMove(deltaY);
        moveLeft = deltaX < 0 && shouldMove(deltaX);
        moveRight = deltaX > 0 && shouldMove(deltaX);
    }

    /**
     * @param delta Difference between target and current coordinates.
     * @return true, if the character should move,
     *         false, if the character should not move.
     */
    private boolean shouldMove(double delta) {
        return Math.abs(delta) > Position.NON_MOVEMENT_RANGE;
    }

    /**
     * Sets boolean variables indicating
     * the movement direction on STOP value.
     */
    public void stop() {
        moveUp = STOP;
        moveDown = STOP;
        moveLeft = STOP;
        moveRight = STOP;
    }

    @Override
    public boolean isUp() {
        return moveUp;
    }

    @Override
    public boolean isDown() {
        return moveDown;
    }

    @Override
    public boolean isLeft() {
        return moveLeft;
    }

    @Override
    public boolean isRight() {
        return moveRight;
    }

    @Override
    public boolean isShoot() {
        return false;
    }
}
