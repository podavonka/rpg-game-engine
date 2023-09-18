package cz.cvut.fel.pjv.engine.model.object;

import cz.cvut.fel.pjv.engine.model.game.state.State;
import cz.cvut.fel.pjv.engine.model.object.GameObject;
import cz.cvut.fel.pjv.engine.model.utils.CollisionBox;
import cz.cvut.fel.pjv.engine.model.utils.Size;
import cz.cvut.fel.pjv.engine.model.utils.Position;
import cz.cvut.fel.pjv.engine.model.utils.ImageUtils;

import java.awt.*;

/**
 * Heart spawns at a random map point on sand.
 * By stepping on the heart the player can get extra health points.
 */
public class Heart extends GameObject {
    private Image sprite;
    private boolean isUsed;

    public Heart(){
        isUsed = false;
        size = new Size(16,16);
        renderOffset = new Position(size.getWidth() / 2, size.getHeight());
        collisionBoxOffset = new Position(size.getWidth() / 2, size.getHeight());
        sprite = ImageUtils.loadImage("/sprites/units/items/heart.png");
    }

    /**
     * Sets if the player has already stepped on the heart.
     */
    public void setUsed(boolean used) {
        isUsed = used;
    }

    /**
     * Checks whether the player has already stepped on the bag.
     */
    public boolean isUsed(){
        return isUsed;
    }

    @Override
    public void update(State state) { }

    @Override
    public boolean collidesWith(CollisionBox otherBox) {
        return getCollisionBox().collidesWith(otherBox);
    }

    @Override
    public CollisionBox getCollisionBox() {
        Position position = getPosition();
        position.subtract(collisionBoxOffset);
        return CollisionBox.of(position, getSize());
    }

    @Override
    public Image getSprite() {
        return sprite;
    }
}
