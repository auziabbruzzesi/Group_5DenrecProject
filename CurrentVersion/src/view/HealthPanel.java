package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class HealthPanel extends JPanel {
	int height;
	int width;
	double healthHeight;
	public void setHealthHeight(double d) {
		this.healthHeight = d;
	}
	public void setStartingY(double d) {
		this.startingY = d;
	}
	double startingY;
	
	public HealthPanel(int x,int y,int width, int height){
		this.setLocation(0, 0);
		this.setSize(width, height);
		//this.setVisible(true);
	}
	@Override
	protected void paintComponent(Graphics g){
		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.red);
		g.fillRect(0,(int)startingY, width, (int)healthHeight);
		
	}
	
}
	


