package cz.cvut.fel.pjv.engine.view.ui.base;

import cz.cvut.fel.pjv.engine.model.game.state.State;
import cz.cvut.fel.pjv.engine.model.utils.Size;
import cz.cvut.fel.pjv.engine.view.ui.base.UIComponent;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Sets text as a user interface component.
 */
public class UIText extends UIComponent {
    private String text;
    private Color textColor;

    // Font parameters
    private Font font;
    private String fontFamily;
    private int fontStyle;
    private int fontSize;

    public UIText(String text) {
        this.text = text;
        this.fontSize = 20;
        this.fontStyle = Font.BOLD;
        this.fontFamily = "Comic Sans MS";
        this.textColor = Color.WHITE;
    }

    /**
     * Sets line to the text component.
     *
     * @param text Line to be set.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Sets text font.
     */
    private void setFont() {
        font = new Font(fontFamily, fontStyle, fontSize);
    }

    /**
     * Calculates text component size.
     */
    private void setSize() {
        FontMetrics fontMetrics = new Canvas().getFontMetrics(font);
        int width = fontMetrics.stringWidth(text) + padding.getHorizontal();
        int height = fontMetrics.getHeight() + padding.getVertical();
        size = new Size(width, height);
    }

    @Override
    public void update(State state) {
        setFont();
        setSize();
    }

    @Override
    public Image getSprite() {
        BufferedImage image = new BufferedImage(size.getWidth(),size.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();

        graphics.setFont(font);
        graphics.setColor(textColor);
        graphics.drawString(text, padding.getLeft(), fontSize + padding.getTop());

        graphics.dispose();
        return image;
    }
}
