package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;


    public class ScreenManager {
        private Game game;
        private Screen currentScreen;
    
        public ScreenManager(Game game) {
            this.game = game;
        }
    
        public void showScreen(Screen newScreen) {
            if (currentScreen != null) {
                currentScreen.dispose(); // Mevcut ekranı temizle
            }
            currentScreen = newScreen; // Yeni ekranı ayarla
            game.setScreen(newScreen); // Oyunun ekranını yeni ekran olarak ayarla
        }
    }

   
        
    
