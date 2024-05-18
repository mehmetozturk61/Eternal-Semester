package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.powerups.PowerUp;

import java.util.ArrayList;

public class AllDescriptions {
    public ArrayList<Description> allDescriptions = new ArrayList<>();;

    public AllDescriptions() {
        allDescriptions.add(new Description("Acid", "Throws acid to the nearest enemy.", new Texture("rpg_skill_icons/elemental_blast.png")));
        allDescriptions.add(new Description("DarkBolt", "Creates random explosions on map.", new Texture("rpg_skill_icons/lighning_bolt.png")));
        allDescriptions.add(new Description("IceShard", "Throws ice shard in random directions.", new Texture("rpg_skill_icons/ice_shard.png")));
        allDescriptions.add(new Description("RotatingFire", "Creates three fireballs that rotates aroundthe world.", new Texture("rpg_skill_icons/fireball.png")));
        allDescriptions.add(new Description("ShockBomb", "Throws an explosive bomb to the nearest enemy.", new Texture("rpg_skill_icons/ethereal_barrier.png")));
        for (int i = 0; i < 7; i++) {
            PowerUp powerUp = new PowerUp(null, null, null, PowerUp.Type.values()[i]);
            allDescriptions.add(new Description(powerUp.type.name(), powerUp.description, powerUp.texture));
        }
    }
}
