package model;

import java.awt.Point;

public class Shoreline extends GameObject{
	private Point shoreTop;
	private Point shoreBottom;
	private int shoreLine;
	private double slope;

	private int loosingCoordinate;
	private int totalDecrement = 0;

	public Shoreline(Point t, Point b){
		setShoreTop(t);
		setShoreBottom(b);
		this.slope = (  this.getShoreTop().y - this.shoreBottom.y)/(this.getShoreTop().x - this.shoreBottom.x);
		this.loosingCoordinate = getShoreBottom().x - 50;
	}
	
	public int findCorrespondingX(int y){
		double x = (((y - this.getShoreTop().y)/(this.slope)) + this.getShoreTop().x);
		int x1 = (int) (x);
		return x1;			
	}
	public void updateTotalDecrement(int dec){
		this.totalDecrement -= dec;
	}
	public Point getShoreBottom() {
		return shoreBottom;
	}
	public void setShoreBottom(Point shoreBottom) {
		this.shoreBottom = shoreBottom;
	}
	public int getShoreLine() {
		return shoreLine;
	}
	public void setShoreLine(int shoreLine) {
		this.shoreLine = shoreLine;
	}
	public Point getShoreTop() {
		return shoreTop;
	}
	public void setShoreTop(Point shoreTop) {
		this.shoreTop = shoreTop;
	}

	public int getLoosingCoordinate() {
		return loosingCoordinate;
	}

	public void setLoosingCoordinate(int loosingCoordinate) {
		this.loosingCoordinate = loosingCoordinate;
	}

	public double getSlope() {
		return slope;
	}

	public void setSlope(double slope) {
		this.slope = slope;
	}
	public int getTotalDecrement() {
		return totalDecrement;
	}	
	public void setTotalDecrement(int totalDecrement) {
		this.totalDecrement = totalDecrement;

	}
}
