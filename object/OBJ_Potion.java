package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion extends Entity {

    GamePanel gp;
    int value = 5;

    public OBJ_Potion(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_consumable;
        name = "Life Potion";
        down1 = setup("/objectsImage/potion", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nThis will save your life,\nyoung man! Heals " + value + ".";
        price = 100;
        getImage();
    }

    public void use(Entity entity) {
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You drank the " + name + "!\n" + "Your life has been recovered by " + value + ".";
        entity.life += value;
        if (gp.player.life > gp.player.maxLife) {
            gp.player.life = gp.player.maxLife;
        }
        gp.playSE(2);
    }

    public void getImage() {
        weapon = setup("/objectsImage/potion", gp.tileSize, gp.tileSize);
    }
}
