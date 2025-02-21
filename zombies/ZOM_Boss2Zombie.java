package zombies;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

// Define a class named ZOM_Boss2Zombie, which extends the Entity class
public class ZOM_Boss2Zombie extends Entity {
	GamePanel gp; // Declare a variable of type GamePanel to store the game panel reference

	// Constructor for ZOM_Boss2Zombie, takes a GamePanel as a parameter
	public ZOM_Boss2Zombie(GamePanel gp) {
		super(gp); // Call the constructor of the superclass (Entity)

		this.gp = gp; // Set the game panel reference in the instance variable

		type = type_zombie; // Set the type of the entity to "zombie"
		name = "Boss2 Zombie"; // Set the name of the entity to "Boss2 Zombie"
		speed = 1; // Set the speed of the entity to 1
		maxLife = 200; // Set the maximum life of the entity to 200
		life = maxLife; // Set the current life of the entity to the maximum life

		// Set the solid area (collision area) of the entity
		solidArea.x = 2;
		solidArea.y = 2;
		solidArea.width = 42;
		solidArea.height = 42;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		getImage(); // Call the getImage method to load images for different directions
	}

	// Method to load images for different directions
	public void getImage() {
		// Load images for each direction using the setup method
		up = setup("/npcImage/boss2_up_stand", gp.tileSize, gp.tileSize);
		up1 = setup("/npcImage/boss2_up_left", gp.tileSize, gp.tileSize);
		up2 = setup("/npcImage/boss2_up_right", gp.tileSize, gp.tileSize);
		down = setup("/npcImage/boss2_down_stand", gp.tileSize, gp.tileSize);
		down1 = setup("/npcImage/boss2_down_left", gp.tileSize, gp.tileSize);
		down2 = setup("/npcImage/boss2_down_right", gp.tileSize, gp.tileSize);
		left = setup("/npcImage/boss2_left_stand", gp.tileSize, gp.tileSize);
		left1 = setup("/npcImage/boss2_left_left", gp.tileSize, gp.tileSize);
		left2 = setup("/npcImage/boss2_left_right", gp.tileSize, gp.tileSize);
		right = setup("/npcImage/boss2_right_stand", gp.tileSize, gp.tileSize);
		right1 = setup("/npcImage/boss2_right_left", gp.tileSize, gp.tileSize);
		right2 = setup("/npcImage/boss2_right_right", gp.tileSize, gp.tileSize);
	}

	// Method to update the entity's state
	public void update() {
		super.update(); // Call the update method of the superclass

		onPath = true; // Set the onPath flag to true
	}

	// Method to set the action of the entity
	public void setAction() {
		// Check if the entity is on a path
		if (onPath == true) {
			// Calculate the goal column and row based on the player's position
			int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
			int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;

			// Call the searchPath method to find a path to the goal
			searchPath(goalCol, goalRow);
		} else {
			// If not on a path, increment the action lock counter
			actionLockCounter++;

			// Check if the action lock counter reaches 120
			if (actionLockCounter == 120) {
				// Generate a random number between 1 and 100
				Random random = new Random();
				int i = random.nextInt(100) + 1;

				// Based on the random number, set the direction of the entity
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
	}
}