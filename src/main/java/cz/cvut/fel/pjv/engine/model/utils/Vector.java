package cz.cvut.fel.pjv.engine.model.utils;

/**
 * Utility for {@link Motion}.
 * Helps to process movement as a vector.
 */
public class Vector {
    private double x;
    private double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Calculates vector's length.
     */
    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Normalizes vector so that objects moves at equal distances
     * in all directions including diagonally.
     */
    public void normalize() {
        double length = length();
        x = (x == 0) ? 0 : (x / length);
        y = (y == 0) ? 0 : (y / length);
    }

    /**
     * @param start Start position to calculate direction vector.
     * @param end End position to calculate direction vector.
     * @return Direction vector between start and stop positions.
     */
    public static Vector directionBetweenPositions(Position start, Position end) {
        Vector direction = new Vector(start.getX() - end.getX(), start.getY() - end.getY());
        direction.normalize();
        return direction;
    }

    /**
     * Calculates scalar product of two vectors.
     *
     * @param v1 First vector to calculate.
     * @param v2 Second vector to calculate.
     * @return Dot product of two vectors.
     */
    public static double dotProduct(Vector v1, Vector v2) {
        return v1.getX() * v2.getX() + v1.getY() * v2.getY();
    }

    /**
     * Increases length of vector to speed up the movement.
     *
     * @param speed Vector multiplier.
     */
    public void multiply(double speed) {
        x *= speed;
        y *= speed;
    }

    /**
     * Makes copy of vector.
     *
     * @param vector Vector to be copied.
     * @return Copied vector.
     */
    public static Vector copyOf(Vector vector) {
        return new Vector(vector.getX(), vector.getY());
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
