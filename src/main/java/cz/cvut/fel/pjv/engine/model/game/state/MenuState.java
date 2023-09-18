package cz.cvut.fel.pjv.engine.model.game.state;

import cz.cvut.fel.pjv.engine.controller.InputHandler;
import cz.cvut.fel.pjv.engine.model.utils.Size;
import cz.cvut.fel.pjv.engine.view.map.TileManager;
import cz.cvut.fel.pjv.engine.view.ui.state.UIMenu;

/**
 * Menu state of the game.
 */
public class MenuState extends State {

    public MenuState(InputHandler inputHandler, Size windowSize) {
        super(inputHandler, windowSize);
        tileManager = new TileManager(new Size(80, 80), spriteStorage);
        uiContainers.add(new UIMenu(windowSize));
    }
}