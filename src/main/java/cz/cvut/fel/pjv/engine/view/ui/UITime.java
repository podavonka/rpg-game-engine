package cz.cvut.fel.pjv.engine.view.ui;

import cz.cvut.fel.pjv.engine.model.game.state.State;
import cz.cvut.fel.pjv.engine.model.utils.Size;
import cz.cvut.fel.pjv.engine.view.ui.base.UIContainer;
import cz.cvut.fel.pjv.engine.view.ui.base.UIText;
import cz.cvut.fel.pjv.engine.view.ui.layout.Alignment;

/**
 * Sets time as a user interface component.
 */
public class UITime extends UIContainer {
    private UIText time;

    public UITime(Size windowSize) {
        super(windowSize);
        this.alignment = new Alignment(Alignment.Position.END, Alignment.Position.START);
        this.time = new UIText("");
        addComponent(time);
    }

    @Override
    public void update(State state) {
        super.update(state);
        time.setText(state.getTime().getFormattedTime());
    }
}
