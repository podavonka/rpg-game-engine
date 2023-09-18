package cz.cvut.fel.pjv.engine.model.object.entity;

import cz.cvut.fel.pjv.engine.model.game.Game;
import cz.cvut.fel.pjv.engine.model.object.TargetZone;
import cz.cvut.fel.pjv.engine.model.utils.Size;
import cz.cvut.fel.pjv.engine.controller.Controller;
import cz.cvut.fel.pjv.engine.view.animation.AnimationManager;
import cz.cvut.fel.pjv.engine.view.animation.SpriteStorage;

/**
 * Cactus enemy type.
 */
public class CactusEnemy extends Enemy {

    public CactusEnemy(Controller controller, SpriteStorage spriteStorage, TargetZone targetZone) {
        super(controller, spriteStorage, 1, targetZone, 1);
        this.size = new Size(40,40);
        this.animationManager = new AnimationManager(this, spriteStorage.getUnit("cactus"));
        this.targetRange = 5 * Game.TILE_SIZE;
    }
}
