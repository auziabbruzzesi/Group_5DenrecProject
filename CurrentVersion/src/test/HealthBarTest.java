package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import model.HealthBar;

public class HealthBarTest {
	int height = 20;
	double insideHeight = height;
	int width = 10;
	HealthBar HP = new HealthBar(width, height);

	@Test
	public void testHealthBar() {
		HP.setHeight(height);
		HP.setWidth(width);
		HP.setInsideHeight(height);

		HP.damage(10); // 10 percent
		assertEquals(HP.getHealth(), 90);

		assertEquals(HP.getHeight(), 20);
		assertTrue(HP.getInsideHeight() == 18.0);

		HP.damage(5); // +5 percent
		assertTrue(HP.getInsideHeight() == 17.0);

		HP.damage(20); // +20 percent (now 35%)
		assertTrue(HP.getInsideHeight() == 13.0);

		HP.damage(15); // +15 percent (now 50%)
		assertTrue(HP.getInsideHeight() == 10.0);
		HP.reset();
		assertEquals(HP.getHealth(), 100);
		
	}

	// TODO: write tests for the health bar -- Auzi

}
