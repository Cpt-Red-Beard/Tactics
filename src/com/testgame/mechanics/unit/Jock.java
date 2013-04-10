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
		this.attack = 70;
		this.attackEnergy = 50;
		this.movementRange = 10;
		this.attackRange = 1;
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
		
		this.IDLE_START_FRAME = 0;
		this.IDLE_END_FRAME = 1;
		
		this.WALK_DOWN_START_FRAME = 2;
		this.WALK_DOWN_END_FRAME = 4;
		
		this.WALK_LEFT_START_FRAME = 10;
		this.WALK_LEFT_END_FRAME = 12;
		
		this.WALK_UP_START_FRAME = 5;
		this.WALK_UP_END_FRAME = 7;
		
		this.WALK_RIGHT_START_FRAME = 13;
		this.WALK_RIGHT_END_FRAME = 15;
		
		this.GUARD_FRAME = 16;
		
		this.ATTACKED_START_FRAME = 20;
		this.ATTACKED_END_FRAME = 21;
	}

}
