package com.testgame;

import java.util.Random;

import org.json.JSONObject;

import android.graphics.Point;
import android.util.Log;

import com.testgame.mechanics.map.GameMap;
import com.testgame.mechanics.unit.DummyUnit;
import com.testgame.player.APlayer;
import com.testgame.resource.ResourcesManager;
import com.testgame.scene.GameScene;

/**
 * Abstract implementation of the IGame interface.
 * @author Alen Lukic
 *
 */
public abstract class AGame {

	/**
	 * Resource Manager which contains all information shared across scenes.
	 */
	public ResourcesManager resourcesManager;
	
	/**
	 * The game scene.
	 */
	protected GameScene gameScene;
	
	/**
	 * Player 1.
	 */
	protected APlayer player;
	
	/**
	 * Variable which keeps track of the current turn.
	 */
	protected int turncount;

	/**
	 * Signal for the end of a player's turn.
	 */
	protected boolean turnOver;

	/**
	 * The map.
	 */
	public GameMap gameMap;

	/**
	 * Map's x-dimension.
	 */
	protected int xDim;

	/**
	 * Map's y-dimension.
	 */
	protected int yDim;

	/**
	 * Delimiting line between the two players' sides.
	 */
	protected int divider;

	/**
	 * Random number generator.
	 */
	protected Random rand;

	/**
	 * Constructor.
	 * @param pOne Player one.
	 * @param pTwo Player two.
	 * @param xDim The size of the x-dimension of the map.
	 * @param yDim The size of the y-dimension of the map.
	 */
	public AGame(APlayer pOne, int xDim, int yDim, GameScene game) {
		this.resourcesManager = ResourcesManager.getInstance();
		this.gameScene = game;
		this.gameMap = new GameMap(xDim, yDim);
		this.xDim = xDim;
		this.yDim = yDim;
		this.divider = yDim / 2;
		this.rand = new Random();
		this.turnOver = false;
		this.player = pOne;
		turncount = 0;
		
	}

	/**
	 * Sets up the obstacles in the map. Needs to be called from any expansion of this method in implementations of AGame.
	 */
	public void init() {
		for (Point p : ResourcesManager.getInstance().obstacles) {
			Log.d("AndEngine", "Obstacle at ("+p.x+", "+p.y+")");
			this.gameMap.setOccupied(p.x, gameMap.yDim - p.y - 1, new DummyUnit(0, 0, resourcesManager.ditz_tileset, resourcesManager.vbom));
		}
	}
	
	/**
	 * Abstract method for ending the game.
	 */
	public abstract void endGame();
	
	/**
	 * Abstract method for changing the game to the next turn.
	 */
	public abstract void nextTurn();

	/**
	 * Method to get the current turn.
	 * @return the integer representing the current turn.
	 */
	public int getCount(){
		return turncount;
	}
	
	/**
	 * Increments the turn.
	 */
	public void incrementCount(){
		turncount++;
	}

	/**
	 * Gets player one.
	 * @return player one.
	 */
	public APlayer getPlayer() {
		return this.player;
	}

	/**
	 * Abstract methods for adding a move. Only implemented in Online game. Every other implementation should have nothing occur here.
	 * @param move
	 */
	public void addMove(JSONObject move){ 
	}

	/**
	 * Gets the game scene.
	 * @return gameScene.
	 */
	public GameScene getGameScene() {
	
		return gameScene;
	}
	
	

}
