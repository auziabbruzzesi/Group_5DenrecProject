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
import view.*;

public class Main {

//	static Model fromSaveFile = new Model("filename.someExtension");
	//view
	//controller
	private static boolean playTutorial = false; //change to true to play tutorial before game starts
	static Model m = new Model();
	static View v = new View();
	static Controller c = new Controller(m,v /*, State tutorial*/);
	
	public static void main(String[] args) {
		

		if(playTutorial){
			c.startTutorial();
		}
		else{
			c.wTimer.start();
		}		
		
		v.setLayout(null);
		v.getJPanel().addMouseListener(c);
		v.repaint();
	}

}
