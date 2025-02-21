package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;
//import java.awt.image.BufferedImage;
//import java.text.DecimalFormat;

//import object.OBJ_Key;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font maruMonica, purisaB;
//    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0; // 0: first screen, 1: second screen
   
//    double playTime;
//    DecimalFormat dFormat = new DecimalFormat("#0.00"); // up to; pattern

    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
			is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
			purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

//        OBJ_Key key = new OBJ_Key(gp);
//        keyImage = key.image;

    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
    	
    	this.g2 = g2;
    	
    	g2.setFont(maruMonica);
    	g2.setColor(Color.white);
    	
    	// TITLE STATE
    	if(gp.gameState == gp.titleState) {
    		drawTitleScreen();
    	}
    	
    	// PLAY STATE
    	if(gp.gameState == gp.playState) {
    		// Do playState here
    	}
    	// PAUSE STATE
    	if(gp.gameState == gp.pauseState) {
    		drawPauseScreen();
    	}
    	//DIALOGUE STATE
    	if(gp.gameState == gp.dialogueState) {
    		drawDialogueScreen();
    	}
    	
    	// THIS IS PREVIOUS
//        if (gameFinished == true) {
//
//            g2.setFont(arial_40);
//            g2.setColor(Color.white);
//
//            String text;
//            int textLength;
//            int x;
//            int y;
//
//            text = "You found the end!";
//            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth(); // this returns the length of
//                                                                                         // the text
//
//            x = gp.screenWidth / 2 - textLength / 2;
//            y = gp.screenHeight / 2 - (gp.tileSize * 3);
//            g2.drawString(text, x, y);
//
//            text = "Your time is:" + dFormat.format(playTime) + "!";
//            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
//            x = gp.screenWidth / 2 - textLength / 2;
//            y = gp.screenHeight / 2 + (gp.tileSize * 4);
//            g2.drawString(text, x, y);
//
//            g2.setFont(arial_80B);
//            g2.setColor(Color.yellow);
//            text = "Congratulations!";
//            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth(); // this returns the length of
//                                                                                         // the text
//
//            x = gp.screenWidth / 2 - textLength / 2;
//            y = gp.screenHeight / 2 + (gp.tileSize * 2);
//            g2.drawString(text, x, y);
//
//            gp.gameThread = null; // this stops the game
//
//        } else {
//            g2.setFont(arial_40);
//            g2.setColor(Color.white);
//            g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null); // .../2 means 24
//            // pixels
//            g2.drawString("x " + gp.player.hasKey, 74, 65); // drawString's y is different
//
//            // TIME
//            playTime += (double) 1 / 60; // 1/60 seconds every loop
//            g2.drawString("Time: " + dFormat.format(playTime), gp.tileSize * 11, 65);
//
//            // MESSAGE
//            if (messageOn == true) {
//                g2.setFont(g2.getFont().deriveFont(30F));
//                g2.drawString(message, gp.tileSize / 2, gp.tileSize * 5);
//
//                messageCounter++;
//
//                if (messageCounter > 120) { // 2 seconds (120 frames)
//                    messageCounter = 0;
//                    messageOn = false;
//                }
//            }
//        }

    }
    
    public void drawTitleScreen() {
    	if(titleScreenState == 0) {
    		g2.setColor(new Color(0,0,0));
        	g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        	
        	// TITLE NAME
        	g2.setFont(g2.getFont().deriveFont(Font.BOLD,70F));
        	String text = "Five Minutes of Hell";
        	int x = getXforCenteredText(text);
        	int y = gp.tileSize*3;
        	
        	// SHADOW
        	g2.setColor(Color.gray);
        	g2.drawString(text, x+3, y+3);
        	
        	// MAIN COLOR
        	g2.setColor(Color.white);
        	g2.drawString(text, x, y);
        	
        	// CHARACTER IMAGE
        	x = gp.screenWidth/2 - (gp.tileSize*2)/2;
        	y += gp.tileSize;
        	g2.drawImage(gp.player.down, x, y, gp.tileSize*2, gp.tileSize*2, null);
        	
        	//MENU
        	g2.setFont(g2.getFont().deriveFont(Font.PLAIN,35F));
        	text = "NEW GAME";
        	x = getXforCenteredText(text);
        	y += gp.tileSize*3.5;
        	g2.drawString(text, x, y);
        	
        	if(commandNum == 0) {
        		g2.drawString(">", x-gp.tileSize, y);
        	}
        	
        	text = "ABOUT";
        	x = getXforCenteredText(text);
        	y += gp.tileSize;
        	g2.drawString(text, x, y);
        	
        	if(commandNum == 1) {
        		g2.drawString(">", x-gp.tileSize, y);
        	}
        	
        	text = "DEVELOPERS";
        	x = getXforCenteredText(text);
        	y += gp.tileSize;
        	g2.drawString(text, x, y);
        	
        	if(commandNum == 2) {
        		g2.drawString(">", x-gp.tileSize, y);
        	}
        	
        	text = "QUIT";
        	x = getXforCenteredText(text);
        	y += gp.tileSize;
        	g2.drawString(text, x, y);
        	
        	if(commandNum == 3) {
        		g2.drawString(">", x-gp.tileSize, y);
        	}
    	}
    	else if(titleScreenState == 1) {
    		g2.setColor(Color.white);
    		g2.setFont(g2.getFont().deriveFont(42F));
    		
    		String text = "STORY";
    		// ETC ETC
    		int x = getXforCenteredText(text);
    		int y = gp.tileSize*3;
    		g2.drawString(text, x, y);
    		
    		text = "START";
    		x = getXforCenteredText(text);
    		y += gp.tileSize*3;
    		g2.drawString(text, x, y);

    		if(commandNum == 0) {
    			g2.drawString(">", x-gp.tileSize, y);
    		}
    		
    		text = "BACK";
    		x = getXforCenteredText(text);
    		y += gp.tileSize*3;
    		g2.drawString(text, x, y);

    		if(commandNum == 1) {
    			g2.drawString(">", x-gp.tileSize, y);
    		}
    	}
    	

    }
    
    public void drawPauseScreen() {
    	g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
    	String text = "PAUSED";
    	int x = getXforCenteredText(text);
    	int y = gp.screenHeight/2;
    	
    	g2.drawString(text, x, y);
    }
    
    public void drawDialogueScreen(){
    	//WINDOW
    	int x = gp.tileSize*2;
    	int y = gp.tileSize/2;
    	int width = gp.screenWidth - (gp.tileSize*4);
    	int height = gp.tileSize*4;
    	
    	drawSubWindow(x, y, width, height);
    	
    	g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
    	x += gp.tileSize;
    	y += gp.tileSize;
    	g2.drawString(currentDialogue, x, y);
    }
    
    public void drawSubWindow(int x, int y, int width, int height){
    	Color c = new Color(0,0,0, 210); // RGB Value; the fourth number is alpha value -> opacity
    	g2.setColor(c);
    	g2.fillRoundRect(x, y, width, height, 35, 35);
    	
    	c = new Color(255,255,255); // white
    	g2.setColor(c);
    	g2.setStroke(new BasicStroke(5));
    	g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }
    
    public int getXforCenteredText(String text) {
    	int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
    	int x = gp.screenWidth/2 - length/2; // Position of text in the screen
    	return x;
    }
}
