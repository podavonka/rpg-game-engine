package cz.cvut.fel.pjv.engine.model.utils;

import cz.cvut.fel.pjv.engine.controller.Controller;

/**
 * Sets the direction and speed of movement
 * using {@link Vector}.
 */
public class Motion {
    private Vector vector;
    private double speed;

    public Motion(double speed) {
        this.speed = speed;
        this.vector = new Vector(0, 0);
    }

    /**
     * Analyzes input and calculates the vector of the next object's movement.
     * Accelerates the movement by applying the speed value.
     *
     * @param controller Input from user.
     */
    public void update(Controller controller) {
        int deltaX = 0;
        int deltaY = 0;

        if (controller != null){
            if(controller.isUp())
                deltaY--;
            if(controller.isDown())
                deltaY++;
            if(controller.isLeft())
                deltaX--;
            if(controller.isRight())
                deltaX++;
        }

        vector = new Vector(deltaX, deltaY);
        vector.normalize();
        vector.multiply(speed);
    }

    /**
     * Stops motion and sets vector's coordinates to 0.
     *
     * @param stopX Should the x-axis movement be stopped.
     * @param stopY Should the y-axis movement be stopped.
     */
    public void stop(boolean stopX, boolean stopY) {
        vector = new Vector(
                stopX ? 0 : vector.getX(),
                stopY ? 0 : vector.getY()
        );
    }

    /**
     * Checks whether the entity is moving.
     *
     * @return true, if the entity is moving,
     *         false if the entity is standing.
     */
    public boolean isMoving() {
        return vector.length() > 0;
    }

    /**
     * @return Movement vector.
     */
    public Vector getVector() {
        return vector;
    }

    /**
     * Gets normalized direction.
     */
    public Vector getDirection() {
        Vector direction = Vector.copyOf(vector);
        direction.normalize();
        return direction;
    }
}
