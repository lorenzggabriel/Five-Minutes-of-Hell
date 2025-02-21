package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

// This class manages tiles and the game map
public class TileManager {
    GamePanel gp; // Reference to the GamePanel
    public Tile[] tile; // Array to store different kinds of tiles
    public int mapTileNum[][]; // 2D array to store the map layout using tile indices

    // Constructor that initializes the TileManager
    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[10]; // no. of kinds of tiles; // Initialize the tile array with a size of 10
                             // (assuming 10 types of tiles)
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow]; // Initialize the mapTileNum array with world dimensions

        getTileImage(); // Method to set up the images for different tiles
        loadMap("/maps/world01.txt"); // Method to load the map layout from a file
    }

    // Method to set up images for different tiles
    public void getTileImage() {
        // Set up images for different tiles using the setup method
        setup(0, "grass", false);
        setup(1, "wall", true);
        setup(2, "water", true);
        setup(3, "earth", false);
        setup(4, "tree", true);
        setup(5, "sand", false);
    }

    // Method to set up an individual tile
    public void setup(int index, String imageName, boolean collision) {
        UtilityTool uTool = new UtilityTool(); // UtilityTool instance for scaling images
        try {
            // Create a new Tile and set its image and collision properties
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tilesImage/" + imageName + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

        } catch (IOException e) {
            e.printStackTrace(); // Print any IOException stack trace
        }
    }

    // Method to load the map layout from a file
    public void loadMap(String filePath) {
        try {
            // Open an input stream to read the map file
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            // Loop through the map file and populate the mapTileNum array
            while (col < gp.maxWorldCol && row < gp.maxWorldRow) { // the world map is the boundaries
                String line = br.readLine(); // Read a line from the file

                while (col < gp.maxWorldCol) {
                    String numbers[] = line.split(" "); // Split the line into numbers

                    int num = Integer.parseInt(numbers[col]); // Parse the number

                    mapTileNum[col][row] = num; // Store the number in the mapTileNum array
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close(); // Close the BufferedReader

        } catch (Exception e) {
            // Handle any exceptions (currently empty)
        }
    }

    // Method to draw the tiles on the screen
    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        // Loop through the mapTileNum array and draw tiles within the visible screen
        // area
        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) { // Get the tile number from the mapTileNum
                                                                         // array

            int tileNum = mapTileNum[worldCol][worldRow]; // extract a tile number; map data has been stored in the
                                                          // mapTileNum[][]

            // Calculate the screen coordinates for drawing the tile
            int worldX = worldCol * gp.tileSize; // worldX is the position on the map
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX; // first, we check the tile's worldX so that is
                                                                         // worldCol x tileSize; screenX is where on the
                                                                         // screen we draw it
            int screenY = worldY - gp.player.worldY + gp.player.screenY; // if player is at worldX 500 and worldY 500,
                                                                         // then player is 500 pixels away from this
                                                                         // [0][0] tile, then the [0][0] tile should be
                                                                         // drawn at 500 pixels to the left and 500
                                                                         // pixels to the top from the player (that's
                                                                         // why we need to subtract the player's worldX
                                                                         // and worldY from the tile's worldX and
                                                                         // worldY)

            // Check if the tile is within the visible screen area
            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
                    && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                    && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                    && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) { // we create a boundary
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }

            worldCol++;

            // Move to the next row when reaching the end of a column
            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;

                worldRow++;
            }
        }
    }

}
