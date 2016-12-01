//make set-up func in view, then call it in controller

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.status;
import model.*;
import model.Character;
import view.View.HealthPanel;

public class View extends JFrame {
	public static final int viewHeight = 650;
	public static final int viewWidth = 1200;
	// private ArrayList<button> waveBtns = new ArrayList<button>();

	public ArrayList<button> gameObjBtns = new ArrayList<button>();

	Point playerPos = (new Point(0, 0));
	Point playerDest = (new Point(200, 200));
	private int playerDims;
	String playerDir = "";
	private int playerVelocity = 7;
	JPanel jP = new jpanel();
	private HealthPanel healthBar = new HealthPanel(200, 200);
	Dimension frameDimensions = new Dimension(viewWidth, viewHeight);

	private Integer shoreLine = (2 * viewWidth) / 3;

	private int shoreMin;

	private boolean waveBoxCollision = false;
	private BufferedImage[] scenery = new BufferedImage[2];

	/*
	 * View Constructor
	 */
	public View() {
		initView();
		initScenery();
	}

	/*
	 * General functions
	 */
	public void updateViewObjs() {
		// for each item in the Model array, look at the type,
		// look at the item's current position in Model, and update the View
		// button's position to match

		for (int i = 0; i < Model.objArrSize; i++) {

			if (!(Model.getGameObjs().get(i) instanceof Integer)) {

				// PLAYER
				if (Model.getGameObjs().get(i) instanceof Player) {
					gameObjBtns.get(i).setLocation((Point) (((Player) (Model.getGameObjs().get(i))).getPosition()));
					gameObjBtns.get(i).setIcon( (Icon)( (Player)( Model.getGameObjs().get(i) ) ).getObjIcon() );
				}
				// WAVE
				else if (Model.getGameObjs().get(i) instanceof Wave) {
					gameObjBtns.get(i).setLocation((Point) (((Wave) (Model.getGameObjs().get(i))).getPosition()));
				}
				// BEACHOBJ
				else if (Model.getGameObjs().get(i) instanceof BeachObject) {
//					gameObjBtns.get(i).setLocation((Point) (((BeachObject) (Model.getGameObjs().get(i))).getPosition()));
					
					if( ( (BeachObject)( Model.getGameObjs().get(i) ) ).getObjIcon() == null){
						gameObjBtns.get(i).setVisible(false);
					}
				}
				// BOX
				else if (Model.getGameObjs().get(i) instanceof Box) {
					gameObjBtns.get(i).setLocation((Point) (((Box) (Model.getGameObjs().get(i))).getPosition()));
				}
			}
			// SHORELINE
			else {
				shoreLine = (Integer) Model.getGameObjs().get(i);
			}
		}
	}

	public void gameEnd(status gameStatus) {
		// display dialogue
		String message = "";
		switch (gameStatus) {
		case LOSE_PLAYER:
			message = "You Lose :( \nPlayer health reached zero";
			break;
		case LOSE_SHORE:
			message = "You Lose :( \n Too much of the Estuary eroded away";
			break;
		case LOSE_BOXES:
			message = "You Lose :( \n The Estuary wasn't protected well enough. Try adding more Oyster Gabions!";
			break;
		case WIN:
			message = "You Win! :D \n You created enough protection for the Estuary.";
			break;
		default:
			System.out.println("Error in determining gameEndStatus to display. Status = " + gameStatus);
			break;
		}

		JOptionPane.showMessageDialog(null, message);
		System.exit(0);

	}

	public void updateShoreline(int damage) {
		if (shoreLine > shoreMin) {
			shoreLine -= damage;
			repaint();
		}
	}

	/*
	 * Functions required for View initialization
	 */
	public void initView() {

		shoreMin = shoreLine - this.healthBar.overallHeight;
		setTitle("Estuary Quest");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		jP = new jpanel();
		getContentPane().add(jP);
		jP.add(healthBar);
		pack();
		setVisible(true);

		initGameObjBtns();// DO NOT MOVE - dependent on lines of code above. Thanks!
	}

	public void initGameObjBtns() {

		for (int i = 0; i < Model.objArrSize; i++) {

			if (!(Model.getGameObjs().get(i) instanceof Integer)) {
				button j = new button();
				j.setMargin(new Insets(0, 0, 0, 0));
				j.setBorder(BorderFactory.createEmptyBorder());
				j.setContentAreaFilled(false);

				// PLAYER
				if (Model.getGameObjs().get(i) instanceof Player) {
					j.setSize(new Dimension(Player.playerDimensions, Player.playerDimensions));
					j.setHoldingType(HoldingType.EMPTY);
					j.setLocation((Point) (((Player) (Model.getGameObjs().get(i))).getPosition()));
					j.setBounds(j.getLocation().x, j.getLocation().y, Player.playerDimensions, Player.playerDimensions);
					j.setIcon((Icon) ((Player) (Model.getGameObjs().get(i))).getObjIcon());

				}
				// WAVE
				else if (Model.getGameObjs().get(i) instanceof Wave) {
					j.setSize(new Dimension(Wave.waveWidth, Wave.waveHeight));
					j.setHoldingType(null);
					j.setLocation((Point) (((Wave) (Model.getGameObjs().get(i))).getPosition()));
					j.setBounds(j.getLocation().x, j.getLocation().y, Wave.waveWidth, Wave.waveHeight);
					j.setIcon((Icon) ((Wave) (Model.getGameObjs().get(i))).getObjIcon());
				}
				// BEACHOBJ
				else if (Model.getGameObjs().get(i) instanceof BeachObject) {
					j.setSize(new Dimension(BeachObject.beachObjDimensions, BeachObject.beachObjDimensions));
					j.setHoldingType(((BeachObject) Model.getGameObjs().get(i)).getH());
					j.setLocation((Point) (((BeachObject) (Model.getGameObjs().get(i))).getPosition()));
					j.setBounds(j.getLocation().x, j.getLocation().y, BeachObject.beachObjDimensions,
							BeachObject.beachObjDimensions);
					j.setIcon((Icon) ((BeachObject) (Model.getGameObjs().get(i))).getObjIcon());
				}
				// BOX
				else if (Model.getGameObjs().get(i) instanceof Box) {
					j.setSize(new Dimension(Box.boxDimensions, Box.boxDimensions));
					j.setHoldingType(HoldingType.BOX);
					j.setLocation((Point) (((Box) (Model.getGameObjs().get(i))).getPosition()));
					j.setBounds(j.getLocation().x, j.getLocation().y, Box.boxDimensions, Box.boxDimensions);
					j.setIcon((Icon) ((Box) (Model.getGameObjs().get(i))).getObjIcon());
				}

				gameObjBtns.add(j);
				jP.add(gameObjBtns.get(i));
			}
			// SHORELINE
			else {
				shoreLine = (Integer) Model.getGameObjs().get(i);
			}
		}
	}
	
	private void initScenery() {
		scenery[0] = (BufferedImage) createImage("sky.png").getImage();
		scenery[1] = (BufferedImage) createImage("shore.png").getImage();
	}
	
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
	
	/*
	 * Inner Classes
	 */
	public class jpanel extends JPanel {

		public jpanel() {
			setLayout(null);
			setPreferredSize(frameDimensions);
		}

		@Override
		protected void paintComponent(Graphics g) {
			g.drawImage(scenery[0], 0, 0, this);
			g.drawImage(scenery[1], 0, 0, this);

			// g.setColor(Color.BLUE);
			//// System.out.println("painting. shoreline = " + shoreLine);
			// g.fillRect(shoreLine, 0, viewHeight, viewWidth);
			//
			// g.setColor(Color.yellow);
			// g.fillRect(0, 0, shoreLine, viewHeight);
			g.setColor(Color.red);
			g.drawLine(shoreLine, 0, shoreLine, viewHeight);
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

	public class HealthPanel extends JPanel {
		public int overallHeight;
		public double healthHeight;
		public double startingY;

		private int xPos = 0;
		private int yPos = 0;

		/**
		 * Constructor
		 * 
		 * @param overallHeight
		 * @param healthHeight
		 */
		public HealthPanel(int overallHeight, int healthHeight) {
			this.overallHeight = overallHeight;
			this.healthHeight = healthHeight;
			this.startingY = 0;
			// System.out.println("view's HB overall height = "+overallHeight);
		}

		public HealthPanel() {
			this.overallHeight = 200;
			this.healthHeight = 200;
			this.startingY = 0;
			// System.out.println("view's HB overall height = "+overallHeight);
		}

		public int getOverallHeight() {
			return overallHeight;
		}

		@Override
		protected void paintComponent(Graphics g) {
			g.setColor(Color.WHITE);
			g.fillRect(this.xPos, this.yPos, this.getWidth(), this.getHeight());
			g.setColor(Color.green);
			// System.out.println("here: " + this.overallHeight);
			g.fillRect(this.xPos, (int) this.startingY, this.getWidth(), (int) healthHeight);
		}

		public void setHealthHeight(double d) {
			this.healthHeight = d;
			repaint();
		}

		public void damage(int healthDamage) {
			healthHeight = healthHeight + healthDamage;
			repaint();
		}

	}// end healthPanel class

	/*
	 * Setters & getters
	 */
	public void setPlayerDims(int d) {
		playerDims = d;
	}

	public void setPlayerPos(Point position) {
		this.playerPos = position;
	}

	public void setPlayerPos(double x, double y) {
		this.playerPos.setLocation(x, y);
	}

	public void setPlayerDest(Point destination) {
		this.playerDest = destination;
	}

	public Point getPlayerPos() {
		return playerPos;
	}

	public JPanel getJPanel() {
		return this.jP;
	}

	public HealthPanel getHealthBar() {
		return healthBar;
	}

	public void setHealthBar(HealthPanel healthBar) {
		this.healthBar = healthBar;
	}

	public void setScenery(BufferedImage[] scenery) {
		this.scenery = scenery;
	}

	public void setWaveBoxCollision(boolean b) {
		waveBoxCollision = b;
	}

	public int getShoreLine() {
		return shoreLine;
	}
}// end View class