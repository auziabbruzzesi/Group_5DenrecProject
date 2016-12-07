package controller;



import model.Model;
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
		//Menu m = new Menu();
	
		
		if(playTutorial){
				c.startTutorial();
	}
		
		v.screenTimer.start();
		c.pTimer.start();
		c.wTimer.start();
		v.getJPanel().addMouseListener(c);
		
	}

}
