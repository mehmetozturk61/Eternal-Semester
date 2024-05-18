package com.mygdx.game.Scenes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Constants;
import com.mygdx.game.EternalSemester;
import com.mygdx.game.characters.Assassin;
import com.mygdx.game.characters.Player;
import com.mygdx.game.characters.Tank;

import java.text.DecimalFormat;

public class Hud implements Disposable {

    public Stage stage;
    private Viewport viewport;
    public EternalSemester game;
    public Player player;
    private Skin skin1 = new Skin(Gdx.files.internal("BasicAsset/uiskin.json"));
    private Skin skin2 = new Skin(Gdx.files.internal("Pixthulhu/pixthulhu-ui.json"));
    private Skin skin3 = new Skin(Gdx.files.internal("GoldenPack/golden-ui-skin.json"));

    //Mario score/time Tracking Variables
    public Integer timerMinute;
    public float timerSecond;
    private Integer cooldownTimer;
    public Viewport port;
    public DecimalFormat decimal;

     
    public ProgressBar healthBar;
    public ProgressBar xpBar;
    
    private static Integer score;
    
    
   

    //Scene2D widgets
    private Label timerLabel;
    private Label countdownLabel;
    private static Label scoreLabel;
    private Label levelLabel;
    private Texture texture;
    public int requiredXP = 40;
    private Label cooldownTimerLabel;
    
    public Hud(EternalSemester game, SpriteBatch sb, Player player){

        this.game = game;
        this.player = player;
        timerMinute = 0;
        timerSecond = 0;

        healthBar = new ProgressBar(0, player.maxHealth,1 , false, skin2);
        //healthBar.setColor();
        healthBar.setValue(player.health);
        float newWidth = healthBar.getWidth()*5 ;
        float newHeight = healthBar.getHeight() ;
        healthBar.setWidth(newWidth);
        healthBar.setHeight(newHeight);
        healthBar.setBounds(healthBar.getX(), healthBar.getY(), newWidth, newHeight);




        //healthBar.setBounds(0,0, healthBar.getPrefWidth()*5, healthBar.getPrefHeight());

        
        xpBar = new ProgressBar(0,40 , 1, false, skin1 );

        xpBar.setValue(0);
        float newWidthXP = xpBar.getWidth()*4 ;
        float newHeightXP = xpBar.getHeight()* 3 / 2 ;
        xpBar.setWidth(newWidthXP);
        xpBar.setHeight(newHeightXP);
        xpBar.setBounds(xpBar.getX(), xpBar.getY(), newWidthXP, newHeightXP);
       
       

        decimal = new DecimalFormat();
        decimal.setMaximumFractionDigits(0);


        port = new FitViewport(game.width, game.height, new OrthographicCamera());
        stage = new Stage(port, sb);

        Table table = new Table();
        //table.setWidth(game.width/3);
        //Top-Align table
        table.setFillParent(true);
        table.setBounds(0, 0, game.width, game.height);
        table.top();
        //make the table fill the entire stage

       
        

        //cooldownTimerLabel = new Label("",skin2);
        countdownLabel = new Label(decimal.format(timerMinute) + ":" + decimal.format(timerSecond), skin2);
        countdownLabel.setFontScale(game.height/400);

        if (player instanceof Tank)
            texture = new Texture(Gdx.files.internal("Pixthulhu/vesikalik.png"));
        if (player instanceof Assassin)
            texture = new Texture(Gdx.files.internal("The SwordMaster/2ndvesikalik.png"));
        Image vesikalik = new Image(new TextureRegionDrawable(new TextureRegion(texture)));
        
        //table.setSize(game.width, game.height);
        //table.debug();
        table.add(countdownLabel);
        table.row();
        //table.add(healthBar).padTop((game.height-countdownLabel.getHeight())/2);
        table.row();
        //table.add(xpBar).padTop(healthBarHeight/3);
        table.row();
        //table.add(vesikalik).align(Align.bottomLeft);
        vesikalik.setPosition(0, 0);

        healthBar.setPosition(vesikalik.getWidth() , game.height / 12f);
        xpBar.setPosition(vesikalik.getWidth() , game.height / 40f );

        


        //stage.setDebugAll(true);
        stage.addActor(healthBar);
        stage.addActor(xpBar);
        stage.addActor(vesikalik);
        stage.addActor(table);
    }

    public void update(float dt){
        timerSecond += dt;
        timerMinute = (int) timerSecond / 60;
        countdownLabel.setText(decimal.format(timerMinute) + ":" + decimal.format((int) timerSecond % 60));
        healthBar.setValue(player.health);
        xpBar.setValue(player.xp);
        
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
    
}
