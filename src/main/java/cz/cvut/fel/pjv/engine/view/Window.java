package cz.cvut.fel.pjv.engine.view;

import cz.cvut.fel.pjv.engine.controller.InputHandler;
import cz.cvut.fel.pjv.engine.model.game.state.State;
import cz.cvut.fel.pjv.engine.model.utils.DebugCollisionBox;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * Creates window and canvas.
 * Renders objects and the game as a whole.
 */
public class Window extends JFrame {

    // Window width
    public final int WIDTH = 800;
    // Window height
    public final int HEIGHT = 600;

    private Canvas canvas;
    private Renderer renderer;

    private DebugCollisionBox debugCollisionBox;

    public Window(InputHandler inputHandler) {
        this.renderer = new Renderer();
        this.debugCollisionBox = new DebugCollisionBox();
        setCanvas(inputHandler);
        setWindow();
    }

    private void setWindow() {
        setTitle("Shadow Fight: Western Edition");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setCanvas(InputHandler inputHandler) {
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        canvas.setFocusable(false);
        canvas.addMouseListener(inputHandler);
        canvas.addMouseMotionListener(inputHandler);

        add(canvas);
        addKeyListener(inputHandler);
        pack();

        canvas.createBufferStrategy(3);
    }

    /**
     * Draws the contents of the window,
     * background and objects on it.
     */
    public void render(State state) {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();
        graphics.setColor(new Color(135, 110, 191));
        graphics.fillRect(0,0, canvas.getWidth(), canvas.getHeight());

        renderer.render(state, graphics);

//        debugCollisionBox.render(state, graphics);

        graphics.dispose();
        bufferStrategy.show();
    }
}
