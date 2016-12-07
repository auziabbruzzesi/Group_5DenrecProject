//package test;
//
//import static org.junit.Assert.*;
//
//import java.awt.Point;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import controller.Controller;
//import model.HoldingType;
//import model.Model;
//import model.Player;
//import view.View;
//
//public class ControllerTest {
//	
//	
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
//	// takes in wave, seems like it belongs in wave
//	@Test 
//	public void determineDamageTest(){
//		
//		
//	}
//	
//	
//
//	
//}
