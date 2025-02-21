package entity;

import main.GamePanel;

// Projectile class, extending Entity
public class Projectile extends Entity {

	Entity user; // Reference to the user of the projectile

	// Constructor for Projectile, taking a GamePanel as a parameter
	public Projectile(GamePanel gp) {
		super(gp); // Call the constructor of the superclass (Entity)
		// TODO Auto-generated constructor stub
	}

	// Method to set initial values of the projectile
	public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
		this.worldX = worldX; // Set world X coordinate
		this.worldY = worldY; // Set world Y coordinate
		this.direction = direction; // Set the direction of the projectile
		this.alive = alive; // Set whether the projectile is alive
		this.user = user; // Set the user of the projectile
		this.life = this.maxLife; // Set the life of the projectile to its maximum
	}

	// Method to update the state of the projectile
	public void update() {
		if (user == gp.player) { // If the user is the player
			int zombieIndex = gp.cChecker.checkEntity(this, gp.zombie); // Check if the projectile hits a zombie
			if (zombieIndex != 999) { // If the projectile hits a zombie
				gp.player.damageZombie(zombieIndex, attack); // Damage the zombie
				alive = false; // The projectile will disappear once it hit a zombie
			}
		}

		// If the user is not the player (additional logic can be added here)
		if (user != gp.player) {
		}

		// Move the projectile based on its direction
		switch (direction) {
			case "up":
				worldY -= speed;
				break;
			case "down":
				worldY += speed;
				break;
			case "left":
				worldX -= speed;
				break;
			case "right":
				worldX += speed;
				break;
		}

		life--; // Decrease the life of the projectile
		if (life <= 0) { // If the life of the projectile reaches or falls below zero, mark it as not
							// alive
			alive = false;
		}

		// Update the sprite animation counters
		spriteCounter++;
		if (spriteCounter > 12) {
			if (spriteNum == 1) {
				spriteNum = 2;
			} else if (spriteNum == 2) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
	}
}
