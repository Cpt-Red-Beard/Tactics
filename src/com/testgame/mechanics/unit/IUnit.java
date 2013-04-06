package com.testgame.mechanics.unit;

import java.util.ArrayList;

import android.graphics.Point;

import com.testgame.player.APlayer;

/**
 * Interface which defines a unit in the game. 
 * @author Alen Lukic
 *
 */
public interface IUnit {
	
	/**
	 * Sets player ownership of this unit.
	 * @param player The player who owns this unit.
	 */
	public void setPlayer(APlayer player);
	
	/*
	 * Returns the player who owns this unit.
	 * @return The player who owns this unit.
	 */
	public APlayer getPlayer();
	
	/**
	 * Gets unit's x-coordinate.
	 * @return Unit's x-coordinate.
	 */
	public int getMapX();
	
	/**
	 * Gets unit's y-coordinate.
	 * @return Unit's y-coordinate.
	 */
	public int getMapY();
	
	/**
	 * Gets unit's health.
	 * @return The current health the unit has.
	 */
	public int getHealth();
	
	/**
	 * Gets unit's attack power.
	 * @return The unit's attack power.
	 */
	public int getAttack();
	
	/**
	 * Gets unit's range.
	 * @return The number of squares that a unit can move at a time.
	 */
	public int getRange();
	
	/**
	 * Gets the unit's current energy.
	 * @return Unit's current energy.
	 */
	public int getEnergy();
	
	/**
	 * Indicates whether this unit is currently in a defensive state or not.
	 * @return Boolean value indicating whether the unit is in a defensive state.
	 */
	public boolean isDefending();
	
	/**
	 * Unit moves.
	 * @param xNew Unit's new x-coordinate.
	 * @param yNew Unit's new y-coordinate.
	 */
	public void move(int xNew, int yNew, ArrayList<Point> path, int cost);
	
	
	
	/**
	 * Unit defends (takes half damage if attacked).
	 */
	public void defend();
	
	/**
	 * Takes away a certain amount of health from a unit.
	 * @param health The amount of health to remove.
	 */
	public void reduceHealth(int health);
	
	/**
	 * Restores a certain amount of energy to a unit.
	 * @param energy The amount of energy to restore.
	 */
	public void restoreEnergy(int energy);
	
	/**
	 * Takes away a certain amount of energy from a unit.
	 * @param energy The amount of energy to remove.
	 */
	public void reduceEnergy(int energy);
	
	/**
	 * Performs stats adjustments (e.g. restoring energy) for this unit at the beginning of the player's turn.
	 */
	public void turnInit();

	public void attack(AUnit unit);

}
