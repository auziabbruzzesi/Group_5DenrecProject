package test;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Direction;
import model.HoldingType;
import model.Player;

public class PlayerTest {

	// Point start = new Point(100,100);
	// Player player = new Player(start);

	Point start = new Point(100, 100);
	Point destination = new Point(107, 100);
	Player player = new Player(start);
	int velocity = 0;
	// player.setH(HoldingType.EMPTY);
	// player.setHealth(100);

	@Test
	public void pickUptest() {

		// player picks up concrete
		player.pickUp(HoldingType.CONCRETE);
		equals(player.pickUp(HoldingType.CONCRETE));
		assertTrue(player.getH() == HoldingType.CONCRETE);

		// set holding type to empty, player picks up oyster
		player.setH(HoldingType.EMPTY);
		player.pickUp(HoldingType.OYSTER);
		assertTrue(player.getH() != HoldingType.CONCRETE);
		assertTrue(player.getH() == HoldingType.OYSTER);

	}

	@Test
	public void moveTest() {
		player.getCurrentPos();
		player.setDestination(destination);

		player.move();

		// checking to see if move was incremented any towards destination.
		// every time move() is called player moves CLOSER to destination

		assertEquals(player.getCurrentPos(), player.getDestination());

	}
	@Test
	public void updateDirectionTest(){
		
		player.setCurrentPos(start);
		
		
		// checks north 
		player.setDestination(new Point(100, 50));
		player.updateDirection();
		assertEquals(player.getDirection(), Direction.NORTH);
		
		// checks south
		player.setDestination(new Point(100, 150));
		player.updateDirection();
		assertEquals(player.getDirection(), Direction.SOUTH);
		
		// checks east 
		player.setDestination(new Point(150, 100));
		player.updateDirection();
		assertEquals(player.getDirection(), Direction.EAST);
		
		// checks west
		player.setDestination(new Point(50, 100));
		player.updateDirection();
		assertEquals(player.getDirection(), Direction.WEST);
		
		//checks northeast
		player.setDestination(new Point(150, 50));
		player.updateDirection();
		assertEquals(player.getDirection(), Direction.NORTHEAST);
		
		//checks northwest
		player.setDestination(new Point(50, 50));
		player.updateDirection();
		assertEquals(player.getDirection(), Direction.NORTHWEST);
		
		// checks southeast
		player.setDestination(new Point(150, 150));
		player.updateDirection();
		assertEquals(player.getDirection(), Direction.SOUTHEAST);
		
		//checks southwest
		player.setDestination(new Point(50, 150));
		player.updateDirection();
		assertEquals(player.getDirection(), Direction.SOUTHWEST);
	}
	
	
}
