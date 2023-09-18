package cz.cvut.fel.pjv.engine.model.object;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.cvut.fel.pjv.engine.model.game.state.State;
import cz.cvut.fel.pjv.engine.model.utils.CollisionBox;
import cz.cvut.fel.pjv.engine.model.utils.Position;
import cz.cvut.fel.pjv.engine.model.utils.Size;
import cz.cvut.fel.pjv.engine.view.Camera;

import java.awt.*;

/**
 * Describes any object in the game.
 * For example, entities.
 *
 * Each object at the beginning of the game has
 * a specific size and a specific position on the map,
 * its own collision box.
 */
public abstract class GameObject {
    @JsonIgnore
    protected GameObject parent;
    protected Size size;
    protected Position position;
    @JsonIgnore
    protected Position collisionBoxOffset;
    @JsonIgnore
    protected Position renderOffset;
    @JsonIgnore
    protected int renderOrder;

    public GameObject() {
        size = new Size(48,44);
        position = new Position(0,0);
        collisionBoxOffset = new Position(0, 0);
        renderOffset = new Position(0, 0);
        renderOrder = 5;
    }

    /**
     * Changes, or updates, position and values of this object.
     */
    public abstract void update(State state);

    /**
     * Deletes the object's parent.
     */
    public void clearParent() {
        parent = null;
    }

    /**
     * Assigns target zone to another game object that is its parent.
     */
    public void setParent(GameObject parent) {
        this.parent = parent;
    }

    /**
     * Sets position of game object on the map.
     * Position is presented by X and Y coordinates.
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * @param renderOrder Number of game object in the rendering queue.
     */
    public void setRenderOrder(int renderOrder) {
        this.renderOrder = renderOrder;
    }

    /**
     * @return Size of the game object.
     */
    public Size getSize() {
        return size;
    }

    /**
     * @return Position of the game object.
     *         For target zone - position of its parent.
     */
    public Position getPosition() {
        Position finalPosition = Position.copyOf(position);

        if (parent != null)
            finalPosition.add(parent.getPosition());

        return finalPosition;
    }

    /**
     * @return Image with current object's appearance
     *         until the next update.
     */
    public abstract Image getSprite();

    /**
     * @return true, if this object collides with other object
     *         false, if this object NOT collides with other object.
     */
    public abstract boolean collidesWith(CollisionBox otherBox);

    /**
     * @return Collision box of the game object.
     */
    public abstract CollisionBox getCollisionBox();

    /**
     * @param camera Camera that is focused on the certain game object.
     * @return Position of the game object in the visible part of the map.
     */
    public Position getRenderPosition(Camera camera){
        return new Position(
                getPosition().getX() - camera.getPosition().getX() - renderOffset.getX(),
                getPosition().getY() - camera.getPosition().getY() - renderOffset.getY());
    }

    /**
     * @return Number of game object in the rendering queue.
     */
    public int getRenderOrder() {
        return renderOrder;
    }
}
