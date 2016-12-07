package test;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Shoreline;

public class ShorelineTest {

	Shoreline sl = new Shoreline(new Point(0,0), new Point(5,5));
	

	@Test
	public void updateTotalDecrementtest() {
		sl.updateTotalDecrement(10);
		assertEquals(sl.getTotalDecrement(), -10);
	}

}
