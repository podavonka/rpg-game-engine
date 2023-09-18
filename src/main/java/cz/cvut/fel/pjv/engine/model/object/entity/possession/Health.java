package cz.cvut.fel.pjv.engine.model.object.entity.possession;

/**
 * Every entity has health, which means that it is alive.
 */
public class Health {
    private int healthPoints;
    private boolean isAlive;

    public Health(int healthPoints) {
        this.healthPoints = healthPoints;
        this.isAlive = true;
    }

    /**
     * Sets whether the character is alive.
     *
     * @param alive Value to be set.
     */
    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    /**
     * Sets number of health points.
     *
     * @param healthPoints Number of points to be set.
     */
    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    /**
     * @return true, if the character is alive,
     *         false, if the character is NOT alive.
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * @return Number of health points.
     */
    public int getHealthPoints() {
        return healthPoints;
    }
}
