package model;

import java.awt.image.BufferedImage;

public class Scenery extends GameObject{
	transient BufferedImage[] scenery= new BufferedImage[2];
	
	public Scenery(){
		
	}
	
	public BufferedImage[] getScenery(){
		return scenery;
	}

	public void setScenery(BufferedImage[] s) {
		this.scenery = s;
	}
}
