/*
 * Quick Notes:
 * 	1. Might be able to consolidate comparison if/else statements of checkOverlap functions into their own callable function. That's not
 * 		tonight's problem, but it's something to think about for later.
 */
package model;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import java.util.Random;
import java.util.Set;
import model.BeachObject;
import view.View;

public class Model {	
	
	//Variables related to initialization
	public static final int numBoxes = 4;
	public static final int numWaves = 5;
	public static final int numBOS = 20;
	private static int score = 0;
	
	private static ArrayList<Object> gameObjs = new ArrayList<Object>();
	
	//General variables
	private Player p;
	private HashMap<Point, BeachObject> beachObjHM = new HashMap<Point, BeachObject>();
	private HashMap<Point, Box> boxes = new HashMap<Point, Box>();
	private ArrayList<Wave> waves = new ArrayList<Wave>();
	private HealthBar HB = new HealthBar(50, 200);
	private Integer shoreLine;
	private int minShoreLine;// TODO: m's & v's shores & minshores in-sync

	//Sprite-related variables
		ImageIcon oystIcon;
		ImageIcon concIcon;

		final int numSprites = 6;
		final int startPSprites = 0;
		final int oystSprite = 9;
		final int concSprite = 8;
		int picNum = 0;
		ImageIcon[] pics;// holds all sprites for all characters
		ImageIcon[] oysterBoxes;
		ImageIcon[] crabPics;
		BufferedImage[] scenery = new BufferedImage[2];
	
/*
 * Model Constructor
 */
	public Model() {
		initSprites();//DO NOT MOVE. This must come first for other inits to work. Thanks!
		initPlayer();//Same comment as above ^
		initBoxes();
		initWaves();
		initBeachObjs();
		shoreLine = (2*view.View.viewWidth)/3;
		minShoreLine = shoreLine - HB.getHeight()+ 100; 
		initGameObjsArr();	

		System.out.println("Instantiating new game");
		System.out.println("Player Position: " + this.p.getPosition());
		System.out.println(this.boxes.size() + " Boxes.");
		System.out.println(this.waves.size() + " Waves.");
		System.out.println(this.beachObjHM.size() + " Beach Objects.");
		System.out.println("Shoreline = " + getShoreLine());
	}

	
/*
 * General Functions
 */
	public void resetWave(int a) {
		/*
		 *  get the wave with this position, calculate what its new start position/destination
		 *  should be based on where shoreline currently is, and reset it based on that. 
		 */
			Wave w = waves.get(a);
			Point startPos = new Point(View.viewWidth - Wave.waveWidth, a * Wave.waveSpawnSpacing);
			Point newDest = new Point(getShoreLine(), w.getDestination().y);
			w.reset(startPos, newDest);
	}


	/*
	 * this is used when shoreline is eroded. If one wave erodes shoreline, all waves
	 * have their destinations updated so they don't crash (reset) early.
	 */
	public void updateWavesDestinations() {
		for(Wave w: waves){
			Point newDest = new Point(getShoreLine(), w.getDestination().y);
			w.setDestination(newDest);
		}		
	}

	public void updatePlayerPosition(Point updatedPos) {
		this.p.setCurrentPos(updatedPos);
	}
	public void updatePlayerSprite() {
		p.setObjIcon(crabPics[p.findIndex()]);
	}
	
	public void updateShoreLine(int damage) {
		shoreLine -= damage;
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

	/*
	 * used by controller at end of game to determine whether enough boxes were made into gabions
	 */
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
				correct = false;
			} else {
				correct = true;
			}
		} else {
			System.out.println("All boxes not yet full");
			correct = false;
		}
		return correct;
	}
	
/*
 * Functions required for Model initialization
 */
	private void initGameObjsArr() {
		gameObjs.addAll((Collection<? extends Object>) this.boxes.values());
		gameObjs.addAll(this.waves);
		gameObjs.addAll((Collection<? extends Object>) this.beachObjHM.values());
		gameObjs.add(this.p);
		gameObjs.add(this.shoreLine);
		gameObjs.add(scenery);
		gameObjs.add(this.HB);
		System.out.println(gameObjs);
	}
	
	private void initPlayer(){
		p = new Player(new Point(Player.startPosition));
		updatePlayerSprite();
	}
	
	private void initBoxes(){
		for (int i = 0; i < numBoxes; i++) {
			Point p = new Point(Box.boxX, i * Box.boxToBoxInterval + Box.boxToTopSpacing);
			this.boxes.put(p, new Box(p, pics[10]) );
		}
	}
	private void initWaves(){
		for (int i = 0; i < numWaves; i++) {
			Point p = new Point(View.viewWidth - Wave.waveWidth, i * Wave.waveSpawnSpacing);
			this.waves.add(new Wave(p, pics[11]));
		}
	}
	private void initBeachObjs(){
		Random r = new Random();
		Boolean canPlace;
		
		for (int i = 0; i < numBOS; i++) {
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
						BeachObject bo = new BeachObject(p, HoldingType.OYSTER, pics[oystSprite]);
						this.beachObjHM.put(bo.getPosition(), bo);
					} else {
						BeachObject bo = new BeachObject(p, HoldingType.CONCRETE, pics[concSprite]);
						this.beachObjHM.put(bo.getPosition(), bo);
					}
				}
			} while (!canPlace);
		}
	}


	public Boolean checkPlayerOverlap(Point toCreate) {
		Boolean canCreate = true;
		double X1 = p.getPosition().getX();
		double Y1 = p.getPosition().getY();

		double X2 = toCreate.getX();
		double Y2 = toCreate.getY();

		if (X1 == X2 && Y1 == Y2) {
			return false;
		}

		// if the left side of the obj we want to create would overlap with the
		// existing object
		if ((X2 + BeachObject.beachObjDimensions >= X1)
				&& (X2 + BeachObject.beachObjDimensions <= X1 + Player.playerDimensions)) {

			//top of object to be created would overlap
			if (Y2 + BeachObject.beachObjDimensions >= Y1
					&& Y2 + BeachObject.beachObjDimensions <= Y1 + Player.playerDimensions) {
				canCreate = false;
			}
			// bottom of obj to be created would overlap
			else if (Y2 >= Y1 && Y2 <= Y1 + Player.playerDimensions) {
				canCreate = false;
			}
		}

		// if the right side of the obj we want to create would overlap with the
		// existing object
		else if ((X2 >= X1) && (X2 <= X1 + Player.playerDimensions)) {

			//top of object to be created would overlap
			if (Y2 + BeachObject.beachObjDimensions >= Y1
					&& Y2 + BeachObject.beachObjDimensions <= Y1 + Player.playerDimensions) {
				canCreate = false;
			}
			//bottom of object to be created would overlap
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

				//top of object to be created would overlap
				if (Y2 + BeachObject.beachObjDimensions >= Y1
						&& Y2 + BeachObject.beachObjDimensions <= Y1 + Box.boxDimensions) {
					canCreate = false;
				}
				//bottom of object to be created would overlap
				else if (Y2 >= Y1 && Y2 <= Y1 + Box.boxDimensions) {
					canCreate = false;
				}
			}

			// if the right side of the obj we want to create would overlap with
			// the existing object
			else if ((X2 >= X1) && (X2 <= X1 + Box.boxDimensions)) {

				//top of object to be created would overlap
				if (Y2 + BeachObject.beachObjDimensions >= Y1
						&& Y2 + BeachObject.beachObjDimensions <= Y1 + Box.boxDimensions) {
					canCreate = false;
				}
				//bottom of object to be created would overlap
				else if (Y2 >= Y1 && Y2 <= Y1 + Box.boxDimensions) {
					canCreate = false;
				}
			}
		}
		return canCreate;
	}

	/**
	 * A note: This operation would work better and be less costly if we used a
	 * hashset instead of a hashmap for beachobjects. 
	 * TODO: Will explore changing it, 
	 * but for now it works and we don't want to mess it up the day before beta.
	 */

	public Boolean checkBeachObjectOverlap(Point toCreate) {
		Boolean canCreate = true;

		// check if the position would cause overlap
		for (BeachObject bo : beachObjHM.values()) {
			Point existing = bo.getPosition();
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
	
	public void initSprites() {

		String[] myNames = { "crabN.png", "crabS.png", "crabE.png", "crabW.png", "crabNE.png","crabNW.png", 
				"crabSE.png","crabSW.png", "concrete1.png", "oyster1.png","box.png", "wave.png"};
		String[] boxImages = {"box1.png","box2.png","box3.png","box4.png","box5.png"};
		String[] crabFiles = {"crabN.png", "crabS.png", "crabE.png", "crabW.png", "crabNE.png","crabNW.png", 
								"crabSE.png","crabSW.png","ConcretecrabN.png", "ConcretecrabS.png", "ConcretecrabE.png", "ConcretecrabW.png", "ConcretecrabNE.png","ConcretecrabNW.png", 
								"ConcretecrabSE.png","ConcretecrabSW.png","OystercrabN.png", "OystercrabS.png", "OystercrabE.png", "OystercrabW.png", "OystercrabNE.png","OystercrabNW.png", 
								"OystercrabSE.png","OystercrabSW.png"};
		
		String[] sceneryFiles = {"shore.png", "sky.png"};
		pics = new ImageIcon[myNames.length];
		oysterBoxes = new ImageIcon[boxImages.length];
		crabPics = new ImageIcon[crabFiles.length];
		scenery = new BufferedImage[sceneryFiles.length];
		
		
		int i = 0;
		for (String s : myNames) {
			pics[i] = createImage(s);
			i++;
		}
		i = 0;
		for(String s : boxImages){
			oysterBoxes[i] = createImage(s);
			i++;
		}
		i=0;
		for(String s : crabFiles){
			crabPics[i] = createImage(s);
			i++;
		}
		i = 0;
		scenery[0] = (BufferedImage) createImage("sky.png").getImage();
		scenery[1] = (BufferedImage) createImage("shore.png").getImage();		
	}

	/**
	 * @param n
	 * @return Read image icon and return
	 */
	private ImageIcon createImage(String n) {
		ImageIcon imageIcon;
		try {
			// System.out.println("About to read an image");
			imageIcon = new ImageIcon(ImageIO.read(new File("src/Sprites/Player/copy/" + n)));
			// System.out.println("bufferedImage");
			return imageIcon;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

/*
 * Setters and Getters
 */
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

	public int getShoreLine() {
		return shoreLine;
	}

	public void setShoreLine(int shoreLine) {
		this.shoreLine = shoreLine;
	}

	public int getNumBoxes() {
		return numBoxes;
	}

	public int getminShoreLine() {
		return minShoreLine;
	}
	
	public static ArrayList<Object> getGameObjs(){
		return gameObjs;
	}	
}