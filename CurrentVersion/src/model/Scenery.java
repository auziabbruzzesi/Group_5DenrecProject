package model;

import java.awt.image.BufferedImage;

public class Scenery extends GameObject{

	transient BufferedImage[] scenery= new BufferedImage[2];
	
	//dummy constructor to compile
	public Scenery(){
		
	}
	
	@Override
	public BufferedImage[] getScenery(){
		return scenery;
	}

	public void setScenery(BufferedImage[] s) {
		this.scenery = s;
	}
}
