package com.mygdx.game.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGdxGame;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;


public class MainMenu implements Screen{

    private final MyGdxGame game;

    private Skin skin = new Skin(Gdx.files.internal("Craftacular/craftacular-ui.json"));;
	private Skin skin2 = new Skin(Gdx.files.internal("Pixthulhu/pixthulhu-ui.json"));
    private Table table;
    private Label header;
    private Stage stage; 
    private int width = Gdx.graphics.getWidth();
    private int height = 1964;
    

    public MainMenu(MyGdxGame game)
    {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(width, height));
        Gdx.input.setInputProcessor((InputProcessor) stage);
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        
        table = new Table();
		table.setWidth((float) stage.getWidth());
        table.setSize(width/3, height/10);
		//table.align(Align.bottom);		
		table.setPosition((width-table.getWidth())/2, (height/2-table.getHeight())/2);
        //table.center();

		TextButton start = new TextButton("Start", skin2);
		TextButton settings = new TextButton("Settings", skin2);
		TextButton changeProfile = new TextButton("Change Profile", skin2);
		TextButton exit = new TextButton("Exit", skin2);
		Label gameName = new Label("Eternal Semester", skin2);

        gameName.setBounds(0, 0, width, 40);
        
        gameName.setFontScale(width/100);
		gameName.setPosition(0, Gdx.graphics.getHeight());
        gameName.debug();
        stage.addActor(gameName);
		
		
		float buttonWidth = 50; // Adjust width as needed
        float buttonHeight = 50; // Adjust height as needed

        start.setSize(50, 10);
        settings.setSize(buttonWidth, buttonHeight);
        changeProfile.setSize(buttonWidth, buttonHeight);
        exit.setSize(buttonWidth, buttonHeight);

		
		table.row();

        // Add padding and buttons to the table
        
        table.add(start).padBottom(10);
        table.row();
        table.add(settings).padBottom(10);
        table.row(); 
        table.add(changeProfile).padBottom(10);
        table.row();
        table.add(exit);
        //table.debug();


        settings.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.settingsScreen);
			}
		});

        exit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});

        stage.setDebugAll(false);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT );
        stage.act(delta); 
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
       
    }
    
}
