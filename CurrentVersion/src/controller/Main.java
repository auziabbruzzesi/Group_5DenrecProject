package controller;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
//GITHUB SUCKS PLEASE WORK THOUGH SERIOUSLY
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//hello
import javax.swing.JButton;
import javax.swing.Timer;

import model.HoldingType;
import model.Model;
import model.Player;
import view.View;

public class Main {

	static Model m = new Model(/*State tutorial*/);
	static View v = new View(/*State tutorial*/);
	static Controller c = new Controller(m,v /*, State tutorial*/);
	
	
	public static void main(String[] args) {
		v.setLayout(null);
		/*
		 * Note: I got distracted and started playing around with window resizing. I commented out my changes (below) 
		 * but left them in here because they might be helpful. If not, just delete them :)
		 */
//		v.setLayout(new GridBagLayout());
//		v.setPreferredSize(new Dimension(View.viewWidth, View.viewHeight));
//		v.setMinimumSize(new Dimension(700, 150));
//		v.setMaximumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width , Toolkit.getDefaultToolkit().getScreenSize().height));
//		v.setResizable(true);
		
		//tutorial
		
		v.getJPanel().addMouseListener(c);
		c.wTimer.start();
		v.repaint();
	}

}
