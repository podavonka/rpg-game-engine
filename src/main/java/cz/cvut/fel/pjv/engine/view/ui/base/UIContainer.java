package cz.cvut.fel.pjv.engine.view.ui.base;

import cz.cvut.fel.pjv.engine.model.game.state.State;
import cz.cvut.fel.pjv.engine.model.utils.Position;
import cz.cvut.fel.pjv.engine.model.utils.Size;
import cz.cvut.fel.pjv.engine.view.ui.layout.Alignment;
import cz.cvut.fel.pjv.engine.view.ui.layout.Spacing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains UI components inside it.
 * Adds the content vertically from top to bottom.
 */
public class UIContainer extends UIComponent {
    private List<UIComponent> components;
    protected Alignment alignment;
    private Color backgroundColor;
    protected Size windowSize;
    protected Size fixedSize;

    public UIContainer(Size windowSize) {
        super();
        this.windowSize = windowSize;
        this.components = new ArrayList<>();
        defaultSettings();
        setSize();
        setPosition();
    }

    /**
     * Sets parameters to default values.
     */
    private void defaultSettings() {
        alignment = new Alignment(Alignment.Position.START);
        backgroundColor = new Color(81, 47, 118, 0);
        margin = new Spacing(5);
        padding = new Spacing(5);
    }

    /**
     * Sets container's size.
     */
    private void setSize() {
        Size componentsSize = setComponentsSize();
        size = fixedSize != null ? fixedSize : new Size(padding.getHorizontal() + componentsSize.getWidth(), padding.getVertical() + componentsSize.getHeight());
    }

    /**
     * Calculates container's size based on sizes of components inside it.
     */
    private Size setComponentsSize() {
        int height = 0;
        int maxWidth = 0;
        for(UIComponent component : components) {
            height += component.getSize().getHeight() + component.getMargin().getVertical();
            if(component.getSize().getWidth() > maxWidth)
                maxWidth = component.getSize().getWidth();
        }
        return new Size(maxWidth, height);
    }

    /**
     * Sets container's position.
     */
    private void setPosition() {
        // Calculates horizontal position based on alignment value
        int x = padding.getLeft();
        if (alignment.getHorizontal().equals(Alignment.Position.CENTER))
            x = windowSize.getWidth() /2 - size.getWidth()/2;
        if (alignment.getHorizontal().equals(Alignment.Position.END))
            x = windowSize.getWidth() - size.getWidth() - margin.getRight();

        // Calculates vertical position based on alignment value
        int y = padding.getTop();
        if (alignment.getVertical().equals(Alignment.Position.CENTER))
            y = windowSize.getHeight() /2 - size.getHeight()/2;
        if (alignment.getVertical().equals(Alignment.Position.END))
            y = windowSize.getHeight() - size.getHeight() - margin.getBottom();

        // Position inside the container
        this.relativePosition = new Position(x, y);
        // Position inside the window
        this.absolutePosition = new Position(x, y);

        setComponentsPosition();
    }

    /**
     * Places components vertically from top to bottom.
     */
    private void setComponentsPosition() {
        // Sets carriage
        int currentY = padding.getTop();
        // Iterating through all the components
        for(UIComponent component : components) {
            currentY += component.getMargin().getTop();
            component.setRelativePosition(new Position(padding.getLeft() , currentY));
            component.setAbsolutePosition(new Position(padding.getLeft() + absolutePosition.parseIntX(), currentY + absolutePosition.parseIntY()));
            currentY += component.getSize().getHeight();
            currentY += component.getMargin().getBottom();
        }
    }

    /**
     * Adds the component to this container.
     *
     * @param component Component to be added.
     */
    public void addComponent(UIComponent component) {
        components.add(component);
    }

    @Override
    public void update(State state) {
        components.forEach(component -> component.update(state));
        setSize();
        setPosition();
    }

    /**
     * Sets alignment.
     *
     * @param alignment Position in the window.
     */
    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    /**
     * Sets background color.
     *
     * @param backgroundColor Color to be set.
     */
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * Sets fixed size of container.
     *
     * @param fixedSize Required size.
     */
    public void setFixedSize(Size fixedSize) {
        this.fixedSize = fixedSize;
    }

    @Override
    public Image getSprite() {
        BufferedImage image = new BufferedImage(size.getWidth(),size.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(backgroundColor);
        graphics.fillRect(0, 0, size.getWidth(), size.getHeight());

        for (UIComponent component : components)
            graphics.drawImage(
                    component.getSprite(),
                    component.getRelativePosition().parseIntX(),
                    component.getRelativePosition().parseIntY(),
                    null
            );

        graphics.dispose();
        return image;
    }
}
