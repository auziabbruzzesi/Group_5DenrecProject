package model;

import java.awt.Point;

import javax.swing.Icon;

public abstract class Character {
	private Point currentPos;
	private Point destination = currentPos;
	private int velocity;
	private HoldingType h = HoldingType.EMPTY;
	Direction direction = Direction.EAST;//default
	private Icon objIcon;		
	
	/**
	 * 
	 * @param location:currentPos
	 * @param target:destination
	 * @return angle for use in finding directions
	 */
	public float getAngle() {
	    float angle = (float) Math.toDegrees(Math.atan2(destination.y - currentPos.y, destination.x - currentPos.x));

	    if(angle < 0){
	        angle += 360;
	    }

	    return angle;
	}

	
	/**
	 * finds the proper index for the image
	 * based on direction and holdingType
	 * @return
	 */
	public int findIndex(){
		return direction.getRank()+ (h.getRank() * 8);
	}
	
	
	// Setters and Getters
	public void setH(HoldingType h) {
		this.h = h;
	}
	public HoldingType getH() {
		return h;
	}
	public Point getPosition() {
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
	public Direction getDirection() {
		return direction;
	}


	public void setDirection(Direction direction) {
		this.direction = direction;
	}


	public void setCurrentPos(double d, double e){
		this.currentPos.setLocation(d, e);
	}


	public Icon getObjIcon() {
		return objIcon;
	}


	public void setObjIcon(Icon objIcon) {
		this.objIcon = objIcon;
	}
}
