package com.testgame.mechanics.map;

import com.testgame.mechanics.unit.AUnit;

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

}
