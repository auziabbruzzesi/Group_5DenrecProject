package model;

import java.awt.Point;
import java.util.Random;

import javax.swing.text.View;

import controller.Controller.button;

public class Wave extends Character {

	public static final int waveWidth = 50;// wave width
	public static final int waveHeight = 50; // wave height
	public static final int waveSpawnSpacing = 150; // minimum distance spawned
													// objects should be from
													// waves upon creation
	public static final int waveToWaveInterval = 100;// distance between created
														// waves
	public static final int waveToViewEdgeSpacing = 20;// distance boxes are
														// from the right edge
														// of the screen
	
	private int shorelineX = 360;//TODO:
	private Point initialPos;

	private Random randomGenerator = new Random();
	
	public Wave(Point p) {
		this.setCurrentPos(p);
		this.setInitialPos(p);
		
		int v = randomGenerator.nextInt(1) + 1;
		//generate a random number within a specified range (1-4 for now) and set velocity to that number
		this.setVelocity(v);

		Point d = new Point(this.getCurrentPos().x - shorelineX, this.getCurrentPos().y);

		this.setDestination(d);
	}

	@Override
	// note that direction is always west
	public void move() {
			this.setCurrentPos(getCurrentPos().getX() - getVelocity(), getCurrentPos().getY());
	}

	public Point getInitialPos() {
		return initialPos;
	}

	public void setInitialPos(Point p) {
		this.initialPos = p;
	}

	public void resetVelocity(){
		int v = randomGenerator.nextInt(4) + 1;
		this.setVelocity(v);
	}
	
}
