package projetocoo.model.base;

import java.util.ArrayList;
import java.util.List;

import projetocoo.MainGame;
import projetocoo.model.Enemy1;
import projetocoo.model.Enemy2;
import projetocoo.model.EnemyProjectile;
import projetocoo.model.movements.MovementType;
import projetocoo.model.shooter.ActiveShooter;
import projetocoo.model.shooter.InactiveShooter;

public abstract class Enemy extends Shooter {
	private double angle;
	private double rv;
	private MovementType movementType;
	private static final int NUMBER_PROJECTILES = 200;

	public Enemy(double radius) {
		super();
		setRadius(radius);
		List<EnemyProjectile> projectiles = new ArrayList<EnemyProjectile>();
		for (int i = 0; i < NUMBER_PROJECTILES; i++) {
			projectiles.add(new EnemyProjectile());
		}
		setProjectiles(projectiles);
	}

	public void updatePosition() {
		this.movementType.updatePosition(this);
	}

	public MovementType getMovementType() {
		return movementType;
	}

	public void setMovementType(MovementType movementType) {
		this.movementType = movementType;
	}

	public double getAngle() {
		return angle;
	}

	public double getRv() {
		return rv;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public void setRv(double rv) {
		this.rv = rv;
	}

	public static int getActiveEnemiesCount(List<? extends Enemy> enemies) {
		int x = 0;
		for (Enemy e : enemies) {
			if (e.getState() instanceof ActiveShooter)
				x++;
		}
		return x;
	}

	@Override
	public boolean checkCollision(Element e) {
		double dx = getX() - e.getX();
		double dy = getY() - e.getY();
		double dist = Math.sqrt(dx * dx + dy * dy);
		return dist < getRadius();
	}

	public static void Spawn(List<? extends Enemy> enemies) {

		long nextEnemyDelay = 0;
		MainGame mainGame = MainGame.getInstance();

		if (enemies.get(0) instanceof Enemy1) {
			nextEnemyDelay = mainGame.getNextEnemy1Delay();
		} else if (enemies.get(0) instanceof Enemy2) {
			nextEnemyDelay = mainGame.getNextEnemy2Delay();
		}

		if (mainGame.getCurrentTime() > nextEnemyDelay) {

			for (Enemy e : enemies) {

				if (e.getState() instanceof InactiveShooter) {

					e.spawn();
					return;
				}

			}
		}

	}

	public abstract void spawn();

}
