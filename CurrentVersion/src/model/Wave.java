package model;

import java.awt.Point;

public class Wave extends Character {
	
	
	public Wave(Point p){
		this.setCurrentPos(p);
	}
	public Wave(Point p, int speed){
		this.setCurrentPos(p);
		this.setVelocity(speed);
	}
	@Override
	public void move(Point destination) {
		// TODO Auto-generated method stub
		
	}

}
