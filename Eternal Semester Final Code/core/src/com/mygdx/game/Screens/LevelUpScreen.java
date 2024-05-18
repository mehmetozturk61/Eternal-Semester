package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.AllDescriptions;
import com.mygdx.game.EternalSemester;
import com.mygdx.game.powerups.PowerUp;

import java.text.DecimalFormat;
import java.util.Random;

public class LevelUpScreen implements Screen{

    private final EternalSemester game;

    private Skin skin2 = new Skin(Gdx.files.internal("Pixthulhu/pixthulhu-ui.json"));
    private Table table;
    private Label header;
    private Stage stage; 
    private Level1 level;
    public DecimalFormat decimal;
    public int first;
    public int second;
    public int third;
    public AllDescriptions descriptions = new AllDescriptions();

    SpriteBatch batch;
    Sprite sprite;

    public LevelUpScreen(EternalSemester game, Level1 level)
    {
        this.game = game;
        this.level = level;
        Random rand = new Random();
        first = rand.nextInt(12);
        do {
            second = rand.nextInt(12);
        } while (first == second);
        do {
            third = rand.nextInt(12);
        } while (third == first || third == second);
        
    }

    @Override
    public void show() {
        stage = new Stage(new ExtendViewport(game.width,game.height));
        Gdx.input.setInputProcessor(stage);
        if (game.isFullScreen)
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        else
            Gdx.graphics.setWindowedMode(game.width, game.height);

        table = new Table();
        table.setFillParent(true);
        //table.setOrigin(game.width/2,game.height/4);
        //table.debug();

        TextButton firstButton = new TextButton("", skin2);
        firstButton.setHeight(game.height/3);
        firstButton.setWidth(game.width*2/3);
        firstButton.setPosition(game.width/6, game.height*2/3);

        TextButton secondButton = new TextButton("", skin2);
        secondButton.setHeight(game.height/3);
        secondButton.setWidth(game.width*2/3);
        secondButton.setPosition(game.width/6, game.height/3);

        TextButton thirdButton = new TextButton("", skin2);
        thirdButton.setHeight(game.height/3);
        thirdButton.setWidth(game.width*2/3);
        thirdButton.setPosition(game.width/6, 0);

        Label label1 = new Label(descriptions.allDescriptions.get(first).name + ": " + descriptions.allDescriptions.get(first).description + (getLevel(first) > 0 ? " Level: " + getLevel(first) : ""), skin2);
        Label label2 = new Label(descriptions.allDescriptions.get(second).name + ": " + descriptions.allDescriptions.get(second).description + (getLevel(second) > 0 ? " Level: " + getLevel(second) : ""), skin2);
        Label label3 = new Label(descriptions.allDescriptions.get(third).name + ": " + descriptions.allDescriptions.get(third).description + (getLevel(third) > 0 ? " Level: " + getLevel(third) : ""), skin2);
        
        label1.setPosition(game.width/6 + game.width/20, game.height*2/3 + game.height/6 - label1.getHeight()/2 );
        label2.setPosition(game.width/6 + game.width/20, game.height/3 + game.height/6 - label1.getHeight()/2 );
        label3.setPosition(game.width/6 + game.width/20, game.height/6 - label1.getHeight()/2);

        firstButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                upgrade(first);
                game.dorm.level1Screen.checker = 0;
                dispose();
                game.setScreen(game.dorm.level1Screen);
            }
        });

        secondButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                upgrade(second);
                game.dorm.level1Screen.checker = 0;
                dispose();
                game.setScreen(game.dorm.level1Screen);
            }
        });

        thirdButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                upgrade(third);
                game.dorm.level1Screen.checker = 0;
                dispose();
                game.setScreen(game.dorm.level1Screen);
            }
        });

        
        stage.addActor(firstButton);
        stage.addActor(secondButton);
        stage.addActor(thirdButton);
        stage.addActor(label1);
        stage.addActor(label2);
        stage.addActor(label3);
    }

    public void upgrade(int number) {
        if (number == 0) game.dorm.level1Screen.acidLevel++;
        else if (number == 1) game.dorm.level1Screen.darkBoltLevel++;
        else if (number == 2) game.dorm.level1Screen.iceShardLevel++;
        else if (number == 3) game.dorm.level1Screen.rotatingFireLevel++;
        else if (number == 4) game.dorm.level1Screen.shockBombLevel++;
        else {
            PowerUp powerUp = new PowerUp(game, game.dorm.level1Screen.world, game.dorm.level1Screen.player, PowerUp.Type.values()[number - 5]);
            powerUp.applyEffect();
        }
    }

    public int getLevel(int number) {
        if (number == 0) return game.dorm.level1Screen.acidLevel ;
        else if (number == 1) return game.dorm.level1Screen.darkBoltLevel ;
        else if (number == 2) return game.dorm.level1Screen.iceShardLevel ;
        else if (number == 3) return game.dorm.level1Screen.rotatingFireLevel ;
        else if (number == 4) return game.dorm.level1Screen.shockBombLevel ;
        else return 0;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
       
    }

    @Override
    public void resume() {
       
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
       
    }

    @Override
    public void dispose() {
        stage.dispose();
        table.clear();
        table.remove();
    }
    
}
