package object;

import entity.Projectile;
import main.GamePanel;

public class OBJ_ShotgunBullet extends Projectile {
	GamePanel gp;

	public OBJ_ShotgunBullet(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_shotgun;
		name = "Shotgun";
		speed = 20;
		maxLife = 10; // 80 frames till the bullet disappear in the world or how far the bullet
						// reaches
		life = maxLife;
		attack = 6;
		alive = false;
		price = 500;
		description = "[" + name + "]\nA gun that will blow up\nthe enemies.";
		getImage();
	}

	public void getImage() {
		weapon = setup("/weaponImage/weapon_shotgun", gp.tileSize, gp.tileSize);
		up1 = setup("/projectileImage/shotgun_up", gp.tileSize, gp.tileSize);
		up2 = setup("/projectileImage/shotgun_up", gp.tileSize, gp.tileSize);
		down1 = setup("/projectileImage/shotgun_down", gp.tileSize, gp.tileSize);
		down2 = setup("/projectileImage/shotgun_down", gp.tileSize, gp.tileSize);
		left1 = setup("/projectileImage/shotgun_left", gp.tileSize, gp.tileSize);
		left2 = setup("/projectileImage/shotgun_left", gp.tileSize, gp.tileSize);
		right1 = setup("/projectileImage/shotgun_right", gp.tileSize, gp.tileSize);
		right2 = setup("/projectileImage/shotgun_right", gp.tileSize, gp.tileSize);
	}

}