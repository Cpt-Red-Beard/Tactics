package com.testgame;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.testgame.mechanics.unit.AUnit;
import com.testgame.mechanics.unit.Ditz;
import com.testgame.mechanics.unit.Jock;
import com.testgame.mechanics.unit.Nerd;
import com.testgame.player.APlayer;
import com.testgame.player.Base;
import com.testgame.player.ComputerPlayer;
import com.testgame.scene.GameScene;

public class OnlineGame extends AGame{
	
	
	public JSONArray moves;
	
	
	/**
	 * Player 2.
	 */
	private ComputerPlayer compPlayer;
	
	
	private boolean firstTurn;
	
	public OnlineGame(APlayer pOne, ComputerPlayer pTwo, int xDim, int yDim, GameScene game, boolean turn) {
		super(pOne,xDim, yDim, game);
		this.setFirstTurn(turn);
		moves = new JSONArray();
		this.setCompPlayer(pTwo);
		init();
		
	}
	
	/**
	 * Performs initialization needed to begin the game.
	 */
	public void init() {
		
		int jocks = resourcesManager.unitArray.get(0);
		int nerds = resourcesManager.unitArray.get(1);
		int ditz = resourcesManager.unitArray.get(2);
		int j = 1;
		int x = 0;
		if(isFirstTurn()){
			j = 10;
			x = 11;
		}
		
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
		
		AUnit unitbase = new Base(gameMap, 5, x, gameScene, "blue");
		player.setBase(unitbase);
	}
	
	/**
	 * Ends the game.
	 */
	public void endGame() {
		if(player.getActiveUnits().size() == 0 || player.getBase() == null){
			gameScene.activity.runOnUiThread(new Runnable() {
        	    @Override
        	    public void run() {
        	    	gameScene.quitDialog("You Lose!");
          			 
        	    }
        	});
			
			
			
		}
		else if(compPlayer.getActiveUnits().size() == 0 || compPlayer.getBase() == null){
			nextTurn();
			gameScene.activity.runOnUiThread(new Runnable() {
        	    @Override
        	    public void run() {
        	    	gameScene.quitDialog("You Win!");
          			 
        	    }
        	});
			
			
			
		}
		
	}
	
	public void nextTurn() {
		if(!getPlayer().isTurn())
			return;
		Log.d("Turn", getCount()+"");
		ParseObject turns = new ParseObject("Turns");
		turns.put("PlayerId", "user_"+ParseUser.getCurrentUser().getObjectId());
		turns.put("Player", "user_"+ParseUser.getCurrentUser().getObjectId()+"_"+getCount());
		turns.put("GameId", resourcesManager.gameId);
		turns.put("Device", resourcesManager.deviceID);
		turns.put("Moves", moves);
		turns.saveInBackground();
		try {
			JSONObject data = new JSONObject("{\"alert\": \"Next Turn\", \"deviceId\": \""+resourcesManager.deviceID+"\", \"action\": \"com.testgame.NEXT_TURN\"}");
			 ParsePush push = new ParsePush();
			 push.setChannel("user_"+resourcesManager.opponentString);
             push.setData(data);
			 
             push.sendInBackground();
        } catch (JSONException e) {
			e.printStackTrace();
		}
		 
		
		
		moves = new JSONArray();
		if(!this.isFirstTurn()) 
			this.incrementCount(); 
		this.getPlayer().endTurn();
		
	}
	
	@Override
	public void addMove(JSONObject move){ 
		moves.put(move);
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
	
}
