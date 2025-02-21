package zombies;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

// Define a class named ZOM_StrongZombie that extends the Entity class
public class ZOM_StrongZombie extends Entity {
	// Declare a GamePanel variable to store a reference to the game panel
	GamePanel gp;

	// Constructor for ZOM_StrongZombie that takes a GamePanel as a parameter
	public ZOM_StrongZombie(GamePanel gp) {
		// Call the constructor of the superclass (Entity) and pass the GamePanel
		// parameter
		super(gp);

		// Assign the GamePanel parameter to the local variable
		this.gp = gp;

		// Set properties for the zombie entity
		type = type_zombie;
		name = "Strong Zombie";
		speed = 1;
		maxLife = 8;
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
		// Set up images for different directions using the setup method
		up = setup("/npcImage/strong_zombie_up_stand", gp.tileSize, gp.tileSize);
		up1 = setup("/npcImage/strong_zombie_up_left", gp.tileSize, gp.tileSize);
		up2 = setup("/npcImage/strong_zombie_up_right", gp.tileSize, gp.tileSize);
		down = setup("/npcImage/strong_zombie_down_stand", gp.tileSize, gp.tileSize);
		down1 = setup("/npcImage/strong_zombie_down_left", gp.tileSize, gp.tileSize);
		down2 = setup("/npcImage/strong_zombie_down_right", gp.tileSize, gp.tileSize);
		left = setup("/npcImage/strong_zombie_left_stand", gp.tileSize, gp.tileSize);
		left1 = setup("/npcImage/strong_zombie_left_left", gp.tileSize, gp.tileSize);
		left2 = setup("/npcImage/strong_zombie_left_right", gp.tileSize, gp.tileSize);
		right = setup("/npcImage/strong_zombie_right_stand", gp.tileSize, gp.tileSize);
		right1 = setup("/npcImage/strong_zombie_right_left", gp.tileSize, gp.tileSize);
		right2 = setup("/npcImage/strong_zombie_right_right", gp.tileSize, gp.tileSize);
	}

	// Method to update the zombie's state
	public void update() {
		// Call the update method of the superclass (Entity)
		super.update();

		// Set the zombie's state to be on a path
		onPath = true;
	}

	// Method to set the zombie's action
	public void setAction() {
		// Check if the zombie is on a path
		if (onPath == true) {
			// Calculate the goal column and row based on the player's position
			int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
			int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;

			// Search for a path to the goal
			searchPath(goalCol, goalRow);
		} else {
			// Increment the action lock counter
			actionLockCounter++;

			// Check if the action lock counter has reached a certain threshold
			if (actionLockCounter == 120) {
				// Generate a random number between 1 and 100
				Random random = new Random();
				int i = random.nextInt(100) + 1;

				// Change the zombie's direction based on the random number
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