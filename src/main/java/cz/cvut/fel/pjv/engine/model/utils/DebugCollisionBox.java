package cz.cvut.fel.pjv.engine.model.utils;

import cz.cvut.fel.pjv.engine.model.game.state.State;
import cz.cvut.fel.pjv.engine.view.Camera;

import java.awt.*;

/**
 * Draws collision box for each object.
 */
public class DebugCollisionBox {

    /**
     * Renders collision box for all objects.
     *
     * @param state State of the game.
     * @param graphics Graphics to draw.
     */
    public void render(State state, Graphics graphics) {
        Camera camera = state.getCamera();
        state.getGameObjects().stream()
                .filter(gameObject -> camera.isInView(gameObject))
                .map(gameObject -> gameObject.getCollisionBox())
                .forEach(collisionBox -> drawCollisionBox(collisionBox, graphics, camera));
    }

    /**
     * Draws one collision box.
     */
    private void drawCollisionBox(CollisionBox collisionBox, Graphics graphics, Camera camera) {
        graphics.setColor(Color.red);
        graphics.drawRect(
                (int) collisionBox.getBounds().getX() - camera.getPosition().parseIntX(),
                (int) collisionBox.getBounds().getY() - camera.getPosition().parseIntY(),
                (int) collisionBox.getBounds().getWidth(),
                (int) collisionBox.getBounds().getHeight()
        );
    }
}
