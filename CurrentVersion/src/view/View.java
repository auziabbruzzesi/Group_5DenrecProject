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
import javax.swing.JPanel;

import controller.Controller.button;
import model.Player;
import model.Wave;

public class View extends JFrame {
	public static final int viewHeight = 650;
	public static final int viewWidth = 1200;

	private ArrayList<button> waveBtns = new ArrayList<button>();

	Point playerPos = (new Point(0, 0));
	Point playerDest = (new Point(200, 200));
	private int playerDims;
	String playerDir = "";

	private int playerVelocity = 7;

//	// SHORELINE VARS
//	private int erosion = 150; // may only need this locally in paint component.
//	Point pointOfErosion = new Point(650, 0); // aka threshold
//	private boolean hasReached = false; // has the xCoord reached 650?

	JPanel jP = new jpanel();

//	private JPanel sand;
//	private JPanel ocean;
	private JPanel healthBar = new JPanel();
	Dimension frameDimensions = new Dimension(viewWidth, viewHeight);

	private int shoreWidth = viewWidth - 360;//make this more generic later
	private boolean waveBoxCollision = false;

	// Constructor
	public View() {

		setTitle("Estuary Quest");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		jP = new jpanel();
		getContentPane().add(jP);

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
		// erosion point is (650, 0);

		@Override
		protected void paintComponent(Graphics g) {
			
			if (!waveBoxCollision) {

				g.setColor(Color.BLUE);
				g.fillRect(shoreWidth, 0, viewHeight, viewWidth);

				g.setColor(Color.yellow);
				g.fillRect(0, 0, shoreWidth, viewHeight);

			}
			// if there was a collision:
			else {
				shoreWidth -= 10;

				g.setColor(Color.BLUE);
				g.fillRect(shoreWidth, 0, viewHeight, viewWidth);

				g.setColor(Color.yellow);
				g.fillRect(0, 0, shoreWidth, viewHeight);

				waveBoxCollision = false;
			}
			
			for (int i = 0; i < waveBtns.size(); i++) {
				add(waveBtns.get(i));
			}
			// System.out.println("weird thing is a: " +
			// this.getComponentAt(0,0));
			// }
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

	public JPanel getHealthBar() {
		return healthBar;
	}

	public void setHealthBar(JPanel healthBar) {
		this.healthBar = healthBar;
	}

	public void addToWaveBtns(button wB) {
		this.waveBtns.add(wB);
	}

	public boolean getWaveBoxCollision() {
		return waveBoxCollision;
	}

	public void setWaveBoxCollision(boolean wBC) {
		this.waveBoxCollision = wBC;
	}
	public int getShoreWidth(){
		return shoreWidth;
	}
	public void setShoreWidth(int x){
		shoreWidth = x;
	}

}// end View class