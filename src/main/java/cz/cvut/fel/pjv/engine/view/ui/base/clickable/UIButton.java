package cz.cvut.fel.pjv.engine.view.ui.base.clickable;

import cz.cvut.fel.pjv.engine.model.game.state.State;
import cz.cvut.fel.pjv.engine.model.utils.Size;
import cz.cvut.fel.pjv.engine.view.ui.base.UIText;
import cz.cvut.fel.pjv.engine.view.ui.base.UIContainer;

import java.awt.*;

/**
 * Creates clickable UI component represented by button.
 */
public class UIButton extends Clickable {
    private UIContainer container;
    private UIText label;
    private ClickAction clickAction;

    public UIButton(String label, ClickAction clickAction) {
        this.label = new UIText(label);
        this.clickAction = clickAction;

        container = new UIContainer(new Size(0, 0));
        container.setFixedSize(new Size(150, 50));
        container.addComponent(this.label);
    }

    @Override
    public void update(State state) {
        super.update(state);
        container.update(state);
        size = container.getSize();

        Color color = new Color(119, 109, 163);
        if(hasFocus)
            color = new Color(217, 187, 107);;
        if(isPressed)
            color = new Color(108, 86, 49);;
        container.setBackgroundColor(color);
    }

    @Override
    protected void onClick(State state) {
        clickAction.execute(state);
    }

    @Override
    public Image getSprite() {
        return container.getSprite();
    }
}
