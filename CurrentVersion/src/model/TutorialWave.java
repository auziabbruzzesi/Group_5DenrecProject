package model;


import java.awt.Point;

import javax.swing.Icon;

public class TutorialWave extends Wave {
	int animation;
	
	public TutorialWave(Point p, Icon k, int shorelineDestX, int a) {
		super(p, k, shorelineDestX);
		animation = a;
	}
	
	public int getAnimationNumber(){
		return this.animation;
	}
}