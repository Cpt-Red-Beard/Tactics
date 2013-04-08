package com.testgame.mechanics.unit;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.testgame.mechanics.map.GameMap;
import com.testgame.scene.GameScene;

public class Base extends AUnit {

	public Base(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
	}
	
	// TODO: need colored artwork;
	public Base(GameMap map, int x, int y, GameScene game, String color) {
		super(0, 0, game.resourcesManager.map_tiles, game.vbom);
		this.game = game;
		this.map = map;
		this.x = x;
		this.y = y;
		this.maxHealth = 500;
		this.currentHealth = 500;
		this.attack = 0;
		this.attackenergy = 0;
		this.range = 0;
		this.attackrange = 0;
		this.energy = 0;
		this.energyUsedLastTurn = 0;
		this.isDefending = false;
		map.setOccupied(x, y, this);
		this.unitType = "Base";
		int redStart = 0; // TODO: change for colored.
		if (color.equals("red")) {
			this.start_frame = redStart;
		}
		this.setCurrentTileIndex(start_frame);
	}
}
