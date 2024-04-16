package com.veyrongaming.eternalsemester;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.veyrongaming.eternalsemester.characters.Assassin;
import com.veyrongaming.eternalsemester.weapons.Projectile;

public class GameWorld {
    private Character character;
    private ArrayList<Enemy> enemies;
    private ArrayList<Projectile> projectiles;

    public GameWorld() {
        enemies = new ArrayList<Enemy>();
        projectiles = new ArrayList<Projectile>();
    }

    public void render() {
        // Clear the screen
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        character.draw(spriteBatch);
    }

    public void addProjectile(Projectile projectile) {
        projectiles.add(projectile);
    }

    public void removeProjectile(Projectile projectile) {
        projectiles.remove(projectile);
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
}
