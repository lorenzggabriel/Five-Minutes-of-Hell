package object;

import entity.Projectile;
import main.GamePanel;

public class OBJ_RifleBullet extends Projectile {
	GamePanel gp;

	public OBJ_RifleBullet(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_rifle;
		name = "Rifle";
		speed = 20;
		maxLife = 40; // 80 frames till the bullet disappear in the world or how far the bullet
						// reaches
		life = maxLife;
		attack = 2;
		alive = false;
		price = 200;
		description = "[" + name + "]\nA gun that can strike\nthrough enemies' heads.";
		getImage();
	}

	public void getImage() {
		weapon = setup("/weaponImage/weapon_rifle", gp.tileSize, gp.tileSize);
		up1 = setup("/projectileImage/rifle_up", gp.tileSize, gp.tileSize);
		up2 = setup("/projectileImage/rifle_up", gp.tileSize, gp.tileSize);
		down1 = setup("/projectileImage/rifle_down", gp.tileSize, gp.tileSize);
		down2 = setup("/projectileImage/rifle_down", gp.tileSize, gp.tileSize);
		left1 = setup("/projectileImage/rifle_left", gp.tileSize, gp.tileSize);
		left2 = setup("/projectileImage/rifle_left", gp.tileSize, gp.tileSize);
		right1 = setup("/projectileImage/rifle_right", gp.tileSize, gp.tileSize);
		right2 = setup("/projectileImage/rifle_right", gp.tileSize, gp.tileSize);
	}

}