package cz.cvut.fel.pjv.engine.controller;

import cz.cvut.fel.pjv.engine.model.game.state.GameState;
import cz.cvut.fel.pjv.engine.model.game.state.State;
import cz.cvut.fel.pjv.engine.model.net.packets.Packet01Disconnect;
import cz.cvut.fel.pjv.engine.view.Window;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Used to remove a character from
 * the server during multiplayer
 * when the window is closed.
 */
public class WindowHandler implements WindowListener {
    private GameState gameState;


    public WindowHandler(State state, Window window){
        this.gameState = (GameState)state;
        window.addWindowListener(this);

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

        Packet01Disconnect packet = new Packet01Disconnect(this.gameState.player.getUsername());
        packet.sendData(this.gameState.socketClient);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}