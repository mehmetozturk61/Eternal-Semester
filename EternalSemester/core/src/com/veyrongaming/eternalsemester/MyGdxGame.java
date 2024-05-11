package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;




public class MyGdxGame extends Game implements Screen{
	
	SpriteBatch batch;
	Texture img;
	private Skin skin;
	private Skin skin2;
	private Stage stage;
	private Settings settingsScreen;
	//int gameScreenWidth = Gdx.graphics.getWidth();
	//int gameScreenHeight = Gdx.graphics.getHeight();
	private ScreenManager screenManager;


	
	
	@Override
	public void create () {
		//batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");
		com.badlogic.gdx.Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode();
        Gdx.graphics.setFullscreenMode(displayMode);
		screenManager = new ScreenManager(this);
		
		
		skin = new Skin(Gdx.files.internal("Craftacular/craftacular-ui.json"));
		skin2 = new Skin(Gdx.files.internal("Pixthulhu/pixthulhu-ui.json"));
		
		stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));

		//stage = new Stage(new FitViewport(3024, 1964));
		Gdx.input.setInputProcessor(stage);

		Table table = new Table();
		table.setWidth(stage.getWidth());
		table.align(Align.center|Align.bottom);		
		table.setPosition(0, Gdx.graphics.getHeight()/7);

		TextButton start = new TextButton("Start", skin2);
		TextButton settings = new TextButton("Settings", skin2);
		TextButton changeProfile = new TextButton("Change Profile", skin2);
		TextButton exit = new TextButton("Exit", skin2);
		Label gameName = new Label("Eternal Semester", skin2);

		gameName.setFontScale(5);
		gameName.setPosition(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()-200);
		stage.addActor(gameName);
		
		float buttonWidth = 100; // Adjust width as needed
        float buttonHeight = 50; // Adjust height as needed

        start.setSize(buttonWidth, buttonHeight);
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

		//exit.setPosition(Gdx.graphics.getWidth() / 2, (float) (Gdx.graphics.getHeight() / 2 - Gdx.graphics.getHeight() * 0.2));

		
		settings.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// Settings ekranını göster
				settingsScreen = new Settings();
				stage.clear();
				setScreen(settingsScreen);
				stage.addActor(settingsScreen);
				screenManager.showScreen(new Settings());
			}
		});
		
		

		exit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});

		stage.addActor(table);
	}

	@Override
    public void resize(int width, int height) {
    
        stage.getViewport().update(width, height, true);
    }
 
	@Override
	public void render () {
		//ScreenUtils.clear(4, 1, 0, 1);
		//stage.act();
		//stage.draw();
		ScreenUtils.clear(0, 0, 0, 1);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
		super.render();
	}
	
	@Override
	public void dispose () {
		stage.dispose();
	}

	@Override
	public void show() {
		
	}


	private void update(float delta) {
		stage.act(delta);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'hide'");
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'render'");
	}
}
