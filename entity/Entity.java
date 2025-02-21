// CREDITS TO: RyiSnow for the base code implementation of path finding of the Entities(Zombies)

package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

// This is the superclass of all the entity of the game (from player to NPCs)
public class Entity {
    GamePanel gp; // Reference to the GamePanel
    public BufferedImage up, up1, up2, down, down1, down2, left, left1, left2, right, right1, right2; // BufferedImage
                                                                                                      // for the
                                                                                                      // player's
                                                                                                      // sprites while
                                                                                                      // moving or
                                                                                                      // standing
    public BufferedImage image, image2, image3, weapon; // Image variables
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // Rectangle defining the solid area of the entity
    public int solidAreaDefaultX, solidAreaDefaultY;
    String dialogues[] = new String[20]; // Dialogues for the entity
    int dialogueIndex = 0;

    // STATE
    public int worldX, worldY;
    public String direction = "down";
    public int spriteNum = 1;
    public boolean collisionOn = false;
    public boolean invincible = false;
    public boolean alive = true;
    public boolean dying = false;
    public boolean onPath = false;

    // COUNTER
    public int spriteCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    public int pistolShotAvailableCounter = 0;
    public int shotgunShotAvailableCounter = 0;
    public int rifleShotAvailableCounter = 0;
    public int minigunShotAvailableCounter = 0;
    int dyingCounter = 0;

    // Entity's name and collision flag
    public String name;
    public boolean collision = false;

    // CHARACTER ATTRIBUTES
    public int speed;
    public int maxLife;
    public int life;
    public int strength;
    public int attack;
    public Projectile projectile[] = new Projectile[40];
    public int playerPoints;
    public int coin;
    public Entity currentWeapon;

    // ITEM ATTRIBUTES
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;
    public int value;
    public String description = "";
    public int price;

    // TYPE
    public int type; // type of entity
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_zombie = 2;
    public final int type_consumable = 3;
    public final int type_pickupOnly = 4;
    public final int type_noWeapon = 5;
    public final int type_pistol = 6;
    public final int type_shotgun = 7;
    public final int type_rifle = 8;
    public final int type_minigun = 9;

    // PERK STATE
    public final int perk_red = 1;
    public final int perk_yellow = 2;
    public final int perk_green = 3;
    public int maxPerk = 0;
    public int perkTimer = 5;
    public boolean perkAvailable = false;

    // Constructor
    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    // Method to set the action of the entity
    public void setAction() {
    }

    // Method for the entity to speak
    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;
    }

    // Method for the entity to use an item
    public void use(Entity entity) {
    }

    // Method for checking collision with tiles, objects, zombies and players
    public void checkCollision() {

        collisionOn = false; // default
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, true);
        gp.cChecker.checkPlayer(this);

        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        // If the entity is a zombie and has collision with the player, the player loses
        // life.
        if (this.type == type_zombie && contactPlayer == true) {
            if (gp.player.invincible == false) {
                gp.player.life -= 2;
                gp.player.invincible = true;
            }
        }

    }

    // Update method for the entity
    public void update() {
        setAction();
        checkCollision();

        // IF COLLLISION IS FALSE, PLAYER CAN MOVE
        // If there is no collision, the entity can move based on its direction
        if (collisionOn == false) {
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
        }

        // Update sprite animation
        spriteCounter++;
        if (spriteCounter > 12) { // Player image changes in every 12 frames
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    // Draw method for the entity
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        // Calculate the screen position of the entity relative to the player
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // Check if the entity is within the visible screen boundaries
        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
                && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) { // we create a boundary

            // Choose the appropriate sprite based on the direction
            switch (direction) {
                case "up":
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                    break;
                case "down":
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                    break;
                case "left":
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                    break;
                case "right":
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                    break;
            }

            // Zombie Health
            // Display health bar for zombies
            if (type == 2) {

                // Scale of the healthbar depending on the health of the zombie
                double oneScale = (double) gp.tileSize / maxLife;
                double hpBarValue = oneScale * life;
                
                // Border for the healthbar
                g2.setColor(new Color(35, 35, 35));
                g2.fillRect(screenX - 1, screenY - 16, gp.tileSize + 2, 12);
                
                // Color and shape for the healthbar
                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(screenX, screenY - 15, (int) hpBarValue, 10);
            }

            // If the entity is dying, perform a dying animation
            if (dying == true) {
                dyingAnimation(g2);
            }

            // Draw the entity's image
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            // Reset alpha for drawing
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }

    // Method in which how long the zombie will blink before disappearing
    // Dying animation for the entity
    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;
        int i = 10;

        if (dyingCounter <= i) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i && dyingCounter <= i * 2) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 2) {
            dying = false;
            alive = false;
        }

    }

    // Method to change alpha value for drawing
    public void changeAlpha(Graphics2D g2, float alphaValue) {
    	// This line of code sets the composite (blending) mode for the graphics context g2. 
    	// The AlphaComposite class is used to control how the alpha (transparency) values of 
    	// pixels are combined when rendering graphics.
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    // Method for getting the image from a file path for the entities
    public BufferedImage setup(String imagePath, int width, int height) {
    	// Create an instance of the UtilityTool class for utility functions
        UtilityTool uTool = new UtilityTool();
        
        // Initialize BufferedImage to null
        BufferedImage image = null;

        try {
        	 // Read the image from the specified file path with the ".png" extension
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            
            // Scale the image to the specified width and height using the utility method
            image = uTool.scaleImage(image, width, height);
        } catch (IOException e) {
        	// Handle IOException if there is an issue reading the image
            e.printStackTrace();
        }

        return image;
    }

    // Method to search for a path to a goal position
    public void searchPath(int goalCol, int goalRow) {

        int startCol = (worldX + solidArea.x) / gp.tileSize;
        int startRow = (worldY + solidArea.y) / gp.tileSize;

        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);

        if (gp.pFinder.search() == true) {

            // Next worldX & worldY
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

            // Entity's solidArea position
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            // Determine the direction based on the next path point
            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "up";
            } else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "down";
            } else if (enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
                // left or right
                if (enLeftX > nextX) {
                    direction = "left";
                }
                if (enLeftX < nextX) {
                    direction = "right";
                }
            } else if (enTopY > nextY && enLeftX > nextX) {
                // up or left
                direction = "up";
                checkCollision();
                if (collisionOn == true) {
                    direction = "left";
                }
            } else if (enTopY > nextY && enLeftX < nextX) {
                // up or right
                direction = "up";
                checkCollision();
                if (collisionOn == true) {
                    direction = "right";
                }
            } else if (enTopY < nextY && enLeftX > nextX) {
                // down or left
                direction = "down";
                checkCollision();
                if (collisionOn == true) {
                    direction = "left";
                }
            } else if (enTopY < nextY && enLeftX < nextX) {
                // down or right
                direction = "down";
                checkCollision();
                if (collisionOn == true) {
                    direction = "right";
                }
            }
        }
    }
}
