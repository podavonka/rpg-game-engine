package cz.cvut.fel.pjv.engine.view;

import cz.cvut.fel.pjv.engine.model.object.GameObject;
import cz.cvut.fel.pjv.engine.model.game.state.State;
import cz.cvut.fel.pjv.engine.model.utils.Position;
import cz.cvut.fel.pjv.engine.model.utils.Size;

import java.awt.*;
import java.util.Optional;

/**
 * Sets object to be focused on.
 * Places it in the middle of canvas.
 * Follows that object wherever it moves.
 */
public class Camera {
    private Position position;
    private Size windowSize;
    private Optional<GameObject> focusedObject;
    private Rectangle viewBounds;

    public Camera(Size windowSize) {
        this.position = new Position(0, 0);
        this.windowSize = windowSize;
        this.focusedObject = Optional.empty();
        calculateViewBounds();
    }

    /**
     * Calculates bounds of the visible part of the map that shows in the window.
     *
     * If the focused object is located near the border of the map,
     * it will not be displayed in the center of the window.
     */
    private void calculateViewBounds() {
        viewBounds = new Rectangle(position.parseIntX(), position.parseIntY(), windowSize.getWidth(), windowSize.getHeight());
    }

    /**
     * Sets object that will be in focus of game camera.
     *
     * @param object Object to be focused on.
     */
    public void focusOn(GameObject object) {
        this.focusedObject = Optional.of(object);
    }

    /**
     * Checks if there is an object in focus of game camera.
     * If there is, places that object in the middle of canvas.
     *
     * @param state Is used to prevent camera from going off the map.
     * @see Camera#stayWithinBorders(State)
     */
    public void update(State state) {
        if(focusedObject.isPresent()) {
            Position objectPosition = focusedObject.get().getPosition();

            this.position.setX(objectPosition.getX() - windowSize.getWidth() / 2);
            this.position.setY(objectPosition.getY() - windowSize.getHeight() / 2);

            stayWithinBorders(state);
            calculateViewBounds();
        }
    }

    /**
     * Camera does not follow focused object when it is at the border of game map.
     * So the focused object is not displayed at the center of the window.
     *
     * @param state Is used to get tile's width and height.
     */
    private void stayWithinBorders(State state) {
        if(position.getX() < 0)
            position.setX(0);
        if(position.getY() < 0)
            position.setY(0);
        if(position.getX() + windowSize.getWidth() > state.getTileManager().getWidth())
            position.setX(state.getTileManager().getWidth() - windowSize.getWidth());
        if(position.getY() + windowSize.getHeight() > state.getTileManager().getHeight())
            position.setY(state.getTileManager().getHeight() - windowSize.getHeight());
    }

    /**
     * Checks whether the object is in view of camera.
     *
     * @param gameObject Object that we plan to render.
     * @return true, if the object is visible,
     *         false, if the object is not visible.
     */
    public boolean isInView(GameObject gameObject) {
        return viewBounds.intersects(
                gameObject.getPosition().parseIntX(),
                gameObject.getPosition().parseIntY(),
                gameObject.getSize().getWidth(),
                gameObject.getSize().getHeight());
    }

    /**
     * @return Size of camera that is equals to window size.
     */
    public Size getSize() {
        return windowSize;
    }

    /**
     * @return Relative position of the focused object
     *         inside the window.
     */
    public Position getPosition() {
        return position;
    }
}
