package main;

import java.util.Random;

import object.OBJ_MinigunBullet;
import object.OBJ_PistolBullet;
import object.OBJ_RifleBullet;
import object.OBJ_ShotgunBullet;

public class EventHandler {
	GamePanel gp;
	EventRect eventRect[][];

	// Constructor
	public EventHandler(GamePanel gp) {
		this.gp = gp;
		eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];

		// Initialize event rectangles for each world column and row
		int col = 0;
		int row = 0;
		while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
			// Size of the interactable rectangle
			eventRect[col][row] = new EventRect();
			eventRect[col][row].x = 23;
			eventRect[col][row].y = 23;
			eventRect[col][row].width = 2;
			eventRect[col][row].height = 2;
			eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
			eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

			col++;
			if (col == gp.maxWorldCol) {
				col = 0;
				row++;
			}
		}
	}

	// Method to check events
	public void checkEvent() {
		// This can still be modified
		// Check for a specific event (loot box in this case)
		if (hit(27, 15, "right") == true) {
			lootBox(27, 15, gp.dialogueState);
		}
	}

	// Method to check if the player hits an event
	public boolean hit(int col, int row, String reqDirection) {
		boolean hit = false;

		// Player's current solid area positions
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

		// Event's solid area position
		eventRect[col][row].x = col * gp.tileSize + eventRect[col][row].x;
		eventRect[col][row].y = row * gp.tileSize + eventRect[col][row].y;

		// Collision checker
		if (gp.player.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false) {
			if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
				hit = true;
			}
		}

		// Rest the solid area
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
		eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

		return hit;
	}

	// Method to handle loot box event
	public void lootBox(int col, int row, int gameState) {
		Random random = new Random(); // Get a random number to get a random gun
		// Generate a random integer between 1 and 10 (inclusive)
		int random_number = random.nextInt(20) + 1;

		// Check if the enter key is pressed to collect the loot
		if (gp.keyH.enterPressed == true) {
			gp.gameState = gameState;
			gp.ui.currentDialogue = "You collected a random gun!";
			eventRect[col][row].eventDone = true;

			// Determine the type of gun based on the random number
			if (random_number <= 15) {
				gp.player.inventory.add(new OBJ_PistolBullet(gp));
			} else if (random_number > 15 && random_number <= 18) {
				gp.player.inventory.add(new OBJ_RifleBullet(gp));
			} else if (random_number == 19) {
				gp.player.inventory.add(new OBJ_ShotgunBullet(gp));
			} else if (random_number == 20) {
				gp.player.inventory.add(new OBJ_MinigunBullet(gp));
			}
		}
	}
}
