package cz.cvut.fel.pjv.engine.view.ui.base.clickable;

import cz.cvut.fel.pjv.engine.model.game.state.State;
import cz.cvut.fel.pjv.engine.model.utils.Position;
import cz.cvut.fel.pjv.engine.view.ui.base.UIComponent;

import java.awt.*;

/**
 * Creates clickable UI component.
 */
public abstract class Clickable extends UIComponent {
    protected boolean hasFocus;
    protected boolean isPressed;

    @Override
    public void update(State state) {
        Position mousePosition = state.getInputHandler().getMousePosition();
        hasFocus = getBounds().contains(mousePosition.parseIntX(), mousePosition.parseIntY());
        isPressed = hasFocus && state.getInputHandler().isMousePressed();
        if(hasFocus && state.getInputHandler().isMouseClicked())
            onClick(state);
    }

    /**
     * Executes action on click.
     *
     * @param state State of the game.
     */
    protected abstract void onClick(State state);

    /**
     * @return Bounds of the clickable component.
     */
    private Rectangle getBounds() {
        return new Rectangle(
                absolutePosition.parseIntX(),
                absolutePosition.parseIntY(),
                size.getWidth(),
                size.getHeight()
        );
    }
}