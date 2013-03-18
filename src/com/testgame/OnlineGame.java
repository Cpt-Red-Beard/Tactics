package com.testgame;

import com.testgame.mechanics.unit.AUnit;
import com.testgame.mechanics.unit.Ditz;
import com.testgame.mechanics.unit.Jock;
import com.testgame.mechanics.unit.Nerd;
import com.testgame.player.APlayer;
import com.testgame.player.ComputerPlayer;
import com.testgame.resource.ResourcesManager;
import com.testgame.scene.GameScene;

public class OnlineGame extends AGame{
	
	/**
	 * Player 1.
	 */
	protected APlayer player;
	
	/**
	 * Player 2.
	 */
	private ComputerPlayer compPlayer;
	
	
	private boolean firstTurn;
	
	public OnlineGame(APlayer pOne, ComputerPlayer pTwo, int xDim, int yDim, GameScene game, boolean turn) {
		super(xDim, yDim, game);
		this.setFirstTurn(turn);
		init();
		
	}
	
	/**
	 * Performs initialization needed to begin the game.
	 */
	private void init() {
		
		int jocks = resourcesManager.unitArray.get(0);
		int nerds = resourcesManager.unitArray.get(1);
		int ditz = resourcesManager.unitArray.get(2);
		int j = 0;
		if(isFirstTurn())
			j = 10;
		for(int i = 0; i < 10; i++){
				if(nerds > 0){
					AUnit unit = new Nerd(gameMap, i, j, gameScene, "blue");
					unit.init(); 
					player.addUnit(unit);
					nerds--;
				}
				else if(ditz > 0){
					AUnit unit = new Ditz(gameMap, i, j, gameScene, "blue");
					unit.init(); 
					player.addUnit(unit);
					ditz--;
				}
				else if(jocks > 0){
					AUnit unit = new Jock(gameMap, i, j, gameScene, "blue");
					unit.init(); 
					player.addUnit(unit);
					jocks--;
				}
			}
	}
	
}
