package model;

import java.awt.Point;

public class Player extends Character {
	private HoldingType h = HoldingType.EMPTY;
	private int health = 100;
	public static final Point startPosition = new Point(100,100);
	public static final int playerDimensions = 40;
	
		
	//Constructors (2)
	public Player(Point p){
		this.setCurrentPos(p);
	}
	public Player(Point p, int health){
		this.setCurrentPos(p);
		this.setHealth(health);
	}
	
	//Setters and Getters
	public HoldingType getH() {
		return h;
	}
	public void setH(HoldingType h) {
		this.h = h;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}

	@Override
	public void putDown(HoldingType boxObjectType) {
		// TODO Auto-generated method stub
		
	}
	
	
}
