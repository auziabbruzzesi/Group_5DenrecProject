package test;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Wave;

public class WaveTest {
	Point start = new Point(10,10);
	Wave wave = new Wave(new Point(10,10));
	
	Point destination = new Point(5, 10); // moving west
	
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
	public void moveTest() {
		wave.setDestination(destination);
		wave.move();
		// checking to see if waves xcoord has decremented, indicates wave has moved west
		assertTrue(wave.getCurrentPos().getX() <= start.getX()); 
		
		// checks to see if waves ycoord is the same as the start position, waves only move horizontally.
		assertTrue(wave.getCurrentPos().getY() == start.getY());
		

	}

}
