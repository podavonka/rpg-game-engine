package cz.cvut.fel.pjv.engine.model.object.entity;

import cz.cvut.fel.pjv.engine.model.game.Game;
import cz.cvut.fel.pjv.engine.model.object.TargetZone;
import cz.cvut.fel.pjv.engine.model.utils.Size;
import cz.cvut.fel.pjv.engine.controller.Controller;
import cz.cvut.fel.pjv.engine.view.animation.AnimationManager;
import cz.cvut.fel.pjv.engine.view.animation.SpriteStorage;

/**
 * Shadow enemy type.
 */
public class ShadowEnemy extends Enemy {

    public ShadowEnemy(Controller controller, SpriteStorage spriteStorage, TargetZone targetZone) {
        super(controller, spriteStorage, 3, targetZone, 1.5);
        this.size = new Size(48,44);
        this.animationManager = new AnimationManager(this, spriteStorage.getUnit("steve shadow"));
        this.targetRange = 3 * Game.TILE_SIZE;
    }
}
