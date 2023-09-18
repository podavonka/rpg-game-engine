package cz.cvut.fel.pjv.engine.model.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

/**
 * Works with images.
 */
public class ImageUtils {

    /**
     * Loads image from path.
     *
     * @param filePath Path to image.
     * @return Read image.
     */
    public static Image loadImage(String filePath) {
        try {
            return ImageIO.read(ImageUtils.class.getResource(filePath));
        } catch (IOException e) {
            System.err.println("ERROR: can not load image from path: " + filePath);
        }
        return null;
    }
}
