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

	jpanel j = new jpanel();

	// Constructor
	public View() {

		setTitle("Estuary Quest");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		j.setPreferredSize(new Dimension(viewWidth, viewHeight));
		add(j);
		pack();

		setVisible(true);
	}

	
	//Inner classes	
	public class jpanel extends JPanel {

		protected void paintComponent(Graphics g) {

			g.setColor(Color.black);
			g.fillRect(playerPos.x, playerPos.y, Player.playerDimensions, Player.playerDimensions);
	
			movePlayer();			
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
		// TODO Auto-generated method stub
		return this.j;
	}
	
	
	// VIEW'S REPRESENTATION OF MOVEMENT HANDLED IN 2 FUNCTIONS BELOW
	// TODO:
	// convert playerDir to enum type instead of string
	//Add in basic NSEW directions
	
	public void updatePlayerDir() {
		if (playerPos.getY() < playerDest.getY()) {
			// then we're moving generally south
			if (playerPos.getX() < playerDest.getX()) {
				// then we're moving southeast
				playerDir = "southeast";
			} else if (playerPos.getX() > playerDest.getX()) {
				// then we're moving southwest
				playerDir = "southwest";
			}
		} else if (playerPos.getY() > playerDest.getY()) {
			// then we're moving generally north
			if (playerPos.getX() > playerDest.getX()) {
				// then we're moving northwest
				playerDir = "northwest";
			} else if (playerPos.getX() < playerDest.getX()) {
				// then we're moving northeast
				playerDir = "northeast";
			}
		}
	}
	
	public void movePlayer(){
		//movement of player based on direction & whether current position = destination
		switch (playerDir) {
		// SOUTHEAST
		case "southeast": // dest x&y > pos x&y
			if (playerPos.getX() != playerDest.getX()) {
				if (playerDest.getX() - playerPos.getX() < playerVelocity) {
					// don't overshoot destination
					playerPos.translate((int) (playerDest.getX() - playerPos.getX()), 0);
				} else {
					playerPos.translate(playerVelocity, 0);
				}
			}
			if (playerPos.getY() != playerDest.getY()) {
				if (playerDest.getY() - playerPos.getY() < playerVelocity) {
					// don't overshoot destination
					playerPos.translate(0, (int) (playerDest.getY() - playerPos.getY()));
				} else {
					playerPos.translate(0, playerVelocity);
				}
			}
		break;
		// SOUTHWEST
		case "southwest": // dest x< pos x, dest y > pos y (Moving down and
						// left)
			// Handling x
			if (playerPos.getX() != playerDest.getX()) {
				if (playerPos.getX() - playerVelocity < playerDest.getX()) {
					// don't overshoot destination
					setPlayerPos(playerDest.getX(), getPlayerPos().getY());//update rest of code to this
				} else {
					playerPos.translate(-playerVelocity, 0);
				}
			}
			// Handling y
			if (playerPos.getY() != playerDest.getY()) {
				if (playerPos.getY() + playerVelocity > playerDest.getY() ) {
					// don't overshoot destination
					playerPos.translate(0, (int) (playerDest.getY() - playerPos.getY()));
				} else {
					playerPos.translate(0, playerVelocity);
				}
			}
		break;
		// NORTHEAST
		case "northeast": // dest x > pos x, dest y < pos y
			// Handling x
			if (playerPos.getX() != playerDest.getX()) {
				if (playerDest.getX() - playerPos.getX() < playerVelocity) {
					// don't overshoot destination
					playerPos.translate((int) (playerDest.getX() - playerPos.getX()), 0);
				} else {
					playerPos.translate(playerVelocity, 0);
				}
			}

			// Handling y
			if (playerPos.getY() != playerDest.getY()) {
				
				if (playerPos.getY() - playerDest.getY() < playerVelocity) {
					playerPos.translate((int) (playerPos.getY() - playerDest.getY()), 0);
				} else {		
					playerPos.translate(0, -playerVelocity);
				}
			}
		break;
	// NORTHWEST
		case "northwest":
			// Handling x
			if (playerPos.getX() != playerDest.getX()) {
				// Handling x
				if (playerPos.getX() != playerDest.getX()) {
					if (playerPos.getX() - playerVelocity < playerDest.getX()) {
						// don't overshoot destination
						setPlayerPos(playerDest.getX(), getPlayerPos().getY());//update rest of code to this
					} else {
						playerPos.translate(-playerVelocity, 0);
					}
				} else {
					playerPos.translate(-playerVelocity, 0);
				}
			}

			// Handling y
			if (playerPos.getY() != playerDest.getY()) {
				
				if (playerPos.getY() - playerDest.getY() < playerVelocity) {
					playerPos.translate((int) (playerPos.getY() - playerDest.getY()), 0);
				} else {
					playerPos.translate(0, -playerVelocity);
				}
			}
		break;

		default:
		break;
		}//end switch
	}//end move()
}//end View class


//public class button extends JButton {
//@Override
//protected void paintComponent(Graphics g) {
//	this.setBounds(50, 50, 30, 30);
//}
//}