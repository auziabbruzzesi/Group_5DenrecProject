package model;

import java.awt.Point;

public class Shoreline extends GameObject{
	
	private Point shoreBottom;
	private int shoreLine;
	private double slope;

	private int loosingCoordinate;
	private int totalDecrement = 0;


	
	

	//current
//	public Shoreline(int x){
//		setShoreLine(x);
//		setShoreBottom(new Point(x,x));
//		setShoreTop(new Point(x,x));
//	}
	//will switch to this
	public Shoreline(Point t, Point b){
		setShoreTop(t);
		setShoreBottom(b);
		this.slope = (  this.getShoreTop().y - this.shoreBottom.y)/(this.getShoreTop().x - this.shoreBottom.x);
		
	}
	
	public int findCorrespondingX(int y){
		double x = (((y - this.getShoreTop().y)/(this.slope)) + this.getShoreTop().x);
		int x1 = (int) (x);
		return x1;			
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
}
