package com.veyrongaming.eternalsemester;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.veyrongaming.eternalsemester.characters.Assassin;
import com.veyrongaming.eternalsemester.characters.Character;
import com.veyrongaming.eternalsemester.weapons.Projectile;
import com.veyrongaming.eternalsemester.weapons.Weapon;

public class GameScreen implements Screen {
    private EternalSemester game;
    private OrthographicCamera camera;
    private Character character;
    private ArrayList<Enemy> enemies;
    private ArrayList<Projectile> projectiles;
    private ArrayList<Weapon> weapons;
    private long lastEnemySpawnTime;

    public GameScreen(EternalSemester game) {
        this.game = game;
        camera = new OrthographicCamera();
        character = new Assassin(game, null);
        enemies = new ArrayList<Enemy>();
        projectiles = new ArrayList<Projectile>();
        weapons = character.getWeapons();

        camera.setToOrtho(false, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        spawnEnemy();
    }

    private void spawnEnemy() {
        Texture texture = new Texture(Gdx.files.internal("enemy.png"));
        float spawnX = (Constants.VIEWPORT_WIDTH  - texture.getWidth()) / 2f + (2 * MathUtils.random(0, 1) - 1) * MathUtils.random(Constants.VIEWPORT_WIDTH / 2f, Constants.VIEWPORT_WIDTH / 2f + Constants.ENEMY_SPAWN_BUFFER); 
        float spawnY = (Constants.VIEWPORT_HEIGHT  - texture.getHeight()) / 2f + (2 * MathUtils.random(0, 1) - 1) * MathUtils.random(Constants.VIEWPORT_HEIGHT / 2f, Constants.VIEWPORT_HEIGHT / 2f + Constants.ENEMY_SPAWN_BUFFER); 
        Vector2 position = new Vector2(spawnX, spawnY);
        enemies.add(new Enemy(texture, position, 0.05f, 20));
        lastEnemySpawnTime = TimeUtils.nanoTime();
    }

    public void render(float delta) {
        // Clear the screen
        ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1f);

        game.batch.begin();

        camera.position.set(character.getPosition().x, character.getPosition().y, 0f);
        camera.update();

        game.batch.draw(character.getTexture(), character.getPosition().x - character.getTexture().getWidth() / 2f, character.getPosition().y - character.getTexture().getHeight() / 2f);
        for (Enemy enemy : enemies) {
            game.batch.draw(enemy.getTexture(), enemy.getPosition().x - enemy.getTexture().getWidth() / 2f, enemy.getPosition().y - enemy.getTexture().getHeight() / 2f);
        }
        for (Projectile projectile : projectiles) {
            game.batch.draw(projectile.getTexture(), projectile.getPosition().x - projectile.getTexture().getWidth() / 2f, projectile.getPosition().y - projectile.getTexture().getHeight() / 2f);
        }
        for (Weapon weapon : weapons) {
            // game.batch.draw(Gdx.files.internal("weapon.png"), delta, delta);
        }
        game.batch.end();

        if (TimeUtils.nanoTime() - lastEnemySpawnTime > 1000000000) {
            spawnEnemy();
        }
    }

    public void update(float delta) {
        character.update(delta);
        for (Enemy enemy : enemies) {
            enemy.update(delta, character);
        }
        for (Projectile projectile : projectiles) {
            projectile.update(delta);   
        }
        for (Weapon weapon : weapons) {
            weapon.update(delta, character, this);
        }
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

    @Override
    public void show() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show'");
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resize'");
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pause'");
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resume'");
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hide'");
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'dispose'");
    }

    public Character getCharacter() {
        return character;
    }
}
