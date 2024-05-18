package com.mygdx.game.Screens;

import java.text.DecimalFormat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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
import com.mygdx.game.Constants;
import com.mygdx.game.DatabaseUtil;
import com.mygdx.game.EternalSemester;

public class Died implements Screen{

    private final EternalSemester game;

    private Skin skin2 = new Skin(Gdx.files.internal("Pixthulhu/pixthulhu-ui.json"));
    private Table table;
    private Label header;
    private Stage stage; 
    private Levels level;
    public DecimalFormat decimal;

    SpriteBatch batch;
    Sprite sprite;



    public Died(EternalSemester game, Levels level)
    {
        this.game = game;
        this.level = level;
        decimal = new DecimalFormat();
        decimal.setMaximumFractionDigits(0);
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
        stage.addActor(table);


        Label gameName = new Label("Game Over", skin2);
        gameName.setFontScale(game.height/200);
        float labelWidth = gameName.getPrefWidth();
        float labelHeight = gameName.getPrefHeight();
        gameName.setBounds(0,0, labelWidth, labelHeight);
        gameName.setPosition((game.width-labelWidth)/2, (game.height-labelHeight/2));
        //gameName.debug();
        //stage.addActor(gameName);
        table.add(gameName).colspan(2);
        table.row();

        Label timer = new Label("Time: " + decimal.format(level.hud.timerMinute) + ":" + decimal.format(level.hud.timerSecond), skin2);
        timer.setFontScale(game.height/540);
        table.add(timer).colspan(2).padTop(game.height/10);
        table.row();

        Label killCount = new Label("Kill Count: " + level.killCount, skin2);
        DatabaseUtil.updateKillCount(game.username, 1, level.killCount);
        killCount.setFontScale(game.height/540);
        table.add(killCount).colspan(2).padTop(game.height/10);
        table.row();

        TextButton start = new TextButton("Main Menu", skin2);
            start.getLabel().setFontScale(1);; // Increase font size
            //table.add(start).width(300).height(100).pad(10); // Increase button size and add padding
        table.add(start).padTop(game.height/10).colspan(2);
            table.row();
        start.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Constants.soundEffect.play(game.soundEffectVolume);
                    game.setScreen(game.mainMenuScreen);
                }
            });

        table.center();



        batch = new SpriteBatch();
        sprite = new Sprite(new Texture(Gdx.files.internal("background2.jpg")));
        sprite.setSize(1920, 1080);


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT );
        stage.act(delta); 
        batch.begin();
        sprite.draw(batch);
        batch.end();
        
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
        // TODO Auto-generated method stub
     
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        
    }
    
}
