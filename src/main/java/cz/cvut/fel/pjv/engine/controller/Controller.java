package cz.cvut.fel.pjv.engine.controller;

/**
 * The controller accepts input from user.
 */
public interface Controller {

    /**
     * @return true value if upward movement is requested.
     */
    boolean isUp();

    /**
     * @return true value if downward movement is requested.
     */
    boolean isDown();

    /**
     * @return true value if left movement is requested.
     */
    boolean isLeft();

    /**
     * @return true value if right movement is requested.
     */
    boolean isRight();

    /**
     * @return true value if shoot action is requested.
     */
    boolean isShoot();
}
