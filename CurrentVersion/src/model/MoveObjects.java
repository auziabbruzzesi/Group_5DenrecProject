package model;
/**
 * 
 * @author Auzi
 * an interface for anything that moves and can pick up
 */
public interface MoveObjects {
	
	public boolean pickUp(HoldingType beachObjectType);

	public void move();

}
