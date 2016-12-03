package model;

import java.awt.Point;

import javax.swing.Icon;

public class Player extends GameObject implements MoveObjects {

	private int health = 100;
	public static final Point startPosition = new Point(100, 100);
	public static final int playerDimensions = 60;
	private Point destination;
	private int velocity;
	Direction direction = Direction.EAST;//default
	
	// Constructor
	public Player(Point p) {
		this.setPosition(p);
		this.setVelocity(7);
		this.setDestination(this.getPosition());
		this.setHT(HoldingType.EMPTY);
	}

	/*
	 * General functions
	 */
	/**
	 * @author Auzi
	 * @param boType:
	 *            the enum type of the beach object we want to pick up
	 * @return boolean: whether or not the player was able to pickup the object
	 *         Purpose: Last modified: 12.1.16
	 * 
	 */
	public boolean pickUp(HoldingType boType) {
		if (this.getHT() == HoldingType.EMPTY) {
			this.setHT(boType);
			return true;
		} else {
			return false;
		}
	}

	public void updateSprite() {
		this.getDirection();
		// crabPics[m.getP().findIndex()]);
	}

	/**
	 * @author Eaviles
	 * Purpose: moves the player north (decrements y-coordinate of position) 
	 * by 1 increment of velocity, or by whatever distance is left.
	 * 
	 */

	public void moveNorth() {
		// if we don't have a full tick available to move, move whatever
		// distance is left north
		if (this.getPosition().getY() - this.getDestination().getY() < this.getVelocity()) {
			this.getPosition().translate(0, this.getPosition().y - this.getDestination().y);
			this.setPosition(this.getPosition().x, this.getDestination().y);
		}
		// otherwise, move 1 tick
		else {
			this.getPosition().translate(0, -this.getVelocity());
		}
	}

	/**
	 * @author Eaviles
	 * Purpose: moves the player south (increments y-coordinate of position) 
	 * by 1 increment of velocity, or by whatever distance is left.
	 * 
	 */
	public void moveSouth() {
		if (this.getPosition().getY() + this.getVelocity() > this.getDestination().getY()) {
			// don't overshoot destination
			this.getPosition().translate(0, this.getDestination().y - this.getPosition().y);
		} else {
			this.getPosition().translate(0, this.getVelocity());
		}
	}
	/**
	 * @author Eaviles
	 * Purpose: moves the player east (increments x-coordinate of position) 
	 * by 1 increment of velocity, or by whatever distance is left.
	 * 
	 */
	public void moveEast() {
		// check if there is enough space for 1 tick
		if (this.getDestination().getX() - this.getPosition().getX() < this.getVelocity()) {
			// don't overshoot destination

			this.getPosition().translate(this.getDestination().x - this.getPosition().x, 0);
		} else {
			this.getPosition().translate(this.getVelocity(), 0);
		}
	}
	/**
	 * @author Eaviles
	 * Purpose: moves the player east (decrements x-coordinate of position) 
	 * by 1 increment of velocity, or by whatever distance is left.
	 * 
	 */
	public void moveWest() {
		if (this.getPosition().getX() - this.getVelocity() < this.getDestination().getX()) {
			// don't overshoot destination
			setPosition(this.getDestination().x, this.getPosition().y);
		} else {
			this.getPosition().translate(-this.getVelocity(), 0);
		}
	}

	/**
	 * @author EAviles
	 * Function Purpose: to update Player's position by one increment of velocity. 
	 * This function checks player's current direction and destination, then checks whether position's
	 * x & y coordinates match those of destination. If either does not match, x and/or y is incremented
	 * by velocity, or (if distance to destination is less than velocity), by whatever distance remains.
	 * 
	 */
	@Override
	public void move() {
		// System.out.println("calling move");
		// movement of player based on direction & whether current position =
		// destination
		switch (direction) {
		// SOUTHEAST
		case NORTH:
			// if we've not yet reached our destination
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
			// is x there yet?
			if (this.getPosition().getX() != this.getDestination().getX()) {
				// check if there is enough space for 1 tick
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
	 * Purpose: Sets player direction by checking position & destination.
	 */
	public void updateDirection() {

		if (this.getPosition().x == this.getDestination().x) {
			if (this.getPosition().y > this.getDestination().y) {
				direction = Direction.NORTH;
			} else {
				direction = Direction.SOUTH;
			}
		} else if (this.getPosition().y == this.getDestination().y) {
			if (this.getPosition().x > this.getDestination().x) {
				direction = Direction.WEST;
			} else {
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

	public int findIndex() {
		return direction.getRank() + (this.getHT().getRank() * 8);
	}
	/*
	 * Setters and Getters
	 */

	public void setDestination(Point destination) {
		this.destination = destination;
	}

	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public Point getDestination() {
		return destination;
	}

	public int getVelocity() {
		return velocity;
	}

	public Direction getDirection() {
		return direction;
	}
}
