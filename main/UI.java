package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import entity.Entity;
import object.OBJ_Coin;
import object.OBJ_GreenStar;
import object.OBJ_Heart;
import object.OBJ_RedStar;
import object.OBJ_YellowStar;

import java.awt.image.BufferedImage;

public class UI {
	GamePanel gp;
	Graphics2D g2;
	Font maruMonica, purisaB;
	BufferedImage coin;
	BufferedImage heart_full, heart_half, heart_blank;
	BufferedImage red_perk, yellow_perk, green_perk;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	public String currentDialogue = "";
	public int commandNum = 0;
	public int titleScreenState = 0; // 0: first screen, 1: second screen
	public int playerSlotCol = 0;
	public int playerSlotRow = 0;
	public int npcSlotCol = 0;
	public int npcSlotRow = 0;
	int subState = 0;
	int optionsSubState = 0;
	public Entity npc;

	// double playTime;
	DecimalFormat dFormat = new DecimalFormat("#0.00"); // up to; pattern

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

		// CREATE HUD OBJECT
		Entity heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;

		Entity red = new OBJ_RedStar(gp);
		red_perk = red.image;

		Entity yellow = new OBJ_YellowStar(gp);
		yellow_perk = yellow.image;

		Entity green = new OBJ_GreenStar(gp);
		green_perk = green.image;

		Entity coinM = new OBJ_Coin(gp);
		coin = coinM.image;

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
		if (gp.gameState == gp.titleState) {
			drawTitleScreen();
		}

		// PLAY STATE
		if (gp.gameState == gp.playState) {
			drawPlayerLife();
			if (gp.gameTimer != gp.endGameTimer) {
				drawTimeLeft();
			}
			if (gp.player.perkAvailable == true && gp.player.perkTimer != 0) {
				drawPerk();
			}
		}
		// PAUSE STATE
		if (gp.gameState == gp.pauseState) {
			drawPlayerLife();
			if (gp.player.perkAvailable == true && gp.player.perkTimer != 0) {
				drawPerk();
			}
			drawPauseScreen();
		}
		// DIALOGUE STATE
		if (gp.gameState == gp.dialogueState) {
			drawPlayerLife();
			drawDialogueScreen();
		}

		// CHARACTER STATE
		if (gp.gameState == gp.characterState) {
			drawCharacterScreen();
			drawInventory(gp.player, true);
		}

		// OPTIONS STATE
		if (gp.gameState == gp.optionsState) {
			drawOptionsScreen();
		}

		// GAME OVER STATE
		if (gp.gameState == gp.gameOverState) {
			drawGameOverScreen();
		}

		// WINNING STATE
		if (gp.gameState == gp.winningState) {
			drawWinningScreen();
		}

		// TRADE STATE
		if (gp.gameState == gp.tradeState) {
			drawTradeScreen();
		}
	}

	public void drawPerk() {
		int i = 0;
		if (gp.perkState == 1) {
			int x = gp.tileSize * 3 + 33;
			int y = gp.tileSize / 2 + 2;

			if (i != gp.player.maxPerk + 1) {
				g2.drawImage(red_perk, x, y, null);
				i++;
			}

		} else if (gp.perkState == 2) {
			int x = gp.tileSize * 3 + 33;
			int y = gp.tileSize / 2 + 2;

			if (i != gp.player.maxPerk + 1) {
				g2.drawImage(yellow_perk, x, y, null);
				i++;
			}

		} else if (gp.perkState == 3) {
			int x = gp.tileSize * 3 + 33;
			int y = gp.tileSize / 2 + 2;
			if (i != gp.player.maxPerk + 1) {
				g2.drawImage(green_perk, x, y, null);
				i++;
			}
		}
	}

	public void drawPlayerLife() {
		int x = gp.tileSize / 2;
		int y = gp.tileSize / 2;
		int i = 0;

		// DRAW MAX LIFE
		while (i < gp.player.maxLife / 2) {
			g2.drawImage(heart_blank, x, y, null);
			i++;
			x += gp.tileSize;
		}

		// RESET
		x = gp.tileSize / 2;
		y = gp.tileSize / 2;
		i = 0;

		// DRAW CURRENT LIFE
		while (i < gp.player.life) {
			g2.drawImage(heart_half, x, y, null);
			i++;
			if (i < gp.player.life) {
				g2.drawImage(heart_full, x, y, null);
			}
			i++;
			x += gp.tileSize;
		}
	}

	public void drawTitleScreen() {
		if (titleScreenState == 0) {
			g2.setColor(new Color(0, 0, 0));
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

			// TITLE NAME
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 70F));
			String text = "Five Minutes of Hell";
			int x = getXforCenteredText(text);
			int y = gp.tileSize * 3;

			// SHADOW
			g2.setColor(Color.gray);
			g2.drawString(text, x + 3, y + 3);

			// MAIN COLOR
			g2.setColor(Color.white);
			g2.drawString(text, x, y);

			// CHARACTER IMAGE
			x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
			y += gp.tileSize;
			g2.drawImage(gp.player.down, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

			// MENU
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 35F));
			text = "NEW GAME";
			x = getXforCenteredText(text);
			y += gp.tileSize * 3.5;
			g2.drawString(text, x, y);

			if (commandNum == 0) {
				g2.drawString(">", x - gp.tileSize, y);
			}

			text = "ABOUT";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);

			if (commandNum == 1) {
				g2.drawString(">", x - gp.tileSize, y);
			}

			text = "DEVELOPERS";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);

			if (commandNum == 2) {
				g2.drawString(">", x - gp.tileSize, y);
			}

			text = "QUIT";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);

			if (commandNum == 3) {
				g2.drawString(">", x - gp.tileSize, y);
			}
		} else if (titleScreenState == 1) {
			g2.setColor(Color.red);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 70F));

			String text = "CAN YOU SURVIVE?";
			int x = getXforCenteredText(text);
			int y = gp.tileSize * 3;
			g2.drawString(text, x, y);

			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(42F));

			text = "START";
			x = getXforCenteredText(text);
			y += gp.tileSize * 4;
			g2.drawString(text, x, y);

			if (commandNum == 0) {
				g2.drawString(">", x - gp.tileSize, y);
			}

			text = "BACK";
			x = getXforCenteredText(text);
			y += gp.tileSize * 2;
			g2.drawString(text, x, y);

			if (commandNum == 1) {
				g2.drawString(">", x - gp.tileSize, y);
			}
		} else if (titleScreenState == 2) { // WILL FIX THIS
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 60F));

			String text = "ABOUT";
			int x = getXforCenteredText(text);
			int y = (int) (gp.tileSize * 1.8);
			g2.drawString(text, x, y);

			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(25F));

			text = "A police officer is en-route via airplane to Europe for an upcoming";
			x = getXforCenteredText(text);
			y = gp.tileSize * 3;
			g2.drawString(text, x, y);

			text = "vacation for their department. However, the airplane's engine suddenly";
			x = getXforCenteredText(text);
			y = (int) (gp.tileSize * 3.7);
			g2.drawString(text, x, y);

			text = "malfunctioned while flying through Bermuda's Triangle, causing it to go";
			x = getXforCenteredText(text);
			y = (int) (gp.tileSize * 4.4);
			g2.drawString(text, x, y);

			text = "down. He woke up on an island, alone and cold. He tried to get help;";
			x = getXforCenteredText(text);
			y = (int) (gp.tileSize * 5.1);
			g2.drawString(text, x, y);

			text = "however, the help was not coming until five minutes later. Little";
			x = getXforCenteredText(text);
			y = (int) (gp.tileSize * 5.8);
			g2.drawString(text, x, y);

			text = "did he know that there were monsters lurking around the island";
			x = getXforCenteredText(text);
			y = (int) (gp.tileSize * 6.5);
			g2.drawString(text, x, y);

			text = "coming to get him. He must SURVIVE for five minutes.";
			x = getXforCenteredText(text);
			y = (int) (gp.tileSize * 7.2);
			g2.drawString(text, x, y);

			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(27F));

			text = "INSTRUCTIONS";
			x = getXforCenteredText(text);
			y += (int) (gp.tileSize * 1.5);
			g2.drawString(text, x, y);
			if (commandNum == 0) {
				g2.drawString(">", x - gp.tileSize, y);
			}

			text = "CONTROLS";
			x = getXforCenteredText(text);
			y += gp.tileSize - 10;
			g2.drawString(text, x, y);
			if (commandNum == 1) {
				g2.drawString(">", x - gp.tileSize, y);
			}

			text = "REFERENCES";
			x = getXforCenteredText(text);
			y += gp.tileSize - 10;
			g2.drawString(text, x, y);
			if (commandNum == 2) {
				g2.drawString(">", x - gp.tileSize, y);
			}

			text = "BACK";
			x = getXforCenteredText(text);
			y += gp.tileSize - 10;
			g2.drawString(text, x, y);

			if (commandNum == 3) {
				g2.drawString(">", x - gp.tileSize, y);
			}
		} else if (titleScreenState == 3) {
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));

			String text = "DEVELOPERS";
			int x = getXforCenteredText(text);
			int y = (int) (gp.tileSize * 1.5);
			g2.drawString(text, x, y);

			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(25F));

			text = "A Project For CMSC 22";
			x = getXforCenteredText(text);
			y = gp.tileSize * 3;
			g2.drawString(text, x, y);

			text = "Lorenz Gabriel\nGian Carlo Suarez";
			x = gp.tileSize;
			y = gp.tileSize * 5;
			int lineHeight = 40;

			for (String line : text.split("\n")) {
				x = getXforCenteredText(line);
				g2.drawString(line, x, y);
				y += lineHeight;
			}

			text = "from YZ2L";
			x = getXforCenteredText(text);
			y = gp.tileSize * 8;
			g2.drawString(text, x, y);

			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(42F));

			text = "BACK";
			x = getXforCenteredText(text);
			y += gp.tileSize * 2;
			g2.drawString(text, x, y);

			if (commandNum == 2) {
				g2.drawString(">", x - gp.tileSize, y);
			}
		} else if (titleScreenState == 4) {
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 60F));

			String text = "INSTRUCTIONS";
			int x = getXforCenteredText(text);
			int y = (int) (gp.tileSize * 1.5);
			g2.drawString(text, x, y);

			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(22F));

			text = "1) Once the game starts, the timer will start. You must be alive for";
			x = getXforCenteredText(text);
			y += gp.tileSize - 10;
			g2.drawString(text, x, y);

			text = "five minutes to survive and win the game.";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);

			text = "2) You have three hearts for the whole game. If a monster attacks";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);

			text = "you, you lose a life.";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);

			text = "3) Collect perks that will give you advantages in winning the game and";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);

			text = "making your points go up.";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);

			text = "4) Using the coins that the monsters are dropping, you may buy certain";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);

			text = "guns that is available in a merchant.";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);

			text = "5) Score the most points. Play it with your friends to see who did better.";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);

			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(42F));

			text = "BACK";
			x = getXforCenteredText(text);
			y += gp.tileSize + 10;
			g2.drawString(text, x, y);

			if (commandNum == 0) {
				g2.drawString(">", x - gp.tileSize, y);
			}
		} else if (titleScreenState == 5) {
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 60F));

			String text = "CONTROLS";
			int x = getXforCenteredText(text);
			int y = (int) (gp.tileSize * 1.5);
			g2.drawString(text, x, y);

			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));

			x = (int) (gp.tileSize * 4);
			y = gp.tileSize * 3;
			g2.drawString("Move", x, y);

			y += gp.tileSize;
			g2.drawString("Confirm/Interact", x, y);

			y += gp.tileSize;
			g2.drawString("Shoot", x, y);

			y += gp.tileSize;
			g2.drawString("Character Screen", x, y);

			y += gp.tileSize;
			g2.drawString("Options", x, y);

			y += gp.tileSize;
			g2.drawString("Pause", x, y);

			x = (int) (gp.tileSize * 10);
			y = gp.tileSize * 3;
			g2.drawString("WASD", x, y);
			y += gp.tileSize;
			g2.drawString("ENTER", x, y);
			y += gp.tileSize;
			g2.drawString("F", x, y);
			y += gp.tileSize;
			g2.drawString("C", x, y);
			y += gp.tileSize;
			g2.drawString("O", x, y);
			y += gp.tileSize;
			g2.drawString("ESC", x, y);
			y += gp.tileSize;

			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(42F));

			text = "BACK";
			x = getXforCenteredText(text);
			y += gp.tileSize + 10;
			g2.drawString(text, x, y);

			if (commandNum == 0) {
				g2.drawString(">", x - gp.tileSize, y);
			}
		} else if (titleScreenState == 6) {
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 60F));

			String text = "REFERENCES & CREDITS";
			int x = getXforCenteredText(text);
			int y = (int) (gp.tileSize * 1.5);
			g2.drawString(text, x, y);

			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));

			text = "Websites:";
			x = getXforCenteredText(text);
			y += gp.tileSize - 5;
			g2.drawString(text, x, y);

			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 22F));

			text = "Piskel, Booth, Fonts Geek, Stack Overflow";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);

			text = "Github, ChatGPT, Google Services";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);

			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));

			text = "Youtube Channels:";
			x = getXforCenteredText(text);
			y += gp.tileSize + 15;
			g2.drawString(text, x, y);

			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 22F));

			text = "Kaarin Gaming, thenewboston, RealTutsGML, and M3832";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);

			text = "- for helping us get through this project.";
			x = getXforCenteredText(text);
			y += gp.tileSize - 10;
			g2.drawString(text, x, y);

			text = "AND RyiSnow";
			x = getXforCenteredText(text);
			y += gp.tileSize + 10;
			g2.drawString(text, x, y);

			text = "- for being a wonderful teacher and our own inspiration.";
			x = getXforCenteredText(text);
			y += gp.tileSize - 10;
			g2.drawString(text, x, y);

			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(42F));

			text = "BACK";
			x = getXforCenteredText(text);
			y += gp.tileSize + 10;
			g2.drawString(text, x, y);

			if (commandNum == 0) {
				g2.drawString(">", x - gp.tileSize, y);
			}
		}
	}

	public void drawPauseScreen() {
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
		String text = "PAUSED";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight / 2;

		g2.drawString(text, x, y);
	}

	public void drawDialogueScreen() {
		// WINDOW
		int x = gp.tileSize * 2;
		int y = gp.tileSize / 2;
		int width = gp.screenWidth - (gp.tileSize * 4);
		int height = gp.tileSize * 4;

		drawSubWindow(x, y, width, height);

		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
		x += gp.tileSize;
		y += gp.tileSize;

		for (String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y += 40;
		}
	}

	public void drawCharacterScreen() {
		// CREATE A FRAME
		final int frameX = gp.tileSize * 2;
		final int frameY = gp.tileSize;
		final int frameWidth = gp.tileSize * 5;
		final int frameHeight = gp.tileSize * 10;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);

		// TEXT
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));

		int textX = frameX + 20;
		int textY = frameY + gp.tileSize;
		final int lineHeight = 32;

		// NAMES
		g2.drawString("Life", textX, textY);
		textY += lineHeight;
		g2.drawString("Attack", textX, textY);
		textY += lineHeight;
		g2.drawString("Coin", textX, textY);
		textY += lineHeight;
		g2.drawString("Points", textX, textY);
		textY += lineHeight + 20;
		g2.drawString("Weapon", textX, textY);
		textY += lineHeight;

		// VALUES
		int tailX = (frameX + frameWidth) - 30;
		// Reset textY
		textY = frameY + gp.tileSize;
		String value;

		value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.currentWeapon.attack);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.coin);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.playerPoints);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		if (gp.player.currentWeapon != null) {
			g2.drawImage(gp.player.currentWeapon.weapon, tailX - gp.tileSize, textY - 14,
					null);
			textY += gp.tileSize;
		}

	}

	public void drawInventory(Entity entity, boolean cursor) {
		int frameX = 0;
		int frameY = 0;
		int frameWidth = 0;
		int frameHeight = 0;
		int slotCol = 0;
		int slotRow = 0;

		if (entity == gp.player) {
			frameX = gp.tileSize * 9;
			frameY = gp.tileSize;
			frameWidth = gp.tileSize * 6;
			frameHeight = gp.tileSize * 5;
			slotCol = playerSlotCol;
			slotRow = playerSlotRow;
		} else {
			frameX = gp.tileSize * 2;
			frameY = gp.tileSize;
			frameWidth = gp.tileSize * 6;
			frameHeight = gp.tileSize * 5;
			slotCol = npcSlotCol;
			slotRow = npcSlotRow;
		}

		// FRAME
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);

		// SLOT
		final int slotXstart = frameX + 20;
		final int slotYstart = frameY + 20;
		int slotX = slotXstart;
		int slotY = slotYstart;
		int slotSize = gp.tileSize + 3;

		// DRAW PLAYER'S ITEMS
		for (int i = 0; i < entity.inventory.size(); i++) {

			// EQUIP CURSOR
			if (entity.inventory.get(i) == entity.currentWeapon) {
				g2.setColor(new Color(240, 190, 90));
				g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
			}

			g2.drawImage(entity.inventory.get(i).weapon, slotX, slotY, null);
			slotX += gp.tileSize;

			if (i == 4 || i == 9 || i == 14) {
				slotX = slotXstart;
				slotY += slotSize;
			}
		}

		// CURSOR
		if (cursor == true) {
			int cursorX = slotXstart + (gp.tileSize * slotCol);
			int cursorY = slotYstart + (gp.tileSize * slotRow);
			int cursorWidth = gp.tileSize;
			int cursorHeight = gp.tileSize;

			// DRAW CURSOR
			g2.setColor(Color.white);
			g2.setStroke(new BasicStroke(3));
			g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

			// DESCRIPTION
			int dFrameX = frameX;
			int dFrameY = frameY + frameHeight;
			int dFrameWidth = frameWidth;
			int dFrameHeight = gp.tileSize * 3;

			// DRAW DESCRIPTION TEXT
			int textX = dFrameX + 20;
			int textY = dFrameY + gp.tileSize;
			g2.setFont(g2.getFont().deriveFont(28F));

			int itemIndex = getItemIndexOnSlot(slotCol, slotRow);

			if (itemIndex < entity.inventory.size()) {
				drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
				for (String line : entity.inventory.get(itemIndex).description.split("\n")) {
					g2.drawString(line, textX, textY);
					textY += 32;
				}
			}
		}

	}

	public void drawOptionsScreen() {
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));

		// SUB WINDOW
		int frameX = (int) (gp.tileSize * 0.7);
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize * 7;
		int frameHeight = (int) (gp.tileSize * 4.5);

		drawSubWindow(frameX, frameY, frameWidth, frameHeight);

		int frameX1 = (int) (gp.tileSize * 8.3);
		int frameY1 = gp.tileSize;
		int frameWidth1 = gp.tileSize * 7;
		int frameHeight1 = gp.tileSize * 10;

		drawSubWindow(frameX1, frameY1, frameWidth1, frameHeight1);

		switch (optionsSubState) {
			case 0:
				optionsTop(frameX, frameY);
				optionsTop1(frameX, frameY);
				break;
		}
	}

	public void optionsTop(int frameX, int frameY) {
		int textX;
		int textY;

		// TITLE
		String text = "Options";
		textX = frameX + gp.tileSize + 80;
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);

		// MUSIC
		textX = frameX + gp.tileSize;
		textY += gp.tileSize;
		g2.drawString("Music", textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
		}

		// SE
		textY += gp.tileSize;
		g2.drawString("SE", textX, textY);
		if (commandNum == 1) {
			g2.drawString(">", textX - 25, textY);
		}

		// END GAME
		textY += gp.tileSize;
		g2.drawString("End Game", textX, textY);
		if (commandNum == 2) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed == true) {
				optionsSubState = 0;
			}
		}

		// MUSIC VOLUME
		textX = frameX + (int) (gp.tileSize * 4.1);
		textY = frameY + gp.tileSize + 24;
		g2.drawRect(textX, textY, 120, 24); // Divide 120 by 5 = 24
		int volumeWidth = 24 * gp.music.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);

		// SE VOLUME
		textY += gp.tileSize;
		g2.drawRect(textX, textY, 120, 24);
		volumeWidth = 24 * gp.se.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
	}

	public void optionsTop1(int frameX1, int frameY1) {
		int textX;
		int textY;

		String text = "Controls";
		textX = frameX1 + gp.tileSize + 445;
		textY = frameY1 + gp.tileSize;
		g2.drawString(text, textX, textY);

		textX = frameX1 + gp.tileSize + 340;
		textY += gp.tileSize;
		g2.drawString("Move", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Confirm/Interact", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Shoot", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Character Screen", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Options", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Pause", textX, textY);
		textY += gp.tileSize;

		textX = frameX1 + (int) (gp.tileSize * 12.7);
		textY = frameY1 + gp.tileSize * 2;
		g2.drawString("WASD", textX, textY);
		textY += gp.tileSize;
		g2.drawString("ENTER", textX, textY);
		textY += gp.tileSize;
		g2.drawString("F", textX, textY);
		textY += gp.tileSize;
		g2.drawString("C", textX, textY);
		textY += gp.tileSize;
		g2.drawString("O", textX, textY);
		textY += gp.tileSize;
		g2.drawString("ESC", textX, textY);
		textY += gp.tileSize;
	}

	public void drawTimeLeft() {

		int x;
		int y;
		String totalTime;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 60f));

		int MinuteTime = 0;
		int SecondTime = 0;

		if (gp.endGameTimer - gp.gameTimer > 59) {
			MinuteTime = (gp.endGameTimer - gp.gameTimer) / 60;
			SecondTime = (gp.endGameTimer - gp.gameTimer) % 60;

			if (SecondTime > 9) {
				totalTime = MinuteTime + ":" + SecondTime;
			} else {
				totalTime = MinuteTime + ":" + "0" + SecondTime;
			}

		} else if (gp.endGameTimer - gp.gameTimer < 10) {
			SecondTime = (gp.endGameTimer - gp.gameTimer) % 60;
			totalTime = "0" + ":" + "0" + SecondTime;
		} else {
			SecondTime = (gp.endGameTimer - gp.gameTimer) % 60;
			totalTime = "0" + ":" + SecondTime;
		}

		g2.setColor(Color.black);
		x = getXforCenteredText(totalTime);
		y = gp.tileSize * 2;
		g2.drawString(totalTime, x, y);

		// Main
		g2.setColor(Color.white);
		g2.drawString(totalTime, x - 4, y - 4);
	}

	public void drawGameOverScreen() {
		g2.setColor(new Color(0, 0, 0, 150));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		int x;
		int y;
		String text;
		String totalTime;
		String totalPoints;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

		int MinuteTime = 0;
		int SecondTime = 0;

		if (gp.gameTimer > 60) {
			MinuteTime = gp.gameTimer / 60;
			SecondTime = gp.gameTimer % 60;

			if (SecondTime > 9) {
				totalTime = "Time: " + MinuteTime + ":" + SecondTime;
			} else {
				totalTime = "Time: " + MinuteTime + ":" + "0" + SecondTime;
			}

		} else if (gp.gameTimer < 10) {
			SecondTime = gp.gameTimer % 60;
			totalTime = "Time: " + "0" + ":" + "0" + SecondTime;
		} else {
			SecondTime = gp.gameTimer % 60;
			totalTime = "Time: " + "0" + ":" + SecondTime;
		}

		g2.setColor(Color.black);
		x = getXforCenteredText(totalTime);
		y = gp.tileSize * 5;
		g2.drawString(totalTime, x, y);

		// Main
		g2.setColor(Color.white);
		g2.drawString(totalTime, x - 4, y - 4);

		g2.setFont(g2.getFont().deriveFont(50f));
		totalPoints = "Points: " + gp.player.playerPoints;
		x = getXforCenteredText(totalPoints);
		y = gp.tileSize * 6;
		g2.drawString(totalPoints, x, y);

		// Restart
		// Title Screen
		text = "Quit";
		x = getXforCenteredText(text);
		y += 150;
		g2.drawString(text, x, y);
		if (commandNum == 0) {
			g2.drawString(">", x - 40, y);
		}
	}

	public void drawWinningScreen() {
		g2.setColor(new Color(0, 0, 0, 150));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		int x;
		int y;
		String text;
		String totalPoints;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

		text = "Congratulations!";
		g2.setColor(Color.black);
		x = getXforCenteredText(text);
		y = gp.tileSize * 5;
		g2.drawString(text, x, y);

		g2.setColor(Color.white);
		g2.drawString(text, x - 4, y - 4);

		// Main

		g2.setFont(g2.getFont().deriveFont(30f));

		text = "The help arrived and you are on your way home!";
		x = getXforCenteredText(text);
		y = gp.tileSize * 6;
		g2.setColor(Color.white);
		g2.drawString(text, x, y);

		g2.setFont(g2.getFont().deriveFont(50f));
		totalPoints = "Points: " + gp.player.playerPoints;
		x = getXforCenteredText(totalPoints);
		y = gp.tileSize * 8;
		g2.drawString(totalPoints, x, y);

		// Restart
		// Title Screen
		text = "Quit";
		x = getXforCenteredText(text);
		y += 150;
		g2.drawString(text, x, y);
		if (commandNum == 0) {
			g2.drawString(">", x - 40, y);
		}
	}

	public void drawTradeScreen() {
		switch (subState) {
			case 0:
				tradeSelect();
				break;
			case 1:
				tradeBuy();
				break;
			case 2:
				tradeSell();
				break;
		}
		gp.keyH.enterPressed = false;
	}

	public void tradeSelect() {
		drawDialogueScreen();

		// DRAW WINDOW
		int x = gp.tileSize * 12;
		int y = gp.tileSize * 5;
		int width = gp.tileSize * 3;
		int height = (int) (gp.tileSize * 3.5);
		drawSubWindow(x, y, width, height);

		// DRAW TEXTS
		x += gp.tileSize;
		y += gp.tileSize;
		g2.drawString("Buy", x, y);
		if (commandNum == 0) {
			g2.drawString(">", x - 24, y);
			if (gp.keyH.enterPressed == true) {
				subState = 1;
			}
		}

		y += gp.tileSize;

		g2.drawString("Sell", x, y);
		if (commandNum == 1) {
			g2.drawString(">", x - 24, y);
			if (gp.keyH.enterPressed == true) {
				subState = 2;
			}
		}

		y += gp.tileSize;

		g2.drawString("Leave", x, y);
		if (commandNum == 2) {
			g2.drawString(">", x - 24, y);
			if (gp.keyH.enterPressed == true) {
				commandNum = 0;
				gp.gameState = gp.dialogueState;
				currentDialogue = "Come again!";
			}
		}
	}

	public void tradeBuy() {
		// DRAW PLAYER INVENTORY
		drawInventory(gp.player, false);
		// DRAW NPX INVENTORY
		drawInventory(npc, true);

		// DRAW HINT WINDOW
		int x = gp.tileSize * 2;
		int y = gp.tileSize * 9;
		int width = gp.tileSize * 6;
		int height = gp.tileSize * 2;
		drawSubWindow(x, y, width, height);
		g2.drawString("[ESC] Back", x + 24, y + 60);

		// DRAW COIN WINDOW
		x = gp.tileSize * 9;
		y = gp.tileSize * 9;
		width = gp.tileSize * 6;
		height = gp.tileSize * 2;
		drawSubWindow(x, y, width, height);
		g2.drawString("Your Coin: " + gp.player.coin, x + 24, y + 60);

		// DRAW PRICE WINDOW
		int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
		if (itemIndex < npc.inventory.size()) {
			x = (int) (gp.tileSize * 5.5);
			y = (int) (gp.tileSize * 5.5);
			width = (int) (gp.tileSize * 2.5);
			height = gp.tileSize;
			drawSubWindow(x, y, width, height);
			g2.drawImage(coin, x + 10, y + 8, 32, 32, null);

			int price = npc.inventory.get(itemIndex).price;
			String text = "" + price;
			x = getXforAlignToRightText(text, gp.tileSize * 8 - 20);
			g2.drawString(text, x, y + 34);

			// BUY AN ITEM
			if (gp.keyH.enterPressed == true) {
				if (npc.inventory.get(itemIndex).price > gp.player.coin) {
					subState = 0;
					gp.gameState = gp.dialogueState;
					currentDialogue = "You need more coins to buy!";
					drawDialogueScreen();
				} else if (gp.player.inventory.size() == gp.player.maxInventorySize) {
					subState = 0;
					gp.gameState = gp.dialogueState;
					currentDialogue = "Inventory is full!";
				} else {
					gp.player.coin -= npc.inventory.get(itemIndex).price;
					gp.player.inventory.add(npc.inventory.get(itemIndex));
					System.out.println("ITEM: " + npc.inventory.get(itemIndex));
				}
			}
		}
	}

	public void tradeSell() {
		// DRAW PLAYER INVENTORY
		drawInventory(gp.player, true);

		int x;
		int y;
		int width;
		int height;

		// DRAW HINT WINDOW
		x = gp.tileSize * 2;
		y = gp.tileSize * 9;
		width = gp.tileSize * 6;
		height = gp.tileSize * 2;
		drawSubWindow(x, y, width, height);
		g2.drawString("[ESC] Back", x + 24, y + 60);

		// DRAW COIN WINDOW
		x = gp.tileSize * 9;
		y = gp.tileSize * 9;
		width = gp.tileSize * 6;
		height = gp.tileSize * 2;
		drawSubWindow(x, y, width, height);
		g2.drawString("Your Coin: " + gp.player.coin, x + 24, y + 60);

		// DRAW PRICE WINDOW
		int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
		if (itemIndex < gp.player.inventory.size()) {
			x = (int) (gp.tileSize * 12.5);
			y = (int) (gp.tileSize * 5.5);
			width = (int) (gp.tileSize * 2.5);
			height = gp.tileSize;
			drawSubWindow(x, y, width, height);
			g2.drawImage(coin, x + 10, y + 8, 32, 32, null);

			int price = gp.player.inventory.get(itemIndex).price;
			String text = "" + price;
			x = getXforAlignToRightText(text, gp.tileSize * 15 - 20);
			g2.drawString(text, x, y + 34);

			// SELL AN ITEM
			if (gp.keyH.enterPressed == true) {
				if (gp.player.inventory.get(itemIndex) == gp.player.currentWeapon) {
					commandNum = 0;
					subState = 0;
					gp.gameState = gp.dialogueState;
					currentDialogue = "You cannot sell an equipped item!";
				} else {
					gp.player.inventory.remove(itemIndex);
					gp.player.coin += price;
				}
			}
		}
	}

	public int getItemIndexOnSlot(int slotCol, int slotRow) {
		int itemIndex = slotCol + (slotRow * 5);
		return itemIndex;
	}

	public void drawSubWindow(int x, int y, int width, int height) {
		Color c = new Color(0, 0, 0, 210); // RGB Value; the fourth number is alpha value -> opacity
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);

		c = new Color(255, 255, 255); // white
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
	}

	public int getXforCenteredText(String text) {
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth / 2 - length / 2; // Position of text in the screen
		return x;
	}

	public int getXforAlignToRightText(String text, int tailX) {
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = tailX - length;
		return x;
	}
}
