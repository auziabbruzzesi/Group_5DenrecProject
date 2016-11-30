//make set-up func in view, then call it in controller

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.Controller.button;
import controller.status;
import model.Model;
import model.Player;
import model.Wave;
import view.View.HealthPanel;

public class View extends JFrame {
	public static final int viewHeight = 650;
	public static final int viewWidth = 1200;

	private ArrayList<button> waveBtns = new ArrayList<button>();

	/*
	 * Notes on the arraylist gameObjBtns (from eaviles):
	 * - waveBtns will be merged into this later (probably tomorrow, 11.30)
	 * - each button will correspond to a gameObjs array object (see model)
	 * - will track by index or position - index would be easier but position is safer. 
	 *   Index should be fine if we're careful, and given the deadline, I vote for this.
	 */
	private ArrayList<JButton> gameObjBtns = new ArrayList<JButton>();
	
	Point playerPos = (new Point(0, 0));
	Point playerDest = (new Point(200, 200));
	private int playerDims;
	String playerDir = "";

	private int playerVelocity = 7;

	JPanel jP = new jpanel();

	private HealthPanel healthBar = new HealthPanel(200, 200);
	Dimension frameDimensions = new Dimension(viewWidth, viewHeight);

	private int shoreLine = (2 * viewWidth) / 3;
	private int shoreMin;

	private boolean waveBoxCollision = false;

/*
 * View Constructor 
 */
	public View() {
		initView();	
	}
	
/*
 * General functions
 */
	public void drawViewObjs() {
		// call get() on Model's array of pointers
		// for each item in the Model array, look at the type (may do this in 1 of 2 ways)
		// look at the item's current position in Model, and update the View button's position to match
		// repaint();
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

	public void resetWave(int i, Point p) {
		waveBtns.get(i).setLocation(p);
	}
	
/*
 * Functions required for View initialization
 */
	public void initView(){
		
//		drawViewObjs(); //call getGameObjs and add buttons and panels with proper sprites based on that
		
		shoreMin = shoreLine - this.healthBar.overallHeight;
		setTitle("Estuary Quest");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		jP = new jpanel();
		getContentPane().add(jP);
		jP.add(healthBar);
		pack();
		setVisible(true);
	}

	public void initGameObjBtns(){
		//for each element in model's gameobjs array, create a button with the position
		//TODO: to make this easier, standardize "get posision" functions for all objs, so they're named the same

		for(int i = 0; i<Model.objArrSize; i++){
//		add button to array and set its location based on corresponding element in obj array

			gameObjBtns.add(new JButton());
			
		}
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

			g.setColor(Color.BLUE);
			g.fillRect(shoreLine, 0, viewHeight, viewWidth);

			g.setColor(Color.yellow);
			g.fillRect(0, 0, shoreLine, viewHeight);

			for (int i = 0; i < waveBtns.size(); i++) {
				add(waveBtns.get(i));
			}

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

	public ArrayList<button> getWaveBtns() {
		return waveBtns;
	}

	public button getSingleWaveBtn(int i) {
		return waveBtns.get(i);
	}

	public void setSingleWaveBtn(int i, Point nP) {
		waveBtns.get(i).setLocation(nP);

	}

	public void setWaveBtns(ArrayList<button> waveBtns) {
		this.waveBtns = waveBtns;
	}

	public HealthPanel getHealthBar() {
		return healthBar;
	}

	public void setHealthBar(HealthPanel healthBar) {
		this.healthBar = healthBar;
	}

	public void addToWaveBtns(button wB) {
		this.waveBtns.add(wB);
	}

	public void setWaveBoxCollision(boolean b) {
		waveBoxCollision = b;
	}

	public int getShoreLine() {
		return shoreLine;
	}
}// end View class