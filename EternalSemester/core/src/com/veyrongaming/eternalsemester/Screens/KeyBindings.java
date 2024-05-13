package com.mygdx.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.MyGdxGame;

public class KeyBindings implements Screen, InputProcessor{

    private Stage stage;
    private MyGdxGame game;
    private Skin skin2 = new Skin(Gdx.files.internal("Pixthulhu/pixthulhu-ui.json"));
    int currentChar;
    private TextField currentTextField = null;
    TextField[] keyField = new TextField[6];
    TextButton[] changeButton = new TextButton[6];

    SpriteBatch batch;
    Sprite sprite;
    

    public KeyBindings(MyGdxGame game) {
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

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        /*
        Table kutu = new Table();
        Texture texture = new Texture("background.jpeg");
        TextureRegionDrawable textureDrawable = new TextureRegionDrawable(texture);
        kutu.setBackground(textureDrawable); // Texture olarak arka plan
        kutu.setSize(game.width/3, game.height/2);
        stage.addActor(kutu);
        */
        

        // Title label
        Label title = new Label("Key Bindings", skin2);
        title.setFontScale(2);
        table.add(title).colspan(6).align(Align.center);
        table.row();

        TextButton back = new TextButton("Back", skin2);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.settingsScreen);
            }
        });
        back.align(Align.topLeft);
        stage.addActor(back);


        
        
        //int i=0;
        // Create buttons and text fields for each binding
        for (int i=0;i<5;i++) {
            Label actionLabel = new Label(game.bindings[i][0], skin2);
            
            keyField[i] = new TextField(game.bindings[i][1], skin2);
            keyField[i].setDisabled(true); // Make the field not editable

            table.add(actionLabel).pad(game.width/300);

            if (game.width==1280)
                table.add(keyField[i]).width(game.width/14).pad(game.width/300);
            else if (game.width==1600)
                table.add(keyField[i]).width(game.width/17).pad(game.width/300);
            else
                table.add(keyField[i]).width(game.width/20).pad(game.width/300);
            changeButton[i] = new TextButton("Change", skin2);

            // Add listener to change button here if needed
            table.add(changeButton[i]).pad(game.width/300);

            if (i==1 || i==2 || i==3)
                table.row();
        }
        Label actionLabel = new Label(game.bindings[5][0], skin2);
        CheckBox checkBox = new CheckBox("", skin2);
        if (game.isUsingMouse)
            checkBox.setChecked(true);
        table.add(actionLabel).pad(game.width/300).colspan(2).align(Align.center);
        table.add(checkBox).pad(game.width/300);




        checkBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // When an item is selected, this method will be called
                if (checkBox.isChecked())
                {
                    game.isUsingMouse = true;
                }
                else
                    game.isUsingMouse = false;
            }
        });

   
        changeButton[0].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                keyField[0].setText("-");
                captureKeyInput();
            }

            private void captureKeyInput() {
                stage.addListener(new InputListener() {
                    @Override
                    public boolean keyDown(InputEvent event, int keycode) {
                        String keyName = Input.Keys.toString(keycode); // Kodu tuş ismine çevirir
                        if (!keyName.equals(game.bindings[1][1]) && !keyName.equals(game.bindings[2][1]) && !keyName.equals(game.bindings[3][1]) && !keyName.equals(game.bindings[4][1]))
                        {
                            keyField[0].setText(keyName); // TextField'a tuş ismini yaz
                            game.bindings[0][1] = keyName; // İlgili dizi elemanını güncelle
                            stage.removeListener(this); // Tek tuş vuruşu sonrası dinlemeyi kaldır
                        }
                        
                        return true;
                    }
                });
            }
        });

        changeButton[1].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                keyField[1].setText("-");
                captureKeyInput();
            }

            private void captureKeyInput() {
                stage.addListener(new InputListener() {
                    @Override
                    public boolean keyDown(InputEvent event, int keycode) {
                        String keyName = Input.Keys.toString(keycode); // Kodu tuş ismine çevirir
                        if (!keyName.equals(game.bindings[0][1]) && !keyName.equals(game.bindings[2][1]) && !keyName.equals(game.bindings[3][1]) && !keyName.equals(game.bindings[4][1]))
                        {
                        keyField[1].setText(keyName); // TextField'a tuş ismini yaz
                        game.bindings[1][1] = keyName; // İlgili dizi elemanını güncelle
                        stage.removeListener(this); // Tek tuş vuruşu sonrası dinlemeyi kaldır
                        }
                        return true;
                    }
                });
            }
        });

        changeButton[2].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                keyField[2].setText("-");
                captureKeyInput();
            }

            private void captureKeyInput() {
                stage.addListener(new InputListener() {
                    @Override
                    public boolean keyDown(InputEvent event, int keycode) {
                        String keyName = Input.Keys.toString(keycode); // Kodu tuş ismine çevirir
                        if (!keyName.equals(game.bindings[1][1]) && !keyName.equals(game.bindings[0][1]) && !keyName.equals(game.bindings[3][1]) && !keyName.equals(game.bindings[4][1]))
                        {
                            keyField[2].setText(keyName); // TextField'a tuş ismini yaz
                            game.bindings[2][1] = keyName; // İlgili dizi elemanını güncelle
                            stage.removeListener(this); // Tek tuş vuruşu sonrası dinlemeyi kaldır
                            
                            
                        }
                        return true;
                    }
                });
            }
        });

        changeButton[3].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                keyField[3].setText("-");
                captureKeyInput();
            }

            private void captureKeyInput() {
                stage.addListener(new InputListener() {
                    @Override
                    public boolean keyDown(InputEvent event, int keycode) {
                        String keyName = Input.Keys.toString(keycode); // Kodu tuş ismine çevirir
                        if (!keyName.equals(game.bindings[1][1]) && !keyName.equals(game.bindings[2][1]) && !keyName.equals(game.bindings[0][1]) && !keyName.equals(game.bindings[4][1]))
                        {

                            keyField[3].setText(keyName); // TextField'a tuş ismini yaz
                            game.bindings[3][1] = keyName; // İlgili dizi elemanını güncelle
                            stage.removeListener(this); // Tek tuş vuruşu sonrası dinlemeyi kaldır
                        }
                        return true;
                    }
                });
            }
        });

        changeButton[4].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                keyField[4].setText("-");
                captureKeyInput();
            }

            private void captureKeyInput() {
                stage.addListener(new InputListener() {
                    @Override
                    public boolean keyDown(InputEvent event, int keycode) {
                        String keyName = Input.Keys.toString(keycode); // Kodu tuş ismine çevirir
                        if (!keyName.equals(game.bindings[1][1]) && !keyName.equals(game.bindings[2][1]) && !keyName.equals(game.bindings[3][1]) && !keyName.equals(game.bindings[0][1]))
                        {
                            
                            keyField[4].setText(keyName); // TextField'a tuş ismini yaz
                            game.bindings[4][1] = keyName; // İlgili dizi elemanını güncelle
                            stage.removeListener(this); // Tek tuş vuruşu sonrası dinlemeyi kaldır

                        }
                        return true;
                    }
                });
            }
        });

        
        
       

        table.center();
        batch = new SpriteBatch();
        sprite = new Sprite(new Texture(Gdx.files.internal("secondbackground.jpeg")));
        sprite.setSize(1920, 1080);
    }

    

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
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
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        //stage.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println(keycode);
        currentChar = keycode;
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // TODO Auto-generated method stub
        return false;    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        return false;    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        // TODO Auto-generated method stub
        return false;    }

}
