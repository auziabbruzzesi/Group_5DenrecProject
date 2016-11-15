/*
 * Quick Notes:
 * 	1. Might be able to consolidate comparison if/else statements of checkOverlap functions into their own callable function. That's not
 * 		tonight's problem, but it's something to think about for later.
 */
package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import model.BeachObject;
import view.View;
import controller.Controller.button;

public class Model {

	// TODO: Tidy up these attribute declarations!
	private int numBoxes = 4;
	private int score = 0;
	private Player p = new Player(new Point(Player.startPosition));
	private HashMap<Point, BeachObject> beachObjHM = new HashMap<Point, BeachObject>();
	private HashMap<Point, Box> boxes = new HashMap<Point, Box>();
	private ArrayList<Wave> waves = new ArrayList<Wave>();
	private int numWaves = 6;
	private HealthBar HB = new HealthBar(50, 200);
	private int shoreLine = 840;
	private int minShoreLine = 750; // TODO: have C init m's & v's minshores to
									// ensure they're in-sync

	public Model() {
		Random r = new Random();
		Boolean canPlace;

		// BOXES ARE CREATED HERE
		for (int i = 0; i < numBoxes; i++) {
			Point p = new Point(Box.boxX, i * Box.boxToBoxInterval + 20);// Auzi
																			// did
																			// this.
																			// this
																			// is
			// bad. please fix it when
			// the time comes.
			this.boxes.put(p, new Box(p));
		}

		// WAVES ARE CREATED HERE
		for (int i = 0; i < numWaves; i++) {

			Point p = new Point(View.viewWidth - Wave.waveWidth, i * Wave.waveSpawnSpacing);
			this.waves.add(new Wave(p));
		}

		// BEACH OBJECTS ARE CREATED HERE
		for (int i = 0; i < 20; i++) {
			do {
				// create a point and check that it won't overlap
				Point p = new Point(r.nextInt(BeachObject.spawnZoneWidth), r.nextInt(BeachObject.spawnZoneHeight));

				if (checkBeachObjectOverlap(p) && checkBoxOverlap(p) && checkPlayerOverlap(p)) {
					canPlace = true;
				} else {
					canPlace = false;
				}
				if (canPlace) {

					if (i % 2 == 0) {
						BeachObject bo = new BeachObject(p, HoldingType.OYSTER);
						this.beachObjHM.put(bo.getCurrentPos(), bo);
					} else {
						BeachObject bo = new BeachObject(p, HoldingType.CONCRETE);
						this.beachObjHM.put(bo.getCurrentPos(), bo);
					}
				}
			} while (!canPlace);

		}

		System.out.println("Instantiating new game");
		System.out.println("Player Position: " + this.p.getCurrentPos());
		System.out.println(this.boxes.size() + " Boxes.");
		System.out.println(this.waves.size() + " Waves.");
		System.out.println(this.beachObjHM.size() + " Beach Objects.");

		System.out.println("Shoreline = " + getShoreLine());
	}

	public Boolean checkPlayerOverlap(Point toCreate) {
		Boolean canCreate = true;
		double X1 = p.getCurrentPos().getX();
		double Y1 = p.getCurrentPos().getY();

		double X2 = toCreate.getX();
		double Y2 = toCreate.getY();

		if (X1 == X2 && Y1 == Y2) {
			return false;
		}

		// if the left side of the obj we want to create would overlap with the
		// existing object
		if ((X2 + BeachObject.beachObjDimensions >= X1)
				&& (X2 + BeachObject.beachObjDimensions <= X1 + Player.playerDimensions)) {

			if (Y2 + BeachObject.beachObjDimensions >= Y1
					&& Y2 + BeachObject.beachObjDimensions <= Y1 + Player.playerDimensions) {
				canCreate = false;
			}
			// bottom of creating would overlap
			else if (Y2 >= Y1 && Y2 <= Y1 + Player.playerDimensions) {
				canCreate = false;
			}

		}

		// if the right side of the obj we want to create would overlap with the
		// existing object
		else if ((X2 >= X1) && (X2 <= X1 + Player.playerDimensions)) {

			// if the top of the obj we're creating would overlap with the
			// existing object
			if (Y2 + BeachObject.beachObjDimensions >= Y1
					&& Y2 + BeachObject.beachObjDimensions <= Y1 + Player.playerDimensions) {
				canCreate = false;
			}
			// bottom of creating would overlap
			else if (Y2 >= Y1 && Y2 <= Y1 + Player.playerDimensions) {
				canCreate = false;
			}

		}
		return canCreate;
	}

	/**
	 * 
	 * @param toCreate
	 *            : proposed point of creation
	 * @return true = spot available false = not
	 */
	public Boolean checkBoxOverlap(Point toCreate) {
		Boolean canCreate = true;

		for (Box b : this.boxes.values()) {

			// check if the position would cause overlap
			double X1 = b.getPosition().getX();
			double Y1 = b.getPosition().getY();

			double X2 = toCreate.getX();
			double Y2 = toCreate.getY();

			// if the left side of the obj we want to create would overlap with
			// the existing object
			if ((X2 + BeachObject.beachObjDimensions >= X1)
					&& (X2 + BeachObject.beachObjDimensions <= X1 + Box.boxDimensions)) {

				// if the top of the obj we're creating would overlap with the
				// existing object
				if (Y2 + BeachObject.beachObjDimensions >= Y1
						&& Y2 + BeachObject.beachObjDimensions <= Y1 + Box.boxDimensions) {
					canCreate = false;
				}
				// bottom of creating would overlap
				else if (Y2 >= Y1 && Y2 <= Y1 + Box.boxDimensions) {
					canCreate = false;
				}
			}

			// if the right side of the obj we want to create would overlap with
			// the existing object
			else if ((X2 >= X1) && (X2 <= X1 + Box.boxDimensions)) {

				if (Y2 + BeachObject.beachObjDimensions >= Y1
						&& Y2 + BeachObject.beachObjDimensions <= Y1 + Box.boxDimensions) {
					canCreate = false;
				}
				// bottom of creating would overlap
				else if (Y2 >= Y1 && Y2 <= Y1 + Box.boxDimensions) {
					canCreate = false;
				}
			}
		}

		return canCreate;
	}

	/**
	 * A note: This operation would work better and be less costly if we used a
	 * hashset instead of a hashmap for beachobjects. Did not change because
	 * there's probably a reason we didn't do that.
	 */

	public Boolean checkBeachObjectOverlap(Point toCreate) {
		Boolean canCreate = true;

		// check if the position would cause overlap
		for (BeachObject bo : beachObjHM.values()) {
			Point existing = bo.getCurrentPos();
			// if the element we want to create would overlap horizontally with
			// the element already created
			double X1 = existing.getX();
			double Y1 = existing.getY();
			double X2 = toCreate.getX();
			double Y2 = toCreate.getY();

			// if the left side of the obj we want to create would overlap with
			// the existing object
			if ((X2 + BeachObject.beachObjDimensions >= X1)
					&& (X2 + BeachObject.beachObjDimensions <= X1 + BeachObject.beachObjDimensions)) {

				if (Y2 + BeachObject.beachObjDimensions >= Y1
						&& Y2 + BeachObject.beachObjDimensions <= Y1 + BeachObject.beachObjDimensions) {

					canCreate = false;
				}
				// bottom of creating would overlap
				else if (Y2 >= Y1 && Y2 <= Y1 + BeachObject.beachObjDimensions) {
					canCreate = false;
				}
			}

			// if the right side of the obj we want to create would overlap with
			// the existing object
			else if ((X2 >= X1) && (X2 <= X1 + BeachObject.beachObjDimensions)) {

				if (Y2 + BeachObject.beachObjDimensions >= Y1
						&& Y2 + BeachObject.beachObjDimensions <= Y1 + BeachObject.beachObjDimensions) {

					canCreate = false;
				}
				// bottom of creating would overlap
				else if (Y2 >= Y1 && Y2 <= Y1 + BeachObject.beachObjDimensions) {
					canCreate = false;
				}
			}
		}
		return canCreate;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Player getP() {
		return p;
	}

	public void setP(Player p) {
		this.p = p;
	}

	public HashMap<Point, BeachObject> getBeachObject() {
		return beachObjHM;
	}

	public void setBeachObject(HashMap<Point, BeachObject> beachObject) {
		beachObjHM = beachObject;
	}

	public HashMap<Point, Box> getBoxes() {
		return boxes;
	}

	public void setBoxes(HashMap<Point, Box> boxes) {
		this.boxes = boxes;
	}

	public HealthBar getHB() {
		return HB;
	}

	public void setHB(HealthBar hB) {
		HB = hB;
	}

	public ArrayList<Wave> getWaves() {
		return waves;
	}

	public void setWaves(ArrayList<Wave> waves) {
		this.waves = waves;
	}

	public void updatePlayerPosition(Point updatedPos) {
		this.p.setCurrentPos(updatedPos);
	}

	public void resetWave(int a) {
		// get the wave with this position, and set its position to its initial
		// position

		int i = 0;
		for (Wave w : waves) {
			if (i == a) {
				Point p = new Point(View.viewWidth - Wave.waveWidth, i * Wave.waveSpawnSpacing);
				w.setCurrentPos(p);
				w.resetVelocity();
			}
			i++;

		}
	}

	public void updateShoreLine(int damage) {
		shoreLine -= damage;
	}

	public int getShoreLine() {
		return shoreLine;
	}

	public void setShoreLine(int shoreLine) {
		this.shoreLine = shoreLine;
	}

	public int getNumBoxes() {
		return numBoxes;
	}

	public void setNumBoxes(int numBoxes) {
		this.numBoxes = numBoxes;
	}

	public int getminShoreLine() {
		// TODO Auto-generated method stub
		return minShoreLine;
	}

	public boolean allBoxesFull() {
		boolean allFull = true;

		for (Box b : boxes.values()) {
			if (!(b.isfull())) {
				allFull = false;
			}
		}

		return allFull;
	}

	public boolean boxesCorrect() {
		boolean correct = false;
		double oyst = 0;
		double conc = 0;
		if (allBoxesFull()) {
			for (Box b : boxes.values()) {
				switch (b.getContains()) {
				case OYSTER:
					oyst++;
					break;
				case CONCRETE:
					conc++;
					break;
				default:
					System.out.println("Problem in model->boxesCorrect: box contains " + b.getContains());
					break;
				}
			}
			double percentageOyst = (oyst / (oyst + conc)) * 100;
			if (percentageOyst < 50) {
				// System.out.println("Not enough gabions to win. Percentage = "
				// + percentageOyst);
				correct = false;
			} else {
				correct = true;
				// System.out.println("you win!");
			}
		} else {
			System.out.println("All boxes not yet full");
			correct = false;
		}

		return correct;
	}

}
