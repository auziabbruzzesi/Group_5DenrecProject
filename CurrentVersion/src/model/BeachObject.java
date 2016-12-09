package model;

import java.awt.Point;

import javax.swing.Icon;

import view.View;
import model.Box;

/**
 * @Class BeachObject - representation of oysters and concrete
 *
 */
public class BeachObject extends GameObject {
	private HoldingType h;
	
	public static final int beachObjDimensions = 30;
	
	public static int spawnZoneHeight = View.viewHeight - beachObjDimensions;
	public static int spawnZoneWidth = (2*View.viewWidth)/3 - Box.boxToViewEdgeSpacing - Box.boxDimensions - Box.boxSpawnSpacing;

	
/*
 * Constructor
 */
	/**
	 * @Constructor
	 * @param position set position to this
	 * @param h set type to this
	 * @param k set icon to this
	 */
	public BeachObject(Point position, HoldingType h, Icon k){
		this.setPosition(position);
		this.setDestination(null);
		setObjIcon(k);
		setMyType(h);
	}	
}
