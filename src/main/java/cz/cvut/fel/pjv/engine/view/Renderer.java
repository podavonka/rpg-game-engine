package cz.cvut.fel.pjv.engine.view;

import cz.cvut.fel.pjv.engine.model.game.Game;
import cz.cvut.fel.pjv.engine.view.map.Tile;
import cz.cvut.fel.pjv.engine.model.game.state.State;

import java.awt.*;

/**
 * Renders all game objects and background.
 */
public class Renderer {

    public void render(State state, Graphics graphics) {
        renderMap(state, graphics);
        renderGameObjects(state, graphics);
        renderUI(state,graphics);
    }

    /**
     * Draws the game map using array of {@link Tile}.
     *
     * @param state Contains tiles for creating game map and camera.
     *              Is used for correct placement of tiles relative to the focused object.
     * @param graphics Places images of tiles at the designated position.
     */
    private void renderMap(State state, Graphics graphics) {
        Tile[][] tiles = state.getTileManager().getTiles();
        Camera camera = state.getCamera();
        for(int x = 0; x < tiles.length; x++)
            for(int y = 0; y < tiles[0].length; y++)
                graphics.drawImage(
                        tiles[x][y].getSprite(),
                        x * Game.TILE_SIZE - camera.getPosition().parseIntX(),
                        y * Game.TILE_SIZE - camera.getPosition().parseIntY(),
                        null
                );
    }

    /**
     * Draws each game object created in game state that is in view of camera.
     *
     * @param state Contains list of game objects and game camera.
     *              Is used for correct placement of objects relative to the focused object.
     * @param graphics Places images of objects at the designated position.
     */
    private void renderGameObjects(State state, Graphics graphics) {
        Camera camera = state.getCamera();
        state.getGameObjects().stream()
                .filter(gameObject -> camera.isInView(gameObject))
                .forEach(gameObject -> graphics.drawImage(
                        gameObject.getSprite(),
                        gameObject.getRenderPosition(camera).parseIntX(),
                        gameObject.getRenderPosition(camera).parseIntY(),
                        null
                ));
    }

    /**
     * Draws user interfaces components in the window.
     *
     * @param state Contains list of user interface containers.
     * @param graphics Places containers at the designated position.
     */
    private void renderUI(State state, Graphics graphics) {
        state.getUiContainers().forEach(uiContainer -> graphics.drawImage(
                uiContainer.getSprite(),
                uiContainer.getRelativePosition().parseIntX(),
                uiContainer.getRelativePosition().parseIntY(),
                null
        ));
    }
}
