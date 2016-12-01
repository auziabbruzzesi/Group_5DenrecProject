package model;

import java.awt.Point;

import javax.swing.Icon;

public class Player extends Character {

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
	
	//General function
	public void updateSprite(){
		this.getDirection();
//		crabPics[m.getP().findIndex()]);
	}
	
	//Setters and Getters
	
	
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}

	
	
	
}
