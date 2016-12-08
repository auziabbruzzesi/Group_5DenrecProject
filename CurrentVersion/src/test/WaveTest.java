package test;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.TutorialWave;
import model.Wave;

public class WaveTest {
	Point start = new Point(10,10);
	Wave wave = new Wave(new Point(10,10), null, 0);
	
	Point destination = new Point(5, 10); // moving west
	
	

	@Test
	public void moveTest() {
		wave.setDestination(destination);
		wave.move();
		// checking to see if waves xcoord has decremented, indicates wave has moved west
		assertTrue(wave.getPosition().getX() <= start.getX()); 
		
		// checks to see if waves ycoord is the same as the start position, waves only move horizontally.
		assertTrue(wave.getPosition().getY() == start.getY());
		

	}
	@Test 
	public void resetTest(){
		wave.setPosition(start);
		wave.reset(start, new Point(20,20));
		assertEquals(wave.getPosition(), start);
		assertEquals(wave.getDestination(), new Point(20,20));
		assertTrue(wave.getVelocity() <= 5);
		
		
	}
	// how to test random values? 
	@Test
	public void resetVelocityTest(){
		wave.setVelocity(1);
		wave.resetVelocity();
		assertTrue(wave.getVelocity() <= 5);
		
	}
	@Test
	public void tutorialWave(){
		TutorialWave tw = new TutorialWave(new Point(1,1), null, 0, 0);
		tw.getAnimationNumber();
		
		assertEquals(tw.getAnimationNumber(), 0);
	}

}

