package cz.cvut.fel.pjv.engine.view.map;

import cz.cvut.fel.pjv.engine.model.game.Game;
import cz.cvut.fel.pjv.engine.model.utils.CollisionBox;
import cz.cvut.fel.pjv.engine.model.utils.Position;
import cz.cvut.fel.pjv.engine.view.animation.SpriteStorage;
import cz.cvut.fel.pjv.engine.model.utils.Size;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains array of {@link Tile}.
 * Helps to generate, add and create tile maps.
 */
public class TileManager {
    private Tile[][] tiles;
    private MapEditor mapEditor;
    private int mapWidth;
    private int mapHeight;

    public TileManager(Size size, SpriteStorage spriteStorage) {
        tiles = new Tile[size.getWidth()][size.getHeight()];
        mapEditor = new MapEditor(size.getWidth(), size.getHeight(), spriteStorage);
        initializeTiles();
        mapWidth = tiles[0].length * Game.TILE_SIZE;
        mapHeight = tiles.length * Game.TILE_SIZE;
    }

    /**
     * Loads tiles from {@link MapEditor}.
     */
    private void initializeTiles() {
        tiles = mapEditor.getMap();
    }

    /**
     * Generates random positions until it is sand.
     *
     * @return Random position on sand.
     */
    public Position getRandomPosition() {
        double x = getRandomNumber(mapWidth);
        double y = getRandomNumber(mapHeight);
        while (tiles[(int)x / 16][(int)y / 16].getSpriteName() != "sand") {
            x = getRandomNumber(mapWidth);
            y = getRandomNumber(mapHeight);
        }
        return new Position(x, y);
    }

    /**
     * @param range Range in which random number should be.
     * @return Random number in certain range.
     */
    private double getRandomNumber(int range) {
        return Math.random() * range;
    }

    /**
     * Based on the ratio of the character's size and tile's size,
     * looks for unwanted collisions with unwalkable tiles.
     *
     * @param collisionBox Collision box of the character.
     * @return List of unwalkable collision boxes
     *         that the character colliding with.
     */
    public List<CollisionBox> getCollidingUnwalkableTileBoxes(CollisionBox collisionBox) {
        int gridX = (int) (collisionBox.getBounds().getX() / Game.TILE_SIZE);
        int gridY = (int) (collisionBox.getBounds().getY() / Game.TILE_SIZE);
        List<CollisionBox> collidingUnwalkableTileBoxes = new ArrayList<>();

        for(int x = gridX - 1; x < gridX + 2; x++)
            for(int y = gridY - 1; y < gridY + 3; y++)
                if(gridWithinBounds(x, y) && !getTile(x, y).isWalkable()) {
                    CollisionBox gridCollisionBox = getGridCollisionBox(x, y);
                    if(collisionBox.collidesWith(gridCollisionBox))
                        collidingUnwalkableTileBoxes.add(gridCollisionBox);
                }

        return collidingUnwalkableTileBoxes;
    }

    /**
     *  Checks whether the tile is within map's bounds.
     *
     * @param gridX Tile's X coordinate of the upper-left corner.
     * @param gridY Tile's Y coordinate of the upper-left corner.
     * @return true, if the tile is within bounds,
     *         false, if the tile is NOT within bounds.
     */
    public boolean gridWithinBounds(int gridX, int gridY) {
        return gridX >= 0 && gridX < tiles.length
                && gridY >= 0 && gridY < tiles[0].length;
    }

    /**
     * Creates collision box for tile with certain coordinates.
     *
     * @param x Tile's X coordinate of the upper-left corner.
     * @param y Tile's Y coordinate of the upper-left corner.
     * @return Collision box of the tile.
     */
    private CollisionBox getGridCollisionBox(int x, int y) {
        return new CollisionBox(new Rectangle(x * Game.TILE_SIZE, y * Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE));
    }

    /**
     * Looks for tile with required coordinates.
     *
     * @param x Tile's X coordinate of the upper-left corner.
     * @param y Tile's Y coordinate of the upper-left corner.
     * @return Tile with required coordinates.
     */
    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    /**
     * @return Tile map with coordinates as IDs.
     */
    public Tile[][] getTiles() {
        return tiles;
    }

    /**
     * @return Map width.
     */
    public int getWidth() {
        return mapWidth;
    }

    /**
     * @return Map height.
     */
    public int getHeight() {
        return mapHeight;
    }
}
