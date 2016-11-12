package model;

import java.awt.Point;
import view.View;
import model.Box;

public class BeachObject extends Character {
	private HoldingType h;
	public static final int beachObjDimensions = 30;
	public static final int spawnZoneHeight = View.viewHeight - beachObjDimensions;
	public static final int spawnZoneWidth = (2*View.viewWidth)/3 - Box.boxToViewEdgeSpacing - Box.boxDimensions - Box.boxSpawnSpacing;

	public BeachObject(Point position, HoldingType h){
		this.setH(h);
		this.setCurrentPos(position);
	}
	
	public HoldingType getH() {
		return h;
	}
	
	public void setH(HoldingType h) {
		this.h = h;
	}
	



	
	
	
}
