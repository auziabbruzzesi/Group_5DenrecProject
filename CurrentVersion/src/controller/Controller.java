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

public class Controller implements MouseListener {
	private static Model m;
	private static View v;
	private boolean pickUpRequest = false;
	private boolean putDownRequest = false;
	button b = new button();
	button player = new button();

	Timer timer = new Timer(0, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			m.getP().updateDirection();
			m.getP().move();
			//System.out.println(
				//	"currentPos: " + m.getP().getCurrentPos() + " Current destination: " + m.getP().getDestination());
			updatePlayerMV();
			v.repaint();

//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e2) {
//				e2.printStackTrace();
//			}

		}
	});

	/**
	 * Constructor
	 */

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

			j.addMouseListener(this);

		}

		for (BeachObject bo : m.getBeachObject().values()) {
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
				l.addMouseListener(this);
			} else if (bo.getH() == HoldingType.OYSTER) {
				l.setHoldingType(HoldingType.OYSTER);
				l.setText("o");
				l.setBackground(Color.blue);
				l.addMouseListener(this);
			}

			// BEACH OBJECT ADDED TO JPANEL HERE (but created in model)
			v.getJPanel().add(l);
		}

		player.setMargin(new Insets(0, 0, 0, 0));
		player.setBackground(Color.black);
		player.setBounds(Player.startPosition.x, Player.startPosition.y, Player.playerDimensions,
				Player.playerDimensions);
		v.getJPanel().add(player);
	}

	/**
	 * @author EAviles
	 * 
	 *         decides whether or not we've reached our destination TODO: make
	 *         this work with pickup and putdown functionality
	 */
	public void updatePlayerMV() {

		// System.out.println("distance to dest: " +
		// m.getP().getCurrentPos().distance(m.getP().getDestination()));

		if (m.getP().getDestination().distance(m.getP().getCurrentPos()) < 10) {
			timer.stop();
			System.out.println("we've reached our destination");
			
			if (pickUpRequest) {
				// call pickup send b.type
				System.out.println("pickupreq after stopping timer = " + pickUpRequest);
				
				//pickup representation in model: check if we can pickup, if yes: update holding type & return true
				if( m.getP().pickUp(b.getHoldingType()) ){
					//if we were able to pickUp an object
					//view's representation of pickUp
					//note: this seems to work fine, but
						//could possibly get problematic if the player overlaps with the button's position.
						//getComponentAt returns the component on jpanel containing the specified point.
					v.getJPanel().getComponentAt(b.getLocation()).setVisible(false);
				}				
			}//end if(pickup)
			
//			else if(putDownRequest){
//				//do putdown
//				//match b. position (last known position) to collection
//				if(m.getP().putDown(b.getHoldingType() )){
//					//put down
//				}
//				//make view changes
//			}
		}

		// else if we're still moving toward destination
		else {
			player.setLocation(m.getP().getCurrentPos());
			//m.updatePlayerPosition(v.getPlayerPos());

		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		v.setPlayerDest(e.getComponent().getLocation());
		m.getP().setDestination(e.getPoint());
		if (e.getComponent() instanceof button) {
			b = (button) (e.getComponent());
			m.getP().setDestination(b.getLocation());

			if (b.getHoldingType() == HoldingType.BOX) {
				System.out.println("Box button clicked");
				putDownRequest = true;
				System.out.println(this);
			} else {
				pickUpRequest = true;
				//System.out.println("pickuprequest = " + pickUpRequest);
			}

		}
		timer.start();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

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
