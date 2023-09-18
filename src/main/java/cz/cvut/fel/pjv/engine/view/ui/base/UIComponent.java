package cz.cvut.fel.pjv.engine.view.ui.base;

import cz.cvut.fel.pjv.engine.model.game.state.State;
import cz.cvut.fel.pjv.engine.model.utils.Position;
import cz.cvut.fel.pjv.engine.model.utils.Size;
import cz.cvut.fel.pjv.engine.view.ui.layout.Spacing;

import java.awt.*;

/**
 * Basic user interface element.
 * Can exist independently or as a base for other elements.
 * For example, creates container and formatted text.
 *
 * Basic terms from CSS:
 * margin - outer spacing to the border,
 * padding - internal spacing from the border to the content.
 */
public abstract class UIComponent {
    // Position inside the component
    protected Position relativePosition;
    // Position inside the window.
    protected Position absolutePosition;

    protected Size size;
    protected Spacing margin;
    protected Spacing padding;

    public UIComponent() {
        // Sets parameters to default values.
        relativePosition = new Position(0, 0);
        absolutePosition = new Position(0, 0);
        size = new Size(1, 1);
        margin = new Spacing(1);
        padding = new Spacing(1);
    }

    /**
     * Updates the data inside the component.
     */
    public abstract void update(State state);

    public void setSize(Size size) {
        this.size = size;
    }

    public void setRelativePosition(Position relativePosition) {
        this.relativePosition = relativePosition;
    }

    public void setAbsolutePosition(Position absolutePosition) {
        this.absolutePosition = absolutePosition;
    }

    public void setMargin(Spacing margin) {
        this.margin = margin;
    }

    public void setPadding(Spacing padding) {
        this.padding = padding;
    }

    public Size getSize() {
        return size;
    }

    public Position getRelativePosition() {
        return relativePosition;
    }

    public Position getAbsolutePosition() {
        return absolutePosition;
    }

    public Spacing getMargin() {
        return margin;
    }

    public Spacing getPadding() {
        return padding;
    }

    public abstract Image getSprite();
}
