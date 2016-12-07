package test;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.Controller;
import model.Box;
import model.HoldingType;
import model.Model;
import model.Player;
import model.Wave;
import view.View;

public class ControllerTest {
//	
//	Model model  = new Model();
//	View view = new View();
//	Controller controller = new Controller(model, view);
//	@Before
//	public void setUp(){
//		 model = new Model();
//		 view = new View();
//		 controller = new Controller(model, view);
		
	
//		Point gc = new Point(100, 5);
//		Point cc = new Point(100, 10);
//		Wave w1 = new Wave(gc, null, 0);
//		Wave w2 = new Wave(cc, null, 0);
//		ArrayList<Wave> waves = new ArrayList<Wave>();
//		waves.add(w1);
//		waves.add(w2);
//		
//		Box g = new Box();
//		Box c = new Box();
//		
//		g.setPosition(gc);
//		c.setPosition(cc);
//		
//		LinkedHashMap<Point, Box> boxes = new LinkedHashMap<Point, Box>();
//		
//		
//		boxes.put(gc, g);
//		boxes.put(cc, c);
//		
//		assertEquals(controller.getM().getBoxes().get(gc).getPosition(), gc);
//		
	
//	}
	
//	
//	@Test 
//	public void updatePlayerMVTest(){
//		Model model = new Model();
//		View view = new View();
//		Controller controller = new Controller(model, new View());
//	
//		Player p = new Player(new Point(5,5));
//		p.setDestination(new Point(20,20));
//		
//		model.setP(p);
//		
//		
//		
//		controller.updatePlayerMV();
//		assertEquals(model.getP().getDestination(), new Point(20,20));
//		
//		p.pickUp(HoldingType.CONCRETE);
//		
//		controller.updatePlayerMV();
//		// checking if player has picked up the concrete 
//		assertTrue(model.getP().getHT() == HoldingType.CONCRETE);
//		
//		
//		assertEquals(model.getP().getPosition(),new Point(20,20)); 
//		// since player has picked something up, the view should get rid of the component, checks if is visible is false
//		assertTrue(view.getJPanel().getComponentAt(p.getPosition()).isVisible());
//		assertFalse(controller.isPickUpRequest());
//		
//		// player puts something down, so putdown request should be false
//		controller.putDown();
//		assertFalse(controller.isPutDownRequest());
//		
//		
//		
//	}
//	
//	// belongs in model
//	@Test 
//	public void putDownTest(){
//		Model model = new Model();
//		View view = new View();
//		Controller controller = new Controller(model, new View());
//		Point point = new Point(5,5);
//		Player p = new Player(point);
//		
////		// plpayer picks up oyster 
////		p.pickUp(HoldingType.OYSTER);
////		System.out.println("CONTROLLER DOT PUT DOWN: "+ controller.putDown());
////		//assertEquals(controller.putDown(), model.getBoxes().get(point).getContains().name());
////		
//		
//	}
//	
//	// why is this in controller?
//	@Test
//	public void waveMoveTest(){
//		
//	}
//	
	// takes in wave, seems like it belongs in wave
	@Test 
	public void determineDamageTest(){
		Model model  = new Model();
		View view = new View();
		Controller controller = new Controller(model, view);
		controller.setM(model);
		controller.setV(view);
		
	Point gc = new Point(100, 5);
	Point cc = new Point(100, 10);
	Wave w1 = new Wave(gc, null, 1);
	Wave w2 = new Wave(cc, null, 1);
	ArrayList<Wave> waves = new ArrayList<Wave>();
	waves.add(w1);
	waves.add(w2);
	w1.setIndex(0);
	w1.setIndex(1);
	model.setWaves(waves);
	
	Box g = new Box();
	Box c = new Box();
	
	
	g.setPosition(gc);
	c.setPosition(cc);
	g.setIndex(0);
	c.setIndex(1);
	
	
	LinkedHashMap<Point, Box> boxes = new LinkedHashMap<Point, Box>();
	
	
	boxes.put(gc, g);
	boxes.put(cc, c);
	model.setBoxes(boxes);
	
	g.setCapacity(100);
	g.setCount(100);
	g.setContains(HoldingType.OYSTER);
	c.setContains(HoldingType.CONCRETE);
	//controller.determineDamage(w1);
	System.out.println("HELLO: " + controller.getM().getBoxes().get(gc).getIndex());
	System.out.println("HELLO CONCRETE: " + controller.getM().getBoxes().get(cc).getIndex());
	assertEquals(controller.getM().getBoxes().get(gc).getIndex(), 0);
	assertEquals(controller.getM().getBoxes().get(cc).getIndex(), 1);
	
	
	//assertEquals(controller.determineDamage(w2), 1);
	//System.out.println(controller.getM().getBoxes().get(cc));
		
	}
	
	

	
}
