package cz.cvut.fel.pjv.engine.view.ui.layout;

/**
 * Represents the content's offset from
 * the container's borders.
 */
public class Spacing {
    private int top;
    private int bottom;
    private int left;
    private int right;

    public Spacing(int spaceLength) {
        this(spaceLength, spaceLength);
    }

    public Spacing(int horizontal, int vertical) {
        this(vertical, vertical, horizontal, horizontal);
    }

    public Spacing(int top, int bottom, int left, int right) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    /**
     * @return Offset from the upper border.
     */
    public int getTop() {
        return top;
    }

    /**
     * @return Offset from the lower border.
     */
    public int getBottom() {
        return bottom;
    }

    /**
     * @return Offset from the left border.
     */
    public int getLeft() {
        return left;
    }

    /**
     * @return Offset from the right border.
     */
    public int getRight() {
        return right;
    }

    /**
     * @return Sum of top and bottom offset.
     */
    public int getHorizontal() {
        return left + right;
    }

    /**
     * @return Sum of left and right offset.
     */
    public int getVertical() {
        return top + bottom;
    }
}
