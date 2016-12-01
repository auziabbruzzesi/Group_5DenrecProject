package model;

import java.awt.Point;

import javax.swing.Icon;

public class Player extends Character implements MoveObjects {

	private int health = 100;
	public static final Point startPosition = new Point(100,100);
	public static final int playerDimensions = 60;
	
		
	//Constructors (2)
	public Player(Point p){
		this.setCurrentPos(p);
		this.setVelocity(7);
	}
	public Player(Point p, int health){
		this.setCurrentPos(p);
		this.setHealth(health);
		this.setVelocity(2);
	}
	
/*
 * General functions
 */
	/**
	 * @author Auzi
	 * @param boType: the enum type of the beach object we want to pick up
	 * @return boolean: whether or not the player was able to pickup the object
	 * Purpose: 
	 * Last modified: 12.1.16
	 * 
	 */
		public boolean pickUp(HoldingType boType) {
			if (this.getH() == HoldingType.EMPTY) {
				this.setH(boType);
				return true;
			} else {
				return false;
			}
		}
		
		
	public void updateSprite(){
		this.getDirection();
//		crabPics[m.getP().findIndex()]);
	}

	
	/**
	 * 
	 * 
	 * functions for use in player movement
	 */
	
	public void moveNorth(){
		//if we have a full tick available to move
		if (this.getPosition().getY() - this.getDestination().getY() < this.getVelocity()) {
			this.getPosition().translate(this.getPosition().y - this.getDestination().y, 0);
		} 
		//otherwise, move whatever distance is left north
		else {
			this.getPosition().translate(0, -this.getVelocity());
		}
	}
	public void moveSouth(){
		if (this.getPosition().getY() + this.getVelocity() > this.getDestination().getY()) {
			// don't overshoot destination
			this.getPosition().translate(0,  this.getDestination().y- this.getPosition().y);
		} else {
			this.getPosition().translate(0, this.getVelocity());
		}
	}
	public void moveEast(){
		//check if there is enough space for 1 tick
		if (this.getDestination().getX() - this.getPosition().getX() < this.getVelocity()) {
			// don't overshoot destination
			
			this.getPosition().translate(this.getDestination().x- this.getPosition().x, 0);
		} else {
			this.getPosition().translate(this.getVelocity(), 0);
		}
	}
	public void moveWest(){
		if (this.getPosition().getX() - this.getVelocity() < this.getDestination().getX()) {
			// don't overshoot destination
			setCurrentPos(this.getDestination().getX(), this.getPosition().getY());
		} else {
			this.getPosition().translate(-this.getVelocity(), 0);
		}
	}
	
	/**
	 * @author EAviles
	 * 
	 * updates current position once based on current destination
	 */
	@Override
	public void move() {
		//System.out.println("calling move");
		// movement of player based on direction & whether current position =
		// destination
		switch (direction) {
		// SOUTHEAST
		case NORTH:
			//if we've not yet reached our destination
			if (this.getPosition().getY() != this.getDestination().getY()) {
				moveNorth();
			}
			break;
		case SOUTH:
			if (this.getPosition().getY() != this.getDestination().getY()) {
				moveSouth();
			}
			break;
				
		case EAST:
			if (this.getPosition().getX() != this.getDestination().getX()) {
				moveEast();
			}
			break;
		
		case WEST:
			if (this.getPosition().getX() != this.getDestination().getX()) {
				moveWest();
			}
			break;	
			
		case SOUTHEAST: // dest x&y > pos x&y
			//is x there yet?
			if (this.getPosition().getX() != this.getDestination().getX()) {
				//check if there is enough space for 1 tick
				moveEast();
			}
			if (this.getPosition().getY() != this.getDestination().getY()) {
				moveSouth();
			}
			break;
		// SOUTHWEST
		case SOUTHWEST: // dest x< pos x, dest y > pos y (Moving down and
						// left)
			// Handling x
			if (this.getPosition().getX() != this.getDestination().getX()) {
				moveWest();
			}
			// Handling y
			if (this.getPosition().getY() != this.getDestination().getY()) {
				moveSouth();
			}
			break;
		// NORTHEAST
		case NORTHEAST: // dest x > pos x, dest y < pos y
			// Handling x
			if (this.getPosition().getX() != this.getDestination().getX()) {
				moveEast();
			}

			// Handling y
			if (this.getPosition().getY() != this.getDestination().getY()) {
				moveNorth();
			}
			break;
		// NORTHWEST
		case NORTHWEST:
			// Handling x
			if (this.getPosition().getX() != this.getDestination().getX()) {
				moveWest();
			}

			// Handling y
			if (this.getPosition().getY() != this.getDestination().getY()) {
				moveNorth();
			}
			break;

		default:
			break;
		}// end switch
	}// end move()
	
	
	/**
	 * @author EAviles
	 * 
	 * figures out which direction we'll be moving in so 
	 * move can function properly
	 * 
	 * make directions Enums (enum class already made)
	 */
	public void updateDirection() {
	//moving North
//		if((getAngle() > 60) && (getAngle() <= 120)){
//			direction = Direction.NORTH;
//		//moving south
//		}else if((getAngle() > 240) && (getAngle() <= 300)){
//			direction = Direction.SOUTH;
//		}else if((getAngle() > 300) && (getAngle() <= 30)){
//			direction = Direction.EAST;
//		}else if((getAngle() > 150) && (getAngle() <= 210)){
//			direction = Direction.WEST;
//		}else if((getAngle() > 30) && (getAngle() <= 60)){
//			direction = Direction.NORTHEAST;
//			
//		}else if((getAngle() > 120) && (getAngle() <= 150)){
//			direction = Direction.NORTHWEST;
//		}else if((getAngle() > 300) && (getAngle() <= 330)){
//			direction = Direction.SOUTHEAST;
//		}else{
//			direction = Direction.SOUTHWEST;
//		}
//		System.out.println(direction);
		
	
		//System.out.println("updateDirection");
		//North

		if(this.getPosition().x == this.getDestination().x){
			if(this.getPosition().y > this.getDestination().y){
				direction = Direction.NORTH;
			}else{
				direction = Direction.SOUTH;
			}
		}
		else if(this.getPosition().y == this.getDestination().y){
			if(this.getPosition().x > this.getDestination().x){
				direction = Direction.WEST;
			}else{
				direction = Direction.EAST;
			}
		}
		
		else if (this.getPosition().getY() < this.getDestination().getY()) {
			// then we're moving generally south
			if (this.getPosition().getX() < this.getDestination().getX()) {
				// then we're moving southeast
				direction = Direction.SOUTHEAST;
			} else if (this.getPosition().getX() > this.getDestination().getX()) {
				// then we're moving southwest
				direction = Direction.SOUTHWEST;
			}
		} else if (this.getPosition().getY() > this.getDestination().getY()) {
			// then we're moving generally north
			if (this.getPosition().getX() > this.getDestination().getX()) {
				// then we're moving northwest
				direction = Direction.NORTHWEST;
			} else if (this.getPosition().getX() < this.getDestination().getX()) {
				// then we're moving northeast
				direction = Direction.NORTHEAST;
			}
		}
	}
	
	
/*
 * Setters and Getters
 */
	
	
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}

	
	
	
}
