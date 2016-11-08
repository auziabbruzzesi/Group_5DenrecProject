package model;

import java.awt.Point;
import view.View;
import model.Box;

public class BeachObject extends Character {
	private HoldingType h;
	public static final int beachObjDimensions = 30;
	public static final int spawnZoneHeight = View.viewHeight - beachObjDimensions;
	
	/*
	 * NOTE: we don't need to worry about objs spawning too close below/above boxes. This takes care of everything.
	 * 		It will need to be changed, however, if we reposition the boxes in later implementations. 
	 * 		Should be pretty minor to do this. (FLW)
	 */
	
	public static final int spawnZoneWidth = View.viewWidth - Box.boxToViewEdgeSpacing - Box.boxDimensions - Box.boxSpawnSpacing;

	public HoldingType getH() {
		return h;
	}
	
	public void setH(HoldingType h) {
		this.h = h;
	}

	//@Override
	//public void move(Point destination) {
		// TODO Auto-generated method stub
		
	//}
	
	public BeachObject(Point position, HoldingType h){
		this.setH(h);
		this.setCurrentPos(position);
	}


	
	
	
}
