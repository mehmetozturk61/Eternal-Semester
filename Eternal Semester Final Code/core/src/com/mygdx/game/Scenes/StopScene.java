package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.EternalSemester;
import com.mygdx.game.Screens.SettingsScreen;

public class StopScene implements Disposable {

    public Stage stage;
    private Viewport viewport;
    public EternalSemester game;
    private Skin skin2 = new Skin(Gdx.files.internal("Pixthulhu/pixthulhu-ui.json"));
    public Viewport port;
    public TextButton exit;
    public SettingsScreen settingsScreen;

    public SpriteBatch batch;
    public Sprite sprite;

    public StopScene (EternalSemester game, SpriteBatch sb){

        this.game = game;
        
        stage = new Stage(new ExtendViewport(game.width,game.height));
        Gdx.input.setInputProcessor(stage);


        Table table = new Table();
        table.setFillParent(true);
        table.setBounds(0, 0, game.width, game.height);
        table.center();

       
        

        
        
        /* TextButton settings = new TextButton("Main Menu", skin2);
        settings.getLabel().setFontScale(1); // Increase font size
            //table.add(settings).width(300).height(100).pad(10); // Increase button size and add padding
        table.add(settings).pad(game.height/300);
        table.row();
        settings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                
                game.setScreen(game.mainMenuScreen);
            }
        }); */
        
        
        exit = new TextButton("Exit", skin2);
        exit.getLabel().setFontScale(1); // Increase font size
            //table.add(exit).width(300).height(100).pad(10); // Increase button size and add padding
        table.add(exit).pad(game.height/300);
        table.row();

       


            exit.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.app.exit();
                }
            });

        stage.addActor(table);

        

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
    
}
