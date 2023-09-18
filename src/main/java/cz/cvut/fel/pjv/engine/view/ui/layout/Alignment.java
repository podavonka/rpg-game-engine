package cz.cvut.fel.pjv.engine.view.ui.layout;

/**
 * Represents position of the component in the window.
 * Places the component at start, center or end of the coordinates axis.
 */
public class Alignment {

    /**
     * Represents position on the coordinate axis.
     */
    public enum Position {
        START, CENTER, END
    }

    private final Position horizontal;
    private final Position vertical;

    public Alignment(Position position) {
        this(position, position);
    }

    public Alignment(Position horizontal, Position vertical) {
        this.vertical = vertical;
        this.horizontal = horizontal;
    }

    /**
     * @return Vertical position.
     */
    public Position getVertical() {
        return vertical;
    }

    /**
     * @return Horizontal position.
     */
    public Position getHorizontal() {
        return horizontal;
    }
}