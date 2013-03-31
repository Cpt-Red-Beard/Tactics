package com.testgame.mechanics.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import android.graphics.Point;
import android.util.Log;

import com.testgame.mechanics.unit.AUnit;

public class GameMap implements IMap {
	
	/**
	 * Mapping of coordinate to a unit if that unit occupies it.
	 * Coordinates formatted as strings as in "x, y".
	 */
	protected HashMap<String, AUnit> coordMap;

	/**
	 * Maps each coordinate as a string to a corresponding Node object.
	 */
	protected HashMap<String, Node> graph;
	
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
		this.coordMap = new HashMap<String, AUnit>();
		this.graph = new HashMap<String, Node>();
		this.xDim = xDim;
		this.yDim = yDim;
		buildGraph();
	}

	@Override
	public boolean isOccupied(int x, int y) {		
		if (x < 0 || y < 0) {
			return true;
		}
		
		if (x >= xDim || y >= yDim) {
			return true;
		}
		
		if (coordMap.get(entry(x, y)) != null) {
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
	
	@Override
	public String entry(int x, int y) {
		return x + ", " + y;
	}

	@Override
	public int manhattanDistance(Point s, Point d) {
		//Log.d("AndEngine", "Calculating shortest path!");
		Node startNode = graph.get(this.entry(s.x, s.y));
		Node goal = graph.get(this.entry(d.x, d.y));
		if (goal == null) return -1;

		ArrayList<Node> closedSet = new ArrayList<Node>(); // Nodes already evaluated
		ArrayList<Node> openSet = new ArrayList<Node>(); // Nodes for tentative evaluation

		startNode.setParentNode(null);
		startNode.setGScore(0.0);
		startNode.setFScore(Node.h(startNode, goal));
		openSet.add(startNode);
		
		// A* search
		while (openSet.size() > 0) {
			Collections.sort(openSet);
			Node cur = openSet.remove(0);
			double curGScore = cur.gScore();
			
			if (cur.equals(goal)) {
				//Log.d("AndEngine", "A* found the goal!");
				closedSet.add(cur);
				int pathLength = getSteps(cur);
				resetParentNodes(); // Shortest path may be different as map changes	
				return pathLength;	
			}
			
			closedSet.add(cur);
			HashSet<Node> neighbors = cur.neighbors();
			for (Node neigh : neighbors) {
				if (closedSet.contains(neigh) || neigh.isObstacle())
					continue;
				double tentativeGScore = curGScore + 1.0;
				
				if (!(openSet.contains(neigh)) || (tentativeGScore < neigh.gScore())) {
					neigh.setParentNode(cur);
					neigh.setGScore(tentativeGScore);
					neigh.setFScore(tentativeGScore + Node.h(neigh, goal));
					if (!(openSet.contains(neigh)))
						openSet.add(neigh);
				}
			}
		}

		return -1; // Didn't find a path...
	}

	
	/**
	 * Returns number of steps in calculated shortest path.
	 * @param goal The goal node.
	 */
	private int getSteps(Node goal) {
		int steps = 0;
		Node curNode = goal;
		while (curNode.parent() != null) {
			steps++;
			curNode = curNode.parent();
		}
		return steps;
	}

	
	/**
	 * Resets all nodes' parent nodes.
	 */
	private void resetParentNodes() {
		for (int i = 0; i < this.xDim; i++) {
			for (int j = 0; j < this.yDim; j++) {
				(graph.get(this.entry(i, j))).setParentNode((Node) null);
			}
		}
	}


	/**
	 * Builds the graph representation of the grid.
	 */
	private void buildGraph() {
		// First add all nodes to the graph
		for (int i = 0; i < this.xDim; i++) {
			for (int j = 0; j < this.yDim; j++) 				
				graph.put(this.entry(i, j), new Node(this, i, j));
			
		}
		// Add neighbors to each node
		for (int i = 0; i < this.xDim; i++) {
			for (int j = 0; j < this.yDim; j++) {
				Node curNode = graph.get(this.entry(i, j));
				Node leftNeigh = graph.get(this.entry(i-1, j));
				Node rightNeigh = graph.get(this.entry(i+1, j));
				Node topNeigh = graph.get(this.entry(i, j+1));
				Node bottomNeigh = graph.get(this.entry(i, j-1));
				if (leftNeigh != null) curNode.setNeighbor(leftNeigh);
				if (rightNeigh != null) curNode.setNeighbor(rightNeigh);
				if (topNeigh != null) curNode.setNeighbor(topNeigh);
				if (bottomNeigh != null) curNode.setNeighbor(bottomNeigh);
			}
		}
	}

}
