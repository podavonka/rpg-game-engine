package cz.cvut.fel.pjv.engine.model.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fel.pjv.engine.model.object.entity.Player;

import java.io.File;
import java.io.IOException;

/**
 * Saves game data in .json format file.
 * Loads that data on request.
 */
public class SaveGame {
    private final ObjectMapper objectMapper;

    public SaveGame(GameData gameData) {
        this.objectMapper = new ObjectMapper();
//        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        saveJSON(gameData);
    }

    /**
     * Saves game data in .json format file.
     *
     * @param gameData Data to be saved.
     */
    public void saveJSON(GameData gameData) {
        try {
            objectMapper.writeValue(new File("saves/gamesave.json"), gameData);
        }
        catch (IOException ex) {
            System.err.println("ERROR: can not save game to .json file!" + ex);
        }
    }

    /**
     * Loads game data from .json format file.
     *
     * @param gameData Place for saved data to be loaded to.
     * @return Data that were loaded.
     */
    public Player loadJSON(GameData gameData) {
        try {
            var data = objectMapper.readValue(new File("saves/gamesave.json"), Player.class);
            return data;
        }
        catch (IOException ex) {
            System.err.println("ERROR: can not load game to .json file!" + ex);
        }
        return null;
    }
}