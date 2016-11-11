//make set-up func in view, then call it in controller

package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
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
	private int playerVelocity = 7;// may need this to be public-static-final at
									// some point
	JPanel jP = new jpanel();

	// jpanel j = new jpanel();

	// Constructor
	public View() {

		setTitle("Estuary Quest");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		jP.setPreferredSize(new Dimension(viewWidth, viewHeight));

		add(jP);
		pack();

		setVisible(true);
	}

	// Inner classes
	// if we have time, change this to a component, rather than a whole jpanel
	public class jpanel extends JPanel {

		protected void paintComponent(Graphics g) {
			g.setColor(Color.BLUE);
			g.fillRect((2 * viewWidth) / 3, 0, viewHeight, viewWidth);
			g.setColor(Color.yellow);
			g.fillRect(0, 0, (2 * viewWidth) / 3, viewHeight);
		
			for (int i = 0; i<waveBtns.size(); i++) {
				add(waveBtns.get(i));
			}
		}
	}

	public void resetWaves() {
		
		// for (button b : v.getWaveBtns()) {
		// // v.getJPanel().getComponentAt(w.getCurrentPos()).setVisible(false);
		// }

		int i = 0;
		for (button wB : waveBtns) {
			Point a = new Point(View.viewWidth - Wave.waveWidth, i * Wave.waveSpawnSpacing);
			
			setSingleWaveBtn(i, a);
			i++;
		}
		// for (button b : v.getWaveBtns()) {
		// // v.getJPanel().getComponentAt(w.getCurrentPos()).setVisible(true);
		// }
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

	public void addToWaveBtns(button wB) {
		this.waveBtns.add(wB);
	}

}// end View class