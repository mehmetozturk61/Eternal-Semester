package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.Constants;
import com.mygdx.game.DatabaseUtil;
import com.mygdx.game.EternalSemester;
import org.bson.Document;

public class Leaderboard implements Screen {

    private final EternalSemester game;

    private Skin skin = new Skin(Gdx.files.internal("Craftacular/craftacular-ui.json"));;
    private Skin skin2 = new Skin(Gdx.files.internal("Pixthulhu/pixthulhu-ui.json"));
    private Table table;
    private Label header;
    private Stage stage;

    public TextField[] textField = new TextField[10];

    public Leaderboard(EternalSemester game)
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

        Label label = new Label("Leaderboard", skin2);
        label.setFontScale(game.height/540);
        table.add(label).align(Align.left);
        table.row();

        for(int i=0;i<10;i++)
        {

            textField[i] = new TextField("",skin);
            textField[i].setDisabled(true);
            table.add(textField[i]).width(game.width/3);
            table.row();
        }
        TextButton back = new TextButton("Back", skin2);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Constants.soundEffect.play(game.soundEffectVolume);
                game.setScreen(game.dorm);
            }
        });
        back.align(Align.topLeft);
        stage.addActor(back);
        stage.setDebugAll(false);
        stage.addActor(table);
        String[] leaderboard = DatabaseUtil.getUsersByKillCount();
        for(int i = 0; i < 10; i++){
            textField[i].setText(leaderboard[i] == null ? " " : leaderboard[i]);
        }
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
