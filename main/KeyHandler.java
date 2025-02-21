package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// Main class for handling keyboard input
public class KeyHandler implements KeyListener {
    GamePanel gp; // Reference to the GamePanel instance to interact with the game
    // Boolean variables to track the state of various keys
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed;
    // DEBUG: Boolean variable for checking draw time
    boolean checkDrawTime = false;

    // Constructor that takes a GamePanel instance as a parameter
    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    // Overridden method for handling key-typed events (not implemented)
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    // Overridden method for handling key-pressed events
    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        int code = e.getKeyCode(); // Getting the key code of the pressed key

        // Checking the current state of the game and delegating key handling
        // accordingly

        // TITLE STATE
        if (gp.gameState == gp.titleState) {
            titleState(code);
        }

        // PLAY STATE
        else if (gp.gameState == gp.playState) {
            playState(code);
        }
        // PAUSE STATE
        else if (gp.gameState == gp.pauseState) {
            pauseState(code);
        }

        // DIALOGUE STATE
        else if (gp.gameState == gp.dialogueState) {
            dialogueState(code);
        }

        // CHARACTER STATE
        else if (gp.gameState == gp.characterState) {
            characterState(code);
        }

        // OPTIONS STATE
        else if (gp.gameState == gp.optionsState) {
            optionsState(code);
        }

        // GAMEOVER STATE
        else if (gp.gameState == gp.gameOverState) {
            gameOverState(code);
        }

        // WINNING STATE
        else if (gp.gameState == gp.winningState) {
            winningState(code);
        }

        // TRADE STATE
        else if (gp.gameState == gp.tradeState) {
            tradeState(code);
        }
    }

    // Method for handling key events in the TITLE STATE
    public void titleState(int code) {
        // Different actions based on the current screen in the title state
        if (gp.ui.titleScreenState == 0) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--; // Moving the cursor up in the command selection
                gp.playSE(6);
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 3;
                }
            }

            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++; // Moving the cursor down in the command selection
                gp.playSE(6);
                if (gp.ui.commandNum > 3) {
                    gp.ui.commandNum = 0;
                }
            }
            // Handling the selection when Enter key is pressed
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    gp.ui.titleScreenState = 1;
                }
                if (gp.ui.commandNum == 1) {
                    // ABOUT
                    gp.ui.commandNum = 0;
                    gp.ui.titleScreenState = 2;
                }
                if (gp.ui.commandNum == 2) {
                    // DEVELOPERS
                    gp.ui.titleScreenState = 3;
                }
                if (gp.ui.commandNum == 3) {
                    System.exit(0);
                }
            }
        }

        else if (gp.ui.titleScreenState == 1) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                gp.playSE(6);
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 1;
                }
            }

            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                gp.playSE(6);
                if (gp.ui.commandNum > 1) {
                    gp.ui.commandNum = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                // START
                if (gp.ui.commandNum == 0) {
                    System.out.println("Can you survive?");
                    gp.gameState = gp.playState;
                    gp.playMusic(0);
                }
                if (gp.ui.commandNum == 1) {
                    // BACK
                    gp.ui.commandNum = 0;
                    gp.ui.titleScreenState = 0;
                }
            }
        } else if (gp.ui.titleScreenState == 2) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                gp.playSE(6);
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 3;
                }
            }

            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                gp.playSE(6);
                if (gp.ui.commandNum > 3) {
                    gp.ui.commandNum = 0;
                }
            }

            if (code == KeyEvent.VK_ENTER) {
                // INSTRUCTIONS
                if (gp.ui.commandNum == 0) {
                    gp.ui.commandNum = 0;
                    gp.ui.titleScreenState = 4;
                }
                // CONTROLS
                if (gp.ui.commandNum == 1) {
                    gp.ui.commandNum = 0;
                    gp.ui.titleScreenState = 5;
                }
                // REFERENCES
                if (gp.ui.commandNum == 2) {
                    gp.ui.commandNum = 0;
                    gp.ui.titleScreenState = 6;
                }

                if (gp.ui.commandNum == 3) {
                    // BACK
                    gp.ui.commandNum = 1;
                    gp.ui.titleScreenState = 0;
                }
            }
        } else if (gp.ui.titleScreenState == 3) {
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 2) {
                    // BACK
                    gp.ui.titleScreenState = 0;
                }
            }
        } else if (gp.ui.titleScreenState == 4) {
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    // BACK
                    gp.ui.commandNum = 0;
                    gp.ui.titleScreenState = 2;
                }
            }
        } else if (gp.ui.titleScreenState == 5) {
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    // BACK
                    gp.ui.commandNum = 1;
                    gp.ui.titleScreenState = 2;
                }
            }
        } else if (gp.ui.titleScreenState == 6) {
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    // BACK
                    gp.ui.commandNum = 2;
                    gp.ui.titleScreenState = 2;
                }
            }
        }
    }

    // Method during the main gameplay
    public void playState(int code) {
        // PLAY STATE

        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }

        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }

        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }

        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }

        // Check if the ESCAPE key is pressed to switch to the pause state.
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.pauseState;
        }

        // Check if the C key is pressed to switch to the character state.
        if (code == KeyEvent.VK_C) {
            gp.gameState = gp.characterState;
        }

        // Check if the ENTER key is pressed.
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;

        }

        // Check if the F key is pressed to handle shooting.
        if (code == KeyEvent.VK_F) {
            shotKeyPressed = true;
        }

        // Check if the O key is pressed to switch to the options state.
        if (code == KeyEvent.VK_O) {
            gp.gameState = gp.optionsState;
        }

        // DEBUG: Toggle a boolean flag for checking draw time.
        if (code == KeyEvent.VK_T) {
            if (checkDrawTime == false) {
                checkDrawTime = true;
            } else if (checkDrawTime == true) {
                checkDrawTime = false;
            }
        }
    }

    public void pauseState(int code) {
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
        }
    }

    public void dialogueState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            gp.gameState = gp.playState;
        }
    }

    public void characterState(int code) {
        if (code == KeyEvent.VK_C) {
            gp.gameState = gp.playState;
        }
        if (code == KeyEvent.VK_ENTER) {
            gp.player.selectItem();
        }
        playerInventory(code);
    }

    public void optionsState(int code) {
        if (code == KeyEvent.VK_O) {
            gp.gameState = gp.playState;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
            if (gp.ui.commandNum == 2) {
                gp.ui.titleScreenState = 0;
                gp.gameState = gp.titleState;
                gp.restart();
                gp.stopMusic();
            }
        }

        int maxCommandNum = 0;
        switch (gp.ui.optionsSubState) {
            case 0:
                maxCommandNum = 2;
                break;
        }
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            gp.playSE(6);
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = maxCommandNum;
            }
        }
        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            gp.playSE(6);
            if (gp.ui.commandNum > maxCommandNum) {
                gp.ui.commandNum = 0;
            }
        }

        if (code == KeyEvent.VK_A) {
            if (gp.ui.optionsSubState == 0) {
                if (gp.ui.commandNum == 0 && gp.music.volumeScale > 0) {
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playSE(6);
                }
                if (gp.ui.commandNum == 1 && gp.se.volumeScale > 0) {
                    gp.se.volumeScale--;
                    gp.playSE(6);
                }
            }
        }

        if (code == KeyEvent.VK_D) {
            if (gp.ui.optionsSubState == 0) {
                if (gp.ui.commandNum == 0 && gp.music.volumeScale < 5) {
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playSE(6);
                }

                if (gp.ui.commandNum == 1 && gp.se.volumeScale < 5) {
                    gp.se.volumeScale++;
                    gp.playSE(6);
                }
            }
        }
    }

    public void gameOverState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0) {
                gp.ui.titleScreenState = 0;
                gp.gameState = gp.titleState;
                gp.restart();
                gp.stopMusic();
            }
        }
    }

    public void winningState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0) {
                gp.ui.titleScreenState = 0;
                gp.gameState = gp.titleState;
                gp.restart();
                gp.stopMusic();
            }
        }
    }

    public void tradeState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        if (gp.ui.subState == 0) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
            }
        }

        if (gp.ui.subState == 1) {
            npcInventory(code);
            if (code == KeyEvent.VK_ESCAPE) {
                gp.ui.subState = 0;
            }
        }

        if (gp.ui.subState == 2) {
            playerInventory(code);
            if (code == KeyEvent.VK_ESCAPE) {
                gp.ui.subState = 0;
            }
        }
    }

    public void playerInventory(int code) {
        if (code == KeyEvent.VK_W) {
            if (gp.ui.playerSlotRow != 0) {
                gp.ui.playerSlotRow--;
            }
        }
        if (code == KeyEvent.VK_S) {
            if (gp.ui.playerSlotRow != 3) {
                gp.ui.playerSlotRow++;
            }
        }
        if (code == KeyEvent.VK_A) {
            if (gp.ui.playerSlotCol != 0) {
                gp.ui.playerSlotCol--;
            }
        }
        if (code == KeyEvent.VK_D) {
            if (gp.ui.playerSlotCol != 4) {
                gp.ui.playerSlotCol++;
            }
        }
    }

    public void npcInventory(int code) {
        if (code == KeyEvent.VK_W) {
            if (gp.ui.npcSlotRow != 0) {
                gp.ui.npcSlotRow--;
            }
        }
        if (code == KeyEvent.VK_S) {
            if (gp.ui.npcSlotRow != 3) {
                gp.ui.npcSlotRow++;
            }
        }
        if (code == KeyEvent.VK_A) {
            if (gp.ui.npcSlotCol != 0) {
                gp.ui.npcSlotCol--;
            }
        }
        if (code == KeyEvent.VK_D) {
            if (gp.ui.npcSlotCol != 4) {
                gp.ui.npcSlotCol++;
            }
        }
    }

    // This method handles key releases.
    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }

        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }

        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }

        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }

        if (code == KeyEvent.VK_F) {
            shotKeyPressed = false;
        }

    }

}
