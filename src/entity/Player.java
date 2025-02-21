package entity;

// import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Rectangle;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity {
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

//    public int hasKey = 0; // indicates how many keys the player currently has

    public Player(GamePanel gp, KeyHandler keyH) {
    	super(gp); // calling the constructor of the its superclass; Entity class is abstract class has no instance
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
//        setDialogue();
    }

    public void setDefaultValues() {
        // set player's default position
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4; // pixels
        direction = "down";
    }

    public void getPlayerImage() {
        up = setup("up_stand");
        up1 = setup("up_left");
        up2 = setup("up_right");
        down = setup("down_stand");
        down1 = setup("down_left");
        down2 = setup("down_right");
        left = setup("left_stand");
        left1 = setup("left_left");
        left2 = setup("left_right");
        right = setup("right_stand");
        right1 = setup("right_left");
        right2 = setup("right_right");
    }
    
    public BufferedImage setup(String imageName) {
    	UtilityTool uTool = new UtilityTool();
    	BufferedImage image = null;
    	
    	try {
    		image = ImageIO.read(getClass().getResourceAsStream("/playerImage/" + imageName + ".png"));
    		image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
    	return image;
    }

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
            pickUpObject(objIndex);

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
    }

    public void pickUpObject(int i) {
    	//FROM PREVIOUS
        if (i != 999) { // any number is fine; this means if we didnt touch the object
//            String objectName = gp.obj[i].name;
//
//            switch (objectName) {
//                case "Key":
//                    gp.playSE(1);
//                    hasKey++;
//                    gp.obj[i] = null;
//                    gp.ui.showMessage("You got a key!");
//                    // System.out.println("Key:" + hasKey);
//                    break;
//
//                case "Door":
//                    if (hasKey > 0) {
//                        gp.playSE(3);
//                        gp.obj[i] = null;
//                        hasKey--;
//                        gp.ui.showMessage("You opened the door!");
//                    }
//                    // System.out.println("Key:" + hasKey);
//                    else {
//                        gp.ui.showMessage("You need a key!");
//                    }
//                    break;
//
//                case "Boots":
//                    gp.playSE(2);
//                    speed += 2;
//                    gp.obj[i] = null; // makes it disappear after getting it
//                    gp.ui.showMessage("Lightning McQueen~~~ Speed Up!");
//                    break;
//
//                case "Chest":
//                    gp.ui.gameFinished = true;
//                    gp.stopMusic();
//                    gp.playSE(4);
//                    break;
//            }
        }
    }
    
    public void setDialogue() {
    	dialogues[0] = "Argh! W-what happened?";
    }
    
    public void interactNPC(int i) {
    	
    }
    
    public void speak() {
//    	gp.ui.currentDialogue = dialogues[0];
    }

    public void draw(Graphics2D g2) {
        // g2.setColor(Color.white);
        // g2.fillOval(screenX, screenY, gp.tileSize, gp.tileSize);

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
