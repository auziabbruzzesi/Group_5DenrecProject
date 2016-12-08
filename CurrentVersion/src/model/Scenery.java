package model;

import java.awt.image.BufferedImage;

public class Scenery extends GameObject{

	transient BufferedImage[] scenery= new BufferedImage[2];
	
	//dummy constructor to compile
	public Scenery(){
		
	}
	
	
	/**
	 * @return scenery image array
	 */
	public BufferedImage[] getScenery(){
		return scenery;
	}
	/**
	 * 
	 * @param s - an array of scenery images
	 */
	public void setScenery(BufferedImage[] s) {
		this.scenery = s;
	}
}
