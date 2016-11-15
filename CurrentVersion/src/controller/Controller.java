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

import controller.Controller.button;
import model.BeachObject;
import model.Box;
import model.HoldingType;
import model.Model;
import model.Player;
import model.Wave;
import view.View;

public class Controller implements MouseListener {

	private status gameStatus = status.IN_PROGRESS;
	
	private static Model m;
	private static View v;
	private boolean pickUpRequest = false;
	private boolean putDownRequest = false;
	button b = new button();
	button player = new button();

	int i = 0;

	// sprite-related variables
	ImageIcon oystIcon;
	ImageIcon concIcon;

	final int numSprites = 6;
	final int startPSprites = 0;
	final int oystSprite = 9;
	final int concSprite = 8;
	int picNum = 0;
	ImageIcon[] pics;// holds all sprites for all characters

	Timer wTimer = new Timer(30, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			moveWave();
			//checkGameStatus();
		}
	});

	Timer pTimer = new Timer(10, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			m.getP().updateDirection();
			player.setIcon(pics[m.getP().getDirection().getRank()]);
			m.getP().move();
			updatePlayerMV();
			v.repaint();
		}
	});

	/**
	 * Constructor
	 */

	public Controller(Model m, View v) {
		this.m = m;
		this.v = v;

		initSprites();
		oystIcon = pics[oystSprite];
		concIcon = pics[concSprite];

		v.setPlayerPos(m.getP().getCurrentPos());
		v.setPlayerDims(Player.playerDimensions);

		// Healthbar
		v.getHealthBar().setBounds(0, 0, m.getHB().getWidth(), m.getHB().getHeight());
		v.getHealthBar().healthHeight = m.getHB().getInsideHeight();
		v.getHealthBar().startingY = m.getHB().getStartingY();

		for (Box b : m.getBoxes().values()) {
			button j = new button();
			j.setMargin(new Insets(0, 0, 0, 0));
			j.setBounds(b.getPosition().x, b.getPosition().y, Box.boxDimensions, Box.boxDimensions);
			j.setSize(new Dimension(Box.boxDimensions, Box.boxDimensions));
			j.setLocation(b.getPosition());
			j.setHoldingType(HoldingType.BOX);

			v.getJPanel().setLayout(null);

			// BOXES ARE ADDED TO JPANEL HERE (but created in model)
			j.setBorder(BorderFactory.createEmptyBorder());
			j.setContentAreaFilled(false);
			j.setIcon(pics[10]);
			v.getJPanel().add(j);
			
			j.addMouseListener(this);

		}

		for (Wave w : m.getWaves()) {

			button k = new button();
			k.setMargin(new Insets(0, 0, 0, 0));
			k.setBounds(w.getCurrentPos().x, w.getCurrentPos().y, Wave.waveWidth, Wave.waveHeight);

//			k.setBackground(Color.pink);
			k.setBorder(BorderFactory.createEmptyBorder());
			k.setContentAreaFilled(false);

			k.setIcon(pics[11]);
			v.addToWaveBtns(k);

			i++;
		}

		i = 0;

		for (BeachObject bo : m.getBeachObject().values()) {
			button s = new button();
			s.setMargin(new Insets(0, 0, 0, 0));

			// BEACH OBJECT BOUNDS/DIMENSIONS ARE SET HERE
			s.setBounds(bo.getCurrentPos().x, bo.getCurrentPos().y, BeachObject.beachObjDimensions,
					BeachObject.beachObjDimensions);

			if (bo.getH() == HoldingType.CONCRETE) {
				s.setHoldingType(HoldingType.CONCRETE);
				//s.setText("c");
//				s.setBackground(Color.gray);
				s.addMouseListener(this);
				
				s.setBorder(BorderFactory.createEmptyBorder());
				s.setContentAreaFilled(false);

				s.setIcon(concIcon);
			} else if (bo.getH() == HoldingType.OYSTER) {
				s.setHoldingType(HoldingType.OYSTER);
				//s.setText("o");
//				s.setBackground(Color.blue);
				s.addMouseListener(this);
				s.setBorder(BorderFactory.createEmptyBorder());
				s.setContentAreaFilled(false);
				s.setIcon(oystIcon);
			}

			// BEACH OBJECT ADDED TO JPANEL HERE (but created in model)
			v.getJPanel().add(s);
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

		if (m.getP().getDestination().distance(m.getP().getCurrentPos()) < 10) {
			pTimer.stop();
			// System.out.println("we've reached our destination");

			if (pickUpRequest) {

				if (m.getP().pickUp(b.getHoldingType())) {
					v.getJPanel().getComponentAt(b.getLocation()).setVisible(false);
				}
				pickUpRequest = false;
			} // end if(pickup)

			else if (putDownRequest) {
				putDown();
				putDownRequest = false;
			}
		} // end if(pickup)
			// else if we're still moving toward destination
		else {
			player.setLocation(m.getP().getCurrentPos());
		}
	}

	/**
	 * 
	 * @return String type
	 */
	public String putDown() {
		String type = "";
		// check player is holding something
		if (m.getP().getH() != HoldingType.EMPTY) {
			HoldingType boxContains = m.getBoxes().get(b.getLocation()).getContains();

			// check type of obj matches box type, or box is empty
			if (m.getP().getH() == boxContains || boxContains == HoldingType.EMPTY) {

				// check box not full
				if (!(m.getBoxes().get(b.getLocation()).isfull())) {

					// set box type in model if this is 1st item placed in box
					if (boxContains == HoldingType.EMPTY) {
						m.getBoxes().get(b.getLocation()).setContains(m.getP().getH());
					}

					m.getBoxes().get(b.getLocation()).incrementCount();
//					System.out.println("\n\nbox count = " + m.getBoxes().get(b.getLocation()).getCount() + " isfull = "+ m.getBoxes().get(b.getLocation()).isfull());
					m.getP().setH(HoldingType.EMPTY);
				}
			}
		} 
		else {
			System.out.println("can't put that down in this box");
		}

		type = m.getBoxes().get(b.getLocation()).getContains().name() + " " + m.getBoxes().get(b.getLocation()).getCount();
		return type;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		//NOTE: the line of code below fixes the box pickup bug we had 11/12-11/13. Do not remove.
		if(pickUpRequest){
			pickUpRequest = false;
		}
		
		v.setPlayerDest(e.getComponent().getLocation());
		m.getP().setDestination(e.getPoint());

		// if a button was clicked
		if (e.getComponent() instanceof button) {
			b = (button) (e.getComponent());
			m.getP().setDestination(b.getLocation());

			// TODO: fix this -- Auzi;
			// v.getHealthBar().setHealthHeight(v.getHealthBar().healthHeight +
			// 4);
			
			//if pickup = true, and btn was clicked, then pickup = false.
			if (b.getHoldingType() == HoldingType.BOX) {
				putDownRequest = true;
			} else {
				pickUpRequest = true;
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
		int i = 0;
		for (Wave w : m.getWaves()) {

			if (!(w.getCurrentPos().equals(w.getDestination()))) {
				// move model's version of wave
				w.move();

				// move view's version of wave based on model
				v.setSingleWaveBtn(i, w.getCurrentPos());
			} else {
				int shoreDamage = determineDamage(w, i);
//				int HP damage = ;
				m.resetWave(i);
				m.updateShoreLine(shoreDamage);
				v.updateShoreline(shoreDamage);
				m.getHB().damage(shoreDamage);
				
				System.out.println("\nShoreline5 = "+ m.getShoreLine() + " min shoreline = "+ m.getminShoreLine());
				v.resetWave(i, w.getCurrentPos());
				checkGameStatus();//we call this here bc shoreline was updated (above)
				// System.out.println("model shoreline = "+ m.getShoreLine() +
				// "\nview shoreline = " + v.getShoreLine());
			}
			i++;
		}

	}

	private void checkGameStatus() {
		System.out.println("in Controller->check game status function \nShoreline = "+ m.getShoreLine() + "\nmin shoreline = "+ m.getminShoreLine());
		if(m.getShoreLine() <= m.getminShoreLine()){
			gameStatus = status.LOSE_SHORE;
			System.out.println("lose - shoreline receeded");
		}
		else if(m.getP().getHealth() < 0){
			gameStatus = status.LOSE_PLAYER;
			System.out.println("lose - player health at zero");
		}
		else if( m.allBoxesFull() ){
			if( m.boxesCorrect() ){
				gameStatus = status.WIN;
				System.out.println("win! filled the boxes in time, with at least 50% Gabions");
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

	public void initSprites() {

		String[] myNames = { "pNORTH.png", "pSOUTH.png", "pEAST.png", "pWEST.png", "pNORTHEAST.png", "pNORTHWEST.png",
				"pSOUTHEAST.png", "pSOUTHWEST.png", "concrete1.png", "oyster1.png","box.png", "wave.png"};
		pics = new ImageIcon[myNames.length];
		int i = 0;
		for (String s : myNames) {
			pics[i] = createImage(s);
			i++;
		}
	}

	/**
	 * @param n
	 * @return Read image icon and return
	 */
	private ImageIcon createImage(String n) {
		ImageIcon imageIcon;
		try {
			// System.out.println("About to read an image");
			imageIcon = new ImageIcon(ImageIO.read(new File("src/Sprites/Player/copy/" + n)));
			// System.out.println("bufferedImage");
			return imageIcon;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public status getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(status gameStatus) {
		this.gameStatus = gameStatus;
	}
}
