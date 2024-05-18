package com.mygdx.game.Screens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Constants;
import com.mygdx.game.EternalSemester;
import com.mygdx.game.GameContactListener;
import com.mygdx.game.Bosses.Boss;
import com.mygdx.game.Bosses.WidowOfSin;
import com.mygdx.game.Enemies.Enemy;
import com.mygdx.game.Enemies.Hunter;
import com.mygdx.game.Enemies.TamedBeast;
import com.mygdx.game.Enemies.TribeWarrior;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Scenes.StopScene;
import com.mygdx.game.characters.Assassin;
import com.mygdx.game.characters.Player;
import com.mygdx.game.characters.Tank;

import com.mygdx.game.powerups.PowerUp;
import com.mygdx.game.weapons.*;

public class Level1 extends Levels implements Screen {
    public static final float TIME_LIMIT = 20 * 60f;

    public EternalSemester game;

    public OrthographicCamera camera;
    public World world;
    public Player player;
    public ArrayList<Weapon> weapons;
    public ArrayList<Projectile> projectiles;
    public ArrayList<Boss> bosses;
    public Boss boss;
    public float acidTimer = 0;
    public float shardTimer = 0;
    public float gameTimer = 0;
    public float bombTimer = 0;
    public float boltTimer = 0;
    public boolean notSpawned = true;
    public Viewport port;
    private StopScene stopScene;
    public int acidLevel = 0;
    public int darkBoltLevel = 0;
    public int iceShardLevel = 0;
    public int rotatingFireLevel = 0;
    public int shockBombLevel = 0;

    public int requiredXP = 40;
    public boolean isPaused = false;
    public TextButton exit;

    public TmxMapLoader mapLoader;
    public TiledMap map;
    public OrthogonalTiledMapRenderer otmr;
    public Box2DDebugRenderer b2dr;
    private Stage stage;
    private Stage stage2;
    private Window pause;
    private Skin skin2 = new Skin(Gdx.files.internal("Pixthulhu/pixthulhu-ui.json"));
    public int checker = 0;
    public boolean cond = true;
    SpriteBatch batch;
    Sprite sprite;


    public Level1(EternalSemester game) {
        this.game = game;
        camera = new OrthographicCamera();

        //stage = new Stage(new FitViewport(game.width,game.height,camera));
        port = new FitViewport(game.width ,game.height,camera);
        stage = new Stage(port);

        Gdx.input.setInputProcessor(stage);
        if (game.isFullScreen)
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        else
            Gdx.graphics.setWindowedMode(game.width, game.height);

        world = new World(new Vector2(0, 0), false);
        world.setContactListener(new GameContactListener());
        player = Constants.isTank ? new Tank(game, world , new Vector2(9200,6500)) : new Assassin(game, world , new Vector2(9200,6500)); // BİR İŞARET
        stopScene = new StopScene(game, game.batch);
        batch = new SpriteBatch();
        sprite = new Sprite(new Texture(Gdx.files.internal("blackscreen.png")));
        sprite.setColor(sprite.getColor().r, sprite.getColor().g, sprite.getColor().b, 0.7f);
        sprite.setSize(1920, 1080);
        hud = new Hud(game, game.batch, player);
        enemies = new ArrayList<Enemy>();
        skills = new ArrayList<Skill>();
        bosses = new ArrayList<Boss>();


        if (player instanceof Assassin) skills.add(new Dagger(game, world, player));
        else if (player instanceof Tank) skills.add(new BattleAxe(game, world, player));
        /* else if (player instanceof Warrior) skills.add(new Sword());
        else if (player instanceof Wizard) skills.add(new Staff()); */

        player.setSkill(skills.get(0));

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("maps/maps/campus1.tmx");
        otmr = new OrthogonalTiledMapRenderer(map, 3 / Constants.PPM);
        b2dr = new Box2DDebugRenderer();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for(MapObject object: map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) *3 / Constants.PPM, (rect.getY() + rect.getHeight() / 2)* 3 / Constants.PPM);
            body = world.createBody(bdef);
            shape.setAsBox((rect.getWidth() / 2) * 3/ Constants.PPM, (rect.getHeight() / 2) * 3/ Constants.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        camera.zoom -= 5f/6f;
    }

    @Override
    public void render(float delta) {
        if(isPaused)
        {
            delta = 0;
        }
        if (player.xp >= requiredXP && checker==0)
        {
            delta = 0;
            checker++;
            game.setScreen(new LevelUpScreen(game, this));
            hud.xpBar.setValue(0);
            player.xp = 0;
            requiredXP += 40;
            hud.xpBar.setRange(0,requiredXP);

        }
        update(delta);

        if (player.health <=0 && checker==0)
        {
            checker++;
            Constants.bossMusic.pause();
            Constants.gameMusic.pause();
            Constants.music.play();
            Constants.music.setLooping(true);
            game.setScreen(new Died(game,this));
        }
        Gdx.gl.glClearColor(0.2f, 0.5f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        otmr.render();

        game.batch.setProjectionMatrix(camera.combined);
        otmr.render();

        game.batch.begin();

        for (Enemy enemy : enemies) {
            enemy.draw(delta);
        }
        for (Boss boss : bosses) {
            boss.draw(delta);
        }
        player.draw(delta);
        for (Skill skill : skills) {
            skill.draw(delta);
        }

        game.batch.end();
        hud.stage.draw();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);

        if (isPaused)
        {
            stopScene.stage.act();
            batch.begin();
            sprite.draw(batch);
            batch.end();
            stopScene.stage.draw();
        }

        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE))
        {
            delta = 0;
            gameStop();
            //stopScene.dispose();
        }

        //b2dr.render(world, camera.combined);
        world.step(delta, 6, 2);
        stage.draw();
    }

    public void update(float delta) {
        if (rotatingFireLevel > 0 && cond) {
            skills.add(new RotatingFire(game,world,player,player.position));
            cond = false;
        }
        if (player.xp > requiredXP) {
            //isPaused = true;
            /*Scanner scanner = new Scanner(System.in);
            PowerUp.Type[] powerUps = PowerUp.Type.values();
            ArrayList<PowerUp.Type> powerUpTypes = new ArrayList<PowerUp.Type>(powerUps.length);
            for (int i = 0; i < powerUps.length; i++) {
                powerUpTypes.add(powerUps[i]);
            }
            Collections.shuffle(powerUpTypes);
            System.out.println("1) " + powerUpTypes.get(0).name());
            System.out.println("2) " + powerUpTypes.get(1).name());
            System.out.println("3) " + powerUpTypes.get(2).name());
            int option = scanner.nextInt();
            new PowerUp(game, world, player, powerUpTypes.get(option));
            requiredXP *= 2;*/
        }

        gameTimer += delta;
        spawnEnemies();

        player.update(delta);

        shardTimer += delta;
        acidTimer += delta;
        boltTimer += delta;
        bombTimer += delta;

        if (iceShardLevel > 0 && shardTimer >= 1.5f) {
            shardTimer = 0;
            for (int i = 0; i < iceShardLevel; i++)
                skills.add(new IceShard(game, world, player, player.position));
        }
        if(acidLevel > 0 && acidTimer >= 3f) {
            acidTimer = 0;
            skills.add(new Acid(game, world, player, this , 20*acidLevel));
        }

        if(darkBoltLevel > 0 && boltTimer + darkBoltLevel >= 6f) {
            boltTimer = 0;
            skills.add(new DarkBolt(game, world, player, this));
        }
        if(shockBombLevel > 0 && bombTimer >= 3.5f)
        {
            bombTimer = 0;
            for (int i = 0; i < shockBombLevel; i++)
                skills.add(new ShockBomb(game,world,player,this));
        }
        hud.update(delta);
        camera.position.set(player.position, 0);
        camera.update();
        otmr.setView(camera);

        ArrayList<Enemy> enemiesCopy = new ArrayList<Enemy>(enemies);
        for (Enemy enemy : enemiesCopy) {
            enemy.update(delta, player);

        }
        ArrayList<Skill> skillsCopy = new ArrayList<Skill>(skills);
        for (Skill skill : skillsCopy) {
            skill.update(delta);
        }
        ArrayList<Boss> bossesCopy = new ArrayList<Boss>(bosses);
        for (Boss boss : bossesCopy) {
            boss.update(delta, player);

            if (boss.health <= 0 && boss.stateTimer  >= 0.8 + 0.15f) {
                player.gainXP(boss.xp);
                bosses.remove(boss);
                boss.dispose();
                killCount++;
            }
        }
    }

    public void spawnEnemies() {
        Enemy enemy;

        float enemiesPerSecond = 2 + 40 * gameTimer / TIME_LIMIT;
        float tribeWarriorSpawnRate = 1 - gameTimer / TIME_LIMIT;
        float hunterSpawnRate = 1 - Math.abs(gameTimer - TIME_LIMIT / 2) / (TIME_LIMIT / 2);
        float tamedBeastSpawnRate = gameTimer / TIME_LIMIT;
        if(gameTimer > 120f && notSpawned)
        {
            boss = new WidowOfSin(game, world, player);
            bosses.add(boss);
            notSpawned = false;
            Constants.gameMusic.pause();
            Constants.bossMusic.play();
            Constants.bossMusic.setLooping(true);
        }

        if ((float) Math.random() < enemiesPerSecond / 60f / 4) {
            float enemyType = (float) Math.random();

            if (enemyType < tribeWarriorSpawnRate / (tribeWarriorSpawnRate + hunterSpawnRate + tamedBeastSpawnRate)) {
                enemy = new TribeWarrior(game, world, player , this);
            }
            else if (enemyType < (tribeWarriorSpawnRate + hunterSpawnRate) / (tribeWarriorSpawnRate + hunterSpawnRate + tamedBeastSpawnRate)) {
                enemy = new Hunter(game, world, player , this);
            }
            else {
                enemy = new TamedBeast(game, world, player , this);
            }

            enemies.add(enemy);
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {


    }
    public void gameStop(){
        isPaused = !isPaused;
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
    public Hud getHud(){ return hud; }
    public StopScene getStopScene(){ return stopScene; }

}
