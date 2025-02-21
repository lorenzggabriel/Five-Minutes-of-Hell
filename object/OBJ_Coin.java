package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Coin extends Entity {

    public OBJ_Coin(GamePanel gp) {
        super(gp);

        name = "Coin";
        image = setup("/objectsImage/coin", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nTrade it out with better guns!.";
    }
}
