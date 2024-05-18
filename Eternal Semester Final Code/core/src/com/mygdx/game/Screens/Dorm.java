package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Constants;
import com.mygdx.game.DormContactListener;
import com.mygdx.game.EternalSemester;
import com.mygdx.game.GameContactListener;
import com.mygdx.game.characters.Assassin;
import com.mygdx.game.characters.Player;
import com.mygdx.game.characters.Tank;
import com.mygdx.game.createObstacle;
import com.mygdx.game.createInteractive;

import java.util.ArrayList;

public class Dorm extends Levels implements Screen {

    public EternalSemester game;

    public OrthographicCamera camera;
    public World world;
    public Player player;

    public Viewport port;

    public TmxMapLoader mapLoader;
    public TiledMap map;
    public OrthogonalTiledMapRenderer otmr;
    public Box2DDebugRenderer b2dr;
    private Stage stage;
    public Level1 level1Screen;
    public Leaderboard leaderboard;


    public Dorm(EternalSemester game , Leaderboard leaderboard) {
        this.game = game;
        camera = new OrthographicCamera();
        this.leaderboard = leaderboard;

        //stage = new Stage(new FitViewport(game.width,game.height,camera));
        port = new FitViewport(game.width ,game.height,camera);
        stage = new Stage(port);



        Gdx.input.setInputProcessor(stage);
        if (game.isFullScreen)
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        else
            Gdx.graphics.setWindowedMode(game.width, game.height);

        world = new World(new Vector2(0, 0), false);
        world.setContactListener(new DormContactListener());
        player = new Tank(game, world, new Vector2(900,900)); // BİR İŞARET


        mapLoader = new TmxMapLoader();
        map = mapLoader.load("maps/maps/dorm.tmx");
        otmr = new OrthogonalTiledMapRenderer(map, 3 / Constants.PPM);
        b2dr = new Box2DDebugRenderer();

        new createObstacle(map,world);

        new createInteractive(map,world);

        camera.zoom -= 5f/6f;
    }

    @Override
    public void render(float delta) {
        update(delta);

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
        {
            game.setScreen(game.mainMenuScreen);
        }

        Gdx.gl.glClearColor(0.2f, 0.5f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        otmr.render();

        game.batch.setProjectionMatrix(camera.combined);
        otmr.render();

        game.batch.begin();

        player.draw(delta);

        game.batch.end();

        //b2dr.render(world, camera.combined);
        world.step(1/60f, 6, 2);
        stage.draw();
    }
    public void update(float delta) {

        if(Constants.enterGame)
        {
            game.setScreen(level1Screen = new Level1(game));
            Constants.enterGame = false;
            Constants.music.pause();
            Constants.gameMusic.play();
            Constants.gameMusic.setLooping(true);
        }
        player.update(delta);
        if(Constants.swapPlayer)
        {
            if(player instanceof Tank)
            {
                player.dispose();
                player = new Assassin(game,world, new Vector2( 900 ,  900));
                Constants.isTank = false;
            }
            else if(player instanceof Assassin)
            {
                player.dispose();
                player = new Tank(game, world, new Vector2( 900 ,  900));
                Constants.isTank = true;
            }
            Constants.swapPlayer = false;
        }
        if(Constants.openLeaderboard)
        {
            game.setScreen(game.leaderboardScreen);
            Constants.openLeaderboard = false;
        }
        camera.position.set(player.position, 0);
        camera.update();
        otmr.setView(camera);


    }


    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
