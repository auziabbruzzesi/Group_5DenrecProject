//Estella Avilesssssss
package controller;
//ritaaaaaa

import java.awt.GridLayout;
//GITHUB SUCKS PLEASE WORK THOUGH SERIOUSLY
import java.awt.Point;
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
	// this is mayahs change

	static Model m = new Model();
	static View v = new View();
	static Controller c = new Controller(m,v);
	
	

	public static void main(String[] args) {
		v.setLayout(null);
		v.getJPanel().addMouseListener(c);
		v.repaint();

		Timer timer = new Timer(0, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//System.out.println("running timer");
				c.updatePlayerMV();
				v.repaint();
	    		try {
	    			Thread.sleep(100);
	    		} catch (InterruptedException e2) {
	    			e2.printStackTrace();
	    		}
				
			}
			
		});
		//timer.start();
//		for(int i = 0; i < 100; i++){    			
//    		v.repaint();
//    		try {
//    			Thread.sleep(100);
//    		} catch (InterruptedException e) {
//    			e.printStackTrace();
//    		}
//    	}
		//System.out.println("for loop ended");
	}

}
