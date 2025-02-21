package object;

import entity.Projectile;
import main.GamePanel;

public class OBJ_PistolBullet extends Projectile {
	GamePanel gp;

	public OBJ_PistolBullet(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_pistol;
		name = "Pistol";
		speed = 20;
		maxLife = 30; // 80 frames till the bullet disappear in the world or how far the bullet
						// reaches
		life = maxLife;
		attack = 2;
		alive = false;
		price = 20;
		description = "[" + name + "]\nA simple gun used to\nattack simple enemies.";
		getImage();
	}

	public void getImage() {
		weapon = setup("/weaponImage/weapon_pistol", gp.tileSize, gp.tileSize);
		up1 = setup("/projectileImage/pistol_up", gp.tileSize, gp.tileSize);
		up2 = setup("/projectileImage/pistol_up", gp.tileSize, gp.tileSize);
		down1 = setup("/projectileImage/pistol_down", gp.tileSize, gp.tileSize);
		down2 = setup("/projectileImage/pistol_down", gp.tileSize, gp.tileSize);
		left1 = setup("/projectileImage/pistol_left", gp.tileSize, gp.tileSize);
		left2 = setup("/projectileImage/pistol_left", gp.tileSize, gp.tileSize);
		right1 = setup("/projectileImage/pistol_right", gp.tileSize, gp.tileSize);
		right2 = setup("/projectileImage/pistol_right", gp.tileSize, gp.tileSize);
	}

}
