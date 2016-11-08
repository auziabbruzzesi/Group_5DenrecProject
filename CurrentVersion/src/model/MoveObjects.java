package model;

public interface MoveObjects {
	
	public boolean pickUp(HoldingType beachObjectType);
	public boolean putDown(HoldingType boxObjectType, Box toPut);
	public void move();

}
