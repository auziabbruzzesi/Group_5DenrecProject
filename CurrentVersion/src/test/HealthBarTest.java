package test;

import static org.junit.Assert.*;

import org.junit.Test;

import model.HealthBar;

public class HealthBarTest {
	int height = 20;
	double insideHeight = height;
	int width = 10;
	HealthBar HP = new HealthBar(width, height);
	
	@Test
	public void testHealthBar(){
		HP.setHeight(height);
		HP.setWidth(width);
		HP.setInsideHeight(height);
		
		HP.damage(10); // 10 percent 
		assertEquals(HP.getHeight(), 20);
		assertTrue(HP.getInsideHeight() == 18.0);
		
		HP.damage(5); // +5 percent 
		assertTrue(HP.getInsideHeight() == 17.0);
		
		HP.damage(20); // +20 percent (now 35%) 
		assertTrue(HP.getInsideHeight() == 13.0);
		
		HP.damage(15); // +15 percent (now 50%)
		assertTrue(HP.getInsideHeight() == 10.0);
		
//		// TODO: implement powerUps
//		HP.powerUp(10); // power up by 10 percent so health should now = 10+2 = 12.
//		assertTrue(HP.getInsideHeight() == 12);
//		
//		HP.powerUp(15); // (+15%) from 40% health should = 15
//		assertTrue(HP.getInsideHeight() == 15);
//		
//		HP.damage(10); // checking damage after powerup
//		assertTrue(HP.getInsideHeight() == 17.0);
		
 	}
	@Test 
	public void damageTest(){
		HP.damage(10);
		assertEquals(HP.getHeight(), 20);
		assertEquals(HP.getInsideHeight(), 18, 1);
		assertEquals(HP.getHealth(), 90);
		assertEquals(HP.getStartingY(), 2, .5);
	}
	
	
	
	//TODO: write tests for the health bar -- Auzi

}
