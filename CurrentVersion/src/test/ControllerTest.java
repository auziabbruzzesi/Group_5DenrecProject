package test;

import org.junit.Test;

import controller.Controller;
import model.Model;
import view.View;

public class ControllerTest {
	
	Model model = new Model();
	View view = new View();
	Controller controller = new Controller(model, view);
	
	
	@Test 
	public void updatePlayerMVTest(){
		
	}
	
	// belongs in model
	@Test 
	public void putDownTest(){
		
	}
	
	// why is this in controller?
	@Test
	public void waveMoveTest(){
		
	}
	
	// takes in wave, seems like it belongs in wave
	@Test 
	public void determineDamageTest(){
		
		
	}
	
	

	
}
