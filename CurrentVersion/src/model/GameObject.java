package model;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import javax.swing.Icon;


public abstract class GameObject implements Serializable {
/**
 * @author Eaviles
 * Class GameObject: parent class for all game objects. Used by Model to implement an array visible to View.
 * 
 * Classes that extend GameObject:
 * Wave, Box, BeachObject, Player, HealthBar, Shoreline, Scenery.
 *
 */



	/**
	 * 
	 */
	private static final long serialVersionUID = 571534261898920559L;
	
	
	private Point position;
	private transient Icon myIcon;
	private HoldingType hT;
	//private HoldingType myType;
	private int width;
	private int height;
	private Point destination;
	private Point shoreTop;
	private int totalDecrement = 0;
	private double insideHeight = height;
	private double startingY = 0;
//	private int width = 100;
	BufferedImage[] scenery;
	
    public GameObject(){
    	
    }
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
	public Point getShoreTop() {
		return shoreTop;
	}
	public void setShoreTop(Point shoreTop) {
		this.shoreTop = shoreTop;
	}
	public void updateTotalDecrement(int dec){
		this.totalDecrement -= dec;
	}
	public int getTotalDecrement() {
		return totalDecrement;
	}
	public void setTotalDecrement(int totalDecrement) {
		this.totalDecrement = totalDecrement;
	}
	public double getInsideHeight(){
		return this.insideHeight;
	}
	public void setInsideHeight(double insideHeight){
		this.insideHeight = insideHeight;
	}
	public double getStartingY() {
		return startingY;
	}
	public void setStartingY(double y) {
		startingY = y;
	}
	public void setHeight(int height){
		this.height = height;
	}
	public int getHeight(){
		return this.height;
	}
	public void setWidth(int width){
		this.width = width;
	}
	public int getWidth(){
		return this.width;
	}
	public BufferedImage[] getScenery(){
		return null;
	}
	
}