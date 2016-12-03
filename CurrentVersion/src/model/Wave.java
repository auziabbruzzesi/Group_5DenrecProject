package model;

import java.awt.Point;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.text.View;



public class Wave extends GameObject {

	public static final int waveWidth = 50;// wave width
	public static final int waveHeight = 50; // wave height
	public static final int waveSpawnSpacing = 150; // minimum distance spawned objects should be from waves upon creation
	public static final int waveToWaveInterval = 100;// distance between created waves
	public static final int waveToViewEdgeSpacing = 20;// distance waves are from the right edge of the screen 
	private int velocity;
	private int shorelineX = 360;//TODO:
	private Point initialPos;
	private Random randomGenerator = new Random();
	
	
/*
 * Constructor
 */
	public Wave(Point p, Icon k) {
		this.setPosition(p);
		this.setInitialPos(p);
		
		//generate a random number within a specified range (1-4 for now) and set velocity to that number
		int v = randomGenerator.nextInt(2) + 1;
		this.setVelocity(v);

		Point d = new Point(this.getPosition().x - shorelineX, this.getPosition().y);
		this.setDestination(d);
		
		setObjIcon(k);
	}

	
/*
 * General Functions
 */
	
	// note that direction is always west
	public void move() {
			this.setPosition(getPosition().x - getVelocity(), getPosition().y);
	}

	public Point getInitialPos() {
		return initialPos;
	}

	public void setInitialPos(Point p) {
		this.initialPos = p;
	}

	public void reset(Point startPos, Point newDest){
		setPosition(startPos);
		setDestination(newDest);
		resetVelocity();
	}
	
	public void resetVelocity(){
		int v = randomGenerator.nextInt(4) + 1;
		this.setVelocity(v);
	}
	
	//sg
	public int getVelocity() {
		return velocity;
	}	
	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}
}
