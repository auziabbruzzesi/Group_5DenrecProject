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
	
		
		//Healthbar
		v.getHealthBar().setBounds(0, 0, m.getHB().getWidth(), m.getHB().getHeight());
		v.getJPanel().add(v.getHealthBar());
		
		for (Box b : m.getBoxes().values()) {
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

		for (Wave w : m.getWaves()) {

			button k = new button();
			k.setMargin(new Insets(0, 0, 0, 0));
			k.setBounds(w.getCurrentPos().x, w.getCurrentPos().y, Wave.waveWidth, Wave.waveHeight);

			k.setBackground(Color.pink);
			// k.setIcon(defaultIcon);

			v.addToWaveBtns(k);

			i++;
		}

		i = 0;
		
		for (BeachObject bo : m.getBeachObject().values()) {
			button s = new button();
			// The line of code below fixes the issue of displaying c/o text in windows
			// note, if this starts giving trouble later, try: l.setBorder(null);
			s.setMargin(new Insets(0, 0, 0, 0));

			// BEACH OBJECT BOUNDS/DIMENSIONS ARE SET HERE
			s.setBounds(bo.getCurrentPos().x, bo.getCurrentPos().y, BeachObject.beachObjDimensions,
					BeachObject.beachObjDimensions);

			if (bo.getH() == HoldingType.CONCRETE) {
				s.setHoldingType(HoldingType.CONCRETE);
				s.setText("c");
				s.setBackground(Color.gray);
				s.addMouseListener(this);
				s.setIcon(concIcon);
			} else if (bo.getH() == HoldingType.OYSTER) {
				s.setHoldingType(HoldingType.OYSTER);
				s.setText("o");
				s.setBackground(Color.blue);
				s.addMouseListener(this);
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

		// System.out.println("distance to dest: " +
		// m.getP().getCurrentPos().distance(m.getP().getDestination()));

		if (m.getP().getDestination().distance(m.getP().getCurrentPos()) < 10) {
			pTimer.stop();
			// System.out.println("we've reached our destination");

			if (pickUpRequest) {
				// call pickup send b.type
				// System.out.println("pickupreq after stopping timer = " +
				// pickUpRequest);

				// pickup representation in model: check if we can pickup, if
				// yes: update holding type & return true
				System.out.println("\nIn pickup request: \nplayer holding type = " + m.getP().getH());
				System.out.println("beachobj holding type = " + b.getHoldingType());
				if (m.getP().pickUp(b.getHoldingType())) {

					System.out.println("player holding type = " + m.getP().getH());
					// System.out.println("componentat returns: " +
					// v.getJPanel().getComponentAt(b.getLocation()));
					v.getJPanel().getComponentAt(b.getLocation()).setVisible(false);
				}
				pickUpRequest = false;
			} // end if(pickup)

			else if (putDownRequest) {
				System.out.println("\nin putdown request:");
				System.out.println("player holding type = " + m.getP().getH());
				System.out.println("box holding type = " + b.getHoldingType());

				b.setText(putDown());
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

					// set box type if this is 1st item placed in box
					if (boxContains == HoldingType.EMPTY) {
						// in model
						m.getBoxes().get(b.getLocation()).setContains(m.getP().getH());
					}

					m.getBoxes().get(b.getLocation()).incrementCount();
					m.getP().setH(HoldingType.EMPTY);

				}
				System.out.println("putDown was executed: \nPlayer holding type = " + m.getP().getH()
						+ "\nBox holding type = " + m.getBoxes().get(b.getLocation()).getH() + "\nBox contains = "
						+ m.getBoxes().get(b.getLocation()).getContains() + "\nBox count = "
						+ m.getBoxes().get(b.getLocation()).getCount());
			}
		} else {
			System.out.println("you can't put this type of object in this box");
			System.out.println("putDown was executed: \nPlayer holding type = " + m.getP().getH()
					+ "\nBox holding type = " + m.getBoxes().get(b.getLocation()).getH() + "\nBox contains = "
					+ m.getBoxes().get(b.getLocation()).getContains() + "\nBox count = "
					+ m.getBoxes().get(b.getLocation()).getCount());
		}

		type = m.getBoxes().get(b.getLocation()).getContains().name();
		return type;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		v.setPlayerDest(e.getComponent().getLocation());
		m.getP().setDestination(e.getPoint());

		// if a button was clicked
		if (e.getComponent() instanceof button) {
			b = (button) (e.getComponent());
			m.getP().setDestination(b.getLocation());


			//TODO: fix this -- Auzi;
//			v.getHealthBar().setHealthHeight(v.getHealthBar().healthHeight + 4);
			
			if (b.getHoldingType() == HoldingType.BOX) {
				System.out.println("\nBox button clicked");
				putDownRequest = true;
				// System.out.println(this);
			} else {
				pickUpRequest = true;
				// System.out.println("pickuprequest = " + pickUpRequest);
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
				m.resetWave(i);
				//v.setWaveBoxCollision(true);
				v.updateShoreline();
				v.resetWave(i, w.getCurrentPos());
			}
			i++;
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

	
	public void initSprites() {

		String[] myNames = { "pNORTH.png", "pSOUTH.png", "pEAST.png", "pWEST.png", "pNORTHEAST.png", "pNORTHWEST.png",
				"pSOUTHEAST.png", "pSOUTHWEST.png", "concrete.png", "oyster.png" };
		pics = new ImageIcon[myNames.length];
		int i = 0;
		for (String s : myNames) {
			pics[i] = createImage(s);
			i++;
		}
	}

	/**
	 * @param n
	 * @return
	 * Read image icon and return
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
}
