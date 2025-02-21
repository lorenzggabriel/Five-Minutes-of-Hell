package object;

import entity.Projectile;
import main.GamePanel;

public class OBJ_EmptyWeapon extends Projectile {
    GamePanel gp;

    public OBJ_EmptyWeapon(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Nothing";
        attack = 0;

        description = "[" + name + "]\nNothing.";
        getImage();
    }

    public void getImage() {
        weapon = setup("/weaponImage/empty", gp.tileSize, gp.tileSize);

    }

}
