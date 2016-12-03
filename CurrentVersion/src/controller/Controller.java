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


/*
 * Timers (2)
 *   wTimer - handles waves
 *   pTimer - handles player movement
 */

	Timer wTimer = new Timer(30, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			View.viewHeight = v.getContentPane().getHeight();
			View.viewWidth = v.getContentPane().getWidth();
			v.getJPanel().setSize(v.getContentPane().getSize());
			
			m.gameDi = v.getContentPane().getSize();
			
		//	m.initSprites();
			//v.updateViewObjs();
			//m.initSprites();
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
		}
	});

	/**
	 * Constructor
	 */


	public Controller(Model m, View v) {
		this.m = m; //initialization occurs in model's constructor
		this.v = v; //init occurs in view's constructor

		initViewBtnListeners();
	}



/*
 * General functions
 */
	public void updatePlayerMV() {

		if (m.getP().getDestination().distance(m.getP().getPosition()) < 10) {
			
			pTimer.stop();
//			 System.out.println("we've reached our destination");

			//if player clicked on oyster or concrete
			if (pickUpRequest) {

				System.out.println("topickup = "+objToPickUp);
//				System.out.println("player at = "+m.getP().getPosition());
				
				//check that we can pick up the desired object
				if (m.getP().pickUp(objToPickUpHT)) {
					//pick it up
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
	}

	/**
	 * 
	 * @return String type
	 */
	public String putDown() {
		String type = "";
		// check player is holding something
		if (m.getP().getHT() != HoldingType.EMPTY) {
			HoldingType boxContains = m.getBoxes().get(putDownBox).getContains();

			// check type of obj matches box type, or box is empty
			if (m.getP().getHT() == boxContains || boxContains == HoldingType.EMPTY) {

				// check box not full
				if (!(m.getBoxes().get(putDownBox).isfull())) {

					// set box type in model if this is 1st item placed in box
					if (boxContains == HoldingType.EMPTY) {
						m.getBoxes().get(putDownBox).setContains(m.getP().getHT());
					}

					m.getBoxes().get(putDownBox).incrementCount();
					
//					System.out.println("\n\nbox count = " + m.getBoxes().get(putDownBox).getCount() + " isfull = "+ m.getBoxes().get(putDownBox).isfull());
					m.getP().setHT(HoldingType.EMPTY);
				}
			}
		 
		else {
			System.out.println("Can't put that down in this box. It is a " + m.getBoxes().get(putDownBox).getContains() +" box and you are holding "+ m.getP().getHT());
		}
	}
		type = m.getBoxes().get(putDownBox).getContains().name() + " " + m.getBoxes().get(putDownBox).getCount();
		return type;
	}
	
	public void moveWave() {
		int a = 0;
		for (Wave w : m.getWaves()) {
			//if we haven't reached destination
			if ( w.getPosition().x > w.getDestination().x ) {
				w.move();		
			}
			
			//else, wave has reached the shore. Shoreline, Estuary Health must be updated and Wave must be reset.
			else {
											
				int shoreDamage = determineDamage(w);				

				int healthDamage = shoreDamage;//this is redundant in terms of code, but makes it more obvious what's going on. Leaving for improved readability.				
				m.updateShoreLine(shoreDamage);
//				System.out.println("shoreline updated (model). shoreline = "+ m.getShoreLine());
				
				m.getHB().damage(healthDamage);
				


				
				m.resetWave(a);

				checkGameStatus();//we call this here bc shoreline was updated (above)
			}
			a++;	
		}
		m.updateWavesDestinations();
	}


	private int determineDamage(Wave w) {
		int decrement = 0;
		Box b = null;
		Point p;
		//TODO: this is repeated code with what is in model
		//figure out which box wave crashed on
//		if (i < m.getBoxes().size()) {
//			p = new Point(Box.boxX, i * Box.boxToBoxInterval + 20);
//		} else {
//			p = new Point(Box.boxX, (m.getNumBoxes() - 1) * Box.boxToBoxInterval + 20);
//		}
//		b = m.getBoxes().get(w.getPosition());
		
		for( Box k : m.getBoxes().values() ){
			if(k.getIndex() == w.getIndex()){
				b = k;
			}
		}
		if(b == null){
			System.out.println("error in Controller: determineDamage: no box with index matching wave");
		}
		
		//set decrement based box contents
		switch (b.getContains()) {
		case EMPTY:
			decrement = 5;
			
			b.setCount(b.getCount() -1);
			b.setObjIcon(m.concreteImages[b.getCount() - 1]);
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
//		System.out.println("box with index "+b.getIndex() + " decremented");
		return decrement;
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
			
			if ( ( (View.button) ( e.getComponent() ) ).getType() == HoldingType.BOX) {
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


	private void checkGameStatus() {
//		System.out.println("in Controller->check game status function \nShoreline = "+ m.getShoreLine() + "\nmin shoreline = "+ m.getminShoreLine());
		String endMessage = "";
		
		if(m.getShoreLine() <= m.getminShoreLine()){
			gameStatus = status.LOSE_SHORE;
			endMessage = "You Lose :( \n Too much of the Estuary eroded away";
		}
		else if( m.allBoxesFull() ){
			if( m.boxesCorrect() ){
				gameStatus = status.WIN;
				endMessage = "You Win! :D \n You created enough protection for the Estuary.";
			}
			else{
				gameStatus = status.LOSE_BOXES;
				endMessage = "You Lose :( \n The Estuary wasn't protected well enough. Try adding more Oyster Gabions!";
			}
		}
		
		if(gameStatus != status.IN_PROGRESS){
			wTimer.stop();
			pTimer.stop();
			v.gameEnd(endMessage);
		}
		
	}
	
/*
 * Initialization functions
 */
	private void initViewBtnListeners() {
		for(button b : v.gameObjBtns){
			b.addMouseListener(this);
		}		
	}

/*
 * Setters & Getters
 */
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
	
/*
 * Unused mouse listener functions (these are required to implement MouseListener, even if they're not used)
 */
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

}
