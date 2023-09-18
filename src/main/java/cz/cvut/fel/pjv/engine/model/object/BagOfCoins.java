package cz.cvut.fel.pjv.engine.model.object;

import cz.cvut.fel.pjv.engine.model.game.state.State;
import cz.cvut.fel.pjv.engine.model.object.GameObject;
import cz.cvut.fel.pjv.engine.model.utils.CollisionBox;
import cz.cvut.fel.pjv.engine.model.utils.Size;
import cz.cvut.fel.pjv.engine.model.utils.Position;
import cz.cvut.fel.pjv.engine.model.utils.ImageUtils;

import java.awt.*;

/**
 * Bag of coins spawns at a random map point on sand.
 * By stepping on the bag the player can get extra coins.
 */
public class BagOfCoins extends GameObject {
    private Image sprite;
    private boolean isUsed;

    public BagOfCoins() {
        isUsed = false;
        size = new Size(16,16);
        renderOffset = new Position(size.getWidth() / 2, size.getHeight());
        collisionBoxOffset = new Position(size.getWidth() / 2, size.getHeight());
        sprite = ImageUtils.loadImage("/sprites/units/items/bagOfCoins.png");
    }

    /**
     * Sets if the player has already stepped on the bag.
     */
    public void setUsed(boolean used) {
        isUsed = used;
    }

    /**
     * Checks whether the player has already stepped on the bag.
     */
    public boolean isUsed() {
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
