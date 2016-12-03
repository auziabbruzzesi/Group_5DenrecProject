package model;

import java.awt.Point;

import javax.swing.Icon;

import view.View;
import model.Box;

public class BeachObject extends GameObject {
	private HoldingType h;
	public static final int beachObjDimensions = 30;
	public static final int spawnZoneHeight = View.viewHeight - beachObjDimensions;
	public static final int spawnZoneWidth = (2*View.viewWidth)/3 - Box.boxToViewEdgeSpacing - Box.boxDimensions - Box.boxSpawnSpacing;

	
/*
 * Constructor
 */
	public BeachObject(Point position, HoldingType h, Icon k){
		this.setH(h);
		this.setPosition(position);
		this.setDestination(null);
		setObjIcon(k);
	}

/*
 * Setters & Getters
 */
	public HoldingType getH() {
		return h;
	}
	
	public void setH(HoldingType h) {
		this.h = h;
	}

	
	
	
}
