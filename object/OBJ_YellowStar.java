package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_YellowStar extends Entity{
	public OBJ_YellowStar(GamePanel gp) {
    	super(gp);
    	
        name = "YellowStar";
        down1 = setup("/objectsImage/yellow_star",gp.tileSize,gp.tileSize);
        image = setup("/objectsImage/yellow_star",gp.tileSize,gp.tileSize);
    }
}
