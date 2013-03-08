package com.testgame.mechanics.map;

import java.util.HashMap;

import com.testgame.mechanics.unit.AUnit;

public class GameMap implements IMap {
	
	/**
	 * Mapping of coordinate to a unit if that unit occupies it.
	 * Coordinates formatted as strings as in "x, y".
	 */
	protected HashMap<String, AUnit> coordMap;
	
	/**
	 * X-dimensionality of the map.
	 */
	public int xDim;
	
	/**
	 * Y-dimensionality of the map.
	 */
	public int yDim;
	
	/**
	 * Constructor. 
	 */
	public GameMap(int xDim, int yDim) {
		coordMap = new HashMap<String, AUnit>();
		this.xDim = xDim;
		this.yDim = yDim;
	}

	@Override
	public boolean isOccupied(int x, int y) {
		
		//Log.d("AndEngine", "is occupied? "+x+", "+y);
		
		if (x < 0 || y < 0) {
			return true;
		}
		
		if (x >= xDim || y >= yDim) {
			return true;
		}
		
		if (coordMap.get(entry(x,y)) != null) {
			return true;
		}
		return false;
	}
	
	@Override
	public AUnit getOccupyingUnit(int x, int y) {
		return coordMap.get(entry(x,y));
	}

	@Override
	public void setOccupied(int x, int y, AUnit unit) {
		if (x <= (xDim - 1) && x >= 0) {
			if (y <= (yDim - 1) && y >= 0) {
				coordMap.put(entry(x,y), unit);
			}
		}
	}

	@Override
	public void setUnoccupied(int x, int y) {
		if (coordMap.get(entry(x, y)) != null)
			coordMap.remove(entry(x,y));
	}
	
	public String entry(int x, int y) {
		return x+", "+y;
	}

}
