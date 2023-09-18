package cz.cvut.fel.pjv.engine.view.ui;

import cz.cvut.fel.pjv.engine.model.object.entity.Player;
import cz.cvut.fel.pjv.engine.model.game.state.GameState;
import cz.cvut.fel.pjv.engine.model.utils.Size;
import cz.cvut.fel.pjv.engine.view.ui.base.UIContainer;
import cz.cvut.fel.pjv.engine.view.ui.base.UIText;
import cz.cvut.fel.pjv.engine.view.ui.base.clickable.UIButton;
import cz.cvut.fel.pjv.engine.view.ui.layout.Alignment;

import java.util.logging.Logger;

/**
 * Represents 'Shop' button in the game state.
 * Shows shop menu by pressing appropriate button.
 * Suggests to buy health points for coins.
 */
public class UIShop extends UIContainer {
    private Player player;
    private boolean isShopping = false;

    public UIShop(Size windowSize, GameState gameState) {
        super(windowSize);
        this.player = gameState.getPlayer();
        this.alignment = new Alignment(Alignment.Position.END);

        UIContainer shopContainer = new UIContainer(windowSize);
        setContainer(shopContainer);
        addComponent(new UIButton("SHOP", (state) ->
        {
            if (!isShopping && !gameState.isPaused()) {
                isShopping = true;
                state.getUiContainers().add(shopContainer);
                Logger.getLogger(this.getClass().getName()).info("Shop is open!");
            } else {
                isShopping = false;
                gameState.deleteUiContainer(shopContainer);
                Logger.getLogger(this.getClass().getName()).info("Shop is closed!");
            }
        }));
    }

    /**
     * Sets shop container with suggestions.
     *
     * @param shopContainer Container to be set.
     */
    private void setContainer(UIContainer shopContainer) {
        shopContainer.setAlignment(new Alignment(Alignment.Position.CENTER));
        shopContainer.addComponent(new UIText("LET'S SHOP"));
        shopContainer.addComponent(new UIButton("BUY HEALTH", (healthState) -> {
            buyHealthPoints(player);
        }));
    }

    /**
     * Sells 3 health points for 100 coins.
     *
     * @param player Buyer of health points.
     */
    private void buyHealthPoints(Player player) {
        if (player.getCoins().isEnough(100)) {
            player.increaseHealth(3);
            player.getCoins().spendCoins(100);
            Logger.getLogger(this.getClass().getName()).info("Player bought health!");
        } else
            Logger.getLogger(this.getClass().getName()).info("Player does not have enough money!");
    }
}
