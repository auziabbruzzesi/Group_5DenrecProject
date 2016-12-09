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

	private static final long serialVersionUID = 571534261898920559L;
	
	
	private Point position;
	private transient Icon myIcon;
	private HoldingType myType;
	private int width;
	private int height;
	private Point destination;
	private Point shoreTop;
	private int totalDecrement = 0;
	private double insideHeight = height;
	private double startingY = 0;

	//dummy constructor to compule
    public GameObject(){
    	
    }
	private int index;

	/**
	 * @param dec decrement totalDecrement by this
	 */
	public void updateTotalDecrement(int dec){
		this.totalDecrement -= dec;
	}

/*
 * Setters & Getters
 */
	
	/**
	 * @param p set position to this point
	 */
	public void setPosition(Point p){
		this.position = p;
	}
	/**
	 * @param x set x coordinate of position to this
	 * @param y set y coordinate of position to this
	 */
	public void setPosition(int x, int y){
		position.x = x;
		position.y = y;
	}
	/**
	 * @param d set destination to this
	 */
	public void setDestination(Point d){
		this.destination = d;
	}
	/**
	 * @param k set icon to this
	 */
	public void setObjIcon(Icon k){
		myIcon = k;
	}
	
	/**
	 * @return position
	 */
	public Point getPosition(){
		return position;
	}

	/**
	 * @return myIcon
	 */
	public Icon getObjIcon(){
		return myIcon;
	}

	/**
	 * @return destination
	 */
	public Point getDestination() {
		return destination;
	}
	/**
	 * @return index
	 */
	public int getIndex() {
		return index;
	}
	/**
	 * @param index set index to this
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	/**
	 * @return shoreTop
	 */
	public Point getShoreTop() {
		return shoreTop;
	}
	/**
	 * @param shoreTop set shoreTop to this
	 */
	public void setShoreTop(Point shoreTop) {
		this.shoreTop = shoreTop;
	}
	
	/**
	 * @return totalDecrement
	 */
	public int getTotalDecrement() {
		return totalDecrement;
	}
	
	/**
	 * @param totalDecrement set totalDecrement to this
	 */
	public void setTotalDecrement(int totalDecrement) {
		this.totalDecrement = totalDecrement;
	}
	
	/**
	 * @return insideHeight
	 */
	public double getInsideHeight(){
		return this.insideHeight;
	}
	
	/**
	 * @param insideHeight set insideheight to this
	 */
	public void setInsideHeight(double insideHeight){
		this.insideHeight = insideHeight;
	}
	/**
	 * @return startingY
	 */
	public double getStartingY() {
		return startingY;
	}
	/**
	 * @param y set startingY to this
	 */
	public void setStartingY(double y) {
		startingY = y;
	}
	/**
	 * @param height set height to this
	 */
	public void setHeight(int height){
		this.height = height;
	}
	/**
	 * @return height
	 */
	public int getHeight(){
		return this.height;
	}
	/**
	 * @param width set width to this
	 */
	public void setWidth(int width){
		this.width = width;
	}
	/**
	 * @return width
	 */
	public int getWidth(){
		return this.width;
	}
	
	//overrided by Scenery.java - dummy placed here to remove casting from View
	public BufferedImage[] getScenery(){
		return null;
	}
	/**
	 * @return myType
	 */
	public HoldingType getMyType() {
		return myType;
	}
	/**
	 * @param myType set myType to this
	 */
	public void setMyType(HoldingType myType) {
		this.myType = myType;
	}
}