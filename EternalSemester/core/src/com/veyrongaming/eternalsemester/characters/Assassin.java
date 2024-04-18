package com.veyrongaming.eternalsemester.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.veyrongaming.eternalsemester.EternalSemester;
import com.veyrongaming.eternalsemester.weapons.Dagger;

public class Assassin extends Character {
    public Assassin(EternalSemester game, String name) {
        super(game, name, 150, 0.1f, new Dagger(), new Texture(Gdx.files.internal("assassin.jpg")));
    }

    @Override
    public void useSpecialAbility() {
        // TODO Iplement logic for Assassin's dash ability (e.g., move a certain distance instantly)
    }
}
