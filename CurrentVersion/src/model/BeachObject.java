package model;

import java.awt.Point;

import javax.swing.Icon;

import view.View;
import model.Box;

public class BeachObject extends GameObject {
	private HoldingType h;
	
	public static final int beachObjDimensions = 30;
	
	public static int spawnZoneHeight = View.viewHeight - beachObjDimensions;
	public static int spawnZoneWidth = (2*View.viewWidth)/3 - Box.boxToViewEdgeSpacing - Box.boxDimensions - Box.boxSpawnSpacing;

	
/*
 * Constructor
 */
	/**
	 * @param position set position to this
	 * @param h set object's type to this
	 * @param k set object's icon to this
	 */
	public BeachObject(Point position, HoldingType h, Icon k){
		this.setH(h);
		this.setPosition(position);
		this.setDestination(null);
		setObjIcon(k);
		setMyType(h);
	}

/*
 * Setters & Getters
 */
	/**
	 * @param h set h to this
	 */
	public void setH(HoldingType h) {
		this.h = h;
	}	
}
