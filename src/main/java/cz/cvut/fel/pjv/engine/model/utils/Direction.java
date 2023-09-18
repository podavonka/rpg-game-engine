package cz.cvut.fel.pjv.engine.model.utils;

/**
 * Contains a list of possible entity movements' directions.
 *
 * The following abbreviations are used:
 * 'S' - South, 'W' - West,
 * 'N' - North, 'E' - East.
 */
public enum Direction {
    S(0),
    SW(1),
    W(2),
    NW(3),
    N(4),
    NE(5),
    E(6),
    SE(7);

    private int animationRow;

    Direction(int animationRow) {
        this.animationRow = animationRow;
    }

    /**
     * Determines direction of entity's movement.
     *
     * @param motion Current entity's motion.
     * @return Movement direction.
     */
    public static Direction fromMotion(Motion motion){
        double x = motion.getVector().getX();
        double y = motion.getVector().getY();

        if (x == 0 && y > 0) return S;
        if (x < 0 && y > 0) return SW;
        if (x < 0 && y == 0) return W;
        if (x < 0 && y < 0) return NW;
        if (x == 0 && y < 0) return N;
        if (x > 0 && y < 0) return NE;
        if (x > 0 && y == 0) return E;
        if (x > 0 && y > 0) return SE;

        return S;
    }

    /**
     * @return Number of row that contains necessary
     *         animation in the sprite sheet.
     */
    public int getAnimationRow() {
        return animationRow;
    }
}
