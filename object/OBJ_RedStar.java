package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_RedStar extends Entity {
    public OBJ_RedStar(GamePanel gp) {
        super(gp);

        name = "RedStar";
        down1 = setup("/objectsImage/red_star", gp.tileSize, gp.tileSize);
        image = setup("/objectsImage/red_star", gp.tileSize, gp.tileSize);
    }
}
