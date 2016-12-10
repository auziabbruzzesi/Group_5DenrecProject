package test;

import static org.junit.Assert.*;

import java.awt.Point;

import javax.swing.Icon;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;
import controller.status;
import model.Box;
import model.HealthBar;
import model.HoldingType;
import model.Model;
import model.Player;
import model.Wave;
import view.View;

public class ControllerTest {

	private static final Icon NULL = null;



	// takes in wave, seems like it belongs in wave
	@Test
	public void determineDamageTest() {
		Model model = new Model();
		View view = new View();
		Controller contoller = new Controller(model, view);

		Box b = new Box(new Point(0, 0), 0);
		HoldingType ht = null;
		Wave w1 = new Wave(new Point(0, 0), NULL, 10);
		b.setContains(ht.EMPTY);

		assertEquals(contoller.determineDamage(w1), 5);
		b.setContains(ht.OYSTER);
		System.out.println("box contains: " + b.getContains());

		b.setContains(ht.CONCRETE);

		b.setContains(ht.TUTORIAL_C);

		b.setContains(ht.TUTORIAL_O);

	}

}
