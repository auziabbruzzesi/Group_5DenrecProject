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

public class Controller implements MouseListener{
	private static Model m;
	private static View v;
	private boolean pickUpRequest = false;
	button b = new button();
	// private gameEndStatus winLose = gameEndStatus.EMPTY;
	// private int minigame = 0;
	

  // hi

	public void updatePlayerMV() {
		
		
		
		//if we've arrived
		if(m.getP().getCurrentPos() == m.getP().getDestination() ){
			if(pickUpRequest){
				//call pickup send b.type
				m.getP().pickUp( b.getHoldingType() );
			}
			//update
		}
		
		//else if we're still moving toward destination		
		else{
			m.updatePlayerPosition(v.getPlayerPos());
		}
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


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
	
		//change destination to the location of screen that user just clicked
		v.setPlayerDest(e.getComponent().getLocation());
		//update direction based on the new destination
		//v.updatePlayerDir();

		//move player 1 tic closer to destination
		//v.movePlayer();
		
		if(e.getComponent() instanceof button){
			System.out.println("button listener registered a click");
			b = (button)( e.getComponent() );
			
			if(b.getHoldingType() == HoldingType.BOX){
				System.out.println("Box button clicked");
				//call putdown or create putdown request
				System.out.println(this);
			}
			else{
				pickUpRequest = true;
			}
		}
	}
			
			
//			else if( m.getP().pickUp( b.getHoldingType() ) ){
//					m.getBeachObject().remove(b.getLocationOnScreen());
//					v.getJPanel().remove(e.getComponent());
//					v.repaint();
//				}
			
			
			//updatePlayerMV(b.getLocationOnScreen());
		
	 

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
