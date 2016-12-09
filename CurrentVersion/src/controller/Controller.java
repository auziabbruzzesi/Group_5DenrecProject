/*
 * Note:
 * 		- Green comments exist to denote the start of new sections of code (grouped by functionality)
 * 		and to aid with code readability
 * 		- Blue comments are Javadocs
 */

package controller;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.Timer;

import model.Box;
import model.GameObject;
import model.HoldingType;
import model.Model;
import model.Wave;
import view.Menu;
import view.View;
import view.View.button;
/**
 * @Class Controller regulates the game and handles Model and View
 */
public class Controller implements MouseListener {
	private status gameStatus = status.IN_PROGRESS;
	
	private static Model m;
	private static View v;
	private boolean pickUpRequest = false;
	private boolean putDownRequest = false;
   
	
	private Point objToPickUp = new Point();
	private HoldingType objToPickUpHT = null;
	private Point putDownBox = new Point();
	private int movedObjs = 0;
	
	Box currBox = new Box();
	
	int i = 0;
	
	//save and load variable
	int saveFileNum=1;
	String fname=Integer.toString(saveFileNum)+".sav";
	
	//Tutorial vars
	Boolean tutorial = false;
	HoldingType tutorialPickUp = HoldingType.EMPTY;	
	int waveAnimation = 0;
	boolean tW1Collided = false;
/*
 * Game Timers (2)
 *   wTimer - handles waves
 *   pTimer - handles player movement
 */
	
	/**
	 * @author Eaviles
	 * @Purpose regulate waves in the game
	 */
	Timer wTimer = new Timer(30, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			m.gameDi = v.getContentPane().getSize();
			moveWaves();
			checkGameStatus();
		}
	});

	/**
	 * @author Auzi
	 * @Purpose: regulate player movement in the game
	 */
	Timer pTimer = new Timer(10, new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			m.getP().updateDirection();
			m.updatePlayerSprite();			
			m.getP().move();
			handlePlayerAction();
		}
	});

	/**
	 * @Constructor
	 * @param m, v instance of Model and View objects to work with
	 */
	public Controller(Model m, View v) {
		this.m = m; //initialization occurs in model's constructor
		this.v = v; //init occurs in view's constructor
		v.viewHeight = m.gameDi.height;
		v.viewWidth = m.gameDi.width;
		v.getJPanel().addMouseListener(this);

		initViewBtnListeners();
		
		initMenu();
	}

	/*
	 * Save and load function
	 */
	/**
	 * @author Rzhao
	 * @param objectToSerializa
	 * @param filename, the name of the file where classes are saved
	 * @Purpose save class to a file
	 */
		public void save(Serializable objectToSerializa,String filename){
			FileOutputStream fos=null;//file creation
			try{
				fos=new FileOutputStream(filename);
				ObjectOutputStream oos=new ObjectOutputStream(fos);
				oos.writeObject(objectToSerializa);
				oos.flush();
				oos.close();
			
			}catch(IOException e){
				e.printStackTrace();
			}
		
	}

		/**
		 * @author Rzhao
		 * @param loadName, name of file to read object from
		 * @Purpose load class from a file
		 */
		public static void load(String loadName){
			if(checkFileExists()){
				FileInputStream fis=null;
			
			try{
				fis=new FileInputStream(loadName);

				ObjectInputStream ois=new ObjectInputStream(fis);
				GameObject loadedObject=(GameObject)ois.readObject();
				ois.close();
				System.out.println("load game");
				System.out.println(loadedObject.toString());
			}catch(ClassNotFoundException|IOException e){
				e.printStackTrace();
			}
		}
		}

		/**
		 * @author Rzhao
		 * @Purpose check if the given file exists
		 */
		private static boolean checkFileExists() {
			return new File("game.sav").isFile();
		}

/*
 * General functions
 */
	
	/**
	 * @author Eaviles
	 * @Purpose dictates what should happen when a player tries to pickup or putdown
	 * a BeachObject.
	 */
	public void handlePlayerAction() {

		if (m.getP().getDestination().distance(m.getP().getPosition()) < 10) {	
			pTimer.stop();

			//if player clicked on oyster or concrete
			if (pickUpRequest) {
				v.setResetBOs(false);
				//try to pickup the object. If we are successful, remove that object from jpanel
				if (m.getP().pickUp(objToPickUpHT)) {
					v.getJPanel().getComponentAt(objToPickUp).setVisible(false);
					m.updatePlayerSprite();
					
					if(tutorial){
						tutorialPickUp = objToPickUpHT;
						v.playTutorialSequence(3);
					}
				}
				m.updatePlayerSprite();
				pickUpRequest = false;
			} // end if(pickup)

			else if (putDownRequest) {
				putDown();
				putDownRequest = false;
				this.movedObjs++;
				
				if(movedObjs >= 10){
					v.setResetBOs(true);
					movedObjs = 0;
				}
			}
		}
	}

	/**
	 * @author Auzi, Eaviles
	 * @return String type
	 * @Purpose handles logic for when player tries to place a beachObject in a box 
	 */
	public String putDown() {
		String type = "";

		// check player is holding something
		if (m.getP().getHT() != HoldingType.EMPTY) {
		
			//what's in the box is stored in a variable		
			HoldingType boxContains = m.getBoxes().get(putDownBox).getContains();			
			
			// check type of obj matches box type, or box is empty
			if (m.getP().getHT() == boxContains || boxContains == HoldingType.EMPTY) {

				
				// check box not full
				if (!(m.getBoxes().get(putDownBox).isfull())) {
			 
					
					m.getBoxes().get(putDownBox).incrementCount();

					// set box type in model if this is 1st item placed in box
						currBox = m.getBoxes().get(putDownBox);
						currBox.setContains(m.getP().getHT());
					
						switch( m.getP().getHT() ){
						case CONCRETE:
							currBox.setObjIcon(m.concreteImages[currBox.getCount()]);
						break;
						case OYSTER:
							currBox.setObjIcon(m.getGabionImages()[currBox.getCount()]);					
						break;
						case TUTORIAL_C:
							currBox.setObjIcon(m.concreteImages[currBox.getCount()]);
						break;
						case TUTORIAL_O:
							currBox.setObjIcon(m.getGabionImages()[currBox.getCount()]);
						break;
						default:
							System.out.println("error in Controller.putDown(): incorrect conditions.");
						break;
						}						
						
					m.getP().setHT(HoldingType.EMPTY);
					m.updatePlayerSprite();
				}
			}
		 
			else {
				System.out.println("Can't put that down in this box. It is a "
						+ m.getBoxes().get(putDownBox).getContains() + " box and you are holding " + m.getP().getHT());
			}


			if(tutorial){
				switch(currBox.getContains()){
				case TUTORIAL_O:
					v.getCTBtn().removeMouseListener(this);
					v.playTutorialSequence(4);
				break;
				case TUTORIAL_C:
					v.getOTBtn().removeMouseListener(this);
					v.playTutorialSequence(5);
				break;
				default:
					System.out.println("error in tutorial switch: Controller.putDown()");
				break;
				}
				
				m.playTutorialWaveCollision(currBox);
				this.waveAnimation++;
				this.wTutorialTimer.start();
			}
	}
		type = m.getBoxes().get(putDownBox).getContains().name() + " " + m.getBoxes().get(putDownBox).getCount();
		return type;
	}
	
	
	/**
	 * @author Eaviles
	 * @Purpose move all waves by one increment of their respective velocities. This is done by
	 * calling Wave.move() on each wave. Reset waves upon collision by calling appropriate helper functions.
	 */
	public void moveWaves() {
		int a = 0;
		for (Wave w : m.getWaves()) {

			//if we haven't reached destination
			if ( w.getPosition().x > w.getDestination().x ) {
				w.move();		
			}
			
			//else, wave has reached the shore. Shoreline, Estuary Health must be updated and Wave must be reset.
			else {						
				waveCollision(w);
				m.resetWave(a);
			}
			a++;	
		}
		m.updateWavesDestinations();
	}

	/**
	 * @author Eaviles
	 * @param w - wave that hit shoreline
	 * @return int decrement - the amount to decrement the shoreline by
	 * @Purpose waves will damage the shoreline by different amounts, depending on the contents
	 * of the box they hit. This function determines how much damage will be done.
	 */
	public int determineDamage(Wave w) {
		int decrement = 0;
		Box b = null;
		
		for( Box k : m.getBoxes().values() ){
			if(k.getIndex() == w.getIndex()){
				b = k;
			}
		}
		if(b == null){
			System.out.println("error in Controller: determineDamage: no box with index matching wave");
		}
		if(!b.isfull()){
			// set decrement based box contents
			switch (b.getContains()) {
			case EMPTY:
				decrement = 5;
				// b.setCount(b.getCount() -1);
				// b.setObjIcon(m.concreteImages[b.getCount() - 1]);
				break;
			case OYSTER:
			
				if (b.isfull()) {
					decrement = 1;

				} else {
					decrement = 3;
				}
			
				b.setCount(b.getCount() - 1);
				b.setObjIcon(m.getGabionImages()[b.getCount()]);
				
				break;
			case CONCRETE:
				
				if (b.isfull()) {
					decrement = 3;
				} else {
					decrement = 4;
				}
				b.setCount(b.getCount() - 1);
				b.setObjIcon(m.concreteImages[b.getCount()]);
				break;
			case TUTORIAL_C:
				decrement = 4;
				break;

			case TUTORIAL_O:
				decrement = 4;
				break;
			default:
				System.out.println("Error: Box contains = " + b.getContains());
				break;
			}
		}
		return decrement;
	}

	/**
	 * @author Auzi/Eaviles
	 * @Purpose handle action when user clicks the mouse (regardless of where on the screen they click)
	 */
	@Override
	public void mousePressed(MouseEvent e) {

		//NOTE: the if statement below fixes the box pickup bug we had 11/12-11/13. Do not remove.
		if(pickUpRequest){
			pickUpRequest = false;
		}

		m.getP().setDestination(e.getPoint());

		//if a saveButton was clicked
//		if(e.getComponent() instanceof SaveButton){
//			System.out.println("press save button");
//			//save(m.getGameObjs());
//			saveRequest=false;
//		}
		
		// if a button was clicked
		if (e.getComponent() instanceof button) {

			m.getP().setDestination( ((View.button) (e.getComponent())).getLocationOnScreen() );
			
			//if a box was clicked
			if ( ( (View.button) ( e.getComponent() ) ).getType() == HoldingType.BOX ) {
				putDownRequest = true;
				putDownBox = e.getComponent().getLocation();
			} 
			else if( tutorial && ( ( (View.button) ( e.getComponent() ) ).getType() == HoldingType.TUTORIAL_O
						|| ( (View.button) ( e.getComponent() ) ).getType() == HoldingType.TUTORIAL_C ) ){
				pickUpRequest = true;
				objToPickUp = e.getComponent().getLocation();
				objToPickUpHT = ( ( View.button )( e.getComponent() ) ).getType();
			}
			//if anything else was clicked
			else if(!tutorial) {
				pickUpRequest = true;
				objToPickUp = e.getComponent().getLocation();
				objToPickUpHT = ( ( View.button )( e.getComponent() ) ).getType();
			}
			else{
				System.out.println("error in Controller:mousePressed: incorrect conditions:");
			}
		}

		pTimer.start();
	}


	/**
	 * @author Eaviles
	 * @Purpose check whether the game has ended. If ended, pause all timers,
	 * determine the game outcome, craft message communicating the outcome, 
	 * and tell View to display it.
	 */
	private void checkGameStatus() {

		String endMessage = "";

//		if(m.getShoreLine().getShoreBottom().x <= m.getShoreLine().getLoosingCoordinate()){
		if(m.getHB().getHealth() <= 0){
			System.out.println("SHORELINE TOP COORD: " + m.getShoreLine().getShoreBottom().getX());
			System.out.println("SHORELINE LOOSING  COORD: " + m.getShoreLine().getLoosingCoordinate());
			gameStatus = status.LOSE_SHORE;
			endMessage = "You Lose :( \n Too much of the Estuary eroded away";
		}
		else if( m.allBoxesFull() ){
			if( m.boxesCorrect() ){
				gameStatus = status.WIN;
				endMessage = "You Win! :D \n You created enough protection for the Estuary.";
			}
			else{
				gameStatus = status.LOSE_BOXES;
				endMessage = "You Lose :( \n The Estuary wasn't protected well enough. Try adding more Oyster Gabions!";
			}
		}
		
		if(gameStatus != status.IN_PROGRESS){
			wTimer.stop();
			pTimer.stop();
			v.gameEnd(endMessage);
			v.setMenu(new Menu());
			initMenu();

		}
		
	}
	
	/**
	 * @author Eaviles
	 * @param w: the wave that collided with the shore
	 * @Purpose handle actions related to collision shared by tutorial and regular waves.
	 * This was created to remove some repeated code.
	 */
	public void waveCollision(Wave w){
		int shoreDamage = determineDamage(w);
		int healthDamage = shoreDamage;// this is redundant in terms of code, but makes it more
										// obvious what's going on. Leaving for improved readability.
		m.updateShoreLine(shoreDamage);
		m.getShoreLine().updateTotalDecrement(shoreDamage);
		m.getHB().damage(healthDamage);
		System.out.println("shoreline current bottom = "+ m.getShoreLine().getShoreBottom().x);
		System.out.println("shoreline losting bottom = "+ m.getShoreLine().getLoosingCoordinate());
		checkGameStatus();
	}
/*
 * Tutorial-related functions
 */
	/**
	 * @author Eaviles 
	 * @Purpose calls the necessary functions for the game
	 * tutorial to execute. Regulates the flow of the tutorial.
	 */
	public void startTutorial() {
		tutorial = true;
		
		v.playTutorialSequence(1);
		
		m.playTutorialWaveAnimation();
		
		this.waveAnimation++;
		
		this.wTutorialTimer.start();
		
		v.getOTBtn().addMouseListener(this);
		v.getCTBtn().addMouseListener(this);
	}

	/**
	 * @author Eaviles 
	 * @Purpose regulate waves in the tutorial
	 */
	Timer wTutorialTimer = new Timer(30, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			m.gameDi = Toolkit.getDefaultToolkit().getScreenSize();
			moveTutorialWave();
		}
	});

	/**
	 * @author Eaviles 
	 * @Purpose reset model and view after tutorial executes
	 */
	public void resetAll() {
		this.gameStatus = status.IN_PROGRESS;
		m.resetGameObjsArray();
		v.resetGameObjBtnsArray();
	}
		

	/**
	 * @author Eaviles
	 * @Purpose handle moving the tutorial waves for both animations
	 */
	public void moveTutorialWave() {
		if (this.waveAnimation == 1) {

			if(m.gettSWave1().getPosition().x > m.gettSWave1().getDestination().x){
				m.gettSWave1().move();
			} else{
				if (!tW1Collided) {
					waveCollision(m.gettSWave1());

					v.getErosionTWave1().setVisible(false);
					m.removeTutorialWave(1);
					tW1Collided = true;
				}
			}
			
			if(m.gettSWave2().getPosition().x > m.gettSWave2().getDestination().x){
				m.gettSWave2().move();
			} 
			else{
				waveCollision(m.gettSWave2());
				
				v.getErosionTWave2().setVisible(false);
				
				m.removeTutorialWave(2);
				v.playTutorialSequence(2);
				wTutorialTimer.stop();
			}
			
			
		} else if (this.waveAnimation == 2) {
			
			if (m.gettBWave().getPosition().x > m.gettBWave().getDestination().x) {
				m.gettBWave().move();
			} else {

				waveCollision(m.gettBWave());

				v.getBuildingTWave().setLocation(1000, 400);// this is a hack, but it works when nothing else does
				Point wLoc = new Point(v.getBuildingTWave().getLocation());
				v.getJPanel().getComponentAt(wLoc).setVisible(false);
				m.removeTutorialWave(3);

				switch (tutorialPickUp) {
				case TUTORIAL_O:
					currBox.setCount(currBox.getCount()-1);
					currBox.setObjIcon(m.getGabionImages()[currBox.getCount()]);
					v.playTutorialSequence(6);
					break;
				case TUTORIAL_C:
					currBox.setCount(currBox.getCount()-1);
					currBox.setObjIcon(m.getGabionImages()[currBox.getCount()]);
					v.playTutorialSequence(7);
					break;
				default:
					System.out.println("error in Controller.tutorialWTimer: tutorialPickUp invalid type");
					break;
				}

				v.playTutorialSequence(8);
				tutorial = false;

				resetAll();
				
				initViewBtnListeners();
				

				wTimer.start();
				wTutorialTimer.stop();
			}
		} else{
			System.out.println("error in Controller.moveWave(): waveAnimation set to: " + this.waveAnimation);
		}
	}
		
/*
 * Initialization functions
 */
	/**
	 * @author Eaviles
	 * @Purpose adds listeners to buttons in View
	 */
	private void initViewBtnListeners() {
		for(button b : v.gameObjBtns){
			b.addMouseListener(this);
		}			
	}

	/**
	 * @author Auzi
	 * @Purpose initializes game Menu
	 */
	
	private void initMenu(){
		//System.out.println(v.getMenu);
		((JButton)v.getMenu().getContentPane().getComponent(0)).addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				tutorial = true;

				resetAll();
				
				initViewBtnListeners();
				

				
				v.getMenu().setAlwaysOnTop(false);
				v.getMenu().dispose();
				startTutorial();
				
				v.getMenu().dispose();	
			}
		});	
		//clicking new game button
		((JButton)v.getMenu().getContentPane().getComponent(1)).addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				v.getMenu().setAlwaysOnTop(false);
				v.getMenu().dispose();

				tutorial = false;

				resetAll();
				v.updateViewObjs();
				
				
				initViewBtnListeners();
				
				

				wTimer.start();
				wTutorialTimer.stop();
				
			}
			
		});
		
		((JButton)v.getMenu().getContentPane().getComponent(2)).addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				for (GameObject go : m.getGameObjs()) {
					save(go, fname);
					saveFileNum = saveFileNum + 1;
					fname = Integer.toString(saveFileNum) + ".sav";
				}
				
			}
			
		
		});
		
		((JButton)v.getMenu().getContentPane().getComponent(3)).addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				wTimer.stop();
				pTimer.stop();
				v.getMenu().setAlwaysOnTop(false);
				v.getMenu().dispose();
				v.dispose();
				
			}
		
		});
	}
/*
 * Setters & Getters
 */
	/**
	 * @return gameStatus (current win/lose/inprogress status of game)
	 */
	public status getGameStatus() {
		return gameStatus;
	}

	/**
	 * @param gameStatus set gameStatus to this
	 */
	public void setGameStatus(status gameStatus) {
		this.gameStatus = gameStatus;
	}

	/**
	 * @return objToPickUp
	 */
	public Point getObjToPickUp() {
		return objToPickUp;
	}

	/**	
	 * @param objToPickUp set objToPickUp to this
	 */
	public void setObjToPickUp(Point objToPickUp) {
		this.objToPickUp = objToPickUp;
	}

	
/*
 * Unused mouse listener functions (these are required to implement MouseListener, even if they're not used)
 */
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
