package cz.cvut.fel.pjv.engine.view.ui.state;

import cz.cvut.fel.pjv.engine.model.game.state.GameState;
import cz.cvut.fel.pjv.engine.model.game.state.MenuState;
import cz.cvut.fel.pjv.engine.model.utils.Size;
import cz.cvut.fel.pjv.engine.view.ui.base.UIText;
import cz.cvut.fel.pjv.engine.view.ui.base.UIContainer;
import cz.cvut.fel.pjv.engine.view.ui.base.clickable.UIButton;
import cz.cvut.fel.pjv.engine.view.ui.layout.Alignment;

import java.awt.*;

/**
 * Game over menu is shown at the end of the game.
 * Suggests to start over, go to the main menu or exit the game.
 */
public class UIGameOver extends UIContainer {

    public UIGameOver(Size windowSize, GameState gameState, String result) {
        super(windowSize);
        this.alignment = new Alignment(Alignment.Position.CENTER);
        setBackgroundColor(new Color(135, 112, 196, 148));
        addComponent(new UIText(result));
        addComponent(new UIButton("RESTART", (state) -> state.setNextState(new GameState(gameState.getInputHandler(), windowSize, state.isMultiplayer()))));
        addComponent(new UIButton("MENU", (state) -> state.setNextState(new MenuState(gameState.getInputHandler(), windowSize))));
        addComponent(new UIButton("QUIT", (state) -> System.exit(0)));
    }
}
