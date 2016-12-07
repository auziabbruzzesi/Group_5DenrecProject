package test;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.swing.ImageIcon;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.BeachObject;
import model.Box;
import model.Direction;
import model.GameObject;
import model.HealthBar;
import model.HoldingType;
import model.Model;
import model.Player;
import model.Shoreline;
import model.TutorialWave;
import model.Wave;

public class ModelTest {

	Model model = new Model();
	Player player = new Player(new Point(5, 5));
	Point playerPos = new Point(5, 5);
	BeachObject concrete = new BeachObject(new Point(2, 4), HoldingType.CONCRETE, null);
	HashMap<Point, BeachObject> beachObjHM = new HashMap<Point, BeachObject>();
	LinkedHashMap<Point, Box> boxes = new LinkedHashMap<Point, Box>();
	ArrayList<Wave> waves = new ArrayList<Wave>();
	int numWaves = 6;
	int shoreLine = 840;
	
	String[] crabFiles = {"crabN.png", "crabS.png", "crabE.png", "crabW.png", "crabNE.png","crabNW.png", 
			"crabSE.png","crabSW.png","ConcretecrabN.png", "ConcretecrabS.png", "ConcretecrabE.png", "ConcretecrabW.png", "ConcretecrabNE.png","ConcretecrabNW.png", 
			"ConcretecrabSE.png","ConcretecrabSW.png","OystercrabN.png", "OystercrabS.png", "OystercrabE.png", "OystercrabW.png", "OystercrabNE.png","OystercrabNW.png", 
			"OystercrabSE.png","OystercrabSW.png"};
	ImageIcon[] crabPics = new ImageIcon[crabFiles.length];
	Box b1 = new Box();  
	Box b2 = new Box();
	Box b3 = new Box();
	Box b4 = new Box();
	@Test 
	public void initGameObjArrayTest(){
		model.initGameObjsArr();
		assertTrue(Model.getGameObjs().containsAll(( Collection <? extends GameObject>) model.getBoxes().values()));
		assertTrue(Model.getGameObjs().containsAll(( Collection <? extends GameObject>) model.getWaves()));
		assertTrue(Model.getGameObjs().containsAll(( Collection <? extends GameObject>) model.getBeachObject().values()));
		assertTrue(Model.getGameObjs().contains(model.getP()));
		assertTrue(Model.getGameObjs().contains(model.getShoreLine()));
		assertTrue(Model.getGameObjs().contains(model.getHB()));
		assertTrue(Model.getGameObjs().contains(model.getGameScenery()));
	}
	@Test 
	public void initPlayerTest(){
		model.initPlayer();
		assertEquals(model.getP().getPosition(),new Point(Player.startPosition));
		assertNotNull(model.getP().getObjIcon());
	}
	@Test 
	public void initBoxesTest(){
		Box b = new Box();
		model.initBoxes();
	
		assertNotNull(model.getBoxes());
		assertTrue(b.getCount() == 0);
		assertTrue(b.getCapacity() == 3);
		assertEquals(b.getContains(), HoldingType.EMPTY);
	}
	@Test
	public void initWavesTest(){
		
		model.initWaves();
		assertNotNull(model.getWaves());
		assertEquals(model.getWaves().get(0).getPosition(), new Point(1390,230));
		assertEquals(model.getWaves().get(0).getIndex(), 0);
		assertEquals(model.getWaves().get(0).getDestination().x, model.getShoreLine().findCorrespondingX(model.getWaves().get(0).getPosition().y));
	}
	@Test 
	public void initBeachObjTest(){
		
		BeachObject bo = new BeachObject(new Point(1,1), null, null);
		beachObjHM.put(new Point(1,1), bo);
		model.setBeachObject(beachObjHM);
		
		model.initBeachObjs();
		assertNotNull(model.getBeachObject());
	}
 

	@Test
	public void playerOverlapTest() {
		model.setP(player);

		model.checkPlayerOverlap(playerPos);
		assertFalse(model.checkPlayerOverlap(playerPos));

		model.checkPlayerOverlap(concrete.getPosition());

		// point (2,4) is too close to player current position to create
		assertFalse(model.checkPlayerOverlap(concrete.getPosition()));
		Point farPoint = new Point(70, 70); // point that should return true
		assertTrue(model.checkPlayerOverlap(farPoint));

	}
 	@Test
	public void boxOverlapTest() {
		Box testBox = new Box();
		testBox.setPosition(playerPos);
		boxes.put(new Point(30, 30), testBox); // box at players position
		model.setP(player);

		model.setBoxes(boxes);

		model.checkBoxOverlap(playerPos);
		assertFalse(model.checkBoxOverlap(playerPos));

		Point farPoint = new Point(100, 1000);

		model.checkBoxOverlap(farPoint);
		assertTrue(model.checkBoxOverlap(farPoint));

	}

	@Test
	public void checkBeachObjectOverlap() {
		model.setBeachObject(beachObjHM);
		beachObjHM.put(playerPos, concrete);
		model.checkBeachObjectOverlap(playerPos);
		assertFalse(model.checkBeachObjectOverlap(playerPos));

		Point farPoint = new Point(200, 500);
		model.checkBeachObjectOverlap(farPoint);
		assertTrue(model.checkBeachObjectOverlap(farPoint));
	}

	// TODO: figure out why this test is failing!!!!!!!!!!!!!!!!
	@Test
	public void resetWaveTest() {
		 model.resetWave(2);
		 Point defaultPos = new Point(10, 2);
		 Wave w1 = new Wave(defaultPos, null, 15);
		 Wave w2 = new Wave(new Point (1,1), null, 20);
		
		 waves.add(w1);
		 waves.add(w2);
		
		 model.resetWave(2);
		
		 assertEquals(w1.getInitialPos().x, w1.getPosition().x, 0);
		 assertEquals(w1.getDestination().x, 15);
		 assertEquals(w2.getDestination().x, 20);
		

	}

	@Test
	public void allBoxesNotTest() {
		
		b1.setIsfull(true);
		b2.setIsfull(false);
		b3.setIsfull(false);
		b4.setIsfull(false);
		boxes.put(new Point(1, 1), b1);
		boxes.put(new Point(2, 2), b2);
		boxes.put(new Point(3, 3), b3);
		boxes.put(new Point(4,4), b4);
		model.setBoxes(boxes);
		System.out.println(model.getBoxes());
		
		// only one box is full so returns false.
    	assertFalse(model.allBoxesFull());
		
    	b2.setIsfull(true);
    	b3.setIsfull(true);
    	b4.setIsfull(true);
    	
    	assertTrue(model.allBoxesFull());

	}


	@Test
	public void boxesCorrectTest() {
		b1.setIsfull(true);
		b2.setIsfull(true);
    	b3.setIsfull(true);
    	b4.setIsfull(true);
//    	@Mayah
//    	b1.setHT(HoldingType.CONCRETE);
//    	b2.setHT(HoldingType.OYSTER);
//    	b3.setHT(HoldingType.CONCRETE);
//    	b4.setHT(HoldingType.OYSTER);
		assertFalse(model.boxesCorrect());

	}
	
	@Test 
	public void updateWaveDestinationsTest(){
		// assumes view is a box with dimensions 100, 100 for simplicity 
		Wave w1 = new Wave(new Point(90, 5), null, 5);
		Wave w2 = new Wave(new Point(90, 10), null, 70);
	
		waves.add(w1);
		waves.add(w2);
		
		model.updateWavesDestinations();
		
		assertEquals(w2.getDestination().getX(), 70, 0);
		assertEquals(w1.getDestination().getX(), 5, 0);
		
	}
	@Test
	public void updateShorelineTest(){
		Shoreline sl = new Shoreline(new Point(10,10), new Point(100,100));
		model.setShoreLine(sl);
		model.updateShoreLine(10);
		
		assertEquals(sl.getShoreTop().x, 0);  // decrement 10
		assertEquals(sl.getShoreTop().y, 10);
		assertEquals(sl.getShoreBottom().x, 90);  // decrement 10
		assertEquals(sl.getShoreBottom().y, 100);  
	}
//	@Test
//	public void testRemoveTutorialWave(){
//		Iterator<GameObject> myIt = Model.getGameObjs().iterator();
//		model.removeTutorialWave();
//		
//		assertTrue(myIt.hasNext());
//		
//		model.removeTutorialWave();
//		assertTrue(myIt.hasNext());
//		
//		model.removeTutorialWave();
//		assertTrue(myIt.hasNext());
//		
//		
//	}
	@Test 
	public void updatePlayerPosTest(){
		model.updatePlayerPosition(new Point(10,10));
		assertEquals(model.getP().getPosition(), new Point(10,10));
	}
	@Test
	public void resetGameObjArray(){
		b1.setPosition(new Point(0,0));
		b2.setPosition(new Point(1,1));
		boxes.put(new Point(0,0), b1);
		boxes.put(new Point(1,1), b2);
		model.setBoxes(boxes);
		model.resetGameObjsArray();
		
		assertEquals(model.getBoxes().get(b1.getPosition()).getCount() , 0);
		assertEquals(model.getBoxes().get(b2.getPosition()).getCount() , 0);
		assertEquals(model.getBoxes().get(b1.getPosition()).getContains() , HoldingType.EMPTY);
		assertEquals(model.getBoxes().get(b2.getPosition()).getContains() , HoldingType.EMPTY);
		assertNotNull(model.getBoxes().get(b1.getPosition()).getObjIcon());
		assertNotNull(model.getBoxes().get(b2.getPosition()).getObjIcon());
		

	}
	@Test
	public void playTutorialWaveCollisionTest(){
		Shoreline sl = new Shoreline(new Point (0,0), new Point (5,5));
		model.setShoreLine(sl);
		Point test = new Point(10,5);
		Box tbox = new Box();
		tbox.setPosition(test);
		char b = 'b';
		TutorialWave tBWave = new TutorialWave(new Point((model.getShoreLine().findCorrespondingX(tbox.getPosition().y) + 100), tbox.getPosition().y + Box.boxDimensions/2), null, model.getShoreLine().findCorrespondingX(tbox.getPosition().y), numWaves);
		model.playTutorialWaveCollision(tbox);
		assertEquals(tBWave.getPosition().y, model.getShoreLine().findCorrespondingX((tBWave.getPosition().y)));
	}
	@Test
	public void healthTest(){
		HealthBar HB = new HealthBar(10, 20);
		model.setHB(HB);
		model.getHB().damage(10);
		
		assertEquals(model.getHB().getHeight(), 20);
		assertEquals(model.getHB().getInsideHeight(), 18, 1);
		assertEquals(model.getHB().getHealth(), 90);
		assertEquals(model.getHB().getStartingY(), 2, .5);
	}
	@Test 
	public void shorelineDecrementTest(){
		Shoreline sl = new Shoreline(new Point(0,0), new Point(5,5));
		sl.setTotalDecrement(15);
		model.setShoreLine(sl);
		model.getShoreLine().updateTotalDecrement(10);
		assertEquals(model.getShoreLine().getTotalDecrement(), 5);
	}
	@Test
	public void incrementCountBoxTest(){
		Point p = new Point(1,1);
		Box b = new Box();
		b.setPosition(p);
		b.setCount(0);
		b.setCapacity(2);
		b.incrementCount();
		LinkedHashMap<Point, Box > bb = new LinkedHashMap<Point, Box>();
		bb.put(p, b);
		model.setBoxes(bb);
		assertEquals(model.getBoxes().get(p).getCount(), 1);
		b.incrementCount();
		assertTrue(model.getBoxes().get(p).isfull());
		
	}
	@Test
	public void playerTest(){
		Point point = new Point(5,5);
		
		Player p = new Player(point);
		p.setDestination(new Point(10,10));
		model.setP(p);
		model.getP().moveNorth();
		
		assertEquals(model.getP().getPosition().getY() , point.getY(), 0);
		p.setPosition(point);
		model.getP().move();
		
		assertEquals(model.getP().getPosition(), model.getP().getDestination());
		
		assertEquals(model.getP().getHT(), HoldingType.EMPTY);
		model.getP().pickUp(HoldingType.OYSTER);
		assertEquals(model.getP().getHT(), HoldingType.OYSTER);
		
		assertEquals(model.getP().getDirection(), Direction.EAST);
		
		p.setDirection(Direction.SOUTH);
		
		model.getP().updateDirection();
		assertEquals(model.getP().getDirection(), Direction.SOUTH);
		
		model.getP().moveSouth();
		assertTrue(model.getP().getPosition().y >= point.y);
		
		model.getP().moveWest();
		assertTrue(model.getP().getPosition().x <= point.x);
		
		System.out.println(model.getP().getPosition());

		model.getP().setDestination(new Point(7,7));
		model.getP().move();
		System.out.println(model.getP().getPosition());
		assertEquals(model.getP().getPosition(), new Point(10, 7));
		
		model.getP().updateDirection();
		assertEquals(model.getP().getDirection(), Direction.WEST);
		
		model.getP().move();
		model.getP().updateDirection();
		assertEquals(model.getP().getDirection(), Direction.SOUTH);
		
		model.getP().setHT(HoldingType.EMPTY);
		model.getP().pickUp(HoldingType.TUTORIAL_C);
		
		assertEquals(model.getP().getHT(), HoldingType.TUTORIAL_C);
		
		
		model.getP().setHT(HoldingType.EMPTY);
		model.getP().pickUp(HoldingType.TUTORIAL_O);
		
		assertEquals(model.getP().getHT(), HoldingType.TUTORIAL_O);
		
		model.getP().updateSprite();
		assertEquals(model.getP().getDirection(), Direction.SOUTH);
		
		assertEquals(model.getP().getHealth(), 100);
		
		model.getP().setDestination(new Point(15,15));
		model.getP().move();
		assertEquals(model.getP().getPosition(), new Point(7, 14));
		
		model.getP().updateDirection();
		assertEquals(model.getP().getDirection(), Direction.SOUTHEAST);
		
		model.getP().move();
		assertEquals(model.getP().getPosition(), new Point(14, 15));
		
		model.getP().updateDirection();
		assertEquals(model.getP().getDirection(), Direction.EAST);
		
		model.getP().setHealth(10);
		assertEquals(model.getP().getHealth(), 10);
		
		model.getP().move();
		assertEquals(model.getP().getPosition(), new Point(15, 15));
		
		model.getP().updateDirection();
		assertEquals(model.getP().getDirection(), Direction.SOUTH);
		
		model.getP().setPosition(new Point(0,0));
		model.getP().setDestination(new Point(28,28));
		
		model.getP().moveWest();
		assertEquals(model.getP().getPosition(), new Point(28, 0));
		
		model.getP().updateDirection();
		assertEquals(model.getP().getDirection(), Direction.SOUTH);
		
		model.getP().moveNorth();
		assertEquals(model.getP().getPosition(), new Point(28, 28));
		
		model.getP().updateDirection();
		model.getP().setDirection(Direction.NORTHEAST);
		
		assertEquals(model.getP().getDirection(), Direction.NORTHEAST);
		
		model.getP().setPosition(new Point(0,0));
		model.getP().setDestination(new Point(100,100));
		
		model.getP().move();
		assertEquals(model.getP().getPosition(), new Point(7, 100));
		
		model.getP().updateDirection();
		assertEquals(model.getP().getDirection(), Direction.EAST);
		
		model.getP().move();
		assertEquals(model.getP().getPosition(), new Point(14, 100 ));
		
		
		model.getP().moveWest();
		model.getP().updateDirection();
		assertEquals(model.getP().getDirection(), Direction.SOUTH);
		
		model.getP().moveNorth();
		model.getP().updateDirection();
		assertEquals(model.getP().getDirection(), Direction.SOUTH);
		
	}
	@Test
	public void waveTest(){
		Wave w = new Wave(new Point(5,5), null, 2);
		w.setDestination(new Point(4, 5));
		ArrayList<Wave> ww = new ArrayList<Wave>();
		ww.add(w);
		model.setWaves(ww);
		model.getWaves().get(0).move();
		assertEquals(model.getWaves().get(0).getPosition(), w.getDestination() );
	}

	
}
