package com.testgame.player;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.testgame.AGame;
import com.testgame.OnlineGame;
import com.testgame.mechanics.unit.AUnit;
import com.testgame.mechanics.unit.Ditz;
import com.testgame.mechanics.unit.Jock;
import com.testgame.mechanics.unit.Nerd;

public class ComputerPlayer extends APlayer {
	
	JSONArray actionsToPerform;
	AGame game;
	
	public ComputerPlayer(String name) {
		super(name);
	}
	
	public void startTurn(final OnlineGame game, JSONArray array){
		
		this.actionsToPerform = array;
		this.game = game;
		this.beginTurn();
		performNext(); // perform all of the animations
		
		if(game.isFirstTurn()) 
			game.incrementCount();
		game.getPlayer().beginTurn(); // this calls turn init on all the units
		this.endTurn();
		
	}
	
	public void performNext() {
		if(actionsToPerform.length() == 0){
			game.getGameScene().activity.runOnUiThread(new Runnable() {
        	    @Override
        	    public void run() {
        	    	game.getGameScene().textMenu("Your Turn!");
          			 
        	    }
        	});
		}
		for (int i = 0; i < actionsToPerform.length(); i++) {
			
			Log.d("AndEngine", "[PerformNext] i = "+i);

			if (actionsToPerform.isNull(i)) {
				if (i == actionsToPerform.length() - 1) {
					game.getGameScene().activity.runOnUiThread(new Runnable() {
		        	    @Override
		        	    public void run() {
		        	    	game.getGameScene().textMenu("Your Turn!");
		          			 
		        	    }
		        	});
				}
				else continue; // performed this action already.
			}
			
			else {
		
				try {
					
					JSONObject nextAction = actionsToPerform.getJSONObject(i);
					String moveType = nextAction.getString("MoveType");
					
					int unitX = nextAction.getInt("UnitX");
					int unitY = nextAction.getInt("UnitY");
					
					AUnit unit = game.gameMap.getOccupyingUnit(unitX, unitY);
					
					if (moveType.equals("MOVE")) {
						
						Log.d("AndEngine", "[PerformNext] found move!");
						
						int destX = nextAction.getInt("DestX");
						int destY = nextAction.getInt("DestY");
						int energy = nextAction.getInt("Energy");
						
						actionsToPerform.put(i, null); // finished action, clear it out
						
						unit.ComputerMove(destX, destY, energy, this);
					}
					
					else if (moveType.equals("ATTACK")) {
						
						Log.d("AndEngine", "[PerformNext] found attack!");
						
						int targetX = nextAction.getInt("OppX");
						int targetY = nextAction.getInt("OppY");
						int energy = nextAction.getInt("Energy");
						int attack = nextAction.getInt("Attack");
						
						AUnit target = game.gameMap.getOccupyingUnit(targetX, targetY);
						
						actionsToPerform.put(i, null); // finished action, clear it out
						
						unit.ComputerAttack(target, attack, energy, this);
					}
					
					return;
					
				} catch (JSONException e) {
					// Failure getting the next move
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public void init(final OnlineGame game, JSONObject object) {
		int nerds = 0;
		int jocks = 0;
		int ditz = 0;
		
		
		try {
			ditz = object.getInt("Ditzes");
			nerds = object.getInt("Nerds");
			jocks = object.getInt("Jocks");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("Nerds", nerds+" ");
		Log.d("Jocks", jocks +  " ");
		Log.d("Nerds", ditz + " ");
		int j = 10;
		if(game.isFirstTurn())
			j = 0;
		
		
			for(int i = 0; i < 10; i++){
				if(nerds > 0){
					AUnit unit = new Nerd(game.gameMap, i, j, game.getGameScene(), "red");
					unit.init(); 
					game.getCompPlayer().addUnit(unit);
					nerds--;
				}
				else if(ditz > 0){
					AUnit unit = new Ditz(game.gameMap, i, j, game.getGameScene(), "red");
					unit.init();
					game.getCompPlayer().addUnit(unit);
					ditz--;
				}
				else if(jocks > 0){
					AUnit unit = new Jock(game.gameMap, i, j, game.getGameScene(), "red");
					unit.init(); 
					game.getCompPlayer().addUnit(unit);
					jocks--;
				}
			}
		

		game.incrementCount();
		if(game.isFirstTurn()) {
			game.getPlayer().beginTurn();
			game.getGameScene().activity.runOnUiThread(new Runnable() {
        	    @Override
        	    public void run() {
        	    	game.getGameScene().textMenu("Your Turn!");
          			 
        	    }
        	});
		}
		
	}

}
