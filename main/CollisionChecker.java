// CREDITS TO: RyiSnow for the base code implementation of strict collision (Only for player)

package main;

import entity.Entity;

public class CollisionChecker {

    GamePanel gp; // Reference to the GamePanel

    // Constructor that takes a GamePanel as a parameter
    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    // Method to check collision with tiles
    public void checkTile(Entity entity) {
        // Calculate the world coordinates of the entity's bounding box
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        // Convert world coordinates to tile indices
        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        // Variables to store tile numbers for collision checking
        int tileNum1, tileNum2;// we only need to check two tiles for each direction

        // Switch based on the entity's direction
        switch (entity.direction) {
            case "up":
                // Predict the player's position when moving up
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize; // we kind of predict the player's
                                                                               // position
                // Get tile numbers at the predicted positions
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];

                // Check if either of the tiles has collision
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }

                break;

            case "down":
                // Predict the player's position when moving down
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize; // we kind of predict the player's
                                                                                     // position
                // Get tile numbers at the predicted positions
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];

                // Check if either of the tiles has collision
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;

            case "left":
                // Predict the player's position when moving left
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize; // we kind of predict the player's
                                                                                 // position
                // Get tile numbers at the predicted positions
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];

                // Check if either of the tiles has collision
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;

            case "right":
                // Predict the player's position when moving right
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize; // we kind of predict the player's
                                                                                   // position
                // Get tile numbers at the predicted positions
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];

                // Check if either of the tiles has collision
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
        }

    }

    // Method to check collision with objects
    public int checkObject(Entity entity, boolean player) { // we check if player is hitting any object
        int index = 999; // Initialize index to a default value

        // Iterate through the objects in the GamePanel
        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {
                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Get the object's solid area position
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                // Adjust entity's solid area based on its direction
                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        break;
                }

                // Check for intersection between entity and object solid areas
                if (entity.solidArea.intersects(gp.obj[i].solidArea)) { // this checks if these two rectangles
                    // are colliding or not
                    // System.out.println("up collision!");
                    if (gp.obj[i].collision == true) {
                        entity.collisionOn = true;
                    }
                    // If checking for the player and there's a collision, set the index
                    if (player == true) {
                        index = i;
                    }
                }
                // Reset solid areas to default positions
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }

        return index; // Return the index of the object that was hit
    }

    // Method to check collision with other entities - NPC or Monster Collision
    public int checkEntity(Entity entity, Entity[] target) {
        int index = 999; // Initialize index to a default value

        // Iterate through the array of target entities
        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {
                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Get the object's solid area position
                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

                // Adjust entity's solid area based on its direction
                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        break;
                }

                // Check for intersection between entity and target entity solid areas
                if (entity.solidArea.intersects(target[i].solidArea)) { // this checks if these two rectangles are
                                                                        // colliding or not
                    // System.out.println("up collision!");
                    // If there's a collision and the target entity is not the same as the entity,
                    // set the index
                    if (target[i] != entity) {
                        entity.collisionOn = true;
                        index = i;
                    }
                }

                // Reset solid areas to default positions
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }

        return index; // Return the index of the target entity that was hit
    }

    // Method to check collision with the player
    public boolean checkPlayer(Entity entity) {

        boolean contactPlayer = false; // Initialize a flag to track player contact

        // Get entity's solid area position
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;

        // Get the object's solid area position
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        // Adjust entity's solid area based on its direction
        switch (entity.direction) {
            case "up":
                entity.solidArea.y -= entity.speed;
                break;
            case "down":
                entity.solidArea.y += entity.speed;
                break;
            case "left":
                entity.solidArea.x -= entity.speed;
                break;
            case "right":
                entity.solidArea.x += entity.speed;
                break;
        }

        // Check for intersection between entity and player solid areas
        if (entity.solidArea.intersects(gp.player.solidArea)) { // this checks if these two rectangles are colliding or
                                                                // not
            // System.out.println("down collision!");
            entity.collisionOn = true;
            contactPlayer = true;
        }

        // Reset solid areas to default positions
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;

        return contactPlayer; // Return whether there is contact with the player
    }
}
