// CREDITS TO: RyiSnow for the image borrowing of the merchant

package entity;

import java.awt.Rectangle;
import java.util.Random;

import main.GamePanel;
import object.OBJ_MinigunBullet;
import object.OBJ_Potion;
import object.OBJ_RifleBullet;
import object.OBJ_ShotgunBullet;

public class NPC_Merchant extends Entity {
    // Constructor for NPC_Merchant class
    public NPC_Merchant(GamePanel gp) {
        super(gp); // Call the constructor of the superclass (Entity)

        // Set initial properties for the NPC_Merchant
        direction = "down";
        speed = 1;

        // Create a Rectangle to define the solid area of the NPC for collision
        // detection
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        // Load images for different directions of the NPC
        getImage();
        setDialogue(); // Set initial dialogue for the NPC
        setItems(); // Set initial items for the NPC's inventory
    }

    // Method to load images for different directions of the NPC
    public void getImage() {
        up1 = setup("/npcImage/merchant_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("/npcImage/merchant_down_2", gp.tileSize, gp.tileSize);
        down1 = setup("/npcImage/merchant_down_2", gp.tileSize, gp.tileSize);
        down2 = setup("/npcImage/merchant_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/npcImage/merchant_down_2", gp.tileSize, gp.tileSize);
        left2 = setup("/npcImage/merchant_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("/npcImage/merchant_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/npcImage/merchant_down_2", gp.tileSize, gp.tileSize);
    }

    // Method to set initial dialogue for the NPC
    public void setDialogue() {
        dialogues[0] = "Hello, young man.\nYou are so doomed coming here in this\nisland. Luckily, I can help you survive.\nI have some good stuff. Do you want to buy?";
    }

    // Method to set NPC's random actions (change direction)
    public void setAction() {
        actionLockCounter++;
        // After a certain time (120 ticks), randomly change the NPC's direction
        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; // pick up a number from 1 to 100

            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }

    // Method to set initial items for the NPC's inventory
    public void setItems() {
        inventory.add(new OBJ_RifleBullet(gp));
        inventory.add(new OBJ_ShotgunBullet(gp));
        inventory.add(new OBJ_MinigunBullet(gp));
        inventory.add(new OBJ_Potion(gp));
    }

    // Method to initiate trading dialogue with the player
    public void speak() {
        super.speak();
        gp.gameState = gp.tradeState;
        gp.ui.npc = this;
    }
}
