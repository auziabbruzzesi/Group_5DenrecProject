package model;

import java.awt.Point;

public abstract class Character implements MoveObjects {
	private Point currentPos;
	private Point destination = currentPos;
	private int velocity = 7;
	HoldingType h = HoldingType.EMPTY;
	String direction = "";

	public void setCurrentPos(double d, double e){
		this.currentPos.setLocation(d, e);
	}	
	
	@Override
	public void move() {
		//System.out.println("calling move");
		// movement of player based on direction & whether current position =
		// destination
		switch (direction) {
		// SOUTHEAST
		case "southeast": // dest x&y > pos x&y
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
		case "southwest": // dest x< pos x, dest y > pos y (Moving down and
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
		case "northeast": // dest x > pos x, dest y < pos y
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
		case "northwest":
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
	
	
	
	public void updateDirection() {
		//System.out.println("updateDirection");
		if (currentPos.getY() < destination.getY()) {
			// then we're moving generally south
			if (currentPos.getX() < destination.getX()) {
				// then we're moving southeast
				direction = "southeast";
			} else if (currentPos.getX() > destination.getX()) {
				// then we're moving southwest
				direction = "southwest";
			}
		} else if (currentPos.getY() > destination.getY()) {
			// then we're moving generally north
			if (currentPos.getX() > destination.getX()) {
				// then we're moving northwest
				direction = "northwest";
			} else if (currentPos.getX() < destination.getX()) {
				// then we're moving northeast
				direction = "northeast";
			}
		}
	}

	public boolean pickUp(HoldingType boType) {
		if (this.h == HoldingType.EMPTY) {
			this.h = boType;
			return true;
		} else {
			return false;
		}
	}

	public void putDown(HoldingType boxObjectType) {
		;
	}

	// Setters and Getters
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
}
