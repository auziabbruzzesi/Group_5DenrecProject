//move set color for bo's to view

package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.Timer;

import controller.Controller.button;
import model.BeachObject;
import model.Box;
import model.HoldingType;
import model.Model;
import model.Player;
import view.View;

public class Controller {
	private static Model m;
	private static View v;
	// private gameEndStatus winLose = gameEndStatus.EMPTY;
	// private int minigame = 0;
	MouseEventListener concreteListener = new MouseEventListener();
	MouseEventListener oysterListener = new MouseEventListener();
	MouseEventListener boxListener = new MouseEventListener();

  // hi

	public static void updatePlayerMV(Point d) {
		v.setPlayerDest(d);
		v.updatePlayerDir();
		m.updatePlayerPosition(v.getPlayerPos());
	}

	public Controller(Model m, View v) {
		this.m = m;
		this.v = v;

		v.setPlayerPos(m.getP().getCurrentPos());
		v.setPlayerDims(Player.playerDimensions);

		for (Box b : m.getBoxes()) {
			button j = new button();
			j.setMargin(new Insets(0, 0, 0, 0));
			j.setBounds(b.getPosition().x, b.getPosition().y, Box.boxDimensions, Box.boxDimensions);
			j.setSize(new Dimension(Box.boxDimensions, Box.boxDimensions));
			j.setLocation(b.getPosition());
			j.setHoldingType(HoldingType.BOX);

			v.getJPanel().setLayout(null);

			// BOXES ARE ADDED TO JPANEL HERE (but created in model)
			v.getJPanel().add(j);

			j.addMouseListener(boxListener);

		}

		for (BeachObject bo : m.getBeachObject()) {
			button l = new button();
			// The line of code below fixes the issue of displaying c/o text in
			// windows
			// note, if this starts giving trouble later, try:
			// l.setBorder(null);
			l.setMargin(new Insets(0, 0, 0, 0));

			// BEACH OBJECT BOUNDS/DIMENSIONS ARE SET HERE
			l.setBounds(bo.getCurrentPos().x, bo.getCurrentPos().y, BeachObject.beachObjDimensions,
					BeachObject.beachObjDimensions);

			if (bo.getH() == HoldingType.CONCRETE) {
				l.setHoldingType(HoldingType.CONCRETE);
				l.setText("c");
				l.setBackground(Color.gray);
				l.addMouseListener(concreteListener);
			} else if (bo.getH() == HoldingType.OYSTER) {
				l.setHoldingType(HoldingType.OYSTER);
				l.setText("o");
				l.setBackground(Color.blue);
				l.addMouseListener(oysterListener);
			}

			// BEACH OBJECT ADDED TO JPANEL HERE (but created in model)
			v.getJPanel().add(l);
		}
	}


	public class button extends JButton {
		private HoldingType h = HoldingType.EMPTY;

		public HoldingType getHoldingType() {
			return this.h;
		}

		public void setHoldingType(HoldingType h) {
			this.h = h;
		}

		public button() {
			this.setPreferredSize(new Dimension(20, 20));
		}
	}

}

// if(m.getBeachObject().contains(e.getPoint())){
// System.out.println("about to pick something up");
// m.getP().setH(m.getBeachObject().get(destination).getH());
// m.getBeachObject().remove(destination);
// v.repaint();
// } Point destination = e.getPoint();
//System.out.println("Mouse pressed.");
//
//// if user clicked a button, we need to move as close to that button as we can
//// (i.e. until we collide), and then call pickup or putdown
//if(e.getComponent()instanceof button){System.out.println("A button was clicked");}
//
//// else, we are just moving:
//else{
//// Set direction & destination in view
//updatePlayerMV(destination);}
