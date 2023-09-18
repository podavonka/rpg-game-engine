package cz.cvut.fel.pjv.engine.view.animation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.cvut.fel.pjv.engine.model.object.entity.Entity;
import cz.cvut.fel.pjv.engine.model.utils.Direction;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Animates movements and actions using sprites from resources
 * via {@link SpriteStorage} and {@link SpriteSet}.
 */
public class AnimationManager {
    private SpriteSet spriteSet;
    private BufferedImage currentAnimationSheet;
    private String currentAnimationName;
    private int updatesPerFrame;
    private int currentFrameTime;
    private int frameIndex;
    private int directionIndex;
    private int spriteWidth;
    private int spriteHeight;


    public AnimationManager(Entity entity, SpriteSet spriteSet) {
        this.spriteSet = spriteSet;
        this.updatesPerFrame = 10 ;
        this.currentFrameTime = 0;
        this.frameIndex = 0;
        this.directionIndex = 0;
        this.spriteWidth = entity.getSize().getWidth();
        this.spriteHeight = entity.getSize().getHeight();
        currentAnimationName = "";
        playAnimation("stand");
    }

    /**
     * @return Animation frame depending on direction of object's movement.
     */
    @JsonIgnore
    public Image getSprite() {
        return currentAnimationSheet.getSubimage(
                frameIndex * spriteWidth,
                directionIndex * spriteHeight,
                spriteWidth,
                spriteHeight
        );
    }

    /**
     * Updates frames depending on direction of object's movement.
     *
     * @param direction Is used to get number of row
     *                  that contains necessary animation.
     */
    public void update(Direction direction) {
        currentFrameTime++;
        directionIndex = direction.getAnimationRow();

        if(currentFrameTime >= updatesPerFrame) {
            currentFrameTime = 0;
            frameIndex++;

            if(frameIndex >= currentAnimationSheet.getWidth() / spriteWidth)
                frameIndex = 0;
        }
    }

    /**
     * Plays animation of certain action.
     *
     * @param name Name of action.
     *             For example, 'stand' or 'walk'.
     */
    public void playAnimation(String name) {
        if(!name.equals(currentAnimationName)) {
            this.currentAnimationSheet = (BufferedImage) spriteSet.get(name);
            currentAnimationName = name;
            frameIndex = 0;
        }
    }

    public String getCurrentAnimationName() {
        return currentAnimationName;
    }
}
