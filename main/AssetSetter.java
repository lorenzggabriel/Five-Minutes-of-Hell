package main;

import java.util.Random;

import entity.NPC_Merchant;
import entity.NPC_OldMan;
import object.OBJ_GreenStar;
import object.OBJ_RedStar;
import object.OBJ_YellowStar;
import zombies.ZOM_Boss1Zombie;
import zombies.ZOM_Boss2Zombie;
import zombies.ZOM_NormalZombie;
import zombies.ZOM_StrongZombie;

public class AssetSetter {

    GamePanel gp;

    // Constructor that takes a GamePanel as a parameter
    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    // Method to set NPC (Non-Playable Character) entities
    public void setNPC() {
        // Create and initialize a Merchant NPC
        gp.npc[0] = new NPC_Merchant(gp);
        gp.npc[0].worldX = gp.tileSize * 21;
        gp.npc[0].worldY = gp.tileSize * 21;

        // Create and initialize an OldMan NPC
        gp.npc[1] = new NPC_OldMan(gp);
        gp.npc[1].worldX = gp.tileSize * 22;
        gp.npc[1].worldY = gp.tileSize * 22;
    }

    // Method to set game objects (perks)
    public void setObject() {
        Random random = new Random(); // Get a random number to spawn the objects
        int i = random.nextInt(10); // Get a random number between 0 and 9

        // Check if the object at index i is null
        if (gp.obj[i] == null) {
            int worldCol;
            int worldRow;
            int tileNum;

            // Repeat until a spawnable tile (tileNum 0 or 5) is found
            do {
                worldCol = random.nextInt(gp.maxWorldCol - 2);
                worldRow = random.nextInt(gp.maxWorldRow - 2);
                tileNum = gp.tileM.mapTileNum[worldCol][worldRow]; // Get the tile number to check if it is spawnable

            } while (tileNum != 0 && tileNum != 5);

            // Based on the value of i, create and initialize a Red, Yellow, or Green Star
            // object
            if (i <= 2) {
                gp.obj[i] = new OBJ_RedStar(gp);
                gp.obj[i].worldX = gp.tileSize * worldCol;
                gp.obj[i].worldY = gp.tileSize * worldRow;
            } else if (i > 2 && i <= 5) {
                gp.obj[i] = new OBJ_YellowStar(gp);
                gp.obj[i].worldX = gp.tileSize * worldCol;
                gp.obj[i].worldY = gp.tileSize * worldRow;
            } else if (i > 5 && i < 10) {
                gp.obj[i] = new OBJ_GreenStar(gp);
                gp.obj[i].worldX = gp.tileSize * worldCol;
                gp.obj[i].worldY = gp.tileSize * worldRow;
            }
        }
    }

    // Method to set zombies
    public void setZombie(int i) {
        Random random = new Random(); // Get a random number to spawn the zombie

        // Check if the zombie at index i is null
        if (gp.zombie[i] == null) {
            int worldCol;
            int worldRow;
            int tileNum;
            int xDistance;
            int yDistance;

            // Repeat until a spawnable tile (tileNum 0 or 5) is found
            // Also, ensure that the zombie is spawned away from the player, 5
            do {
                worldCol = random.nextInt(gp.maxWorldCol - 2);
                worldRow = random.nextInt(gp.maxWorldRow - 2);
                tileNum = gp.tileM.mapTileNum[worldCol][worldRow]; // Get the tile number to check if it is spawnable

                // Distance to spawn the zombie away from the player
                xDistance = Math.abs(gp.player.worldX / 48 - worldCol);
                yDistance = Math.abs(gp.player.worldY / 48 - worldRow);

            } while (tileNum != 0 && tileNum != 5 || (xDistance < 5 && yDistance < 5));

            // Based on the value of i and game conditions, create and initialize a Normal,
            // Strong, Boss1, or Boss2 Zombie
            if (i % 2 == 0 && i != 20 && i != 21) {
                gp.zombie[i] = new ZOM_NormalZombie(gp);
                gp.zombie[i].worldX = gp.tileSize * worldCol;
                gp.zombie[i].worldY = gp.tileSize * worldRow;
            } else if (gp.gameTimer >= 60 && i % 2 != 0 && i != 20 && i != 21) { // at 1 minute, strong zombies will
                                                                                 // spawn
                gp.zombie[i] = new ZOM_StrongZombie(gp);
                gp.zombie[i].worldX = gp.tileSize * worldCol;
                gp.zombie[i].worldY = gp.tileSize * worldRow;
            } else if (gp.gameTimer >= 60 * 2 && i == 20 && gp.boss1counter != 1) { // at 2 minutes, 1st zombie will spawn
                gp.zombie[i] = new ZOM_Boss1Zombie(gp);
                gp.zombie[i].worldX = gp.tileSize * worldCol;
                gp.zombie[i].worldY = gp.tileSize * worldRow;
                gp.boss1counter = 1;
            } else if (gp.gameTimer >= 60 * 3 && i == 21 && gp.boss2counter != 1) { // at 3 minutes, 2nd zombie will spawn
                gp.zombie[i] = new ZOM_Boss2Zombie(gp);
                gp.zombie[i].worldX = gp.tileSize * worldCol;
                gp.zombie[i].worldY = gp.tileSize * worldRow;
                gp.boss2counter = 1;
            }
        }
    }

}
