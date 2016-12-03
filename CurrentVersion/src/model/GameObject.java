package model;

import java.awt.Point;

import javax.swing.Icon;
/**
 * @author Eaviles
 * Class GameObject: parent class for all game objects. Used by Model to implement an array visible to View.
 * 
 * Classes that extend GameObject:
 * Wave, Box, BeachObject, Player, HealthBar, Shoreline, Scenery.
 *
 */
public abstract class GameObject {

	private Point position;
	private Icon myIcon;
	private HoldingType hT;
	private HoldingType myType;
	private int width;
	private int height;
	private Point destination;
	private int index;

	public Icon setObjIcon(){
		return myIcon;
	}
	public void setPosition(Point p){
		this.position = p;
	}
	public void setPosition(int x, int y){
		position.x = x;
		position.y = y;
	}
	public void setHT(HoldingType h){
		this.hT = h;
	}
	public void setDestination(Point d){
		this.destination = d;
	}
	public void setObjIcon(Icon k){
		myIcon = k;
	}
	
	public Point getPosition(){
		return position;
	}
	public HoldingType getHT(){
		return hT;
	}
	public Icon getObjIcon(){
		return myIcon;
	}

	public Point getDestination() {
		return destination;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
}