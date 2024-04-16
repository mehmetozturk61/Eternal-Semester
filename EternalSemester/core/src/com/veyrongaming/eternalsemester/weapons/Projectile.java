package com.veyrongaming.eternalsemester.weapons;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.veyrongaming.eternalsemester.Enemy;
import com.veyrongaming.eternalsemester.GameWorld;

public class Projectile {
    private Vector2 position;
    private Vector2 velocity;
    private float damage;
    private float lifetime;
    private GameWorld gameWorld;

    public Projectile(Vector2 position, Vector2 velocity, float damage, float lifetime, GameWorld gameWorld) {
        this.position = position;
        this.velocity = velocity;
        this.damage = damage;
        this.lifetime = lifetime;
        this.gameWorld = gameWorld;
    }

    public void update(float delta) {
        position.add(velocity.scl(delta));
        lifetime -= delta;

        checkEnemyCollision();

        if (lifetime <= 0) {
            gameWorld.removeProjectile(this);
        }
    }

    private void checkEnemyCollision() {
        ArrayList<Enemy> enemies = gameWorld.getEnemies();
        
        for (Enemy enemy : enemies) {
            if (enemy.getHitBox().contains(position)) {
                enemy.takeDamage(damage);
                enemy.applySlow(0.5f, 1f);
                gameWorld.removeProjectile(this);
                break;
            }
        }
    }
}
