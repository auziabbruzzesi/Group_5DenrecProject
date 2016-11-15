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
import model.Player;
import model.Wave;
import view.View.HealthPanel;

public class View extends JFrame {
	public static final int viewHeight = 650;
	public static final int viewWidth = 1200;

	private ArrayList<button> waveBtns = new ArrayList<button>();

	Point playerPos = (new Point(0, 0));
	Point playerDest = (new Point(200, 200));
	private int playerDims;
	String playerDir = "";

	private int playerVelocity = 7;

	JPanel jP = new jpanel();

	private HealthPanel healthBar = new HealthPanel(200, 200);
	Dimension frameDimensions = new Dimension(viewWidth, viewHeight);

	private int shoreLine = (2*viewWidth)/3;
	private int shoreMin;
			
	private boolean waveBoxCollision = false;

	// Constructor
	public View() {
		
		shoreMin = shoreLine - this.healthBar.overallHeight;
		System.out.println("View start: shoreline = "+shoreLine+" shoremin = " + shoreMin);
		setTitle("Estuary Quest");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		jP = new jpanel();
		getContentPane().add(jP);
		jP.add(healthBar);
		pack();
		setVisible(true);
	}

	// Inner classes
	// if we have time, change this to a component, rather than a whole jpanel
	public class jpanel extends JPanel {

		public jpanel() {
			setLayout(null);
			setPreferredSize(frameDimensions);
		}

		@Override
		protected void paintComponent(Graphics g) {

			g.setColor(Color.BLUE);
//			System.out.println("painting. shoreline = " + shoreLine);
			g.fillRect(shoreLine, 0, viewHeight, viewWidth);

			g.setColor(Color.yellow);
			g.fillRect(0, 0, shoreLine, viewHeight);

			for (int i = 0; i < waveBtns.size(); i++) {
				add(waveBtns.get(i));
			}

		}
	}
	public class HealthPanel extends JPanel{
		public int overallHeight;
		public double healthHeight;
		public double startingY;
		
		private int xPos = 0;
		private int yPos = 0;
		
		/**
		 * Constructor
		 * @param overallHeight
		 * @param healthHeight
		 */
		public HealthPanel(int overallHeight, int healthHeight){
			this.overallHeight = overallHeight;
			this.healthHeight = healthHeight;
			this.startingY = 0;
			System.out.println("view's HB overall height = "+overallHeight);
		}
		
		public HealthPanel() {
			// TODO Auto-generated constructor stub
			this.overallHeight = 200;
			this.healthHeight = 200;
			this.startingY = 0;
			System.out.println("view's HB overall height = "+overallHeight);
		}

		public int getOverallHeight(){
			return overallHeight;
		}
		
		@Override
		protected void paintComponent(Graphics g){
			g.setColor(Color.WHITE);
			g.fillRect(this.xPos, this.yPos, this.getWidth(), this.getHeight());
			g.setColor(Color.green);
			//System.out.println("here: " + this.overallHeight);
			g.fillRect(this.xPos, (int) this.startingY, this.getWidth(),(int)healthHeight);
			
		}
		public void setHealthHeight(double d){
			this.healthHeight = d;
			repaint();
		}
		
	}

	public void updateShoreline(int damage) {
		System.out.println("View: shoreline = " + shoreLine + " shoremin = " + shoreMin);
		if (shoreLine > shoreMin) {
			shoreLine -= damage;
		}
	}

	public void resetWave(int i, Point p) {
		waveBtns.get(i).setLocation(p);
	}

	// Setters & getters
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
		// System.out.println("Returning JPanel");
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
	public int getShoreLine(){
		return shoreLine;
	}

	public void gameEnd(status gameStatus) {		
		//display dialogue
		String message = "";
		switch(gameStatus){
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
			message = "You Win! :D";
		break;
		}		
		
		JOptionPane.showMessageDialog(null, message);
		System.exit(0);
		
		
	}

}// end View class