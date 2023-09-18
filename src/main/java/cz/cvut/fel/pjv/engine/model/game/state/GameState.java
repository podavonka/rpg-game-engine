package cz.cvut.fel.pjv.engine.model.game.state;

import cz.cvut.fel.pjv.engine.controller.EnemyController;
import cz.cvut.fel.pjv.engine.controller.InputHandler;
import cz.cvut.fel.pjv.engine.controller.PlayerController;
import cz.cvut.fel.pjv.engine.model.game.Game;
import cz.cvut.fel.pjv.engine.model.game.GameData;
import cz.cvut.fel.pjv.engine.model.net.GameClient;
import cz.cvut.fel.pjv.engine.model.net.GameServer;
import cz.cvut.fel.pjv.engine.model.net.packets.Packet00Login;
import cz.cvut.fel.pjv.engine.model.object.TargetZone;
import cz.cvut.fel.pjv.engine.model.object.entity.*;
import cz.cvut.fel.pjv.engine.model.object.BagOfCoins;
import cz.cvut.fel.pjv.engine.model.object.Heart;
import cz.cvut.fel.pjv.engine.view.animation.SpriteStorage;
import cz.cvut.fel.pjv.engine.view.map.TileManager;
import cz.cvut.fel.pjv.engine.model.utils.Size;
import cz.cvut.fel.pjv.engine.view.ui.*;
import cz.cvut.fel.pjv.engine.view.ui.base.UIContainer;
import cz.cvut.fel.pjv.engine.view.ui.state.UIGameOver;
import cz.cvut.fel.pjv.engine.view.ui.state.UIPause;

import java.awt.event.KeyEvent;
import java.util.logging.Logger;


/**
 * Creates objects necessary for the game process itself.
 * Places them on canvas.
 */
public class GameState extends State {
    public PlayerMP player;
    private GameData gameData;
    private UIContainer pauseContainer;
    private boolean playing;
    private boolean isPaused = false;
    private boolean isMultiplayer;
    public GameServer socketServer;
    public GameClient socketClient;

    public GameState(InputHandler inputHandler, Size winSize, boolean isMultiplayer) {
        super(inputHandler, winSize);
        setMultiplayer(isMultiplayer);
        this.isMultiplayer = isMultiplayer;
        playing = true;
        if (isMultiplayer())
            setNet();
        setMap();
        setCharacters();
        setItems(5, 5);
        setUI(winSize);
        gameData = new GameData(player);
        pauseContainer = new UIPause(windowSize, this);
    }

    private void setNet() {
        socketServer = new GameServer(this);
        socketServer.start();

        socketClient = new GameClient(this, "localHost");
        socketClient.start();
    }

    private void setMap() {
        tileManager = new TileManager(new Size(80, 80), spriteStorage);
    }

    private void setCharacters() {
        setPlayer();
        setCactusEnemy(5);
        setShadowEnemy(10);
    }

    private void setPlayer() {
        if (isMultiplayer()){
            TargetZone targetZone = new TargetZone();
            player = new PlayerMP(this, new PlayerController(inputHandler), spriteStorage, targetZone,"player", null, -1);
            gameObjects.add(player);
            camera.focusOn(player);
            gameObjects.add(targetZone);

            Packet00Login loginPacket = new Packet00Login(player.getUsername());
            if (socketServer != null)
                socketServer.addConnection(player, loginPacket);
            loginPacket.sendData(socketClient);
        } else {
            TargetZone targetZone = new TargetZone();
            player = new PlayerMP(this, new PlayerController(inputHandler), spriteStorage, targetZone,"player", null, -1);
            gameObjects.add(player);
            camera.focusOn(player);
            gameObjects.add(targetZone);
        }
    }

    private void setCactusEnemy(int numberOfEnemies) {
        for(int i = 0; i < numberOfEnemies; i++) {
            TargetZone targetZone = new TargetZone();
            CactusEnemy cactusEnemy = new CactusEnemy(new EnemyController(), spriteStorage, targetZone);
            cactusEnemy.setPosition(tileManager.getRandomPosition());
            gameObjects.add(cactusEnemy);
        }
    }

    private void setShadowEnemy(int numberOfEnemies) {
        for(int i = 0; i < numberOfEnemies; i++) {
            TargetZone targetZone = new TargetZone();
            ShadowEnemy shadowEnemy = new ShadowEnemy(new EnemyController(), spriteStorage, targetZone);
            shadowEnemy.setPosition(tileManager.getRandomPosition());
            gameObjects.add(shadowEnemy);
        }
    }

    private void setItems(int numberOfBags, int numberOfHearts) {
        for(int i = 0; i < numberOfBags; i++) {
            BagOfCoins bagOfCoins = new BagOfCoins();
            bagOfCoins.setPosition(tileManager.getRandomPosition());
            gameObjects.add(bagOfCoins);
        }

        for(int i = 0; i < numberOfHearts; i++) {
            Heart heart = new Heart();
            heart.setPosition(tileManager.getRandomPosition());
            gameObjects.add(heart);
        }
    }

    private void setUI(Size winSize) {
        uiContainers.add(new UITime(winSize));
        uiContainers.add(new UIStatistics(winSize, player));
        uiContainers.add(new UIShop(winSize, this));
    }

    /**
     * Stops the game process and shows pause menu.
     */
    private void makePause() {
        if(inputHandler.isPressed(KeyEvent.VK_ESCAPE)) {
            boolean shouldPause = !isPaused;
            if(shouldPause) {
                isPaused = true;
                uiContainers.add(pauseContainer);
                Logger.getLogger(this.getClass().getName()).info("Game is paused!");
            } else {
                isPaused = false;
                Logger.getLogger(this.getClass().getName()).info("Game is unpaused!");
                deleteUiContainer(pauseContainer);
            }
        }
    }

    /**
     * Saves game data to .json format file.
     * @see GameData#saveGame()
     */
    public void saveGame(){
        gameData.saveGame();
    }

    /**
     * The game ends and the win menu displays.
     */
    private void win() {
        endGame("Win");
        uiContainers.add(new UIGameOver(camera.getSize(), this, "WIN"));
    }

    /**
     * The game ends and the defeat menu displays.
     */
    private void lose() {
        endGame("Lose");
        uiContainers.add(new UIGameOver(camera.getSize(), this, "DEFEAT"));
    }

    /**
     * Sets parameters to end the game.
     *
     * @param nameOfState Reason for ending the game.
     */
    private void endGame(String nameOfState) {
        playing = false;
        uiContainers.clear();
        Logger.getLogger(this.getClass().getName()).info(nameOfState + " state is on!");
    }

    @Override
    public void update(Game game) {
        super.update(game);
        if (!isPaused)
            time.update();
        makePause();
        if(playing) {
            if (getGameObjectsOfClass(Enemy.class).stream().allMatch(enemy -> !enemy.isAlive()))
                win();
            if (!player.isAlive())
                lose();
        }
    }

    @Override
    protected void updateGameObjects() {
        if(!isPaused)
            super.updateGameObjects();
    }

    /**
     * Checks whether the game is paused.
     *
     * @return true, if the game is paused,
     *         false, if the game is NOT paused.
     */
    public boolean isPaused() {
        return isPaused;
    }

    public boolean isMultiplayer() {
        return isMultiplayer;
    }

    public Player getPlayer() {
        return player;
    }

    public SpriteStorage getSpriteStorage(){
        return spriteStorage;
    }

}
