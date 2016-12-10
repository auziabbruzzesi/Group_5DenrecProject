package controller;



import model.Model;
import view.View;

//Main class. Objects start the game up on their own, so no code is needed inside the main function. 
public class Main {

	static Model m = new Model();
	static View v = new View();
	static Controller c = new Controller(m,v);
	
	public static void main(String[] args) {
	
	}

}
