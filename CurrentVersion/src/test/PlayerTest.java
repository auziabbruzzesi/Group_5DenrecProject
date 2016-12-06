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


	Point start = new Point(100, 100);
	Point destination = new Point(107, 100);
	Player player = new Player(start);
	int velocity = 0;
	Point moveStart = new Point(10,10);
	// player.setH(HoldingType.EMPTY);
	// player.setHealth(100);

	@Test
	public void pickUptest() {

		// player picks up concrete
		player.pickUp(HoldingType.CONCRETE);
		equals(player.pickUp(HoldingType.CONCRETE));
		assertTrue(player.getHT() == HoldingType.CONCRETE);

		// set holding type to empty, player picks up oyster
		player.setHT(HoldingType.EMPTY);
		player.pickUp(HoldingType.OYSTER);
		assertTrue(player.getHT() != HoldingType.CONCRETE);
		assertTrue(player.getHT() == HoldingType.OYSTER);

	}
	// does not pass. could be reason why we are getting the mysterious bug.
 @Test 
	public void moveNorthTest(){
		player.setPosition(moveStart);
		player.setDestination(new Point(10, 5));
		
		player.moveNorth();
		assertEquals(player.getPosition(), player.getDestination());
	}
	@Test 
	public void moveWestTest(){
		player.setPosition(moveStart);
		player.setDestination(new Point(5, 10));
	}
	@Test
	public void moveEastTest(){
		player.setPosition(moveStart);
		player.setDestination(new Point (15, 10));
		
		player.moveEast();
		
		assertEquals(player.getPosition(), player.getDestination());
		
		
	}
	@Test 
	public void moveSouthTest(){
		player.setPosition(moveStart);
		player.setDestination(new Point(10, 15));
		
		player.moveSouth();
		
		assertEquals(player.getPosition(), player.getDestination());
	}

	@Test
	public void moveTest() {
		player.getPosition();
		player.setDestination(destination);

		player.move();

		// checking to see if move was incremented any towards destination.
		// every time move() is called player moves CLOSER to destination

		assertEquals(player.getPosition(), player.getDestination());

	}
	@Test
	public void updateDirectionTest(){
		
		player.setPosition(start);
		
		
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
	@Test 
	public void findIndexTest(){
		// player default direction is east, so should return 2 because 3rd image in array is crab at east direction
		player.findIndex();
		assertEquals(player.findIndex(), 2);
		
		// switch direction to north,so corresponding index should be 0
		player.setDirection(Direction.NORTH);
		player.findIndex();
		assertEquals(player.findIndex(), 0);
		
		player.setDirection(Direction.SOUTH);
		player.findIndex();
		assertEquals(player.findIndex(), 1);
		
		player.setDirection(Direction.WEST);
		player.findIndex();
		assertEquals(player.findIndex(), 3);
	}
	
	
}
