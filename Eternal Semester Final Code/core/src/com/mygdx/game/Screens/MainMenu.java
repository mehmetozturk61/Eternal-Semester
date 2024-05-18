package com.mygdx.game.Screens;


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
import com.mygdx.game.EternalSemester;



public class MainMenu implements Screen{

    private final EternalSemester game;

    private Skin skin = new Skin(Gdx.files.internal("Craftacular/craftacular-ui.json"));;
	private Skin skin2 = new Skin(Gdx.files.internal("Pixthulhu/pixthulhu-ui.json"));
    private Table table;
    private Label header;
    private Stage stage; 
    public Level1 level1screen;
    
   
    SpriteBatch batch;
    Sprite sprite;


    

    public MainMenu(EternalSemester game)
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
            stage.addActor(table);
        
          
            // Label for the game name
                    //Label gameName = new Label("Eternal Semester", skin2);
                    //gameName.setFontScale(game.width/300);
            //gameName.setAlignment(Align.center);;
           // stage.addActor(gameName);
                    //table.add(gameName).align(Align.center|Align.top).padTop(game.height/40);
            Label gameName = new Label("Eternal Semester", skin2);
            gameName.setFontScale(game.height/200);
            float labelWidth = gameName.getPrefWidth();
            float labelHeight = gameName.getPrefHeight();
            gameName.setBounds(0,0, labelWidth, labelHeight);
            gameName.setPosition((game.width-labelWidth)/2, (game.height-labelHeight/2));
            //gameName.debug();
            //stage.addActor(gameName);
            table.add(gameName);
            table.row();

            Label userNotFound = new Label("User not found.", skin2);
            userNotFound.setFontScale(game.height/540);
            table.add(userNotFound).padTop(game.height/5);
            userNotFound.setVisible(false);
            table.row();
            table.top();

            // Start button
            TextButton start = new TextButton("Start", skin2);
            start.getLabel().setFontScale(1);; // Increase font size
            //table.add(start).width(300).height(100).pad(10); // Increase button size and add padding
            table.add(start);
            table.row();
            

            // Settings button
            TextButton settings = new TextButton("Settings", skin2);
            settings.getLabel().setFontScale(1); // Increase font size
            //table.add(settings).width(300).height(100).pad(10); // Increase button size and add padding
            table.add(settings).pad(game.height/300);
            table.row();

            TextButton changeProfile = new TextButton("Change Profile", skin2);
            changeProfile.getLabel().setFontScale(1); // Increase font size
            //table.add(changeProfile).width(300).height(100).pad(10); // Increase button size and add padding
            table.add(changeProfile).pad(game.height/300);
            table.row();

            TextButton exit = new TextButton("Exit", skin2);
            exit.getLabel().setFontScale(1); // Increase font size
            //table.add(exit).width(300).height(100).pad(10); // Increase button size and add padding
            table.add(exit).pad(game.height/300);
            table.row();

        
   

        start.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
                Constants.soundEffect.play(game.soundEffectVolume);
                game.setScreen(game.dorm);
			}
		});

        changeProfile.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
                Constants.soundEffect.play(game.soundEffectVolume);
                game.setScreen(game.loginScreen);
			}
		});

        settings.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
                Constants.soundEffect.play(game.soundEffectVolume);
                game.setScreen(game.settingsScreen);
			}
		});

        exit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});

        //table.debug();
        
        stage.setDebugAll(false);
        stage.addActor(table);
        
        batch = new SpriteBatch();
        sprite = new Sprite(new Texture(Gdx.files.internal("background.jpeg")));
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
        
    }

    @Override
    public void dispose() {
      // stage.dispose();
    }
    
}
