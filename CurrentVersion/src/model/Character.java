package model;

import java.awt.Point;

public abstract class Character implements MoveObjects {
	private Point currentPos;
	private Point destination = currentPos;
	private int velocity;
	private HoldingType h = HoldingType.EMPTY;
	Direction direction = Direction.EAST;//default

		
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
		case SOUTHEAST: // dest x&y > pos x&y
			if (currentPos.getX() != destination.getX()) {
				if (destination.getX() - currentPos.getX() < velocity) {
					// don't overshoot destination
					currentPos.translate((int) (destination.getX() - currentPos.getX()), 0);
				} else {
					currentPos.translate(velocity, 0);
				}
			}
			if (currentPos.getY() != destination.getY()) {
				if (destination.getY() - currentPos.getY() < velocity) {
					// don't overshoot destination
					currentPos.translate(0, (int) (destination.getY() - currentPos.getY()));
				} else {
					currentPos.translate(0, velocity);
				}
			}
			break;
		// SOUTHWEST
		case SOUTHWEST: // dest x< pos x, dest y > pos y (Moving down and
							// left)
			// Handling x
			if (currentPos.getX() != destination.getX()) {
				if (currentPos.getX() - velocity < destination.getX()) {
					// don't overshoot destination
					setCurrentPos(destination.getX(), currentPos.getY());
				} else {
					currentPos.translate(-velocity, 0);
				}
			}
			// Handling y
			if (currentPos.getY() != destination.getY()) {
				if (currentPos.getY() + velocity > destination.getY()) {
					// don't overshoot destination
					currentPos.translate(0, (int) (destination.getY() - currentPos.getY()));
				} else {
					currentPos.translate(0, velocity);
				}
			}
			break;
		// NORTHEAST
		case NORTHEAST: // dest x > pos x, dest y < pos y
			// Handling x
			if (currentPos.getX() != destination.getX()) {
				if (destination.getX() - currentPos.getX() < velocity) {
					// don't overshoot destination
					currentPos.translate((int) (destination.getX() - currentPos.getX()), 0);
				} else {
					currentPos.translate(velocity, 0);
				}
			}

			// Handling y
			if (currentPos.getY() != destination.getY()) {

				if (currentPos.getY() - destination.getY() < velocity) {
					currentPos.translate((int) (currentPos.getY() - destination.getY()), 0);
				} else {
					currentPos.translate(0, -velocity);
				}
			}
			break;
		// NORTHWEST
		case NORTHWEST:
			// Handling x
			if (currentPos.getX() != destination.getX()) {
				// Handling x
				if (currentPos.getX() != destination.getX()) {
					if (currentPos.getX() - velocity < destination.getX()) {
						// don't overshoot destination
						setCurrentPos(destination.getX(), getCurrentPos().getY());
					} else {
						currentPos.translate(-velocity, 0);
					}
				} else {
					currentPos.translate(-velocity, 0);
				}
			}

			// Handling y
			if (currentPos.getY() != destination.getY()) {

				if (currentPos.getY() - destination.getY() < velocity) {
					currentPos.translate((int) (currentPos.getY() - destination.getY()), 0);
				} else {
					currentPos.translate(0, -velocity);
				}
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
		//System.out.println("updateDirection");
		if (currentPos.getY() < destination.getY()) {
			// then we're moving generally south
			if (currentPos.getX() < destination.getX()) {
				// then we're moving southeast
				direction = Direction.SOUTHEAST;
			} else if (currentPos.getX() > destination.getX()) {
				// then we're moving southwest
				direction = Direction.SOUTHWEST;
			}
		} else if (currentPos.getY() > destination.getY()) {
			// then we're moving generally north
			if (currentPos.getX() > destination.getX()) {
				// then we're moving northwest
				direction = Direction.NORTHWEST;
			} else if (currentPos.getX() < destination.getX()) {
				// then we're moving northeast
				direction = Direction.NORTHEAST;
			}
		}
	}
/**
 * @author Auzi
 * 
 * @param boType: the enum type of the beach object we're picking up
 * 
 */
	public boolean pickUp(HoldingType boType) {
		if (this.h == HoldingType.EMPTY) {
			this.h = boType;
			return true;
		} else {
			return false;
		}
	}

	
	
	// Setters and Getters
	public void setH(HoldingType h) {
		this.h = h;
	}
	public HoldingType getH() {
		return h;
	}
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
	public void setCurrentPos(double d, double e){
		this.currentPos.setLocation(d, e);
	}
}
