package com.veyrongaming.eternalsemester.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.veyrongaming.eternalsemester.EternalSemester;
import com.veyrongaming.eternalsemester.weapons.Sword;

public class Warrior extends Character {
    public Warrior(EternalSemester game, String name) {
        super(game, name, 200, 0.08f, new Sword(), new Texture(Gdx.files.internal("assassin.jpg")));
    }

    @Override
    public void useSpecialAbility() {
        // TODO Implement logic for Warrior's life steal and damage boost ability (e.g., steal health from enemies dealt damage, increase attack damage for a duration)
    }
}
