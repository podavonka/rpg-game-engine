package cz.cvut.fel.pjv.engine.model.utils;

/**
 * Contains data about object's position on the map.
 */
public class Position {

    /**
     * The distance from the character
     * at which it will not start moving.
     */
    public static int NON_MOVEMENT_RANGE = 6;

    private double x;
    private double y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Makes copy if this position.
     *
     * @param position Position to be copied.
     * @return Copied position.
     */
    public static Position copyOf(Position position) {
        return new Position(position.getX(), position.getY());
    }

    /**
     * Calculates new object's position on the map after the movement is completed.
     *
     * @param motion Is used to get object's displacement vector.
     */
    public void makeMove(Motion motion) {
        Vector vector = motion.getVector();
        x += vector.getX();
        y += vector.getY();
    }

    /**
     * Applying motion to the vector.
     *
     * @param motion Motion to be applied.
     */
    public void apply(Motion motion) {
        Vector vector = motion.getVector();
        x += vector.getX();
        y += vector.getY();
    }

    /**
     * Calculates distance from this position to another.
     *
     * @param other Position that we count the distance to.
     * @return Distance between two positions.
     */
    public double distanceTo(Position other) {
        double deltaX = this.getX() - other.getX();
        double deltaY = this.getY() - other.getY();

        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    /**
     * Calculates new position adding coordinates.
     *
     * @param position Component to add to current position.
     */
    public void add(Position position) {
        x += position.getX();
        y += position.getY();
    }

    /**
     * Calculates new position subtracting coordinates.
     *
     * @param position Component to subtract from current position.
     */
    public void subtract(Position position) {
        x -= position.getX();
        y -= position.getY();
    }

    /**
     * Checks whether the requested position is in non-movement zone.
     *
     * @param position Position to be moved to.
     * @return true, if position is in range,
     *         false, if position is NOT in range.
     */
    public boolean isInRange(Position position) {
        return shouldStay(x - position.getX()) && shouldStay(y - position.getY());
    }

    /**
     * Checks whether the character should stay.
     *
     * @param delta Change between the current coordinate and new one.
     * @return true, if the character should stay,
     *         false, if the character should NOT stay.
     */
    private boolean shouldStay(double delta) {
        return Math.abs(delta) < Position.NON_MOVEMENT_RANGE;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /**
     * Rounds the value of the X coordinate to integer.
     */
    public int parseIntX() {
        return (int) Math.round(x);
    }

    /**
     * Rounds the value of the Y coordinate to integer.
     */
    public int parseIntY() {
        return (int) Math.round(y);
    }
}
