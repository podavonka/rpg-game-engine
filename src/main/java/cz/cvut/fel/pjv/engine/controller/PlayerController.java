package cz.cvut.fel.pjv.engine.controller;

import java.awt.event.KeyEvent;

/**
 * Accepts player's input from keyboard to control character.
 * Implements methods of {@link Controller}.
 *
 * To control character are used keys:
 * 'W' to move UP, 'S' to move DOWN,
 * 'A' to move LEFT, 'D' to move RIGHT,
 * 'SPACE' to shoot.
 * */
public class PlayerController implements Controller {
    private InputHandler inputHandler;

    public PlayerController(InputHandler inputHandler){
        this.inputHandler = inputHandler;
    }

    @Override
    public boolean isUp() {
        return inputHandler.isCurrentlyPressed(KeyEvent.VK_W);
    }

    @Override
    public boolean isDown() {
        return inputHandler.isCurrentlyPressed(KeyEvent.VK_S);
    }

    @Override
    public boolean isLeft() {
        return inputHandler.isCurrentlyPressed(KeyEvent.VK_A);
    }

    @Override
    public boolean isRight() {
        return inputHandler.isCurrentlyPressed(KeyEvent.VK_D);
    }

    @Override
    public boolean isShoot() {
        return inputHandler.isCurrentlyPressed(KeyEvent.VK_SPACE);
    }
}
