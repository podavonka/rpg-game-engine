package cz.cvut.fel.pjv.engine.view.ui.state;

import cz.cvut.fel.pjv.engine.model.game.state.GameState;
import cz.cvut.fel.pjv.engine.model.game.state.MenuState;
import cz.cvut.fel.pjv.engine.model.utils.Size;
import cz.cvut.fel.pjv.engine.view.ui.base.UIText;
import cz.cvut.fel.pjv.engine.view.ui.base.UIContainer;
import cz.cvut.fel.pjv.engine.view.ui.base.clickable.UIButton;
import cz.cvut.fel.pjv.engine.view.ui.layout.Alignment;

/**
 * Is shown when the game state is on pause.
 * Suggests to save game, start over, go to the main menu or quit the game.
 */
public class UIPause extends UIContainer {

    public UIPause(Size windowSize, GameState gameState) {
        super(windowSize);
        this.alignment = new Alignment(Alignment.Position.CENTER);
        addComponent(new UIText("PAUSED"));
        addComponent(new UIButton("MENU", (state) -> state.setNextState(new MenuState(gameState.getInputHandler(), windowSize))));
        addComponent(new UIButton("SAVE GAME", (state) -> gameState.saveGame()));
        addComponent(new UIButton("RESTART", (state) -> state.setNextState(new GameState(gameState.getInputHandler(), windowSize, state.isMultiplayer()))));
        addComponent(new UIButton("QUIT", (state) -> System.exit(0)));
    }
}
