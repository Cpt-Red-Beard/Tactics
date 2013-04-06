package com.testgame;



import android.graphics.Point;
import com.testgame.mechanics.unit.AUnit;
import com.testgame.mechanics.unit.Base;
import com.testgame.mechanics.unit.Ditz;
import com.testgame.mechanics.unit.Jock;
import com.testgame.mechanics.unit.Nerd;
import com.testgame.player.APlayer;
import com.testgame.scene.GameScene;

public class LocalGame extends AGame {
	
	/**
	 * Player two.
	 */
	protected APlayer player2;
	
	/**
	 * Constructor for a local game. Calls super constructor before setting specific variables.
	 * @param pOne is player one.
	 * @param pTwo is player two.
	 * @param xDim is the x dimension of the map.
	 * @param yDim is the y dimension of the map.
	 * @param game is the game scene in which the GUI is based.
	 */
	public LocalGame(APlayer pOne, APlayer pTwo, int xDim, int yDim, GameScene game) {
		super(pOne, xDim, yDim, game);
		this.player2 = pTwo;
		init();
	}

	/**
	 * Ends the game by checking for any win cases.
	 */
	@Override
	public void endGame() {
		if(player.getActiveUnits().size() == 0 || player.getBase() == null){
			gameScene.activity.runOnUiThread(new Runnable() {
        	    @Override
        	    public void run() {
        	    	gameScene.quitDialog("Player 2 Wins!");
          			 
        	    }
        	});
			
			this.gameScene.setEndGameText(player2);
			
		}
		else if(player2.getActiveUnits().size() == 0 || player2.getBase() == null){
			gameScene.activity.runOnUiThread(new Runnable() {
        	    @Override
        	    public void run() {
        	    	gameScene.quitDialog("Player 1 Wins!");
          			 
        	    }
        	});
			
			this.gameScene.setEndGameText(player);
			
		}

	}

	/**
	 * Starts the next turn of the game.
	 */
	@Override
	public void nextTurn() {
		
		
		if(this.getPlayer().isTurn()){
			this.getPlayer().endTurn();
			this.player2.beginTurn();
		}
		else{
			this.player2.endTurn();
			this.getPlayer().beginTurn();
		}
	}

	/**
	 * Initilizes the game by spawning all players and starting the first turn. Calls the super init method.
	 */
	@Override
	public void init() {
		
		super.init();
		
		int jocks = resourcesManager.unitArray.get(0);
		int nerds = resourcesManager.unitArray.get(1);
		int ditz = resourcesManager.unitArray.get(2);

		
		Point[] spawns = resourcesManager.getSpawn1(resourcesManager.mapString);
		
		for(Point i : spawns){
				if(nerds > 0){
					AUnit unit = new Nerd(gameMap, i.x, i.y, gameScene, "blue");
					unit.init(); 
					player.addUnit(unit);
					nerds--;
				}
				else if(ditz > 0){
					AUnit unit = new Ditz(gameMap, i.x, i.y, gameScene, "blue");
					unit.init(); 
					player.addUnit(unit);
					ditz--;
				}
				else if(jocks > 0){
					AUnit unit = new Jock(gameMap, i.x, i.y, gameScene, "blue");
					unit.init(); 
					player.addUnit(unit);
					jocks--;
				}
				else{
					AUnit unitbase = new Base(gameMap, i.x, i.y, gameScene, "blue");
					unitbase.init();
					player.setBase(unitbase);
				}
			}
		
		
		jocks = resourcesManager.unitArray2.get(0);
		nerds = resourcesManager.unitArray2.get(1);
		ditz = resourcesManager.unitArray2.get(2);

		spawns = resourcesManager.getSpawn2(resourcesManager.mapString);


		for(Point i : spawns){
				if(nerds > 0){
					AUnit unit = new Nerd(gameMap, i.x, i.y, gameScene, "red");
					unit.init(); 
					player2.addUnit(unit);
					nerds--;
				}
				else if(ditz > 0){
					AUnit unit = new Ditz(gameMap, i.x, i.y, gameScene, "red");
					unit.init(); 
					player2.addUnit(unit);
					ditz--;
				}
				else if(jocks > 0){
					AUnit unit = new Jock(gameMap, i.x, i.y, gameScene, "red");
					unit.init(); 
					player2.addUnit(unit);
					jocks--;
				}
				else{
					AUnit unitbase2 = new Base(gameMap, i.x, i.y, gameScene, "red");
					unitbase2.init();
					player2.setBase(unitbase2);
				}
			}
		
		
		player.beginTurn();
	}

	/**
	 * Gets player two.
	 * @return player2.
	 */
	public APlayer getOtherPlayer() {
		return player2;
	}
}
