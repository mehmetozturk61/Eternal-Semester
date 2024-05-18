package com.mygdx.game.Screens;

import com.mygdx.game.Enemies.Enemy;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.weapons.Skill;

import java.util.ArrayList;

public class Levels {
    public ArrayList<Enemy> enemies;
    public ArrayList<Skill> skills;
    public int killCount = 0;
    public Hud hud;
}
