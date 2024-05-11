package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;





public class Settings extends Actor implements Screen {

    private Skin skin;
	private Skin skin2;
	private Stage stage;



    public void create(){

        com.badlogic.gdx.Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode();
        Gdx.graphics.setFullscreenMode(displayMode);

        skin = new Skin(Gdx.files.internal("Craftacular/craftacular-ui.json"));
		skin2 = new Skin(Gdx.files.internal("Pixthulhu/pixthulhu-ui.json"));
        Gdx.input.setInputProcessor(stage);


        skin = new Skin(Gdx.files.internal("Craftacular/craftacular-ui.json"));
		skin2 = new Skin(Gdx.files.internal("Pixthulhu/pixthulhu-ui.json"));
		
		stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));

        Table table = new Table();
		table.setWidth(stage.getWidth());
		table.align(Align.center|Align.bottom);		
		table.setPosition(0, Gdx.graphics.getHeight()/7);

        Label settingsLabel = new Label("Settings", skin2);

        table.add(settingsLabel);

        stage.addActor(table);
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        //stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pause'");
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resume'");
    }
    

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        skin.dispose();
        skin2.dispose();
        stage.dispose();
    }
    
}
