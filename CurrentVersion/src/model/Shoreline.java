package model;

import java.awt.Point;

public class Shoreline extends GameObject{
	private Point shoreTop;
	private Point shoreBottom;
	private int shoreLine;
	private double slope;

	private int loosingCoordinate;
	private int totalDecrement = 0;

	/**
	 * @Constructor
	 * @author Eaviles/Auzi
	 * @param t set shoreTop to this
	 * @param b set shoreBottom to this
	 * @Purpose initialize shoreline
	 */
	public Shoreline(Point t, Point b){
		setShoreTop(t);
		setShoreBottom(b);
		this.slope = (  this.getShoreTop().y - this.shoreBottom.y)/(this.getShoreTop().x - this.shoreBottom.x);
		this.loosingCoordinate = getShoreBottom().x - 30;
	}
	
	/**
	 * @author Eaviles/Auzi
	 * @param y - find x to match this
	 * @return x1 - the x coordinate that matches (param)y
	 * @Purpose given some y, find the point on the shoreline at which the line y = (param)y intersects. 
	 * This is used to set waves' destinations. 
	 */
	public int findCorrespondingX(int y){
		double x = (((y - this.getShoreTop().y)/(this.slope)) + this.getShoreTop().x);
		int x1 = (int) (x);
		return x1;			
	}
	/**
	 * @param dec add this value to totalDecrement
	 */
	public void updateTotalDecrement(int dec){
		this.totalDecrement -= dec;
	}
	/**
	 * @return shoreBottom coordinate
	 */
	public Point getShoreBottom() {
		return shoreBottom;
	}
	/**
	 * @param shoreBottom set shoreBottom to this
	 */
	public void setShoreBottom(Point shoreBottom) {
		this.shoreBottom = shoreBottom;
	}

	/**
	 * @return shoreTop coordinate
	 */
	public Point getShoreTop() {
		return shoreTop;
	}
	/**
	 * @param shoreTop set shoreTop coordinate to this
	 */
	public void setShoreTop(Point shoreTop) {
		this.shoreTop = shoreTop;
	}

	/**
	 * @return losingCoordinate
	 */
	public int getLoosingCoordinate() {
		return loosingCoordinate;
	}

	/**
	 * @param loosingCoordinate set losingcoordinate to this
	 */
	public void setLoosingCoordinate(int loosingCoordinate) {
		this.loosingCoordinate = loosingCoordinate;
	}

	/**
	 * @return slope of shoreline (shoreline is a slanted line)
	 */
	public double getSlope() {
		return slope;
	}

	/**
	 * @param slope set slope of shoreline to this (shoreline is a slanted line)
	 */
	public void setSlope(double slope) {
		this.slope = slope;
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
}
