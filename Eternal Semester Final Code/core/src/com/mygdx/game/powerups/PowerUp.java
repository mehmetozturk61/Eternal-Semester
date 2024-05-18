package com.mygdx.game.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.EternalSemester;
import com.mygdx.game.characters.Player;

public class PowerUp {
	public enum Type {MaxHealth, Armor, XPGain, MoveSpeed, AttackSpeed, AttackDamage, SpecialCooldown, SpecialRange}

	public EternalSemester game;
	public World world;
	public Player player;
	public Type type;
	public String description;
	public Texture texture;

	public PowerUp(EternalSemester game, World world, Player player, Type type) {
		this.game = game;
		this.world = world;
		this.player = player;
		this.type = type;
		getDescriptions();
	}

	public void getDescriptions() {
		switch (type) {
			case MaxHealth:
				description = "Increases the character’s maximum health by 10% and heals.";
				break;
			case Armor:
				description = " Reduces the character’s damage taken by 10%.";
				break;
			case XPGain:
				description = "Increases the XP gain by 10%";
				break;
			case MoveSpeed:
				description = "Increases the movement speed by 10%";
				break;
			case AttackSpeed:
				description = "Increases the main weapon speed by 10%";
				break;
			case AttackDamage:
				description = "Increases the main weapon damage by 10%";
				break;
			case SpecialCooldown:
				description = "Decreases the special ability cooldown by 10%";
				break;
			default:
				break;
		}
	}

	public void applyEffect() {
		switch (type) {
			case MaxHealth:
				//texture = new Texture("PowerUps/powerup_maxhealth.png");
				description = "Increases the character’s maximum health by 10% and heals.";
				player.health += player.maxHealth / 10;
				player.maxHealth = player.maxHealth * 11 / 10;
				break;
			case Armor:
				//texture = new Texture("PowerUps/powerup_armor.png");
				description = " Reduces the character’s damage taken by 10%.";
				player.damageResistence += 10;
				break;
			case XPGain:
				//texture = new Texture("PowerUps/powerup_xpgain.png");
				description = "Increases the XP gain by 10%";
				player.xpCoefficient *= 1.1f;
				break;
			case MoveSpeed:
				//texture = new Texture("PowerUps/powerup_movespeed.png");
				description = "Increases the movement speed by 10%";
				player.initialSpeed = player.initialSpeed * 11 / 10;
				player.speed = player.speed * 11 / 10;
				break;
			case AttackSpeed:
				//texture = new Texture("PowerUps/powerup_attackspeed.png");
				description = "Increases the main weapon speed by 10%";
				player.skill.cooldown *= 0.9f;
				break;
			case AttackDamage:
				//texture = new Texture("PowerUps/powerup_attackdamage.png");
				description = "Increases the main weapon damage by 10%";
				player.skill.damage = player.skill.damage * 11 / 10;
				break;
			case SpecialCooldown:
				//texture = new Texture("PowerUps/powerup_specialcooldown.png");
				description = "Decreases the special ability cooldown by 10%";
				player.specialCooldown *= 0.9f;
				break;
			default:
				break;
		}
	}

	public String getDescription() {
		return description;
	}

	public Texture getTexture() {
		return texture;
	}
}