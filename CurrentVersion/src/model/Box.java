package model;

import java.awt.Point;

import view.View;

public class Box {
	private Point position;
	private int capacity = 2;
	private int count;
	private HoldingType h = HoldingType.BOX;
	private HoldingType contains = HoldingType.EMPTY;
	private boolean isfull = false;
	public static final int boxDimensions = 100;// box dimensions (boxes are
												// square, so don't need
												// individual width/height)
	public static final int boxSpawnSpacing = 50; // minimum distance spawned
													// objects should be from
													// boxes upon creation
	public static final int boxToBoxInterval = 150;// distance between created
													// boxes
	public static final int boxToViewEdgeSpacing = 40;// distance boxes are from
														// the right edge of the
														// screen
	public static final int boxX = (2 * View.viewWidth) / 3 - Box.boxDimensions - Box.boxToViewEdgeSpacing;

	public Box(Point p) {
		this.position = p;
	}

	public Box(Point p, int capacity) {
		this.position = p;
		this.capacity = capacity;
	}

	public Box() {
		// TODO Auto-generated constructor stub
	}

	// Setters and Getters
	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void incrementCount(){
		this.count++;
		if(count == capacity){
			isfull = true;
		}
	}

	public HoldingType getH() {
		return h;
	}

	public void setH(HoldingType h) {
		this.h = h;
	}

	public boolean isfull() {
		return isfull;
	}

	public void setIsfull(boolean isfull) {
		this.isfull = isfull;
	}

	public HoldingType getContains() {
		return contains;
	}

	public void setContains(HoldingType contains) {
		this.contains = contains;
	}

}
