package controller;



import model.Model;
import view.*;

public class Main {

	private static boolean playTutorial = true; //change to true to play tutorial before game starts
	static Model m = new Model();
	static View v = new View();
	static Controller c = new Controller(m,v /*, State tutorial*/);
	
	public static void main(String[] args) {
	
	
	}

}
