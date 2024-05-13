package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.MyGdxGame;
import com.badlogic.gdx.graphics.g2d.TextureRegion;





public class SignUpScreen implements Screen {

    private final MyGdxGame game;

    private Skin skin = new Skin(Gdx.files.internal("Craftacular/craftacular-ui.json"));;
	private Skin skin2 = new Skin(Gdx.files.internal("Pixthulhu/pixthulhu-ui.json"));
    private Table table;
    private Label header;
    private Stage stage; 

    public String[] signUpStrings = {"Email","Username", "Password"};
    public TextField[] textField = new TextField[3];

    
   
    SpriteBatch batch;
    Sprite sprite;


    

    public SignUpScreen(MyGdxGame game)
    {
        this.game = game;
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


        Label gameName = new Label("Eternal Semester", skin2);
        gameName.setFontScale(game.height/200);
        float labelWidth = gameName.getPrefWidth();
        float labelHeight = gameName.getPrefHeight();
        gameName.setBounds(0,0, labelWidth, labelHeight);
        gameName.setPosition((game.width-labelWidth)/2, (game.height-labelHeight/2));
        //gameName.debug();
        stage.addActor(gameName);
        

        for (int i=0;i<3;i++) 
        {
            Label actionLabel = new Label(signUpStrings[i], skin2);
            actionLabel.setFontScale(game.height/540);
            textField[i] = new TextField("",skin);

        
            if (i==2)
            {
                textField[2].setPasswordCharacter('*');
                textField[2].setPasswordMode(true);
            }
            table.add(actionLabel).pad(game.width/300).align(Align.left);
            table.row();
            table.add(textField[i]).width(game.width/3);
            table.row();
        }
        //table.setBackground(backgroundDrawable);
        TextButton signUp = new TextButton("Sign Up", skin2);
        table.add(signUp).pad(50);

        TextButton back = new TextButton("Back", skin2);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.soundEffect.play(game.soundEffectVolume);
                game.setScreen(game.loginScreen);
            }
        });
        back.align(Align.topLeft);
        stage.addActor(back);
        
        
        stage.setDebugAll(false);
        stage.addActor(table);
            
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
        // TODO Auto-generated method stub

    }
    
}
