package model;

import java.awt.Point;

public abstract class Character  {
	private Point currentPos;
	private Point destination; //?
	private int velocity;
	
	
	//Setters and Getters
	public Point getCurrentPos() {
		return currentPos;
	}
	public void setCurrentPos(Point currentPos) {
		this.currentPos = currentPos;
	}
	public Point getDestination() {
		return destination;
	}
	public void setDestination(Point destination) {
		this.destination = destination;
	}
	public int getVelocity() {
		return velocity;
	}
	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}
	
	/**
	 * updates currentPosition based on where the 
	 * user clicks
	 * @param p: a point that the object is trying to reach
	 */
	public abstract void move(Point destination);
	
	

}
