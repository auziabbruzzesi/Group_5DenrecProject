package model;

import java.awt.Point;

public class Shoreline extends GameObject{
	private Point shoreTop;
	private Point shoreBottom;
	private int shoreLine;
	private double slope;
	private int totalDecrement = 0;
	
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
		this.slope = (this.shoreTop.y - this.shoreBottom.y)/(this.shoreTop.x - this.shoreBottom.x);
		
	}
	
	public int findCorrespondingX(int y){
		double x = (((y - this.shoreTop.y)/(this.slope)) + this.shoreTop.x);
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
	public int getTotalDecrement() {
		return totalDecrement;
	}
	public void setTotalDecrement(int totalDecrement) {
		this.totalDecrement = totalDecrement;
	}
	
	
}
