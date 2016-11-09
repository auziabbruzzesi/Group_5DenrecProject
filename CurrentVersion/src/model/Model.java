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

	private int score = 0;
	private Player p = new Player(new Point(Player.startPosition));
	// private Collection<BeachObject> beachObjHM = new
	// ArrayList<BeachObject>();
	private HashMap<Point, BeachObject> beachObjHM = new HashMap<Point, BeachObject>();
	private HashMap<Point, Box> boxes = new HashMap<Point, Box>();
	private Collection<Wave> waves = new ArrayList<Wave>();

	public Model() {
		Random r = new Random();
		Boolean canPlace;
		// BOXES ARE CREATED HERE
		for (int i = 0; i < 4; i++) {
			Point p = new Point((2 * View.viewWidth) / 3 - Box.boxDimensions - Box.boxToViewEdgeSpacing,
					i * Box.boxToBoxInterval + 20);// Auzi did this. this is bad. please fix it when the time comes.
			this.boxes.put(p, new Box(p));
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
				// System.out.println(canPlace);
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
		// WAVES CREATED HERE
		for (int i = 0; i < 4; i++) {
			// create point and check if they overlap
			Point p = new Point(r.nextInt(Wave.waveSpawnSpacing), r.nextInt(Wave.waveSpawnSpacing));
			if (checkBeachObjectOverlap(p) && checkBoxOverlap(p))
				canPlace = true;
			else {
				canPlace = false;
			}

		}

		System.out.println("Instantiating new game");
		System.out.println("Player Position: " + this.p.getCurrentPos());
		System.out.println(this.boxes.size() + " Boxes.");
		System.out.println(this.waves.size() + " Waves.");
		System.out.println(this.beachObjHM.size() + " Beach Objects.");

	}

	public Boolean checkPlayerOverlap(Point toCreate) {
		// System.out.println("In player overlap checking function");
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
			// We know X's overlap, so check if Y's do, too. If they don't, it's
			// okay. If they do, it's a true overlap.
			// System.out.println("x overlap from the left occurred");
			// if the top of the obj we're creating would overlap with the
			// existing object
			if (Y2 + BeachObject.beachObjDimensions >= Y1
					&& Y2 + BeachObject.beachObjDimensions <= Y1 + Player.playerDimensions) {
				canCreate = false;
				// System.out.println("overlap from the top occurred");
			}
			// bottom of creating would overlap
			else if (Y2 >= Y1 && Y2 <= Y1 + Player.playerDimensions) {
				canCreate = false;
				// System.out.println("overlap from the bottom occurred");
			}

		}

		// if the right side of the obj we want to create would overlap with the
		// existing object
		else if ((X2 >= X1) && (X2 <= X1 + Player.playerDimensions)) {
			// We know X's overlap, so check if Y's do, too. If they don't, it's
			// okay. If they do, it's a true overlap.
			// if the top of the obj we're creating would overlap with the
			// existing object
			// System.out.println("x overlap from the right occurred");

			if (Y2 + BeachObject.beachObjDimensions >= Y1
					&& Y2 + BeachObject.beachObjDimensions <= Y1 + Player.playerDimensions) {
				canCreate = false;
				// System.out.println("overlap from the top occurred");
			}
			// bottom of creating would overlap
			else if (Y2 >= Y1 && Y2 <= Y1 + Player.playerDimensions) {
				canCreate = false;
				// System.out.println("overlap from the bottom occurred");
			}

		}

		// System.out.println("canCreate = " + canCreate);
		return canCreate;
	}

	/**
	 * 
	 * @param toCreate
	 *            : proposed point of creation
	 * @return true = spot available false = not
	 */
	public Boolean checkBoxOverlap(Point toCreate) {
		// System.out.println("In box overlap check function");
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
				// We know X's overlap, so check if Y's do, too. If they don't,
				// it's okay. If they do, it's a true overlap.

				// if the top of the obj we're creating would overlap with the
				// existing object
				if (Y2 + BeachObject.beachObjDimensions >= Y1
						&& Y2 + BeachObject.beachObjDimensions <= Y1 + Box.boxDimensions) {
					canCreate = false;
					// System.out.println("overlap op 3 occurred");
				}
				// bottom of creating would overlap
				else if (Y2 >= Y1 && Y2 <= Y1 + Box.boxDimensions) {
					canCreate = false;
					// System.out.println("overlap op 4 occurred");
				}
				// System.out.println("x overlap from the left occurred");
			}

			// if the right side of the obj we want to create would overlap with
			// the existing object
			else if ((X2 >= X1) && (X2 <= X1 + Box.boxDimensions)) {
				// We know X's overlap, so check if Y's do, too. If they don't,
				// it's okay. If they do, it's a true overlap.
				// if the top of the obj we're creating would overlap with the
				// existing object
				if (Y2 + BeachObject.beachObjDimensions >= Y1
						&& Y2 + BeachObject.beachObjDimensions <= Y1 + Box.boxDimensions) {
					canCreate = false;
					// System.out.println("overlap op 3 occurred");
				}
				// bottom of creating would overlap
				else if (Y2 >= Y1 && Y2 <= Y1 + Box.boxDimensions) {
					canCreate = false;
					// System.out.println("overlap op 4 occurred");
				}

				// System.out.println("x overlap from the right occurred");

			}
		}

		return canCreate;
	}
	/*
	 * A note: This operation would work better and be less costly if we used a
	 * hashset instead of a hashmap for beachobjects. Did not change because
	 * there's probably a reason we didn't do that.
	 */

	public Boolean checkBeachObjectOverlap(Point toCreate) {
		// System.out.println("In overlap check function");
		Boolean canCreate = true;

		// check if the position would cause overlap

		// Set<Entry<Point, model.BeachObject>> BOs = BeachObject.entrySet();

		for (BeachObject bo : beachObjHM.values()) {
			Point existing = bo.getCurrentPos();
			// if the element we want to create would overlap horizontally with
			// the element already created
			// cast it.next() to a point (which is what it is) and then getX()
			double X1 = existing.getX();
			double Y1 = existing.getY();
			double X2 = toCreate.getX();
			double Y2 = toCreate.getY();

			// if the left side of the obj we want to create would overlap with
			// the existing object
			if ((X2 + BeachObject.beachObjDimensions >= X1)
					&& (X2 + BeachObject.beachObjDimensions <= X1 + BeachObject.beachObjDimensions)) {
				// We know X's overlap, so check if Y's do, too. If they don't,
				// it's okay. If they do, it's a true overlap.
				// if the top of the obj we're creating would overlap with the
				// existing object
				if (Y2 + BeachObject.beachObjDimensions >= Y1
						&& Y2 + BeachObject.beachObjDimensions <= Y1 + BeachObject.beachObjDimensions) {
					canCreate = false;
					// System.out.println("overlap op 3 occurred");
				}
				// bottom of creating would overlap
				else if (Y2 >= Y1 && Y2 <= Y1 + BeachObject.beachObjDimensions) {
					canCreate = false;
					// System.out.println("overlap op 4 occurred");
				}
				// System.out.println("x overlap from the left occurred");
			}

			// if the right side of the obj we want to create would overlap with
			// the existing object
			else if ((X2 >= X1) && (X2 <= X1 + BeachObject.beachObjDimensions)) {
				// We know X's overlap, so check if Y's do, too. If they don't,
				// it's okay. If they do, it's a true overlap.
				// if the top of the obj we're creating would overlap with the
				// existing object
				if (Y2 + BeachObject.beachObjDimensions >= Y1
						&& Y2 + BeachObject.beachObjDimensions <= Y1 + BeachObject.beachObjDimensions) {
					canCreate = false;
					// System.out.println("overlap op 3 occurred");
				}
				// bottom of creating would overlap
				else if (Y2 >= Y1 && Y2 <= Y1 + BeachObject.beachObjDimensions) {
					canCreate = false;
					// System.out.println("overlap op 4 occurred");
				}

				// System.out.println("x overlap from the right occurred");

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

	public Collection<Wave> getWaves() {
		return waves;
	}

	public void setWaves(Collection<Wave> waves) {
		this.waves = waves;
	}

	public void updatePlayerPosition(Point updatedPos) {
		this.p.setCurrentPos(updatedPos);
	}

}
