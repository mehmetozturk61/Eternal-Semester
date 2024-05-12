package com.veyrongaming.eternalsemester.Screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.veyrongaming.eternalsemester.Constants;
import com.veyrongaming.eternalsemester.Enemy;
import com.veyrongaming.eternalsemester.EternalSemester;
import com.veyrongaming.eternalsemester.characters.Assassin;
import com.veyrongaming.eternalsemester.characters.Player;
import com.veyrongaming.eternalsemester.characters.Tank;
import com.veyrongaming.eternalsemester.characters.Warrior;
import com.veyrongaming.eternalsemester.characters.Wizard;
import com.veyrongaming.eternalsemester.weapons.BattleAxe;
import com.veyrongaming.eternalsemester.weapons.Dagger;
import com.veyrongaming.eternalsemester.weapons.Sword;
import com.veyrongaming.eternalsemester.weapons.Weapon;

public class Level1 implements Screen {
    public EternalSemester game;
    public Viewport port;
    public OrthographicCamera camera;
    public World world;
    public Player player;
    public ArrayList<Enemy> enemies;
    public ArrayList<Weapon> weapons;
    //public Boss boss;

    public TmxMapLoader mapLoader;
    public TiledMap map;
	public OrthogonalTiledMapRenderer otmr;
    public Box2DDebugRenderer b2dr;


    public Level1(EternalSemester game) {
        this.game = game;
        camera = new OrthographicCamera();
        port = new FitViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT, camera);
        world = new World(new Vector2(0, 0), false);
        player = new Tank(game, world); // BİR İŞARET
        enemies = new ArrayList<Enemy>();
        weapons = new ArrayList<Weapon>();
        //boss = new Level1Boss();
        
        /* if (player instanceof Assassin) weapons.add(new Dagger());
        else */  if (player instanceof Tank) weapons.add(new BattleAxe(game, world, player));
        /* else if (player instanceof Warrior) weapons.add(new Sword());
        else if (player instanceof Wizard) weapons.add(new Staff()); */
 
        player.setWeapon(weapons.get(0));

        /* mapLoader = new TmxMapLoader();
        map = mapLoader.load("campus1.tmx");
        otmr = new OrthogonalTiledMapRenderer(map); */
        b2dr = new Box2DDebugRenderer();

        camera.position.set(Constants.VIEWPORT_WIDTH / 2, Constants.VIEWPORT_HEIGHT / 2, 0);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0.2f, 0.5f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        player.draw(delta);
        for (Enemy enemy : enemies) {
            enemy.draw(delta);
        }
        for (Weapon weapon : weapons) {
            weapon.draw(delta);
        }
        //boss.draw(); // Boss bi süreden sonra gelcek

        game.batch.end();

        /* otmr.render(); */
        b2dr.render(world, camera.combined);
        world.step(1/60f, 6, 2);
    }

    public void update(float delta) {
        player.update(delta);

        camera.position.set(player.position, 0);
        camera.update();

        ArrayList<Enemy> enemiesCopy = new ArrayList<Enemy>(enemies);
        for (Enemy enemy : enemiesCopy) {
            enemy.update(delta, player);

            if (enemy.isDead()) {
                enemies.remove(enemy);
                enemy.dispose();
            }
        }
        for (Weapon weapon : weapons) {
            weapon.update(delta);
        }
        //boss.update();
    }

    @Override
    public void resize(int width, int height) {
        port.update(width, height);
    }

    @Override
    public void show() {
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
        /* otmr.dispose();
        b2dr.dispose();
        player.dispose();
        boss.dispose();
        for (Enemy enemy : enemies) {
            enemy.dispose();
        }
        for (Weapon weapon : weapons) {
            weapon.dispose();
        }
        map.dispose();
        world.dispose(); */
    }
}
