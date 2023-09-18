package cz.cvut.fel.pjv.engine.view.ui;

import cz.cvut.fel.pjv.engine.model.object.entity.Player;
import cz.cvut.fel.pjv.engine.model.game.state.State;
import cz.cvut.fel.pjv.engine.model.utils.Size;
import cz.cvut.fel.pjv.engine.view.ui.base.UIContainer;
import cz.cvut.fel.pjv.engine.view.ui.base.UIText;
import cz.cvut.fel.pjv.engine.view.ui.layout.Alignment;

import java.util.HashMap;
import java.util.Map;

/**
 * Shows on the screen statistics about
 * number of health points and coins.
 */
public class UIStatistics extends UIContainer {
    private Player player;
    private UIText health;
    private UIText coins;
    private Map<String, String> statistics;

    public UIStatistics(Size windowSize, Player player) {
        super(windowSize);
        this.player = player;
        defaultSettings();
        loadStatistics();
    }

    /**
     * Sets parameters to default values.
     */
    private void defaultSettings() {
        this.alignment = new Alignment(Alignment.Position.START, Alignment.Position.START);
        this.statistics = new HashMap<>();
        this.health = new UIText("");
        this.coins = new UIText("");
        addComponent(health);
        addComponent(coins);
    }

    /**
     * Updates information about statistics.
     */
    private void loadStatistics() {
        if (player.getHealthPoints() >= 0)
            statistics.put("health", Integer.toString(player.getHealthPoints()));
        statistics.put("coins", Integer.toString(player.getNumberOfCoins()));
    }

    @Override
    public void update(State state) {
        super.update(state);
        loadStatistics();
        health.setText("HEALTH : " + statistics.get("health"));
        coins.setText("COINS : " + statistics.get("coins"));
    }
}
