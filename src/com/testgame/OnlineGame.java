package com.testgame;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Point;
import android.util.Log;

import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.testgame.mechanics.unit.AUnit;
import com.testgame.mechanics.unit.Base;
import com.testgame.mechanics.unit.Ditz;
import com.testgame.mechanics.unit.Jock;
import com.testgame.mechanics.unit.Nerd;
import com.testgame.player.APlayer;
import com.testgame.player.ComputerPlayer;
import com.testgame.scene.GameScene;

public class OnlineGame extends AGame{
	
	
	public JSONArray moves;
	
	
	/**
	 * Player 2.
	 */
	private ComputerPlayer compPlayer;
	
	/**
	 * Variable used to determine if the player on the device has the last or first move in any turn.
	 */
	private boolean firstTurn;
	
	/**
	 * Constructor of an online game. Calls the super constructor.
	 * @param pOne player one.
	 * @param pTwo player two.
	 * @param xDim x dimension of the map.
	 * @param yDim y dimension of the map.
	 * @param game the game GUI.
	 * @param turn whether the player on the current device moves first.
	 */
	public OnlineGame(APlayer pOne, ComputerPlayer pTwo, int xDim, int yDim, GameScene game, boolean turn) {
		super(pOne,xDim, yDim, game);
		Log.d("xDim", xDim+"");
		Log.d("yDim", yDim+"");
		this.setFirstTurn(turn);
		moves = new JSONArray();
		this.setCompPlayer(pTwo);
		init();
		
	}
	
	/**
	 * Performs initialization needed to begin the game. Calls super init method.
	 */
	public void init() {
		super.init();
		int jocks = resourcesManager.unitArray.get(0);
		int nerds = resourcesManager.unitArray.get(1);
		int ditz = resourcesManager.unitArray.get(2);


		Point[] spawns;
		
		if(isFirstTurn()){
			spawns = resourcesManager.getSpawn2(resourcesManager.mapString);
		}
		else{
			spawns = resourcesManager.getSpawn1(resourcesManager.mapString);
		}
		
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
		
		
	}
	
	/**
	 * Ends the game by checking win cases between the two players..
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
	
	/**
	 * Starts the next turn. Sends the turn data into the cloud for the other player to use.
	 */
	public void nextTurn() {
		if(!getPlayer().isTurn())
			return;
		ParseObject turns = new ParseObject("Turns");
		turns.put("PlayerId", "user_"+ParseUser.getCurrentUser().getObjectId());
		turns.put("Player", "user_"+ParseUser.getCurrentUser().getObjectId()+"_"+getCount());
		turns.put("GameId", resourcesManager.gameId);
		turns.put("Device", resourcesManager.deviceID);
		turns.put("Moves", moves);
		turns.saveInBackground();
		try {
			JSONObject data = new JSONObject("{\"alert\": \"Next Turn\", \"deviceId\": \""+resourcesManager.gameId+"\", \"deviceId\": \""+resourcesManager.deviceID+"\", \"action\": \"com.testgame.NEXT_TURN\"}");
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
	
	/**
	 * Adds a move to the JSON array.
	 */
	@Override
	public void addMove(JSONObject move){ 
		moves.put(move);
	}
	
	/**
	 * Returns the player who you are playing against online.
	 * @return compPlayer.
	 */
	public ComputerPlayer getCompPlayer() {
		return compPlayer;
	}

	/**
	 * Sets the player who you are playing against online.
	 * @param compPlayer the person on the other device.
	 */
	public void setCompPlayer(ComputerPlayer compPlayer) {
		this.compPlayer = compPlayer;
	}

	/**
	 * Gets the boolean which is used to determine whether the player had the first or last move of the turn.
	 * @return firstTurn.
	 */
	public boolean isFirstTurn() {
		return firstTurn;
	}

	/**
	 * Sets the boolean which is used to determine whether the player had the first or last move of the turn.
	 * @param firstTurn boolean.
	 */
	public void setFirstTurn(boolean firstTurn) {
		this.firstTurn = firstTurn;
	}
	
}
