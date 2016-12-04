package test;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.BeachObject;
import model.Box;
import model.GameObject;
import model.HoldingType;
import model.Model;
import model.Player;
import model.Shoreline;
import model.Wave;

public class ModelTest {

	Model model = new Model();
	Player player = new Player(new Point(5, 5));
	Point playerPos = new Point(5, 5);
	BeachObject concrete = new BeachObject(new Point(2, 4), HoldingType.CONCRETE, null);
	HashMap<Point, BeachObject> beachObjHM = new HashMap<Point, BeachObject>();
	HashMap<Point, Box> boxes = new HashMap<Point, Box>();
	ArrayList<Wave> waves = new ArrayList<Wave>();
	int numWaves = 6;
	int shoreLine = 840;
	// 
	Box b1 = new Box();  
	Box b2 = new Box();
	Box b3 = new Box();
	Box b4 = new Box();


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
	
}
