package model;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import java.util.Random;
import java.util.Set;
import model.BeachObject;
import model.*;
import view.View;
/**
 * @Class Model handles game logic, backend representation of the game
 */
public class Model {	
	
	//Variables related to initialization
	public static final int numBoxes = 4;
	public static final int numBOS = 20;

	private static int score = 0;
	public final Double a = .206;
	public final Double b = .45;
	public final Double c = .625;

	//Array of gameObjects that view can access
	private static ArrayList<GameObject> gameObjs = new ArrayList<GameObject>();
	
	//General variables
	private Player p;
	private HashMap<Point, BeachObject> beachObjHM = new HashMap<Point, BeachObject>();
	private LinkedHashMap<Point, Box> boxes = new LinkedHashMap<Point, Box>();
	private ArrayList<Wave> waves = new ArrayList<Wave>();
	private HealthBar HB = new HealthBar(50, 200);
	private Shoreline shoreLine;

	public Dimension gameDi = new Dimension(1200,650);
	
	//tutorial vars
	private TutorialWave tBWave;
	private TutorialWave tSWave1;
	private TutorialWave tSWave2;

	//Sprite-related variables
		ImageIcon oystIcon;
		ImageIcon concIcon;
		final int numSprites = 6;
		final int startPSprites = 0;
		final int oystSprite = 9;
		final int concSprite = 8;
		int picNum = 0;
		public ImageIcon[] pics;// holds all sprites for all characters
		public ImageIcon[] oysterBoxes;
		public ImageIcon[] crabPics;
		public ImageIcon[] concreteImages;
		private ImageIcon[] gabionImages;
		transient BufferedImage[] scenery = new BufferedImage[2];	
		private Scenery gameScenery = new Scenery();
	
		

/*
 * Model Constructor
 */
	/**
	 * @author Eaviles
	 * @Purpose initialize all relevant Model variables/objects
	 */
	public Model() {
		this.gameDi = Toolkit.getDefaultToolkit().getScreenSize();
		initSprites();//DO NOT MOVE. This must come first for other inits to work. Thanks!
		initPlayer();//Same comment as above ^
		
		initShoreline();
		initBoxes();
		initWaves();
		initBeachObjs();
		
		tBWave = new TutorialWave(new Point(1100,650),  pics[11], 500, 3);
		shoreLine.setLoosingCoordinate(shoreLine.getShoreBottom().x - HB.getHeight() + 100);
		initGameObjsArr();	
	}



	
/*
 * General Functions
 */
	/**
	 * @author Eaviles
	 * @param a the index of the wave to be reset
	 * Purpose: resets one wave (specified by parameter) by changing its position to its initial position
	 * and recalculating its destination.
	 */
	public void resetWave(int a) {
			Wave w = waves.get(a);
			Point startPos = new Point( this.gameDi.width - Wave.waveWidth, w.getPosition().y );
			Point newDest = new Point( ( getShoreLine().findCorrespondingX(w.getInitialPos().y) ), w.getDestination().y);
			w.reset(startPos, newDest);
	}


	/**
	 * @author Eaviles
	 * Purpose: this is used when shoreline is eroded. If one wave erodes shoreline, all waves
	 * must have their destinations updated so they don't reset early.
	 */
	public void updateWavesDestinations() {
		for(Wave w: waves){
			Point newDest = new Point(getShoreLine().findCorrespondingX(w.getInitialPos().y), w.getDestination().y);
			w.setDestination(newDest);
				
		}		
	}

	/**
	 * @param updatedPos set Player position to this
	 */
	public void updatePlayerPosition(Point updatedPos) {
		this.p.setPosition(updatedPos);
	}
	
	/**
	 * @author Auzi
	 * @Purpose update player sprite to reflect current game state
	 */
	public void updatePlayerSprite() {
		p.setObjIcon(crabPics[p.findIndex()]);
	}
	
	
	/**
	 * @author Estella
	 * @param damage erode the shoreline by this much
	 * @Purpose erode shoreline by damage param to reflect current state of game
	 */
	public void updateShoreLine(int damage) {	
		this.shoreLine.setShoreTop(new Point(this.shoreLine.getShoreTop().x -= damage,this.shoreLine.getShoreTop().y));
		this.shoreLine.setShoreBottom(new Point(this.shoreLine.getShoreBottom().x -= damage,this.shoreLine.getShoreBottom().y));
	}

	/**
	 * @author Eaviles
	 * @return true if all boxes are full. Else, return false.
	 * @Purpose: used by controller to check whether all boxes are full
	 * (i.e. all boxes count variables are equal to their capacity)
	 */
	public boolean allBoxesFull() {
		boolean allFull = true;

		for (Box b : boxes.values()) {
			if (!(b.isfull())) {
				allFull = false;
			}
		}

		return allFull;
	}
	
	/**
	 * @author Eaviles
	 * @return true if at least 50% of boxes were filled with oysters. Else, returns false
	 * @Purpose: used by controller at end of game to determine whether enough boxes were made into gabions
	 */
	public boolean boxesCorrect() {
		boolean correct = false;
		double oyst = 0;
		double conc = 0;
		if (allBoxesFull()) {
			for (Box b : getBoxes().values()) {
				switch (b.getContains()) {
				case OYSTER:
					oyst++;
					break;
				case CONCRETE:
					conc++;
					break;
				default:
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
			correct = false;
		}
		return correct;
	}
	
	
	
/*
 * Tutorial-related functions
 */
	
	
	/**
	 * @author Eaviles
	 * @Purpose: handles Model's role in the game tutorial. Allows tutorial to
	 * demonstrate the effects of a wave hitting a box/the shore
	 */
	public void playTutorialWaveCollision(Box tBox) {
		int x, y;
		// create a wave that corresponds to the box the player put something in
		x = this.shoreLine.findCorrespondingX(tBox.getPosition().y) + 100;
		y = tBox.getPosition().y + Box.boxDimensions / 2;

		tBWave = new TutorialWave(new Point(x, y), pics[11], this.shoreLine.findCorrespondingX(tBox.getPosition().y), 3);
		gameObjs.add(this.tBWave);
	}

	/**
	 * @author Eaviles
	 * @Purpose: handles Model's role in playing the first wave animation for the tutorial
	 */
	public void playTutorialWaveAnimation(){
		int x, y;
		int i=0;
		for(Box b : this.boxes.values()){
			if(i==0){
				x = this.shoreLine.findCorrespondingX(b.getPosition().y) + 50;
				y = b.getPosition().y + Box.boxDimensions/2;
				tSWave1 = new TutorialWave(new Point(x, y), pics[11], this.shoreLine.findCorrespondingX(b.getPosition().y), 1);
				gameObjs.add(this.tSWave1);
			}
			else if(i==1){
				x = this.shoreLine.findCorrespondingX(b.getPosition().y) + 100;
				y = b.getPosition().y + Box.boxDimensions/2;
				tSWave2 = new TutorialWave(new Point(x, y), pics[11], this.shoreLine.findCorrespondingX(b.getPosition().y), 2);
				gameObjs.add(this.tSWave2);
			}
			i++;
		}
	}
	
	/**
	 * @author Eaviles
	 * @Purpose: removes the tutorialwaves from gameobjs array after tutorial is done with them.
	 * This is nice for the update function, as it won't have to process unused objects. It also
	 * keeps the array clean/consisting of only objects that are being used by the game. 
	 */
	
	public void removeTutorialWave(int anim){
		Iterator<GameObject> myIt = gameObjs.iterator();
		while( myIt.hasNext() ){
			GameObject g = myIt.next();
			if(g instanceof TutorialWave){
				if(((TutorialWave)g).getAnimationNumber() == anim){
					myIt.remove();
				}
			}
		}
	}
	
	/**
	 * @author Eaviles
	 * @Purpose reset gameObjsArray by resetting all objects contained within it, 
	 * then clearing the array and re-adding everything. The latter step isn't technically necessary,
	 * but I prefer to have it because I'm paranoid.
	 */
	public void resetGameObjsArray(){

		initShoreline();
		p.setHT(HoldingType.EMPTY);
		p.setObjIcon(this.crabPics[p.getIndex()]);
		this.HB.reset();
		for(Box b: boxes.values()){
			b.setIsfull(false);
			b.setContains(HoldingType.EMPTY);
			b.setCount(0);
			b.setObjIcon(concreteImages[b.getCount()]);
		}

		this.HB.reset();
		
		this.gameObjs.clear();
		this.initGameObjsArr();
		
	}
	
/*
 * Functions required for Model initialization
 * Note: these functions were made public solely for testing purposes
 */

	/**
	 * @author Eaviles
	 * @Purpose: initialize gameObjsArr to contain all game objects needed for 
	 * saving the game and updating View.
	 */
	public void initGameObjsArr() {

		gameObjs.addAll(( Collection <? extends GameObject>) this.boxes.values());
		gameObjs.addAll(this.waves);
		gameObjs.addAll( ( Collection <? extends GameObject>) this.beachObjHM.values());
		gameObjs.add(this.p);
		gameObjs.add(this.shoreLine);
		gameObjs.add(gameScenery);//might not need to save this
		gameObjs.add(this.HB);
	}
	/**
	 * @author Eaviles
	 * @Purpose: initializes Player 
	 */
	public void initPlayer(){
		p = new Player(new Point(Player.startPosition));
		updatePlayerSprite();
	}
	
	/**
	 * @Purpose initialize shoreline according to screen size
	 */
	public void initShoreline(){
		shoreLine =  new Shoreline(new Point((int)(this.gameDi.width*b),(int)(this.gameDi.height*a)),new Point((int) (this.gameDi.width*c),this.gameDi.height));
	}

	/**
	 * @author @Auzi
	 * @Purpose: initializes Boxes 
	 */
	public void initBoxes(){
		for (int i = 0; i < numBoxes; i++) {
			
			Point p = new Point((int) ((this.gameDi.width*.40)+(i*47)),(int) ((.2)*this.gameDi.height+(i*131)));
			
			Box box = new Box(p, concreteImages[0]);
			box.setCapacity(3);
			box.setCount(0);
			box.setContains(HoldingType.EMPTY);
			box.setIndex(i);
			boxes.put(p, box);
		}
	}
	/**
	 * @author Eaviles
	 * @Purpose: initializes Waves
	 */
	public void initWaves(){

		int i=0;
		for( Box b : this.getBoxes().values() ){
			
			Point p = new Point( this.gameDi.width - Wave.waveWidth, b.getPosition().y + Box.boxDimensions/2 );
			Wave w = new Wave(p, pics[11],this.shoreLine.findCorrespondingX(p.y));
			w.setIndex(i);
			this.waves.add(w);
			i++;
		}
		
	}
	/**
	 * @author Eaviles
	 * @Purpose: initializes BeachObjects within their spawn zone (area they're allowed to spawn in)
	 * and makes sure they are not created with positions that would overlap with player/boxes
	 */
	public void initBeachObjs(){
		BeachObject.spawnZoneHeight = (int) (this.gameDi.height*.88);
		BeachObject.spawnZoneWidth = (int) (this.gameDi.width*.45);
		Random r = new Random();
		Boolean canPlace = false;
		
		for (int i = 0; i < numBOS; i++) {
			do {
				// create a point and check that it won't overlap
				Point p = new Point(r.nextInt(BeachObject.spawnZoneWidth),(int) ((this.gameDi.height*.22) + r.nextInt(BeachObject.spawnZoneHeight)));

				if (checkBeachObjectOverlap(p) && checkBoxOverlap(p) && checkPlayerOverlap(p)) {
					if(p.y<HB.getHeight()){
						if(p.x > HB.getWidth()){
							canPlace = true;
						}
					}
					
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

	/**
	 * @author Eaviles
	 * @param toCreate: proposed point of creation
	 * @return true = spot available false = spot not available
	 * @Purpose: checks whether creating a beachObject at Point toCreate would
	 * cause the beachObject to overlap with Player. Takes into account the size
	 * of beachObjects and Player, as well as their position variables.
	 */
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
	 * @author Eaviles
	 * @param toCreate: proposed point of creation
	 * @return true = spot available false = spot not available
	 * @Purpose: checks whether creating a beachObject at Point toCreate would
	 * cause the beachObject to overlap with a box. Takes into account the size
	 * of beachObjects and Boxes, as well as their position variables.
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
	 * @author Eaviles
	 * @param toCreate: proposed point of creation
	 * @return true = spot available false = spot not available
	 * @Purpose: checks whether creating a beachObject at Point toCreate would
	 * cause the BeachObject to overlap with another BeachObject. Takes into account the size
	 * of the BeachObjects, as well as their position variables.
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
	
	/**
	 * @author Auzi
	 * @Purpose:
	 */
	public void initSprites() {

		String[] myNames = { "crabN.png", "crabS.png", "crabE.png", "crabW.png", "crabNE.png","crabNW.png", 
				"crabSE.png","crabSW.png", "concrete1.png", "oyster1.png","box.png", "wave.png"};
		
		String[] concreteFiles = {"empty.png","concrete33.png","concrete66.png","concrete100.png"};
		String[] gabionFiles = {"empty.png", "gabion33.png","gabion66.png","gabion100.png"};
		String[] crabFiles = {"crabN.png", "crabS.png", "crabE.png", "crabW.png", "crabNE.png","crabNW.png", 
								"crabSE.png","crabSW.png","ConcretecrabN.png", "ConcretecrabS.png", "ConcretecrabE.png", "ConcretecrabW.png", "ConcretecrabNE.png","ConcretecrabNW.png", 
								"ConcretecrabSE.png","ConcretecrabSW.png","OystercrabN.png", "OystercrabS.png", "OystercrabE.png", "OystercrabW.png", "OystercrabNE.png","OystercrabNW.png", 
								"OystercrabSE.png","OystercrabSW.png"};
		
		String[] sceneryFiles = {"shore.png", "sky.png"};
		pics = new ImageIcon[myNames.length];
		concreteImages = new ImageIcon[concreteFiles.length];
		setGabionImages(new ImageIcon[gabionFiles.length]);
		crabPics = new ImageIcon[crabFiles.length];
		scenery = new BufferedImage[sceneryFiles.length];
		
		
		int i = 0;
		for (String s : myNames) {
			pics[i] = createImage(s);
			i++;
		}
		i = 0;
	
		for(String s : crabFiles){
			crabPics[i] = createImage(s);
			i++;
		}
		i = 0;
		for(String s: concreteFiles){
			concreteImages[i] = createImage(s);
			i++;
		}
		i = 0;
		for(String s: gabionFiles){
			getGabionImages()[i] = createImage(s);
			i++;
		}
		scenery[0] = (BufferedImage) createImage("sky.png").getImage();
		scenery[1] = (BufferedImage) createImage("shore.png").getImage();	
		
		this.gameScenery.setScenery(scenery);
	}

	/**
	 * @author Pulled from Orc animation lab
	 * @param n: name of file to be read from
	 * @return: imageIcon that was created from file, or null
	 * @Purpose: read from file, create imageIcon from file. 
	 */
	private ImageIcon createImage(String n) {
		ImageIcon imageIcon;
		try {
			imageIcon = new ImageIcon(ImageIO.read(new File("src/Sprites/Player/copy/" + n)));
			return imageIcon;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

/*
 * Setters and Getters
 */
	/**
	 * @return Player object
	 */
	public Player getP() {
		return p;
	}
	
	/**
	 * @param p set player to this (used in testing)
	 */
	public void setP(Player p) {
		this.p = p;
	}

	/**
	 * @return beachObjHM - HashMap of beachObjects
	 */
	public HashMap<Point, BeachObject> getBeachObject() {
		return beachObjHM;
	}

	/**
	 * @param beachObject set beachObjHM (HashMap of beachObjects) to this
	 */
	public void setBeachObject(HashMap<Point, BeachObject> beachObject) {
		beachObjHM = beachObject;
	}

	/**
	 * @return LinkedHashMap of boxes
	 */
	public LinkedHashMap<Point, Box> getBoxes() {
		return boxes;
	}

	/**
	 * @param boxes set LinedHashMap boxes to this
	 */
	public void setBoxes(LinkedHashMap<Point, Box> boxes) {
		this.boxes = boxes;
	}
	
	/**
	 * @return HB health bar
	 */
	public HealthBar getHB() {
		return HB;
	}

	/**
	 * @param hB health bar
	 */
	public void setHB(HealthBar hB) {
		HB = hB;
	}
	
	/**
	 * @return waves
	 */
	public ArrayList<Wave> getWaves() {
		return waves;
	}

	/**
	 * @param waves set waves to this
	 */
	public void setWaves(ArrayList<Wave> waves) {
		this.waves = waves;
	}

	/**
	 * @return shoreLine object
	 */
	public Shoreline getShoreLine() {
		return shoreLine;
	}

	/**
	 * @param shoreLine set shoreLine to this
	 */
	public void setShoreLine(Shoreline shoreLine) {
			this.shoreLine = shoreLine;
	}
	/**
	 * @return gabionImages
	 */
	public ImageIcon[] getGabionImages() {
		return gabionImages;
	}
	/**
	 * @param gabionImages set gabionImages to this
	 */
	public void setGabionImages(ImageIcon[] gabionImages) {
		this.gabionImages = gabionImages;
	}
	
	/**
	 * @return numBoxes
	 */
	public int getNumBoxes() {
		return numBoxes;
	}

	/**
	 * @return gameObjs arraylist
	 */
	public static ArrayList<GameObject> getGameObjs(){
		return gameObjs;
	}

	/**
	 * @return tBWave (tutorial wave for the second wave animation)
	 */
	public Wave gettBWave() {
		return tBWave;
	}

	/**
	 * @param tBWave set tBWave (tutorial wave for the second wave animation) to this
	 */
	public void settBWave(TutorialWave tBWave) {
		this.tBWave = tBWave;
	}

	/**
	 * @return gameScenery
	 */
	public Scenery getGameScenery() {
		return gameScenery;
	}
	/**
	 * @param gameScenery set gameScenery to this
	 */
	public void setGameScenery(Scenery gameScenery) {
		this.gameScenery = gameScenery;
	}
	/**
	 * @return tSWave1 the first wave of the first wave animation in the tutorial
	 */
	public TutorialWave gettSWave1() {
		return tSWave1;
	}
	/**
	 * @return tSWave2 the second wave of the first wave animation in the tutorial
	 */
	public TutorialWave gettSWave2() {
		return tSWave2;
	}

}