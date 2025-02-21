package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    // DEBUG
     boolean checkDrawTime = false;
    
    public KeyHandler(GamePanel gp) {
    	this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        int code = e.getKeyCode();

        // TITLE STATE
        if(gp.gameState == gp.titleState) {
        	
        	if(gp.ui.titleScreenState == 0) {
        		if (code == KeyEvent.VK_W) {
            		gp.ui.commandNum--;
            		if(gp.ui.commandNum < 0) {
            			gp.ui.commandNum = 3;
            		}
                }

                if (code == KeyEvent.VK_S) {
                	gp.ui.commandNum++;
                	if(gp.ui.commandNum > 3) {
            			gp.ui.commandNum = 0;
            		}
                }
                if(code == KeyEvent.VK_ENTER) {
                	if(gp.ui.commandNum == 0) {
                		// WILL PROBABLY ADD SOMETHING HERE; STORY
                		gp.ui.titleScreenState = 1;
//                		gp.playMusic(0);
                	}
                	if(gp.ui.commandNum == 1) {
                		// ABOUT
                	}
                	if(gp.ui.commandNum == 2) {
                		// DEVELOPERS
                	}
                	if(gp.ui.commandNum == 3) {
                		System.exit(0);
                	}
                }
            }
        	
        	else if(gp.ui.titleScreenState == 1) {
        		if (code == KeyEvent.VK_W) {
            		gp.ui.commandNum--;
            		if(gp.ui.commandNum < 0) {
            			gp.ui.commandNum = 1;
            		}
                }

                if (code == KeyEvent.VK_S) {
                	gp.ui.commandNum++;
                	if(gp.ui.commandNum > 1) {
            			gp.ui.commandNum = 0;
            		}
                }
                if(code == KeyEvent.VK_ENTER) {
                	if(gp.ui.commandNum == 0) {
                		System.out.println("Can you survive?");
                		gp.gameState = gp.playState;
                		gp.playMusic(0);
                	}
                	if(gp.ui.commandNum == 1) {
                		// BACK
                		gp.ui.titleScreenState = 0;
                	}
                }
            }
        }
        	
        	
        
        // GAME STATE
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
        
        if (code == KeyEvent.VK_ESCAPE) {
        	if(gp.gameState == gp.playState) {
        		gp.gameState = gp.pauseState;
        	}
        	else if(gp.gameState == gp.pauseState) {
        		gp.gameState = gp.playState;
        	}
        }
        
        if (code == KeyEvent.VK_ENTER) {
        	enterPressed = true;
        	// SOON DI KO PA ALAM GAGAWIN SA DIALOGUES KUNG MAG IMPLEMENT BA TAYO lol
        }


        // DEBUG
        if (code == KeyEvent.VK_T) {
            if (checkDrawTime == false) {
                checkDrawTime = true;
            } else if (checkDrawTime == true) {
                checkDrawTime = false;
            }
        }
    }

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
    }

}
