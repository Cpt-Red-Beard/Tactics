package com.testgame.mechanics.map;

import android.graphics.Point;

import com.testgame.mechanics.unit.AUnit;
import com.testgame.player.APlayer;

/**
 * Interface for a  game map.
 * @author Alen Lukic
 *
 */
public interface IMap {
	
	/**
	 * Checks to see whether the indicated space on the map is occupied.
	 * @param x The x-coordinate.
	 * @param y The y-coordinate.
	 * @return Whether the space is occupied or not.
	 */
	public boolean isOccupied(int x, int y);
	
	/**
	 * If a unit is occupying this square, returns the reference to the 
	 * occupying unit; otherwise, returns null.
	 * @param x The x-coordinate.
	 * @param y The y-coordinate.
	 */
	public AUnit getOccupyingUnit(int x, int y);
	
	/**
	 * Sets the indicated space as occupied.
	 * @param x The x-coordinate.
	 * @param y The y-coordinate.
	 * @param unit The unit which now occupies this square.
	 */
	public void setOccupied(int x, int y, AUnit unit);
	
	/**
	 * Sets the indicated space as unoccupied.
	 * @param x The x-coordinate.
	 * @param y The y-coordinate.
	 */
	public void setUnoccupied(int x, int y);

	/**
	 * Returns x, y as a string.
	 * @param x The x-coordinate.
	 * @param y The y-coordinate.
	 */
	public String entry(int x, int y);

	/**
	 * Performs A* and returns distance from s to d, or -1 if no path is available.
	 * @param s The source.
	 * @param d The destination.
	 * @param requestingPlayer Player trying to move this unit.
	 */ 
	public int manhattanDistanceAStar(Point s, Point d, APlayer requestingPlayer);

}
