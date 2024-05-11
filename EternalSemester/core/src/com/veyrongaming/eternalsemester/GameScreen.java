package com.veyrongaming.eternalsemester;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.veyrongaming.eternalsemester.characters.Assassin;
import com.veyrongaming.eternalsemester.characters.Character;
import com.veyrongaming.eternalsemester.characters.Tank;
import com.veyrongaming.eternalsemester.weapons.Weapon;

public class GameScreen implements Screen {
    public EternalSemester game;
    private OrthographicCamera camera;
    private Character character;
    private ArrayList<Enemy> enemies;
    private ArrayList<Weapon> weapons;
    private long lastEnemySpawnTime;
    public World world;
    private Box2DDebugRenderer b2dr;

    public GameScreen(EternalSemester game) {
        this.game = game;
        camera = new OrthographicCamera();
        enemies = new ArrayList<Enemy>();
        world = new World(new Vector2(0, 0), false);
        world.setContactListener(new GameContanctListener());
        character = new Tank(game, null, world);
        weapons = character.getWeapons();
        b2dr = new Box2DDebugRenderer();

        camera.setToOrtho(false, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        spawnEnemy();
    }

    private void spawnEnemy() {
        Texture texture = new Texture(Gdx.files.internal("enemy.png"));
        Rectangle screenArea = new Rectangle(0, 0, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        float spawnX;
        float spawnY;

        do {
            spawnX = MathUtils.random(-Constants.ENEMY_SPAWN_BUFFER - texture.getWidth(), Constants.VIEWPORT_WIDTH + Constants.ENEMY_SPAWN_BUFFER);
            spawnY = MathUtils.random(-Constants.ENEMY_SPAWN_BUFFER - texture.getHeight(), Constants.VIEWPORT_HEIGHT + Constants.ENEMY_SPAWN_BUFFER);
        }
        while (screenArea.contains(spawnX, spawnY));

        Vector2 position = new Vector2(spawnX, spawnY);
        enemies.add(new Enemy(texture, position, 15, 0.1f, 20, world));
        lastEnemySpawnTime = TimeUtils.nanoTime();
    }

    public void render(float delta) {
        // Clear the screen
        ScreenUtils.clear(0.2f, 0.5f, 0.3f, 1f);

        game.batch.begin();
        
        character.draw();
        
        for (Enemy enemy : enemies) {
            game.batch.draw(enemy.getTexture(), enemy.getPosition().x - enemy.getTexture().getWidth() / 10f, enemy.getPosition().y - enemy.getTexture().getHeight() / 10f, enemy.getTexture().getWidth() / 5, enemy.getTexture().getWidth() / 5);
        }
        for (Weapon weapon : weapons) {
            // game.batch.draw(Gdx.files.internal("weapon.png"), delta, delta);
        }
        game.batch.end();

        if (TimeUtils.nanoTime() - lastEnemySpawnTime > 1000000000) {
            spawnEnemy();
        }

        world.step(1/144f, 6, 2);
        b2dr.render(world, camera.combined);
    }

    public void update(float delta) {
        character.update(delta);

        ArrayList<Enemy> enemiesCopy = new ArrayList<Enemy>(enemies);
        for (Enemy enemy : enemiesCopy) {
            enemy.update(delta, character);

            if(enemy.isDead()) {
                enemies.remove(enemy);
                enemy.dispose();
            }
        }
        for (Weapon weapon : weapons) {
            weapon.update(delta, character, this);
        }
    }

    private Character chooseCharacter() {
        return new Assassin(game, null, world);
        // TODO
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        b2dr.dispose();
        //character.dispose();
        for (Enemy enemy : enemies) {
            enemy.getTexture().dispose();
        }
        for (Weapon weapon : weapons) {
            //weapon.getTexture().dispose();
        }
    }

    public Character getCharacter() {
        return character;
    }
}
