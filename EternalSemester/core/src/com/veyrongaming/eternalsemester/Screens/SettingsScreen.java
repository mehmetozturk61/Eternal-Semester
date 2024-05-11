package com.mygdx.game.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGdxGame;

public class SettingsScreen implements Screen{

    private final MyGdxGame game;


    private Skin skin = new Skin(Gdx.files.internal("Craftacular/craftacular-ui.json"));;
	private Skin skin2 = new Skin(Gdx.files.internal("Pixthulhu/pixthulhu-ui.json"));
    private Table table;
    private Label header;
    private Stage stage; 
    private int width = 3024;
    private int height = 1964;


    public SettingsScreen(MyGdxGame myGdxGame)
    {
        this.game = myGdxGame;
    }
    
    @Override
    public void show() {
        stage = new Stage(new FitViewport(width, height));
        Gdx.input.setInputProcessor((InputProcessor) stage);
        
        table = new Table();
        table.setWidth((float) stage.getWidth());
		table.align(Align.center|Align.bottom);		
		table.setPosition(0, Gdx.graphics.getHeight()/8);

		TextButton start = new TextButton("Start", skin2);
        table.add(start);

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
        //stage.getViewport().update(width, height, true);
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
