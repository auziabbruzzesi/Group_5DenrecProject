package model;

import java.awt.Point;
import java.util.Random;

import javax.swing.Icon;

/**
 * @Class Wave wave objects spawn continuously throughout the game, move toward the beach (shoreline), and, upon
 * collision, erode the estuary. 
 * @author Eaviles
 */

public class Wave extends GameObject {

	public static final int waveWidth = 50;// wave width
	public static final int waveHeight = 50; // wave height
	public static final int waveSpawnSpacing = 150; // minimum distance spawned objects should be from waves upon creation
	public static final int waveToWaveInterval = 100;// distance between created waves
	public static final int waveToViewEdgeSpacing = 20;// distance waves are from the right edge of the screen 
	public static final int waveToTopSpacing = 100;
	
	private int velocity;

	private final Point initialPos;
	private Random randomGenerator = new Random();	
	
/*
 * Constructor
 */

	/**
	 * @author Eaviles
	 * @param p - wave position/initial position
	 * @param k - wave icon
	 * @param shorelineDest - wave's current shoreline destination, since it is constantly being updated 
	 * Purpose: creates a wave at location p with icon k. 
	 * Initializes wave values position, initalposition, velocity, destination, objectIcon 
	 */
	public Wave(Point p, Icon k, int shorelineDestX) {

		this.setPosition(p);
		initialPos = new Point(p);
		
		//generate a random number within a specified range (1-4 for now) and set velocity to that number
		int v = randomGenerator.nextInt(2) + 1;
		this.setVelocity(v);

		Point d = new Point((shorelineDestX), this.getPosition().y);
		this.setDestination(d);
		
		setObjIcon(k);
	}

	
/*
 * General Functions
 */
	
	/**
	 * @author Eaviles
	 * @Purpose move a wave west by one tick of velocity
	 */
	public void move() {
			this.setPosition(getPosition().x - getVelocity(), getPosition().y);
	}

	/**
	 * @return initialPos
	 */
	public Point getInitialPos() {
		return initialPos;
	}



	/**
	 * @author Eaviles
	 * Purpose: Reset wave position after it hits the shoreline. Position is reset to wave's original
	 * starting position. Velocity is reset (see resetVelocity function for details). Shoreline recedes
	 * after wave collision, so wave's destination changes slightly, as well. 
	 */
	public void reset(Point start, Point newDest){
//		System.out.println("resetting wave. stored startpos = "+this.getInitialPos());
		setPosition(start);
		setDestination(newDest);
		resetVelocity();
	}
	
	/**
	 * @author Eaviles
	 * Purpose: to reset a wave's velocity within the specified range.
	 */
	public void resetVelocity(){
		int v = randomGenerator.nextInt(1) + 1;
		this.setVelocity(v);
	}
	
	/**
	 * @return velocity
	 */
	public int getVelocity() {
		return velocity;
	}	
	
	/**
	 * @param velocity set velocity to this
	 */
	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}
}
