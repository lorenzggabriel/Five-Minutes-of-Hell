package zombies;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

// Define a class named ZOM_Boss1Zombie that extends the Entity class
public class ZOM_Boss1Zombie extends Entity {

	// Declare a GamePanel object
	GamePanel gp;

	// Constructor for ZOM_Boss1Zombie that takes a GamePanel parameter
	public ZOM_Boss1Zombie(GamePanel gp) {
		// Call the superclass constructor with the GamePanel parameter
		super(gp);

		// Initialize the GamePanel object
		this.gp = gp;

		// Set attributes for the zombie entity
		type = type_zombie;
		name = "Boss1 Zombie";
		speed = 1;
		maxLife = 100;
		life = maxLife;

		// Set the solid area dimensions for collision detection
		solidArea.x = 2;
		solidArea.y = 2;
		solidArea.width = 42;
		solidArea.height = 42;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		// Load images for different directions
		getImage();
	}

	// Method to load images for different directions
	public void getImage() {
		// Set up images for different directions using file paths and tile size
		up = setup("/npcImage/boss1_up_stand", gp.tileSize, gp.tileSize);
		up1 = setup("/npcImage/boss1_up_left", gp.tileSize, gp.tileSize);
		up2 = setup("/npcImage/boss1_up_right", gp.tileSize, gp.tileSize);
		down = setup("/npcImage/boss1_down_stand", gp.tileSize, gp.tileSize);
		down1 = setup("/npcImage/boss1_down_left", gp.tileSize, gp.tileSize);
		down2 = setup("/npcImage/boss1_down_right", gp.tileSize, gp.tileSize);
		left = setup("/npcImage/boss1_left_stand", gp.tileSize, gp.tileSize);
		left1 = setup("/npcImage/boss1_left_left", gp.tileSize, gp.tileSize);
		left2 = setup("/npcImage/boss1_left_right", gp.tileSize, gp.tileSize);
		right = setup("/npcImage/boss1_right_stand", gp.tileSize, gp.tileSize);
		right1 = setup("/npcImage/boss1_right_left", gp.tileSize, gp.tileSize);
		right2 = setup("/npcImage/boss1_right_right", gp.tileSize, gp.tileSize);
	}

	// Method to update the zombie's state
	public void update() {
		// Call the superclass update method
		super.update();

		// Set the flag indicating the zombie is on a path to true
		onPath = true;
	}

	// Method to set the zombie's action
	public void setAction() {

		// Check if the zombie is on a path
		if (onPath == true) {
			// Calculate the goal column and row based on the player's position
			int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
			int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;

			// Search for a path to the goal column and row
			searchPath(goalCol, goalRow);

		} else {
			// Increment the action lock counter
			actionLockCounter++;

			// Check if the action lock counter has reached 120 (change direction condition)
			if (actionLockCounter == 120) {
				// Generate a random number between 1 and 100
				Random random = new Random();
				int i = random.nextInt(100) + 1;

				// Set the zombie's direction based on the random number
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