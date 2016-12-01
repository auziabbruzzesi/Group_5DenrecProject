package model;

import java.awt.Point;

import javax.swing.Icon;

public abstract class Character implements MoveObjects {
	private Point currentPos;
	private Point destination = currentPos;
	private int velocity;
	private HoldingType h = HoldingType.EMPTY;
	Direction direction = Direction.EAST;//default
	private Icon objIcon;
		
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
			if (currentPos.getY() != destination.getY()) {

				if (currentPos.getY() - destination.getY() < velocity) {
					currentPos.translate(currentPos.y - destination.y, 0);
				} else {
					currentPos.translate(0, -velocity);
				}
			}
			break;
		case SOUTH:
			if (currentPos.getY() != destination.getY()) {
				if (currentPos.getY() + velocity > destination.getY()) {
					// don't overshoot destination
					currentPos.translate(0,  destination.y- currentPos.y);
				} else {
					currentPos.translate(0, velocity);
				}
			}
			break;
		
		
		
		
		case EAST: // dest x&y > pos x&y
			//is x there yet?
			if (currentPos.getX() != destination.getX()) {
				//check if there is enough space for 1 tick
				if (destination.getX() - currentPos.getX() < velocity) {
					// don't overshoot destination
					
					currentPos.translate(destination.x- currentPos.x, 0);
				} else {
					currentPos.translate(velocity, 0);
				}
			}
			break;
		
		case WEST: // dest x< pos x, dest y > pos y (Moving down and
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
			break;
		
		

			
			
		
			
			
			
		case SOUTHEAST: // dest x&y > pos x&y
			//is x there yet?
			if (currentPos.getX() != destination.getX()) {
				//check if there is enough space for 1 tick
				if (destination.getX() - currentPos.getX() < velocity) {
					// don't overshoot destination
					
					currentPos.translate(destination.x- currentPos.x, 0);
				} else {
					currentPos.translate(velocity, 0);
				}
			}
			if (currentPos.getY() != destination.getY()) {
				if (destination.getY() - currentPos.getY() < velocity) {
					// don't overshoot destination
					currentPos.translate(0, destination.y - currentPos.y);
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
					currentPos.translate(0, destination.y - currentPos.y);
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
					currentPos.translate(destination.x - currentPos.x, 0);
				} else {
					currentPos.translate(velocity, 0);
				}
			}

			// Handling y
			if (currentPos.getY() != destination.getY()) {

				if (currentPos.getY() - destination.getY() < velocity) {
					currentPos.translate(currentPos.y- destination.y, 0);
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
						setCurrentPos(destination.getX(), getPosition().getY());
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
					currentPos.translate(currentPos.y - destination.y, 0);
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

		if(currentPos.x == destination.x){
			if(currentPos.y > destination.y){
				direction = Direction.NORTH;
			}else{
				direction = Direction.SOUTH;
			}
		}
		else if(currentPos.y == destination.y){
			if(currentPos.x > destination.x){
				direction = Direction.WEST;
			}else{
				direction = Direction.EAST;
			}
		}
		
		
		
		else if (currentPos.getY() < destination.getY()) {
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
