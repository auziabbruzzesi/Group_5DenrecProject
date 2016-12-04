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
	static View v = new View("t"/*State tutorial*/);
	static Controller c = new Controller(m,v /*, State tutorial*/);
	
	public static void main(String[] args) {
		
		//tutorial
//		v.screenTimer.start();
		
//		c.playTutorial();
		
		
		v.setLayout(null);
		v.getJPanel().addMouseListener(c);
		c.wTimer.start();
		v.repaint();
	}

}
