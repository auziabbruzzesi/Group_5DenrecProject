package model;

import java.awt.Point;

public class Wave extends Character {
	
		public static final int waveWidth = 50;//wave width 
		public static final int waveHeight = 50; // wave height 
		public static final int waveSpawnSpacing = 50; //minimum distance spawned objects should be from waves upon creation 
		public static final int waveToWaveInterval = 100;//distance between created waves
		public static final int waveToViewEdgeSpacing = 20;//distance boxes are from the right edge of the screen
	
	
	public Wave(Point p){
		this.setCurrentPos(p);
	}
	public Wave(Point p, int speed){
		this.setCurrentPos(p);
		this.setVelocity(speed);
	}
	
		public void move() {
		// TODO Auto-generated method stub
	
	}
		

}
