// CREDITS TO: RyiSnow for the image borrowing of the old man
// Just for extra.

package entity;

import java.awt.Rectangle;
import java.util.Random;
import main.GamePanel;

// Definition of the NPC_OldMan class, which extends the Entity class
public class NPC_OldMan extends Entity {

    // Constructor for NPC_OldMan, taking a GamePanel instance as a parameter
    public NPC_OldMan(GamePanel gp) {
        // Call the constructor of the superclass (Entity)
        super(gp);

        // Set the type of the entity to type_npc
        type = type_npc;

        // Set the initial direction of the NPC to "down" and speed to 1 pixel
        direction = "down";
        speed = 1;

        // Create a Rectangle to represent the solid area of the NPC
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        // Load images for different directions
        getImage();

        // Set the initial dialogue for the NPC
        setDialogue();
    }

    // Method to load images for different directions
    public void getImage() {
        up1 = setup("/npcImage/oldman_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/npcImage/oldman_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/npcImage/oldman_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/npcImage/oldman_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/npcImage/oldman_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/npcImage/oldman_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/npcImage/oldman_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/npcImage/oldman_right_2", gp.tileSize, gp.tileSize);
    }

    // Method to set the NPC's action
    public void setAction() {
        // Increment the action lock counter
        actionLockCounter++;

        // If the action lock counter reaches 120, perform the action
        if (actionLockCounter == 120) {
            // Generate a random number between 1 and 100
            Random random = new Random();
            int i = random.nextInt(100) + 1;

            // Determine the direction based on the random number
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

            // Reset the action lock counter
            actionLockCounter = 0;
        }
    }

    // Method to set the initial dialogues for the NPC
    public void setDialogue() {
        dialogues[0] = "Hello, lad.";
        dialogues[1] = "How's Raccoon City?";
        dialogues[2] = "Zombie Dogs?! Tyrant? ";
        dialogues[3] = "Well, I guess I'll just die.";
    }
}
