package controller;

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.Timer;

import model.HoldingType;
import model.Model;
import model.Player;
import view.View;

public class Main {
	
	static Model m = new Model();
	static View v = new View();
	static Controller c = new Controller(m,v);
	

	public static void main(String[] args) {
		v.setLayout(null);
		v.getJPanel().addMouseListener(new MouseEventListener());
		v.repaint();
		
		for(int i = 0; i < 100; i++){    			
    		v.repaint();
    		try {
    			Thread.sleep(100);
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    	}

	}

}
