package cz.cvut.fel.pjv.engine.view.map;

import cz.cvut.fel.pjv.engine.view.animation.SpriteStorage;

import java.awt.*;

/**
 * Contains individual tiles with
 * specific names to create game map.
 */
public class Tile {
    private Image sprite;
    private String spriteName;
    private boolean walkable;

    public Tile(SpriteStorage spriteStorage, String spriteName, boolean walkable) {
        this.sprite = spriteStorage.getTile(spriteName);
        this.spriteName = spriteName;
        this.walkable = walkable;
    }

    /**
     * Checks whether characters can walk on this tile.
     *
     * @return true, if character can walk on this tile,
     *         false, if character can NOT walk on this tile.
     */
    public boolean isWalkable() {
        return walkable;
    }

    /**
     * @return Name of the sprite image.
     */
    public String getSpriteName() {
        return spriteName;
    }

    /**
     * @return Image of this tile.
     */
    public Image getSprite() {
        return sprite;
    }
}
