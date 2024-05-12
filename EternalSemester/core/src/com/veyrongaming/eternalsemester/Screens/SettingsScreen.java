package com.mygdx.game.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGdxGame;


public class SettingsScreen implements Screen{

    private final MyGdxGame game;


    private Skin skin2 = new Skin(Gdx.files.internal("Craftacular/craftacular-ui.json"));;
	private Skin skin = new Skin(Gdx.files.internal("Pixthulhu/pixthulhu-ui.json"));
    private Table table;
    private Label header;
    private Stage stage; 

    SpriteBatch batch;
    Sprite sprite;



    public SettingsScreen(MyGdxGame myGdxGame)
    {
        this.game = myGdxGame;
    }
    
    @Override
    public void show() {
        
        stage = new Stage(new ExtendViewport(game.width,game.height));
        Gdx.input.setInputProcessor(stage);
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
       
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton back = new TextButton("Back", skin);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.mainMenuScreen);
            }
        });
        back.align(Align.topLeft);
        stage.addActor(back);
        


        // Widget'lar oluşturuluyor
        Label titleLabel = new Label("Settings", skin);
        SelectBox<String> resolutionSelectBox = new SelectBox<>(skin);
        resolutionSelectBox.setItems("1920x1080","1600x900","1280x720");
        if (game.width==1920)
            resolutionSelectBox.setSelected("1920x1080");
        if (game.width==1280)
            resolutionSelectBox.setSelected("1280x720");
        if (game.width==1600)
            resolutionSelectBox.setSelected("1600x900");
        SelectBox<String> windowModeSelectBox = new SelectBox<>(skin);
        windowModeSelectBox.setItems("Fullscreen", "Windowed");
        final Slider soundSlider = new Slider(0, 100, 1, false, skin);
    
        TextButton keyBindingsButton = new TextButton("Key Bindings", skin);

        // Listener'lar ekleniyor

        resolutionSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // When an item is selected, this method will be called
                if (resolutionSelectBox.getSelected().equals("1920x1080"))
                {
                    game.setWidth(1920);
                    game.setHeight(1080);
                    game.setScreen(game.settingsScreen);
                }
                if (resolutionSelectBox.getSelected().equals("1280x720"))
                {
                    game.setWidth(1280);
                    game.setHeight(720);
                    game.setScreen(game.settingsScreen);
                }
                if (resolutionSelectBox.getSelected().equals("1600x900"))
                {
                    game.setWidth(1600);
                    game.setHeight(900);
                    game.setScreen(game.settingsScreen);
                }
                
            }
        });

        keyBindingsButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.keyBindingsScreen);
			}
		});

        soundSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Frame rate set to: " + soundSlider.getValue());
            }
        });

        // Layout düzenlemesi
        Label title = new Label("Settings", skin);
        title.setFontScale(game.height/540);
        table.add(title).colspan(3).align(Align.center);
        table.row();
        table.add(new Label("Resolution", skin)).pad(game.width/300);
        table.add(resolutionSelectBox).pad(10).width(game.width/3);
        table.row();
        table.add(new Label("Window Mode", skin)).pad(game.width/300);
        table.add(windowModeSelectBox).pad(10).width(game.width/3);
        table.row();
        table.add(new Label("Volume", skin)).pad(game.width/300);
        table.add(soundSlider).left().pad(10).width(game.width/3);
        table.row();
     
        table.add(keyBindingsButton).colspan(2).center().padTop(10);

        //table.setDebug(true); // Tablo çizgilerini hata ayıklama için etkinleştir
        table.center();
        // Padding ve spacing ayarları
        table.pad(10);  // Tüm tablo için padding
        table.defaults().pad(5); // Tüm hücreler için varsayılan padding

        batch = new SpriteBatch();
        sprite = new Sprite(new Texture(Gdx.files.internal("secondbackground.jpeg")));
        sprite.setSize(1920,1080);

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
      
    }
    
}
