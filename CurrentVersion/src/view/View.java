//make set-up func in view, then call it in controller

package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Player;

public class View extends JFrame {
	public static final int viewHeight = 650;
	public static final int viewWidth = 1200;

	Point playerPos = (new Point(0, 0));
	Point playerDest = (new Point(200, 200));
	private int playerDims;
	String playerDir = "";
	private int playerVelocity = 7;//may need this to be public-static-final at some point
	JPanel jP = new jpanel();
	
	//jpanel j = new jpanel();

	// Constructor
	public View() {

		setTitle("Estuary Quest");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		jP.setPreferredSize(new Dimension(viewWidth, viewHeight));
		
		add(jP);
		pack();

		setVisible(true);
	}

	
	//Inner classes	
	//if we have time, change this to a component, rather than a whole jpanel
	public class jpanel extends JPanel {

		protected void paintComponent(Graphics g) {
			g.setColor(Color.BLUE);
			g.fillRect((2*viewWidth)/3, 0, viewHeight, viewWidth);
			g.setColor(Color.yellow);
			g.fillRect(0, 0, (2*viewWidth)/3, viewHeight);
		}
	}
	
	
	//Setters & getters
	public void setPlayerDims(int d) {
		playerDims = d;
	}
	public void setPlayerPos(Point position) {
		this.playerPos = position;
	}
	public void setPlayerPos(double x, double y){
		this.playerPos.setLocation(x, y);
	}
	public void setPlayerDest(Point destination) {
		this.playerDest = destination;
	}
	public Point getPlayerPos() {
		return playerPos;
	}
	public JPanel getJPanel() {
		System.out.println("Returning JPanel");
		return this.jP;
	}	
	
}//end View class


//public class button extends JButton {
//@Override
//protected void paintComponent(Graphics g) {
//	this.setBounds(50, 50, 30, 30);
//}
//}