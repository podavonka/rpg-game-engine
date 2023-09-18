package cz.cvut.fel.pjv.engine.model.game;

import cz.cvut.fel.pjv.engine.model.object.entity.Player;

/**
 * All data that is set in the beginning of the game
 * and changes during its course.
 */
public class GameData {
    private Player player;
    SaveGame saveGame = new SaveGame(this);

    public GameData() {
    }

    public GameData(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void saveGame() {
        saveGame.saveJSON(this);
    }

    public Player loadGame() {
        return saveGame.loadJSON(this);
    }
}
