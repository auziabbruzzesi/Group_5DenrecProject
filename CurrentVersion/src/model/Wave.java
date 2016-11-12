package model;

import java.awt.Point;
import java.util.Random;

import javax.swing.text.View;

import controller.Controller.button;

public class Wave extends Character {

	public static final int waveWidth = 50;
	public static final int waveHeight = 50; 
	public static final int waveSpawnSpacing = 150; 
	public static final int waveToWaveInterval = 100;
	public static final int waveToViewEdgeSpacing = 20;
	private int shorelineX = 360;
	private Point initialPos;
	private Random randomGenerator = new Random();
	
	
	public Wave(Point p) {

		this.setCurrentPos(p);
		this.setInitialPos(p);
		int v = randomGenerator.nextInt(4) + 1;
		
		//generate a random number within a specified range (1-4 for now) and set velocity to that number
		this.setVelocity(v);

		Point d = new Point(this.getCurrentPos().x - shorelineX, this.getCurrentPos().y);

		this.setDestination(d);
	}
	
	public void resetVelocity(){
		int v = randomGenerator.nextInt(4) + 1;
		this.setVelocity(v);
	}

	@Override
	// note that direction is always west
	public void move() {
		if(getCurrentPos().x - getVelocity() < getDestination().x){
			this.setCurrentPos(getDestination().getX(), getCurrentPos().getY());
		}
		else{
			this.setCurrentPos(getCurrentPos().getX() - getVelocity(), getCurrentPos().getY());
		}
	}

	public Point getInitialPos() {
		return initialPos;
	}

	public void setInitialPos(Point p) {
		this.initialPos = p;
	}


	
}
