package com.veyrongaming.eternalsemester;

import java.util.ArrayList;

import com.veyrongaming.eternalsemester.characters.Assassin;

public class GameWorld {
    private Character chosenCharacter;
    private ArrayList<Enemy> enemies;

    public void chooseCharacter(String characterName) {
        if (characterName.equals("Assassin")) {
            chosenCharacter = new Assassin(characterName);
        }
        // ...
    }

    // Update and use the chosenCharacter throughout the game

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
}
