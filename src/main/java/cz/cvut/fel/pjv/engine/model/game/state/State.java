package cz.cvut.fel.pjv.engine.model.game.state;

import cz.cvut.fel.pjv.engine.controller.InputHandler;
import cz.cvut.fel.pjv.engine.model.game.Game;
import cz.cvut.fel.pjv.engine.model.object.BagOfCoins;
import cz.cvut.fel.pjv.engine.model.object.entity.Entity;
import cz.cvut.fel.pjv.engine.model.object.GameObject;
import cz.cvut.fel.pjv.engine.model.object.Heart;
import cz.cvut.fel.pjv.engine.model.object.entity.PlayerMP;
import cz.cvut.fel.pjv.engine.model.utils.Position;
import cz.cvut.fel.pjv.engine.view.animation.SpriteStorage;
import cz.cvut.fel.pjv.engine.view.map.TileManager;
import cz.cvut.fel.pjv.engine.model.utils.Size;
import cz.cvut.fel.pjv.engine.model.utils.Timer;
import cz.cvut.fel.pjv.engine.view.Camera;
import cz.cvut.fel.pjv.engine.view.ui.base.UIContainer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Contains features of all states in the game.
 */
public abstract class State {
    private boolean isMultiplayer;
    protected InputHandler inputHandler;
    protected Size windowSize;
    protected List<GameObject> gameObjects;
    protected List<UIContainer> uiContainers;
    protected SpriteStorage spriteStorage;
    protected Camera camera;
    protected Timer time;
    protected TileManager tileManager;
    private State nextState;

    public State(InputHandler inputHandler, Size windowSize) {
        this.inputHandler = inputHandler;
        this.windowSize = windowSize;
        gameObjects = new ArrayList<>();
        uiContainers = new ArrayList<>();
        spriteStorage = new SpriteStorage();
        camera = new Camera(windowSize);
        time = new Timer();
    }

    /**
     * Adjusts the display of game objects and other elements.
     */
    public void update(Game game) {
        sortObjectsByPosition();
        updateGameObjects();
        List.copyOf(uiContainers).forEach(uiContainer -> uiContainer.update(this));
        camera.update(this);
        handleMouseInput();

        if (nextState != null)
            game.enterState(nextState);
    }

    /**
     * Deletes unnecessary objects and updates the remaining ones.
     */
    protected void updateGameObjects() {
        getGameObjectsOfClass(Entity.class).stream().filter(entity -> entity.isKilled()).forEach(entity -> deleteGameObject(entity));
        getGameObjectsOfClass(BagOfCoins.class).stream().filter(bagOfCoins -> bagOfCoins.isUsed()).forEach(bagOfCoins -> deleteGameObject(bagOfCoins));
        getGameObjectsOfClass(Heart.class).stream().filter(heart -> heart.isUsed()).forEach(heart -> deleteGameObject(heart));
        gameObjects.forEach(gameObject -> gameObject.update(this));
    }

    /**
     * Removes the character with username from param.
     *
     * @param username Username of character to be removed.
     */
    public void removePlayerMP(String username){
        int index = 0;
        for (GameObject gameObject: gameObjects){
            if (gameObject instanceof PlayerMP && ((PlayerMP) gameObject).getUsername().equals(username)){
                break;
            }
            index ++;
        }
        this.gameObjects.remove(index);

    }

    /**
     * @param username Username of character which id we want to get.
     * @return Index of character with username from param.
     */
    public int getPlayerMPindex(String username) {
        int index = 0;
        for (GameObject gameObject: gameObjects){
            if (gameObject instanceof PlayerMP && ((PlayerMP) gameObject).getUsername().equals(username)){
                break;
            }
            index ++;
        }
        return index;
    }

    /**
     * Used when playing multiplayer.
     *
     * @param username Username of character we want to move.
     * @param x X coordinate we want to reach.
     * @param y Y coordinate we want to reach.
     */
    public void movePlayer(String username, int x, int y){
        int index = getPlayerMPindex(username);
        this.gameObjects.get(index).setPosition(new Position(x,y));
    }

    /**
     * Compares game objects by their position at the Y coordinate.
     * Object with larger value will have "priority".
     *
     * This method is necessary for correct rendering of objects relative to each other.
     * When one of them is located lower, it should be shown in the front.
     */
    private void sortObjectsByPosition() {
        gameObjects.sort(Comparator.comparing(GameObject::getRenderOrder).thenComparing(gameObject -> gameObject.getPosition().getY()));
    }

    /**
     * Deletes game objects from list.
     * It stops displaying.
     *
     * @param objectToDelete Game objects to be deleted.
     */
    public void deleteGameObject(GameObject objectToDelete) {
        gameObjects.remove(objectToDelete);
    }

    /**
     * Deletes UI container from list.
     * It stops displaying.
     *
     * @param containerToDelete UI container to be deleted.
     */
    public void deleteUiContainer(UIContainer containerToDelete) {
        uiContainers.remove(containerToDelete);
    }

    /**
     * Clears mouse click every update.
     */
    protected void handleMouseInput() {
        inputHandler.clearMouseClick();
    }

    public void setMultiplayer(boolean multiplayer) {
        isMultiplayer = multiplayer;
    }

    /**
     * @param nextState State to be set.
     */
    public void setNextState(State nextState) {
        this.nextState = nextState;
    }

    public boolean isMultiplayer() {
        return isMultiplayer;
    }

    /**
     * @return Random position on the map.
     * @see TileManager#getRandomPosition()
     */
    public Position getRandomPosition() {
        return tileManager.getRandomPosition();
    }

    /**
     * @return List of game objects.
     */
    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    /**
     * @param clazz Class whose objects we are looking for.
     * @return List of required class game objects.
     */
    public <T extends GameObject> List<T> getGameObjectsOfClass(Class<T> clazz) {
        return gameObjects.stream()
                .filter(clazz::isInstance)
                .map(gameObject -> (T) gameObject)
                .collect(Collectors.toList());
    }

    /**
     * Finds collisions with the requested object.
     *
     * @param gameObject Object for checking collisions.
     * @return List of game objects that collides with requested object.
     */
    public List<GameObject> getCollidingGameObjects(GameObject gameObject) {
        return gameObjects.stream()
                .filter(other -> other.collidesWith(gameObject.getCollisionBox()))
                .collect(Collectors.toList());
    }

    /**
     * @return List of user interface containers.
     */
    public List<UIContainer> getUiContainers() {
        return uiContainers;
    }

    public Timer getTime() {
        return time;
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public Camera getCamera() {
        return camera;
    }
}
