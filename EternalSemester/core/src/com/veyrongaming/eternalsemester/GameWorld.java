package com.veyrongaming.eternalsemester;

import com.veyrongaming.eternalsemester.characters.Assassin;

public class GameWorld {
    private Character chosenCharacter;

    public void chooseCharacter(String characterName) {
        if (characterName.equals("Assassin")) {
            chosenCharacter = new Assassin(characterName);
        }
        // ...
    }

    // Update and use the chosenCharacter throughout the game
}
