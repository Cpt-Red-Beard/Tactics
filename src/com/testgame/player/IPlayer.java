package com.testgame.player;

import java.util.ArrayList;

import com.testgame.mechanics.unit.AUnit;

/**
 * Interfaces which defines a player.
 * @author Alen Lukic
 *
 */
public interface IPlayer {
	
	/**
	 * Begins the player's turn.
	 */
	public void beginTurn();
	
	/**
	 * Ends the player's turn.
	 */
	public void endTurn();
	
	/** 
	 * Checks whether it's this player's turn.
	 * @return True if it's the player's turn, false otherwise
	 */
	public boolean isTurn();
	
	/**
	 * Adds a unit to this player's set of controlled units.
	 * @param unit The unit to add.
	 */
	public void addUnit(AUnit unit);
	
	/**
	 * Removes the specified unit from the player's set of controlled units.
	 * @param unit THe unit to remove.
	 */
	public void removeUnit(AUnit unit);
	
	/**
	 * Returns the player's units.
	 * @return List of the player's current units.
	 */
	public ArrayList<AUnit> getUnits();

}
