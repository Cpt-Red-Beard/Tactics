package com.testgame;

import java.util.Random;

import com.testgame.mechanics.map.GameMap;
import com.testgame.mechanics.unit.AUnit;
import com.testgame.mechanics.unit.Ditz;
import com.testgame.mechanics.unit.Jock;
import com.testgame.mechanics.unit.Nerd;
import com.testgame.player.APlayer;
import com.testgame.player.ComputerPlayer;
import com.testgame.resource.ResourcesManager;
import com.testgame.scene.GameScene;

/**
 * Abstract implementation of the IGame interface.
 * @author Alen Lukic
 *
 */
public class AGame implements IGame {

	
	public ResourcesManager resourcesManager;
	
	protected GameScene gameScene;
	
	/**
	 * Player 1.
	 */
	protected APlayer player;
	
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
	public AGame(int xDim, int yDim, GameScene game) {
		this.resourcesManager = ResourcesManager.getInstance();
		this.gameScene = game;
		this.gameMap = new GameMap(xDim, yDim);
		this.xDim = xDim;
		this.yDim = yDim;
		this.divider = yDim / 2;
		this.rand = new Random();
		this.turnOver = false;
		turncount = 0;
		
	}

	

	public int getCount(){
		return turncount;
	}
	
	public void incrementCount(){
		turncount++;
	}

	/**
	 * Ends the game.
	 */
	public void endGame() {
		if(player.getActiveUnits().size() == 0){
			this.gameScene.quitDialog("You Lose!");
			this.gameScene.setEndGameText(compPlayer);
			
		}
		else if(compPlayer.getActiveUnits().size() == 0){
			this.gameScene.quitDialog("You Win!");
			this.gameScene.setEndGameText(player);
			
		}
		
	}
	
	public APlayer getPlayer() {
		return this.player;
	}

	public ComputerPlayer getCompPlayer() {
		return compPlayer;
	}

	public void setCompPlayer(ComputerPlayer compPlayer) {
		this.compPlayer = compPlayer;
	}

	public boolean isFirstTurn() {
		return firstTurn;
	}

	public void setFirstTurn(boolean firstTurn) {
		this.firstTurn = firstTurn;
	}

	public GameScene getGameScene() {
	
		return gameScene;
	}
	
	

}
