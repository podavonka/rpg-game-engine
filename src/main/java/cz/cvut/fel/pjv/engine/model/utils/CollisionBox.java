package cz.cvut.fel.pjv.engine.model.utils;

import java.awt.*;

/**
 * Collision box for game object.
 */
public class CollisionBox {
    private Rectangle bounds;

    public CollisionBox(Rectangle bounds) {
        this.bounds = bounds;
    }

    /**
     * Creates collision box of curtain position and size.
     *
     * @param position Position of collision box's upper-left corner.
     * @param size Size of new collision box.
     */
    public static CollisionBox of(Position position, Size size) {
        return new CollisionBox(
                new Rectangle(
                        position.parseIntX(),
                        position.parseIntY(),
                        size.getWidth(),
                        size.getHeight()
                )
        );
    }

    /**
     * Determines whether this collision box intersects
     * with another collision box.
     *
     * @param other Collision box of another game object.
     * @return true, if collision boxes intersect.
     *         false, if collision boxes DO NOT intersect.
     */
    public boolean collidesWith(CollisionBox other) {
        return bounds.intersects(other.getBounds());
    }

    /**
     * @return Bounds of the collision box.
     */
    public Rectangle getBounds() {
        return bounds;
    }
}
