package cz.cvut.fel.pjv.engine.model.object.entity;

import cz.cvut.fel.pjv.engine.controller.Controller;
import cz.cvut.fel.pjv.engine.model.game.state.GameState;
import cz.cvut.fel.pjv.engine.model.object.TargetZone;
import cz.cvut.fel.pjv.engine.view.animation.SpriteStorage;

import java.net.InetAddress;

/**
 * Player class for multiplayer.
 */
public class PlayerMP extends Player{

    public InetAddress ipAddress;
    public int port;


    public PlayerMP(GameState gameState, Controller controller, SpriteStorage spriteStorage, TargetZone targetZone, String username, InetAddress ipAddress, int port) {
        super(gameState, controller, spriteStorage, targetZone);
        this.username = username;
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public PlayerMP(GameState gameState, SpriteStorage spriteStorage, TargetZone targetZone, String username, InetAddress ipAddress, int port) {
        super(gameState,null, spriteStorage, targetZone);
        this.username = username;
        this.ipAddress = ipAddress;
        this.port = port;
    }


}
