package cz.cvut.fel.pjv.engine.view.animation;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Contains sprite sheets for action animation.
 */
public class SpriteSet {
    private Map<String, Image> animationSheets;

    public SpriteSet() {
        this.animationSheets = new HashMap<>();
    }

    /**
     * @param name Name of action.
     *             For example, 'stand' or 'walk'.
     * @param animationSheet Sprite sheet to be put in set.
     */
    public void addSheet(String name, Image animationSheet) {
        animationSheets.put(name, animationSheet);
    }

    /**
     * @param name Name of action.
     * @return Sprite sheet with received name.
     */
    public Image get(String name) {
        return animationSheets.get(name);
    }
}
