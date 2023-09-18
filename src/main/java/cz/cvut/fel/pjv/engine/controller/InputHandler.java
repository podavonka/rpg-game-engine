package cz.cvut.fel.pjv.engine.controller;

import cz.cvut.fel.pjv.engine.model.utils.Position;

import java.awt.event.*;

/**
 * Reads keystrokes and mouse clicks.
 * Processes input from user.
 */
public class InputHandler implements KeyListener, MouseListener, MouseMotionListener {
    private boolean[] pressed;
    private boolean[] currentlyPressed;

    private Position mousePosition;
    private boolean mouseClicked;
    private boolean mousePressed;

    public InputHandler() {
        pressed = new boolean[255];
        currentlyPressed = new boolean[255];
        mousePosition = new Position(0,0);
    }

    /**
     * Checks whether the key was pressed.
     *
     * @param keyCode Code of the key on keyboard, is used as index in the array.
     * @return true, if the key was pressed, false, if the key was NOT pressed.
     */
    public boolean isPressed(int keyCode){
        if (!pressed[keyCode] && currentlyPressed[keyCode]) {
            pressed[keyCode] = true;
            return true;
        }
        return false;
    }

    /**
     * Checks whether the key was CURRENTLY pressed.
     *
     * @param keyCode Code of the key on keyboard, is used as index in the array.
     */
    public boolean isCurrentlyPressed(int keyCode){
        return currentlyPressed[keyCode];
    }

    public Position getMousePosition() {
        return mousePosition;
    }

    public boolean isMouseClicked() {
        return mouseClicked;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void clearMouseClick() {
        mouseClicked = false;
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        currentlyPressed[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        currentlyPressed[e.getKeyCode()] = false;
        pressed[e.getKeyCode()] = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseClicked = true;
        mousePressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseDragged(MouseEvent e) {
        mousePosition = new Position(e.getPoint().getX(), e.getPoint().getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePosition = new Position(e.getPoint().getX(), e.getPoint().getY());
    }
}
