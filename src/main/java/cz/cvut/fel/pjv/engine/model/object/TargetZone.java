package cz.cvut.fel.pjv.engine.model.object;

import cz.cvut.fel.pjv.engine.model.game.state.State;
import cz.cvut.fel.pjv.engine.model.object.GameObject;
import cz.cvut.fel.pjv.engine.model.utils.CollisionBox;
import cz.cvut.fel.pjv.engine.model.utils.Position;
import cz.cvut.fel.pjv.engine.model.utils.Size;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The oval area under the target character
 * that the shooter is aiming at.
 */
public class TargetZone extends GameObject {
    private Color color;
    private BufferedImage sprite;

    public TargetZone() {
        color = new Color(203, 20, 20, 176);
        size = new Size(24,22);
        renderOrder = 4;
        renderOffset = new Position(size.getWidth() / 2, size.getHeight());
        collisionBoxOffset = new Position(size.getWidth() / 2, size.getHeight());
        setSprite();
    }

    /**
     * Creates oval to mark the target.
     */
    private void setSprite() {
        sprite = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = sprite.createGraphics();

        graphics.setColor(color);
        graphics.fillOval(0, 0, size.getWidth(), size.getHeight());

        graphics.dispose();
    }

    @Override
    public void update(State state) { }

    @Override
    public boolean collidesWith(CollisionBox otherBox) {
        return false;
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
