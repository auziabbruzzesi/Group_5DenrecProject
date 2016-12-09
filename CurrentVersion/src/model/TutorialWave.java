package model;


import java.awt.Point;

import javax.swing.Icon;
/**
 * @Class TutorialWave Model representation of waves used in tutorial animations
 * @author Estella
 *
 */
public class TutorialWave extends Wave {
	int animation;
	
	/**
	 * @Constructor
	 * @author Eaviles
	 * @param p set position to this (super)
	 * @param k set icon to this (super)
	 * @param shorelineDestX set destinationX to this (super)
	 * @param a set animation number to this
	 */
	public TutorialWave(Point p, Icon k, int shorelineDestX, int a) {
		super(p, k, shorelineDestX);
		animation = a;
	}
	
	/**
	 * @return animation number
	 */
	public int getAnimationNumber(){
		return this.animation;
	}
}