package test;

import static org.junit.Assert.assertEquals;

import java.awt.Point;

import org.junit.Test;

import model.Shoreline;

public class ShorelineTest {

	Shoreline sl = new Shoreline(new Point(0, 0), new Point(5, 5));

	@Test
	public void getTotalDecrementTest() {
		sl.setTotalDecrement(10);
		assertEquals(sl.getTotalDecrement(), 10);
	}

	@Test
	public void getLoosingCoordinateTest() {
		sl.setLoosingCoordinate(10);
		assertEquals(sl.getLoosingCoordinate(), 10);
	}

	@Test
	public void updateTotalDecrementtest() {

		sl.updateTotalDecrement(10);
		assertEquals(sl.getTotalDecrement(), -10);
	}

	@Test
	public void slopetest() {
		sl.setSlope(1.0);
		assertEquals(sl.getSlope(), 1.0, 0.0f);
	}

}
