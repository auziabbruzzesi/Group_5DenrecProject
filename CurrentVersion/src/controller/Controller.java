//move set color for bo's to view

package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.BeachObject;
import model.Box;
import model.HoldingType;
import model.Model;
import model.Player;
import model.Wave;
import view.View;
import view.View.button;

public class Controller implements MouseListener {
	private status gameStatus = status.IN_PROGRESS;
	
	private static Model m;
	private static View v;
	private boolean pickUpRequest = false;
	private boolean putDownRequest = false;

	private Point objToPickUp = new Point();
	private HoldingType objToPickUpHT = null;
	private Point putDownBox = new Point();

	int i = 0;

	

	Timer wTimer = new Timer(30, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			moveWave();
			v.updateViewObjs();
			v.repaint();
			checkGameStatus();
		}
	});

	Timer pTimer = new Timer(10, new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			m.getP().updateDirection();
			m.updatePlayerSprite();
//			player.setBorder(BorderFactory.createEmptyBorder());
			
			m.getP().move();
			updatePlayerMV();
//			v.repaint();
		}
	});

	/**
	 * Constructor
	 */


	public Controller(Model m, View v) {
		this.m = m; //initialization occurs in model's constructor
		this.v = v; //init occurs in view's constructor

		initViewBtnListeners();

		// Healthbar
		v.getHealthBar().setBounds(0, 0, m.getHB().getWidth(), m.getHB().getHeight());
		v.getHealthBar().healthHeight = m.getHB().getInsideHeight();
		v.getHealthBar().startingY = m.getHB().getStartingY();
	}

	private void initViewBtnListeners() {
		for(button b : v.gameObjBtns){
			b.addMouseListener(this);
		}
		
	}

	/**
	 * @author EAviles
	 * 
	 *         decides whether or not we've reached our destination TODO: make
	 *         this work with pickup and putdown functionality
	 */
	public void updatePlayerMV() {

		if (m.getP().getDestination().distance(m.getP().getPosition()) < 10) {
			pTimer.stop();

//			 System.out.println("we've reached our destination");

			if (pickUpRequest) {

//				System.out.println("topickup = "+objToPickUp);
//				System.out.println("player at = "+m.getP().getPosition());
				
				if (m.getP().pickUp(objToPickUpHT)) {
//					System.out.println("component = "+v.getJPanel().getComponentAt(objToPickUp));
					v.getJPanel().getComponentAt(objToPickUp).setVisible(false);//TODO: fix
				}
				m.updatePlayerSprite();
				pickUpRequest = false;
			} // end if(pickup)

			else if (putDownRequest) {
				putDown();
				
				putDownRequest = false;
			}
		} // end if(pickup)
			// else if we're still moving toward destination
//		else {
//			player.setLocation(m.getP().getPosition());
//		}
	}

	/**
	 * 
	 * @return String type
	 */
	public String putDown() {
		String type = "";
		// check player is holding something
		if (m.getP().getH() != HoldingType.EMPTY) {
			HoldingType boxContains = m.getBoxes().get(putDownBox).getContains();

			// check type of obj matches box type, or box is empty
			if (m.getP().getH() == boxContains || boxContains == HoldingType.EMPTY) {

				// check box not full
				if (!(m.getBoxes().get(putDownBox).isfull())) {

					// set box type in model if this is 1st item placed in box
					if (boxContains == HoldingType.EMPTY) {
						m.getBoxes().get(putDownBox).setContains(m.getP().getH());
					}

					m.getBoxes().get(putDownBox).incrementCount();
					
					System.out.println("\n\nbox count = " + m.getBoxes().get(putDownBox).getCount() + " isfull = "+ m.getBoxes().get(putDownBox).isfull());
					m.getP().setH(HoldingType.EMPTY);
				}
			}
		} 
		else {
			System.out.println("can't put that down in this box");
		}
//
		type = m.getBoxes().get(putDownBox).getContains().name() + " " + m.getBoxes().get(putDownBox).getCount();
		return type;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		//NOTE: the line of code below fixes the box pickup bug we had 11/12-11/13. Do not remove.
		if(pickUpRequest){
			pickUpRequest = false;
		}
		
		m.getP().setDestination(e.getPoint());

		// if a button was clicked
		if (e.getComponent() instanceof button) {
			m.getP().setDestination( ((View.button) (e.getComponent())).getLocationOnScreen() );
			
			//if pickup = true, and btn was clicked, then pickup = false.
			if ( ( (View.button) ( e.getComponent() ) ).getHoldingType() == HoldingType.BOX) {
				putDownRequest = true;
				putDownBox = e.getComponent().getLocation();
			} else {
				pickUpRequest = true;
				objToPickUp = e.getComponent().getLocation();
				objToPickUpHT = ( ( View.button )( e.getComponent() ) ).getHoldingType();
			}
		}

		pTimer.start();
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


	
	public void moveWave() {
		int a = 0;
		for (Wave w : m.getWaves()) {
			//if we haven't reached destination
			if ( w.getPosition().x > w.getDestination().x ) {

				w.move(); // move model's version of wave
				
			} else {
											
				int shoreDamage = determineDamage(w, i);				

				int healthDamage = shoreDamage;//this is redundant in terms of code, but makes it more obvious what's going on in the code. may delete later, but keeping for now.				
				m.updateShoreLine(shoreDamage);
				v.updateShoreline(shoreDamage);

				m.getHB().damage(healthDamage);	
				//v.getHealthBar().damage(healthDamage);
				v.getHealthBar().setHealthHeight(m.getHB().getInsideHeight());
				v.getHealthBar().startingY = m.getHB().getStartingY();
				
				m.resetWave(a);

				checkGameStatus();//we call this here bc shoreline was updated (above)
			
			}
			a++;	
		}
		m.updateWavesDestinations();
	}

	private void checkGameStatus() {
//		System.out.println(gameStatus);
//		System.out.println("in Controller->check game status function \nShoreline = "+ m.getShoreLine() + "\nmin shoreline = "+ m.getminShoreLine());
		if(m.getShoreLine() <= m.getminShoreLine()){
			gameStatus = status.LOSE_SHORE;
//			System.out.println("lose - shoreline receded");
		}
		else if( m.allBoxesFull() ){
			if( m.boxesCorrect() ){
				gameStatus = status.WIN;
//				System.out.println("win! filled the boxes in time, with at least 50% Gabions");
			}
			else{
				gameStatus = status.LOSE_BOXES;
			}
		}
		
		if(gameStatus != status.IN_PROGRESS){
			wTimer.stop();
			pTimer.stop();
			v.gameEnd(gameStatus);
		}
		
	}

	private int determineDamage(Wave w, int i) {
		int decrement = 0;
		Box b;
		Point p;
		//TODO: this is repeated code with what is in model
		//figure out which box wave crashed on
		if (i < m.getBoxes().size()) {
			p = new Point(Box.boxX, i * Box.boxToBoxInterval + 20);
		} else {
			p = new Point(Box.boxX, (m.getNumBoxes() - 1) * Box.boxToBoxInterval + 20);
		}
		b = m.getBoxes().get(p);

		//set decrement based box contents
		switch (b.getContains()) {
		case EMPTY:
			decrement = 5;
			break;
		case OYSTER:
			if(b.isfull()){
				decrement = 1;
			}
			else{
				decrement = 3;
			}
			break;
		case CONCRETE:
			if(b.isfull()){
				decrement = 3;
			}
			else{
				decrement = 4;
			}
			break;
		default:
			System.out.println("Error: Box contains = " + b.getContains());
			break;
		}
//		System.out.println("\nbox contains " + b.getContains() + "\nfull = " + b.isfull() + "\ndecrement = " + decrement);
		return decrement;
	}


//	//inner classes
//	public class CButton extends JButton {
//		private HoldingType h = HoldingType.EMPTY;
//
//		public HoldingType getHoldingType() {
//			return this.h;
//		}
//
//		public void setHoldingType(HoldingType h) {
//			this.h = h;
//		}
//
//		public CButton() {
//			this.setPreferredSize(new Dimension(20, 20));
//		}
//	}

	public status getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(status gameStatus) {
		this.gameStatus = gameStatus;
	}

	public Point getObjToPickUp() {
		return objToPickUp;
	}

	public void setObjToPickUp(Point objToPickUp) {
		this.objToPickUp = objToPickUp;
	}
}
