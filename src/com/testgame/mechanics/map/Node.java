package com.testgame.mechanics.map;

import java.util.HashSet;

/**
 * A graph node.
 * @author Alen Lukic
 *
 */
public class Node implements Comparable<Node> {

	/**
	 * The map which this Node is on.
	 */
	private GameMap map;
	
	/**
	 * The x-value of this node.
	 */
	private int x;
	
	/**
	 * The y-value of this node.
	 */
	private int y;
	
	/**
	 * Rover number who discovered this space; or, -2 if obstacle.
	 */
	private int value;
	
	/**
	 * F-score for dynamic A*.
	 */
	private double fScore;
	
	/**
	 * G-score for dynamic A*.
	 */
	private double gScore;
	
	/**
	 * Parent node in dynamic A*.
	 */
	private Node parent;
	
	/**
	 * Set of neighbors.
	 */
	private HashSet<Node> neighbors;

	/**
	 * Constructor.
	 * @param x x-coordinate of the node.
	 * @param y y-coordinate of the node.
	 * @param map Map this node is on.
	 */
	public Node(GameMap map, int x, int y) {
		this.map = map;
		this.x = x;
		this.y = y;
		this.neighbors = new HashSet<Node>();
	}
	
	/**
	 * Sets the node value.
	 * @param newVal The new value of the node.
	 */
	public void setValue(int newVal) {
		this.value = newVal;
	}
	
	/**
	 * Returns whether this node is an obstacle or not.
	 */
	public boolean isObstacle() {
		return map.isOccupied(x, y);
	}
	
	/**
	 * Gets x-coordinate.
	 * @return The x-coordinate.
	 */
	public Integer x() {
		return x;
	}
	
	/**
	 * Gets y-coordinate.
	 * @return The y-coordinate.
	 */
	public Integer y() {
		return y;
	}
	
	/**
	 * Gets the value of the node.
	 * @return Value of this node.
	 */
	public Integer value() {
		return value;
	}
	
	/**
	 * Adds neighbor of this node.
	 * @param neigh The neighbor.
	 */
	public void setNeighbor(Node neigh) {
		neighbors.add(neigh);
	}
	
	/**
	 * Returns all neighbors of this node.
	 * @return The node's neighbors.
	 */
	public HashSet<Node> neighbors() {
		return neighbors;
	}
	
	
	/**
	 * Sets parent node in dynamic A* algorithm.
	 * @param parent The parent node.
	 */
	public void setParentNode(Node parent) {
		this.parent = parent;
	}
	
	/**
	 * Returns the parent node.
	 * @return DA* parent node.
	 */
	public Node parent() {
		return this.parent;
	}
	
	/**
	 * Sets the f-score.
	 * @param fScore Score to set.
	 */
	public void setFScore(double fScore) {
		this.fScore = fScore;
	}
	
	/**
	 * Returns the f-score.
	 * @return The f-score.
	 */
	public double fScore() {
		return this.fScore;
	}
	
	/**
	 * Sets the g-score.
	 * @param gScore Score to set.
	 */
	public void setGScore(double gScore) {
		this.gScore = gScore;
	}
	
	/**
	 * Returns the g-score.
	 * @return The g-score.
	 */
	public double gScore() {
		return this.gScore;
	}
	
	/**
	 * Returns Euclidean distance between the indicated nodes (used for heuristic function).
	 * @param source The first node.
	 * @param dest The second node.
	 * @return Euclidean distance between the two nodes.
	 */
	public static double h(Node source, Node dest) {
		if (dest.isObstacle())
			return Double.MAX_VALUE;
		return Math.sqrt(Math.pow(source.x() - dest.x(), 2) + Math.pow(source.y() - dest.y(), 2));
	}
	
	@Override
	public int compareTo(Node theOther) {
		if (this.fScore() == theOther.fScore()) 
			return 0;
		else
			return (this.fScore > theOther.fScore()) ? 1 : -1;
	}
	
	@Override
	public boolean equals(Object otherNode) {
		return (this.x() == ((Node) otherNode).x()) && (this.y() == ((Node) otherNode).y());
	}
	
	

}
