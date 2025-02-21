package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import ai.PathFinder;
import entity.Entity;
import entity.Player;
import tile.TileManager;

// This class represents the main game panel that extends JPanel and implements Runnable.
public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile; standard size for 2D game
    final int scale = 3;

    // The size of each scaled tile.
    public final int tileSize = originalTileSize * scale; // 16x3(scale) = 48; 48x48 tile
    // The maximum number of tiles to be displayed on the screen horizontally and
    // vertically.
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12; // ratio 4x3
    // The total width and height of the screen in pixels.
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // FPS
    int FPS = 60;

    // SYSTEM
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    public PathFinder pFinder = new PathFinder(this);
    Thread gameThread;

    // ENTITY AND OBJECT
    // The player object and arrays to store entities, NPCs, and zombies.
    public Player player = new Player(this, keyH);
    public Entity obj[] = new Entity[10]; // 10 slots for object; this means we can display up to 10 objects at the same
                                          // time
    public Entity npc[] = new Entity[10];
    public Entity zombie[] = new Entity[22];
    ArrayList<Entity> entityList = new ArrayList<>();
    public ArrayList<Entity> projectileList = new ArrayList<>();

    // GAME STATE
    // Various game states that represent different situations or screens.
    public int gameState; // situations; screen
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int gameOverState = 6;
    public final int tradeState = 7;
    public final int optionsState = 8;
    public final int winningState = 9;
    public int perkState;

    // Counters and timers used in the game.
    public int boss1counter = 0;
    public int boss2counter = 0;
    public int gameTimer = 0;
    public final int endGameTimer = 60 * 5;

    // Constructor for the GamePanel class.
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    // Method to initialize the game settings and assets.
    public void setupGame() {
        gameTimer = 0; // method so we can add other setup stuff in the future
        aSetter.setNPC();
        gameState = titleState;
    }

    // Method to restart the game by resetting player and NPC properties.
    public void restart() {
        gameTimer = 0;
        player.setDefaultValues();
        player.setDefaultPositions();
        player.getPlayerImage();
        player.setItems();
        aSetter.setNPC();
    }

    // Method to start the game thread.
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // The main game loop run by the game thread.
    @Override
    public void run() {
        // Timing variables for controlling the game loop.
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        // Game loop
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            // Update and repaint the game at a fixed interval
            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;

                if (gameState == playState) { // Game Timer
                    gameTimer += 1;
                }

                // PERK TIMER
                if (player.perkAvailable == true && player.perkTimer > 0 && gameState == playState) {
                    player.perkTimer--;
                    System.out.println("Perk Timer:" + player.perkTimer);

                    if (perkState == 1) { // Keep restoring the life if red perk is used
                        if (player.life != 6) {
                            player.life = 6;
                        }
                    } else if (perkState == 2) { // Keep adding points if yellow perk is used
                        player.playerPoints += 500;
                    }
                }

                // RESET ABILITIES
                if (player.perkTimer == 0 || gameState == gameOverState || gameState == winningState) {
                    if (perkState == 1) {
                        System.out.println("Red Star");
                        player.life = 6;
                    } else if (perkState == 2) {
                        System.out.println("Yellow Star");
                    } else if (perkState == 3) {
                        System.out.println("Green Star");
                        player.speed -= 2;
                    }

                    player.perkTimer = 999;
                    player.maxPerk = 0;
                    player.perkAvailable = false;
                    perkState = 0;
                }

                // Spawn perks between 1 second
                aSetter.setObject();

            }
        }
    }

    // Method to update game logic
    public void update() {
        // Update game logic based on the current game state
        if (gameState == playState) { // If the game is in the playing state
            // Player
            player.update();
            // NPC
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].update(); // Update non-player characters (NPCs)
                }
            }

            // Update zombies
            for (int i = 0; i < zombie.length; i++) {
                if (zombie[i] != null) {
                    // Check if the zombie is alive and not in the process of dying
                    if (zombie[i].alive == true && zombie[i].dying == false) {
                        zombie[i].update();
                    }
                    // If the zombie is not alive, set it to null
                    if (zombie[i].alive == false) {
                        zombie[i] = null;
                    }
                }
            }

            // Update projectiles
            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    // Check if the projectile is alive
                    if (projectileList.get(i).alive == true) {
                        projectileList.get(i).update();
                    }
                    // If the projectile is not alive, remove it from the list
                    if (projectileList.get(i).alive == false) {
                        projectileList.remove(i);
                    }
                }
            }
        }

        // SPAWN ENTITIES
        if (gameState != gameOverState && gameState != winningState) { // If the game is not in the game over state and
                                                                       // not in the winning state

            for (int i = 0; i < zombie.length; i++) { // Spawn zombie infinitely
                aSetter.setZombie(i);
            }

        } else {
            // If the game is in the game over state or
            for (int i = 0; i < zombie.length; i++) { // Clear zombie
                zombie[i] = null; // Clear zombie array
            }

            for (int i = 0; i < obj.length; i++) { // Clear perks
                obj[i] = null; // Clear obj array
            }
        }

        // Check if the game is in the pause state
        if (gameState == pauseState) {
            // If in the pause state, do nothing
        }

    }

    // built-in method in java
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Call the superclass's paintComponent method to ensure proper painting
                                 // behavior

        Graphics2D g2 = (Graphics2D) g; // Cast the Graphics object to Graphics2D for additional functionality

        // DEBUG: Measure the time taken to draw if keyH.checkDrawTime is true
        long drawStart = 0;
        if (keyH.checkDrawTime == true) {
            drawStart = System.nanoTime();
        }

        // TITLE SCREEN
        // Check the game state to determine what to draw
        if (gameState == titleState) {
            ui.draw(g2); // Draw UI for the title screen
        }
        // OTHERS
        else {
            // TILE
            // Draw tiles (a layer beneath entities)
            tileM.draw(g2); // make sure it is before the player; like a layer

            // ADD ENTITIES TO THE LIST
            entityList.add(player); // Add player to the entityList

            // OBJECTS
            for (int i = 0; i < obj.length; i++) { // Add objects to the entityList
                if (obj[i] != null) {
                    entityList.add(obj[i]);
                }
            }

            // NPC
            for (int i = 0; i < npc.length; i++) { // Add NPCs to the entityList
                if (npc[i] != null) {
                    entityList.add(npc[i]);
                }
            }

            // ZOMBIE
            for (int i = 0; i < zombie.length; i++) { // Add zombies to the entityList
                if (zombie[i] != null) {
                    entityList.add(zombie[i]);
                }
            }

            // PROJECTILE
            for (int i = 0; i < projectileList.size(); i++) { // Add projectiles to the entityList
                if (projectileList.get(i) != null) {
                    entityList.add(projectileList.get(i));
                }
            }

            // SORT
            // Sort the entityList based on the entities' worldY values
            Collections.sort(entityList, new Comparator<Entity>() {

                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }

            });

            // DRAW ENTITIES
            for (int i = 0; i < entityList.size(); i++) { // Draw entities in the sorted order
                entityList.get(i).draw(g2);
            }

            // EMPTY ENTITY LIST
            entityList.clear(); // Clear the entityList to prepare for the next frame

            // UI
            ui.draw(g2); // Draw UI for the main game state
        }

        // DEBUG: Display the time taken to draw if keyH.checkDrawTime is true
        if (keyH.checkDrawTime == true) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw Time: " + passed, 10, 400);
            System.out.println("Draw Time: " + passed);
        }

        // Dispose of the Graphics2D object to release system resources
        g2.dispose(); // practice to save some memory
    }

    // Method to play background music based on the specified index
    public void playMusic(int i) {
        music.setFile(i); // Set the music file and play it in a loop
        music.play();
        music.loop();
    }

    // Method to stop the currently playing background music
    public void stopMusic() {
        music.stop(); // Stop the music playback
    }

    // Method to play a sound effect based on the specified index
    public void playSE(int i) {
        se.setFile(i); // Set the sound effect file and play it
        se.play();
        // we didn't need to call the loop since this is just a sound effect
    }
}