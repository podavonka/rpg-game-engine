package cz.cvut.fel.pjv.engine.model.utils;

/**
 * Contains size of any object, which is
 * represented by its width and height.
 */
public class Size {
    private int width;
    private int height;

    public Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
