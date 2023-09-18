package cz.cvut.fel.pjv.engine.view.ui.state;

import cz.cvut.fel.pjv.engine.model.game.state.GameState;
import cz.cvut.fel.pjv.engine.model.utils.Size;
import cz.cvut.fel.pjv.engine.view.ui.base.UIText;
import cz.cvut.fel.pjv.engine.view.ui.base.UIContainer;
import cz.cvut.fel.pjv.engine.view.ui.base.clickable.UIButton;
import cz.cvut.fel.pjv.engine.view.ui.layout.Alignment;

/**
 * Represents the main menu of the game.
 * Suggests to start playing or quit the game.
 */
public class UIMenu extends UIContainer {
    public UIMenu(Size windowSize) {
        super(windowSize);
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);
        addComponent(new UIText("MY WESTERN"));
        addComponent(new UIButton("PLAY", (state) -> state.setNextState(new GameState(state.getInputHandler(), windowSize, false))));
        addComponent(new UIButton("ONLINE", (state) -> state.setNextState(new GameState(state.getInputHandler(), windowSize, true))));
        addComponent(new UIButton("QUIT", (state) -> System.exit(0)));
    }
}
