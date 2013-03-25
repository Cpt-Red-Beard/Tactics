package com.testgame;

import android.util.Log;

import com.testgame.mechanics.unit.AUnit;
import com.testgame.mechanics.unit.Ditz;
import com.testgame.mechanics.unit.Jock;
import com.testgame.mechanics.unit.Nerd;
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
		if(player.getActiveUnits().size() == 0 ){
			gameScene.activity.runOnUiThread(new Runnable() {
        	    @Override
        	    public void run() {
        	    	gameScene.quitDialog("Player 2 Wins!");
          			 
        	    }
        	});
			
			this.gameScene.setEndGameText(player2);
			
		}
		else if(player2.getActiveUnits().size() == 0 ){
			gameScene.activity.runOnUiThread(new Runnable() {
        	    @Override
        	    public void run() {
        	    	gameScene.quitDialog("Player 1 Wins!");
          			 
        	    }
        	});
			
			this.gameScene.setEndGameText(player);
			
		}

	}

	@Override
	public void nextTurn() {
		
		Log.d("AndEnine", "[LocalGame] next turn");
		if(this.getPlayer().isTurn()){
			this.getPlayer().endTurn();
			this.player2.beginTurn();
		}
		else{
			this.player2.endTurn();
			this.getPlayer().beginTurn();
		}
	}

	@Override
	public void init() {
		int jocks = resourcesManager.unitArray.get(0);
		int nerds = resourcesManager.unitArray.get(1);
		int ditz = resourcesManager.unitArray.get(2);

		int j = 10;

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
		//AUnit unitbase = new Base(gameMap, 5, j+1, gameScene, "blue");
		//player.setBase(unitbase);
		
		jocks = resourcesManager.unitArray2.get(0);
		nerds = resourcesManager.unitArray2.get(1);
		ditz = resourcesManager.unitArray2.get(2);
		j = 1;

		for(int i = 0; i < 10; i++){
				if(nerds > 0){
					AUnit unit = new Nerd(gameMap, i, j, gameScene, "red");
					unit.init(); 
					player2.addUnit(unit);
					nerds--;
				}
				else if(ditz > 0){
					AUnit unit = new Ditz(gameMap, i, j, gameScene, "red");
					unit.init(); 
					player2.addUnit(unit);
					ditz--;
				}
				else if(jocks > 0){
					AUnit unit = new Jock(gameMap, i, j, gameScene, "red");
					unit.init(); 
					player2.addUnit(unit);
					jocks--;
				}
			}
		//AUnit unitbase2 = new Base(gameMap, 5, j-1, gameScene, "red");
		//player2.setBase(unitbase2);
		
		player.beginTurn();
	}

}
