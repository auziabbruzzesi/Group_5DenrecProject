package model;

import java.awt.Point;

public class Shoreline extends GameObject{
	
	private Point shoreBottom;
	private int shoreLine;
	private double slope;

	
	//current
	public Shoreline(int x){
		setShoreLine(x);
		setShoreBottom(new Point(x,x));
		setShoreTop(new Point(x,x));
	}
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


	
	
}
