package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_GreenStar extends Entity{
	public OBJ_GreenStar(GamePanel gp) {
    	super(gp);
    	
        name = "GreenStar";
        down1 = setup("/objectsImage/green_star",gp.tileSize,gp.tileSize);
        image = setup("/objectsImage/green_star",gp.tileSize,gp.tileSize);
    }
}
