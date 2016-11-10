package model;

import java.awt.Point;

import view.View;

public class Human extends Character implements MoveObjects {
	//private int health = 100;
	//public static final Point startPosition; // = new Point(100,100);
	public static final int humanDimensions = 40;
	public static final int humanBoundsOnScreen = 	(2 * View.viewWidth) / 3;
	private boolean isFriendly = true;
	
	
	// TODO: make it so that some humans spawned are already holding trash that they plan to drop ..  in model?
	// (they have the same pick up and put down functionality as player.)
	public Human(Point p){
		this.setCurrentPos(p);
	}

	public boolean isFriendly() {
		return isFriendly;
	}

	public void setFriendly(boolean isFriendly) {
		this.isFriendly = isFriendly;
	}
	
	// TODO: make it so that human can move vertically up and down on the shore 

	public void move(){
		
	
//		switch (direction){
//		case NORTH:
//		
//		
//		
//		
//		}
//		
		
	}
	
	

}
