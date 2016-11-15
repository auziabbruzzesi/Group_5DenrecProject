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
import model.HoldingType;
import model.Model;
import model.Player;
import model.Wave;

public class ModelTest {

	Model model = new Model();
	Player player = new Player(new Point(5,5));
	Point playerPos = new Point(5,5);
	BeachObject concrete = new BeachObject(new Point(2,4), HoldingType.CONCRETE);
	HashMap<Point, BeachObject> beachObjHM = new HashMap<Point, BeachObject>();
	HashMap<Point, Box> boxes = new HashMap<Point, Box>();
	private ArrayList<Wave> waves = new ArrayList<Wave>();
	private int numWaves = 6;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void playerOverlapTest() {
		model.setP(player);
		
		
		model.checkPlayerOverlap(playerPos);
		assertFalse(model.checkPlayerOverlap(playerPos));
		
		model.checkPlayerOverlap(concrete.getCurrentPos());
		
		// point (2,4) is too close to player current position to create 
		assertFalse(model.checkPlayerOverlap(concrete.getCurrentPos()));
		Point farPoint = new Point(50,50); // point that should return true
		assertTrue(model.checkPlayerOverlap(farPoint));
		
		
	}
	@Test
	public void boxOverlapTest(){
		Box testBox = new Box();
		testBox.setPosition(playerPos);
		boxes.put(new Point(30,30), testBox); // box at players position
		model.setP(player);
		
		model.setBoxes(boxes);
		
		model.checkBoxOverlap(playerPos);
		assertFalse(model.checkBoxOverlap(playerPos));
		
		
		Point farPoint = new Point(100,1000); 
		
		model.checkBoxOverlap(farPoint);
		assertTrue(model.checkBoxOverlap(farPoint));
		
	}
	@Test
	public void checkBeachObjectOverlap(){
		model.setBeachObject(beachObjHM);
		beachObjHM.put(playerPos, concrete);
		model.checkBeachObjectOverlap(playerPos);
		assertFalse(model.checkBeachObjectOverlap(playerPos));
		
		Point farPoint = new Point(200,500);
		model.checkBeachObjectOverlap(farPoint);
		assertTrue(model.checkBeachObjectOverlap(farPoint));
	}
	
	
	// TODO: figure out why this test is failing!!!!!!!!!!!!!!!!
	@Test
	public void resetWaveTest(){
		//model.resetWave(2);
		Wave w1 = new Wave(new Point(3,1));
		Wave w2 = new Wave(new Point (1,1));
		model.resetWave(2);

		waves.add(w1);
		waves.add(w2);
	//	waves.add(new Wave(new Point(3,3)));
		//model.resetWave(2);
		
		assertEquals(w1.getCurrentPos().x, w2.getCurrentPos().x);
		
				
		
	}
	// TODO: updateShorelineTest
	@Test 
	public void updateShorelineTest(){
		
	}
	
	// TODO: allBoxesFullTest
	@Test 
	public void allBoxesFullTest(){
		
	}
	
	// TODO: boxesCorrectTest
	@Test
	public void boxesCorrectTest(){
		
	}
	
	

}
