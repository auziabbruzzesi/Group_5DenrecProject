package model;

import java.awt.Point;

import javax.swing.Icon;

public abstract class GameObject {

	private Point position;
	private Icon myIcon;
	private HoldingType hT;
	private HoldingType myType;
	private int width;
	private int height;
	private Point destination;


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
	
}