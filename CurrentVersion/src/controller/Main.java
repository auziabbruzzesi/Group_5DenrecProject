package controller;



import model.Model;
import view.*;

public class Main {

//	static Model fromSaveFile = new Model("filename.someExtension");
	//view
	//controller
	private static boolean playTutorial = true; //change to true to play tutorial before game starts
	static Model m = new Model();
	static View v = new View();
	static Controller c = new Controller(m,v /*, State tutorial*/);
	
	public static void main(String[] args) {
		if(playTutorial){
			//c.startTutorial();
		}
		else{
			c.wTimer.start();
		}		
		

//		v.screenTimer.start();
		c.pTimer.start();
		//c.wTimer.start();

		v.getJPanel().addMouseListener(c);
		v.repaint();
	}

}
