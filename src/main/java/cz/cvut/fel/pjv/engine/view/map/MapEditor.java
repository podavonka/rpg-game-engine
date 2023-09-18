package cz.cvut.fel.pjv.engine.view.map;

import cz.cvut.fel.pjv.engine.view.animation.SpriteStorage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Loads map from .txt format file as array of integers.
 * Translates numbers from array to tiles.
 */
public class MapEditor {
    private SpriteStorage spriteStorage;
    private int width;
    private int height;
    private Tile[][] map;
    private int[][] tilesIDs;
    private Tile sand;
    private Tile grass;
    private Tile water;
    private Tile herbOnGrass1;
    private Tile herbOnGrass2;
    private Tile rockOnGrass;
    private Tile herbOnSand;
    private Tile rockOnSand1;
    private Tile rockOnSand2;

    public MapEditor(int width, int height, SpriteStorage spriteStorage) {
        this.spriteStorage = spriteStorage;
        this.width = width;
        this.height = height;
        this.map = new Tile[height][width];
        this.tilesIDs = new int[height][width];
        setTiles();
        readTextFile();
        textToTiles();
    }

    private void setTiles() {
        this.sand = new Tile(spriteStorage, "sand", true);
        this.grass = new Tile(spriteStorage, "grass", true);
        this.water = new Tile(spriteStorage, "water", false);
        this.herbOnGrass1 = new Tile(spriteStorage, "herbOnGrass1", true);
        this.herbOnGrass2 = new Tile(spriteStorage, "herbOnGrass2", true);
        this.rockOnGrass = new Tile(spriteStorage, "rockOnGrass", true);
        this.herbOnSand = new Tile(spriteStorage, "herbOnSand", true);
        this.rockOnSand1 = new Tile(spriteStorage, "rockOnSand1", true);
        this.rockOnSand2 = new Tile(spriteStorage, "rockOnSand2", true);
    }

    /**
     * Reads .txt format file from resources
     * Loads to array of integers.
     * Each number represents its own tile.
     */
    private void readTextFile() {
        try {
            File mapTxt = new File("src/main/resources/map.txt");
            Scanner scanner = new Scanner(mapTxt);
            int i = 0, j = 0;
            while(scanner.hasNextInt()) {
                tilesIDs[j++][i] = scanner.nextInt();
                if (j == width) {
                    j = 0;
                    i++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Translates array of integers to curtain tiles.
     * Gives the result to the map array.
     */
    private void textToTiles() {
        for (int i = 0; i < height; i++)
            for (int j = 0; j < height; j++)
                switch (tilesIDs[i][j]) {
                    case 1768 -> map[i][j] = grass;
                    case 1858 -> map[i][j] = sand;
                    case 2101 -> map[i][j] = herbOnGrass1;
                    case 2102 -> map[i][j] = herbOnGrass2;
                    case 2103 -> map[i][j] = rockOnGrass;
                    case 2104 -> map[i][j] = herbOnSand;
                    case 2105 -> map[i][j] = rockOnSand1;
                    case 2106 -> map[i][j] = rockOnSand2;
                    case 0 -> map[i][j] = water;
                }
    }

    /**
     * @return Array of tiles, or game map.
     */
    public Tile[][] getMap() {
        return map;
    }
}
