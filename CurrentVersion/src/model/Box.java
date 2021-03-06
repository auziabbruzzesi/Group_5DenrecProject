package model;

import java.awt.Point;

import javax.swing.Icon;

import view.View;


/**
 * @Class Box representation of boxes in the game. Can be filled/depleted according to player actions and game events.
 * 		  Contents of a box used in determining how much a wave will damage the shore.
 */
public class Box extends GameObject {
	private Point position;
	private int capacity = 3;
	private int count;
	
	private HoldingType contains = HoldingType.EMPTY;
	private boolean isfull = false;
	private Icon objIcon;
	public static int boxDimensions = 100;// box dimensions (boxes are
	//private DimenBoxDimension// square, so don't need
												// individual width/height)
	public static final int boxSpawnSpacing = 50; // minimum distance spawned
													// objects should be from
													// boxes upon creation
	public static final int boxToBoxInterval = 145
			;// distance between created
													// boxes
	public static final int boxToViewEdgeSpacing = 40;// distance boxes are from
														// the right edge of the
														// screen
	public static final int boxToTopSpacing = 20;
	public static final int boxX = (2 * View.viewWidth) / 3 - Box.boxDimensions - Box.boxToViewEdgeSpacing - 250;

	
/*
 * Constructors
 */
	public Box(Point p, Icon k) {
		this.position = p;
		setObjIcon(k);
		setMyType(HoldingType.BOX);
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
		if(count == 0){
			this.contains = HoldingType.EMPTY;
		}
	}

	public void incrementCount(){
		this.count++;
		if(count == capacity){
			isfull = true;
		}
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

	public Icon getObjIcon() {
		return objIcon;
	}

	public void setObjIcon(Icon objIcon) {
		this.objIcon = objIcon;
	}

}
