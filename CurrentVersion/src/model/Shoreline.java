package model;

import java.awt.Point;

public class Shoreline {

	private int oysterDecInterval;
	private int concreteDecInterval;
	private EstuaryHP HP;
	private final int decrementInterval = 2;
	private boolean isPassed = false; // has the line passed the threshold?

	public int getDecrementInterval() {
		return decrementInterval;
	}

	public int getOysterDecInterval() {
		return oysterDecInterval;
	}

	public void setOysterDecInterval(int oysterDecInterval) {
		this.oysterDecInterval = oysterDecInterval;
	}

	public int getConcreteDecInterval() {
		return concreteDecInterval;
	}

	public void setConcreteDecInterval(int concreteDecInterval) {
		this.concreteDecInterval = concreteDecInterval;
	}

	public EstuaryHP getHP() {
		return HP;
	}

	public void setHP(EstuaryHP hP) {
		HP = hP;
	}

	// no need for constructor
	/**
	 * Erode: moves the shoreline west(toward boxes) - once the shoreline passes
	 * the gabians and/or concrete walls game over. HP decrement interval
	 * depends on the box a wave hits
	 * 
	 */
	public void erode() {

		Model model = new Model();

		Box barrier = new Box();
		int currentHP = HP.getHP();

		for (Wave waves : model.getWaves().values()) {
			while (!isPassed) {

				if (waves.getCurrentPos() == barrier.getPosition()) {

					barrier.getH();
					if (barrier.getH() == HoldingType.CONCRETE) {

						currentHP = currentHP - concreteDecInterval;
						int currentCount = barrier.getCapacity();
						currentCount = barrier.getCount() - 1;

					} else if (barrier.getH() == HoldingType.OYSTER) {
						// oyster. health decrements by 5 since gabians are
						// stronger

						currentHP = currentHP - oysterDecInterval;
					}

					if (barrier.getH() == HoldingType.CONCRETE && barrier.isfull()) {
						currentHP = currentHP - (concreteDecInterval - 5); // now
																			// decrementing
																			// by
																			// 5
																			// because
																			// the
																			// wall
																			// was
																			// made

					} else if (barrier.getH() == HoldingType.OYSTER && barrier.isfull()) {
						currentHP = currentHP - (oysterDecInterval - 3);
					}
				}
				currentHP--;

			}
			if (currentHP == 0) {
				// gameOver();
			}

		}
	}

}
