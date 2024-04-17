package com.veyrongaming.eternalsemester.weapons;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.veyrongaming.eternalsemester.Enemy;
import com.veyrongaming.eternalsemester.GameScreen;

public class Projectile {
    private Texture texture;
    private Vector2 position;
    private Vector2 velocity;
    private float damage;
    private float lifetime;
    private GameScreen gameScreen;

    public Projectile(Texture texture, Vector2 position, Vector2 velocity, float damage, float lifetime, GameScreen gameScreen) {
        this.texture = texture;
        this.position = position;
        this.velocity = velocity;
        this.damage = damage;
        this.lifetime = lifetime;
        this.gameScreen = gameScreen;
    }

    public void update(float delta) {
        position.add(velocity.scl(delta));
        lifetime -= delta;

        checkEnemyCollision();

        if (lifetime <= 0) {
            gameScreen.removeProjectile(this);
        }
    }

    private void checkEnemyCollision() {
        ArrayList<Enemy> enemies = gameScreen.getEnemies();
        
        for (Enemy enemy : enemies) {
            if (enemy.getHitBox().contains(position)) {
                enemy.takeDamage(damage);
                enemy.applySlow(0.5f, 1f);
                gameScreen.removeProjectile(this);
                break;
            }
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return texture;
    }
}
