package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.Constants;
import com.mygdx.game.EternalSemester;
import com.mygdx.game.DatabaseUtil;

import javax.sql.ConnectionPoolDataSource;

public class LoginScreen implements Screen {

    private final EternalSemester game;

    private Skin skin = new Skin(Gdx.files.internal("Craftacular/craftacular-ui.json"));;
    private Skin skin2 = new Skin(Gdx.files.internal("Pixthulhu/pixthulhu-ui.json"));
    private Table table;
    private Label header;
    private Stage stage;
    public  Label userNotFound;

    public String[] signUpStrings = {"Username", "Password"};
    public TextField[] textField = new TextField[2];



    SpriteBatch batch;
    Sprite sprite;

    public LoginScreen(EternalSemester game)
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
        //stage.addActor(gameName);
        table.add(gameName).colspan(2);
        table.row();


        userNotFound = new Label("User not found.", skin2);
        userNotFound.setFontScale(game.height/540);
        table.add(userNotFound).colspan(2).padTop(game.height/10);
        userNotFound.setVisible(false);
        table.row();
        table.top();


        for (int i=0;i<2;i++)
        {
            Label actionLabel = new Label(signUpStrings[i], skin2);
            actionLabel.setFontScale(game.height/540);
            textField[i] = new TextField("",skin);


            if (i==1)
            {
                textField[1].setPasswordCharacter('*');
                textField[1].setPasswordMode(true);
            }
            table.add(actionLabel).pad(game.width/300).align(Align.center).colspan(2);
            table.row();
            table.add(textField[i]).width(game.width/3).colspan(2);
            table.row();
        }


        TextButton login = new TextButton("Login", skin2);
        login.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Constants.soundEffect.play(game.soundEffectVolume);
                if(!DatabaseUtil.userLogin(textField[0].getText(), textField[1].getText())){
                    userNotFound.setVisible(true);
                    return;
                }
                game.username = textField[0].getText();
                System.out.println(game.username);
                game.updateUserPreferences(game.username);
                game.setScreen(game.mainMenuScreen);
            }
        });


        TextButton back = new TextButton("Sign Up", skin2);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Constants.soundEffect.play(game.soundEffectVolume);
                game.setScreen(game.signUpScreen);
            }
        });


        table.add(back).align(Align.right).pad(25).colspan(1);
        table.add(login).colspan(1).align(Align.left).pad(25);
        table.row();
        //table.debug();
        //back.align(Align.topLeft);
        //stage.addActor(back);
        TextButton exit = new TextButton("Exit", skin2);
        exit.getLabel().setFontScale(1); // Increase font size
        //table.add(exit).width(300).height(100).pad(10); // Increase button size and add padding
        table.add(exit).pad(game.height/300).align(Align.center).colspan(2);
        table.row();

        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });






        batch = new SpriteBatch();
        sprite = new Sprite(new Texture(Gdx.files.internal("background2.jpg")));
        sprite.setSize(1920, 1080);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT );

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
        {
            Constants.soundEffect.play(game.soundEffectVolume);
            if(!DatabaseUtil.userLogin(textField[0].getText(), textField[1].getText())){
                userNotFound.setVisible(true);
                return;
            }
            game.username = textField[0].getText();
            System.out.println(game.username);
            game.updateUserPreferences(game.username);
            game.setScreen(game.mainMenuScreen);
        }
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
