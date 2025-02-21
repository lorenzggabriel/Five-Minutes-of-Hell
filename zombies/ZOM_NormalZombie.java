package zombies;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class ZOM_NormalZombie extends Entity { // Declare a class named ZOM_NormalZombie that extends the Entity class
	GamePanel gp; // Declare a variable of type GamePanel named gp

	// Constructor for ZOM_NormalZombie that takes a GamePanel parameter
	public ZOM_NormalZombie(GamePanel gp) {
		super(gp); // Call the constructor of the superclass (Entity) with the gp parameter

		this.gp = gp; // Set the gp variable of this class to the passed-in gp parameter

		type = type_zombie; // Set the type variable to the value of type_zombie (presumably defined
							// elsewhere)
		name = "Normal Zombie"; // Set the name variable to "Normal Zombie"
		speed = 2; // Set the speed variable to 2
		maxLife = 6; // Set the maxLife variable to 6
		life = maxLife; // Set the life variable to the value of maxLife

		// Set the coordinates and dimensions of the solidArea rectangle
		solidArea.x = 2;
		solidArea.y = 2;
		solidArea.width = 42;
		solidArea.height = 42;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		getImage(); // Call the getImage() method to load images for different directions
	}

	// Method to load images for different directions
	public void getImage() {
		// Call the setup() method to load images for different directions
		up = setup("/npcImage/zombie_up_stand", gp.tileSize, gp.tileSize);
		up1 = setup("/npcImage/zombie_up_left", gp.tileSize, gp.tileSize);
		up2 = setup("/npcImage/zombie_up_right", gp.tileSize, gp.tileSize);
		down = setup("/npcImage/zombie_down_stand", gp.tileSize, gp.tileSize);
		down1 = setup("/npcImage/zombie_down_left", gp.tileSize, gp.tileSize);
		down2 = setup("/npcImage/zombie_down_right", gp.tileSize, gp.tileSize);
		left = setup("/npcImage/zombie_left_stand", gp.tileSize, gp.tileSize);
		left1 = setup("/npcImage/zombie_left_left", gp.tileSize, gp.tileSize);
		left2 = setup("/npcImage/zombie_left_right", gp.tileSize, gp.tileSize);
		right = setup("/npcImage/zombie_right_stand", gp.tileSize, gp.tileSize);
		right1 = setup("/npcImage/zombie_right_left", gp.tileSize, gp.tileSize);
		right2 = setup("/npcImage/zombie_right_right", gp.tileSize, gp.tileSize);
	}

	// Method to update the state of the zombie
	public void update() {
		super.update(); // Call the update() method of the superclass (Entity)
		onPath = true; // Set the onPath variable to true
	}

	// Method to set the action of the zombie
	public void setAction() {
		if (onPath == true) { // Check if the zombie is on a path
			// Calculate the goal coordinates based on the player's position and solidArea
			int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
			int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;

			// Call the searchPath() method with the goal coordinates
			searchPath(goalCol, goalRow);
		} else {
			actionLockCounter++; // Increment the actionLockCounter variable

			if (actionLockCounter == 120) { // Check if actionLockCounter is equal to 120
				Random random = new Random(); // Create a new Random object
				int i = random.nextInt(100) + 1; // Generate a random number between 1 and 100

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

				actionLockCounter = 0; // Reset the actionLockCounter
			}
		}
	}
}