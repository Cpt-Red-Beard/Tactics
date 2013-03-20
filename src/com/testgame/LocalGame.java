package com.testgame;

import com.testgame.player.APlayer;
import com.testgame.scene.GameScene;

public class LocalGame extends AGame {
	
	protected APlayer player2;
	
	public LocalGame(APlayer pOne, APlayer pTwo, int xDim, int yDim, GameScene game) {
		super(pOne, xDim, yDim, game);
		this.player2 = pTwo;
		init();
	}

	@Override
	public void endGame() {
		if(player.getActiveUnits().size() == 0){
			this.gameScene.quitDialog("Player 2 Wins!");
			this.gameScene.setEndGameText(player2);
			
		}
		else if(player2.getActiveUnits().size() == 0){
			this.gameScene.quitDialog("Player 1 Wins!");
			this.gameScene.setEndGameText(player);
			
		}

	}

	@Override
	public void nextTurn() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

}
