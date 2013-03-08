package com.testgame.mechanics.unit;

import com.testgame.mechanics.map.GameMap;
import com.testgame.scene.GameScene;

/**
 * The jock unit.
 * @author Alen Lukic
 *
 */
public class Jock extends AUnit {
	
	/**
	 * Constructor. Sets mutable and immutable stats.
	 * @param map The map being used for this game.
	 * @param x The initial x-coordinate of the unit.
	 * @param y The initial y-coordinate of the unit.
	 */
	public Jock(GameMap map, int x, int y, GameScene game, String color) {
		super(0, 0, game.resourcesManager.jock_tileset, game.vbom);
		this.game = game;
		this.map = map;
		this.x = x;
		this.y = y;
		this.maxHealth = 100;
		this.currentHealth = 100;
		this.attack = 50;
		this.attackenergy = 30;
		this.range = 20;
		this.attackrange = 1;
		this.energy = 100;
		this.energyUsedLastTurn = 0;
		this.isDefending = false;
		map.setOccupied(x, y, this);
		this.unitType = "Jock";
		int redStart = game.resourcesManager.jock_tileset.getTileCount() / 2;
		if (color.equals("red")) {
			this.start_frame = redStart;
		}
		this.setCurrentTileIndex(start_frame);
	}

}
