package object;

import entity.Projectile;
import main.GamePanel;

public class OBJ_MinigunBullet extends Projectile {
	GamePanel gp;

	public OBJ_MinigunBullet(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_minigun;
		name = "Minigun";
		speed = 20;
		maxLife = 40; // 80 frames till the bullet disappear in the world or how far the bullet
						// reaches
		life = maxLife;
		attack = 2;
		alive = false;
		price = 1000;
		description = "[" + name + "]\nA gun that can instill\nfear in your enemies.";
		getImage();
	}

	public void getImage() {
		weapon = setup("/weaponImage/weapon_minigun", gp.tileSize, gp.tileSize);
		up1 = setup("/projectileImage/minigun_up", gp.tileSize, gp.tileSize);
		up2 = setup("/projectileImage/minigun_up", gp.tileSize, gp.tileSize);
		down1 = setup("/projectileImage/minigun_down", gp.tileSize, gp.tileSize);
		down2 = setup("/projectileImage/minigun_down", gp.tileSize, gp.tileSize);
		left1 = setup("/projectileImage/minigun_left", gp.tileSize, gp.tileSize);
		left2 = setup("/projectileImage/minigun_left", gp.tileSize, gp.tileSize);
		right1 = setup("/projectileImage/minigun_right", gp.tileSize, gp.tileSize);
		right2 = setup("/projectileImage/minigun_right", gp.tileSize, gp.tileSize);
	}

}