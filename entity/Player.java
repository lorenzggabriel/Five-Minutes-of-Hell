package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

import main.GamePanel;
import main.KeyHandler;
import object.OBJ_EmptyWeapon;
import object.OBJ_MinigunBullet;
import object.OBJ_PistolBullet;
import object.OBJ_RifleBullet;
import object.OBJ_ShotgunBullet;

// Definition of the 'Player' class, which extends 'Entity'
public class Player extends Entity {
    public KeyHandler keyH; // Declaration of member variable 'keyH' of type 'KeyHandler' to handle keyboard
                            // input

    // Declaration of constant variables 'screenX' and 'screenY'
    public final int screenX;
    public final int screenY;

    // Constructor for the 'Player' class, taking a 'GamePanel' and 'KeyHandler' as
    // parameters
    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp); // calling the constructor of the its superclass; Entity class is abstract class
                   // has no instance
        this.keyH = keyH; // Initializing member variables with default values

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        // Calling methods to set default values, get player image, and set items
        setDefaultValues();
        getPlayerImage();
        setItems();
    }

    // Method to set default values for the player
    public void setDefaultValues() {
        // set player's default position
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;

        speed = 4; // pixels
        direction = "down";

        // PLAYER STATUS
        maxLife = 6;
        life = maxLife;
        coin = 50;
        playerPoints = 0;
        currentWeapon = new OBJ_EmptyWeapon(gp); // Setting the default weapon to an empty weapon
        attack = currentWeapon.attack;

        // Initializing an array of projectiles with different types
        for (int i = 0; i < projectile.length; i++) { // Store different projectile in an array
        	// This is done in order to spawn specific number of projectiles while a projectile is still alive
        	// 10 projectiles per weapon (It will be enough since the max frames before a projectile disappear is 40 frames)
            if (i < 10) {
                projectile[i] = new OBJ_PistolBullet(gp); 
            } else if (i >= 10 && i < 20) {
                projectile[i] = new OBJ_ShotgunBullet(gp);
            } else if (i >= 20 && i < 30) {
                projectile[i] = new OBJ_RifleBullet(gp);
            } else {
                projectile[i] = new OBJ_MinigunBullet(gp);
            }

        }
    }

    // Method to set default positions for the player
    public void setDefaultPositions() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        direction = "down";
    }

    // Method to restore the player's life to the maximum value
    public void restoreLife() {
        life = maxLife;
        invincible = false;
    }

    // Method to set the player's items
    public void setItems() {
        inventory.clear();
        inventory.add(new OBJ_PistolBullet(gp));
    }

    // Method to get the default image of the player
    public void getPlayerImage() {
        up = setup("/playerImage/up_stand", gp.tileSize, gp.tileSize);
        up1 = setup("/playerImage/up_left", gp.tileSize, gp.tileSize);
        up2 = setup("/playerImage/up_right", gp.tileSize, gp.tileSize);
        down = setup("/playerImage/down_stand", gp.tileSize, gp.tileSize);
        down1 = setup("/playerImage/down_left", gp.tileSize, gp.tileSize);
        down2 = setup("/playerImage/down_right", gp.tileSize, gp.tileSize);
        left = setup("/playerImage/left_stand", gp.tileSize, gp.tileSize);
        left1 = setup("/playerImage/left_left", gp.tileSize, gp.tileSize);
        left2 = setup("/playerImage/left_right", gp.tileSize, gp.tileSize);
        right = setup("/playerImage/right_stand", gp.tileSize, gp.tileSize);
        right1 = setup("/playerImage/right_left", gp.tileSize, gp.tileSize);
        right2 = setup("/playerImage/right_right", gp.tileSize, gp.tileSize);
    }

    // Method to set a new player image based on the equipped weapon
    public void setNewPlayerImage() {
        if (currentWeapon.type == type_pistol) {
            up = setup("/playerImage/pistol_up_stand", gp.tileSize, gp.tileSize);
            up1 = setup("/playerImage/pistol_up_left", gp.tileSize, gp.tileSize);
            up2 = setup("/playerImage/pistol_up_right", gp.tileSize, gp.tileSize);
            down = setup("/playerImage/pistol_down_stand", gp.tileSize, gp.tileSize);
            down1 = setup("/playerImage/pistol_down_left", gp.tileSize, gp.tileSize);
            down2 = setup("/playerImage/pistol_down_right", gp.tileSize, gp.tileSize);
            left = setup("/playerImage/pistol_left_stand", gp.tileSize, gp.tileSize);
            left1 = setup("/playerImage/pistol_left_left", gp.tileSize, gp.tileSize);
            left2 = setup("/playerImage/pistol_left_right", gp.tileSize, gp.tileSize);
            right = setup("/playerImage/pistol_right_stand", gp.tileSize, gp.tileSize);
            right1 = setup("/playerImage/pistol_right_left", gp.tileSize, gp.tileSize);
            right2 = setup("/playerImage/pistol_right_right", gp.tileSize, gp.tileSize);
        } else if (currentWeapon.type == type_shotgun) {
            up = setup("/playerImage/shotgun_up_stand", gp.tileSize, gp.tileSize);
            up1 = setup("/playerImage/shotgun_up_left", gp.tileSize, gp.tileSize);
            up2 = setup("/playerImage/shotgun_up_right", gp.tileSize, gp.tileSize);
            down = setup("/playerImage/shotgun_down_stand", gp.tileSize, gp.tileSize);
            down1 = setup("/playerImage/shotgun_down_left", gp.tileSize, gp.tileSize);
            down2 = setup("/playerImage/shotgun_down_right", gp.tileSize, gp.tileSize);
            left = setup("/playerImage/shotgun_left_stand", gp.tileSize, gp.tileSize);
            left1 = setup("/playerImage/shotgun_left_left", gp.tileSize, gp.tileSize);
            left2 = setup("/playerImage/shotgun_left_right", gp.tileSize, gp.tileSize);
            right = setup("/playerImage/shotgun_right_stand", gp.tileSize, gp.tileSize);
            right1 = setup("/playerImage/shotgun_right_left", gp.tileSize, gp.tileSize);
            right2 = setup("/playerImage/shotgun_right_right", gp.tileSize, gp.tileSize);
        } else if (currentWeapon.type == type_rifle) {
            up = setup("/playerImage/rifle_up_stand", gp.tileSize, gp.tileSize);
            up1 = setup("/playerImage/rifle_up_left", gp.tileSize, gp.tileSize);
            up2 = setup("/playerImage/rifle_up_right", gp.tileSize, gp.tileSize);
            down = setup("/playerImage/rifle_down_stand", gp.tileSize, gp.tileSize);
            down1 = setup("/playerImage/rifle_down_left", gp.tileSize, gp.tileSize);
            down2 = setup("/playerImage/rifle_down_right", gp.tileSize, gp.tileSize);
            left = setup("/playerImage/rifle_left_stand", gp.tileSize, gp.tileSize);
            left1 = setup("/playerImage/rifle_left_left", gp.tileSize, gp.tileSize);
            left2 = setup("/playerImage/rifle_left_right", gp.tileSize, gp.tileSize);
            right = setup("/playerImage/rifle_right_stand", gp.tileSize, gp.tileSize);
            right1 = setup("/playerImage/rifle_right_left", gp.tileSize, gp.tileSize);
            right2 = setup("/playerImage/rifle_right_right", gp.tileSize, gp.tileSize);
        } else if (currentWeapon.type == type_minigun) {
            up = setup("/playerImage/minigun_up_stand", gp.tileSize, gp.tileSize);
            up1 = setup("/playerImage/minigun_up_left", gp.tileSize, gp.tileSize);
            up2 = setup("/playerImage/minigun_up_right", gp.tileSize, gp.tileSize);
            down = setup("/playerImage/minigun_down_stand", gp.tileSize, gp.tileSize);
            down1 = setup("/playerImage/minigun_down_left", gp.tileSize, gp.tileSize);
            down2 = setup("/playerImage/minigun_down_right", gp.tileSize, gp.tileSize);
            left = setup("/playerImage/minigun_left_stand", gp.tileSize, gp.tileSize);
            left1 = setup("/playerImage/minigun_left_left", gp.tileSize, gp.tileSize);
            left2 = setup("/playerImage/minigun_left_right", gp.tileSize, gp.tileSize);
            right = setup("/playerImage/minigun_right_stand", gp.tileSize, gp.tileSize);
            right1 = setup("/playerImage/minigun_right_left", gp.tileSize, gp.tileSize);
            right2 = setup("/playerImage/minigun_right_right", gp.tileSize, gp.tileSize);
        }
    }

    // Method to update the player's state; called 60 times per second
    public void update() { // this is called 60 times per second

        if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true
                || keyH.rightPressed == true) { // sprite counter increases only when you are pressing one of these
                                                // keys, so the player sprite doesn't change unless you press a key
            if (keyH.upPressed == true) {
                direction = "up";

            } else if (keyH.downPressed == true) {
                direction = "down";
            } else if (keyH.leftPressed == true) {
                direction = "left";
            } else if (keyH.rightPressed == true) {
                direction = "right";
            }

            // CHECKING THE TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this); // receive player class as entity

            // CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            if (maxPerk != 1) {
                pickUpObject(objIndex);
            }
            
            // CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // CHECK MONSTER COLLISION
            int zombieIndex = gp.cChecker.checkEntity(this, gp.zombie);
            damageZombie(zombieIndex, attack);

            // CHECK EVENT
            gp.eHandler.checkEvent();

            gp.keyH.enterPressed = false;

            // IF COLLLISION IS FALSE, PLAYER CAN MOVE
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

            spriteCounter++;
            if (spriteCounter > 12) { // player image changes in every 12 frames
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

        if (gp.player.currentWeapon != null) {
            for (int i = 0; i < projectile.length; i++) { // Access specific projectiles

                // projectile.alive is a condition in order to spawn projectiles even if
            	// a projectile is currently alive in the screen
            	
            	// By using projectile[i].alive == false, it will only spawn the projectiles in the array that
            	// are currently not alive
            	
            	// Spawn pistol projectile 
                if (gp.player.currentWeapon.type == type_pistol && gp.keyH.shotKeyPressed == true
                        && projectile[i].alive == false && pistolShotAvailableCounter == 15 && i < 10) {

                    // SET DEFAULT COORDINATES AND DIRECTION OF THE PROJECTILE
                    projectile[i].set(worldX, worldY, direction, true, this);

                    // ADD IT TO THE LIST
                    gp.projectileList.add(projectile[i]);
                    
                    // RESET THE COUNTER
                    pistolShotAvailableCounter = 0;
                    
                    gp.playSE(7);
                  
                } 
                
                // Spawn shotgun projectile 
                else if (gp.player.currentWeapon.type == type_shotgun && gp.keyH.shotKeyPressed == true
                        && projectile[i].alive == false && shotgunShotAvailableCounter == 20 && i >= 10 && i < 20) {
                	
                	// SET DEFAULT COORDINATES AND DIRECTION OF THE PROJECTILE
                    projectile[i].set(worldX, worldY, direction, true, this);

                    // ADD IT TO THE LIST
                    gp.projectileList.add(projectile[i]);
                    
                    // RESET THE COUNTER
                    shotgunShotAvailableCounter = 0;
                    
                    gp.playSE(8);

                }
                
                // Spawn rifle projectile 
                else if (gp.player.currentWeapon.type == type_rifle && gp.keyH.shotKeyPressed == true
                        && projectile[i].alive == false && rifleShotAvailableCounter == 10 && i >= 20 && i < 30) {

                	// SET DEFAULT COORDINATES AND DIRECTION OF THE PROJECTILE
                    projectile[i].set(worldX, worldY, direction, true, this);

                    // ADD IT TO THE LIST
                    gp.projectileList.add(projectile[i]);
                    
                    // RESET THE COUNTER
                    rifleShotAvailableCounter = 0;
                    
                    gp.playSE(9);

                }
                
                // Spawn minigun projectile 
                else if (gp.player.currentWeapon.type == type_minigun && gp.keyH.shotKeyPressed == true
                        && projectile[i].alive == false && minigunShotAvailableCounter == 5 && i >= 30 && i < 40) {

                	// SET DEFAULT COORDINATES AND DIRECTION OF THE PROJECTILE
                    projectile[i].set(worldX, worldY, direction, true, this);

                    // ADD IT TO THE LIST
                    gp.projectileList.add(projectile[i]);
                    
                    // RESET THE COUNTER
                    minigunShotAvailableCounter = 0;
                    
                    gp.playSE(10);
                }
            }
        }

        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 60) { // Time before player can take damage again
                invincible = false;
                invincibleCounter = 0;
            }
        }
        
        // Frames before you can shoot again 
        
        if (pistolShotAvailableCounter < 15) { 
            pistolShotAvailableCounter++;
        }

        if (shotgunShotAvailableCounter < 20) {
            shotgunShotAvailableCounter++;
        }

        if (rifleShotAvailableCounter < 10) { 
            rifleShotAvailableCounter++;
        }

        if (minigunShotAvailableCounter < 5) { 
            minigunShotAvailableCounter++;
        }
        
        // Player has 0 life (Game Over)
        if (life <= 0) {
            gp.gameState = gp.gameOverState;
            if (gp.player.currentWeapon != null) {
                gp.player.currentWeapon.type = type_noWeapon;
            }
            gp.player.inventory.clear();
            getPlayerImage();
            gp.stopMusic();
            gp.playSE(5);
        }
        
        // Timer has reached the Time for the game to end
        if (gp.gameState != gp.gameOverState && gp.gameTimer == gp.endGameTimer) {
            gp.gameState = gp.winningState;
            gp.stopMusic();
            gp.playSE(4);
        }

    }

    // Method to handle picking up objects in the game
    public void pickUpObject(int i) {
        if (i != 999) { // any number is fine; this means if we didnt touch the object
            String objectName = gp.obj[i].name;

            // Will not be used in the game since there are few items only.
            // String text;

            // if (inventory.size() != maxInventorySize) {
            // inventory.add(gp.obj[i]);
            // gp.playSE(1);
            // text = "Got a " + gp.obj[i].name + "!";
            // } else {
            // text = "You cannot carry anymore!";
            // }

            gp.obj[i] = null;

            switch (objectName) {
                case "RedStar": // Infinite health for five seconds
                    life = 6;
                    System.out.println("RedStar");
                    perkAvailable = true;
                    gp.obj[i] = null; // makes it disappear after getting the perk
                    maxPerk = 1;
                    gp.perkState = perk_red;
                    perkTimer = 5;
                    gp.playSE(2);
                    break;

                case "GreenStar": // Speed goes up for five seconds
                    speed += 2;
                    perkAvailable = true;
                    gp.obj[i] = null; // makes it disappear after getting the perk
                    maxPerk = 1;
                    gp.perkState = perk_green;
                    perkTimer = 5;
                    gp.playSE(2);
                    break;

                case "YellowStar": // Double points for five seconds
                    perkAvailable = true;
                    gp.obj[i] = null; // makes it disappear after getting the perk
                    maxPerk = 1;
                    System.out.println("YellowStar");
                    gp.perkState = perk_yellow;
                    perkTimer = 5;
                    gp.playSE(2);
                    break;

                default:
                    break;
            }
        }
    }

    // Method to handle interacting with NPCs in the game
    public void interactNPC(int i) {
        if (i != 999) {
            System.out.println("Collision detected"); // If the player and the NPC has a collision
            if (gp.keyH.enterPressed == true) {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }
    }

    // Method to handle damaging zombies in the game
    public void damageZombie(int i, int attack) {
        if (i != 999) {
            int damage = attack;
            gp.zombie[i].life -= damage;
            if (gp.zombie[i].life <= 0) {
                gp.zombie[i].dying = true;
                
                // Get different amount of points and coins when killing different type of zombie
                if (gp.zombie[i].maxLife == 8) {
                    gp.player.playerPoints += 250;
                    gp.player.coin += 10;
                } else if (gp.zombie[i].maxLife == 6) {
                    gp.player.playerPoints += 100;
                    gp.player.coin += 5;
                } else if (gp.zombie[i].maxLife == 100) {
                    gp.player.playerPoints += 1000;
                    gp.player.coin += 300;
                } else if (gp.zombie[i].maxLife == 200) {
                    gp.player.playerPoints += 2000;
                    gp.player.coin += 400;
                }
            }
        }
    }

    // Method to select an item in the player's inventory
    public void selectItem() {
        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);

        if (itemIndex < inventory.size()) {
            Entity selectedItem = inventory.get(itemIndex);

            if (selectedItem.type == type_pistol) {
                currentWeapon = selectedItem;
                setNewPlayerImage(); // Update the player's image in the game after equipping the weapon
            } else if (selectedItem.type == type_rifle) {
                currentWeapon = selectedItem;
                setNewPlayerImage();
            } else if (selectedItem.type == type_shotgun) {
                currentWeapon = selectedItem;
                setNewPlayerImage();
            } else {
                currentWeapon = selectedItem;
                setNewPlayerImage();

            }

            // If the selected item is consumable or not a weapon
            if (selectedItem.type == type_consumable) {
                selectedItem.use(this);
                inventory.remove(itemIndex);
            }
        }
    }

    // Method to draw the player on the screen
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        if (!keyH.upPressed && !keyH.downPressed && !keyH.leftPressed && !keyH.rightPressed) {
            // Player is not moving, use the stationary image based on the current direction
            switch (direction) {
                case "up":
                    image = up;
                    break;
                case "down":
                    image = down;
                    break;
                case "left":
                    image = left;
                    break;
                case "right":
                    image = right;
                    break;
            }
        } else {
            // Player is moving, use walking images
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
        }

        g2.drawImage(image, screenX, screenY, null);
        // screenX and screenY don't change throughout the game
    }
}
