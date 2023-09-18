package cz.cvut.fel.pjv.engine.view.animation;

import cz.cvut.fel.pjv.engine.model.utils.ImageUtils;

import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Loads units and tiles from their directories and store them.
 */
public class SpriteStorage {
    private final static String PATH_TO_RESOURCES = "src/main/resources";
    private final static String PATH_TO_UNITS = "/sprites/units";
    private final static String PATH_TO_TILES = "/sprites/tiles";

    private Map<String, SpriteSet> units;
    private Map<String, Image> tiles;

    public SpriteStorage() {
        units = new HashMap<>();
        tiles = new HashMap<>();
        loadUnits();
        loadTiles();
    }

    /**
     * Fills the array of units.
     */
    private void loadUnits() {
        String[] folderNames = getFolderNames();

        for(String folderName: folderNames) {
            SpriteSet spriteSet = new SpriteSet();
            String pathToFolder = PATH_TO_UNITS + "/" + folderName;
            String[] sheetsInFolder = getFilesInFolder(pathToFolder);

            for(String sheetName: sheetsInFolder) {
                spriteSet.addSheet(sheetName.substring(0, sheetName.length() - 4),
                        ImageUtils.loadImage(pathToFolder + "/" + sheetName));
            }

            units.put(folderName, spriteSet);
        }
    }

    /**
     * Fills the array of tiles.
     */
    private void loadTiles() {
        String[] filesInFolder = getFilesInFolder(PATH_TO_TILES);

        for(String filename: filesInFolder) {
            tiles.put(filename.substring(0, filename.length() - 4),
                    ImageUtils.loadImage(PATH_TO_TILES + "/" + filename));
        }
    }

    /**
     * @return Name of folder.
     */
    private String[] getFolderNames() {
        Path dir = Paths.get(PATH_TO_RESOURCES + PATH_TO_UNITS);
//        String absolutePath = dir.toFile().getAbsolutePath();
        File file = new File(String.valueOf(dir.toFile()));
        return file.list((current, name) -> new File(current, name).isDirectory());
    }

    /**
     * @param path Path in resources.
     * @return Line with list of files.
     */
    private String[] getFilesInFolder(String path) {
        Path dir = Paths.get(PATH_TO_RESOURCES + path);
//        String absolutePath = dir.toFile().getAbsolutePath();
        File file = new File(String.valueOf(dir.toFile()));
        return file.list((current, name) -> new File(current, name).isFile());
    }

    /**
     * @param name Name of unit.
     *             For example, 'steve'.
     * @return Set of sprite sheets for that unit.
     */
    public SpriteSet getUnit(String name) {
        return units.get(name);
    }

    /**
     * @param name Name of tile.
     *             For example, 'grass'.
     * @return Tile image.
     */
    public Image getTile(String name) {
        return tiles.get(name);
    }
}
